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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainStyle.css">

<style>
            .tb_box_login {
                text-align:center;
            }
            .tb_box_login p{
                display:inline-block;
            }
            .total{
                text-align:center;
            }
            .btn_area{
                text-align:center;
            }
            * {
                box-sizing: border-box;
                font-family: 'Noto Sans KR', sans-serif;
            }
/*            div { border: 1px solid black}*/
            #loginBtn{
                background-color: ;
            }
            #logo {
                width: 100%;
                height: 50px;
                margin: auto;
                background-color: ghostwhite;
            }
            strong{
                font-size: 28px;
            }

            .navbar, .navbar-nav{
                background-color: ;
            }
            #searchTop{
                width: 90%;
                position: absolute;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color:#084480;
                border-radius: 3px;
            }
            #searchBtn1{
                background-color:#ffffff; 
                color:#084480;
                width: 5%;
            }
            #article-img {
                width: 100%;
                height: 100px;
                margin: auto;
                background-color: ghostwhite;
            }

            #footer>div {
                height: 100%;
            }

            #footer-logo>div {
                height: 100%;
                background-color: ghostwhite;
            }

            #footer-contents {
                font-size: 8pt;
            }
            .nav-item {
                display: inline-block;
                width: 100%;
                text-align: center;
            }

            .navbar-nav {
                width: 100%;
            }
            .noneExist{
                display:none;
            }
            .right{
                text-align: right;
            }
            #cmt{
                width:100%;
                height: 100%;
            }
            .center{
                text-align: center;
            }    
            .line{border: 1px solid lightgray;}
            #searchAdd{width: 100%}
            .ipTag{width: 100; height: 100%;}
            #inputPay{width: 20%; height: 50%;}
            .fontReSize{font-size: 8pt;}
</style>
</head>
<body>
	<div class="container col-12">
		<jsp:include page="../standard/header.jsp" />
		
	<div class="row mt-2">
	    <div class="col d-none d-sm-block"></div>

<!--  후원폼  -->
			<div class="col-12 col-sm-6">	
            <div class="row mt-2">
                <div class="col d-none d-sm-block"></div>
                <div class="col-12 col-sm-8">
                    <div class="row mb-2">
                        <div class="col-12">
                            <h2 class="tit_sub mr-2 mb-0">후원하기</h2>
                            <p class="txt_sub align-self-center m-0">소중한 아이들을 위해 기부해주세요!</p>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-12">
                            <img src="../imgs/animal/%EC%BD%94%EB%89%B4%EC%96%B4.png" class="rounded mx-auto d-block" style="border-radius: 10px;">
                        </div>
                    </div>
                        <!--                여기는 세션정보에 따라 block하기-->
                        <c:choose>
                            <c:when test="${loginInfo ==null}">
                               <div class="row line mb-2">
                                <div class="tb_box_login col-12 p-0">
                                    <div class="col-12 p-0">
                                        <p class="col-auto p-0">로그인 후 후원을 하시면&nbsp;</p>
                                        <p class="col-auto p-0">후원조회 및 증빙서류발급이 용이합니다.</p>
                                    </div>
                                    <div class="col-12 p-0">
                                            <a role="button" class="btn btn-outline-secondary btn02_blue mb-1" href="${pageContext.request.contextPath}/member/login.jsp">로그인</a>
                                    </div>
                                </div>
                                </div>
                            </c:when>
                        </c:choose>
                    <!--            여기서부터 입력폼-->
                    <div class="row">
                        <form action="${pageContext.request.contextPath}/donate.do" method="post" class="col-12 p-0">
                            <div class="step1 col-12 p-0">
                                <div class="sps_step_t col-12 p-0">
                                    <h3 class="tit_step">
                                        <span> STEP <strong>1</strong></span> 후원사업 선택
                                    </h3>
                                </div><hr>
                                <div class="sps_step_c col-12">			
                                    <div class="row line">		
                                        <div class="col-12 text-center mb-1">
                                            후원금액
                                        </div>
                                        <div class="col-12">
                                                <div class="row">
                                                    <div class="price col-12 col-md-3">
                                                        <input type="radio" name="support" value="1000">
                                                        <label for="sponsor_money01" style="font-weight: 900">1,000원</label>
                                                    </div>
                                                    <div class="price col-12 col-md-3">
                                                        <input type="radio" name="support" value="2000">
                                                        <label for="sponsor_money02" style="font-weight: 900">2,000원</label>
                                                    </div>
                                                    <div class="price col-12 col-md-3">
                                                        <input type="radio" name="support" value="5000">
                                                        <label for="sponsor_money03" style="font-weight: 900">5,000원</label>
                                                    </div>
                                                    <div class="price col-12 col-md-3">
                                                        <input type="radio" name="support" value="10000">
                                                        <label for="sponsor_money04" style="font-weight: 900">10,000원</label>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="price col-12 col-md-3 align-self-center">
                                                        <input type="radio" name="support" value="50000">
                                                        <label for="sponsor_money05" style="font-weight: 900">50,000원</label>
                                                    </div>
                                                    <div class="price col-12 col-md-9 mb-2 align-self-center"> 
                                                        <input type="radio" name="support" value="etc">
                                                        <label for="sponsor_money06">기타</label>
                                                        <input type="text" class="d-inline form-control" id="inputPay" style="font-weight: 900" disabled> 원
                                                    </div>
                                                </div>
                                        </div>
                                    </div>
                                    <div class="row line mt-1">
                                        <div class="total col-12" style="font-size: 15pt;">
                                        총 후원금액 <span id="inputDisplay"></span>원</div>
                                    </div>
                                </div>	
                            </div>
                            <div class="step2 col-12 p-0 mt-2">
                                <div class="sps_step_t col-12 p-0">
                                    <h3 class="tit_step">
                                        <span> STEP <strong>2</strong>
                                        </span> 후원자 정보
                                    </h3>
                                </div><hr>
                                <div class="sps_step_c col-12">
                                    <div class="row line">
                                        <div class="col-12 mb-1 mt-1">
                                            <div class="row">
                                                <div class="col-12 col-sm-3 text-center">
                                                    <label class="mt-2">후원자명</label>
                                                </div>
                                                <div class="col-12 col-sm-5">
                                                    <input type="text" id="name" class="ipTag form-control" name="name" placeholder="이름">
                                                </div>
                                                <div class="col-12 col-sm-4 align-self-center fontReSize" id="validName"></div>
                                            </div>
                                        </div>

                                        <div class="col-12 mb-1">
                                            <div class="row">
                                                <div class="col-12 col-sm-3 text-center">
                                                     <label class="mt-2">생년월일</label>
                                                </div>
                                                <div class="col-12 col-sm-5">
                                                    <input type="date" class="ipTag form-control" id="birth" name="birth" max="2050-12-31"></div>
                                                <div class="col-12 col-sm-4 align-self-center fontReSize" id="validBirth"></div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-1">
                                            <div class="row">
                                                <div class="col-12 col-sm-3 text-center">
                                                    <label class="mt-2">전화번호</label>
                                                </div>
                                                <div class="col-12 col-sm-5">
                                                    <input type="text" class="ipTag form-control" id="phone" name="phone" placeholder="전화번호">
                                                </div>
                                                <div class="col-12 col-sm-4 align-self-center fontReSize" id="validPhone"></div>
                                            </div>
                                        </div>

                                        <div class="col-12 mb-1">
                                            <div class="row">
                                                <div class="col-12 col-sm-3 text-center">
                                                    <label class="mt-2">이메일</label>
                                                </div>
                                                <div class="col-12 col-sm-5">
                                                    <input type="text" class="ipTag form-control" id="email" name="email" placeholder="이메일">
                                                </div>
                                                <div class="col-12 col-sm-4 align-self-center fontReSize" id="validEmail"></div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-1">
                                            <div class="row">
                                                <div class="col-12 col-sm-3 text-center">
                                                    <label class="mt-2">주소</label>
                                                </div>
                                                <div class="wrap_address col-12 col-sm-9">
                                                    <div class="row mb-1">
                                                        <div class="col-12 col-sm-4">
                                                        <input type="text" class="ipTag form-control" id="postcode" name="postcode" onclick="exeDaumPostcode();" readonly></div>
                                                        <div class="col-12 col-sm-8"><input type="text" class="ipTag form-control" id="addr1" name="addr1" onclick="exeDaumPostcode();" readonly></div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-12 col-sm-8"><input type="text" class="ipTag form-control" id="addr2" name="addr2" placeholder="상세주소를 입력해주세요."></div>
                                                        <div class="col-6 col-sm-4 text-center">
                                                        <input type="button" id="searchAdd" class="btn btn-outline-secondary" value="검색" onclick="exeDaumPostcode();"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-12 mb-1">
                                            <div class="row">
                                                <div class="col-12 col-sm-3 text-center">
                                                    <label class="mt-2">약관동의</label>
                                                </div>
                                                <div class="check_area col-12 col-sm-9">
                                                    <div class="row ml-2">
                                                        <input type="checkbox" class="align-self-center" id="agree" name="agree"> 
                                                        <label for="agree" class="align-self-center mb-0 mr-2">&nbsp;개인정보 수집, 이용목적 및 취급방침</label>
                                                        &nbsp;<button type="button" class="btn_detail btn btn-outline-secondary">+</button>
                                                    </div>
                                                    <div class="agree_cont row">
                                                        <div class="agree_cont_style col-12">
                                                            <p class="">수집하는 개인정보의 항목 및 수집방법</p>
                                                            <p>개인정보 수집항목</p>
                                                        </div>
                                                    </div>
                                                </div>										
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="step3 col-12 p-0 mt-2">
                                    <div class="sps_step_t col-12 p-0">
                                        <h3 class="tit_step">
                                            <span> STEP <strong>3</strong>
                                            </span> 납입방법 선택
                                        </h3>
                                    </div><hr>
                                    <div class="sps_step_c col-12 line">
                                        <div class="row">
                                            <div class="col-4">후원방식</div>
                                            <div class="payMethodBox col-8">
                                                <input type="radio" name="payMethod" id="payMethod1" value="card">
                                                <label for="payMethod1">신용카드</label>
                                            </div>											
                                        </div>							
                                    </div>						
                                </div>
                                <div class="btn_area col-12 mt-2 p-0">
                                    <button class="btn btn-outline-secondary" type="button" id="donaBtn">후원신청</button>
                                </div>
                                <input type="hidden" name="etcPrice" id="etcPrice">
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col d-none d-sm-block"></div>
            </div>
            </div>
<!--  후원폼 끝  -->
      
	    <div class="col d-none d-sm-block"></div>
	</div>
		<jsp:include page="../standard/footer.jsp" />
	</div>
</body>
<script>

$('input:radio[name=support]').on("click", function(){
	var supportVal = $('input:radio[name=support]:checked').val();
	
	if(supportVal == 'etc'){
		if($("#inputPay").val() == ""){
			$("#inputDisplay").html("");
			$("#inputPay").attr("disabled", false);
			$("#inputPay").on("focusout", function(){
				var regex = /[0-9]+/g;
				var data = $("#inputPay").val();
				var result = regex.exec(data);
				
				if(result != null){
					var price = $("#inputPay").val();
					$("#inputDisplay").html(price);
					$("#etcPrice").val(price);
					console.log($("#etcPrice").val());
				}else{
					alert("숫자만 입력가능합니다. 다시 확인해주세요.");
					$("#inputPay").val("");
					
				}
			})
		}else{
			$("#inputPay").attr("disabled", false);
			var price = $("#inputPay").val();
			$("#inputDisplay").html(price);			
			$("#etcPrice").val(price);	
		}
		
		
		
	}else{
		$("#inputPay").attr("disabled", true);
		$("#inputDisplay").html(supportVal);
		$("#etcPrice").val("");
	}
});

$(".agree_cont").css("display", "none");
$(".btn_detail").on("click", function(){
	var dis = $(".agree_cont").css("display");
	if(dis == "block"){
		$(".agree_cont").css("display", "none");
	}else{
		$(".agree_cont").css("display", "block");
	}	
});

function exeDaumPostcode(){
    new daum.Postcode({
        oncomplete: function(data) {
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("addr1").value = roadAddr;
        }
    }).open();
}

//빈칸 검사
$("#donaBtn").on("click", function(){
	var support = $('input:radio[name=support]').is(':checked');
	var agree = $('input:checkbox[name=agree]').is(':checked');
	var method = $('input:radio[name=payMethod]').is(':checked');
	
	if(!support){
		alert("후원금액을 선택해주세요");
		return false;
	}else if($("#name") == ""){
		alert("이름을 입력해주세요");
		return false;
	}else if($("#validName").html() != ""){
		alert("이름을 다시 확인해주세요");
		return false;
	}else if($("#birth").val() == ""){
		alert("생년월일을 입력해주세요");
		return false;
	}else if($("#validBirth").html() != ""){
		alert("생년월일 양식을 다시 확인해주세요");
		return false;
	}else if($("#phone").val() == ""){
		alert("전화번호를 입력해주세요");
		return false;
	}else if($("#validPhone").html() != ""){
		alert("전화번호를 다시 확인해주세요");
		return false;		
	}else if($("#email").val() == ""){
		alert("메일을 입력해주세요");
		return false;
	}else if($("#validEmail").html() != ""){
		alert("메일주소를 다시 확인해주세요");
		return false;
	}else if($("#postcode").val() == ""){
		alert("주소를 입력해주세요");
		return false;
	}else if(!agree){
		alert("약관을 동의해주세요");
		return false;
	}else if(!method){
		alert("납입방법을 선택해주세요");
		return false;
	}else if($("#inputDisplay").html() == ""){
		alert("후원금액을 입력해주세요")
		return false;
	}
		$("form").submit();
})

//유효성검사
$("#name").on("focusout", function(){
	var regex = /\S+/g;
	var data = $("#name").val()
	var result = regex.exec(data);
	
	if(data == ""){
		$("#validName").html("이름을 입력해주세요");
	}else if(result == null){
		$("#validName").html("이름엔 공백이 들어갈 수 없습니다");
	}else{
		$("#validName").html("");
	}
	
})

$("#birth").on("focusout",function(){
	var regex = /^(19[0-9][0-9]|20\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/;
	var data = $("#birth").val();
	var result = regex.exec(data);
	
	if(result == null){
		$("#validBirth").html("생년월일을 다시 확인해주세요");
	}else{
		$("#validBirth").html("");
	}
})

$("#phone").on("focusout",function(){
	var regex = /^01\d\d{3,4}\d{4}$/;
    var text = $("#phone").val();
    var result = regex.exec(text);

    if(result == null){
		$("#validPhone").html("전화번호를 다시 확인해주세요");
    }else{
    	$("#validPhone").html("");
    }
})

$("#email").on("focusout",function(){
    var regex = /^\w+@[a-z]+(.[a-z]+){1,2}$/;
    var text = $("#email").val();
    var result = regex.exec(text);

    if(result == null){
        $("#validEmail").html("이메일을 다시 확인해주세요");
    }else{
    	$("#validEmail").html("");
    }
})




</script>
</html>