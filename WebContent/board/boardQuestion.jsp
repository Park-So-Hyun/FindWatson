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
div{
font-family: 'Noto Sans KR', sans-serif;
}

.line{
border:none;
}
#board-top {
text-align: left !important;
}
#boardTitle{
border-top: 1px solid #084480;

background-color: #f0f4ff;

}
#boardCon{
border-top: 1px solid #084480;
}
#boardPage{
border-top: 1px solid gray;
}
.write-box {
text-align:right;
}
.delBtn{
background-color:white;
border:1px solid #f0f4ff;

}
.delBtn:hover{
color:#084480;
background-color:#f0f4ff;
}
.delBtn {
text-align: center;
}
.header{
color:#084480;
font-weight:700;
font-size:35px;          
}
.date{
padding-right:0px;
}
.count{
text-align:left;
padding-left:0px;
}
   .title:link{
              text-decoration: none;
              color: #084480; 
              }
              .title:visited{
              color: black;  text-decoration: none;
              }
              .title:hover{
              font-weight:700;
              text-decoration: none;
              color: #084480;
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
	<div class="container col-12">
		<!-- 헤더 -->
		<jsp:include page="../standard/headerMember.jsp" />
		<div class="row mt-2">
			<!--            -->
			<div class=container>
				  <div class=container>
		<div class="row">
			<div class="col-12 mb-3" id="article">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="header col-12 m-0">질문게시판</div>							
						</div>
						
						<div class="row line m-0 pt-1 pb-1" id=boardTitle>
							<div class="col-1 d-none d-lg-block">번호</div>
							<div class="col-2 d-none d-lg-block">말머리</div>							
							<div class="col-4 d-none d-lg-block">제목</div>
							<div class="col-2 d-none d-lg-block">작성자</div>
							<div class="col-2 d-none d-lg-block">작성일</div>
							<div class="col-1 d-none d-lg-block">조회수</div>
						</div>
						<!-- 게시글 목록 -->
						<c:choose>
							<c:when test="${list.size() == 0}">게시물이 없습니다.</c:when>
							<c:when test="${list.size() > 0}">
								<c:forEach items="${list}" var="dto">
							<div class="row line m-0 pt-1 pb-1" id=boardCon>
								<div class="col-1 d-none d-sm-block">${dto.seq}</div>
								<div class="col-3 col-sm-2">${dto.animalHeader}</div>
								<div class="col-8 col-sm-4"><a href='${pageContext.request.contextPath}/questionDetail.bo?seq=${dto.seq}' class=title>${dto.title}</a></div>
								<div class="col-5 col-sm-2">${dto.writer}</div>
								<div class="date col-4 col-sm-2"> ${dto.getDate()}</div>
								<div class="count col-3 col-sm-1 text-center d-none d-sm-block">${dto.viewCount} </div>				
							</div>							
						</c:forEach>
							</c:when>
						</c:choose>
						
						
						
						<div class="row">
							<div class="col-12 text-center pt-2 mt-1" id=boardPage>${pageNavi}</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		</div>

				<!-- -->
				<!-- 게시물 검색 -->
				<form action="${pageContext.request.contextPath}/searchFree.bo"
					method="post">
					<div class="row mb-2">
						<div class="col-auto col-sm-2 p-1">
							<select class="category" name="category" style="width:100%;">
								<option value="title">제목</option>
								<option value="writer">작성자</option>
								<option value="animalheader">동물 분류</option>
							</select>
						</div>

						<div class="search-box col-auto col-sm-6 p-1">
							<input type="text" class="" id="search" name="keyword" style="width:100%;">
						</div>

						<div class="search-box col-auto col-sm-2 p-1">
							<button class="btn btn-sm btn-outline-secondary" style="width:100%;">검색</button>
						</div>

						<div class="write-box col-auto col-sm-2 text-center p-1">
							<input type=button id="toWriteBtn"
								class="btn btn-sm btn-outline-secondary" style="width:100%;" value=글쓰기>
						</div>
					</div>
					<script>
						$("#toWriteBtn")
								.on(
										"click",
										function() {
											location.href = "${pageContext.request.contextPath}/board/boardWriteQuestion.jsp";
										});
					</script>
				</form>

				<!--  게시물 검색 끝 -->
				<!--            -->

			</div>
		</div>

		<!-- 푸터-->
		<jsp:include page="../standard/footer.jsp" />

	</div>

</body>
</html>