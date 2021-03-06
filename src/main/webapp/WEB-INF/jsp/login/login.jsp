<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../include/meta.jsp" %>
</head>
<body class="hold-transition login-page">

<div class="login-box">
    <div class="login-logo">
        <a href="/">my<b>PMS</b></a>
    </div>
    <!-- /.login-logo -->
    <div class="card">
        <div class="card-body login-card-body">

            <%-- <p class="login-box-msg">Sign in to start your session</p>--%>
            <form id="loginForm" action="/api/v1/auth/login" method="post">
                <div class="input-group mb-3">
                    <input type="email" class="form-control" placeholder="Email" name="username" value="${username }">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fas fa-envelope"></span>
                        </div>
                    </div>
                </div>
                <div class="input-group mb-3">
                    <input type="password" class="form-control" placeholder="Password" name="password" value="${password }">
                    <div class="input-group-append">
                        <div class="input-group-text">
                            <span class="fas fa-lock"></span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-8"></div>
                    <!-- /.col -->
                    <div class="col-4">
                        <button type="submit" class="btn btn-primary btn-block">로그인</button>
                    </div>
                    <!-- /.col -->
                </div>
            </form>

            <style>
                .social-box {
                    border: 1px darkslateblue solid; text-align: left; padding-left: 70px;
                }
            </style>
            <div class="social-auth-links text-center mb-3">
                <p></p>
                <a href="#" class="btn btn-block social-box">
                    <i class="fab fa-google-plus mr-2"></i> Sign in using Google+
                </a>
                <a href="#" class="btn btn-block social-box" >
                    <i class="fab fa-facebook mr-2" ></i> Sign in using Facebook
                </a>
                <a href="#" class="btn btn-block social-box">
                    <i class="fab fa-github mr-2"></i> Sign in using Github
                </a>
            </div>
            <!-- /.social-auth-links -->

        </div>
        <!-- /.login-card-body -->
    </div>
</div>
<!-- /.login-box -->

<%@include file="../include/script.jsp" %>
</body>
</html>
<script type="application/javascript">
   const $submit = $('#loginForm button[type=submit]');
   $submit.click(function(e){
       e.preventDefault();
       $submit.prop("disabled", true);

       const data = $('#loginForm').serializeObject();
       $.ajax({
           url: '/api/v1/auth/login',
           type: "POST",
           data: data,
           dataType: "json",
           success: function(res){
               const { token } = res.body;
               if(token) {
                    sessionStorage.setItem('token', token);
                    location.href="/";
               }
           },
           complete: function() {
               $submit.prop("disabled", false);
           }
       });
   });

</script>