<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
        <title>찾아줘! 왓슨</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">
<style>
.line{
border:none;
}
#board-top {
text-align: left !important;
}
#boardTitle{
border-top: 2px solid black;
border-bottom: 2px solid black;
}
#boardCon{
border-top: 1px solid gray;
}
#boardPage{
border-top: 1px solid gray;
}
.write-box {
text-align:right;
width: 100%;
}
.search-box>*{width: 100%;}

</style>		
</head>
<body>
   <!-- container -->
	<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/headerAdmin.jsp" />   
	     
	<div class="row mt-2">
	        
	        <!-- 중심내용 -->
	  <div class=container>
		<div class="row">
			<div class="col-12 mb-3" id="article">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="col-12 m-0"><strong>병원 목록</strong></div>							
						</div>
						<hr>
						<div class="row line m-0 pt-1 pb-1" id=boardTitle>
							<div class="col-1 d-none d-lg-block">번호</div>
							<div class="col-4 d-none d-lg-block">병원이름</div>
							<div class="col-3 d-none d-lg-block">전화번호</div>
							<div class="col-4 d-none d-lg-block">등록시간</div>
						</div>
						<!-- 게시글 목록 -->
						<c:forEach items="${list}" var="list">
							<div class="row line m-0 pt-1 pb-1" id=boardCon>
								<div class="col-1">${list.seq}</div>
								<div class="col-4 col-lg-4"><a href="${pageContext.request.contextPath}/hosptInfoDetailView.admin?seq=${list.seq}">${list.hosptName}</a></div>								
								<div class="col-3 col-lg-3 "> ${list.phone}</div>
								<div class="col-4 col-lg-4 ">${list.date} </div>
							</div>
							<script>
								$("#remove${list.seq}").css("cursor","pointer");
								$("#remove${list.seq}").on("click",function() {
									var result = confirm("정말 삭제하시겠습니까?");
									if (result) {
										location.href = '${pageContext.request.contextPath}/expertRemove.admin?seq=${list.seq}';
									}
								})
									</script>
						</c:forEach>
						
						<div class="row">
							<div class="col-12 text-center pt-2 mt-1" id=boardPage>${pageNavi}</div>
						</div>
						<div class="row mb-3 p-1">
                                <div class="col-auto col-sm-2 p-1">
                                    <select id="category">
                                        <option value="">제목</option>
                                    </select>
                                </div>
                                <div class="search-box col-auto col-sm-6 p-1">
                                    <input type="text" class="" id="search" name="search" value="">
                                </div>
                                <div class="search-box col-auto col-sm-2 p-1">
                                    <button class="btn btn-sm btn-outline-secondary">검색</button>
                                </div>
                                <div class="write-box col-auto col-sm-2 text-center p-1">
                                    <button id="HInsert" class="btn btn-sm btn-outline-secondary">등록하기</button>
                                </div>
                            </div>
						
					</div>
				</div>
			</div>
		</div>
		</div>
		 <!-- 중심내용 -->
	        
	</div>
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	<script>
	//글쓰기 버튼
	$("#HInsert").on("click",function(){
		location.href="${pageContext.request.contextPath}/admin/adminHosptInsert.jsp";
	})
	</script>
	</div>
<!-- container -->
</body>
</html>