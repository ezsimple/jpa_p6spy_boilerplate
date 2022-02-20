<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="_ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html lang="ko">

<head>
<%@include file="../include/meta.jsp" %>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-grid.css">
<link rel="stylesheet" href="https://unpkg.com/ag-grid-community/dist/styles/ag-theme-alpine.css">
</head>

<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <jsp:include page="../include/nav.jsp">
  	<jsp:param value="active" name="s0"/>
  	<jsp:param value="active" name="a0"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">매주 월요일 육종가 업로드</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="${_ctx }/dl.do?key=${_key }&fid=1" target="_new">육종가_양식</a></li>
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
          <div class="col-md-12">

          <!-- form start -->
          <form role="form" name="searchForm" method="POST" action="${_ctx }/excel/yukjongga.do" enctype="multipart/form-data">
          	<input type="hidden" name="fid" value="1" />
            <div class="row mb-2">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <button type="button" class="btn btn-info">년도별주차</button>
                    </div>
                    <input id="weekDates" name="weekDates" placeholder="년도별주차" type="text" class="form-control" maxlength="6">
                    <div class="col-6">
                      <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="uploadFile"
                        accept=".csv">
                        <label class="custom-file-label" for="uploadFile">파일을 선택하세요</label>
                      </div>
                    </div>
                    <button type="submit" id="btnSubmit" class="btn btn-primary">확인</button>
                </div>
            </div><!-- /.row -->
          </form>
          <input type="hidden" name="maxWeekDates" />

          <div class="card card-default"> <!-- /.card-div start -->
              <div class="card-header">
                <h3 class="card-title">
                  <i class="fas fa-bullhorn"></i>
                  기능 설명
                </h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <div class="callout callout-danger">
                  <h5>주의</h5>
                  <p>1. 대용량 CSV파일 업로드 및 데이터 마이그레이션 작업입니다.</p>
                  <p>2. 육종가_양식 파일과 형식이 다른 CSV 파일은 등록되지 않습니다.
                  <img src="/static/images/yukjonggaHelp.png" />
                  </p>
                  <p>3. 실행시 시스템(CPU/ORACLE) 사용율이 급격히 증가 됩니다.</p>
                  <p>4. 대략 80M 업로드 자료(CSV) 기준, 50분 이상 처리시간이 소요 됩니다.</p>
                  <p>5. 등록 오류(무결성 오류) 발생시, 직접 처리 하여야 합니다.</p>
                </div>
              </div>
            </div>
          </div> <!-- /.card-div end -->
          
          <!-- form end -->
          </div><!-- left column -->

		  <div class="progress">
			<div class="progress-bar progress-bar-striped" style="min-width: 0px;"></div>
		  </div>
        
        </div><!-- /.row -->
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

<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" charset="utf-8">

	$('#btnSubmit').click(function(e) {
        e.preventDefault();

		let weekDates = $('input[name=weekDates]').val();
		if(!weekDates || weekDates === '') {
			alert('요청할 년도별주차를 입력하세요');
			return false;
		}
        let maxWeekDates = $('input[name=maxWeekDates]').val();
        if(weekDates < maxWeekDates) {
			alert('요청 년도별주차가 작습니다. 최종 : '+maxWeekDates);
			return false;
        }

		let uploadFile = $('#uploadFile')[0].files.length;
		if(uploadFile == 0) {
			alert('업로드 파일을 선택하세요');
			return false;
		}

        let $form = $('form[name=searchForm]');
        $form.submit();
		makeProgress();
	});
	
	function getMaxWeekDates() {
        $.getJSON('/excel/yukjongga/getMaxWeekDates.do',function(data) {
            let curMaxWeekDates = data.maxWeek;
            $('#weekDates')
            	.attr('placeholder', curMaxWeekDates)
            	.val('')
            	.focus().blur();
            $('input[name=maxWeekDates]').val(curMaxWeekDates);
        });
	}
	
	var i = 0;
    function makeProgress(){
        if(i < 100){
            i = i + 1;
            $(".progress-bar").css("width", i + "%").text(i + " %");
        }
        // Wait for sometime before running this script again
        setTimeout("makeProgress()", 30000); // 전체 50분 처리 기준 타이머 : ((50*60*1000)/100)
    }

	$(document).ready(function() {
		getMaxWeekDates();
	});
</script>