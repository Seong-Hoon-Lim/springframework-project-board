package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberMapper {

    /**
     * 회원 계정 DB 중복 검증
     * @param account
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    boolean isExistByAccount(String account) throws SQLException, ClassNotFoundException;

    /**
     * 회원 DB 저장
     * @param member
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void save(Member member) throws SQLException, ClassNotFoundException;

    /**
     * 고유번호로 회원 DB 조회
     * @param memberId
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Member findMemberById(long memberId) throws SQLException, ClassNotFoundException;

    /**
     * 계정으로 회원 DB 조회
     * @param account
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    Member findMemberByAccount(String account) throws SQLException, ClassNotFoundException;

    /**
     * 회원 DB 수정
     * @param member
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void update(Member member) throws SQLException, ClassNotFoundException;

    /**
     * 회원 DB 삭제
     * @param memberId
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void deleteMemberByMemberId(long memberId) throws SQLException, ClassNotFoundException;

}
