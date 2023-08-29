package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface BoardMapper {

    /**
     * 게시글 저장
     * @param board
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void save(Board board) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    List<Board> findList() throws SQLException, ClassNotFoundException;



}
