<%--
  헤더 레이아웃
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<div class="row">
    <div class="col">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href='<c:url value="/board/board_list"/> '>Home</a>
                </div>
                <div>
                    <ul class="navbar-nav mr-auto">
                        <c:choose>
                            <c:when test="${empty sessionMemberAccount}">
                                <li class="nav-item"><a class="nav-link" href="<c:url value="/member/login"/>">로그인 </a>
                                </li>
                                <li class="nav-item"><a class="nav-link"
                                                        href='<c:url value="/member/member_register"/>'>회원가입</a>
                                </li>
                                <li class="nav-item"><a class="nav-link"
                                                        href='<c:url value="/board/board_list"/>'>게시판</a></li>
                            </c:when>
                            <c:otherwise>
                                <li style="padding-top: 7px"><button class="btn btn-second btn-sm">[${sessionScope.sessionMemberAccount}님]</button></li>
                                <li class="nav-item"><a class="nav-link" href="<c:url value="/member/logout"/>">로그아웃 </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="<c:url value="/member/member_view?memberId=${sessionScope.sessionMemberId}" />">마이페이지</a>
                                </li>
                                <li class="nav-item"><a class="nav-link" href='<c:url value="/board/board_list"/>'>게시판</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>
