package com.example.springframeworkprojectboard;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@DisplayName("DB 테스트")
@Log4j2
@ExtendWith(SpringExtension.class) //JUnit5 버전에서 spring-test를 이용하기 위한 설정
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/root-context.xml")
public class DBConnTest {

    @Autowired
    private DataSource dataSource;

    @DisplayName("DB 연결 테스트")
    @Test
    void givenDataSource_whenConnection_thenTrue() throws SQLException, ClassNotFoundException {
        //Given
        Connection conn = dataSource.getConnection();
        log.info("connection success: {}", conn);
        //When&Then
        Assertions.assertNotNull(conn);
    }
}
