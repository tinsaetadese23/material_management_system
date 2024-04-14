<%
if(session.getAttribute("isAlive") == null){
	RequestDispatcher ds = request.getRequestDispatcher("famsLogin.jsp");
	ds.forward(request,response);
}
%>
<%
boolean ASSCLA = false, ASSCAT = false, ASSSUBCAT = false, ASS = false, RBAC = false,
DEPR = false,PO = false,ASSBULK = false,SUPP = false,ASSUN = false,ASSISS=false,ASSRET =false,
ASSDIS = false,ASSTRN = false,STSCHN =false,ISSVOU=false,BROASS = false,CPBR = false,GRVR= false,FAISSVOU = false;
int ASSCLACounter=0,ASSCATCounter = 0,ASSSUBCATCounter=0,ASSCounter = 0,RBACCounter = 0,DEPRCounter = 0,POCounter = 0,SUPPCounter = 0,ASSUNCounter = 0,ASSBULKCounter = 0;
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>MMS | Dashboard</title>
  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <!--commented by tinsae-->
  <link rel="stylesheet" href="../resources/plugins/fontawesome-free/css/all.min.css">
  <!--commented by tinsae-->
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Tempusdominus Bootstrap 4 -->
  <link rel="stylesheet" href="../resources/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="../resources/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <!-- JQVMap -->
  <link rel="stylesheet" href="../resources/plugins/jqvmap/jqvmap.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../resources/dist/css/adminlte.min.css">
  <!-- overlayScrollbars -->
  <link rel="stylesheet" href="../resources/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="../resources/plugins/daterangepicker/daterangepicker.css">
  <!-- summernote -->
  <link rel="stylesheet" href="../resources/plugins/summernote/summernote-bs4.min.css">
  <link rel='stylesheet' href ="../resources/css/branding.css">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Preloader -->
  <!-- <div class="preloader flex-column justify-content-center align-items-center">
    <img class="animation__shake" src="../resources/dist/img/sinqe-modified.png" alt="Siinqee bank Logo" height="60" width="60">
  </div> -->

  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-light custom_header_branding">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" style='color:white;' data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <a href="../views/tester.jsp" style='color:white;' class="nav-link">Material Management System</a>
      </li>
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Navbar Search -->
      <!-- Messages Dropdown Menu -->
      <!-- <li class="nav-item dropdown">
        <a class="nav-link" data-toggle="dropdown" href="#">
          <i class="far fa-bell"></i>
          <span class="badge badge-danger navbar-badge">3</span>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <a href="#" class="dropdown-item">
            Message Start
            <div class="media">
              <img src="../resources/dist/img/user1-128x128.jpg" alt="User Avatar" class="img-size-50 mr-3 img-circle">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  Brad Diesel
                  <span class="float-right text-sm text-danger"><i class="fas fa-star"></i></span>
                </h3>
                <p class="text-sm">Call me whenever you can...</p>
                <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
              </div>
            </div>
            Message End
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            Message Start
            <div class="media">
              <img src="../resources/dist/img/user8-128x128.jpg" alt="User Avatar" class="img-size-50 img-circle mr-3">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  John Pierce
                  <span class="float-right text-sm text-muted"><i class="fas fa-star"></i></span>
                </h3>
                <p class="text-sm">I got your message bro</p>
                <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
              </div>
            </div>
            Message End
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item">
            Message Start
            <div class="media">
              <img src="../resources/dist/img/user3-128x128.jpg" alt="User Avatar" class="img-size-50 img-circle mr-3">
              <div class="media-body">
                <h3 class="dropdown-item-title">
                  Nora Silvester
                  <span class="float-right text-sm text-warning"><i class="fas fa-star"></i></span>
                </h3>
                <p class="text-sm">The subject goes here</p>
                <p class="text-sm text-muted"><i class="far fa-clock mr-1"></i> 4 Hours Ago</p>
              </div>
            </div>
            Message End
          </a>
          <div class="dropdown-divider"></div>
          <a href="#" class="dropdown-item dropdown-footer">See All Messages</a>
        </div>
      </li> -->
      <!-- Notifications Dropdown Menu -->
      <li class="nav-item dropdown">
        <a class="nav-link" style='color:white;' data-toggle="dropdown" href="#">
         <%out.println(session.getAttribute("username"));%><i class="far fa-user"></i>
        </a>
        <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
          <span class="dropdown-item dropdown-header"><%out.println(session.getAttribute("username"));%></span>
          <div class="dropdown-divider"></div>
          <a href="../Auth/ch" class="dropdown-item">
            <i class="fas fa-envelope mr-2"></i> Change Password
          </a>
          <div class="dropdown-divider"></div>
          <a href="../Auth/" class="dropdown-item">
            <i class="fas fa-users mr-2"></i> Logout
          </a>
        </div>
      </li>
    </ul>
  </nav>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <aside class="main-sidebar sidebar-dark-yellow elevation-4 custom_log_bg">
    <!-- Brand Logo -->
    <a href="../views/tester.jsp" class="brand-link">
      <img src="../resources/dist/img/sinqe-modified.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
      <span class="brand-text font-weight-light" style='color:#6C8C4C;'>MMS</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar custom_sidebar_branding">
   <!-- Sidebar Menu -->
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
          <li class="nav-item">
            <a id="classification" href="#" class="nav-link">
              <i class="nav-icon fas fa-book"></i>
              <p>
                General Parameters
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            <%if(request.getAttribute("userRoleLists") != null){%>
            <c:forEach var="roles" items ="${userRoleLists}">
            <%if(!ASSCLA){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetClass.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetClass.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset Class</p>              
	                </a>
	              </li>
	              <%ASSCLA = true; %>
	              </c:if>
	              <%}%>
            <%if(!ASSCAT){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCAT'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetCategory.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetCategory.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset Category</p>              
	                </a>
	              </li>
	              <%ASSCAT = true; %>
	              </c:if>
	              <%}%>
	         <%if(!ASSSUBCAT){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSSUBCAT'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetSubCategory.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetSubCategory.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset SubCategory</p>              
	                </a>
	              </li>
	              <%ASSSUBCAT = true; %>
	              </c:if>
	              <%}%>    
               </c:forEach>
              <%}%>
            </ul>
          </li>
          
          <li class="nav-item">
            <a id="operation" href="#" class="nav-link">
              <i class="nav-icon fas fa-tag"></i>
              <p>
                Common Transaction
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            <%if(request.getAttribute("userRoleLists") != null){%>
            <c:forEach var="roles" items ="${userRoleLists}">
            <%if(!PO){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
            	  <%ASSCLACounter++; %>
	            <li id="po" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsPO.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsPO.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Purchase Order</p>              
	                </a>
	              </li>
	              <%PO = true; %>
	              </c:if>
	              <%}%>
	               
	           <%if(!ASSBULK){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
            	  <%ASSCLACounter++; %>
	            <li onclick="linkChanger()" id="br" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsStock.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsStock.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Bulk Stock Receiving</p>              
	                </a>
	              </li>
	              <%ASSBULK = true; %>
	              </c:if>
	              <%}%>
            <%if(!ASSISS){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSISS'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetIssuance.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetIssuance.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset Issuance</p>              
	                </a>
	              </li>
	              <%ASSISS = false; %>
	              </c:if>
	              <%}%>
	         <%if(!ASSTRN){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSTRN'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetTransfer.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetTransfer.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset Transfer</p>              
	                </a>
	              </li>
	              <%ASSTRN = true; %>
	              </c:if>
	              <%}%>    
               </c:forEach>
              <%}%>
            </ul>
          </li>
          
          <!-- start of comment -->
          <li class="nav-item">
            <a id="classification" href="#" class="nav-link ">
              <i class="nav-icon fas fa-th"></i>
              <p>
                Other Operations
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            <%if(request.getAttribute("userRoleLists") != null){%>
            <c:forEach var="roles" items ="${userRoleLists}">
            <%if(!ASSUN){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
	            <li id="unit" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetUnit.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetUnit.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Unit Management</p>              
	                </a>
	              </li>
	              <%ASSUN = true; %>
	              </c:if>
	              <%}%>
	              
	         <%if(!DEPR){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
            	  <%ASSCLACounter++; %>
	            <li style = "pointer-events:none;" id = "depr" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetDepreciation.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetDepreciation.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p style = "pointer-events:none;">Depreciation Management</p>              
	                </a>
	              </li>
	              <%DEPR = true; %>
	              </c:if>
	              <%}%>
	              
	          <%if(!SUPP){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
            	  <%ASSCLACounter++; %>
	            <li id="supp" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsSupplier.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsSupplier.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Supplier Management</p>              
	                </a>
	              </li>
	              <%SUPP = true; %>
	              </c:if>
	              <%}%> 
	               <%if(!ASSRET){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSRET'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAssetReturn.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAssetReturn.jsp.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset Returns</p>              
	                </a>
	              </li>
	              <%ASSRET = true; %>
	              </c:if>
	              <%}%>
	        <%if(!ASSDIS){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ASSDIS'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsManualDisposal.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsManualDisposal.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Asset Disposal</p>              
	                </a>
	              </li>
	              <%ASSDIS = true; %>
	              </c:if>
	              <%}%>  
               </c:forEach>
              <%}%>
            </ul>
          </li>
          <!-- end of comment -->
          <!-- start of comment -->
          <li class="nav-item">
            <a id="classification" href="#" class="nav-link ">
              <i class="nav-icon far fa-plus-square"></i>
              <p>
                Voucher Printing
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            <%if(request.getAttribute("userRoleLists") != null){%>
            <c:forEach var="roles" items ="${userRoleLists}">
        		<%if(!GRVR){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'GRVR'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/goodreceiving.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="goodreceiving.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>GRV Report</p>              
	                </a>
	              </li>
	              <%GRVR = true; %>
	              </c:if>
	              <%}%>
	           <%if(!ISSVOU){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ISSVOU'}">
	            <li  id="ac" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/storeIssueVoucher.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="storeIssueVoucher.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Stationary Issue Voucher</p>              
	                </a>
	              </li>
	              <%ISSVOU = true; %>
	              </c:if>
	              <%}%>
	              <%if(!FAISSVOU){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'ISSVOU'}">
	            <li  id="ac" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/faStoreIssueVoucher.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="faStoreIssueVoucher.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Fixed Asset Issue Voucher</p>              
	                </a>
	              </li>
	              <%ISSVOU = true; %>
	              </c:if>
	              <%}%>
               </c:forEach>
              <%}%>
            </ul>
          </li>
          <!-- end of comment --> 
          <li class="nav-item">
            <a id="classification" href="#" class="nav-link ">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>
                Reports
                <i class="right fas fa-angle-left"></i>
              </p>
            </a>
            <ul class="nav nav-treeview">
            <%if(request.getAttribute("userRoleLists") != null){%>
            <c:forEach var="roles" items ="${userRoleLists}">
         <%--    <%if(!CPBR){%>
	            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'CPBR'}">
	            <li class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/categoryperbrn.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="categoryperbrn.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Category Per Branch</p>              
	                </a>
	              </li>
	              <%CPBR = true; %>
	              </c:if>
	              <%}%>  --%>  
	                <%if(!BROASS){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'BROASS'}">
	            <li  id="ac" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsAsset.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsAsset.jsp" class="nav-link">
	                <%} %>
	                  <i class="far fa-circle nav-icon"></i>
	                  <p>Browse Asset</p>              
	                </a>
	              </li>
	              <%BROASS = true; %>
	              </c:if>
	              <%}%>  
               </c:forEach>
              <%}%>
            </ul>
          </li>
           <c:forEach var="roles" items ="${userRoleLists}">            
	           <%if(!RBAC){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'RBAC'}">
	            <li  id="ac" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/famsRbac.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="famsRbac.jsp" class="nav-link">
	                <%} %>
	                  <i class="nav-icon ion-person-add"></i>
	                  <p>Access Control</p>              
	                </a>
	              </li>
	              <%RBAC = true; %>
	              </c:if>
	              <%}%>
	              
	            <%if(!STSCHN){%>
            	  <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'STSCHN'}">
	            <li  id="ac" class="nav-item">
	              <%if(request.getAttribute("onServer") != null){ %>
	                <a href="../views/brnstatus.jsp" class="nav-link">
	                <%}else{ %>
	                <a href="brnstatus.jsp" class="nav-link">
	                <%} %>
	                  <i class="nav-icon ion-stats-bars"></i>
	                  <p>Status Change</p>              
	                </a>
	              </li>
	              <%STSCHN = true; %>
	              </c:if>
	              <%}%>    
          </c:forEach>
          
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>
  <script>
  	function linkChanger(){
  		document.getElementById("br").setAttribute("class","nav-item active");
  	}
  </script>