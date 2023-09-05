package com.example.springframeworkprojectboard.controller;

import com.example.springframeworkprojectboard.dto.MemberDto;
import com.example.springframeworkprojectboard.service.MemberServiceImpl;
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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원 관리 controller 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
@WebAppConfiguration
class MemberControllerTest {

    @Autowired WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired MemberServiceImpl memberService;
    @Mock private MemberServiceImpl mockMemberService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("회원 관리 POST controller 테스트 - 회원 중복 체크 후 Http OK 면 테스트 성공 ")
    @Test
    void givenAccount_whenRequestingCheckDuplicateAccount_thenReturnsHttpResponse() throws Exception {
        //Given
        MemberDto originalMemberDto = MemberDto.builder()
                .account("test0")
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

        String account = "test00";
        when(mockMemberService.hasDuplicateMember(account)).thenReturn(false);

        //When & Then
        mockMvc.perform(post("/member/checkAccount")
                        .param("account", account))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("false"));
    }

    @DisplayName("회원 관리 GET controller 테스트 - 해당 되는 고유 번호의 회원 정보를 가져와서 Http OK 면 테스트 성공 ")
    @Test
    void givenNothing_whenRequestingBoardView_thenReturnsBoardView() throws Exception {
        //Given
        long memberId = 2;

        //When
        mockMvc.perform(get("/member/member_view")
                        .param("memberId", String.valueOf(memberId)))

                //Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/member_view"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("member"));
    }

    @DisplayName("회원 관리 POST controller 동작 테스트 - 회원 정보를 가져와서 회원 생성 되면 테스트 성공 ")
    @Test
    void givenMember_whenPostingMemberRegister_thenRedirectsToLoginForm() throws Exception {
        // Given
        MemberDto memberDto = MemberDto.builder()
                .account("test00")
                .password("test1234")
                .name("가길동")
                .gender("남")
//                .birth("1990/01/03")  // 원래는 Controller에서 설정되지만 테스트를 위해 여기에서 미리 설정
//                .email("nagildong@gmail.com")  // 원래는 Controller에서 설정되지만 테스트를 위해 여기에서 미리 설정
                .phone("01012341113")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 3호")
                .createdAt(LocalDateTime.now())
                .build();

        when(mockMemberService.hasDuplicateMember(anyString())).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/member/member_register")
                        .param("account", "test00")
                        .param("password", "test1234")
                        .param("name", "가길동")
                        .param("gender", "남")
                        .param("birthyy", "1990")
                        .param("birthmm", "01")
                        .param("birthdd", "03")
                        .param("mail1", "nagildong")
                        .param("mail2", "gmail.com")
                        .param("phone", "01012341113")
                        .param("zipcode", "34005")
                        .param("addr1", "대전광역시 유성구 대덕대로1111번길 1-8")
                        .param("addr2", "가나타운 1동 3호"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/login"));

    }

    @DisplayName("회원 수정 POST controller 동작 테스트 - 회원 정보를 가져와서 회원 수정이 되면 테스트 성공 ")
    @Test
    void givenMember_whenPostingMemberModify_thenRedirectsToMemberView() throws Exception {
        // Given
        MemberDto createMemberDto = MemberDto.builder()
                .account("test00")
                .password("test1234")
                .name("가길동")
                .gender("남")
//                .birth("1990/01/03")  // 원래는 Controller에서 설정되지만 테스트를 위해 여기에서 미리 설정
//                .email("nagildong@gmail.com")  // 원래는 Controller에서 설정되지만 테스트를 위해 여기에서 미리 설정
                .phone("01012341113")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 3호")
                .createdAt(LocalDateTime.now())
                .build();

        when(mockMemberService.hasDuplicateMember(anyString())).thenReturn(false);

        mockMvc.perform(post("/member/member_register")
                        .param("account", "test00")
                        .param("password", "test1234")
                        .param("name", "가길동")
                        .param("gender", "남")
                        .param("birthyy", "1990")
                        .param("birthmm", "01")
                        .param("birthdd", "03")
                        .param("mail1", "nagildong")
                        .param("mail2", "gmail.com")
                        .param("phone", "01012341113")
                        .param("zipcode", "34005")
                        .param("addr1", "대전광역시 유성구 대덕대로1111번길 1-8")
                        .param("addr2", "가나타운 1동 3호"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/login"));

        MemberDto findMember = memberService.getMember(createMemberDto.getAccount());

        MemberDto memberDto = MemberDto.builder()
                .account(findMember.getAccount())
                .password("test1234")
                .name("가길녀")
                .gender("여")
//                .birth("1990/01/03")  // 원래는 Controller에서 설정되지만 테스트를 위해 여기에서 미리 설정
//                .email("nagildong@gmail.com")  // 원래는 Controller에서 설정되지만 테스트를 위해 여기에서 미리 설정
                .phone("01012341113")
                .zipcode("34005")
                .addr1("대전광역시 유성구 대덕대로1111번길 1-8")
                .addr2("가나타운 1동 3호")
                .updatedAt(LocalDateTime.now())
                .build();

        when(mockMemberService.hasDuplicateMember(anyString())).thenReturn(false);

        // When & Then
        mockMvc.perform(post("/member/member_modify")
                        .param("account", "test00")
                        .param("password", "test1234")
                        .param("name", "가길녀")
                        .param("gender", "여")
                        .param("birthyy", "1990")
                        .param("birthmm", "01")
                        .param("birthdd", "03")
                        .param("mail1", "nagildong")
                        .param("mail2", "gmail.com")
                        .param("phone", "01012341113")
                        .param("zipcode", "34005")
                        .param("addr1", "대전광역시 유성구 대덕대로1111번길 1-8")
                        .param("addr2", "가나타운 1동 3호"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/member_view"));
    }

    @DisplayName("회원 삭제 POST controller 동작 테스트 - 회원 ID를 제공하여 회원 삭제가 되면 테스트 성공 ")
    @Test
    void givenMemberId_whenPostingMemberRemove_thenRedirectsToBoardList() throws Exception {
        // Given
        long memberId = 8; // 이 ID는 예시이며 실제 테스트 케이스에 따라 다를 수 있습니다.

        doNothing().when(mockMemberService).removeMember(memberId);

        // When & Then
        mockMvc.perform(post("/member/member_remove")
                        .param("memberId", String.valueOf(memberId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/board_list"));
    }
    
}