package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import com.example.springframeworkprojectboard.dto.PageResponseDto;

import java.sql.SQLException;

public interface BoardService {

    void registerBoard(BoardDto boardDto) throws SQLException, ClassNotFoundException;

    PageResponseDto<BoardDto> getBoardList(PageRequestDto requestDto) throws SQLException, ClassNotFoundException;

    BoardDto getBoard(long boardId) throws SQLException, ClassNotFoundException;

    void modifyBoard(BoardDto boardDto) throws SQLException, ClassNotFoundException;

    void removeBoard(long boardId) throws SQLException, ClassNotFoundException;

}
