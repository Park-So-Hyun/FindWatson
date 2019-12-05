<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>찾아줘 왓슨!</title>
        <script src="https://code.jquery.com/jquery-3.4.1.js"></script>
        <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700,900&display=swap&subset=korean" rel="stylesheet">
        <script>
            $(function() {

                var IMP = window.IMP;
                IMP.init('imp75189241');

                IMP.request_pay(
                    {
                        pg : 'html5_inicis',
                        pay_method : "${dto.payMethod}",
                        merchant_uid : "${dto.donateId}",
                        name : "${dto.support} 원 후원하기",
                        amount : "${dto.support}",
                        buyer_email : "${dto.email}",
                        buyer_name : "${dto.name}",
                        buyer_tel : "${dto.phone}",
                        buyer_addr : "${dto.addr1}",
                        buyer_postcode : "${dto.postcode}"
                    }, function(rsp) {
                        if (rsp.success) { //여기서 결제 성공

                            $.ajax(
                                {//여기서부터는 결제 성공후 결제 정보 확인
                                    url : "${pageContext.request.contextPath}/payComplete.do",
                                    method : "post", // POST method
                                    dataType : 'json',
                                    data : {
                                        merchant_uid : rsp.merchant_uid,
                                        paid_amount : rsp.paid_amount,
                                        success : rsp.success,
                                        name : rsp.buyer_name
                                    }
                                })
                                .done(function(data) {
                                alert("결제 성공");
                                location.href = "${pageContext.request.contextPath}/toSuccessView.do?name="+data.name+"&id="+data.id;
                            });

                        } else {//결제 실패
                            $.ajax({
                                url : "${pageContext.request.contextPath}/payComplete.do",
                                method : "post", // POST method
                                dataType : 'json',
                                data : {
                                    merchant_uid : rsp.merchant_uid,
                                    paid_amount : rsp.paid_amount,
                                    success : rsp.success
                                }
                            }).done(function(data) {
                                var msg = '결제에 실패하였습니다.';
                                msg += ' '+ rsp.error_msg;
                                alert(msg);
                                location.href = "donate/donateFailView.jsp";
                            });
                        }
                    });
            });
        </script>
        <style>
/*            div{border: 1px solid black;}*/
/*
            #lodingBird{
                position: absolute;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%);
            }
*/
            * {
                box-sizing: border-box;
                font-family: 'Noto Sans KR', sans-serif;
            }
            .line{border: 1px solid lightgray;}
        </style>
    </head>
    <body>
	<div class="container col-12">
        <div class="row mt-5">
            <div class="col d-none d-sm-block"></div>
                <div class="contents col-6 line">
                   <div class="row">
                        <img src="${pageContext.request.contextPath}/imgs/donate/loadingBird.gif" alt="엑박" id="lodingBird" class="rounded mx-auto d-block">
                    </div>                   
                    <div class="row mb-2">
                        <div class="errorNotice col-12 text-center mt-5">
                            <p>혹시 화면이 멈췄다면 돌아가서 결제를 다시 시도해주세요</p>
                            <p>문제 반복시 관리자에게 연락 바랍니다</p>
                            <a role="button" class="btn btn-outline-secondary" href="javascript:history.go(-4)">BACK</a>
                        </div>
                    </div>
                </div>
            <div class="col d-none d-sm-block"></div>
        </div>
	</div>
    </body>
</html>