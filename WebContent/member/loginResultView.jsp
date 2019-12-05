<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찾아줘! 왓슨</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
</head>
<body>
<c:choose>
		<c:when test="${result == true }">
			<script>
				alert("로그인 성공");	
				var redirectPage = "${redirectPage}";
				
				if(redirectPage != null){	
					if(redirectPage != "http://localhost:8080/FindWatson/main/error.jsp"){
						$(location).attr("href", redirectPage);	
					}
					location.href = "${pageContext.request.contextPath}/toMain.main";
				}else{
					location.href = "${pageContext.request.contextPath}/toMain.main";
				}
				
			</script>
		</c:when>
		
		<c:otherwise>
			<script>
				alert("로그인 실패. 아이디 혹은 패스워드를 확인해주세요.");
				location.href = "${pageContext.request.contextPath}/member/login.jsp";
			</script>
		</c:otherwise>
</c:choose>
</body>
</html>