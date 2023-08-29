package com.example.springframeworkprojectboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
public class Board {
    private long id;
    private Member member;
    private String title;
    private String content;
    private Integer hit;
    private String ip;
    private Integer rippleCnt;  //게시판에 등록된 댓글의 갯수
    private String fileName;    //파일명
    private long fileSize;    //파일크기
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Board(long id) {this.id = id;}
}
