<%--
  게시글 수정 뷰 페이지
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Hello spring!</title>
</head>
<body>
<jsp:include page="/resources/layout/header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        게시글
                    </div>
                    <div class="card-body">
                        <form action="/board/board_modify" method="post" enctype="multipart/form-data">
                            <div class="input-group mb-3">
                                <span class="input-group-text">글번호</span>
                                <input type="text" name="id" class="form-control"
                                       value='<c:out value="${board.id}"></c:out>' readonly>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text">제목</span>
                                <input type="text" name="title" class="form-control"
                                       value='<c:out value="${board.title}"></c:out>'>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text">작성일</span>
                                <input type="text" name="createdAt" class="form-control"
                                       value='<c:out value="${board.createdAt}"></c:out>' readonly>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text">수정일</span>
                                <input type="text" name="createdAt" class="form-control"
                                       value='<c:out value="${board.updatedAt}"></c:out>' readonly>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text">작성자</span>
                                <input type="text" name="memberName" class="form-control"
                                       value='<c:out value="${board.memberName}"></c:out>' readonly>
                            </div>
                            <div class="input-group mb-3">
                                <span class="input-group-text">내용</span>
                                <textarea type="text" name="content" class="form-control"><c:out value="${board.content}"></c:out></textarea>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 control-label">이미지</label>
                                <div class="col-sm-8">
                                    <input type="file" name="addImage" class="form-control" required>
                                </div>
                            </div>
                            <div class="my-4">
                                <div class="float-end">
                                    <button type="button" class="btn btn-danger">Remove</button>
                                    <button type="button" class="btn btn-primary">Modify</button>
                                    <button type="button" class="btn btn-secondary">List</button>
                                </div>
                            </div>

                        </form>
                        <%-- remove / modify action 경로 지정 --%>
                        <script>
                            const frmView = document.querySelector("form");

                            document.querySelector(".btn-danger").addEventListener("click", function (e) {
                                frmView.action = `/board/board_remove?boardId=${board.id}`;
                                frmView.method = "post";
                                frmView.submit();
                            });
                            document.querySelector(".btn-primary").addEventListener("click", function (e) {
                                frmView.action = "/board/board_modify?boardId=" + ${board.id};
                                frmView.method = "post";
                                frmView.submit();
                            });
                            document.querySelector(".btn-secondary").addEventListener("click", function (e) {
                                self.location = `/board/board_list?${requestDto.link}`;
                            }, false);

                        </script>
                        <%-- 유효성 검증 처리 --%>
                        <script>
                            const serverValidResult = {};
                            <c:forEach items="${errors}" var="error">
                            serverValidResult['${error.getField()}'] = '${error.defaultMessage}';
                            </c:forEach>
                            console.log(serverValidResult);
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/resources/layout/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>
