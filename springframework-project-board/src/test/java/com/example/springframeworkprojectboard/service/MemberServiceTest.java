package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.domain.Member;
import com.example.springframeworkprojectboard.dto.MemberDto;
import com.example.springframeworkprojectboard.mapper.MemberMapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@DisplayName("회원 관리 service 설계 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
class MemberServiceTest {

    @InjectMocks
    private MemberServiceImpl memberService;

    @Autowired
    private ModelMapper mapper;
    @Autowired private MemberMapper memberMapper;
    @Mock
    private MemberMapper mockMemberMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("회원 service 설계 테스트 - 중복되는 회원 계정이 없으면 회원 정보를 입력하면 회원을 생성 한다")
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

        Member createdMember = mapper.map(memberDto, Member.class);

        //When
        if (!mockMemberMapper.isExistByAccount("test00")) {
            mockMemberMapper.save(createdMember);
        }

        //Then
        then(mockMemberMapper).should().save(any(Member.class));
    }

    @DisplayName("회원 service 설계 테스트 - 고유 번호가 맞으면 회원을 조회한다")
    @Test
    void givenMemberId_whenGettingMember_thenReturnsMember() throws Exception {
        //Given
        long memberId = 2;

        //When
        Member findedMember = memberMapper.findMemberById(memberId);
        MemberDto member = mapper.map(findedMember, MemberDto.class);

        //Then
        assertNotNull(member);
    }

    @DisplayName("회원 service 설계 테스트 - 회원 계정이 맞으면 회원을 조회한다")
    @Test
    void givenAccount_whenGettingMember_thenReturnsMember() throws Exception {
        //Given
        String account = "test0";

        //When
        Member findedMember = memberMapper.findMemberByAccount(account);
        MemberDto member = mapper.map(findedMember, MemberDto.class);

        //Then
        assertNotNull(member);
    }

    @DisplayName("회원 service 설계 테스트 - 회원 수정 정보를 입력하면 회원 정보를 수정한다 ")
    @Test
    void givenBoard_whenUpdatingBoard_thenUpdatesBoard() throws Exception {
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

        Member originalMember = mapper.map(memberDto, Member.class);
        mockMemberMapper.save(originalMember);

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

        Member updatedMember = mapper.map(updatedMemberDto, Member.class);

        //When
        mockMemberMapper.update(updatedMember);

        //Then
        assertNotEquals(originalMember, updatedMember);

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

        Member member = mapper.map(memberDto, Member.class);
        mockMemberMapper.save(member);

        //When
        mockMemberMapper.deleteMemberByMemberId(member.getId());

        //Then
        Member deletedMember = mockMemberMapper.findMemberById(member.getId());
        assertNull(deletedMember);
    }

}