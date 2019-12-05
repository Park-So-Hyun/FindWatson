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
        <script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		
		<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
        <link rel="stylesheet" href="resources/css/mainStyle.css">
<style>
#id{
border:none;
margin-bottom:2px;
}
</style>        

</head>
<body>
<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/header.jsp" />   
	     
	<div class="row mt-2">
	    <div class="col d-none d-sm-block"></div>
	        
	        <!-- 중심내용 -->
             <form action="${pageContext.request.contextPath}/mypageModify.member" method="post" id=frm>
 				
                     <div class =row>
                        <div class= "col-12">
                            <h5>아이디</h5>
                             <input type=text id = id name=id value=${ dto.id} readonly="readonly">
                        </div>
                        </div>

                    <div class =row>
                        <div class= "col-12">
                            <h5>비밀번호</h5>
                            <input type="password" id = pw name=pw>
                        </div>
                        <div class= "col-12">
                            <h5>비밀번호 확인</h5>
                            <input type="password" id = pwRe>
                        </div>
                        <div class=col-12 id=pwCheck></div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>이름</h5>
                            <input type=text id = name name=name value=${ dto.name}>
                        </div>
                        <div class=col-12>
                            <div id=nameCheck></div>
                        </div>
                    </div>

                    <div class =row>
                        <div class= "col-12 col-md-5">
                            <h5>생년월일</h5>
                            <input type=text id = year name=birth placeholder="yyyymmdd" value=${ dto.birth}>
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>성별</h5>
                            남<input type=radio name=gender class=gender value="남">&emsp;
                            &emsp;&emsp;&emsp;&emsp;&emsp;
                            여<input type=radio name=gender class=gender value="여">
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>본인 확인 이메일</h5>
                            <input type=text id = email name=email value=${ dto.email}>
                        </div>
                        <div class=col-12>
                            <div id=emailCheck></div>
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>휴대폰 번호</h5>
                            <input type=text id = phone name=phone value=${ dto.phone}>
                        </div>
                        <div class=col-12>
                            <div id=phoneCheck></div>
                        </div>
                    </div>

                    <div class=row>
                        <div class= col-12>
                            <h5>주소</h5>
                        </div>
                    </div>
                    <div class=row>
                        <div class=col-6><input type="text" id="postcode" name=postcode placeholder="우편번호" readonly value=${ dto.postcode} onclick="sample4_execDaumPostcode()"> </div>
                        <div class=col-6><button id = addressBtn type="button" onclick="sample4_execDaumPostcode()" class="btn btn-outline-secondary">찾기</button></div>                      
                    </div>
                    
                    <div class=row>                    
                        <div class=col-6>
                            <input type="text" id="address1" name=address1 placeholder="도로명주소" readonly value=${ dto.address1}>
                        </div>
                        <div class=col-6>
                            <input type = text name = address2 id = address2 placeholder="상세주소" value=${ dto.address2}>
                        </div>
                    </div>

                    <div class= row>
                        <div class=col-12>
                            <h5>관심 동물</h5>
                        </div>
                    </div>
                    <div class=row>
                        <div class=col-3>새<input type=radio name=lovePet value=bird class=animal></div>
                        <div class=col-3>물고기<input type=radio name=lovePet value=fish class=animal></div>
                        <div class=col-3>햄스터<input type=radio name=lovePet value=mouse class=animal></div>
                        <div class=col-3>토끼<input type=radio name=lovePet value=rabbit class=animal></div>
                    </div>
                    <div class=row>
                        <div class=col-3>고슴도치<input type=radio name=lovePet value=hedgehog class=animal></div>
                        <div class=col-3>파충류<input type=radio name=lovePet value=reptile class=animal></div>
                        <div class=col-3>곤충류<input type=radio name=lovePet value=bug class=animal></div>
                        <div class=col-3>기타<input type=radio name=lovePet value=other class=animal><input type=text name=otherAnimal class=other></div>
                    </div>


                    <!--               -->
                    <div class=row>
                    <div class="col-12 center">
                     <button type="button" id=save class="btn btn-outline-secondary">정보수정</button>
					 <button type="button" id=reset class="btn btn-outline-secondary">초기화</button>
					 
					 </div>
                    </div>
                   </form>
                
              <!--  -->
	    <div class="col d-none d-sm-block"></div>
	</div>
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	
	</div>
        <script>
        //우편번호
            function sample4_execDaumPostcode() {
                new daum.Postcode({
                    oncomplete: function(data) {
                        var roadAddr = data.roadAddress; 
                        document.getElementById('postcode').value = data.zonecode;
                        document.getElementById("address1").value = roadAddr;
                    }
                }).open();
            }
            //비밀번호 확인
             $("#pw").on("keyup",function(){
            	 var regex = /^[a-zA-Z0-9]{8,12}$/;
            	 var data = $('#pw').val();
	                var result = regex.exec(data);
	                if(result !=null){
	                	   var pw = $("#pw").val();
	                       var pwRe = $("#pwRe").val();
	                       
	                       if(pw == pwRe){
	                           $("#pwCheck").html("<div id=blue>비밀번호 일치</div>");
	                       }else{
	                           $("#pwCheck").html("<div id=red>비밀번호 불일치</div>");
	                       }
	                }
	                else if(result == null){
	                	$("#pwCheck").html("<div id=red>비밀번호는 숫자,영어를 이용해 8-14자로 입력하십시오.</div>")
	                }
            })
            $("#pwRe").on("keyup",function(){
                var pw = $("#pw").val();
                var pwRe = $("#pwRe").val();

                if(pw == pwRe){
                    $("#pwCheck").html("<div id=blue>비밀번호 일치</div>");
                }else{
                    $("#pwCheck").html("<div id=red>비밀번호 불일치</div>");
                }
            })

            //이름 확인
            $("#name").on("keyup",function(){
                var regex = /^[가-힣]{2,8}$/gm;
                var name = $("#name").val();
                var result = regex.exec(name);
                if(result == null){
                    $("#nameCheck").html("잘못된 이름 형식입니다.");
                }else{
                    $("#nameCheck").html("");
                }
            })

            //이메일 확인
            $("#email").on("keyup",function(){
                var regex = /^[0-9a-zA-Z]+@[a-z]+(\.[a-z]+){1,2}$/gm;
                var email = $("#email").val();
                var result = regex.exec(email);

                if(result == null){
                    $("#emailCheck").html("잘못된 이메일 형식입니다.");
                }else{
                    $("#emailCheck").html("");
                }
            })

            //연락처 양식 확인
            $("#phone").on("keyup",function(){
              var regex = /^01[0-9]{8,9}$/gm;
                var phone = $("#phone").val();
                var result = regex.exec(phone);
                    if(result == null){
                        $("#phoneCheck").html("잘못된 번호 형식입니다.");
                    }else{
                        $("#phoneCheck").html("");
                    }
                
            })
            
            //정보 수정버튼 클릭
             $("#save").on("click",function(){
            	 if($("#id").val() == ""){
            		 alert("아이디를 입력하세요!");
            		 return;
            	 }
            	 
            		if($("#pw").val() == "" || $("#pwRe").val() == "" || $("#pw").val() != $("#pwRe").val()){
						alert("비밀번호를 제대로 입력하세요!");
						return;
					}
					if($("#pw").val()!=""){
						
						
						var regex = /^[a-zA-Z0-9]{8,12}$/;
		                var data = $('#pw').val();
		                var result = regex.exec(data);
		                if (result != null){
		                	var pw = $("#pw").val();
							var pwRe = $("#pwRe").val();

							if (pw == pwRe) {
								
							} else {
								alert("비밀번호 불일치");
								return;
							}
		                }
		                else{
		                	alert("비밀번호 형식에 맞지않습니다.");
		                	return;
		                }
						
					}
                    $("#frm").submit();
            }) 
            
            //초기화
            $("#reset").on("click",function(){
                var result = confirm("정말 초기화하시겠습니까?");
                if(result){
                    $("#pw").val("");
                    $("#pwRe").val("");
                    $("#name").val("");
                    $("#year").val("");
                    $("#month").val("");
                    $("#day").val("");
                    $("#email").val("");
                    $("#phone").val("");
                    $("#postcode").val("");
                    $("#address1").val("");
                    $("#address2").val("");
                    $(".gender").prop("checked","false");
                    $(".animal").prop("checked","false");
                    $(".other").val("");
                }
            })
        </script>
</body>
</html>