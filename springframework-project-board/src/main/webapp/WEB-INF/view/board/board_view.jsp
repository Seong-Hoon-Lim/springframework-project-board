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
                        <form name="formView">
                            <input type="text" name="id" class="form-control" value='<c:out value="${board.id}"></c:out>' hidden>
                        </form>
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

                        <%-- 댓글 목록 --%>
                        <script>
                            const xhr = new XMLHttpRequest();
                            const getRipples = function () {
                                /* 리플 목록을 가져옴 */
                                let boardId = document.querySelector('form[name=formView] input[name=id]');
                                xhr.open('GET', '/ripple/ripple_list?boardId=' + boardId.value);
                                xhr.setRequestHeader('Accept', 'application/json');
                                xhr.send();
                                xhr.onreadystatechange = () => {
                                    if (xhr.readyState !== XMLHttpRequest.DONE) return;
                                    if (xhr.status === 200) {   //서버(url)에 문서가 존재함
                                        const cleanedResponse = xhr.response.trim();
                                        const json = JSON.parse(cleanedResponse);
                                        console.log("Received response:", xhr.response);
                                        for (let data of json) {
                                            console.log(data);
                                        }
                                        insertRippleTag(json);
                                    } else {  //서버(url)에 문서가 존재하지 않음.
                                        console.error('Error', xhr.status, xhr.statusText);
                                    }
                                }
                            }
                            document.addEventListener("DOMContentLoaded", function () {
                                getRipples();
                            });
                        </script>

                        <%-- 댓글 목록 출력 영역 --%>
                        <div class="form-group row user-repple-list">
                            <ul>

                            </ul>

                        </div>
                        <script>
                            const insertRippleTag = function (items) {
                                let tagUl = document.querySelector('.user-repple-list ul');
                                tagUl.innerHTML = '';
                                for (let item of items) {
                                    console.log("Current item:", item);
                                    let tagNew = document.createElement('li');
                                    tagNew.innerHTML = item.comment + ' | ' + item.memberName + ' | ' + item.createdAt;

                                    // isLogin이 true일 경우에만 삭제 버튼 추가
                                    if (item.login  === true) {
                                        tagNew.innerHTML +=
                                            ' <span class="btn btn-danger" onclick="goRippleDelete(' + item.id + ');">삭제</span>';
                                    }

                                    tagNew.setAttribute('class', 'list-group-item');
                                    tagUl.append(tagNew);
                                }
                            }

                            /* 리플 삭제 하기 */
                            const goRippleDelete = function (rippleId) {
                                if (confirm("삭제하시겠습니까?")) {
                                    const xhr = new XMLHttpRequest();  //XMLHttpRequest 객체 생성
                                    //xhr.open('POST', '../board/ajax_delete.jsp?rippleId=' + ID);
                                    xhr.open('POST', '/ripple/ripple_remove?rippleId=' + rippleId);
                                    xhr.send();
                                    xhr.onreadystatechange = () => {
                                        if (xhr.readyState !== XMLHttpRequest.DONE) return;

                                        if (xhr.status === 200) {   //서버(url)에 문서가 존재함
                                            console.log(xhr.response);
                                            const json = JSON.parse(xhr.response);
                                            console.log("Received response:", xhr.response);
                                            if (json.result === 'true') {
                                                getRipples();
                                            } else {
                                                alert("삭제에 실패했습니다.")
                                            }
                                        } else {  //서버(url)에 문서가 존재하지 않음
                                            console.log('Error', xhr.status, xhr.statusText);
                                        }
                                    }
                                }
                            }
                        </script>
                        <%--// 리플 목록 출력 영역 --%>

                        <%-- 댓글 등록 form --%>
                        <c:if test="${sessionMemberAccount != null}">
                            <form name="frmRipple" class="form-horizontal" method="post">
                                <input type="hidden" name="boardId" value="${board.id}">
                                <div class="form-group row">
                                    <label class="col-sm-2 control-label">성명</label>
                                    <div class="col-sm-3">
                                        <input name="memberName" type="text" class="form-control" value="${sessionMemberAccount}" readonly>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 control-label">내용</label>
                                    <div class="col-sm-8" style="word-break: break-all;">
                                        <textarea name="comment" class="form-control" cols="50" rows="3"></textarea>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label class="col-sm-2 control-label"></label>
                                    <div class="col-sm-3">
                                        <span class="btn btn-dark" name="goRippleRegister">등록</span>
                                    </div>
                                </div>
                            </form>

                            <%-- 댓글 등록 --%>
                            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    const xhr = new XMLHttpRequest();   //XMLHttpRequest 객체 생성
                                    document.querySelector('span[name=goRippleRegister]').addEventListener('click', function (e) {
                                        // e.preventDefault();
                                        //값 들고옴
                                        let boardId = document.querySelector('input[name=boardId]');
                                        let memberName = document.querySelector('input[name=memberName]');
                                        let comment = document.querySelector('textarea[name=comment]');

                                        xhr.open('POST', '/ripple/ripple_register');
                                        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
                                        xhr.send('boardId='+boardId.value+'&memberName='+memberName.value+'&comment='+comment.value);
                                        xhr.onreadystatechange = () => {
                                            if (xhr.readyState !== XMLHttpRequest.DONE) return;
                                            if (xhr.status === 200) {   //서버(url)에 문서가 존재함
                                                const cleanedResponse = xhr.response.trim();
                                                const json = JSON.parse(cleanedResponse);
                                                if (json.result === 'true') {
                                                    alert("등록에 성공했습니다.");
                                                    comment.value = ''; //input 태그에 입력된 값 삭제.
                                                    getRipples();
                                                } else {
                                                    alert("등록에 실패했습니다.");
                                                }
                                            } else {  //서버(url)에 문서가 존재하지 않음
                                                console.log('Error', xhr.status, xhr.statusText);
                                            }
                                        }
                                    });
                                });
                            </script>
                        </c:if>


                        <div class="my-4">
                            <c:if test="${sessionScope.sessionMemberAccount == board.memberName}">
                                <div class="float-end">
                                    <button type="button" class="btn btn-primary">수정</button>
                                </div>
                            </c:if>
                            <div class="float-end">
                                <button type="button" class="btn btn-secondary">목록</button>
                            </div>
                        </div>

                        <script>
                            document.addEventListener("DOMContentLoaded", function(){
                                //수정으로 이동하는 이벤트 처리
                                let btnPrimary = document.querySelector(".btn-primary");
                                if(btnPrimary !== null) {
                                    btnPrimary.addEventListener("click", function (e) {
                                        self.location = `/board/board_modify?boardId=${board.id}&${requestDto.link}`
                                    }, false);
                                }

                                //목록으로 이동하는 이벤트 처리
                                let btnSecondary = document.querySelector(".btn-secondary");
                                if(btnSecondary !== null) {
                                    btnSecondary.addEventListener("click", function (e) {
                                        self.location = "/board/board_list?${requestDto.link}";
                                    }, false);
                                }
                            });

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
