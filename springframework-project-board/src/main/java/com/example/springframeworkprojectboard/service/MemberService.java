package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.dto.MemberDto;

public interface MemberService {

    /**
     * 회원 중복 검사 기능
     * @param account
     * @return
     * @throws Exception
     */
    boolean hasDuplicateMember(String account) throws Exception;

    /**
     * 회원 생성 기능
     * @param memberDto
     * @throws Exception
     */
    void registerMember(MemberDto memberDto) throws Exception;

    /**
     * 고유번호로 회원 조회 기능
     * @param memberId
     * @return
     * @throws Exception
     */
    MemberDto getMember(long memberId) throws Exception;

    /**
     * 회원 계정으로 회원 조회 기능
     * @param account
     * @return
     * @throws Exception
     */
    MemberDto getMember(String account) throws  Exception;

    /**
     * 회원 수정 기능
     * @param memberDto
     * @throws Exception
     */
    void modifyMember(MemberDto memberDto) throws Exception;

    /**
     * 회원 삭제 기능
     * @param memberId
     * @throws Exception
     */
    void removeMember(long memberId) throws Exception;

}
