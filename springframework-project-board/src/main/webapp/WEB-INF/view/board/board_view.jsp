<%--
  게시글 뷰 페이지
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

    <title>게시글</title>
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
                        <div class="input-group mb-3">
                            <span class="input-group-text">글번호</span>
                            <input type="text" name="id" class="form-control"
                                   value='<c:out value="${board.id}"></c:out>' readonly>
                        </div>
                        <div class="input-group mb-3">
                            <span class="input-group-text">제목</span>
                            <input type="text" name="title" class="form-control"
                                   value='<c:out value="${board.title}"></c:out>' readonly>
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
                            <textarea type="text" name="content" class="form-control" readonly><c:out value="${board.content}"></c:out></textarea>
                        </div>

                        <div class="input-group mb-3">
                            <span class="input-group-text">이미지</span>
                            <img src="/upload/${board.fileName}"/>
                        </div>

                        <div class="my-4">
                            <div class="float-end">
                                <button type="button" class="btn btn-primary">Modify</button>
                                <button type="button" class="btn btn-secondary">List</button>
                            </div>
                        </div>

                        <script>
                            //수정으로 이동하는 이벤트 처리
                            document.querySelector(".btn-primary").addEventListener("click", function (e) {
                                self.location = `/board/board_modify?boardId=${board.id}&${requestDto.link}`
                            }, false);

                            //목록으로 이동하는 이벤트 처리
                            document.querySelector(".btn-secondary").addEventListener("click", function (e) {
                                self.location = "/board/board_list?${requestDto.link}";
                            }, false);
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
