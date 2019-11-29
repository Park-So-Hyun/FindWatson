<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
        <title>찾아줘 왓슨!</title>
       <%--부트 스트랩 --%>
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

		<script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


        <style>
            *{box-sizing: border-box}
            div{border: 1px solid black}
            #logo{
                width: 500px;
                height: 100px;
                margin: auto;
                background-color: ghostwhite;
            }
            #article-img{
                width:100%;
                height: 100px;
                margin: auto;
                background-color: ghostwhite;
            }
            #footer{
                height: 100px;
            }
            img{
                width:500px;
            }
            a{
                color:black;
            }
            #here{
                color:red;
            }
        </style>
</head>
<body>
 <div class="container">
            <div class="row">
                <div id="loginBtn" class="col-12 text-right">
                <form action="login.jsp" method="post" id=frm>
                    <button class="btn btn-sm btn-outline-secondary">로그인</button></form>
                </div>
            </div>
            <div class="row">
                <div id="title" class="col-12 mb-3">
                    <div id="logo">로고이미지</div>
                </div>
            </div>
            <div class="row">
                <div id="navi" class="col-12 mb-3">
                    <nav class="navbar navbar-expand-lg navbar-light bg-light">
                        <a class="navbar-brand" href="#">Home</a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <a class="nav-link" href="#">공지사항</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">병원검색</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">전문가Q&A</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">커뮤니티</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">마이페이지</a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>
            <!--            -->
            <div class=row>
                <div class=col-12>
                    <h1>마이 페이지</h1>
                    <hr>
                </div>
            </div>

            <div class=row><!--좌측 네비바-->
                <div class=col-3>
                    <ul>
                        <br>
                        <li><a href="#">내 정보 보기</a></li>
                        <br>
                        <li><a href="mypageModify.jsp">내 정보 수정</a></li>
                        <br>
                        <li><a href="mypageOneByOne.jsp">1:1 문의</a></li>
                        <br>
                        <li><a href="mypageWithdrawal.jsp">회원탈퇴</a></li>
                        <br>
                    </ul>    
                </div>
                <div class=col-9><!--우측공간 시작-->
                    <!--                   -->
                    <div class =row>
                        <div class= col-12>
                            <h5>아이디</h5>
                            ${dto.id}
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>이름</h5>
                            ${dto.name}
                        </div>
                        <div class=col-12>
                            <div id=nameCheck></div>
                        </div>
                    </div>

                    <div class =row>
                        <div class= "col-12">
                            <h5>생년월일</h5>
                            ${dto.birth}
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>성별</h5>
                            ${dto.gender}
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>본인 확인 이메일</h5>
                            ${dto.email}
                        </div>
                    </div>

                    <div class =row>
                        <div class= col-12>
                            <h5>휴대폰 번호</h5>
                            ${dto.phone}
                        </div>
                    </div>

                    <div class=row>
                        <div class= col-12>
                            <h5>주소</h5>
                            ${dto.postcode}
                            ${dto.address1}
                            ${dto.address2}
                        </div>
                    </div>

                    <div class= row>
                        <div class=col-12>
                            <h5>관심 동물</h5>
                             ${dto.lovePet}
                        </div>
                    </div>
                    <!--               -->
                </div><!--우측공간 -->
            </div>
            <!--            -->
            <div class="row">
                <div id="footer" class="col-12">
                    푸터
                </div>
            </div>
        </div>
</body>
</html>