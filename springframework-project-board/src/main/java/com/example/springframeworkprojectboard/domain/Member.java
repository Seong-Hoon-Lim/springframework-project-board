package com.example.springframeworkprojectboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class Member {
    private long id;
    private String memberId;
    private String password;
    private String name;
    private String gender;
    private String birth;
    private String email;
    private String phone;
    private String zipcode;     //우편번호
    private String addr1;
    private String addr2;   //상세주소
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
