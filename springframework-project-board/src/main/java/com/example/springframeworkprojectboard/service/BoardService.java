package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import com.example.springframeworkprojectboard.dto.PageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

public interface BoardService {

    void registerBoard(BoardDto boardDto, MultipartFile file) throws Exception;

    PageResponseDto<BoardDto> getBoardList(PageRequestDto requestDto) throws SQLException, ClassNotFoundException;

    BoardDto getBoard(long boardId) throws SQLException, ClassNotFoundException;

    void modifyBoard(BoardDto boardDto, MultipartFile file) throws Exception;

    void removeBoard(long boardId) throws SQLException, ClassNotFoundException;

}
