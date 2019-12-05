<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<%--부트 스트랩 --%>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="../resources/css/mainStyle.css">

</head>
<style>
div{
text-align: center;
}
</style>

<body>

		<div class="container col-12">
		<jsp:include page="../standard/header.jsp" />
		<div class="row mt-2">
	    <div class="col d-none d-sm-block"></div>
		<!--            -->
		<form action="${pageContext.request.contextPath}/pwFind.member"
			method="post" id=frm>
			<div class=row>
				<div class=col-12>
					<h1>비밀번호 찾기</h1>
					<hr>
				</div>
			</div>
			<div class=row>
				<div class=col-12>
					<h5>아이디</h5>
					<input type=text id=id name="id">
					<div id=idCheck></div>
				</div>
			</div>
			<div class=row>
				<div class=col-12>
					<h5>이름</h5>
					<input type=text id=name name="name">
					<div id=nameCheck></div>
				</div>
			</div>
			<div class=row>
				<div class="col-12">
					<h5>생년월일</h5>
					<input type=text id=birth placeholder="yymmdd" name="birth">
				</div>
				<div class=col-12>
					<div id=birthCheck></div>
				</div>
			</div>
			<div class=row>
				<div class=col-12>
					<h5>이메일</h5>
					<input type=text id=email name="email">
				</div>
				<div class=col-12>
					<div id=emailCheck></div>
				</div>
			</div>
			<div class=row>
				<div class=col-12>
					<h5>휴대폰 번호</h5>
					<input type=text id=phone name="phone">
				</div>
				<div class=col-12>
					<div id=phoneCheck></div>
				</div>
			</div>
			<div class="col-12 con">
		 <button type="button" id=find class="btn btn-outline-secondary">비밀번호 찾기</button></div>
		</form>
		
		<!--            -->
		<div class="col d-none d-sm-block"></div>
	</div>			
		<jsp:include page="../standard/footer.jsp" />
	</div>
	
	<script type="text/javascript">
	//아이디확인
	$("#id").on("keyup",function() {
		var regex = /^[a-z][a-z0-9]{6,13}$/gm;
		var id = $("#id").val();
		var result = regex.exec(id);
		if (result == null) {
			$("#idCheck").html("잘못된 아이디 형식입니다.");
		} else {
			$("#idCheck").html("");
		}

	})
					
	//이름확인
		$("#name").on("keyup", function() {
			var regex = /^[가-힣]{2,8}$/gm;
			var name = $("#name").val();
			var result = regex.exec(name);
			if (result == null) {
				$("#nameCheck").html("잘못된 이름 형식입니다.");
			} else {
				$("#nameCheck").html("");
			}
		})
			//생년월일 양식 검사
		$("#birth").on("keyup",function(){
			var regex = /^[0-9]{6}$/gm;
			var birth = $("#birth").val();
			var result = regex.exec(birth);
			if (result == null){
				$("#birthCheck").html("잘못된 생년월일 형식입니다.")
			} else {
				$("#birthCheck").html("")
			}
		})		
		//이메일 확인
		$("#email").on("keyup", function() {
			var regex = /^[0-9a-zA-Z]+@[a-z]+(\.[a-z]+){1,2}$/gm;
			var email = $("#email").val();
			var result = regex.exec(email);

			if (result == null) {
				$("#emailCheck").html("잘못된 이메일 형식입니다.");
			} else {
				$("#emailCheck").html("");
			}
		})
		//연락처 양식 확인
		$("#phone").on("keyup", function() {
			var regex = /^01[0-9]{8,9}$/gm;
			var phone = $("#phone").val();
			var result = regex.exec(phone);
			if (result == null) {
				$("#phoneCheck").html("잘못된 번호 형식입니다.");
			} else {
				$("#phoneCheck").html("");
			}

		})
		
		//찾기버튼 공란검사
		$("#find").on("click",function(){
			if($("#id").val()==""){
				alert("아이디를 입력하세요!")
				return false;
			}
			if($("#id").val()!==""){
				var regex = /^[a-z][a-z0-9]{6,13}$/gm;
				var id = $("#id").val();
				var result = regex.exec(id);

				if (result !== null) {//조건만족하면 바로 DB확인
					
				}
				else{
					alert("아이디 형식에 맞지않습니다.")
					return false;
				}
			}
			
			if($("#name").val()==""){
				alert("이름을 입력하세요!")
				return false;
			}
			if($("#name").val()!==""){
				var regex = /^[가-힣]{2,8}$/gm;
				var name = $("#name").val();
				var result = regex.exec(name);
				if (result !== null) {
					
				}
				else{
					alert("이름 형식에 맞지않습니다.")
					return false;
				}
			}
			
			if($("#birth").val()==""){
				alert("생년월일을 입력하세요!")
				return false;
			}
			if($("#birth").val()!==""){
				var regex = /^[0-9]{6}$/gm;
				var year = $("#birth").val();
				var result = regex.exec(year);
				if (result !== null) {
					
				}
				else{
					alert("생년월일 형식에 맞지않습니다.")
					return false;
				}
			}
			
			if($("#email").val()==""){
				alert("이메일을 입력하세요!")
				return false;
			}
			if($("#email").val()!==""){
				var regex = /^[0-9a-zA-Z]+@[a-z]+(\.[a-z]+){1,2}$/gm;
				var email = $("#email").val();
				var result = regex.exec(email);
				if (result !== null) {
					
				}
				else{
					alert("이메일 형식에 맞지않습니다.")
					return false;
				}
			}
			
			if($("#phone").val()==""){
				alert("핸드폰번호를 입력하세요!")
				return false;
			}
			if($("#phone").val()!==""){
				var regex = /^01[0-9]{8,9}$/gm;
				var phone = $("#phone").val();
				var result = regex.exec(phone);
				if (result !== null) {
					
				}
				else{
					alert("전화번호 형식에 맞지않습니다.")
					return false;
				}
			}
			
			$("#frm").submit();
		})
	</script>

</body>
</html>