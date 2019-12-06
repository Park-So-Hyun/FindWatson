<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찾아줘! 왓슨</title>
		<%--부트 스트랩 --%>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

		<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	
		<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">
<meta charset="UTF-8">

<style>
#board-top {
text-align: left !important;
}
#boardContent{
min-height:500px;
}
.btns {
text-align:right;
}
</style>
</head>
<body>
	<!-- container -->
	<div class="container col-12">
	<jsp:include page="../standard/headerAdmin.jsp" />   
	     
	<div class="row mt-2">
	   	 <div class="col d-none d-sm-block"></div>       
	        <!--  -->
	<div class="container col-12 col-sm-6">
		<div class="row">
			<div class="col-12 mb-3" id="article">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="col-12 m-0"><strong>병원 상세 정보</strong></div>							
						</div>
						<hr>
						<div class=row>
							<div class=col-12>
							<h4>${dto.hosptName}</h4>
							</div>
							<div class="col-12">
							<div class="row">
							<div class="col-6">정보수정일 :  ${dto.date}</div>
							<div class="col-6 text-right">조회수 ${dto.viewCount}</div>
							</div>
							<hr>
							</div>							
						</div>
					</div>
				</div>	
				<div class=row>
					<div class="col-6">
					<img src="${dto.img}" id=hosImg>
					</div>
					<div class="col-6" id="boardContent">
					<div class="row hosIn1">
						<div class="col-12 ">
							우편번호 : ${dto.postcode}<br>
							주소<br>
							${dto.address1}<br>
							${dto.address2}<br>
							연락처 : ${dto.phone}<br>
							홈페이지 주소 : ${dto.homepage}
						</div>
					</div>
				</div>
					
				</div>
				<hr>
				<div class="row btns">
					<div class="col-12">
					<button type="button" id="hosptMdf" class="btn btn-outline-secondary">수정</button>
					<button type="button" id="hosptRemove" class="btn btn-outline-secondary">삭제</button>
					</div>
				</div>
		</div>
	</div>
</div>

		 <!--  -->
	    
	    
	    <div class="col d-none d-sm-block"></div>    
	</div>
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	</div>
	
	
<script>
	$("#hosptMdf").on("click",function(){
		location.href = '${pageContext.request.contextPath}/hosptModify.admin?seq=${dto.seq}';
	})
	$("#hosptRemove").on("click",function(){
		var result = confirm("정말 삭제하시겠습니까?");
		if(result){
			location.href = '${pageContext.request.contextPath}/hosptRemove.admin?seq=${dto.seq}';
		}
	})
	</script>
		
</body>
</html>