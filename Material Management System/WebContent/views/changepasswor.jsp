<%if(request.getAttribute("logout") != null){
session.invalidate();
System.out.println("Session Has Been Killed!");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="headerLayoutComp.jsp"></jsp:include> 
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Change Password</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Dashboard v1</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
     <!-- Main content -->
     <hr>
    <section class="content">
<div class="row" style="">    
<div class="col-md-4"></div>
<div class="col-md-4 card card-secondary">
              <div class="card-header">
                <h3 class="card-title">FAMS | Password Change</h3>
              </div>
              <!-- /.card-header -->
              <!-- form start -->
              	<form action="../Auth/ch" method="POST" class="form-horizontal" onsubmit="return validate()">
                <div class="card-body">
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
                  <button type="submit" class="btn btn-info">Change</button>
                </div>
                <!-- /.card-footer -->
              </form>
            </div>
            </div>
    
    </section>
    </div>
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
		for(var i=0;i<ct.length;i++)
		{
			if(ct[i]=='0' || ct[i]=='1' || ct[i]=='2' || ct[i]=='3' || ct[i]=='4' || ct[i]=='5' || ct[i]=='6' || ct[i]=='7' || ct[i]=='8' || ct[i]=='9')
			{
				if(testUpper(ct)){
					if(testLower(ct)){
						flag = true;
					}else{
						sstrmsg="Password must contain atleast one Small Letter";
						pass.focus;
					}
				}else{
					sstrmsg="Password must contain atleast one Capital Letter";
					pass.focus;
				}
			}else{
				sstrmsg="Password must contain atleast one number";
				pass.focus;
			}
		}
	}
	else{
		sstrmsg = "Password length should not be less than eight character";
		pass.focus;
	}
	//message.innerHTML=sstrmsg;
	alert(sstrmsg);
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
</script>   
    
   <jsp:include page="footerLayoutComp.jsp"></jsp:include>