package com.example.springframeworkprojectboard.service;

import com.example.springframeworkprojectboard.domain.Board;
import com.example.springframeworkprojectboard.dto.BoardDto;
import com.example.springframeworkprojectboard.dto.PageRequestDto;
import com.example.springframeworkprojectboard.dto.PageResponseDto;
import com.example.springframeworkprojectboard.mapper.BoardMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("게시판 service 설계 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
class BoardServiceTest {

    @InjectMocks private BoardServiceImpl boardService;

    @Autowired private ModelMapper mapper;
    @Autowired private BoardMapper boardMapper;
    @Mock private BoardMapper mockBoardMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("게시판 service 설계 테스트 - 게시글 정보를 입력하면 게시글을 생성 한다")
    @Test
    void givenBoard_whenSavingBoard_thenSavesBoard() throws SQLException, ClassNotFoundException {
        //Given
        BoardDto boardDto = BoardDto.builder()
                .memberId(1)
                .memberName("hooney")
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .ip("127.0.0.1")
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .createdAt(LocalDateTime.now())
                .build();

        Board createdBoard = mapper.map(boardDto, Board.class);
        //When
        mockBoardMapper.save(createdBoard);

        //Then
        then(mockBoardMapper).should().save(any(Board.class));
    }

    @DisplayName("게시판 service 설계 테스트 - 페이징과 검색 조건이 맞으면 게시판 목록을 출력 한다")
    @Test
    void givenBoardList_whenSavingFindingBoardList_thenGetBoardList() throws SQLException, ClassNotFoundException {
        //Given
        PageRequestDto requestDto = PageRequestDto.builder()
                .page(2)
                .size(10)
                .types(new String[]{"t", "w"})
                .keyword("hooney")
                .from(LocalDateTime.of(2023, 9, 1, 0, 0, 0))
                .to(LocalDateTime.of(2023, 9, 2, 23, 59, 59))
                .build();

        List<Board> boardList = boardMapper.findAllList(requestDto);
        int total = 0;

        //When
        List<BoardDto> getList = boardList.stream()
                .map(board -> mapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
        total = boardMapper.findListCount(requestDto);

        PageResponseDto<BoardDto> responseDto = PageResponseDto.<BoardDto>withAll()
                .dtoList(getList)
                .total(total)
                .requestDto(requestDto)
                .build();
        //Then
        assertNotNull(responseDto); // responseDto가 null이 아니어야 함
        assertEquals(2, responseDto.getPage()); // 현재 페이지 번호가 2여야 함
        assertEquals(10, responseDto.getSize()); // 페이지당 게시물 수가 10개여야 함
        assertEquals(total, responseDto.getTotal()); // 전체 게시물 수가 total과 일치해야 함
        assertEquals(requestDto.getTypes().length, 2); // 검색 타입의 개수가 2여야 함
        assertFalse(responseDto.getDtoList().isEmpty()); // 게시판 목록이 비어있지 않아야 함

    }

    @DisplayName("게시판 service 설계 테스트 - 게시글 번호가 맞으면 게시글을 출력하면서 조회수 1증가한다")
    @Test
    void givenBoardId_whenGettingBoard_thenReturnsBoard() throws SQLException, ClassNotFoundException {
        //Given
        long boardId = 1;
        int hit = 0;
        Board findedBoard = boardMapper.findBoardByBoardId(boardId);

        //When
        BoardDto board = mapper.map(findedBoard, BoardDto.class);
        boardMapper.updateBoardHitByBoardId(boardId);
        //Then
        assertEquals(hit + 1, board.getHit());
    }

    @DisplayName("게시판 service 설계 테스트 - 게시글 수정 정보를 입력하면 게시글을 수정한다 ")
    @Test
    void givenBoard_whenUpdatingBoard_thenUpdatesBoard() throws SQLException, ClassNotFoundException {
        //Given
        BoardDto originalBoardDto = BoardDto.builder()
                .memberId(1)
                .memberName("hooney")
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .ip("127.0.0.1")
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .createdAt(LocalDateTime.now())
                .build();

        Board originalBoard = mapper.map(originalBoardDto, Board.class);
        mockBoardMapper.save(originalBoard);

        BoardDto updatedBoardDto = BoardDto.builder()
                .memberId(1)
                .memberName("hooney")
                .title("Lorem Ipsum1")
                .content("Lorem Ipsum1 is simply dummy text of the printing and typesetting industry.")
                .hit(originalBoard.getHit())
                .ip("127.0.0.1")
                .rippleCnt(0)
                .fileName("dummy1.png")
                .fileSize(0)
                .createdAt(originalBoard.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        Board updatedBoard = mapper.map(updatedBoardDto, Board.class);

        //When
        mockBoardMapper.update(updatedBoard);

        //Then
        assertNotEquals(originalBoard, updatedBoard);

    }

    @DisplayName("게시판 service 설계 테스트 - 게시글 번호가 맞으면 해당 게시글을 삭제한다")
    @Test
    void givenBoardId_whenDeletingBoard_thenRemovesBoard() throws SQLException, ClassNotFoundException {
        //Given
        BoardDto boardDto = BoardDto.builder()
                .id(1)
                .memberId(1)
                .memberName("hooney")
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .ip("127.0.0.1")
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .createdAt(LocalDateTime.now())
                .build();

        Board board = mapper.map(boardDto, Board.class);
        mockBoardMapper.save(board);

        //When
        mockBoardMapper.deleteBoardByBoardId(board.getId());

        //Then
        Board deletedBoard = mockBoardMapper.findBoardByBoardId(board.getId());
        assertNull(deletedBoard);

    }
}
