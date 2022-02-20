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
  	<jsp:param value="active" name="a4"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">검정자료 IMF값 업로드</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right"> 
              <li class="breadcrumb-item"><a href="${_ctx }/dl.do?key=${_key }&fid=5" target="_new">검정자료_IMF_양식</a></li>
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
          <form role="form" name="searchForm" method="POST" action="${_ctx }/excel/gumjungzaryu_imf.do" enctype="multipart/form-data">
            <input type="hidden" name="fid" value="5" />
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
                      <div class="custom-file">
                        <input type="file" name="file" class="custom-file-input" id="uploadFile"
                        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                        <label class="custom-file-label" for="uploadFile">파일을 선택하세요</label>
                      </div>
                    </div>
                    <button type="submit" id="btnSubmit" class="btn btn-primary">확인</button>
                </div>
                <div id="myGrid" style="height: 300px;width:100%;" class="ag-theme-alpine"></div>
            </div><!-- /.row -->
          </form>
          
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
                  <p><img src="/static/images/gumjungzaryuHelp.png" /></p>
                  <p>1. 검정자료_IMF_양식 파일과 형식이 다른 Excel 데이터는 업로드 되지 않습니다.</p>
                  <p>2. 업데이트 요청 농장을 선택하세요.</p>
                  <p>3. 업로드할 엑셀 파일을 선택하세요.</p>
                  <p>4. 확인을 누르면 선택한 농장의 검정자료가 업데이트 됩니다.</p>
                </div>
              </div>
            </div>
          </div> <!-- /.card-div end -->

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

	$('#btnSubmit').click(function(e) {
        e.preventDefault();

		let farmNo = $('input[name=farmNo]').val();
		if(!farmNo || farmNo === '') {
			alert('농장을 선택하세요');
			return false;
		}

		let uploadFile = $('#uploadFile')[0].files.length;
		if(uploadFile == 0) {
			alert('업로드 파일을 선택하세요');
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
	});
</script>