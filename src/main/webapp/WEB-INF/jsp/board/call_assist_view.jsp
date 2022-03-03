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

<c:set var="isNew" value="${view.no==null }"/>

<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <jsp:include page="../include/nav.jsp">
  	<jsp:param value="active" name="a9"/>
  </jsp:include>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h2 class="m-0 text-dark">피그플랜v3.0 고객대응</h2>
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
          <div class="col-md-9 col-sm-9">
          <!-- form start -->
          
            <div class="card ${isNew?'card-danger':'card-primary' }">
              <div class="card-header">
                <h3 class="card-title">${isNew?'신규등록':'문의사항' }
                <c:if test="${!isNew}">
                No. ${view.no }
                </c:if>
                </h3>
                <h3 class="card-title" style="float:right !important">
                  <c:if test="${!isNew && (view.prevNo!=null && view.prevNo!='')}">
                  	<a href="#" id="btnPrev" alt="이전 상세 보기"> ◀ </a>
                  </c:if>
                  <c:if test="${!isNew && (view.nextNo!=null && view.nextNo!='')}">
                  	<a href="#" id="btnNext" alt="다음 상세 보기"> ▶ </a>
                  </c:if>
                </h3>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <form role="form" name="viewForm" action="/board/call_assist_view.do" method="POST">
                  <input type="hidden" name="iuFlag" value="${isNew?'I':'U' }" />
                  <input type="hidden" name="no" value="${view.no }" />
                  <div class="row">
                    <div class="col-sm-3">
                      <!-- text input -->
                      <div class="form-group">
                        <label>요청일자</label>
                        <div class="input-group">
                    	  <div class="input-group-prepend" style="cursor:pointer" onClick="setToday('reqDate')">
                      		  <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                    	  </div>
                    	  <input type="text" name="reqDate" class="form-control datemask" data-inputmask-alias="datetime" data-inputmask-inputformat="yyyy-mm-dd" data-mask="" im-insert="false"
                    	  	value="${view.reqDate }">
                  		</div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>접수자</label>
                        <div class="input-group">
                    	  <div class="input-group-prepend" style="cursor:pointer" onClick="setUser('reqUser')">
                      		  <span class="input-group-text"><i class="far fa-user"></i></span>
                    	  </div>
                          <input type="text" name="reqUser" value="${view.reqUser }" class="form-control" placeholder="상담자명" >
                  		</div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <!-- text input -->
                      <div class="form-group">
                        <label>농장명</label>
                        <input type="text" name="farmNm" value="${view.farmNm }" class="form-control" placeholder="농장명">
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>담당자명</label>
                        <input type="text" name="farmUser" value="${view.farmUser }" class="form-control" placeholder="농장요청자명" >
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-sm-3">
                      <!-- text input -->
                      <div class="form-group">
                        <label>연락처</label>
                        <div class="input-group">
                          <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-phone"></i></span>
                          </div>
                          <input type="text" name="farmPhoneNo" value="${view.farmPhoneNo }" class="form-control phone" data-inputmask="'mask': ['99[9]-999[9]-9999', '010-999[9]-9999']" data-mask>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>요청구분</label>
                        <select name="reqKind" class="form-control" }">
                          <option value="기능오류 및 요청" <c:if test="${view.reqKind == null || view.reqKind == '기능오류 및 요청'}">selected</c:if> >기능오류 및 요청</option>
                          <option value="문의" <c:if test="${view.reqKind == '문의' }">selected</c:if> >문의</option>
                          <option value="데이터이관" <c:if test="${view.reqKind == '데이터이관' }">selected</c:if> >데이터이관</option>
                          <option value="보고서" <c:if test="${view.reqKind == '보고서' }">selected</c:if> >보고서</option>
                          <option value="신규가입" <c:if test="${view.reqKind == '신규가입' }">selected</c:if> >신규가입</option>
                          <option value="견적서" <c:if test="${view.reqKind == '견적서' }">selected</c:if> >견적서</option>
                          <option value="기타" <c:if test="${view.reqKind == '기타' }">selected</c:if> >기타</option>

                        </select>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>처리일자</label>
                        <div class="input-group">
                    	  <div class="input-group-prepend" style="cursor:pointer" onClick="setToday('resDate')">
                      		  <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                    	  </div>
                    	  <input type="text" name="resDate" value="${view.resDate }" class="form-control datemask" data-inputmask-alias="datetime" data-inputmask-inputformat="yyyy-mm-dd" data-mask="" im-insert="false">
                  		</div>
                      </div>
                    </div>
                    <div class="col-sm-3">
                      <div class="form-group">
                        <label>완료여부</label>
                        <select name="doneSts" class="form-control" >
                          <option value="접수" <c:if test="${view.doneSts == '' || view.doneSts == '접수' }">selected</c:if> >접수</option>
                          <option value="완료" <c:if test="${view.doneSts == '완료' }">selected</c:if> >완료</option>
                          <option value="보류" <c:if test="${view.doneSts == '보류' }">selected</c:if> >보류</option>
                          <option value="기각" <c:if test="${view.doneSts == '기각' }">selected</c:if> >기각</option>
                        </select>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-sm-6">
                      <!-- textarea -->
                      <div class="form-group">
                        <label>요청내용</label>
                        <textarea name="reqContent" class="form-control" rows="5" placeholder="요구사항을 입력하세요">${view.reqContent }</textarea>
                      </div>
                    </div>
                    <div class="col-sm-6">
                      <div class="form-group">
                        <label>처리내용</label>
                        <textarea name="resContent" class="form-control" rows="5" placeholder="처리 및 조치 내용을 입력하세요">${view.resContent }</textarea>
                      </div>
                    </div>
                  </div>

			  	  <div class="card-footer" style="background-color:#fff">
			        <input type="button" id="btnSave" class="btn btn-primary" value="저장"/>
			        <c:if test="${!isNew }">
			          <input type="button" class="btn btn-danger" style="background-color:gray; border-color:gray;" value="삭제"
			          data-toggle="modal" data-target="#modal-danger" />
			          <input type="button" id="btnNew" class="btn btn-danger" value="등록"/>
			        </c:if>
			        <input type="button" id="btnList" class="btn btn-success" value="목록"/>
			      </div>

                </form>
              </div>
              <!-- /.card-body -->
            </div>
          
            <!-- form end -->
          
          </div><!-- left column -->
        </div>
        <!-- /.row -->
      </div><!-- /.container-fluid -->
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
			  <p>1. 탭키로 다음 항목 이동할 수 있습니다.</p>
			  <p>2. 엔터키로 저장할 수 있습니다.</p>
			  <p>3. 달력 아이콘을 클릭하면 오늘 날짜가 나옵니다.</p>
			  <p>4. CTRL + ← 이전 상세 화면, CTRL + → 다음 상세 화면 출력 됩니다.</p>
			  <p>5. 저장, 삭제시 이중 클릭 방지 됩니다.</p>
			</div>
		  </div>
		</div>
	  </div> <!-- /.card-div end -->
    </div>
    <!-- /.content -->

  </div>
  <!-- /.content-wrapper -->
  <%@include file="../include/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@include file="../include/script.jsp" %>
</body>

<!-- 모달 레이어 -->
<div class="modal fade" id="modal-danger">
	<div class="modal-dialog">
		<div class="modal-content bg-danger">
			<div class="modal-header">
				<h4 class="modal-title">삭제 하시겠습니까?</h4>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<p>데이터를 삭제 합니다. 정말 지울까요? 리얼리?</p>
			</div>
			<div class="modal-footer justify-content-between">
				<button type="button" class="btn btn-outline-light" data-dismiss="modal">닫기</button>
				<button type="button" id="btnDel" class="btn btn-outline-light">그래! 결정했어!</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

</html>

<script type="text/javascript" charset="utf-8">
function setUser(field) {
	const consultant = '김민하';
	$('input[name='+field+']').val(consultant );
}

function getToday() {
    var date = new Date();
    var year = date.getFullYear();
    var month = ("0" + (1 + date.getMonth())).slice(-2);
    var day = ("0" + date.getDate()).slice(-2);
    return year + "-" + month + "-" + day;
}

function setToday(field) {
	const v = getToday(); 
	$('input[name='+field+']').val(v);
}

var clickFlag = true; // 2중 클릭방지
function preventDoubleClick() {
	clickFlag=false;
	setTimeout(function(){ clickFlag = true; },2000);
}

$(document).ready(function() {
	$('.datemask').inputmask('yyyy-mm-dd', { 'placeholder': 'yyyy-mm-dd' })
	$('[data-mask]').inputmask();
	$('input[name="reqDate"]').focus();

	$('#btnSave').click(function(e) {
        e.preventDefault();
        if(!clickFlag) return;
		preventDoubleClick();
        const $form = $('form[name=viewForm]');
		const target = $form.attr('action');
        const param = $form.serialize();
        $.post(target,param,function(data){
		  toast();
        });
	});

	// 엔터키
	$('form input').keydown(function(e) {
	    if (e.keyCode == 13) {
			if(!clickFlag) return;
			preventDoubleClick();
			const $form = $('form[name=viewForm]');
			$form.submit();
	    }
	});

	$(this).keydown(function(e) {
		const prevNo = "${view.prevNo }";
		const nextNo = "${view.nextNo }";
		if(prevNo!='') {
			if (e.ctrlKey && e.keyCode == 37) {
				$('#btnPrev').trigger('click');	
			}
		}
		if(nextNo!='') {
			if (e.ctrlKey && e.keyCode == 39) {
				$('#btnNext').trigger('click');	
			}
		}
	});

	$('#btnPrev').click(function(e) {
      e.preventDefault();
	  console.log("${view.prevNo}");
	  location.href="/board/call_assist_view.do?no=${view.prevNo }";
	});

	$('#btnNext').click(function(e) {
      e.preventDefault();
	  console.log("${view.nextNo}");
      location.href="/board/call_assist_view.do?no=${view.nextNo }";

	});

	$('#btnDel').click(function(e) {
      e.preventDefault();
      if(!clickFlag) return;
	  preventDoubleClick();
      const $form = $('form[name=viewForm]');
	  $('input[name=iuFlag]').val('D');
      $form.submit();
	});

	$('#btnNew').click(function(e) {
      e.preventDefault();
      location.href="/board/call_assist_view.do";
	});

	$('#btnList').click(function(e) {
      e.preventDefault();
	  location.href='/board/call_assist.do';
	});
});
</script>