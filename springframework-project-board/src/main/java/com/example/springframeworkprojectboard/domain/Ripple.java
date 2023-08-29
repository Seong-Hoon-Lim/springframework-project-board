package com.example.springframeworkprojectboard.domain;

import java.time.LocalDateTime;

public class Ripple {
    private long id;
    private Board board;
    private Member member;
    private String content;
    private String ip;
    private LocalDateTime createdAt;

    //로그인 상태가 아닐 때는 '댓글 삭제' 버튼이 생기지 않도록 하기 위한 설정
    private boolean isLogin;
}
