<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
</head>
<body>
	<script>
	alert( "차단된 ip주소입니다. (차단 사유 : ${reason})" );
	window.open("about:blank","_self").close();
	</script>
</body>
</html>