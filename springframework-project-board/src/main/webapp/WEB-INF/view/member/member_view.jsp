<%--
  회원 정보 뷰 페이지
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<html>
<head>
    <title>회원 정보</title>
</head>
<body>
<jsp:include page="/resources/layout/header.jsp"/>

<div class="jumbotron">
    <div class="container">
        <h1 class="display-5">회원 정보</h1>
    </div>
</div>
<div class="container">
    <input id="memberId" name="id" type="hidden" class="form-control" value="<c:out value='${member.id}' />" hidden>
    <div class="form-group row">
        <label class="col-sm-2 ">아이디</label>
        <div class="col-sm-3">
            <input name="account" type="text" class="form-control"
                   value="<c:out value='${member.account}' />" readonly>
        </div>
    </div>
    <input name="password" type="text" class="form-control" value="<c:out value='${member.password}' />" hidden>
    <div class="form-group row">
        <label class="col-sm-2">성명</label>
        <div class="col-sm-3">
            <input name="name" type="text" class="form-control"
                   value="<c:out value='${member.name}' />" readonly>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2">성별</label>
        <div class="col-sm-10">
            <input name="gender" type="radio" value="남"
            <c:if test="${member.gender.equals('남')}">
                <c:out value="checked"/>
            </c:if>> 남
            <input name="gender" type="radio" value="여"
            <c:if test="${member.gender.equals('여')}">
                <c:out value="checked"/>
            </c:if>> 여
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2">생일</label>
        <div class="col-sm-4  ">
            <input type="text" name="birth" value="<c:out value='${member.birth}' />" readonly>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2">이메일</label>
        <div class="col-sm-4  ">
            <input type="text" name="email" value="<c:out value='${member.email}' />" readonly>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2">전화번호</label>
        <div class="col-sm-3">
            <input name="phone" type="text" class="form-control"
                   value="<c:out value='${member.phone}'/>" readonly>
        </div>
    </div>
    <div class="form-group  row">
        <label class="col-sm-2 ">우편번호</label>
        <div class="col-sm-2">
            <input name="zipcode" type="text" class="form-control" value="<c:out value='${member.zipcode}'/>"
                   readonly>
        </div>
    </div>
    <div class="form-group  row">
        <label class="col-sm-2 ">주소1</label>
        <div class="col-sm-5">
            <input name="addr1" type="text" class="form-control" value="<c:out value='${member.addr1}'/>" readonly>
        </div>
    </div>
    <div class="form-group  row">
        <label class="col-sm-2 ">주소2</label>
        <div class="col-sm-5">
            <input name="addr2" type="text" class="form-control" value="<c:out value='${member.addr2}'/>" readonly>
        </div>
    </div>
    <input name="createdAt" type="text" class="form-control"
           value="<c:out value='${member.createdAt}' />" hidden>
    <input name="updatedAt" type="text" class="form-control"
           value="<c:out value='${member.updatedAt}' />" hidden>
    <div class="form-group row">
        <div class="col-sm-offset-2 col-sm-10 ">
            <a href="/member/member_modify" class="btn btn-primary">회원수정</a>
            <a href="/board/board_list" class="btn btn-second">돌아가기</a>
        </div>
    </div>
</div>
<script>
    //회원 수정 이동 이벤트 처리
    document.querySelector(".btn-primary").addEventListener("click", function (e) {
        e.preventDefault();
        const memberId = document.getElementById("memberId").value;
        console.log(memberId);
        window.location.href = "/member/member_modify?memberId=" + memberId;
    }, false);
</script>
<jsp:include page="/resources/layout/footer.jsp"/>
</body>
</html>
