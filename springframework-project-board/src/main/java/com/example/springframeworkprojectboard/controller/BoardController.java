package com.example.springframeworkprojectboard.controller;

import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import com.example.springframeworkprojectboard.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardServiceImpl boardService;

    @GetMapping("/board_register")
    public String showRegisterForm() {
        return "board/board_register";
    }

    @PostMapping("/board_register")
    public String createBoard(BoardDto boardDto, HttpServletRequest request, Model model) {
        log.info("BoardController: POST - createBoard()");
        try {
            HttpSession session = request.getSession();

            boardDto.setMemberId(Long.parseLong((String) session.getAttribute("sessionMemberId")));
            boardDto.setMemberName((String) session.getAttribute("sessionMemberName"));
            boardDto.setIp(request.getRemoteAddr());
            model.addAttribute("board", boardDto);
            return "redirect:/board/board_list";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board/board_register";
    }

    @GetMapping("/board_list")
    public String listBoards(PageRequestDto requestDto, Model model) {
        log.info("BoardController: GET - boardList()");
        try {
            model.addAttribute("response", boardService.getBoardList(requestDto));
            return "board/board_list";
        } catch (Exception e) {
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
    public String modifyBoard(PageRequestDto requestDto, BoardDto boardDto,
                              HttpServletRequest request, Model model) {
        log.info("BoardController: POST - modifyBoard()");
        try {
            HttpSession session = request.getSession();

            boardDto.setMemberId(Long.parseLong((String) session.getAttribute("sessionMemberId")));
            boardDto.setMemberName((String) session.getAttribute("sessionMemberName"));
            boardDto.setIp(request.getRemoteAddr());
            model.addAttribute("boardId", boardDto.getId());
            boardService.modifyBoard(boardDto);
            return "redirect:/board/board_view";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board/board_modify";
    }

    @PostMapping("/board_remove")
    public String removeBoard(PageRequestDto requestDto, long boardId) {
        log.info("BoardController: POST - deleteBoard()");
        try {
            boardService.removeBoard(boardId);
            return "redirect:/board/board_list" + requestDto.getLink();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board/board_modify";
    }
}
