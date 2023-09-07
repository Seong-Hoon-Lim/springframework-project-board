<%--
  게시글 생성 뷰 페이지
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>게시글 작성</title>
</head>
<body>
<jsp:include page="/resources/layout/header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        게시글 작성
                    </div>
                    <div class="card-body">
                        <form name="form_register" action="/board/board_register" class="form-horizontal" method="post"
                              onsubmit="return checkForm()" enctype="multipart/form-data">
                            <div class="form-group row">
                                <input hidden="hidden" name="memberId" value="1">
                                <label class="col-sm-2 control-label">작성자</label>
                                <div class="col-sm-3">
                                    <input name="memberName" type="text" class="form-control" placeholder="${sessionScope.sessionMemberAccount}" required readonly>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 control-label">제목</label>
                                <div class="col-sm-5">
                                    <input name="title" type="text" class="form-control" placeholder="제목 입력" required>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 control-label">내용</label>
                                <div class="col-sm-8">
                                    <textarea name="content" cols="50" rows="5" class="form-control" placeholder="내용 입력"
                                              required></textarea>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-sm-2 control-label">이미지</label>
                                <div class="col-sm-8">
                                    <input type="file" name="addImage" class="form-control" required>
                                </div>
                            </div>
                            <br>
                            <div class="form-group row">
                                <div class="col-sm-offset-2 col-sm-10 d-flex justify-content-end">
                                    <input type="submit" class="btn btn-primary" value="등록">
                                    <a href="/board/board_list" class="btn btn-primary">취소</a>
                                </div>
                            </div>
                        </form>

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
