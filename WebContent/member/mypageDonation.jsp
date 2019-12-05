<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찾아줘 왓슨!</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/mainStyle.css">
		
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
border-bottom: 1px solid gray;
}

</style>
</head>

<body>
	<!-- container -->
	<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/headerMember.jsp" />   
	     
	<div class="row mt-2">
	        
	        <!-- 중심내용 -->
	  <div class=container>
		<div class="row">
			<div class="col-12 mb-3" id="article">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="col-12 m-0"><strong>내 후원보기</strong><span class="ml-3 gray">총 ${result.size() } 회</span></div>							
						</div>
						<hr>
						<div class="row line m-0 pt-1 pb-1" id=boardTitle>
							<div class="col-3 d-none d-lg-block text-left">후원번호</div>
							<div class="col-3 d-none d-lg-block text-center">후원한 이름</div>
							<div class="col-3 d-none d-lg-block text-center">결제금액</div>
							<div class="col-3 d-none d-lg-block text-center">결제방법</div>
						</div>
						<!-- 게시글 목록 -->
						<c:forEach items="${result}" var="dto">
							<div class="row line m-0 pt-1 pb-1" id=boardCon>
								<div class="col-12 col-lg-3 text-left">${dto.donateId }</div>
								<div class="col-4 col-lg-3 text-center gray">${dto.name }</div>								
								<div class="col-4 col-lg-3 text-center gray"> ${dto.support }</div>
								<div class="col-4 col-lg-3 text-center gray">${dto.payMethod } </div>								
							</div>							
						</c:forEach>												
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
	$("#writeBtn").on("click",function(){
		location.href="${pageContext.request.contextPath}/admin/adminBoardExpertWrite.jsp";
	})
	</script>
	</div>
<!-- container --> 
</body>
</html>