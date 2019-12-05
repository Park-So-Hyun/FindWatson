<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찾아줘 왓슨!</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
<script
   src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script
   src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

      <link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.css" rel="stylesheet">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-bs4.js"></script>
  <link rel="stylesheet" href="../resources/css/mainStyle.css">
<style>
	.noneExist{display:none;}
	#category{width: 100%; height: 100%;}
	.search-box>*{width: 100%;}
	.write-box>*{width: 100%;}
	#article, .line{border: 0.5px solid lightgray;}
</style>
</head>
<body>
<form action="${pageContext.request.contextPath}/communityQuestionWrite.bo" method="post" id=frm>
<div class="container col-12">
      <jsp:include page="../standard/headerMember.jsp" /> 
      <!--            -->
      <div class="row mt-2">
<div class="col d-none d-sm-block"></div>
<div class="contents col-12 col-sm-6">
    <div class="row">
         <div class="col-12 mb-3" id="article">
            <div class="row">
               <div id="article-middle" class="col-12 mt-2">
                  <div class="row mb-2 p-1 text-center">
                     <h3 id="board-top" class="col-auto col-sm-4 m-0">질문게시판</h3>
                     <span class="col-auto col-sm-8 mt-2">반려동물에 대해 질문하는 게시판
                        입니다.</span>
                  </div>
                  <div class="row">
                     <div class="col-2 p-1">
                     <input class=noneExist name=header value=질문>
                        <select class="space form-control" name="animalHeader">
                           <option value="새">새</option>
                           <option value="물고기">물고기</option>
                           <option value="햄스터">햄스터</option>
                           <option value="토끼">토끼</option>
                           <option value="고슴도치">고슴도치</option>
                           <option value="파충류">파충류</option>
                           <option value="곤충류">곤충류</option>
                           <option value="기타">기타</option>
                        </select>
                     </div>
                     <div class="col-10 p-1">
                        <input type="text" class="space form-control" id="questionTitle" name="questionTitle">
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-12 p-1">
                        <!-- 썸머노트 -->
                        <textarea id = "summernote"></textarea><br>
                        <textarea id=snInput class="noneExist" name=content></textarea>
                     </div>
                  </div>
                  <div class="row mb-2">
                     <div class="col-12 p-1 text-center">
                        <button id="writeBtn" type=button
                           class="btn btn-sm btn-outline-secondary">등록</button>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
</div>
<div class="col d-none d-sm-block"></div>
</div>
      <!--            -->
      <jsp:include page="../standard/footer.jsp" />  
        </div>
   </form>
   <script>
   //썸머노트 이미지 업로드
   $("#summernote").summernote({
      height : 400,
      disableResizeEditor : true,
      tabsize: 2,
      callbacks : {
         onImageUpload : function(files) {
            var data = new FormData();
            data.append("comQuestionImg", files[0]);

            $.ajax({
               url : "${pageContext.request.contextPath}/imgUploadQuestion.bo",
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
   
   $("#writeBtn").on("click",function(){
      var title = $("#questionTitle").val(); 
      var content = $(".note-editable").html();
      var regex = /<p>.{1}[^b].*?<\/p>/;
      var result = regex.exec(content);
      
      if( (title == "") || (result == null) ){
         alert("내용은 2줄이상 입력해주세요");
      }else{
         var result = confirm("정말 등록 하시겠습니까?");
         if(result){
            var contentReal = $(".note-editable").html();
            $("#snInput").val(contentReal);
            $("#frm").submit();
         }
      }
   })
   
   </script>
</body>
</html>