<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        	<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/mainStyle.css">
		
		<style>
            img{
                width:500px;
            }
            #hosImg{
                width:100%;
            }
            #head_title{
            margin-top:3%;
            margin-bottom:4%;
            font-size:35px;
            color : #084480;
            font-family: 'Noto Sans KR', sans-serif;
            font-weight:700;
            }
            #area-search{
            background-color:#f0f4ff;
            padding-top:2%;
            padding-bottom:0%;
            }
            #info_small{
               font-size:13px;
                }
          #area-line{
          margin-right:0%;
          }
             #address1{
          margin-right:2%;
          padding:0px;
          }
          
            #address2{
        
          padding:0px;
          }
            .animal-line{
            
            margin:0.1%;
            margin-top:10px;
            
           
            background-color:white;
            }
            .label{
            background-color :#084480;
            color:white;
            }
            label{
            padding:0px;
            }
            .checkbox{
            padding-top:0.5%;
            padding-bottom:0.1%;
            padding-right:0px;
            padding-left:0px;
            
            }
            
            #searchBtn2{
           color:white;
            background-color:#084480;
             border-style:none;
            }
            
             #searchBtn2:hover{
               color:black;
            background-color:#ff871f;
            border-style:none;
             }
            .line{
            border-style:none;
            }
            #btnn{
            padding:2%;
            margin-top:0px;
            }
            #result_container{
            border-bottom:1px solid lightgrey;
            padding-top:10px;
            padding-bottom:10px;
            }
            #result_count{
            border-bottom:1px solid lightgrey;
            }
            #result_title{
            font-size:25px;
            }
            #result_title:link { color: #084480; text-decoration: none; font-weight:700;}
 #result_title:visited { color: black; text-decoration: none;}
  #result_title:hover {  font-weight:900;}
#area-label{
padding-right:0px;}
        </style>
</head>
<body>
<!-- container -->
	<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/header.jsp" />   
	     
	<div class="row mt-2">
	    <div class="col d-none d-sm-block"></div>
	       
	       <div class=container>
	        <!-- 중심내용 -->
	        <div class=row>
	        <div class=col>
	        <%--검색 컨테이너 시작 --%>
			<div class=row>
                <div id="article-botton" class="col-12 line mb-3 p-0">
                <form action="${pageContext.request.contextPath}/searchFrom.s" class="col-12 p-0" method="post">
                    <div class="col-12 line" id=head_title>동물병원 검색</div>
                    <div id="area-search" class="col-12">
                        <div class="row line" id=area-line>
                            <label class="col-12 col-sm-2 col-md-1  align-self-center " id=area-label>지역별</label>
                             <a class="d-none d-sm-block">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </a><select name="address1" id="address1" class="col-12 col-sm-4 col-md-5">
                                <option value="null">-시 선택-</option>
                                <option value="서울">서울</option>
                            </select> 
                            <select name="address2" id="address2" class="col-12 col-sm-4 col-md-5">
                            <option value="null">-구 선택-</option>
                            </select>
                        </div>
                        
                        <div class="row animal-line">
                            <div class="col-12 label">진료 동물</div>
                            <div class="col-12 checkbox">
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=새>새
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=물고기>물고기
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=햄스터>햄스터
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=토끼>토끼
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=고슴도치>고슴도치
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=파충류>파충류
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=곤충류>곤충류
                            </label>
                            <label class="col-auto col-md-3 ">
                                <input type="checkbox" name=animal value=기타>기타
                            </label>
                        </div></div>
                        
                        <div class="row animal-line">
                            <div class="col-12 label">진료시간</div>
                             <div class="col-12 checkbox">
                            <label class="col-12 col-md-3">
                                <input type="checkbox" name=time value=주간진료>주간진료
                            </label>
                            <label class="col-12 col-md-3">
                                <input type="checkbox" name=time value=24시간진료>24시간진료
                            </label>
                            <label class="col-12 col-md-3">
                                <input type="checkbox" name=time value=야간응급진료>야간응급진료
                            </label>
                        </div>
                        </div>
                        <div class="row line" id=btnn>
                        	<button id="searchBtn2" class="col m-0 btn btn-sm btn-outline-secondary">검색</button>
                        </div>
                    </div>
                    </form>
                </div> 
                </div>
             <%--검색 컨테이너 끝 --%>

    <%-- 검색 결과 컨테이너 시작 --%>
            <div class ="row line">
                <div class="col-12" id=result_count>
                    <h1>${list.size()}개의 병원이 검색되었습니다.</h1>
                </div>
            </div>
            
            		<c:choose>
            		<%-- 글이 없는 경우  --%>
				<c:when test="${list.size() == 0}">
				<tr align=center>
					<td colspan=5 height=300p >해당하는 병원이 존재하지 않습니다. </td></tr>
				</c:when>
<%-- 글이 있는 경우 --%>
				<c:otherwise>
				
            <c:forEach items="${list}" var="hospital">
            <div class =row id=result_container>
                <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                    <img src = "${hospital.img}" id =hImg >
                </div>
                <div class = "col-12 col-sm-6 col-md-8 col-lg-9">
                     <a href="${pageContext.request.contextPath}/contents.s?seq=${hospital.seq}" id=result_title>${hospital.hosptName}</a>
                  <br>
                        ${hospital.address1} ${hospital.address2}<br>
                        ${hospital.phone}<br>
                        ${hospital.medicalAnimal}<br>
                        ${hospital.openTime}<br>
                 
                </div>
            </div>
            </c:forEach>
            	</c:otherwise>
		</c:choose>
		
		</div>
		</div>
		</div>
      <%-- 검색 결과 컨테이너 끝 --%>
	        
	         <!-- 중심내용 -->
	    
	        
	    <div class="col d-none d-sm-block"></div>
	</div>
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	
<!-- container --> 
</div>
<script>
        $("#address1").on("click", function(){
    		$.ajax({
    			url : "selectGu.s",
    			type : "post",
    			dataType : "json",
    			data : {city : $("#address1 option:selected").val()}
    		}).done(function(result){
    			$("#address2").find("option").remove().end().append("<option value=''>전체</option>");
    			  
    			  //배열 개수 만큼 option 추가
    			   $.each(result, function(i){
    			    $("#address2").append("<option value='"+result[i]+"'>"+result[i]+"</option>")
    			   });  
    		}).fail(function(){
    			alert("오류 발생");
    		});
    	})
        </script>
</body>
</html>