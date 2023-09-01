package com.example.springframeworkprojectboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private long id;
    private long memberId;
    private String memberName;
    private String title;
    private String content;
    private int hit;
    private String ip;
    private int rippleCnt;    //게시판에 등록된 댓글의 갯수
    private String fileName;  //파일명
    private long fileSize;    //파일크기
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
