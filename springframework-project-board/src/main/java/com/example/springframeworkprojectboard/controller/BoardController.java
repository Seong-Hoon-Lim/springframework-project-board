package com.example.springframeworkprojectboard.controller;

import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import com.example.springframeworkprojectboard.dto.PageResponseDto;
import com.example.springframeworkprojectboard.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardServiceImpl boardService;

    @GetMapping("/board_register")
    public String showRegisterForm(HttpServletRequest req) {
        if (req.getSession().getAttribute("sessionMemberAccount") == null ) {
            return "redirect:/board/board_list";
        }
        return "board/board_register";
    }

    @GetMapping("/upload/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new FileSystemResource("C:\\upload\\" + filename); // <-- 경로 수정
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/board_register")
    public String createBoard(@ModelAttribute BoardDto boardDto,
                              @RequestParam("addImage") MultipartFile file,
                              HttpServletRequest request,
                              Model model) {
        log.info("BoardController: POST - createBoard()");
        HttpSession session = request.getSession();
        boardDto.setMemberId((Long) session.getAttribute("sessionMemberId"));
        boardDto.setMemberName((String) session.getAttribute("sessionMemberAccount"));
        boardDto.setIp(request.getRemoteAddr());
        model.addAttribute("board", boardDto);
        log.info("BoardController: board - {}", boardDto);
        if (!file.isEmpty()) {
            boardDto.setFileName(file.getOriginalFilename());
            boardDto.setFileSize(file.getSize());
        }
        try {
            boardService.registerBoard(boardDto, file);
        } catch (Exception e) {
            log.error("에러 내용: ", e);
            e.printStackTrace();
        }
        return "redirect:/board/board_list";
    }

    @GetMapping("/board_list")
    public String listBoards(PageRequestDto requestDto, Model model) {
        log.info("BoardController: GET - boardList()");
        log.info("BoardController: request - {}", requestDto);

        // Set the time for the "from" and "to" fields
        if (requestDto.getFrom() != null) {
            requestDto.setFrom(requestDto.getFrom().with(LocalTime.MIN));
        }
        if (requestDto.getTo() != null) {
            requestDto.setTo(requestDto.getTo().with(LocalTime.MAX));
        }

        try {
            PageResponseDto<BoardDto> response = boardService.getBoardList(requestDto);
            model.addAttribute("response", response);

            return "board/board_list";
        } catch (Exception e) {
            log.error("에러 내용: ", e);
            e.printStackTrace();
        }
        return "board/board_list";
    }


    @GetMapping({"/board_view", "/board_modify"})
    public void showBoard(long boardId, PageRequestDto requestDto, Model model) {
        log.info("BoardController: GET - boardView()");
        try {
            BoardDto boardDto = boardService.getBoard(boardId);
            model.addAttribute("board", boardDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/board_modify")
    public String modifyBoard(PageRequestDto requestDto,
                              @ModelAttribute BoardDto boardDto,
                              @RequestParam("addImage") MultipartFile file,
                              HttpServletRequest request, Model model) {
        log.info("BoardController: POST - modifyBoard()");
        HttpSession session = request.getSession();
        boardDto.setMemberId((Long) session.getAttribute("sessionMemberId"));
        boardDto.setMemberName((String) session.getAttribute("sessionMemberAccount"));
        boardDto.setIp(request.getRemoteAddr());
        if (!file.isEmpty()) {
            boardDto.setFileName(file.getOriginalFilename());
            boardDto.setFileSize(file.getSize());
        }
        try {
            boardService.modifyBoard(boardDto, file);
            model.addAttribute("board", boardDto);
        } catch (Exception e) {
            log.error("에러 내용: ", e);
            e.printStackTrace();
        }
        return "redirect:/board/board_list";

    }

    @PostMapping("/board_remove")
    public String removeBoard(PageRequestDto requestDto, long boardId) {
        log.info("BoardController: POST - deleteBoard()");
        try {
            boardService.removeBoard(boardId);
            return "redirect:/board/board_list?" + requestDto.getLink();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board/board_modify";
    }
}
