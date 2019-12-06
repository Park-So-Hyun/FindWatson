<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Admin Controller와 Manager Controller 무슨 차이...? -->

	<c:choose>
		<c:when test="${adminInfo == null}">
			<script>
			alert("로그인이 필요합니다.");
			location.href = "${pageContext.request.contextPath}/main/startAdmin.jsp";
			</script>
		</c:when>
	</c:choose>
<div class="row mb-2">
   <div class="col d-none d-sm-block"></div>
    <div id="loginBtn" class="col-12 col-sm-6 p-1 text-right">
    <span id=pwMdf>비밀번호 변경</span>
    <span>&nbsp;</span><span id="btnBlank">|</span><span>&nbsp;</span>
    <span id=logout>로그아웃</span>
    </div>
    <div class="col d-none d-sm-block"></div>
</div>
<div class="row">
   <div class="col d-none d-sm-block navibar"></div>
    <div id="title" class="col-12 col-sm-6 p-0 mt-3 mb-3 text-center">
        <a href="${pageContext.request.contextPath}/main/indexAdmin.jsp" class="header_title">FIND! WATSON</a>
    </div>
    <div class="col d-none d-sm-block"></div>
</div>
<div class="row bg-light">
   <div class="col d-none d-sm-block"></div>
    <div id="navi" class="col-12 col-sm-6 p-0">
        <nav class="p-0 navbar navbar-expand-sm navbar-light bg-light">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" id=notice>공지사항</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            	병원관리
                        </a>
                        <div class="dropdown-menu text-center" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" id="hospitalInsert">병원정보 등록</a>
                            <a class="dropdown-item" id="hospitalModify">병원정보 수정/삭제</a>
                            <a class="dropdown-item" id="hospitalReview">병원리뷰 관리</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                           	 게시판관리
                        </a>
                        <div class="dropdown-menu text-center" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" id = expert>전문가Q&amp;A</a>
                            <a class="dropdown-item" id = free>자유게시판</a>
                            <a class="dropdown-item" id = question>질문게시판</a>
                            <a class="dropdown-item" id = oneByOne>1:1문의게시판</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            회원관리
                        </a>
                        <div class="dropdown-menu text-center" aria-labelledby="navbarDropdownMenuLink">
                            <a class="dropdown-item" id = memberList>회원목록조회</a>
                            <a class="dropdown-item" id = banList>차단 IP 조회</a>
                            <a class="dropdown-item" id = memberCharts>회원 통계</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="col d-none d-sm-block navibar"></div>
</div> 
<script>
		//각 버튼별 주소이동
		//홈 : body에 링크
		
		//공지사항
		$("#notice").attr("href", "${pageContext.request.contextPath}/boardNotice.admin");
		
		//병원 관리 - 병원정보 등록
		$("#hospitalInsert").attr("href", "${pageContext.request.contextPath}/admin/adminHosptInsert.jsp");
		//병원 관리 - 병원정보 수정/삭제
		$("#hospitalModify").attr("href", "${pageContext.request.contextPath}/hosptInfoList.admin");
		//병원 관리 - 병원리뷰 관리
		$("#hospitalReview").attr("href", "${pageContext.request.contextPath}/adminHosptReviewList.admin");
		
		//게시판 관리 - 전문가 Q&A
		$("#expert").attr("href", "${pageContext.request.contextPath}/boardExpert.admin");
		//게시판 관리 - 자유게시판
		$("#free").attr("href", "${pageContext.request.contextPath}/boardFree.admin");
		//게시판 관리 - 질문게시판
		$("#question").attr("href", "${pageContext.request.contextPath}/boardQuestion.admin");
		//게시판 관리 - 일대일 문의
		$("#oneByOne").attr("href", "${pageContext.request.contextPath}/adminOneByOne.admin");
		
		//회원 관리 - 회원 목록 조회
		$("#memberList").attr("href", "${pageContext.request.contextPath}/adminMemberList.admin");
		//회원 관리 - 차단 ip 조회
		$("#banList").attr("href", "${pageContext.request.contextPath}/adminBanList.admin");
		//회원 관리 - 회원 통계
		$("#memberCharts").attr("href", "${pageContext.request.contextPath}/adminMemberChart.admin");
 		
		
		//로그아웃 버튼 누를시
		$("#logout").on("click",function(){
			var result = confirm("정말 로그아웃 하시겠습니까?");
			if(result){
				location.href='${pageContext.request.contextPath}/logout.admin';
			}
		});
		//비밀번호 변경 누를시
		$("#pwMdf").on("click",function(){
			location.href='${pageContext.request.contextPath}/admin/adminPwModify.jsp';
		});
		
</script>
