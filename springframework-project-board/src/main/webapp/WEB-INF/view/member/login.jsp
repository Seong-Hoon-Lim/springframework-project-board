<%--
  로그인 뷰 페이지
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>

    <!-- 사용자 css 추가 -->
    <style>
        .form-custom-width {
            width: 300px;
        }
    </style>
</head>
<body>
<jsp:include page="/resources/layout/header.jsp"/>
<div class="bg-light d-flex align-items-center justify-content-center" style="min-height: 85vh;">
    <div class="form-signin form-custom-width">
        <form action="/member/login" method="post">
            <div class="text-center">
                <h1 class="h3 mb-3 fw-normal">Login</h1>
            </div>
            <div class="col-md-12 col-lg-12">
                <div class="row-sm-12">
                    <label class="form-label">아이디</label>
                    <input type="text" class="form-control" name="account" id="account" placeholder="계정을 입력해주세요"
                           value="" required>
                </div>
                <div class="row-sm-12">
                    <label class="form-label">비밀번호</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="비밀번호를 입력해주세요"
                           value="" required>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>
            </div>
        </form>
        <a class="w-100 btn btn-lg btn-secondary" href="/member/member_register">회원가입</a>
    </div>
</div>
<jsp:include page="/resources/layout/footer.jsp"/>
</body>
</html>
