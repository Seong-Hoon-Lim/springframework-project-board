package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;

@Mapper
public interface MemberMapper {

    /**
     * 회원 저장
     * @param member
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    void save(Member member) throws SQLException, ClassNotFoundException;

}
