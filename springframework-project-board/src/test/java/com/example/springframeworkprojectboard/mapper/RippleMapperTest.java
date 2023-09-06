package com.example.springframeworkprojectboard.mapper;

import com.example.springframeworkprojectboard.domain.Ripple;
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
class RippleMapperTest {

    @Autowired private RippleMapper rippleMapper;

    @DisplayName("Mapper 테스트 - 댓글 저장. 댓글 생성 되면 테스트 성공")
    @Test
    void givenRipple_whenSavingRipple_thenReturnsRipple() throws SQLException, ClassNotFoundException {
        //Given
        Ripple ripple = Ripple.builder()
                .boardId(1)
                .memberId(1)
                .memberName("hooney")
                .content("hot coffee very cold!")
                .ip("127.0.0.1")
                .createdAt(LocalDateTime.now())
                .build();

        //When
        rippleMapper.save(ripple);

        //Then
        assertNotNull(ripple);
    }

    @DisplayName("Mapper 테스트 - 게시글 번호로 댓글 목록 조회. 조회 되면 테스트 성공")
    @Test
    void givenBoardId_whenFindingRippleList_thenReturnsRippleList() throws SQLException, ClassNotFoundException {
        //Given
        long boardId = 1;
        List<Ripple> rippleList = rippleMapper.findListByBoardId(1);

        //When & Then
        rippleList.forEach(ripple -> {
            assertTrue(ripple.getId() > 0);
            assertTrue(ripple.getBoardId() > 0);
            assertTrue(ripple.getMemberId() > 0);
            assertNotNull(ripple.getMemberName());
            assertNotNull(ripple.getContent());
            assertNotNull(ripple.getIp());
            assertNotNull(ripple.getCreatedAt());
        });
    }

    @DisplayName("Mapper 테스트 - 댓글 고유 번호 기준으로 게시글 조회. 조회 되면 테스트 성공")
    @Test
    void givenRippleId_whenFindingBoard_thenReturnsBoard() throws SQLException, ClassNotFoundException {
        //Given
        long rippleId = 1;

        //When
        long boardId = rippleMapper.findBoardByRippleId(rippleId);

        //Then
        assertEquals(1, boardId);
    }

    @DisplayName("Mapper 테스트 - 게시글 번호를 기준으로 저장된 댓글 개수 조회. 조회 되면 테스트 성공")
    @Test
    void givenBoardId_whenCountingRipples_thenReturnsRipples() throws SQLException, ClassNotFoundException {
        //Given
        long boardId = 1;
        int expectedCount = 2;

        //When
        int actualCount = rippleMapper.findRippleCountByBoardId(boardId);

        //Then
        assertEquals(expectedCount, actualCount);
    }

    @DisplayName("Mapper 테스트 - 댓글 삭제. 이전 댓글이 있는지 확인 없으면 테스트 성공")
    @Test
    void givenRippleId_whenDeletingRipple_thenReturnsNot() throws SQLException, ClassNotFoundException {
        //Given
        long rippleId = 2;
        long boardId = 1;
        int expectedCount = 2;

        //When
        rippleMapper.deleteRippleByRippleId(rippleId);
        int actualCount = rippleMapper.findRippleCountByBoardId(boardId);

        //Then
        assertNotEquals(expectedCount, actualCount);
    }

}