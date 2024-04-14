<%
session.invalidate();
System.out.println("Session Has Been Killed!");
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Session Timeout</title>
</head>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>FAMS | Dashboard</title>

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
</head><body>
<div class = "offset-md-5" style = "font-family:sans-serif; margin-top:35px;">
<br>
<%if(request.getAttribute("onServer") != null){%>
<img class="text-center" width="8%" height = "auto" class="img-fluid" src = "../images.ico"/>
<%}else{ %>
<img class="text-center" width="8%" height = "auto" class="img-fluid" src = "images.ico"/>
<%}%>
<h4  class = "">Oops! your session has expired.</h4>
<h5 class= ""><a href="../views/famsLogin.jsp" class = "primary">Sign in</a> again to resume.</h5>
</div>
</body>
</html>