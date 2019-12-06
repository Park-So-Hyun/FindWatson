<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">
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
.header{
color:#084480;
font-weight:700;
font-size:35px;          
}
hr{
background-color:#084480;
}
    .btn{
           color:white;
            background-color:#084480;
             border-style:none;
            }
            
            .btn:hover{
               color:black;
            background-color:#ff871f;
            border-style:none;
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
							<div id="board-top" class="col-12 m-0 header">전문가Q&amp;A</div>							
						</div>
						<hr>
						<div class=row>
							<div class=col-12>
							<h4>${dto.title}</h4>
							</div>
							<div class="col-12">
							<div class="row">
							<div class="col-6">${dto.getDate()}</div>
							<div class="col-6 text-right">view.${dto.viewCount}</div>
							</div>
							<hr>
							</div>							
						</div>
					</div>
				</div>	
				<div class=row>
					<div class=col-12 id=boardContent>${dto.content}</div>
					
				</div>
				<hr>
				<div class="row btns">
					<div class="col-12">
					<button type="button" id="toNotice" class="btn btn-outline-secondary">돌아가기</button>
					<button type="button" id="modify" class="btn btn-outline-secondary">수정</button>
					<button type="button" id="delete" class="btn btn-outline-secondary">삭제</button>
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
	$("#modify").on("click", function(){
		location.href = '${pageContext.request.contextPath}/expertModify.admin?seq=${dto.seq}';
	})
	$("#delete").on("click", function(){
		var result = confirm("정말 삭제하시겠습니까?");
		if(result){
			location.href = '${pageContext.request.contextPath}/expertRemove.admin?seq=${dto.seq}';
		}	
	})
	
	$("#toNotice").on("click", function(){
		history.back();
	})
	</script>
</body>
</html>