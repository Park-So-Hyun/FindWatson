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
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">

<style>
#imgCon {
	width: 300px;
	height: 300px;
}

#image_preview {
	display: none;
}

#imgRemove {
	color: black;
}
input[type=text]{
border:none;
border:1px solid gray;
margin-top:2px;
margin-bottom:2px;
width:100%;
}
.inputLabel{
background-color:#f0f4ff;
   font-weight:700;
           
            color : #084480;
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
#find{
margin-bottom:4px;
}

.header{
         
            font-weight:700;
            font-size:35px;
            color : #084480;
          
                }
</style>
</head>
<body>
	<!-- container -->
	<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/headerAdmin.jsp" />   
	     
	<div class="row mt-2">
	  <div class="col d-none d-sm-block"></div>      
	        <!-- 중심내용 -->
	  
		
				<div class="container col-12 col-sm-6">
				<div class="row">
					<div id="article-middle" class="col-12 mt-2">						
						<div class="row mb-3 p-1 text-center">
							<div id="board-top" class="col-12 m-0 header">병원 등록하기</div>							
						</div>
						<hr>
						<form action="${pageContext.request.contextPath}/hosptInfoInsert.admin" method="post" id="frm" enctype="multipart/form-data">
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">병원 이름</div>
							<div class="col-12 col-md-9"><input type="text" id="name" name=name></div>
						</div>
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">우편번호</div>
							<div class="col-12 col-md-9">
							<div class="row">
							<div class=col-12>
							<input type="text" id="postcode" name=postcode readonly style="width:80%" onclick="sample4_execDaumPostcode()">
							<button type=button onclick="sample4_execDaumPostcode()" class="btn btn-sm btn-outline-secondary" id=find>찾기</button>
							</div>
							<div class=col-12>
							<input type="text" id="address1" name=address1 readonly>
							<input type="text" id="address2" name=address2>
							</div>
							</div>
							</div>
						</div>
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">전화번호</div>
							<div class="col-12 col-md-9"><input type="text" id="phone" name=phone></div>
						</div>
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">홈페이지 주소</div>
							<div class="col-12 col-md-9"><input type="text" id="homepage" name=homepage></div>
						</div>
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">진료과목</div>
							<div class="col-12 col-md-9">
							<div class=row>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=새> 새 </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=물고기> 물고기  </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=햄스터> 햄스터 </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=토끼> 토끼 </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=고슴도치> 고슴도치 </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=파충류> 파충류 </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=곤충류> 곤충류 </div>
								<div class="col-6 col-md-4"><input type="checkbox" name=medicalAnimal value=기타> 기타 </div>
							</div>
							</div>
						</div>
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">진료시간</div>
							<div class="col-12 col-md-9">
							<div class=row>
								<div class="col-12 col-lg-4"><input type="checkbox" name=openTime value=주간진료> 주간진료 </div>
								<div class="col-12 col-lg-4"><input type="checkbox" name=openTime value=야간응급진료> 야간응급진료 </div>
								<div class="col-12 col-lg-4"><input type="checkbox" name=openTime value=24시간진료> 24시간진료 </div>
							</div>
							</div>
						</div>
						<div class="row mb-4 p-2">
							<div class="col-12 col-md-3 text-center inputLabel">사진 등록</div>
							<div class="col-12 col-md-9">
							<div class=row>
							<div class="col-12"><input type=file id=image name="image"></div>
							
							</div>
							</div>							
						</div>	
						<div class="row mb-4 p-2">
							<div id="image_preview" class=" col-12 text-center">
							<img src="#" class="col-12 mb-2" id="imgCon" /> <br /> <a id="imgRemove" href="#">이미지 지우기</a></div>
						</div>
																						
						<div class="row mb-4 p-2">													
							<div class="col-12 p-1 text-center">
								<button type=button class="btn btn-sm btn-outline-secondary" id="writeBtn">등록</button>
								<button type=button class="btn btn-sm btn-outline-secondary" id="returnBtn">취소</button>
							</div>
						</div>
						</form>
					</div>
				</div>
				</div>
		
		
		 <!-- 중심내용 -->
	      <div class="col d-none d-sm-block"></div>  
	</div>
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	</div>
<!-- container --> 
<script>
      //우편번호 찾기
      function sample4_execDaumPostcode() {
         new daum.Postcode({
            oncomplete : function(data) {
               var roadAddr = data.roadAddress;
               document.getElementById('postcode').value = data.zonecode;
               document.getElementById("address1").value = roadAddr;
            }
         }).open();
      }
      $('#writeBtn').on('click', function() {
    	 //병원등록 전 빈칸없는지 검사
    	  if($("#name").val()==""){
				alert("병원이름을 입력하세요!");
				return;
			}
    	  if($("#postcode").val()==""){
				alert("우편번호를 입력하세요!");
				return;
			}
    	  if($("#address1").val()==""){
				alert("주소를 입력하세요!");
				return;
			}
    	  if($("#address2").val()==""){
				alert("상세주소를 입력하세요!");
				return;
			}
    	  if($("#phone").val()==""){
				alert("전화번호를 입력하세요!");
				return;
			}
    	  if($("#homepage").val()==""){
				alert("홈페이지주소를 입력하세요!");
				return;
			}
    	  if($("input[name=medicalAnimal]:checked").length < 1){
				alert("진료동물을 선택하세요!");
				return;
			}
    	  if($("input[name=openTime]:checked").length < 1){
				alert("진료과목을 선택하세요!");
				return;
			}
    	  if($("#image").val()==""){
				alert("사진을 선택하세요!");
				return;
			}
    	  
         $('#frm').submit();
      });
      $('#returnBtn').on('click', function() {
    	  var result = confirm("등록을 취소하시겠습니까?");
    	  if(result){
    		  history.back();
    	  }
      });
        
    $('#image').on('change', function() {
        
        ext = $(this).val().split('.').pop().toLowerCase(); //확장자
        
        //배열에 추출한 확장자가 존재하는지 체크
        if($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
            resetFormElement($(this)); //폼 초기화
            window.alert('이미지 파일이 아닙니다! (gif, png, jpg, jpeg 만 업로드 가능)');
        } else {
            file = $('#image').prop("files")[0];
            blobURL = window.URL.createObjectURL(file);
            $('#image_preview img').attr('src', blobURL);
            $('#image_preview').slideDown(); //업로드한 이미지 미리보기 
            $(this).slideUp(); //파일 양식 감춤
        }
    });

    $('#image_preview a').bind('click', function() {
        resetFormElement($('#image')); //전달한 양식 초기화
        $('#image').slideDown(); //파일 양식 보여줌
        $(this).parent().slideUp(); //미리 보기 영역 감춤
        return false; //기본 이벤트 막음
    });
        
    function resetFormElement(e) {
        e.wrap('<form>').closest('form').get(0).reset(); 
        //리셋하려는 폼양식 요소를 폼(<form>) 으로 감싸고 (wrap()) , 
        //요소를 감싸고 있는 가장 가까운 폼( closest('form')) 에서 Dom요소를 반환받고 ( get(0) ),
        //DOM에서 제공하는 초기화 메서드 reset()을 호출
        e.unwrap(); //감싼 <form> 태그를 제거
    }
   </script>
</body>
</html>