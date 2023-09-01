package com.example.springframeworkprojectboard.controller;

import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.service.BoardServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("게시판 controller View 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
@WebAppConfiguration
class BoardControllerTest {

    @Autowired WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired BoardServiceImpl boardService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @DisplayName("게시판 controller View 테스트 - 게시판 목록을 가져와서 Http OK 면 테스트 성공 ")
    @Test
    void givenNothing_whenRequestingBoardListView_thenReturnsBoardListView() throws Exception {
        //Given

        //When
        mockMvc.perform(get("/board/board_list"))

        //Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("board/board_list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("response"));
    }

    @DisplayName("게시판 controller View 테스트 - 해당 되는 번호의 게시글을 가져와서 Http OK 면 테스트 성공 ")
    @Test
    void givenNothing_whenRequestingBoardView_thenReturnsBoardView() throws Exception {
        //Given
        long boardId = 3;

        //When
        mockMvc.perform(get("/board/board_view")
                        .param("boardId", String.valueOf(boardId)))

                //Then
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("board/board_view"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("board"));
    }

    @DisplayName("게시판 controller 동작 테스트 - 게시글 정보를 가져와서 게시글 생성 되면 테스트 성공 ")
    @Test
    void givenBoard_whenPostingBoardRegister_thenRedirectsToBoardList() throws Exception {
        //Given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionMemberId", "1");
        session.setAttribute("sessionMemberName", "hooney");
        BoardDto boardDto = BoardDto.builder()
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .build();

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/board/board_register")
                        .session(session)
                        .flashAttr("board", boardDto))
                .andExpect(status().is3xxRedirection())  // Expecting a redirect
                .andExpect(redirectedUrl("/board/board_list"));
    }

    @DisplayName("게시판 controller 동작 테스트 - 게시글 수정이 성공하면 테스트 성공")
    @Test
    void givenBoard_whenPostingBoardModify_thenRedirectsToBoardView() throws Exception {
        // Given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionMemberId", "1");
        session.setAttribute("sessionMemberName", "hooney");
        BoardDto createdBoardDto = BoardDto.builder()
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/board/board_register")
                        .session(session)
                        .flashAttr("board", createdBoardDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/board_list"))
                .andReturn();

        long boardId = 0;

        BoardDto updateBoardDto = BoardDto.builder()
                .id(boardId)
                .title("Modified Lorem Ipsum")
                .content("Modified content")
                .build();

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/board/board_modify")
                        .session(session)
                        .flashAttr("board", updateBoardDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/board_view?boardId=" + boardId)); // boardId 추가

    }

    @DisplayName("게시판 controller 동작 테스트 - 게시글 삭제가 성공하면 테스트 성공")
    @Test
    void givenBoard_whenPostingBoardRemove_thenRedirectsToBoardList() throws Exception {
        // Given
        int page = 1;
        int size = 10;
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionMemberId", "1");
        session.setAttribute("sessionMemberName", "hooney");
        BoardDto createdBoardDto = BoardDto.builder()
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/board/board_register")
                        .session(session)
                        .flashAttr("board", createdBoardDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/board_list"))
                .andReturn();

        long boardId = 0; // 적절한 값으로 설정

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/board/board_remove")
                        .session(session)
                        .param("boardId", String.valueOf(boardId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/board_list" + "page="+page + "&size="+size));  // 삭제 성공시 board_list 페이지로 리다이렉트
    }


}
