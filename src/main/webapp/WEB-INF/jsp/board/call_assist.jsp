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
  	<jsp:param value="active" name="a9"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header" style="padding-bottom:0px;">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-3">
            <h2 class="m-0 text-dark">프로젝트 관리</h2>
          </div><!-- /.col -->
<%--          <div class="col-sm-3">--%>
<%--			<div class="info-box bg-warning">--%>
<%--			  <span class="info-box-icon"><i class="far fa-flag"></i></span>--%>
<%--			  <div class="info-box-content">--%>
<%--				  <div style="display:flex">--%>
<%--					<div style="margin-right:10px">--%>
<%--						<span class="info-box-text">금일(완료/접수)</span>--%>
<%--						<span class="info-box-number" style="text-align:center" id="countDoneSts1Today">0/0</span>--%>
<%--					</div>--%>
<%--					<div style="margin-right:10px">--%>
<%--						<span class="info-box-text">전일(지연/전체)</span>--%>
<%--						<span class="info-box-number" style="text-align:center" id="doneSts1">0/0</span>--%>
<%--					</div>--%>
<%--					<div style="margin-right:10px">--%>
<%--						<span class="info-box-text">완료</span>--%>
<%--						<span class="info-box-number" style="text-align:center" id="doneSts2">0</span>--%>
<%--					</div>--%>
<%--				</div>--%>
<%--				<div class="progress">--%>
<%--				  <div class="progress-bar" id="doneStsProgress" style="width: 0%"></div>--%>
<%--				</div>--%>
<%--				<span id="doneStsMessage" class="progress-description">전체 0건중 0% 완료</span>--%>
<%--			  </div>--%>
<%--			  <!-- /.info-box-content -->--%>
<%--			</div>--%>
<%--			<!-- /.info-box -->--%>
<%--          </div>--%>
          <div class="col-sm-6"></div>
          <div class="col-sm-3">
			<div class="info-box bg-danger">
			  <span class="info-box-icon"><i class="far fa-star"></i></span>
			  <div class="info-box-content">
				  <div style="display:flex">
					<div style="margin-right:15px">
						<span class="info-box-text">버거</span>
						<span class="info-box-number" style="text-align:center" id="reqKind0">0%</span>
					</div>
					<div style="margin-right:15px">
						<span class="info-box-text">개선</span>
						<span class="info-box-number" style="text-align:center" id="reqKind1">0%</span>
					</div>
					<div style="margin-right:15px">
						<span class="info-box-text">요구</span>
						<span class="info-box-number" style="text-align:center" id="reqKind2">0%</span>
					</div>
					<div style="margin-right:15px">
						<span class="info-box-text">문의</span>
						<span class="info-box-number" style="text-align:center" id="reqKind3">0%</span>
					</div>
					<div style="margin-right:15px">
						<span class="info-box-text">기타</span>
						<span class="info-box-number" style="text-align:center" id="reqKind4">0%</span>
					</div>
				</div>
				<div class="progress">
				  <div class="progress-bar" id="reqKindProgress" style="width: 0%"></div>
				</div>
				<span class="progress-description" id="reqKindMessage">전체 0건중 0% 버거,개선,요구 완료</span>
			  </div>
			  <!-- /.info-box-content -->
			</div>
			<!-- /.info-box -->
          </div>
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
          <form role="form" name="searchForm" method="POST" action="${_ctx }/board/call_assist.do">
            <div class="row mb-2">
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <button type="button" class="btn btn-info">필터</button>
                </div>
                <input name="searchWord" id="filter" placeholder="검색어" type="text" class="form-control">
                <div class="col-6">
                  <input name="dates" placeholder="접수일자선택" type="text" class="form-control" readOnly>
                </div>
                <input type="button" id="btnSearch" class="btn btn-primary" value="검색">
			    <input type="button" id="btnExcel" class="btn btn-success" style="margin-left:5px" value="엑셀"/>
                <input type="button" id="btnCreate" class="btn btn-danger" style="margin-left:5px" value="등록">
              </div>
              <div id="myGrid" style="height: 400px;width:100%;" class="ag-theme-alpine"></div>
            </div><!-- /.row -->
          </form>
          <!-- form end -->
          </div><!-- left column -->
        </div>

        <div id="iframeService" class="row" style="padding-left:10px"></div> 

          <div id="functionGuide" class="card card-default"> <!-- /.card-div start -->
              <div class="card-header">
                <h3 class="card-title">
                  <i class="fas fa-bullhorn"></i>
                  기능 설명
                </h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <div class="callout callout-danger">
                  <p>1. 원하는 항목을 CTRL+클릭 하시면, 상세보기(수정,삭제)로 이동합니다.</p>
                  <p>2. 검색어와 접수일자범위로 검색 가능합니다.</p>
                  <p>3. 목록을 클릭하시면, 간편 수정 가능합니다.</p>
                  <p>4. 엑셀 버튼을 클릭하시면, 표시된 목록을 다운로드 받을 수 있습니다.</p>
                  <p>5. 요청내용과 처리내용을, 보다 상세히 입력해 주시면, 개발에 많은 도움이 됩니다.</p>
                </div>
              </div>
            </div>
          </div> <!-- /.card-div end -->
        <!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content -->

  </div>
  <!-- /.content-wrapper -->
  <%@include file="../include/footer.jsp" %>
<!-- ./wrapper -->

<%@include file="../include/script.jsp" %>
</body>
</html>

<script src="https://unpkg.com/@ag-grid-enterprise/all-modules@25.0.0/dist/ag-grid-enterprise.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<script type="text/javascript" charset="utf-8">
	// LicenseModuleManager.setLicenseKey(licenseKey);

    // specify the columns
    var columnDefs = [
      {headerName: "순번", 
    	  field: "no",
    	  width: 80,
    	  getQuickFilterText: function(params) {
              return params.value;
          },
          editable: false
      },
      {headerName: "접수자", field: "reqUser", width: 100, editable: false},
      {headerName: "접수일자", field: "reqDate", width: 120, editable: false},
      {headerName: "농장명", field: "farmNm",width:180},
      {headerName: "담당자", field: "farmUser", width:100},
      {headerName: "연락처", field: "farmPhoneNo", width:140},
      {headerName: "요청내용", field: "reqContent",width:275
    	  ,editable: false
          ,cellEditor: 'agLargeTextCellEditor',
      },
      {headerName: "처리내용", field: "resContent",width:275
          ,cellEditor: 'agLargeTextCellEditor',
      },
      {headerName: "구분", field: "reqKind", width:120,
          cellEditor: 'agRichSelectCellEditor',
          cellEditorParams: {
              cellRenderer: 'genderCellRenderer',
              values: ['기능오류 및 요청', '문의', '데이터이관', '보고서', '신규가입', '견적서', '기타']
          } 
      },
      {headerName: "처리일자", field: "resDate", width:120, cellEditor: 'datePicker'},
      {headerName: "완료여부", field: "doneSts",width:120,
          cellEditor: 'agRichSelectCellEditor',
          cellEditorParams: {
              cellRenderer: 'genderCellRenderer',
              values: ['접수','완료','보류','기각']
          } 
      },
    ];

    // let the grid know which columns and what data to use
    var gridOptions = {
		defaultColDef : {
			filter : true,
			resizable : true,
			editable : false,
			sortable : true,
			singleClickEdit: true,
            suppressSizeToFit: true
		},
		columnDefs : columnDefs,
		rowSelection : 'single',
		onCellValueChanged: onCellValueChanged,
		onSelectionChanged: onSelectionChanged,
		onRowClicked: onRowClicked,
		onRowDoubleClicked: onRowDoubleClicked,
		paginationPageSize: 10,
		pagination: true,
		components: {
		    datePicker: getDatePicker(),
		},
	};

	// lookup the container we want the Grid to use
	var eGridDiv = document.querySelector('#myGrid');

	// create the grid passing in the div to use together with the columns & data we want to use
	new agGrid.Grid(eGridDiv, gridOptions);
	
	// function onRowGroupOpened(params) {
	//	console.log(params.data.id);
	//	if (params.node.expanded) {
	//		// node was expanded, do your logic
	//		this.gridDataSource.addExpandedGroupId(params.data.id);
	//	} else {
	//		// node was collapsed, so remove if present
	//		if (this.gridDataSource.hasExpandedGroupId(params.data.id)) {
	//			this.gridDataSource.removeExpandedGroupId(params.data.id);
	//		}
	//	}
	// }
	
	function getDatePicker() {
	  // function to act as a class
	  function Datepicker() {}

	  // gets called once before the renderer is used
	  Datepicker.prototype.init = function (params) {
		// create the cell
		this.eInput = document.createElement('input');
		this.eInput.value = params.value;
		this.eInput.classList.add('ag-input');
		this.eInput.style.height = '100%';

		// https://jqueryui.com/datepicker/
		$(this.eInput).datepicker({
		  dateFormat: 'yy-mm-dd',
		});
	  };

	  // gets called once when grid ready to insert the element
	  Datepicker.prototype.getGui = function () {
		return this.eInput;
	  };

	  // focus and select can be done after the gui is attached
	  Datepicker.prototype.afterGuiAttached = function () {
		this.eInput.focus();
		this.eInput.select();
	  };

	  // returns the new value after editing
	  Datepicker.prototype.getValue = function () {
		return this.eInput.value;
	  };

	  // any cleanup we need to be done here
	  Datepicker.prototype.destroy = function () {
		// but this example is simple, no cleanup, we could
		// even leave this method out as it's optional
	  };

	  // if true, then this editor will appear in a popup
	  Datepicker.prototype.isPopup = function () {
		// and we could leave this method out also, false is the default
		return false;
	  };

	  return Datepicker;
	}

	function onCellValueChanged(params) {

		let param = params.data;
		param['iuFlag'] = 'U';
		console.log(param);

		$.post('/board/call_assist_view.do',param,function() {
			$.post("/board/call_assist.do", param, function(data) {
				// 통계 그래프 갱신하기 
				if (data.stat.length > 0) {
					const stat = data.stat[0];
					drawDashboard(stat);
				}
			});
		});
	}

	function onRowClicked(params) {
		// const selectedRows = gridOptions.api.getSelectedRows();
		// const no = selectedRows[0].no;
		const ctrlKeyPressed = params.event.ctrlKey;
		if(!ctrlKeyPressed && isIE()) { 
			// (원인) MS8, 11에서는 현재 flexbox css가 지원되지 않고 있습니다.
			return alert('크롬이나 마이크로 소프트 엣지를 사용해 주세요.\n또는 CTRL+클릭을 사용하세요.'); 
		};
		if(!ctrlKeyPressed) return;
		const no = params.data.no;
		if (no) {
			location.href = '/board/call_assist_view.do?no=' + no;
		}
			
		// console.log(no, params.event.ctrlKey,
		//		this, params, params.data);
		// params.node.expanded = true;
		// params.node.rowHeight = 400;
		// params.context.setExpand.onExpandClicked(params.rowIndex,
		// 		params.node.rowHeight);
	}

	function onRowDoubleClicked() {
		const selectedRows = gridOptions.api.getSelectedRows();
		if (selectedRows.length === 1) {
			const no = selectedRows[0].no;
			// location.href = '/board/call_assist_view.do?no=' + no;
		}
	}
	
	function isIE() {
		const ua = window.navigator.userAgent;
		const msie = ua.indexOf('MSIE ');
		if (msie > 0) {
			return parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
		}
		const trident = ua.indexOf('Trident/');
		if (trident > 0) {
	        var rv = ua.indexOf('rv:');
	        return parseInt(ua.substring(rv + 3, ua.indexOf('.', rv)), 10);
	    }	
	}

	function onSelectionChanged() {
		if(isIE()) return;
		const selectedRows = gridOptions.api.getSelectedRows();
		if (selectedRows.length === 1) {
			const no = selectedRows[0].no;
			$('#iframeService').children().remove();
			$.get('/board/call_assist_inner.do?no='+no,function(html) {
				$('#iframeService').append(html);
			})
		}
	}

	$('#filter').on('input', function(e) {
		const value = e.target.value;
		gridOptions.api.setQuickFilter(value);
	})

	$('#btnSearch').click(function(e) {
		e.preventDefault();
		const searchWord = $('input[name=searchWord]').val();
		const dates = $('input[name=dates]').val();
		const param = {
			'searchWord' : searchWord,
			'dates' : dates
		};
		$.post("/board/call_assist.do", param, function(data) {
			gridOptions.api.setRowData(data.rows);
		});
	});
	
	function getToday(){
	    const date = new Date();
	    const year = date.getFullYear();
	    const month = ("0" + (1 + date.getMonth())).slice(-2);
	    const day = ("0" + date.getDate()).slice(-2);
	    return year + "-" + month + "-" + day;
	}

	$('#btnExcel').click(function(e) {
	    const fileName = '피그플랜v3.0_고객대응-'+getToday()+".xlsx";
	    const sheetName = 'Sheet';
	    const params = {
	        columnWidth: 120,
	        fileName: fileName,
	        sheetName: sheetName,
	        suppressTextAsCDATA: true,
	        rowHeight: 20,
	    };
		gridOptions.api.exportDataAsExcel(params);	
	});

	$('#btnCreate').click(function(e) {
		location.href = '/board/call_assist_view.do';
	});

	function initDateRangePicker() {
		$('input[name="dates"]').daterangepicker(
				{
					autoUpdateInput : false,
					locale : {
						format : "YYYY-MM-DD",
						cancelLabel : '초기화',
						applyLabel : '확인',
						daysOfWeek : [ "일", "월", "화", "수", "목", "금", "토" ],
						monthNames : [ "1월", "2월", "3월", "4월", "5월", "6월",
								"7월", "8월", "9월", "10월", "11월", "12월" ],
					},
				});
		$('input[name="dates"]').unbind('apply.daterangepicker');
		$('input[name="dates"]').unbind('cancel.daterangepicker');
		$('input[name="dates"]').on(
				'apply.daterangepicker',
				function(e, picker) {
					const dateFormat = 'YYYY-MM-DD';
					$(this).val(
							picker.startDate.format(dateFormat) + '~'
									+ picker.endDate.format(dateFormat));
				});
		$('input[name="dates"]').on('cancel.daterangepicker',
				function(e, picker) {
					initDateRangePicker();
					$(this).val('');
				});
	}

	function drawDashboard(stat) {
		const total = stat.total;
		const countDoneSts1Today = stat.countDoneSts1Today;
		const totalDoneSts1Today = stat.totalDoneSts1Today;
		const countDoneSts1 = stat.countDoneSts1;
		const countDoneSts2 = stat.countDoneSts2;
		const countDoneSts3 = stat.countDoneSts3;
		const percentDoneSts2 = stat.percentDoneSts2;

		$('#countDoneSts1Today').text(countDoneSts1Today+'/'+totalDoneSts1Today);
		$('#doneSts1').text(countDoneSts3+'/'+total);
		$('#doneSts2').text(countDoneSts2);
		$('#doneStsProgress').attr('style', 'width:' + percentDoneSts2 + '%');
		$('#doneStsMessage').text(
				'전체 ' + total + '건중 ' + percentDoneSts2 + '% 완료');

		const percentKind1 = stat.percentKind1;
		const percentKind2 = stat.percentKind2;
		const percentKind3 = stat.percentKind3;
		const percentKind4 = stat.percentKind4;
		const percentKind5 = stat.percentKind5;
		const percentKind6 = stat.percentKind6;
		const percentKind7 = stat.percentKind7;

		$('#reqKind0').text(percentKind1 + '%');
		$('#reqKind1').text(percentKind2 + '%');
		$('#reqKind2').text(percentKind3 + '%');
		$('#reqKind3').text(percentKind4 + '%');
		$('#reqKind4').text(percentKind5 + '%');
		$('#reqKindProgress').attr('style', 'width:' + percentKind1 + '%');
		$('#reqKindMessage').text(
				'전체 ' + total + '건중 ' + percentKind1 + '% 오류 및 요청');
	}
	
	var tid = null, timeOut=1000*60*10; // 10분 주기 자동 갱신
	function redrawGridAndStat() {
		const param = {};
		$.post("/board/call_assist.do", param, function(data) {
			// console.log(data);
			gridOptions.api.setRowData(data.rows);

			// 통계 그래프 그리기
			if (data.stat.length > 0) {
				const stat = data.stat[0];
				drawDashboard(stat);
			}
			if(tid) clearInterval(tid);
			tid = setInterval(redrawGridAndStat,timeOut);
		});
	}

	$(document).ready(function() {
		redrawGridAndStat();
		initDateRangePicker();
	});
</script>
