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
<link rel="stylesheet" href="../resources/css/mainStyle.css">

<style>
#board-top {
text-align: left !important;
}
div input{
width:220px;
height:40px;
border:none;
border-bottom: 1px solid gray;
}
input::placeholder{
color: #a6a6a680;
}
#article{
min-height: 720px;
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
	<jsp:include page="../standard/header.jsp" />   	     
	<div class="row mt-2">	        
	        <!--  -->
	        
	  <div class=container>
		<div class="row">
			<div class="col-12 mb-3" id="article">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="col-12 m-0"><strong>로그인</strong></div>							
						</div>
						<hr>
						<div class="row">
							<form action="${pageContext.request.contextPath}/login.member" method=post class="col-12 p-0">
							<div class="col-12 m-1 text-center"><input type=text id=id name=id placeholder="ID"></div>
							<div class="col-12 m-1 text-center"><input type=password id=pw name=pw placeholder="PW"></div>
							<div class="col-12 mt-4 p-0 text-center">
								<button type=button id= tologin class="btn btn-outline-secondary">로그인</button>
								<button type=button id="signUp" class="btn btn-outline-secondary">회원가입</button>
							</div>
							<input type=hidden name=returnPage id=returnPage value="">
							</form>
						</div>	
						<div class=row>
							<div class="col-12 mt-4 text-center"><a href="idFind.jsp" class=title>아이디 찾기</a> / <a href="pwFind.jsp" class=title>비밀번호 찾기</a></div>
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
	
<script>
            $("#signUp").on("click",function(){
                location.href="signup.jsp";
            });
	        $("#tologin").on("click",function(){
	        	var referrer =  document.referrer;
	        	$("#returnPage").val(referrer);
	        	
	        	if($("#id").val() == ""){
	        		alert("아이디를 입력해주세요");
	        		return false;
	        	}else if($("#pw").val() == ""){
	        		alert("비밀번호를 입력해주세요");
	        		return false;
	        	}
	        	$("form").submit();
	        });
        
        </script>
</body>
</html>