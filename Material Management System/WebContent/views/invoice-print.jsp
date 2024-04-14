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
                    <pre style="text-align:center;">Store Issue Voucher<br>Stationary Stock</pre>
                    <small class="float-right"><%Date today = new Date();out.println(today);%></small>
                  </h4>
                </div>
                <!-- /.col -->
    </div>
    <!-- info row -->
    <div class="row invoice-info">
    <div class="col-sm-4 invoice-col">
                  From
                  <address>
                    <strong>Siinqee Bank S.C</strong><br>
                    ODA TOWER,KAZANCHIS AREA<br>
                    ADDIS ABABA
                  </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
                  Issued To
                  <address>
                  <%int counter = 0; %>
                  <c:forEach var = "voucher" items = "${issueV}">
                  <%if(counter == 0){%>
                    <strong><c:out value="${voucher.decription}"></c:out></strong><br>
                    <c:out value="${voucher.status}"></c:out><br>
                    <%}counter++;%>
                    </c:forEach>
                  </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
                 <%int ccounter = 0;%>
                  <c:forEach var = "voucher" items = "${issueV}">
                  <%if(ccounter == 0){%>
                  <b>Issue Ref No:</b> <c:out value=
                  "${voucher.issueRef}"></c:out><br>
                  <b>Issued Date:</b><c:out value=
                  "${voucher.createdAt}"></c:out><br>
                  <b>Responsible Person:</b> <c:out value="${voucher.item}"></c:out>
                  <%}ccounter++;%>
                    </c:forEach>
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
                      <th>S/No.</th>
                      <th>Code</th>
                      <th>Item Description</th>
                      <th>Requisition No</th>
                      <th>Measurement</th>
                      <th>Quantity</th>
                      <th>UnitPrice</th>
                      <th>Total Price</th>
                      <th>Remark</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var = "voucher" items = "${issueV}">
                    <tr>
                      <td><% int ccc = 0;out.println(++ccc);%></td>
                      <td><c:out value="${voucher.authStatus}"></c:out></td>
                      <td><c:out value="${voucher.cat}"></c:out></td>
                      <td><c:out value="${voucher.reqNo}"></c:out></td>
                      <td><c:out value="${voucher.comment}"></c:out></td>
                      <td><c:out value="${voucher.quantity}"></c:out></td>
                      <td><c:out value="${voucher.fcurrency1}"></c:out></td>
                      <td><c:out value="${voucher.fcurrency2}"></c:out></td>
                      <td></td>
                    </tr>
                   </c:forEach>
                   <tr>
                   <td colspan="7" style="text-align:center;">Total</td>
                   <td><%if(request.getAttribute("total") != null){out.println(request.getAttribute("total"));} %></td>
                   </tr>
                    </tbody>
                  </table>
                </div>
                <!-- /.col -->>
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
       <p>Date :_____________</p>
      </div>
      <!-- /.col -->
       <div class="col-4">
        <p>Received By :<c:out value=
                  "${voucher.item}"></c:out></p>
       <p>Signature :_____________</p>
       <p>Date :_____________</p>
      </div>
      <div class="col-4">
        <p>Approved By :<c:out value=
                  "${voucher.checker}"></c:out></p>
       <p>Signature :_____________</p>
       <p>Date :_____________</p>
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
