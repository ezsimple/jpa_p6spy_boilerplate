<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="_ctx" value="${pageContext.request.contextPath }"/>
<c:set var="_adminlte" value="/webjars/AdminLTE/3.0.5" />
<!-- REQUIRED SCRIPTS -->
<!-- jQuery -->
<script src="${_ctx }${_adminlte }/plugins/jquery/jquery.min.js"></script>
<link rel="stylesheet" href="${_ctx }${_adminlte }/plugins/jquery-ui/jquery-ui.css"/>
<script src="${_ctx }${_adminlte }/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Bootstrap 4 -->
<script src="${_ctx }${_adminlte }/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Select2 -->
<script src="${_ctx }${_adminlte }/plugins/select2/js/select2.full.min.js"></script>
<!-- InputMask -->
<script src="${_ctx }${_adminlte }/plugins/moment/moment.min.js"></script>
<script src="${_ctx }${_adminlte }/plugins/inputmask/min/jquery.inputmask.bundle.min.js"></script>
<!-- bs-custom-file-input -->
<script src="${_ctx }${_adminlte }/plugins/bs-custom-file-input/bs-custom-file-input.min.js"></script>
<!-- SweetAlert2 -->
<script src="${_ctx }${_adminlte }/plugins/sweetalert2/sweetalert2.min.js"></script>
<!-- Toastr -->
<link rel="stylesheet" href="${_ctx }${_adminlte }/plugins/toastr/toastr.min.css">
<script src="${_ctx }${_adminlte }/plugins/toastr/toastr.min.js"></script>
<!-- flot jquery chart  -->
<script src="${_ctx }/static/js/flot/excanvas.min.js"></script>
<script src="${_ctx }/static/js/flot/jquery.flot.min.js"></script>
<script src="${_ctx }/static/js/flot/jquery.flot.pie.min.js"></script>
<!-- AdminLTE App -->
<script src="${_ctx }${_adminlte }/dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="${_ctx }${_adminlte }/dist/js/demo.js"></script>
<script type="text/javascript">
$(document).ready(function () {
  bsCustomFileInput.init();
});

const thanksWords = ['오늘 하루 홧팅!!','감사의 마음 전합니다.','잘 부탁해요','또 한건 처리했다!', '감사합니다.', '고마워요!', '도와주셔서 감사합니다.', '고맙게 생각해요.', '이 1건 처리가 정말 큰 힘이 됩니다.', '처리해 주셔서 감사합니다.', '빠른 처리 감사합니다.'];
function toast(title, body, delay) {
  $(document).Toasts('create', {
	  class: 'bg-light',
	  title: title,
	  position: 'topRight',
	  autohide: true,
	  delay: (!delay?2000:delay),
	  image: '/static/images/참잘했어요.gif',
	  imageHeight: '125px',
	  body: (!body||body==''?thanksWords[Date.now()%thanksWords.length]:body)
  });
}

jQuery.fn.serializeObject = function () {
    let result = {};
    jQuery.each(this.serializeArray(), function (i, element) {
        let node = result[element.name];
        if (typeof node !== 'undefined' && node !== null) {
            if (jQuery.isArray(node)) {
                node.push(element.value);
            } else {
                result[element.name] = [node, element.value];
            }
        } else {
            result[element.name] = element.value;
        }
    });
    jQuery.each(this.find('input[type=checkbox]'), function (i, element) {
        result[element.name] = $(element).prop('checked');
    });
    return result;
};
</script>
