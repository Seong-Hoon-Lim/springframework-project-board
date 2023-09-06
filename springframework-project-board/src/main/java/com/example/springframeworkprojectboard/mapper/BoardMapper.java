package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Board;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
     * 게시판 목록 조회
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    List<Board> findList() throws SQLException, ClassNotFoundException;

    /**
     * 페이징 및 검색 처리가 포함 된
     * 게시판 목록 조회
     * @param requestDto
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    List<Board> findAllList(PageRequestDto requestDto) throws SQLException, ClassNotFoundException;

    /**
     * 게시판의 페이지 번호들을 구성하기 위한
     * 전체 데이터 수 조회
     * @param requestDto
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int findListCount(PageRequestDto requestDto) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 조회
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Board findBoardByBoardId(long boardId) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 조회수 증가
     * @param boardId
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void updateBoardHitByBoardId(long boardId) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 수정
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void update(Board board) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 삭제
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void deleteBoardByBoardId(long boardId) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 번호로 댓글 개수 업데이트
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void updateRippleCountByBoardId(Map<String, Object> params) throws SQLException, ClassNotFoundException;

}
