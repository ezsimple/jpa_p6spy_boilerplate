<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
  <title>${statusCode } Error</title>
  <%@include file="../include/meta.jsp" %>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
  <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
  <link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-alpine.css">
  <style>
	.blink {
    animation-duration: 0.5s;
    animation-name: blink;
    animation-iteration-count: infinite;
    animation-direction: alternate;
    animation-timing-function: ease-in-out;
	}
	@keyframes blink {
		from {
			opacity: 1;
		}
		to {
			opacity: 0;
		}
	} 
  </style>
</head>

<c:set var="_ctx" value="${pageContext.request.contextPath }"/>
<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <jsp:include page="../include/nav.jsp"></jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>${statusCode } Error Page</h1>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item active">${statusCode } Error Page</li>
            </ol>
          </div>
        </div>
      </div><!-- /.container-fluid -->
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="error-page">
        <h2 class="headline text-danger"><p class="blink">${statusCode }</p></h2>

        <div class="error-content">
          <h3><i class="fas fa-exclamation-triangle text-danger"></i>
          ${errTitle }
          </h3>
          <p>${errMessage }</p>
        </div>
      </div>
      <!-- /.error-page -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <%@include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@include file="../include/script.jsp" %>
</body>
</html>
