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
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">
      <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
   
   <style>
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
.line{
border:none;
}
 .header{
         
            font-weight:700;
            font-size:35px;
            color : #084480;
          
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
   
   <!-- 헤더 -->
   <jsp:include page="../standard/headerAdmin.jsp" />   
        
   <div class="row mt-2">
       <div class=container>
            <!--            -->
            <div class="row">
        <div class="col-12 mb-3" id="article">
            <div class="row">
            <div id="article-middle" class="col-12 mt-2">
                <div class="row mb-3 p-1 text-center">
                    <div id="board-top" class="col-12 m-0 text-left header">차단 IP 조회</div>
            </div>
            
            <div class="row line m-0 pt-1 pb-1" id=boardTitle>
                
                <div class="col-4  text-center">
                    아이디
                </div>
                <div class="col-4  text-center">
           IP
                </div>
                <div class="col-4  text-center">
                    차단 사유
                </div>
            </div>
             
            <!-- 게시글 목록 -->
           <c:choose>
           <c:when test="${list.size() == 0}">
           	 <div class="row line">
           	<div class="col-12" style="text-align: center">표시할 내용이 없습니다.</div>
           		</div>
           </c:when>
           <c:otherwise>
          		 <c:forEach items="${list}" var="list">
           
           <div class="row line m-0 pt-1 pb-1" id=boardCon>
                
                <div class="col-4  text-center ">
                    ${list.id}
                </div>
                <div class="col-4  text-center ">
           			${list.ip }
                </div>
                <div class="col-4  text-center ">
                    ${list.reason }
                </div>
            </div>
            </c:forEach>
           </c:otherwise>
           </c:choose>
            
                            <div class="row">
                                <div class="col-12 text-center pt-2 mt-1" id=boardPage>
                                    ${pageNavi}
                                </div>
                            </div>
                            
                    </div>
                </div>
            </div>
            <!--            -->
                </div>
   </div>
   
   <!-- 푸터-->
   <jsp:include page="../standard/footer.jsp" />
   
   </div>
<!-- container --> 
</body>
</html>