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
  	<jsp:param value="active" name="s1"/>
  	<jsp:param value="active" name="a6"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">자돈등기 파일 출력</h1>
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
          <form role="form" name="searchForm" method="POST" action="${_ctx }/excel/jadondunggi_print.do">
          	<input type="hidden" name="farmNo" />
          	<input type="hidden" name="farmName" />
          	<input type="hidden" name="company" />
            <div class="row mb-2">
                <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <button type="button" class="btn btn-info">검색</button>
                        </div>
                        <input id="filter" placeholder="농장명 또는 농장번호" type="text" class="form-control">
                        <div class="col-6">
                            <input name="dates" placeholder="날짜 선택" type="text" class="form-control" readOnly>
                        </div>
                        <button type="submit" id="btnPrint" class="btn btn-primary">출력하기</button>
                </div>
                <div id="myGrid" style="height: 300px;width:100%;" class="ag-theme-alpine"></div>
            </div><!-- /.row -->
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

<script src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.min.noStyle.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" charset="utf-8">
    // specify the columns
    var columnDefs = [
      {headerName: "농장번호", 
    	  field: "farmNo",
    	  getQuickFilterText: function(params) {
              return params.value;
          }
      },
      {headerName: "농장명", field: "farmname"},
      {headerName: "COMPANY", field: "company"},
      {headerName: "TEL", field: "tel"},
      {headerName: "FAX_TEL", field: "faxTel"},
      {headerName: "HP_TEL", field: "hpTel"},
      {headerName: "MAIN_TEL", field: "mainTel"},
    ];

    // let the grid know which columns and what data to use
    var gridOptions = {
		defaultColDef : {
			filter : true,
			resizable : true,
			editable : false,
			sortable : true,
            suppressSizeToFit: true
		},
		columnDefs : columnDefs,
		rowSelection : 'single',
		onSelectionChanged: onSelectionChanged,
		paginationPageSize: 10,
		pagination: true,
	};

	// lookup the container we want the Grid to use
	var eGridDiv = document.querySelector('#myGrid');

	// create the grid passing in the div to use together with the columns & data we want to use
	new agGrid.Grid(eGridDiv, gridOptions);
	
	function onSelectionChanged() {
		const selectedRows = gridOptions.api.getSelectedRows();
		console.log(selectedRows[0]);
		if(selectedRows.length === 1) {
            $('input[name=farmNo]').val(selectedRows[0].farmNo);
            $('input[name=farmName]').val(selectedRows[0].farmname);
            $('input[name=company]').val(selectedRows[0].company);
		}
	}
	
	$('#filter').on('input', function(e) {
		const value = e.target.value;
		gridOptions.api.setQuickFilter(value);
	})

	$('#btnPrint').click(function(e) {
        e.preventDefault();
		let farmNo = $('input[name=farmNo]').val();
		let dates = $('input[name=dates]').val();
		if(!farmNo || farmNo === '') {
			alert('농장을 선택하세요');
			return false;
		}
		if(!dates || dates === '') {
			alert('날짜를 선택하세요');
			return false;
		}
        let $form = $('form[name=searchForm]');
        $form.submit();
	});

	$(document).ready(function() {
        agGrid.simpleHttpRequest({
            url : '/select/farm.do'
        }).then(function(data) {
            gridOptions.api.setRowData(data);
        });
        $('input[name="dates"]').daterangepicker({
        	autoUpdateInput: false,
            locale: {
            	format: "YYYY-MM-DD",
                cancelLabel: '취소',
                applyLabel: '확인',
                daysOfWeek: [
					"일",
					"월",
					"화",
					"수",
					"목",
					"금",
					"토"
                ],
				monthNames: [
					"1월",
					"2월",
					"3월",
					"4월",
					"5월",
					"6월",
					"7월",
					"8월",
					"9월",
					"10월",
					"11월",
					"12월"
                ],
            },
        });
        $('input[name="dates"]').on('apply.daterangepicker', function(e, picker) {
        	const dateFormat = 'YYYY-MM-DD';
            $(this).val(picker.startDate.format(dateFormat) + '~' + picker.endDate.format(dateFormat));
        });
        $('input[name="dates"]').on('cancel.daterangepicker', function(e, picker) {
            $(this).val('');
        });
	});
</script>