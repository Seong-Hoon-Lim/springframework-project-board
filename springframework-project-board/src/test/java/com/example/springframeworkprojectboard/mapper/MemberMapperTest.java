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
    void givenMember_whenSavingMember_thenReturnsMember() throws SQLException, ClassNotFoundException {
        //Given
        Member member = Member.builder()
                .account("test00")
                .password("test1234")
                .name("가길동")
                .gender("남")
                .birth("19900103")
                .email("nagildong@gmail.com")
                .phone("01012341113")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 3호")
                .createdAt(LocalDateTime.now())
                .build();
        //When
        memberMapper.save(member);

        //Then
        assertNotNull(member);
    }

    @DisplayName("Mapper 테스트 - 회원 계정 중복 테스트. 중복 되면 true 테스트 성공")
    @Test
    void givenAccount_whenIsExistingAccount_thenReturnsTrue() throws SQLException, ClassNotFoundException {
        //Given
        String account = "test";

        //When
        boolean isExistedAccount = memberMapper.isExistByAccount(account);

        //Then
        assertTrue(isExistedAccount);
    }

    @DisplayName("Mapper 테스트 - 고유번호로 회원 조회 테스트. 조회 되면 테스트 성공")
    @Test
    void givenMemberId_whenFindingMember_thenReturnsMember() throws SQLException, ClassNotFoundException {
        //Given
        long memberId = 2;

        //When
        Member findedMember = memberMapper.findMemberById(memberId);

        //Then
        assertNotNull(findedMember);
    }

    @DisplayName("Mapper 테스트 - 고유번호로 회원 조회 테스트. 조회 되면 테스트 성공")
    @Test
    void givenAccount_whenFindingMember_thenReturnsMember() throws SQLException, ClassNotFoundException {
        //Given
        String account = "test0";

        //When
        Member findedMember = memberMapper.findMemberByAccount(account);

        //Then
        assertNotNull(findedMember);
    }

    @DisplayName("Mapper 테스트 - 회원 수정 테스트. 수정 되면 이전 데이터와 비교 테스트 실패")
    @Test
    void givenMember_whenUpdatingMember_thenReturnsMember() throws SQLException, ClassNotFoundException {
        //Given
        long memberId = 3;
        Member originalMember = memberMapper.findMemberById(memberId);
        Member updatedMember = Member.builder()
                .account("test1")
                .password("test1234")
                .name("nagilnyeo")
                .gender("여")
                .birth("19900103")
                .email("nagilnyeo@gmail.com")
                .phone("01012341113")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 3호")
                .updatedAt(LocalDateTime.now())
                .build();

        //When
        memberMapper.update(updatedMember);

        //Then
        assertEquals(originalMember, updatedMember);
    }

    @DisplayName("Mapper 테스트 - 회원 삭제 테스트. 이전 회원이 있는지 확인 없으면 테스트 성공")
    @Test
    void givenMemberId_whenDeletingMember_thenReturnsNoting() throws SQLException, ClassNotFoundException {
        //Given
        long memberId = 4;

        //When
        memberMapper.deleteMemberByMemberId(memberId);
        Member deletedMember = memberMapper.findMemberById(memberId);

        //Then
        assertNull(deletedMember);
    }

}