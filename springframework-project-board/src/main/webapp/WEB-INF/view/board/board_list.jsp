<%--
  게시판 뷰 페이지
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>AeBong's Board</title>
    <script type="text/javascript">
        function checkForm() {
            <%--if (${sessionMemberId == null}) {--%>
            <%--    alert("로그인 해주세요.");--%>
            <%--    location.href = "/member/login";--%>
            <%--}--%>
            location.href = "/board/board_register";
        }
    </script>
</head>
<body>
<jsp:include page="/resources/layout/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <%-- 검색 기능 추가 --%>
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Search</h5>
                        <form action="/board/board_list" method="get">
                            <input type="hidden" name="size" value="${(requestDto.size == 0 || requestDto.size == null) ? 10 : requestDto.size}">
                            <div class="mb-3">
                                <input type="checkbox" name="types" value="t"${requestDto.checkType("t") ? " checked" : ""}>제목
                                <input type="checkbox" name="types" value="w"${requestDto.checkType("w") ? " checked" : ""}>작성자
                                <input type="text" name="keyword" class="form-control" value="${requestDto.keyword}">
                            </div>
                            <div class="input-group mb-3 dueDateDiv">
                                <input type="date" name="from" class="form-control" value="${requestDto.from}">
                                <input type="date" name="to" class="form-control" value="${requestDto.to}">
                            </div>
                            <div class="input-group mb-3 d-flex justify-content-between">
                                <div>
                                    <button class="btn btn-primary" type="submit">Search</button>
                                    <button class="btn btn-info clearBtn" type="reset">Clear</button>
                                </div>
                                <div>
                                    <a href="#" onclick="checkForm(); return false;" class="btn btn-primary">&laquo;글쓰기</a>
                                </div>
                            </div>
                            <script>
                                /* Clear 버튼 클릭 시 검색 파라미터 값 초기화 */
                                document.querySelector('.clearBtn').addEventListener('click', function (e) {
                                    self.location = '/board/board_list';
                                });
                            </script>

                        </form>
                    </div>
                </div>

            </div>
        </div>
        <%-- 기존 코드 --%>
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        Featured
                        <button class="btn btn-primary btn-sm">total:${response.total} 건</button>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">글번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">작성자</th>
                                <th scope="col">작성일</th>
                                <th scope="col">수정일</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="startNo" value="${response.startNo}" />
                            <c:forEach var="dto" items="${response.dtoList}">
                                <tr>
                                    <th scope="row">${startNo}</th>
                                    <td><a href="/board/board_view?boardId=${dto.id}&${requestDto.link}" class="text-decoration-none"
                                           data-tno="${dto.id}">
                                        <c:out value="${dto.title}" />
                                    </a></td>
                                    <td>${dto.memberName}</td>
                                    <td>${dto.createdAt}</td>
                                    <td>${dto.updatedAt}</td>
                                </tr>
                                <c:set var="startNo" value="${startNo - 1}" />
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="float-end">
                            <ul class="pagination flex-wrap">
                                <c:if test="${response.prev}">
                                    <li class="page-item">
                                        <a class="page-link" data-num="${response.start -1}">Prev</a>
                                    </li>
                                </c:if>
                                <c:forEach var="num" begin="${response.start}" end="${response.end}">
                                    <li class="page-item ${response.page == num ? "active" : ""}">
                                        <a class="page-link" data-num="${num}">${num}</a></li>
                                </c:forEach>
                                <c:if test="${response.next}">
                                    <li class="page-item">
                                        <a class="page-link" data-num="${response.end + 1}">Next</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                        <script>
                            /**
                             * 페이징 기능과 검색 기능을 모두 리스트에 추가하는 경우
                             * 단순 a태그의 href 속성을 이용하는 경우
                             * 적용이 어려울 수 있으므로 자바스크립트로 처리.
                             * pagination 클래스에 이벤트를 적용시킴
                             */
                            document.querySelector('.pagination').addEventListener('click', function (e) {
                                e.preventDefault();
                                e.stopPropagation();

                                //이벤트가 발생된 태그가 a 태그가 아닐 경우 이벤트를 실행하지 않음.
                                const target = e.target
                                if (target.tagName !== 'A') {
                                    return;
                                }
                                const num = target.getAttribute('data-num');
                                const frmPage = document.querySelector('form');
                                frmPage.innerHTML += `<input type="hidden" name="page" value="\${num}">`;
                                frmPage.submit();
                                // self.location = `/todo/list?page=\${num}`;//백틱을 이용해서 템플릿으로 처리.
                            });
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row content">
    </div>
</div>
<jsp:include page="/resources/layout/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>
