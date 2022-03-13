<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="_ctx" value="${pageContext.request.contextPath }"/>
<c:set var="_adminlte" value="/webjars/AdminLTE/3.0.5" />

<c:set var="on_0" value="${param.s0 }" />
<c:set var="on_1" value="${param.s1 }" />
<c:set var="on_2" value="${param.s2 }" />
<c:set var="on_3" value="${param.s3 }" />

<c:set var="act_0" value="${param.a0 }" />
<c:set var="act_1" value="${param.a1 }" />
<c:set var="act_2" value="${param.a2 }" />
<c:set var="act_3" value="${param.a3 }" />
<c:set var="act_4" value="${param.a4 }" />
<c:set var="act_5" value="${param.a5 }" />
<c:set var="act_6" value="${param.a6 }" />
<c:set var="act_7" value="${param.a7 }" />
<c:set var="act_8" value="${param.a8 }" />
<c:set var="act_9" value="${param.a9 }" />

<!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <a href="${_ctx }" class="nav-link">Home</a>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <style>
    .nav-logo {
      font-size: 1.4rem;
      font-weight: 300;
      margin-left: 1rem;
      margin-bottom: 0.9rem;
      text-align: left;
    }
  </style>
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="${_ctx }" class="brand-link">
      <img src="${_ctx }/static/images/insoft_logo_2.png" alt="아이엔 소프트" class="brand-image elevation-3" >
      <span class="brand-text font-weight-light nav-logo">my<b>PMS</b></span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
      <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
		  <li class="nav-item">
			<a href="${_ctx }/board/call_assist.do" class="nav-link ${act_9} }">
<%--              <i class="nav-icon fa-solid fa-diagram-project"></i>--%>
              <i class="nav-icon fas fa-a"></i>
			  <p>프로젝트 관리</p>
			</a>
		  </li>
        </ul>

<%--        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">--%>
<%--          <li class="nav-item has-treeview ${on_3 == 'active'?'menu-open':'' }">--%>
<%--            <a href="#" class="nav-link ${on_3 }">--%>
<%--              <i class="nav-icon fas fa-tachometer-alt"></i>--%>
<%--              <p>--%>
<%--				용어집                --%>
<%--                <i class="right fas fa-angle-left"></i>--%>
<%--              </p>--%>
<%--            </a>--%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/pig_terms.do" class="nav-link ${act_8} }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>양돈 용어집</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--          </li>--%>
<%--        </ul>--%>

<%--        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">--%>
<%--          <!-- Add icons to the links using the .nav-icon class--%>
<%--               with font-awesome or any other icon font library -->--%>
<%--          <li class="nav-item has-treeview ${on_0 == 'active'?'menu-open':'' }">--%>
<%--            <a href="#" class="nav-link ${on_0 }">--%>
<%--              <i class="nav-icon fas fa-tachometer-alt"></i>--%>
<%--              <p>--%>
<%--                엑셀업로드--%>
<%--                <i class="right fas fa-angle-left"></i>--%>
<%--              </p>--%>
<%--            </a>--%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/yukjongga.do" class="nav-link ${act_0 }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>월요일 육종가</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>

<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/sisae.do" class="nav-link ${act_1 }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>매달 시세</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--             -->--%>
<%--             <!-- --%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/farm_migration.do" class="nav-link ${act_2 }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>농장 데이터 이관</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>

<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/dabi_seangsi_chejung.do" class="nav-link ${act_3 }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>다비육종 생시체중</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/gumjungzaryu_imf.do" class="nav-link ${act_4 }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>검정자료 IMF값</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/dodram_update.do" class="nav-link ${act_5} }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>도드람 농가 업데이트</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--          </li>--%>
<%--        </ul>--%>
        
<%--        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">--%>
<%--          <li class="nav-item has-treeview ${on_1 == 'active'?'menu-open':'' }">--%>
<%--            <a href="#" class="nav-link ${on_1 }">--%>
<%--              <i class="nav-icon fas fa-tachometer-alt"></i>--%>
<%--              <p>--%>
<%--                엑셀 다운로드--%>
<%--                <i class="right fas fa-angle-left"></i>--%>
<%--              </p>--%>
<%--            </a>--%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/jadondunggi_print.do" class="nav-link ${act_6} }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>자돈등기 파일출력</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--          </li>--%>
<%--        </ul>--%>

<%--        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">--%>
<%--          <li class="nav-item has-treeview ${on_2 == 'active'?'menu-open':'' }">--%>
<%--            <a href="#" class="nav-link ${on_2 }">--%>
<%--              <i class="nav-icon fas fa-tachometer-alt"></i>--%>
<%--              <p>--%>
<%--                피그에어--%>
<%--                <i class="right fas fa-angle-left"></i>--%>
<%--              </p>--%>
<%--            </a>--%>
<%--            <ul class="nav nav-treeview">--%>
<%--              <li class="nav-item">--%>
<%--                <a href="${_ctx }/excel/yolo_mapping.do" class="nav-link ${act_7} }">--%>
<%--                  <i class="far fa-circle nav-icon"></i>--%>
<%--                  <p>YOLO 매핑</p>--%>
<%--                </a>--%>
<%--              </li>--%>
<%--            </ul>--%>
<%--          </li>--%>
<%--        </ul>--%>

      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>
