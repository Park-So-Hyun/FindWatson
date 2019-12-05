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
   <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminBoard.css">
   <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script><!-- 차트cdn -->
      <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
   

	<style>

.line{
border:none;
}
#board-top{
         
            font-weight:700;
            font-size:35px;
            color : #084480;
          
                }
#boardTitle{
border-top: 1px solid #084480;

background-color: #f0f4ff;
}
canvas{
margin-top:50px;
margin-bottom:50px;
}
#boardCon{
border-top: 1px solid #084480;
}
</style>
</head>
<body>
<!-- container -->
   <div class="container col-12">
   
   <!-- 헤더 -->
   <jsp:include page="../standard/headerAdmin.jsp" />   
        
   <div class="row mt-2">
       <div class=container>
		<!--            -->
		   <div class="row">
        <div class="col-12 mb-3" id="article">
            <div class="row">
            <div id="article-middle" class="col-12 mt-2">
                <div class="row mb-3 p-1 text-center">
                    <h3 id="board-top" class="col-auto col-sm-4 m-0">회원 통계</h3>
                    <span class="col-auto col-sm-8 mt-2">
                           <!-- 코멘트를 뭐라 적어야할지 모르겠... -->
                    </span>
            </div>
            <!-- 회원 통계 -->
            	
            	 <div class="row line m-0 pt-1 pb-1 " id=boardTitle>
            	<div class="col-12  text-center" id=boardTitle2>회원 통계</div>
            </div>
            	 <canvas id="memberChart" class=chart></canvas>
            	<script>
            	   var ctx = document.getElementById('memberChart');
            	   var myChart = new Chart(ctx, {
            	       type: 'bar',
            	       data: {
            	           labels: ['총 회원 수', '성별(남)', '성별(여)'],
            	           datasets: [{
            	               label: '총 회원 수',
            	               data: [${totalCount}, ${memberMCount}, ${memberWCount}],
            	               backgroundColor: [
            	                   'rgba(255, 99, 132, 0.2)',
            	                   'rgba(54, 162, 235, 0.2)',
            	                   'rgba(255, 206, 86, 0.2)',
            	                   'rgba(75, 192, 192, 0.2)',
            	                   'rgba(153, 102, 255, 0.2)',
            	                   'rgba(255, 159, 64, 0.2)'
            	               ],
            	               borderColor: [
            	                   'rgba(255, 99, 132, 1)',
            	                   'rgba(54, 162, 235, 1)',
            	                   'rgba(255, 206, 86, 1)',
            	                   'rgba(75, 192, 192, 1)',
            	                   'rgba(153, 102, 255, 1)',
            	                   'rgba(255, 159, 64, 1)'
            	               ],
            	               borderWidth: 1
            	           }]
            	       },
            	       options: {
            	           scales: {
            	               yAxes: [{
            	                   ticks: {
            	                       beginAtZero: true
            	                   }
            	               }]
            	           }
            	       }
            	   });
            	   </script>
            	   
            <!--        -->
               <div class="row line m-0 pt-1 pb-1" id=boardTitle>
            	<div class="col-12  text-center">관심 동물</div>
            </div>
            <!-- 관심동물 -->
            
            <canvas id="lovePetChart"></canvas>
            	<script>
            	   var ctx = document.getElementById('lovePetChart');
            	   var myDoughnutChart = new Chart(ctx, {
            		    type: 'doughnut',
            	       data: {
            	           labels: ['새', '물고기', '햄스터', '토끼', '고슴도치', '파충류', '곤충류', '기타'],
            	           datasets: [{
            	               label: '관심동물',
            	               data: [${bird}, ${fish}, ${hamster}, ${rabbit}, ${dochi}, ${reptile}, ${bug}, ${other}],
            	               backgroundColor: [
            	                   'rgba(255, 99, 132, 0.2)',
            	                   'rgba(54, 162, 235, 0.2)',
            	                   'rgba(255, 206, 86, 0.2)',
            	                   'rgba(75, 192, 192, 0.2)',
            	                   'rgba(153, 102, 255, 0.2)',
            	                   'rgba(255, 159, 64, 0.2)',
            	                   'rgba(50, 168, 82, 0.2)'
            	               ],
            	               borderColor: [
            	                   'rgba(255, 99, 132, 1)',
            	                   'rgba(54, 162, 235, 1)',
            	                   'rgba(255, 206, 86, 1)',
            	                   'rgba(75, 192, 192, 1)',
            	                   'rgba(153, 102, 255, 1)',
            	                   'rgba(255, 159, 64, 1)',
            	                   'rgba(50, 168, 82, 1)'
            	               ],
            	               borderWidth: 1
            	           }]
            	       },
            	       options: {
            	           scales: {
            	               yAxes: [{
            	                   ticks: {
            	                       beginAtZero: true
            	                   }
            	               }]
            	           }
            	       }
            	   });
            	</script>
            	  <div class="row line m-0 pt-1 pb-1" id=boardTitle>
            		<div class="col-12  text-center">가입 경로</div>
            </div>
            <!--        -->
            <!-- 가입경로 -->
            
            <canvas id="signpathChart"></canvas>
            	<script>
            	   var ctx = document.getElementById('signpathChart');
            	   var myDoughnutChart = new Chart(ctx, {
            		    type: 'doughnut',
            	       data: {
            	           labels: ["'찾아조 왓슨!' 직접검색", "'특수 동물 병원' 키워드 검색", '지인 소개', '기타'],
            	           datasets: [{
            	               label: '가입 경로',
            	               data: [${directSearch}, ${keywordSearch}, ${introduce}, ${otherSearch}],
            	               backgroundColor: [
            	                   'rgba(255, 99, 132, 0.2)',
            	                   'rgba(54, 162, 235, 0.2)',
            	                   'rgba(255, 206, 86, 0.2)',
            	                   'rgba(75, 192, 192, 0.2)',
            	                   'rgba(153, 102, 255, 0.2)',
            	                   'rgba(255, 159, 64, 0.2)'
            	               ],
            	               borderColor: [
            	                   'rgba(255, 99, 132, 1)',
            	                   'rgba(54, 162, 235, 1)',
            	                   'rgba(255, 206, 86, 1)',
            	                   'rgba(75, 192, 192, 1)',
            	                   'rgba(153, 102, 255, 1)',
            	                   'rgba(255, 159, 64, 1)'
            	               ],
            	               borderWidth: 1
            	           }]
            	       },
            	       options: {
            	           scales: {
            	               yAxes: [{
            	                   ticks: {
            	                       beginAtZero: true
            	                   }
            	               }]
            	           }
            	       }
            	   });
            	</script>
            	  <div class="row line m-0 pt-1 pb-1" id=boardTitle>
            		<div class="col-12  text-center">조회수 높은 게시물 top 5</div>
            </div>
            <!--        -->
            <!-- 가장 인기있는 게시물 -->
            
            <canvas id="top5Chart"></canvas>
            	<script>
            	   var ctx = document.getElementById('top5Chart');
            	   var myChart = new Chart(ctx, {
            	       type: 'bar',
            	       data: {
            	           labels: ['${top5List[0].title}','${top5List[1].title}','${top5List[2].title}','${top5List[3].title}','${top5List[4].title}'],
            	           datasets: [{
            	               label: '조회수 가장 많은 게시물',
            	               data: [${top5List[0].viewCount}, ${top5List[1].viewCount}, ${top5List[2].viewCount}, ${top5List[3].viewCount}, ${top5List[4].viewCount}],
            	               backgroundColor: [
            	                   'rgba(255, 99, 132, 0.2)',
            	                   'rgba(54, 162, 235, 0.2)',
            	                   'rgba(255, 206, 86, 0.2)',
            	                   'rgba(75, 192, 192, 0.2)',
            	                   'rgba(153, 102, 255, 0.2)',
            	                   'rgba(255, 159, 64, 0.2)'
            	               ],
            	               borderColor: [
            	                   'rgba(255, 99, 132, 1)',
            	                   'rgba(54, 162, 235, 1)',
            	                   'rgba(255, 206, 86, 1)',
            	                   'rgba(75, 192, 192, 1)',
            	                   'rgba(153, 102, 255, 1)',
            	                   'rgba(255, 159, 64, 1)'
            	               ],
            	               borderWidth: 1
            	           }]
            	       },
            	       options: {
            	           scales: {
            	               yAxes: [{
            	                   ticks: {
            	                       beginAtZero: true
            	                   }
            	               }]
            	           }
            	       }
            	   });
            	</script>
            	  
            <!--        -->
           
                       
      <div class="col d-none d-sm-block"></div>
	</div>
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	
	</div>

<!-- container --> 
</body>
</html>
