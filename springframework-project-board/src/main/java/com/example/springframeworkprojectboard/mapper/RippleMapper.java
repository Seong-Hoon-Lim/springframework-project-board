package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Ripple;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface RippleMapper {

    /**
     * 댓글 저장
     * @param ripple
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void save(Ripple ripple) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 번호로 댓글 목록 조회
     * @param boardId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    List<Ripple> findListByBoardId(long boardId) throws SQLException, ClassNotFoundException;

    /**
     * 댓글 고유 번호 기준으로 게시글 조회
     * @param rippleId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    long findBoardByRippleId(long rippleId) throws SQLException, ClassNotFoundException;

    /**
     * 게시글 번호를 기준으로 저장된 댓글 개수 조회
     * @param boardId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    int findRippleCountByBoardId(long boardId) throws SQLException, ClassNotFoundException;

    /**
     * 댓글 삭제
     * @param rippleId
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void deleteRippleByRippleId(long rippleId) throws SQLException, ClassNotFoundException;

}
