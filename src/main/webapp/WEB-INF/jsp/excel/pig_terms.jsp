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
  	<jsp:param value="active" name="s3"/>
  	<jsp:param value="active" name="a8"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">양돈 용어집 - 심층분석 보고서</h1>
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
          <div class="row mb-2">
            <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <button type="button" class="btn btn-info">검색</button>
                    </div>
                    <input id="filter" placeholder="용어 및 설명" type="text" class="form-control">
            </div>
            <div id="myGrid" style="height: 660px;width:100%;" class="ag-theme-alpine"></div>
          </div><!-- /.row -->
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
      {headerName: "분류", 
    	  field: "cate",
    	  getQuickFilterText: function(params) {
              return params.value;
          },
          width: '120 px',
      },
      {headerName: "용어", field: "term", width: '200 px', },
      {headerName: "설명", field: "explain", width: '1200 px'},
    ];

    // let the grid know which columns and what data to use
    var gridOptions = {
		defaultColDef : {
			filter : true,
			resizable : true,
			editable : false,
			sortable : true,
            suppressSizeToFit: false
		},
		columnDefs : columnDefs,
		rowSelection : 'single',
		onSelectionChanged: onSelectionChanged,
		onGridSizeChanged: onGridSizeChanged,
		paginationPageSize: 20,
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
	
	function onGridSizeChanged(e) {
		e.api.sizeColumnsToFit();
	}
	
	$('#filter').on('input', function(e) {
		const value = e.target.value;
		gridOptions.api.setQuickFilter(value);
	})

	$(document).ready(function() {
        agGrid.simpleHttpRequest({
            url : '/search/pig_terms.do'
        }).then(function(data) {
            gridOptions.api.setRowData(data);
        });
	});
</script>