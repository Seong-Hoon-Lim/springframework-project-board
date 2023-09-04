package com.example.springframeworkprojectboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
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
