<%if(request.getAttribute("logout") != null){
session.invalidate();
System.out.println("Session Has Been Killed!");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
  <!-- custom branding -->
  <link rel='stylesheet' href ="../resources/css/branding.css">
</head>
    
    
     <!-- Main content -->
    <section class="content">
<div class="row" style="margin-top:5%">    
<!-- <div class="col-md-4"></div> -->
<div class="col-md-4 card col-md-4 offset-md-4">
              <div class="card-header custom_card_branding">
                <h3 class="card-title">Material Management System | Sign In</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              <%if(request.getAttribute("fl") == null){ %>
              <form action="../Auth/" method="POST" class="form-horizontal">
                <div class="card-body">
                <%if(request.getAttribute("onServer") != null){ %>
                	<img class="img-fluid"  width = "100%" height="auto" src = "../views/White Background Logo.jpg"/>
                <%}else{ %>
                	<img  class="img-fluid"  width = "100%" height="auto" src = "White Background Logo.jpg"/>
                <%} %>
                <%if(request.getAttribute("msg") != null){ %>
                <p><c:out value ="${msg.successMsg}"/></p>
                <p style="color:red;"><c:out value ="${msg.errorMsg}"/></p>
                <%} %>
                  <div class="form-group row">
                    <label for="inputEmail3" class="col-sm-3 col-form-label">Username</label>
                    <div class="col-sm-9">
                      <input name="username" required type="text" class="form-control" id="inputEmail3" placeholder="Username">
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword3" class="col-sm-3 col-form-label">Password</label>
                    <div class="col-sm-9">
                      <input name="password" required type="password" class="form-control" id="inputPassword3" placeholder="Password">
                    </div>
                  </div>
                  <div class="form-group row">
                    <div class="offset-sm-2 col-sm-10">
                      <div class="form-check">
                        <!-- <input type="checkbox" class="form-check-input" id="exampleCheck2"> -->
                       <!--  <label class="form-check-label" for="exampleCheck2">Remember me</label> -->
                      </div>
                    </div>
                  </div>
                </div>
                <!-- /.card-body -->
                <div class="card-footer">
                  <button type="submit" class="btn custom_button_branding">Sign in</button>
                </div>
                <!-- /.card-footer -->
              </form>
              
              
              
              <%}else{ %>
              	<form action="../Auth/chn" method="POST" class="form-horizontal" onsubmit="return validate()">
                <div class="card-body">
                <%if(request.getAttribute("onServer") != null){ %>
                	<img  width = "100%" height="150px" src = "../views/White Background Logo.jpg"/>
                <%}else{ %>
                	<img  width = "450px" height="150px" src = "White Background Logo.jpg"/>
                <%} %>
                <%if(request.getAttribute("msg") != null){ %>
                <p><c:out value ="${msg.successMsg}"/></p>
                <p style="color:red;"><c:out value ="${msg.errorMsg}"/></p>
                <%} %>
                  <div class="form-group row">
                    <label for="inputEmail3" class="col-sm-3 col-form-label">Previous Password</label>
                    <div class="col-sm-9">
                      <input name="ppassword" required type="password" class="form-control" id="inputEmail3" placeholder="Username">
                    </div>
                  </div>
                  <div class="form-group row">
                    <label for="inputPassword3" class="col-sm-3 col-form-label">New Password</label>
                    <div class="col-sm-9">
                      <input id ="pass" name="npassword" required type="password" class="form-control" id="inputPassword3" placeholder="Password">
                    </div>
                  </div>
                  <div class="form-group row">
                   <label for="inputPassword3" class="col-sm-3 col-form-label">Confirm New Password</label>
                    <div class="col-sm-9">
                      <input name="cpassword" required type="password" class="form-control" id="inputPassword3" placeholder="Password">
                    </div>
                  </div>
                </div>
                <!-- /.card-body -->
                <div class="card-footer">
                  <button type="submit" class="btn btn-info">Sign in</button>
                </div>
                <!-- /.card-footer -->
              </form>
             <%} %>
            </div>
            </div>
    
    </section>
 <script>
function validate(){
	//var s = document.getElementById("btnLog");
	//alert("ff");
	var flag = false;
	var sstrmsg = "";
	var pass = document.getElementById("pass");
//	var message = document.getElementById("message");
	var ct = pass.value;
	var len = ct.length;
	//alert(ct.length);
	if(len > 7){
			if(hasNumber(ct))
			{
				if(testUpper(ct)){
					if(testLower(ct)){
						flag = true;
					}else{
						sstrmsg="Password must contain atleast one Small Letter";
						alert(sstrmsg);
						pass.focus;
					}
				}else{
					sstrmsg="Password must contain atleast one Capital Letter";
					alert(sstrmsg);
					pass.focus;
				}
			}else{
				sstrmsg="Password must contain atleast one number";
				alert(sstrmsg);
				pass.focus;
			}
	}
	else{
		sstrmsg = "Password length should not be less than eight character";
		alert(sstrmsg);
		pass.focus;
	}
	//message.innerHTML=sstrmsg;
	return flag;
}
function testUpper(sstr){
	var containUpper = false;
	for(var i = 0;i<sstr.length;i++)
	{
		if(sstr[i]=='0' || sstr[i]=='1' || sstr[i]=='2' || sstr[i]=='3' || sstr[i]=='4' || sstr[i]=='5' || sstr[i]=='6' || sstr[i]=='7' || sstr[i]=='8' || sstr[i]=='9')
		{
		continue;	
		}
	if(sstr[i].toUpperCase()==sstr[i])
	{
	//console.log("The string contains upper case");
	containUpper = true;
	break;
	}
	}
	return containUpper;
	}
function testLower(sstr){
	var cLower = false;
	for(var i = 0;i<sstr.length;i++)
	{
		if(sstr[i]=='0' || sstr[i]=='1' || sstr[i]=='2' || sstr[i]=='3' || sstr[i]=='4' || sstr[i]=='5' || sstr[i]=='6' || sstr[i]=='7' || sstr[i]=='8' || sstr[i]=='9')
		{
		continue;	
		}
	if(sstr[i].toLowerCase()==sstr[i])
	{
	//console.log("The string contains lower case");
	cLower = true;
	break;
	}
	}
	return cLower;
	}
   function hasNumber(password) {
	return /\d/.test(password);
	}

</script>   
    
   <script src="../resources/plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="../resources/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="../resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- ChartJS -->
<script src="../resources/plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script src="../resources/plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script src="../resources/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="../resources/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<!-- jQuery Knob Chart -->
<script src="../resources/plugins/jquery-knob/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script src="../resources/plugins/moment/moment.min.js"></script>
<script src="../resources/plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="../resources/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script src="../resources/plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script src="../resources/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="../resources/dist/js/adminlte.js"></script>
<!-- AdminLTE for demo purposes -->
<!-- <script src="../resources/dist/js/demo.js"></script> -->
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="../resources/dist/js/pages/dashboard.js"></script>
</body>
</html>
