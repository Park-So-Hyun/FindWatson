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
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">
		
        <style>
            *{box-sizing: border-box}
/*            div{border: 1px solid black}*/
            #logo{
                width: 500px;
                height: 100px;
                margin: auto;
            }
            #footer>div{
                height: 100%;
            }
            #footer-logo>div{
                height: 100%; 
            }
            #footer-contents{font-size: 8pt;}
            .nav-item{
                display: inline-block;
                width: 100%;
                text-align: center;
            }
            .navbar-nav{
                width: 100%;
            }
            #article, .line{
                border: 0.5px solid lightgray;
            }
            #category{width: 100%; height: 100%;}
            .search-box>*{width: 100%;}
            .write-box>*{width: 100%;}
            #article, .line{
                border: 0.5px solid lightgray;
            }
        </style>
</head>
<body>
    <div class="container">
           <jsp:include page="../standard/headerAdmin.jsp" />
            <!--            -->
      	<div class="row">
        <div class="col-12 mb-3" id="article">
            <div class="row">
            <div id="article-middle" class="col-12 mt-2">
                <div class="row mb-3 p-1 text-center">
                    <h3 id="board-top" class="col-auto col-sm-4 m-0">병원 리스트</h3>
            </div>
            <div class="row line">
                
                <div class="col-md-3 d-none d-md-block">
                    병원번호
                </div>
                <div class="col-md-3 d-none d-md-block">
           	병원이름
                </div>
                <div class="col-md-6 d-none d-md-block">
                    홈페이지
                </div>
            </div>
            <!-- 게시글 목록 -->
           <c:forEach items="${list}" var="list">
           <div class="row line">
                
                <div class="col-md-3 d-none d-md-block">
                    ${list.seq}
                </div>
                <div class="col-md-3 d-none d-md-block">
           			<a id="toPost" href="${pageContext.request.contextPath}/hosptInfoDetailView.admin?seq=${list.seq}">${list.hosptName}</a>
                </div>
                <div class="col-md-6 d-none d-md-block">
                    ${list.homepage }
                </div>
            </div>
            </c:forEach>
                            <div class="row">
                                <div class="col-12 text-center">
                                    ${pageNavi}
                                </div>
                            </div>
                            <div class="row mb-2">
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
            <!--            -->
            <jsp:include page="../standard/footer.jsp" />
            </div>
            <script>
            	$("#HInsert").on("click",function(){
            		location.href="${pageContext.request.contextPath}/admin/adminHosptInsert.jsp";
            	});
            </script>
</body>
</html>