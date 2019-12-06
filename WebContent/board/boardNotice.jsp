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
        div{
font-family: 'Noto Sans KR', sans-serif;
}
.line{
border: none;
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
              }
        </style>
</head>
<body>

	<div class="container col-12">
	<jsp:include page="../standard/header.jsp" />   	     
	<div class="row mt-2">
	    
	        
	        <!-- -->
	      
	        <div class=container>
		<div class="row">
			<div class="col-12 mb-3" id="article">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="header col-12 m-0">공지사항</div>							
						</div>
						<hr>
						<div class="row line m-0 pt-1 pb-1" id=boardTitle>
							<div class="col-1 d-none d-lg-block">번호</div>
							<div class="col-8 d-none d-lg-block">제목</div>
							<div class="col-2 d-none d-lg-block">작성일</div>
							<div class="col-1 d-none d-lg-block">조회수</div>
						</div>
						<!-- 게시글 목록 -->
						<c:choose>
							<c:when test="${list.size() == 0}">게시물이 없습니다.</c:when>
							<c:when test="${list.size() > 0}">
							
							<c:forEach items="${list}" var="list">
							<div class="row line m-0 pt-1 pb-1" id=boardCon>
								<div class="col-1">${list.seq}</div>
								<div class="col-10 col-md-6 col-lg-8"><a href='${pageContext.request.contextPath}/noticeDetail.bo?seq=${list.seq}' class=title>${list.title}</a></div>
								
								<div class="date col-6 col-md-2 col-lg-2"> ${list.formedDate}</div>
								<div class="count col-6 col-md-1 col-lg-1 text-center d-none d-md-block">${list.viewCount} </div>					
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
	        
	        
	        <!--  -->
	        
	
	</div>
	<jsp:include page="../standard/footer.jsp" />	
	</div>

</body>
</html>