<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ page import="java.util.Date" %>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Printing</title>

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
</head>
<body>
<div class="wrapper">
  <!-- Main content -->
  <section class="invoice">
    <!-- title row -->
    <div class="row">
       <div class="col-12">
                  <h4 style="text-align:center;">
                    <i class=""></i> SIINQEE BANK S.C
                    <pre style="text-align:center;">Report On Category Per Branch</pre>
                  </h4>
                </div>
                <!-- /.col -->
    </div>
    <!-- info row -->
    <div class="row invoice-info">
      <div class="col-sm-4 invoice-col">
         
      </div>
      <!-- /.col -->
      <div class="col-sm-4 invoice-col">
         
      </div>
      <!-- /.col -->
      <div class="col-sm-4 invoice-col">
       
      </div>
      <!-- /.col -->
    </div>
    <!-- /.row -->

    <!-- Table row -->
    <div class="row">
      <div class="col-12 table-responsive">
        <table class="table table-striped">
          <thead>
                   <tr>
   					<th style="width">No.</th>
   					<th>Branch Name</th>
   					<th style="">Total Price</th>
   					<th style="">Remark</th>
	    		 </tr>
                    </thead>
                    <tbody>
                    <%if(request.getAttribute("rcpb") != null){ %>
  					<%int counter = 0; %>
  					<c:forEach var = "report" items = "${rcpb}">
  					<tr>
  						<td style=""><%out.println(++counter);%></td>
   					<td><c:out value ="${report.rbranch}"/></td>
   					<td><c:out value ="${report.rtotal}"/></td>
   					<td><c:out value ="${report.rcategory}"/></td>
   					<%-- <td><form action = "../ACCS/edit" method="POST">
   					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
   					<button style="background-color:white;border:1px solid white;" type="submit"><i class="far fa-edit"></i></button>
   					</form></td>
   					<td><form action = "../ACCS/delete" method="POST" onsubmit="return deleteMe()">
   					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
   					<button style="background-color:white;border:1px solid white;" type="submit"><i class="fas fa-times btn-danger"></i></button>
   					</form></td> --%>
   					
  					</tr>
  					</c:forEach>
  					<%}%>
                			 <tr>
               				 <td colspan="2" style="">Total</td>
               				 <td colspan="2"><%if(request.getAttribute("alltotal") != null){out.println(request.getAttribute("alltotal"));} %></td>
               			</tr>
                    </tbody>
        </table>
      </div>
      <!-- /.col -->
    </div>
    <!-- /.row -->

    <div class="row">
    <%int cntr = 0; %>
    <c:forEach var ="voucher"  items = "${issueV}">
    <%if(cntr == 0) {%>
      <!-- accepted payments column -->
      <div class="col-4">
       <p>Prepared By :<c:out value=
                  "${voucher.maker}"></c:out></p>
       <p>Signature :_____________</p>
      </div>
      <!-- /.col -->
      <div class="col-4">
        <p>Approved By :<c:out value=
                  "${voucher.checker}"></c:out></p>
       <p>Signature :_____________</p>
      </div>
       <div class="col-4">
        <p>Received By :<c:out value=
                  "${voucher.item}"></c:out></p>
       <p>Signature :_____________</p>
      </div>
      <!-- /.col -->
      <%}cntr++;%>
      </c:forEach>
    </div>
    <!-- /.row -->
  </section>
  <!-- /.content -->
</div>
<!-- ./wrapper -->
<!-- Page specific script -->
<script>
  window.addEventListener("load", window.print());
</script>
</body>
</html>
