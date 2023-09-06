package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.dto.MemberDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Log4j2
public class TokenProvider {

    //시크릿 key
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //토큰 생성 메소드
    public String generateToken(MemberDto memberDto) {  // MemberDto 사용
        //유효기간 설정(7일)
        Date expDate = Date.from(
                Instant.now()
                        .plus(7, ChronoUnit.DAYS)
        );
        //토큰 생성
        String token = Jwts.builder()
                //header 내용
                .signWith(SECRET_KEY)
                //payload 내용
                .setSubject(memberDto.getAccount())  // MemberDto의 account 사용
                .setIssuer("AeBong")  //토큰 발행자
                .setIssuedAt(new Date())  //토큰 발행일
                .setExpiration(expDate)  //토큰 만료일
                .compact();

        log.info("Token generated - account: {}, token: {}", memberDto.getAccount(), token);  // MemberDto의 account 사용
        return token;
    }

    //토큰 검증 메소드
    public String validateToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                //Base64로 디코딩 및 파싱 수행
                .parseClaimsJws(token)
                .getBody();

        String subject = claims.getSubject();
        log.info("Token validated - user: {}", subject);
        return subject;
    }
}
