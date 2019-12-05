<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
       <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
		<link rel="stylesheet" href="resources/css/mainStyle.css">
		
            <style>
            div{
            
            font-family: 'Noto Sans KR', sans-serif;
            }
                img{
                    width:500px;
                }
                h5{
                    text-align: right;
                }
                .hosIn1{
                    height: 80%;
                }
                .hosIn2{
                    height: 20%;
                }
                table,table *{
                    border  :1px solid black;
                }
                #map{
                    width: 100%;
                    height: 250px;
                 
                }
                   #map2{
                    width: 100%;
                    height: 500px;
                 
                }
                
                #hosImg{
                    width:100%;
                    margin:auto;
                }
                hr{
                    background-color: darkgrey;  
                }
                p{
                    margin: 0px;
                    
                }
                .title{
                    width: 100px;
                    padding-left:15px;
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
             .row_info{
             margin-left:10px;
             }
             
                #btn_map{
                    border-right-style: solid 1px white;
                }
                #mapBox{
                   margin-bottom:300px;
                   width:105%;
                   height:30%;
                   
                }
            
                #map_title{
                    background-color: #f0f4ff;
                    padding: 10px;
                    margin-bottom: 0px;
                     font-family: 'Noto Sans KR', sans-serif;
            font-weight:700;
            font-size:35px;
            color : #084480;
          
                }
                #header_name{
                 font-family: 'Noto Sans KR', sans-serif;
            font-weight:700;
            font-size:35px;
            color : #084480;
            margin-top:3%;
            
                }
                .info{
                color:grey;
                }
                .title{
                color:#084480;
                   font-weight:700;
                }
                #info_small{
               font-size:13px;
                }
                .hosIn2{
                margin:0px;
                }
                .btn-group{
                padding-top:30px;
                }
                #map_icon{
                width:60px;
                height:60px;
                padding-left:10px;
                padding-bottom:5px;
                }
              #btnReview{
                margin-left:1px;
                
              }
                 #btnModify{
                margin-left:1px;
               
              }
              .hp_link:link{
              text-decoration: none;
             
              }
              .hp_link:visited{
              color: black; text-decoration: none;
              }
              .hp_link:hover{
              font-weight:700;
              }
             
            </style>
</head>
<body>
<!-- container -->
	<div class="container col-12">
	
	<!-- 헤더 -->
	<jsp:include page="../standard/header.jsp" />   
	     
	    
	<div class="row">
	    <div class="col d-none d-sm-block"></div>
	        
	        
	         <div class="container">
	  
	        <!-- 중심내용 -->
	       <div class = "row">
                  <div class = "col-12">
                       <div id="header_name">${contents.hosptName}</div>
                       <div class="row">
                       <div class="col">
                       <h6>정보수정일 : ${contents.getDate()}</h6>
                       </div>
               
                  </div>
                  <hr>
                  </div>
           </div>

                    <div class = row>
                        <div class = "col-12  col-md-3 col-lg-5 col-xl-6">
                            <%-- <img src="${contents.img}" id=hosImg name=hosImg>--%>
                            <img src="${pageContext.request.contextPath}/resources/hospital.PNG" id=hosImg name=hosImg>
                        </div>
                        
                        <div class = "col-12  col-md-9 col-lg-7 col-xl-6">
                            
                            <%-- 작은 화면일 경우  --%>
                            <div class="row hosIn1 d-block d-sm-none" id=info_small >
                                <div class =col-12>
                                <div class = row>
                                <div class = "col d-block d-md-none">
                                &nbsp; &nbsp;
                                </div>
                                </div>
                                <div class="row info">
                                    <label class = "title d-none d-md-block">병원 이름</label>
                                     <a class="d-block d-md-none">&nbsp; &nbsp; &nbsp; ▶  &nbsp; </a>${contents.hosptName}</div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">주소</label>
                                       <a class="d-block d-md-none">&nbsp; &nbsp; &nbsp; ▶&nbsp;</a>${contents.address1} ${contents.address2}</div>
                                     <div class="row info">
                                   <label class = "title d-none d-md-block">전화번호</label>
                                      <a class="d-block d-md-none">&nbsp; &nbsp; &nbsp; ▶&nbsp;</a>${contents.phone}</div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">홈페이지</label>
                                        <a class="d-block d-md-none">&nbsp; &nbsp; &nbsp; ▶&nbsp;  </a><a href="${contents.homepage}" class=hp_link>${contents.homepage}</a></div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">진료 동물</label>
                                      <a class="d-block d-md-none">&nbsp; &nbsp; &nbsp; ▶&nbsp; </a>${contents.medicalAnimal}</div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">운영 시간</label>
                                       <a class="d-block d-md-none">&nbsp; &nbsp; &nbsp; ▶&nbsp;</a>${contents.openTime}</div>
                              
                                </div>
                            </div> 
                            
                            <%-- 큰 화면일 경우  --%>
                            
                            <div class="row hosIn1 d-none d-sm-block" id=info_big>
                                <div class =col-12>
                                <div class = row>
                                <div class = "col d-block d-md-none">
                                &nbsp; &nbsp;
                                </div>
                                </div>
                                <div class="row info">
                                    <label class = "title d-none d-md-block">병원 이름</label>
                                     <a class="d-block d-md-none">&nbsp; &nbsp; ▶  &nbsp; </a>${contents.hosptName}</div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">주소</label>
                                       <a class="d-block d-md-none">&nbsp; &nbsp; ▶&nbsp;</a>${contents.address1} ${contents.address2}</div>
                                     <div class="row info">
                                   <label class = "title d-none d-md-block">전화번호</label>
                                      <a class="d-block d-md-none">&nbsp; &nbsp; ▶&nbsp;</a>${contents.phone}</div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">홈페이지</label>
                                        <a class="d-block d-md-none">&nbsp; &nbsp; ▶&nbsp;  </a><a href="${contents.homepage}" class=hp_link>${contents.homepage}</a></div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">진료 동물</label>
                                      <a class="d-block d-md-none">&nbsp; &nbsp; ▶&nbsp; </a>${contents.medicalAnimal}</div>
                                     <div class="row info">
                                    <label class = "title d-none d-md-block">운영 시간</label>
                                       <a class="d-block d-md-none">&nbsp; &nbsp; ▶&nbsp;</a>${contents.openTime}</div>
                              
                                </div>
                            </div> 
                            
                               
                        </div>
                    </div>
                    
                    
                 

                    <%--지도, 후기--%>
                    
                    <div class=" row d-none d-md-block">
                    <div class=col-12>
                    <div >&nbsp; &nbsp; &nbsp;  </div>
                     <label>Total. </label> ${contents.viewCount}
                       
                    </div>
                    </div>
                        <div class=row>
                            <div class= col-12>
                                
                                <div class="btn-group" role="group" aria-label="Basic example">

                                    <button type="button" class="btn btn-secondary" id=btnMap>지도</button>
                                    <button type="button" class="btn btn-secondary" id=btnReview>후기</button>
                                     <button type="button" class="btn btn-secondary" id="btnModify" >정보 수정 제안</button>
                                </div>
                                <hr>
                            </div>
                        </div>


                        <div class = row id="mapBoxS d-block d-md-none">
                            <div class = "col-12" id="mapBox_col">
                                <br>
                                <div id= map_title><img src="${pageContext.request.contextPath}/resources/map_icon.png" id=map_icon>&nbsp;&nbsp;찾아가는 길</div>
                                <div class=" d-block d-md-none" id = map></div>
                                <div class="d-none d-md-block" id = map2></div>
                                <br>
                            </div>
                        </div>
                        
               
                     
                     
                        </div>
                      
	    
	    
	    
	    <div class="col d-none d-sm-block"></div>
	</div>
	
	
	<!-- 푸터-->
	<jsp:include page="../standard/footer.jsp" />
	
	</div>
<!-- container --> 
 <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9ebb4503ccfdcae7c7da7ddc3ecf4d58"></script>
                        <script>
                        
                        // map1
                            var place = [];
                            var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                            
                                mapOption = { 
                                    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                                    level: 3 // 지도의 확대 레벨
                                };
                            var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다


                            // 지도를 클릭한 위치에 표출할 마커입니다
                            var marker = new kakao.maps.Marker({ 
                                // 지도 중심좌표에 마커를 생성합니다 
                                position: map.getCenter() 
                            }); 
                            // 지도에 마커를 표시합니다
                            marker.setMap(map);

                            // 지도에 클릭 이벤트를 등록합니다
                            // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
                            kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        

                                // 클릭한 위도, 경도 정보를 가져옵니다 
                                var latlng = mouseEvent.latLng; 

                                // 마커 위치를 클릭한 위치로 옮깁니다
                                marker.setPosition(latlng);
                                //                 marker.setMap(map);
                            });
                     
                     //map2      
                            var place2 = [];
                            var mapContainer2 = document.getElementById('map2'), // 지도를 표시할 div 
                            
                                mapOption2 = { 
                                    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                                    level: 3 // 지도의 확대 레벨
                                };
                            var map2 = new kakao.maps.Map(mapContainer2, mapOption2); // 지도를 생성합니다


                            // 지도를 클릭한 위치에 표출할 마커입니다
                            var marker2 = new kakao.maps.Marker({ 
                                // 지도 중심좌표에 마커를 생성합니다 
                                position: map.getCenter() 
                            }); 
                            // 지도에 마커를 표시합니다
                            marker.setMap(map2);

                            // 지도에 클릭 이벤트를 등록합니다
                            // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
                            kakao.maps.event.addListener(map2, 'click', function(mouseEvent) {        

                                // 클릭한 위도, 경도 정보를 가져옵니다 
                                var latlng2 = mouseEvent.latLng; 

                                // 마커 위치를 클릭한 위치로 옮깁니다
                                marker.setPosition(latlng2);
                                //                 marker.setMap(map);
                            });
                            
                            //버튼
                            //1. 정보수정제안
                            
                            //2. 지도
                            $("#btnMap").on("click", function(){
                            	location.href="${pageContext.request.contextPath}/contents.s?seq=${contents.seq}";
                            })
                            //3. 후기
                            $("#btnReview").on("click", function(){
                            	location.href="${pageContext.request.contextPath}/hospitalSearchDetail2.re?seq=${contents.seq}";
                            })
                            //4. 정보수정제안
                            $("#btnModify").on("click", function(){
                            	location.href="${pageContext.request.contextPath}/member/mypageOneByOne.jsp";
                            })
                        </script>
</body>
</html>