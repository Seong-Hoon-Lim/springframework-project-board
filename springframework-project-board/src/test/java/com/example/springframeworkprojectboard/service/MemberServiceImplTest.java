package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.dto.MemberDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@DisplayName("회원 관리 service 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
class MemberServiceImplTest {

    @Autowired private MemberServiceImpl memberService;
    @Mock private MemberServiceImpl mockMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("회원 service 테스트 - 회원 정보를 입력하면 회원을 생성 한다")
    @Test
    void givenMember_whenSavingMember_thenSavesMember() throws Exception {
        //Given
        MemberDto memberDto = MemberDto.builder()
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
        if (!mockMemberService.hasDuplicateMember("test00")) {
            mockMemberService.registerMember(memberDto);
        }

        //Then
        then(mockMemberService).should().registerMember(any(MemberDto.class));
    }

    @DisplayName("회원 service 테스트 - 고유 번호가 맞으면 회원을 조회한다")
    @Test
    void givenMemberId_whenGettingMember_thenReturnsMember() throws Exception {
        //Given
        long memberId = 2;

        //When
        MemberDto member = memberService.getMember(memberId);

        //Then
        assertNotNull(member);
    }

    @DisplayName("회원 service 설계 테스트 - 회원 계정이 맞으면 회원을 조회한다")
    @Test
    void givenAccount_whenGettingMember_thenReturnsMember() throws Exception {
        //Given
        String account = "test0";

        //When
        MemberDto member = memberService.getMember(account);

        //Then
        assertNotNull(member);
    }

    @DisplayName("회원 service 설계 테스트 - 회원 수정 정보를 입력하면 회원 정보를 수정한다 ")
    @Test
    void givenBoard_whenUpdatingBoard_thenUpdatesBoard() throws Exception {
        //Given
        MemberDto originalMemberDto = MemberDto.builder()
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

        mockMemberService.registerMember(originalMemberDto);

        MemberDto updatedMemberDto = MemberDto.builder()
                .account("test01")
                .password("test1234")
                .name("가길녀")
                .gender("여")
                .birth("19900103")
                .email("nagildong@gmail.com")
                .phone("01012341113")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 3호")
                .createdAt(LocalDateTime.now())
                .build();

        //When
        mockMemberService.modifyMember(updatedMemberDto);

        //Then
        assertNotEquals(originalMemberDto, updatedMemberDto);

    }

    @DisplayName("회원 service 설계 테스트 - 고유번호가 맞으면 해당 회원을 삭제한다")
    @Test
    void givenBoardId_whenDeletingBoard_thenRemovesBoard() throws Exception {
        //Given
        MemberDto memberDto = MemberDto.builder()
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

        mockMemberService.registerMember(memberDto);

        //When
        mockMemberService.removeMember(memberDto.getId());

        //Then
        MemberDto deletedMember = mockMemberService.getMember(memberDto.getId());
        assertNull(deletedMember);
    }
}
