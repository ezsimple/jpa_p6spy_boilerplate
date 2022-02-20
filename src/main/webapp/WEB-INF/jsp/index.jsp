<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<%@include file="include/meta.jsp" %>
<meta http-equiv="refresh" content="0; url=/board/call_assist.do"></meta>
</head>

<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <jsp:include page="include/nav.jsp">
  	<jsp:param value="" name="s0"/>
  	<jsp:param value="" name="s1"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">시작하면서 ... </h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">시작하면서</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="content">
      <div class="container-fluid">
        <div class="row">
        이 프로그램은 피그플랜 운영팀을 위해 제작 되었습니다.
        </div>
        <div class="row">
	    현재 <c:out value="${_browser }" /> 브라우저로 접속하셨습니다.
        </div>
        <c:if test='${fn:startsWith(_browser, "Internet Explorer") }'>
			<div class="row">
			인터넷 익스플로러를 사용하실 경우, 오류발생시 원인이 출력되지 않습니다. <br>
			- 웹 사이트에서 페이지를 표시할 수 없습니다. 로 출력 됩니다. - 
			</div>
        </c:if>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->

  </div>
  <!-- /.content-wrapper -->
  <%@include file="include/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@include file="include/script.jsp" %>
</body>
</html>