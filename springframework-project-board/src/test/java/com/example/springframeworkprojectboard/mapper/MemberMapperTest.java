package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mapper 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
class MemberMapperTest {

    @Autowired(required = false)
    private MemberMapper memberMapper;

    @DisplayName("Mapper 테스트 - 회원 등록 테스트. 생성 완료 되면 테스트 성공")
    @Test
    void givenMember_whenSavingMember_thenMember() throws SQLException, ClassNotFoundException {
        //Given
        Member member = Member.builder()
                .account("hooney")
                .password("hooney1234")
                .name("hooney")
                .gender("남")
                .birth("19900101")
                .email("hooney@gmail.com")
                .phone("01012341111")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 1호")
                .createdAt(LocalDateTime.now())
                .build();
        //When
        memberMapper.save(member);

        //Then
        assertNotNull(member);
    }
}