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
        <link rel="stylesheet" href="resources/css/mainStyle.css">
        <style>
			#article-top{
			position: relative;
			}
			#article-img{
			height : 300px;
			}
			#searchTop{
			width: 90%;
			position: absolute;
			left: 50%;
			transform: translate(-50%, -50%);
			background-color:#084480;
			border-radius: 3px;
			}
			#searchBtn1{
			background-color:#ffffff; 
			color:#084480;
			width: 5%;
			}			           
            .imgSize{
                width: 100%;
                height: 300px;
            }
            #searchBtn2{width: 100%;}
            label{margin-bottom: 0px;}
             
        </style>
    </head>
    <body>
<div class="container col-12">
<jsp:include page="../standard/header.jsp" />           
<!--    내용시작    -->
<div class="row mt-2">
<div class="col d-none d-sm-block"></div>
	<div class="container col-12 col-sm-6">       
            <!--    내용시작        -->
            <div class="row line mb-2">
                <div id="article-top" class="col-12 mb-1 p-0">
                    <!--여기 이미지  -->
                    <div id="article-logo" class="col-12 p-2">
                            <div id="article-img">이미지</div>
                    </div>
                    <!--  -->
                    <div class="col-12 p-1" id="searchTop">
                    <div class="col-12 p-0">
                        <form class="form-inline" action="${pageContext.request.contextPath}/keywordSearch.s" class="col-12" method=post>
                            <input class="form-control col-8 col-sm-10" type="search" placeholder="Search" aria-label="Search" name=keywordSearch>
                            <button id="searchBtn1" class="btn btn-outline-secondary m-auto col-4 col-sm-2" type="submit">Search</button>
                        </form>
				    </div>
                    </div>
                </div>
            </div>
                <div class="row">
                    <div id="article-middle" class="col-12">
                    <div class="row mt-3 mb-1">
                        <!-- 공지사항 -->
                        <div id="article-middle-left" class="col-12 col-sm-6 mb-1 text-center">
                            <div class="row line mb-1">
                                <div class="col-12 prevealTitle">공지사항</div>
                            </div>
                            <div class="row line">
                                <div class="col-12 prevealCon">
                                <c:choose>
                                	<c:when test="${noticeList.size() > 0 }">
                                	<c:forEach items="${noticeList}" var="dto">
                                	<a href="${pageContext.request.contextPath}/noticeDetail.bo?seq=${dto.seq}">${dto.title }</a><br>
                                	</c:forEach>
                                	</c:when>                               	
                                </c:choose> 
                                <c:choose>
                                	<c:when test="${noticeList.size() < 5 }">
                                		<c:forEach var="i" begin="1" end="${5- noticeList.size() }" step="1">
                                		<p class="m-0"><a href="#">내용없음</a></p>
                                		</c:forEach>
                                	</c:when>
                                </c:choose>                                   
                                </div>
                            </div>
                        </div>
                         <!-- 자유게시판 -->
                        <div id="article-middle-right" class="col-12 col-sm-6 text-center">
                            <div class="row line mb-1">
                                <div class="col-12 prevealTitle">자유게시판</div>
                            </div>
                            <div class="row line">
                                <div class="col-12 prevealCon">
                                <c:choose>
                                	<c:when test="${freeList.size() > 0 }">
                                	<c:forEach items="${freeList}" var="dto">
                                	<a href="${pageContext.request.contextPath}/freeDetail.bo?seq=${dto.seq}">[${dto.animalHeader}]&nbsp;${dto.title }</a><br>
                                	</c:forEach> 
                                	</c:when>                               	
                                </c:choose>
                                <c:choose>
                                	<c:when test="${freeList.size() < 5 }">
                                		<c:forEach var="i" begin="1" end="${5- freeList.size() }" step="1">
                                		<p class="m-0"><a href="#">내용없음</a></p>
                                		</c:forEach>
                                	</c:when>
                                </c:choose>                              
                                </div>
                            </div>
                        </div>
                    </div>
                        <!-- 여기 캐러셀 -->
                <div class="row">
                    <div id="carouselExampleIndicators"
                         class="carousel slide col-12 mt-1 mb-2 p-0 align-self-center" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="5"></li>
                        </ol>
                        <div class="carousel-inner" style="width: 100%; margin: auto">
                            <div class="carousel-item active">
                                <img src="imgs/animal/%EA%B3%A0%EC%8A%B4%EB%8F%84%EC%B9%98.jpg" class="rounded mx-auto d-block" style="border-radius: 10px;">
                            </div>
                            <div class="carousel-item">
                                <img src="imgs/animal/%EC%88%98%EB%8B%AC.jpg" class="rounded mx-auto d-block" style="border-radius: 10px;">
                            </div>
                            <div class="carousel-item">
                                <img src="imgs/animal/%ED%86%A0%EB%81%BC.jpg" class="rounded mx-auto d-block" style="border-radius: 10px;"> 
                            </div>
                            <div class="carousel-item">
                                <img src="imgs/animal/%EB%B1%81%EC%83%88.png" class="rounded mx-auto d-block" style="border-radius: 10px;"> 
                            </div>
                            <div class="carousel-item">
                                <img src="imgs/animal/%EC%95%B5%EB%AC%B4%EC%83%88.jpg" class="rounded mx-auto d-block" style="border-radius: 10px;"> 
                            </div>
                            <div class="carousel-item">
                                <img src="imgs/animal/%EC%82%AC%EB%A7%89%EC%97%AC%EC%9A%B0.jpg" class="rounded mx-auto d-block" style="border-radius: 10px;"> 
                            </div>
                        </div>
                    </div>
                </div>
                        <!--  -->
                    </div>
                </div>
        <div class="row">
            <div id="article-botton" class="col-12 p-0">
                <form action="${pageContext.request.contextPath}/searchFrom.s" class="col-12 p-0" id="detailSearch" method="post">
                    <div class="col-12 line mb-1">동물병원 상세 검색</div>
                    <div id="area-search" class="col-12">
                        <div class="row line mb-1">
                            <label class="col-12 col-sm-4 align-self-center">지역별</label>
                            <select name="address1" id="address1" class="col-12 col-sm-4">
                                <option value="null">-시 선택-</option>
                                <option value="서울">서울</option>
                            </select> <select name="address2" id="address2" class="col-12 col-sm-4">
                            <option value="null">-구 선택-</option>
                            </select>
                        </div>
                        <div class="row line mb-1">
                            <div class="col-12">진료동물</div>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=새>새
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=물고기>물고기
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=햄스터>햄스터
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=토끼>토끼
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=고슴도치>고슴도치
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=파충류>파충류
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=곤충류>곤충류
                            </label>
                            <label class="col-4 col-md-3">
                                <input type="checkbox" name=animal value=기타>기타
                            </label>
                        </div>
                        <div class="row line mb-1">
                        <div class="col-12">진료시간</div>
                            <label class="col-12 col-md-4">
                                <input type="checkbox" name=time value=주간진료>주간진료
                            </label>
                            <label class="col-12 col-md-4">
                                <input type="checkbox" name=time value=24시간진료>24시간진료
                            </label>
                            <label class="col-12 col-md-4">
                                <input type="checkbox" name=time value=야간응급진료>야간응급진료
                            </label>
                        </div>
                        <div class="row">
                        	<button id="searchBtn2" class="col m-0 btn btn-sm btn-outline-secondary">검색</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
            <!--            -->
	</div>
<div class="col d-none d-sm-block"></div>
</div>
<!--            -->
<jsp:include page="../standard/footer.jsp" />
</div>
        <script type="text/javascript">
           $("#address1").on("click", function() {
				$.ajax({
					url : "${pageContext.request.contextPath}/selectGu.s",
					type : "post",
					dataType : "json",
					data : {
							city : $("#address1 option:selected").val()
							}
					}).done(function(result) {
						$("#address2").find("option")
									  .remove()
									  .end()
									  .append("<option value=''>전체</option>");

	             //배열 개수 만큼 option 추가
						$.each(result,function(i) {
							$("#address2").append("<option value='"+result[i]+"'>"
												+ result[i]
												+"</option>")
						});
					}).fail(function(){
						alert("오류 발생");
					});
			})
        </script>
    </body>
</html>