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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainStyle.css">
</head>
<body>
	<div class="container col-12">
		<jsp:include page="../standard/header.jsp" />

		<div class="row mt-2">
			<div class="col d-none d-sm-block"></div>
			
			<div class="contents col-12 col-sm-6 line mt-5 mb-5">
				<div class="col-12 mt-2 mb-2">
					<div class="row mb-2">
				        <img src="${pageContext.request.contextPath}/imgs/animal/thanks.gif" class="rounded mx-auto d-block">
				    </div>
					<div class="row">
						<div class="col-12 text-center">
							<h3>후원 감사합니다</h3>
							<p>${paid }원결제하셨습니다.</p>
						</div>
					</div>
					<div class="row">
						<div class="col-12 text-center">
							<c:choose>
								<c:when test="${loginInfo !=null}">
									<button type="button" class="btn btn-outline-secondary"
										id="myDonaBtn">내 후원보기</button>
								</c:when>
							</c:choose>
							<button type="button" class="btn btn-outline-secondary"
								id="toMainBtn">메인으로 돌아가기</button>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col d-none d-sm-block"></div>
		</div>

		<jsp:include page="../standard/footer.jsp" />
	</div>
		<script>
			//나중에 링크 수정하기
			$("#myDonaBtn")
					.on(
							"click",
							function() {
								location.href = "${pageContext.request.contextPath}/mypageDonation.do";
							})
			$("#toMainBtn")
					.on(
							"click",
							function() {
								location.href = "${pageContext.request.contextPath}/main/index.jsp";
							})
		</script>
</body>
</html>