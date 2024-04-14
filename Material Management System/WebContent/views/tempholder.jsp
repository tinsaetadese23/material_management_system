    <%if(request.getAttribute("assetSubCatList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../ACS");
   ds.forward(request,response);
    }%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <jsp:include page="headerLayoutComp.jsp"></jsp:include>
  <!-- end of import header -->
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Asset Management</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Asset</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
    
    <%
    int totalAssetClassCount = 0;
    if(request.getAttribute("assetClass") != null){
    totalAssetClassCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
    
    
     <!-- Main content -->
    <section class="content">
    <%if(request.getAttribute("msg") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-default" data-backdrop="static" data-keyboard="false">
                  
                </button>
                <script type="text/javascript">
                window.onload = function(){
                document.getElementById('bModal').style.display="none";
                document.getElementById('bModal').click();
                }
                </script>
    <%}%>
    <%if(request.getAttribute("employee") != null){%>
    <script type="text/javascript">
    window.onload = function(){
        //document.getElementById('bModal').style.display="none";
        document.getElementById('custom-tabs-two-settings-tab').click();
        }
    </script>
    <% }%>
    <div class="container-fluid">
    	<div class = "row">
		 <div class="col-12 col-sm-6">
            <div class="card card-primary card-tabs">
              <div class="card-header p-0 pt-1">
                <ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
                  <li class="pt-2 px-3"><h3 class="card-title">New Asset</h3></li>
                  <li class="nav-item">
                    <a class="nav-link active" id="custom-tabs-two-home-tab" data-toggle="pill" href="#custom-tabs-two-home" role="tab" aria-controls="custom-tabs-two-home" aria-selected="true">Asset Classification</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Asset Specification</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Asset Ownership</a>
                  </li>
                </ul>
              </div>
              <div class="card-body">
                <div class="tab-content" id="custom-tabs-two-tabContent">
                  <div class="tab-pane fade show active" id="custom-tabs-two-home" role="tabpanel" aria-labelledby="custom-tabs-two-home-tab">
    				<form method = "post" action = "../ACS/tt">
    					<div class="form-group">
    						<label>Asset Sub Category</label>
    						<select name="assetSubCat" class="form-control">
    						<%if(request.getAttribute("assetSubCatList") != null){ %>
    						<c:forEach var = "assetSubCat" items = "${assetSubCatList}">
    						<c:if test="${assetSubCat.id == subCat.id || assetSubCat.id == stockObj.id}">
    						<option selected value ="<c:out value ="${assetSubCat.id}"/>"><c:out value ="${assetSubCat.subCatCode}"/></option>
    						</c:if>
    						<c:if test="${assetSubCat.id != subCat.id }">
    						<option value ="<c:out value ="${assetSubCat.id}"/>"><c:out value ="${assetSubCat.subCatCode}"/></option>
    						</c:if>
    						</c:forEach>
    						<%}%>
    						</select>
    						</div>
    					<div class="from-group">
    						<label>Quantity</label>
    						<input value=""  type="text" name="qty" class="form-control"/>
    					</div>
    					<div id="div01" class  = "form-group">
    					<br>
    					<input onclick="checkMe00();" value="populate"  type="submit" name="qty"/>		
    					</div>
    				</form>
                  </div>
                  <div class="tab-pane fade" id="custom-tabs-two-messages" role="tabpanel" aria-labelledby="custom-tabs-two-messages-tab">
			  			<div class="form-group">
			  			<c:if test="${subCat.mode == 0 }">
			  			    <c:set var = "test" scope = "session" value = "0"/>
			  			    <c:set var = "it" scope = "session" value = "IT Equipment"/>
			  			    <c:set var = "veh" scope = "session" value = "Vehicle"/>
			  				<c:if test = "${stockObj.cat == it}">
			  				<c:set var = "test" scope = "session" value = "1"/>
			  				<input onchange = "categoryChecker();" id="IT Equipment" name="cat" type="radio" value="IT Equipment" checked>
				  			<label>IT Equipment</label>
			  				</c:if>
			  				<c:if test = "${stockObj.cat == veh}">
			  				<c:set var = "test" scope = "session" value = "1"/>
			  				<input onchange = "categoryChecker();" id="IT Equipment" name="cat" type="radio" value="IT Equipment" >
				  			<label>IT Equipment</label>
			  				<input onchange = "categoryChecker();" name="cat" type="radio" value="Vehicle" checked>
				  			<label>Vehicle</label>
			  				</c:if>
			  				<c:if test = "${test == 0}">
			  				<input onchange = "categoryChecker();" id="IT Equipment" name="cat" type="radio" value="IT Equipment" checked>
				  			<label>IT Equipment</label>
				  			<input onchange = "categoryChecker();" name="cat" type="radio" value="Vehicle">
				  			<label>Vehicle</label>
			  				</c:if>
				  		</c:if>	
				  		<c:if test ="${subCat.mode == 1 }">
				  			 <c:set var = "ts" scope = "session" value = "0"/>
			  			    <c:set var = "ff" scope = "session" value = "Furniture and Fitting"/>
			  			    <c:set var = "land" scope = "session" value = "Land and Building"/>
			  			    <c:if test = "${stockObj.cat == ff}">
			  			    <c:set var = "ts" scope = "session" value = "1"/>
			  			    <input onchange = "categoryChecker();" id="Office Equipment" name="cat" type="radio" value="Furniture and Fitting" checked>
				  			<label>Furniture and Fittings</label>
				  			<input onchange = "categoryChecker();" name="cat" type="radio" value="Land and Building">
				  			<label>Land and Building</label>
			  			    </c:if>
			  			    <c:if test = "${stockObj.cat == land}">
			  			    <c:set var = "ts" scope = "session" value = "1"/>
			  			    <input onchange = "categoryChecker();" id="Office Equipment" name="cat" type="radio" value="Furniture and Fitting" >
				  			<label>Furniture and Fittings</label>
				  			<input onchange = "categoryChecker();" name="cat" type="radio" value="Land and Building" checked>
				  			<label>Land and Building</label>
			  			    </c:if>
			  			    <c:if test = "${ts == 0}">
			  			    <input onchange = "categoryChecker();" id="Office Equipment" name="cat" type="radio" value="Furniture and Fitting" checked>
				  			<label>Furniture and Fittings</label>
				  			<input onchange = "categoryChecker();" name="cat" type="radio" value="Land and Building">
				  			<label>Land and Building</label>
			  			    </c:if>
				  		</c:if>
				  			</div>
				  			<hr>
				  		<div class="row">
				  		<div class="col-md-4">
					  		<div  id="itA"  class="form-group">
	    						<label for="some">Generated Tag Number</label>
	    						<select class = "form-control">
	    						<%int count = 0; %>
	    						 <c:forEach var = "tag" items = "${tags}">
	    						 <option>(<%out.println(++count);%>)<c:out value="${tag.tagNumber}"></c:out></option>
	    						 </c:forEach>
	    						</select>
	    						</div>
	  						 <div id="landA"  class="form-group land">
	    						<label for="some">Area</label>
	    						<input value="" id="model" name="area" type="text" class="form-control" id = "assetClass"  placeholder="km square">
	    						</div>
	    			 		<div id = "vehA"  class="form-group veh">
	    						<label for="some">Plate Number</label>
	    						<input value="<c:out value = "${vehObj.plateNo}"/>" id = "serial" name="plateno" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id = "vehD"  class="form-group veh">
	    						<label for="some">No of Wheels</label>
	    						<input value="<c:out value = "${vehObj.noOfWheerls}"/>" id = "serial" name=wheels type="number" class="form-control" id = "assetClass" >
	    						</div>
				  		</div>
				  		<div class="col-md-4">
				  		<div  id="itC"  class="form-group">
	    						<label for="some">List of serials</label>
	    						<select class = "form-control">
	    						<%int counter = 0; %>
	    						 <c:forEach var = "serials" items = "${listSerials}">
	    						 <option>(<%out.println(++counter);%>)<c:out value="${serials.serialNum}"></c:out></option>
	    						 </c:forEach>
	    						</select>
	    						</div>
	  						 <div id="landB" class="form-group">
	    						<label for="some">Latitude</label>
	    						<input value="" id="long" name="lat" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    			 		<div id="vehB" class="form-group">
	    						<label for="some">Color</label>
	    						<input value="<c:out value = "${vehObj.color}"/>" id="lat" name="color" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id = "vehE"  class="form-group veh">
	    						<label for="some">Engine Capacity</label>
	    						<input value="<c:out value = "${vehObj.enginCapacity}"/>" id = "serial" name="engine" type="number" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id = "vehH"  class="form-group veh">
	    						<label for="some">No Of Seat</label>
	    						<input value="<c:out value = "${vehObj.noOfSeat}"/>" id = "serial" name="seat" type="number" class="form-control" id = "assetClass" >
	    						</div>
				  		</div>
				  		<div class="col-md-4" id="xx">
					  		<div id="itB"  class="form-group">
	    						<label for="some">Model</label>
	    						<input value="" id = "area"  name="model" type="text" class="form-control" id = "assetClass" >
	    						</div>
	  						 <div id="landC" class="form-group">
	    						<label for="some">Longitude</label>
	    						<input value="" id="color" name="long" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    			 		<div id ="vehC" class="form-group">
	    						<label for="some">CC</label>
	    						<input value="<c:out value = "${vehObj.cc}"/>" id="cc" name="cc" type="number" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id = "vehF"  class="form-group veh">
	    						<label for="some">Model</label>
	    						<input value="<c:out value = "${vehObj.model}"/>" id = "serial" name="modelname" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id = "vehG"  class="form-group veh">
	    						<label for="some">Engine Type</label>
	    						<input value="<c:out value = "${vehObj.engineType}"/>" id = "serial" name="enginetype" type="text" class="form-control" id = "assetClass" >
	    						</div>
				  		</div>
				  		</div>
				  		<div class="form-group">
    						<label id="spec" for="some">Remark</label>
    						<textarea name="remark"  class="form-control" id = "assetClass" required><c:out value =  "${stockObj.comment}"/></textarea>
    						</div>
                  </div>
                  <div  class="tab-pane fade" id="custom-tabs-two-settings" role="tabpanel" aria-labelledby="custom-tabs-two-settings-tab">
				  	  <div id="div1" class="form-group">
    						<label for="some">Employee Id</label><br>
    						<input value="<c:out value= "${employee.id }"/>" style="display:inline-bock" name="empId" type="text" class="" id = "assetClass" required>
    						<input style="display:iniline-block;" onclick="checkMe00();" value="Populate"  class="" type="submit" class="form-control" id = "assetClass" />
    						</div>
  						 <div class="form-group">
    						<label for="some">Employee Information</label>
    						<hr>
    						<p><strong>ID :</strong></p><c:out value="${employee.id}"></c:out>
    						<p><strong>Name :</strong></p><c:out value="${employee.fullName}"></c:out>
    						<p><strong>Branch :</strong></p>
    						<c:out value="${employee.branch}"></c:out>
    						<p><strong>Dep't :</strong></p>
    						<c:out value="${employee.dept}"></c:out>
    						</div>
    					<div class="form-group">
    						<input onclick="checkMe();" value="Issue Asset"  class="btn btn-primary" type="submit" class="form-control" id = "assetClass" />
    						</div>
                  </div>
                </div>
              </div>
              </form>
              <!-- /.card -->
            </div>
          </div>
          <div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Show Asset Class</h3>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>TAG NO</th>
		    					<th style="">Type</th>
		    					<th colspan="2">Actions</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("assetClass") == null){ %>
	    					<c:forEach var = "asset" items = "${assetClass}">
	    					<tr>
	    						<td style="">1</td>
		    					<td>2055edbsqw3</td>
		    					<td><c:out value ="${asset.assetClassDesc}"/></td>
		    					<td><form action = "../ACCS/edit" method="POST">
		    					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
		    					<button style="background-color:white;border:1px solid white;" type="submit"><i class="far fa-edit"></i></button>
		    					</form></td>
		    					<td><form action = "../ACCS/delete" method="POST" onsubmit="return deleteMe()">
		    					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
		    					<button style="background-color:white;border:1px solid white;" type="submit"><i class="fas fa-times btn-danger"></i></button>
		    					</form></td>
		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					</tbody>
    					</table>
    					<%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">There are 0 classes.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    						<button type = "button" class="btn btn-info">Total : <%out.println(totalAssetClassCount);%></button>
    					</div>
    				</form>
    			</div>
    		</div>
    	</div>
    </div>
    
    </section>
    </div>
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
    <script>
    function categoryChecker(){
    	document.getElementById("itA").style.display = "none";
		document.getElementById("itB").style.display = "none";
		document.getElementById("itC").style.display = "none";
		document.getElementById("landA").style.display = "none";
		document.getElementById("vehA").style.display = "none";
		document.getElementById("landB").style.display = "none";
		document.getElementById("vehB").style.display = "none";
		document.getElementById("landC").style.display = "none";
		document.getElementById("vehC").style.display = "none";
		document.getElementById("vehD").style.display = "none";
		document.getElementById("vehE").style.display = "none";
		document.getElementById("vehF").style.display = "none";
		document.getElementById("vehG").style.display = "none";
		document.getElementById("vehH").style.display = "none";
    	const categories = document.querySelectorAll('input[name="cat"]'); 
    	let selectedCat;
    	for(const cat of categories){
    		if(cat.checked){
    			selectedCat = cat.value;
    			switch(selectedCat){
    			case "IT Equipment" :
    				document.getElementById("itA").style.display = "block";
    				document.getElementById("itB").style.display = "block";
    				document.getElementById("itC").style.display = "block";
    				document.getElementById("landA").style.display = "none";
    				document.getElementById("vehA").style.display = "none";
    				document.getElementById("landB").style.display = "none";
    				document.getElementById("vehB").style.display = "none";
    				document.getElementById("landC").style.display = "none";
    				document.getElementById("vehC").style.display = "none";
    				document.getElementById("vehD").style.display = "none";
    				document.getElementById("vehE").style.display = "none";
    				document.getElementById("vehF").style.display = "none";
    				document.getElementById("vehG").style.display = "none";
    				document.getElementById("vehH").style.display = "none";
    				break;
    			case "Furniture and Fitting" :
    				alert("You are registering Office Equipment!");
    				document.getElementById("itA").style.display = "none";
    				document.getElementById("itB").style.display = "block";
    				document.getElementById("itC").style.display = "none";
    				document.getElementById("landA").style.display = "none";
    				document.getElementById("vehA").style.display = "none";
    				document.getElementById("landB").style.display = "none";
    				document.getElementById("vehB").style.display = "none";
    				document.getElementById("landC").style.display = "none";
    				document.getElementById("vehC").style.display = "none";
    				document.getElementById("vehD").style.display = "none";
    				document.getElementById("vehE").style.display = "none";
    				document.getElementById("vehF").style.display = "none";
    				document.getElementById("vehG").style.display = "none";
    				document.getElementById("vehH").style.display = "none";
    				break;
    			case "Vehicle" :
    				alert("You are registering Office Vehicle!");
    				document.getElementById("itA").style.display = "block";
    				document.getElementById("itB").style.display = "none";
    				document.getElementById("itC").style.display = "block";
    				document.getElementById("landA").style.display = "none";
    				document.getElementById("vehA").style.display = "block";
    				document.getElementById("landB").style.display = "none";
    				document.getElementById("vehB").style.display = "block";
    				document.getElementById("landC").style.display = "none";
    				document.getElementById("vehC").style.display = "block";
    				document.getElementById("vehD").style.display = "block";
    				document.getElementById("vehE").style.display = "block";
    				document.getElementById("vehF").style.display = "block";
    				document.getElementById("vehG").style.display = "block";
    				document.getElementById("vehH").style.display = "block";
    				break;
    			case "Land and Building" :
    				alert("You are registering Land and Building!");
    				document.getElementById("itA").style.display = "none";
    				document.getElementById("itB").style.display = "none";
    				document.getElementById("itC").style.display = "none";
    				document.getElementById("landA").style.display = "block";
    				document.getElementById("vehA").style.display = "none";
    				document.getElementById("landB").style.display = "block";
    				document.getElementById("vehB").style.display = "none";
    				document.getElementById("landC").style.display = "block";
    				document.getElementById("vehC").style.display = "none";
    				document.getElementById("vehD").style.display = "none";
    				document.getElementById("vehE").style.display = "none";
    				document.getElementById("vehF").style.display = "none";
    				document.getElementById("vehG").style.display = "none";
    				document.getElementById("vehH").style.display = "none";
    				break;
    			}
    		}
    	}    	
    }
    categoryChecker();
    function checkMe(){
    	const para = document.createElement("input");
		//const node = document.createTextNode("This is a new paragraph.");
		//para.appendChild(node);
		para.setAttribute("type", "hidden");
		para.setAttribute("name", "su");
		para.setAttribute("value","su");
		const element = document.getElementById("div1");
		element.appendChild(para);
    }
    function checkMe00(){
    	const para = document.createElement("input");
		//const node = document.createTextNode("This is a new paragraph.");
		//para.appendChild(node);
		para.setAttribute("type", "hidden");
		para.setAttribute("name", "populate");
		para.setAttribute("value","populate");
		const element = document.getElementById("div01");
		element.appendChild(para);
    }
    </script>