<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
			<c:when test="${loginInfo ==null}">
				<script>
				alert("로그인이 필요한 페이지입니다.");
				location.href = "${pageContext.request.contextPath}/member/login.jsp"
				</script>
			</c:when>
</c:choose>

<div class="row">
<div class="col d-none d-sm-block"></div>
    <div id="loginBtn" class="col-12 col-sm-6 p-1 mb-1 text-right">       
				<span id="donate">후원하기</span>
				<span>&nbsp;</span><span id="btnBlank">|</span><span>&nbsp;</span>
				<span id="logout">로그아웃</span>			
    </div>
<div class="col d-none d-sm-block"></div>
</div>
<hr class="m-0">

<div class="row">
   <div class="col d-none d-sm-block"></div>
    <div id="title" class="col-12 col-sm-6 p-0 mt-3 mb-3 text-center">
        <a href="${pageContext.request.contextPath}/toMain.main" class="header_title">FIND! WATSON</a>
    </div>
    <div class="col d-none d-sm-block"></div>
</div>
<div class="row bg-light">
   <div class="col d-none d-sm-block navibar"></div>
    <div id="navi" class="col-12 col-sm-6 p-0">
        <nav class="p-0 navbar navbar-expand-sm navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/boardNotice.bo">공지사항</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/simpleMove.s">병원검색</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/boardExpert.bo">전문가Q&amp;A</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            커뮤니티
                        </a>
                        <div class="dropdown-menu text-center" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" id = free href="${pageContext.request.contextPath}/boardFree.bo">자유게시판</a>
                            <a class="dropdown-item" id=question href="${pageContext.request.contextPath}/boardQuestion.bo">질문게시판</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            마이페이지
                        </a>
                        <div class="dropdown-menu text-center" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/mypageInfo.member">내 정보</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/mypageDonation.do">내 후원보기</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/mypageOneByOneList.member">1 : 1 문의</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/member/mypageWithdrawal.jsp">회원 탈퇴</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="col d-none d-sm-block navibar"></div>
</div>

<script>
$("#login").on("click",function(){
    location.href="${pageContext.request.contextPath}/member/login.jsp";
})
$("#join").on("click",function(){
    location.href="${pageContext.request.contextPath}/member/signup.jsp";
})
$("#logout").on("click", function(){
	location.href="${pageContext.request.contextPath}/logout.member"
})
$("#donate").on("click",function(){
    location.href="${pageContext.request.contextPath}/donate/donateForm.jsp"
})
</script>