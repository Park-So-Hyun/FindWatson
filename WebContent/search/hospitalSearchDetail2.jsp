<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원상세검색2</title>
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


<%--썸머노트 --%>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>

<%--별점 --%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link
	href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="resources/css/mainStyle.css">
<meta charset="UTF-8">

<style>
img {
	width: 500px;
}

#array {
	text-align: right;
}

.review>div {
	float: left;
}

.hosIn1 {
	height: 80%;
}

.hosIn2 {
	height: 20%;
}

#hosImg {
	width: 100%;
}

.center {
	text-align: center;
}

#page
 
${
cpage

	

}
{
color

 

:

 

red


;
}
.checked {
	color: orange;
}

.starCon {
	display: inline;
}
.fa-star:hover {
	cursor: pointer;
}

#titleReview {
	width: 58%;
}

#starInputCon {
	width: 100px;
	display: inline;
}

#sss {
	text-align: right;
	width: 150px;
	display: inline;
	position: absolute;
	right: 2%;
}

#ssss {
	position: relative;
}

.noneExist {
	display: none;
}

.center {
	text-align: center;
}
#likeImg{
width:20px;
}
.shield { pointer-events: none; }

</style>

</head>
<body>

	<div class="container">
		<jsp:include page="../standard/header.jsp" />
		<!--            -->
		<form
			action="${pageContext.request.contextPath}/reviewWrite.re?seq=${contents.seq}"
			method="post" id=reviewSave>
			<div class="row">
				<div class="col-12">
					<h1>${contents.hosptName}</h1>
					<h6>정보수정일 : ${contents.getDate()}</h6>
					<hr>
				</div>
			</div>

			<div class=row>
				<div class="col-12 col-md-6">
					<img src="${contents.img}" id=hosImg name=hosImg>
				</div>
				<div class="col-12 col-md-6">
					<div class="row hosIn1">
						<div class=col-12>
							<p>
								<label class=title>병원 이름</label>${contents.hosptName}</p>
							<p>
								<label class=title>주소</label>${contents.address1}
								${contents.address2}
							</p>
							<p>
								<label class=title>전화번호</label>${contents.phone}</p>
							<p>
								<label class=title>홈페이지</label>${contents.homepage}</p>
							<p>
								<label class=title>진료 동물</label>${contents.medicalAnimal}</p>
							<p>
								<label class=title>운영 시간</label>${contents.openTime}</p>
						</div>
					</div>
					<div class="row hosIn2">
						<div class=col-12>
							<button type="button" class="btn btn-secondary">정보 수정 제안</button>
						</div>
					</div>
				</div>
			</div>
			<div class=row>
				<div class=col>
					<label>Total. </label> ${contents.viewCount}
				</div>
			</div>

			<%--지도, 후기--%>
			<div class=row>
				<div class=col-12>
					<br>
					<div class="btn-group" role="group" aria-label="Basic example">
						<button type="button" class="btn btn-secondary" id=btnMap>지도</button>
						<button type="button" class="btn btn-secondary" id=btnReview>후기</button>
					</div>
					<hr>
				</div>
			</div>

			<div class=row>
				<div class=col-12>
					<h2>후기</h2>
				</div>
			</div>

			<div class=row>
				<div class=col-12 id=array>
					<a
						href="${pageContext.request.contextPath}/hospitalSearchDetail2ByScore.re?seq=${contents.seq}"
						id=scoreRange>별점순</a> <a
						href="${pageContext.request.contextPath}/hospitalSearchDetail2.re?seq=${contents.seq}">최신순</a>
					<a
						href="${pageContext.request.contextPath}/hospitalSearchDetail2ByLike.re?seq=${contents.seq}"
						id=defaultRange>좋아요순</a>
				</div>
			</div>


			<c:choose>
				<c:when test="${reviewList.size() == 0}">
					<div class=row>
						<div class=col-12>게시글이 존재하지 않습니다.</div>
					</div>
				</c:when>

				<c:otherwise>
					<c:forEach items="${reviewList}" var="dto">
						<%-------------------------------------------------------------------------------------------------- --%>
						<div class=row>
				<div class="col-4 col-md-2 p-0">		
				<div class="starCon shield">
            <span class="fa fa-star" id="star1t${dto.seq}" onclick="addt${dto.seq}(this,1)"></span>
            <span class="fa fa-star" id="star2t${dto.seq}" onclick="addt${dto.seq}(this,2)"></span>
            <span class="fa fa-star" id="star3t${dto.seq}" onclick="addt${dto.seq}(this,3)"></span>
            <span class="fa fa-star" id="star4t${dto.seq}" onclick="addt${dto.seq}(this,4)"></span>
            <span class="fa fa-star" id="star5t${dto.seq}" onclick="addt${dto.seq}(this,5)"></span>
      			</div>
				</div>
				<div class="col-6 col-sm-5 col-md-4 col-lg-5">
				<p class="m-0">
 				 <button class="btn btn-secondary" type="button" data-toggle="collapse" data-target="#collapse${dto.seq}" aria-expanded="false" 
 				 aria-controls="collapseExample">
				${dto.title}
				</button>
				</p>
				</div>
				<div class="col-3 col-md-2 d-none d-md-block">${dto.formedDate}</div>
				<div class="col-2 d-none d-md-block">${dto.writer} </div>
				<div class="col-1 col-sm-2 col-lg-1">
				<div class="row">
				<div class="col-6 p-0"><button type=button id="like${dto.seq}" class="p-0"><img src="imgs/like.png" id="likeImg"></button></div>
				<div class="col-6 d-none d-sm-block">${dto.likeCount}</div>
				</div>
				</div>
			</div>
						<c:choose>
							<c:when test="${loginInfo == dto.writer}">
								<div class="row collapse" id="collapse${dto.seq}">
									<div class="col-12 card card-body">
										${dto.content}<br>
										<button type=button id=remove${dto.seq}>삭제</button>
									</div>
								</div>
							<script>
							$("#remove${dto.seq}").on("click",function(){
							var result = confirm("정말 삭제하시겠습니까?");
							if(result){
								location.href = '${pageContext.request.contextPath}/reviewDelete.re?seq=${dto.seq}&hspSeq=${dto.hosptListSeq}';
							}
							})
							</script>
							</c:when>
							<c:otherwise>
								<div class="row collapse" id="collapse${dto.seq}">
									<div class="col-12 card card-body">${dto.content}</div>
								</div>
							</c:otherwise>
						</c:choose>

						<script>

			//별점 - 출력
             var point = ${dto.score};
             $("#star"+point+"t${dto.seq}").trigger("click");
			
             function addt${dto.seq}(ths,sno){
                 for (var i=1;i<=5;i++){
                     var cur=document.getElementById("star"+i+"t" + "${dto.seq}");
                     cur.className="fa fa-star";
                 }
                 for (var i=1;i<=sno;i++){
                     var cur=document.getElementById("star"+i+"t" + "${dto.seq}");
                     if(cur.className=="fa fa-star")
                     {
                         cur.className="fa fa-star checked";
                         $("#rating").val(i);
                     }
                 }
             }
             
           
             
           //좋아요 기능
     		$("#like${dto.seq}").on("click",function(){
     			location.href="likeIncrement.re?reviewSeq="+${dto.seq};

     		}); 
             
			</script>
					</c:forEach>
					<%------------------------------------------------------------------------------------------------------------------------------ --%>
				</c:otherwise>
			</c:choose>

			<!-- 여기 후기 쓰는 부분은 로그인 정보 있어야 뜸 -->
			<c:choose>
				<c:when test="${loginInfo != null }">

					<div class=row>
						<div class="col-12 center">${navi}</div>
					</div>

					<div class=row>
						<div class=col-12 id=ssss>
							<h2>후기 남기기</h2>
							제목 : <input type="text" id=titleReview name=title>

							<div id=sss>
								별점 :
								<div id=starInputCon>
									<span class="fa fa-star" id="star1" onclick="add(this,1)"></span>
									<span class="fa fa-star" id="star2" onclick="add(this,2)"></span>
									<span class="fa fa-star" id="star3" onclick="add(this,3)"></span>
									<span class="fa fa-star" id="star4" onclick="add(this,4)"></span>
									<span class="fa fa-star" id="star5" onclick="add(this,5)"></span>
									<input type="hidden" id="rating" value="1" name="rating">
								</div>
							</div>
							<br>

						</div>
					</div>
					<div class=row>
						<div class="col-12 center">
							<textarea id="summernote"></textarea>
							<br>
							<textarea id=snInput class=noneExist name=content></textarea>
							<button id=reviewSaveBtn type=button>후기 등록</button>
						</div>
					</div>

				</c:when>
			</c:choose>

			<!--            -->
			<jsp:include page="../standard/footer.jsp" />
		</form>
	</div>

	<script>
		//리뷰 등록 전 유효성 검사
		$("#reviewSaveBtn").on("click", function() {
			var title = $("#titleReview").val(); 
			var content = $(".note-editable").html();
			var regex = /<p>.{1}[^b].*?<\/p>/;
			var result = regex.exec(content);
			console.log(result);
			console.log(content);
			if( (title == "") || (result == null) ){
				alert("내용을 입력해주세요");
			}else{
				var result = confirm("정말 등록 하시겠습니까?");
				if(result){
					var contentReal = $(".note-editable").html();
					$("#snInput").val(contentReal);
					$("#reviewSave").submit();
				}
			}
			
		})

		//썸머노트 이미지 업로드
		$("#summernote").summernote({
			height : 600,
			tabsize: 2,
			callbacks : {
				onImageUpload : function(files) {
					var data = new FormData();
					data.append("reviewImg", files[0]);

					$.ajax({
						url : "${pageContext.request.contextPath}/imgUpload.re?seq=${contents.seq}",
						enctype : "multipart/form-data",
						type : "post",
						data : data,
						contentType : false,
						processData : false,
						cache : false,
						dataType : "json"
					}).done(function(resp) {
						console.log(resp.imgPath);
						var p = $("<p></p>");
						var img = $("<img>");
						$(img).attr("src", resp.imgPath);
						p.append(img);
						$(".note-editable").append(p);
					}).fail(function(a, b, c) {
						console.log("fail");
						console.log(a);
						console.log(b);
						console.log(c);
					});

				}
			}
		})
		//별점 함수 -입력
		 function add(ths,sno){
               for (var i=1;i<=5;i++){
                   var cur=document.getElementById("star"+i)
                   cur.className="fa fa-star"
               }
               for (var i=1;i<=sno;i++){
                   var cur=document.getElementById("star"+i)
                   if(cur.className=="fa fa-star")
                   {
                       cur.className="fa fa-star checked";
                       $("#rating").val(i);
                   }
               }
           }
		
		 //버튼
        //1. 정보수정제안
        
        //2. 지도
        $("#btnMap").on("click", function(){
        	location.href="${pageContext.request.contextPath}/contents.s?seq=${contents.seq}";
        });
        //3. 후기
        $("#btnReview").on("click", function(){
        	location.href="${pageContext.request.contextPath}/hospitalSearchDetail2.re?seq=${contents.seq}";
        });
		
		
	</script>

</body>
</html>