package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.domain.Board;
import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import com.example.springframeworkprojectboard.dto.PageResponseDto;
import com.example.springframeworkprojectboard.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final ModelMapper mapper;
    private final BoardMapper boardMapper;

    @Override
    public void registerBoard(BoardDto boardDto, MultipartFile file) throws Exception {
        // 파일을 디스크에 저장
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File uploadDir = new File("C:\\upload"); // 저장할 디렉터리
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            File uploadFile = new File(uploadDir, fileName);
            file.transferTo(uploadFile);
        }
        Board board = mapper.map(boardDto, Board.class);
        log.info("BoardService: registerBoard() - registerBoard: {}", board);
        boardMapper.save(board);
    }

    @Override
    public PageResponseDto<BoardDto> getBoardList(PageRequestDto requestDto) throws SQLException, ClassNotFoundException {
        List<Board> boards = boardMapper.findAllList(requestDto);
        List<BoardDto> boardList = boards.stream()
                .map(board -> mapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
        int total = boardMapper.findListCount(requestDto);

        PageResponseDto<BoardDto> responseDto = PageResponseDto.<BoardDto>withAll()
                .dtoList(boardList)
                .total(total)
                .requestDto(requestDto)
                .build();

//        log.info("BoardService: getBoardList() - {}", responseDto);
        return responseDto;
    }

    @Override
    public BoardDto getBoard(long boardId) throws SQLException, ClassNotFoundException {
        Board board = boardMapper.findBoardByBoardId(boardId);
        boardMapper.updateBoardHitByBoardId(boardId);
        log.info("BoardService: getBoard() - board {}", board);
        return mapper.map(board, BoardDto.class);
    }

    @Override
    public void modifyBoard(BoardDto boardDto, MultipartFile file) throws Exception {
        // 파일을 디스크에 저장
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File uploadDir = new File("C:\\upload"); // 저장할 디렉터리
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            File uploadFile = new File(uploadDir, fileName);
            file.transferTo(uploadFile);
        }
        Board board = mapper.map(boardDto, Board.class);
        log.info("BoardService: modifyBoard() - modifiedBoard: {}", board);
        boardMapper.update(board);
    }

    @Override
    public void removeBoard(long boardId) throws SQLException, ClassNotFoundException {
        log.info("BoardService: removeBoard()");
        boardMapper.deleteBoardByBoardId(boardId);
    }
}
