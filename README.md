# 프로젝트 : springframework-project-board
 - SpringFramework(스프링 레거시) 를 활용하여 간단한 회원관리 및 게시판 프로젝트 구현
 - MVC 패턴과 싱글톤 패턴으로 각 생성자 활용
 - HikariCP 활용DB 커넥션
 - Mock, MockMvc 를 활용한 TDD(테스트 주도 개발) 방식 개발
 - 도메인과 DTO 간 ModelMapper 매핑 전략 활용
 - GitKraken 을 통한 Git Flow Feature Branch 전략으로 형상 관리

## 주요 기능

* ajax 를 활용한 회원 가입 시 계정 중복 체크, 패스워드 확인 체크 및 댓글 생성/조회/삭제 기능 구현
* 도로명주소 API 적용
* jjwt 라이브러리 활용 JWT 토큰 생성/인증 활용 간단한 로그인 인증
* BCryptPasswordEncoder 를 활용한 패스워드 암호화 처리
* Page 관련 커스텀 request, response DTO 객체 활용하여 게시판의 정렬, 페이징, 검색(키워드, 날짜) 구현
* apache-commons-fileupload 활용 파일 업로드
* 그 외 회원 조회/생성/수정/삭제, 게시판 조회/생성/수정/삭제 기본 CRUD 기능 구현

## 개발 환경

* window 10
* Intellij IDEA Ultimate 2022.3.2
* Java 8
* Maven
* Tomcat 9.0.71
* Springframework 5.3.25
* Spring Security Core 5.4.0
* Servlet API 4.0.1
* MySQL 8.0.31
* MyBatis 3.5.9
* HikariCP 5.0.0
* Apache Commons FileUpload 1.4
* Json Simple 1.1.1
* jjwt 0.10.5
* jackson-databind 2.11.2
* Junit 5 (Test 환경)
* Mockito 5.3.1 (Test 환경)
* hamcrest 1.3 (test 환경)
* json path 2.4.0 (test 환경)
* Bootstrap 5.1

## 기술 세부 스택

* SpringFramework
* Spring Security
* JWT
* JSP
* Servlet
* MySQL
* MyBatis
* Javascript
* Ajax

## 스프링 MVC 구조
![spring MVC 구조](https://github.com/Seong-Hoon-Lim/springframework-project-board/assets/108711069/ddcf42ad-f8a7-4a8e-aa39-97a81684648a)

## 서블릿 필터 구조
![servlet filter 구조](https://github.com/Seong-Hoon-Lim/springframework-project-board/assets/108711069/5d2e421e-3d47-4e5e-ba26-c0d359e998a1)


