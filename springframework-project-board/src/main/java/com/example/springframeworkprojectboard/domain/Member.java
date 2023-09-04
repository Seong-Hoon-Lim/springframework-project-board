package com.example.springframeworkprojectboard.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private long id;
    private String account;
    private String password;
    private String name;
    private String gender;
    private String birth;
    private String email;
    private String phone;
    private String zipcode; //우편번호
    private String addr1;
    private String addr2;   //상세주소
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
