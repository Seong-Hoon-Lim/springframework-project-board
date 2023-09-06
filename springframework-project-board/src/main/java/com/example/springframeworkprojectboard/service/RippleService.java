package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.dto.RippleDto;

import java.util.List;

public interface RippleService {

    /**
     * 댓글 저장 기능
     * @param rippleDto
     * @throws Exception
     */
    void registerRipple(RippleDto rippleDto) throws Exception;

    /**
     * 게시글 번호 활용 댓글 목록 조회 기능
     * @param boardId
     * @return
     * @throws Exception
     */
    List<RippleDto> getRippleList(long boardId) throws Exception;

    /**
     * 댓글 삭제 기능
     * @param rippleId
     * @throws Exception
     */
    void removeRipple(long rippleId) throws Exception;

}
