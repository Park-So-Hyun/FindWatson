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
	<c:when test="${result > 0 }">
	<script>
	alert("비밀번호가 변경되었습니다. 로그인 페이지로 이동합니다")
	location.href = 'member/login.jsp';
	</script>	
	</c:when>
	<c:otherwise>
	<script>
	alert("비밀번호 변경에 실패했습니다. ")
	location.href = '${pageContext.request.contextPath}/toMain.main';
	</script>
	</c:otherwise>
</c:choose>

	
</body>
</html> 