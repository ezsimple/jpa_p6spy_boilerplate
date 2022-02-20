<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="_ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="ko">

<head>
<%@include file="../include/meta.jsp" %>
</head>

<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <jsp:include page="../include/nav.jsp">
  	<jsp:param value="active" name="s0"/>
  	<jsp:param value="active" name="a1"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">매달 시세 업로드</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="${_ctx }/dl.do?key=${_key }&fid=2" target="_new">샘플포맷파일 다운로드</a></li>
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
          <!-- left column -->
          <div class="col-md-6">
          <!-- form start -->
          <form role="form" method="POST" action="${_ctx }/excel/sisae.do" enctype="multipart/form-data">
            <div class="card-body">
              <div class="form-group">
                <div class="input-group">
                  <div class="custom-file">
                  	<input type="hidden" name="fid" value="2" />
                    <input type="file" name="file" class="custom-file-input" id="uploadFile"
                    accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                    <label class="custom-file-label" for="uploadFile">파일을 선택하세요</label>
                  </div>
                </div>
              </div>
              <button type="submit" class="btn btn-primary">Submit</button>
            </div>
          </form>
          <!-- form end -->
          </div><!-- left column -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->

  </div>
  <!-- /.content-wrapper -->
  <%@include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@include file="../include/script.jsp" %>
</body>
</html>