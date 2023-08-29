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
public class Member {
    private long id;
    private String userId;
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

    public Member(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
