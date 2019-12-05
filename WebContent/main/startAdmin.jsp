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

<style>
#logoImg{
width:200px;
position:relative;
left:14%;
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
</style>
</head>
<body>
				<div class="container">	
				<div class="row pt-5">		
					<!--            -->
					<div class="col d-none d-sm-block"></div>
					
					<div class="wrap">	
					<div class="col-12"><img src="../imgs/findWatson.png" id="logoImg"></div>
					
					<div class="col-12 mt-4">
					<form action="${pageContext.request.contextPath}/login.admin" id=frm method="post">
					
						<div class="col-12 mb-3 center" id="article">
							<div class="col-12 m-1"><input type=text placeholder="아이디" name=id required></div>
							<div class="col-12 m-1"><input type=text placeholder="비밀번호" name=pw required></div>
							<div class="col-12 mt-4 p-0 text-center"><button class="btn btn-outline-secondary">로그인</button></div>
						</div>
					
					</form>
					</div>					
					</div>	
					
					<div class="col d-none d-sm-block"></div>
					<!--            -->
				</div>
					</div>					
</body>
</html>