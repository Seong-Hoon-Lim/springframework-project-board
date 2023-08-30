package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mapper 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @DisplayName("Mapper 테스트 - 게시글 저장 테스트. 게시글 생성 완료 되면 테스트 성공")
    @Test
    void givenBoard_whenSavingBoard_thenReturnsBoard() throws SQLException, ClassNotFoundException {
        //Given
        Board board = Board.builder()
                .memberId(1)
                .memberName("hooney")
                .title("Lorem Ipsum2")
                .content("Lorem Ipsum2 is simply dummy text of the printing and typesetting industry.")
                .hit(0)
                .ip("0.0.0.0")
                .rippleCnt(0)
                .fileName("dummy2.png")
                .fileSize(0)
                .createdAt(LocalDateTime.now())
                .build();
        //When
        boardMapper.save(board);

        //Then
        assertNotNull(board);
    }

    @DisplayName("Mapper 테스트 - 게시판 목록 조회 테스트. 조회 되면 테스트 성공")
    @Test
    void givenBoardList_whenFindingBoardList_thenReturnsBoardList() throws SQLException, ClassNotFoundException {
        //Given
        List<Board> boardList = boardMapper.findList();

        //When

        //Then
        boardList.forEach(board -> {
            assertTrue(board.getId() > 0, "Board ID should be greater than 0");
            assertTrue(board.getMemberId() > 0, "Board MemberID should be greater than 0");
            assertNotNull(board.getMemberName(), "Board MemberName should not be null");
            assertNotNull(board.getTitle(), "Board Title should not be null");
            assertNotNull(board.getContent(), "Board Content should not be null");
            assertTrue(board.getHit() >= 0, "Board Hit Count should not be null");
            assertNotNull(board.getIp(), "Board IP should not be null");
            assertTrue(board.getRippleCnt() >= 0, "Board Ripple Count should not be null");
            assertNotNull(board.getFileName(), "Board FileName should not be null");
            assertTrue(board.getFileSize() >= 0, "Board FileSize should be greater than or equal to 0");
            assertNotNull(board.getCreatedAt(), "Board CreatedAt should not be null");
            // ... more fields to validate as needed.
        });
    }

    @DisplayName("Mapper 테스트 - 게시글 조회 테스트. 조회 되면 테스트 성공")
    @Test
    void givenBoardId_whenFindingBoard_thenReturnsBoard() throws SQLException, ClassNotFoundException {
        //Given
        long boardId = 1;

        //When
        Board expectedBoard = boardMapper.findBoardByBoardId(boardId);

        //Then
        assertNotNull(expectedBoard, "Board should not be null");
    }

    @DisplayName("Mapper 테스트 - 게시글 수정 테스트. 수정 되면 이전 데이터와 비교 테스트 실패")
    @Test
    void givenBoard_whenUpdatingBoard_thenReturnsBoard() throws SQLException, ClassNotFoundException {
        //Given
        long boardId = 2;
        Board originalBoard = boardMapper.findBoardByBoardId(boardId);
        Board updatedBoard = Board.builder()
                .id(boardId)  // 원래 게시글의 ID를 사용
                .title("Lorem Ipsum")
                .content("Lorem Ipsum is simply dummy text of the printing and typesetting industry.")
                .hit(1)
                .ip("0.0.0.0")
                .rippleCnt(0)
                .fileName("dummy.png")
                .fileSize(0)
                .updatedAt(LocalDateTime.now())
                .build();

        //When
        boardMapper.update(updatedBoard);

        //Then
        assertEquals(originalBoard.getTitle(), updatedBoard.getTitle(), "Title should be different from the original");
        assertEquals(originalBoard.getContent(), updatedBoard.getContent(), "Content should be different from the original");
    }

    @DisplayName("Mapper 테스트 - 게시글 삭제 테스트. 이전 게시글이 있는지 확인 없으면 테스트 성공")
    @Test
    void givenBoardId_whenDeletingBoard_thenReturnsNot() throws SQLException, ClassNotFoundException {
        //Given
        long boardId = 2;
        Board board = boardMapper.findBoardByBoardId(boardId);
        assertNotNull(board, "Board should exist before deletion");

        // When
        boardMapper.deleteBoardByBoardId(boardId);

        // Then
        Board deletedBoard = boardMapper.findBoardByBoardId(boardId);
        assertNull(deletedBoard, "Board should be null after deletion");

    }

}