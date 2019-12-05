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
		<link rel="stylesheet" href="resources/css/mainStyle.css">

<style>
.line{
border:none;
}
div h{
text-align: center;
}
</style>

</head>
<body>
	<div class="container">
<jsp:include page="../standard/headerMember.jsp" /> 
 
<div class="row mt-2">
	    <div class="col d-none d-sm-block"></div>
<!--            -->
	<div class="container col-12">
           <div class=col>
  		
  		 <div class="row line"> 
                   <div class=col-12>
                   <hr style="border:solid 1px;">  
                   </div>
            </div>
					<div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 "><h5>아이디</h5></div>
                           <div class= "col-8 text-left"> ${dto.id}</div>
                           </div> 
                        </div>
                        </div>

                    <div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth"><h5>이름</h5></div>
                           <div class= "col-8 text-left"> ${dto.name}</div>
                           </div> 
                        </div>
                        </div>
                        <div  class =row>
                        <div class=col-12>
                            <div id=nameCheck></div>
                        </div>
                        </div>


					<div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth"><h5>생년월일</h5></div>
                           <div class= "col-8 text-left"> ${dto.birth}</div>
                           </div>
                           </div>
                        </div> 



						<div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth"><h5>성별</h5></div>
                           <div class= "col-8 text-left"> ${dto.gender}</div>
                           </div>
                           </div>
                        </div> 


                   <div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth"><h5>본인 확인 이메일</h5></div>
                           <div class= "col-8 text-left"> ${dto.email}</div>
                           </div>
                           </div>
                        </div> 
                   
         
                   
                   <div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth"><h5>휴대폰 번호</h5></div>
                           <div class= "col-8 text-left"> ${dto.phone}</div>
                           </div> 
                           </div>
                        </div>


                  <div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth"><h5>주소</h5></div>
                           <div class= "col-8 text-left"> 
                           ${dto.postcode}
                            ${dto.address1}
                            ${dto.address2}</div>
                           </div> 
                           </div>
                        </div>


                 <div class =row>
                        <div class= col-12>
                           <div class=row>
                           <div class= "col-4 text-rigth "><h5>관심 동물</h5></div>
                           <div class= "col-8 text-left "> ${dto.lovePet}</div>
                           </div> 
                           </div>
                        </div>
                 
                   
                   <div class="row line"> 
                   <div class=col-12>
                   <hr style="border:solid 1px;">  
                   </div>
            </div>
                   
                    <form action="${pageContext.request.contextPath}/InfoModify.member" method="post">
                     <input type=hidden name="id" value=${ dto.id}>
                     <input type=hidden name="pw" value=${ dto.pw}>
                     <input type=hidden name="name" value=${ dto.name}>
                     <input type=hidden name="birth" value=${ dto.birth}>
                     <input type=hidden name="gender" value=${ dto.gender}>
                     <input type=hidden name="email" value=${ dto.email}>
                     <input type=hidden name="phone" value=${ dto.phone}>
                     <input type=hidden name="postcode" value=${ dto.postcode}>
                     <input type=hidden name="address1" value=${ dto.address1}>
                     <input type=hidden name="address2" value=${ dto.address2}>
                     <input type=hidden name="lovePet" value=${ dto.lovePet}>
                     <input type=hidden name="signPath" value=${ dto.signPath}>

                    <div>
                     <button type="button" id=modify class="btn btn-outline-secondary">정보수정</button></div>
                    </form>

            <!--            -->  
                <div class="col d-none d-sm-block"></div>
	</div>
             <jsp:include page="../standard/footer.jsp" />  
        </div>
        </div>
        </div>
        
        <script>
        $("#modify").on("click", function(){
        	$("form").submit();
        });
        </script>
</body>
</html>