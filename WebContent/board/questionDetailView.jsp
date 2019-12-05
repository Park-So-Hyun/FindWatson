<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찾아줘 왓슨!</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/mainStyle.css">

<style>
#category {
	width: 100%;
	height: 100%;
}

.search-box>* {
	width: 100%;
}

.write-box>* {
	width: 100%;
}

#article, .line {
	border: 0.5px solid lightgray;
}
#boardContent{
min-height: 500px;
}
#btnCon{
text-align: right;
}
</style>
</head>
<body>
<!-- container -->
	<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/headerMember.jsp" />   
	     
	<div class="row mt-2">
	    <div class="col d-none d-sm-block"></div>
		<!--  -->
		<div class=container>
		<div class=row>
			<div class=col-12>
				<h3>커뮤니티 - 질문</h3>
				<hr>
				<h4>${dto.title}</h4>
			</div>
		</div>
		<div class=row>
			<div class=col-6>
				<h5>${dto.writer}</h5>
			</div>
			<div class="col-6">
				<h5 class="right">${dto.getDate()}&emsp;view.${dto.viewCount}</h5>
			</div>
			<div class=col-12><hr></div>
			<hr>
		</div>
		<div class=row>
			<div class=col-12 id=boardContent>${dto.content}</div>
			<div class=col-12><hr></div>
		</div>
		<c:choose>
			<c:when test="${loginInfo == dto.writer}">
				<div class=row>
					<div class=col-12 id=btnCon>
						<button id=modify type="button" class="btn btn-outline-secondary">수정</button>
						<button id=remove type="button" class="btn btn-outline-secondary">삭제</button>
					</div>
				</div>


				<script>
		$("#remove").on("click",function(){
			location.href ='${pageContext.request.contextPath}/boardRemove.bo?seq=${dto.seq}';
		})
		$("#modify").on("click",function(){
			location.href ='${pageContext.request.contextPath}/boardModify.bo?seq=${dto.seq}';
		})
		</script>

			</c:when>
		</c:choose>

		<c:choose>
			<c:when test="${list.size() ==0}">
				<div class=row>
					<div class="col-12 center">
						<h5>댓글이 없습니다.</h5>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<c:forEach items="${list}" var="cmtDto">
					<div class=row>
						<div class=col-6>${cmtDto.writer}</div>
						<div class=col-6>${cmtDto.getDate()}</div>
					</div>
					<div class=row>
						<div class=col-12>${cmtDto.content}
						<c:choose>
						<c:when test="${loginInfo == cmtDto.writer}">
						<button id="cmtRemove${cmtDto.comSeq}" type="button" class="btn btn-outline-secondary">삭제</button>
						<script>
						$("#cmtRemove${cmtDto.comSeq}").on("click",function(){
							location.href='${pageContext.request.contextPath}/questionCommentRemove.bo?brdSeq=${dto.seq}&seq=${cmtDto.comSeq}';
						})
						</script>
						</c:when>
						</c:choose>
						</div>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		<div class=row>
			<div class=col-12 id=cmtCon></div>
		</div>
		
		<div class=row>
		<div class="col-12 center">${pageNavi}</div>
		</div>


		<div class=row>
			<div class=col-10>
				<textarea id=cmt placeholder="댓글을 입력해 주세요"></textarea>
			</div>
			<div class="col-2 center">
				<button id=cmtBtn type="button" class="btn btn-outline-secondary">등록</button>
				<button id=reset type="button" class="btn btn-outline-secondary">새로고침</button>
			</div>
		</div>
		<script>
			$("#cmtBtn").on("click",function(){
				$.ajax({
					url:"${pageContext.request.contextPath}/questionCommentWrite.bo",
					type:"post",
					data:{
						comment : $("#cmt").val(),
						boardSeq: ${dto.seq}
					}
					,
					dataType:"json"
				}).done(function(data){
					var row = $("<div class=row></div>");
					var colWriter = $("<div class=col-6></div>");
					var colDate = $("<div class=\"col-6 right\"></div>");
					var colComment = $("<div class=col-12></div>");
					colWriter.append(data.writer);
					//colDate.append(data.date);
					colComment.append(data.comment);
					row.append(colWriter);
					row.append(colDate);
					row.append(colComment);
					
					$("#cmtCon").append(row);
					 $("#cmt").val("");
				}).fail(function(){
					console.log("실패");
				});
			})
			//댓글 새로고침
				$("#reset").on("click",function(){
					location.href='${pageContext.request.contextPath}/questionDetail.bo?seq=${dto.seq}';
				});
		</script>
		</div>
		<!--  -->
		    <div class="col d-none d-sm-block"></div>
	</div>
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	
	</div>
<!-- container --> 
</body>
</html>