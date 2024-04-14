    <%if(request.getAttribute("aSubCatList") == null && request.getAttribute("fSubCatList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../POCS");
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
            <h1 class="m-0">Purchase Order</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Purchase Order</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
        <%
    int totalPoCount = 0;
    if(request.getAttribute("total") != null){
    totalPoCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
    
     <!-- Main content -->
    <section class="content">
    <c:forEach var="roles" items ="${userRoleLists}">
    	<c:if test = "${roles.create == 1 && roles.pageIdNameHolder == 'PO'}">
    	<c:set var="create" value="1" />
    	</c:if>
    	<c:if test = "${roles.update == 1 && roles.pageIdNameHolder == 'PO'}">
    	<c:set var="update" value="1" />
    	</c:if>
    	<c:if test = "${roles.delete == 1 && roles.pageIdNameHolder == 'PO'}">
    	<c:set var="delete" value="1" />
    	</c:if>
    </c:forEach>
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
     <%if(request.getAttribute("objprp") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#objprp" data-backdrop="static" data-keyboard="false">
                  
                </button>
                <script type="text/javascript">
                window.onload = function(){
                document.getElementById('bModal').style.display="none";
                document.getElementById('bModal').click();
                }
                </script>
    <%}%>
    
    <%if(request.getAttribute("filter") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-searching" data-backdrop="static" data-keyboard="false">
                  
                </button>
                <script type="text/javascript">
                window.onload = function(){
                document.getElementById('bModal').style.display="none";
                document.getElementById('bModal').click();
                }
                </script>
    <%}%>
    <div class="container-fluid">
    	<div class = "row">
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header custom_card_branding">
    				<h3 class="card-title">Goods</h3>
    				</div>
    				<%if(request.getAttribute("editPo")== null){%>
    				<form action="../POCS/create" method = "POST">
    					<div class="card-body" style="overflow-y: scroll !important; height:450px;">
    					<div class = "row">
    					<div class = "col-md-6">
    						<div class="form-group">
    						<label for="some">Purchase Order Number(Bid Award No.)<span style="color:red; font-size:18px;"> *</span></label>
    						<input value = '' placeholder=''  name="orderId" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label>Sub-Category<span style="color:red; font-size:18px;"> *</span></label>
    						<%if(request.getParameter("sSubCat") == null) {%>
    						<input value = "<c:out value="${sSubCat.subCatCode}"/><c:out value="${sSubCat.subCatDesc}"/>" id ="subCat" type ="text"  class="form-control"
    						 readonly required></input>
    						 <input type="hidden" name="subCategory" value = "<c:out value="${sSubCat.id}" />"/>
    						<%}else{ %>
    						<input id ="subCat" type ="text" name="subCategory" class="form-control" readonly placeholder="subCategory" required/>
    						<%} %>
    						<button type="button" class="btn custom_button_branding" data-toggle="modal" data-target="#modal-searching" id="btnfilter">
                			 <i class="nav-icon fas fa-search"></i>
               				</button>
    						</div>
    						<div class="form-group">
    						<label>Unit Measurement<span style="color:red; font-size:18px;"> *</span></label>
    						<select name="unitMeasurement" class="form-control">
    						<c:forEach var = "unit" items = "${unitList}">
    						<option value ="<c:out value ="${unit.id}"/>"><c:out value ="${unit.unitDesc}"/></option>
    						</c:forEach>
    						</select>
    						</div>
    						<div class="form-group">
    						<label for="some">Quantity<span style="color:red; font-size:18px;"> *</span></label>
    						<input id ="qty" oninput = "calcTot()" placeholder='No of items'  name="quantity" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Unit Price<span style="color:red; font-size:18px;"> *</span></label>
    						<input id ="up" oninput = "calcTot()" placeholder='No of items'  name="unitPrice" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class = "col-md-6">
    						<div class="form-group">
    						<label for="some">Total Price</label>
    						<input id="tp" name="totalPrice" type="hidden" class="form-control"  required readonly>
    						<input onchange="calcTot2()" id="yp" name="yyyy" type="text" class="form-control"  required readonly>
    						</div>
    						<div class="form-group">
    						<label>Supplier<span style="color:red; font-size:18px;"> *</span></label>
    						<select name="supplier" class="form-control">
    						<c:forEach var = "supplier" items = "${supplierList}">
    						<option value ="<c:out value ="${supplier.id}"/>"><c:out value ="${supplier.supplierName}"/></option>
    						</c:forEach>
    						</select>
    						</div>
    						<div class="form-group">
    						<label>Purchase Type<span style="color:red; font-size:18px;"> *</span></label>
    						<select name="purchaseType" class="form-control">
    						<option value ="Direct">Direct</option>
    						<option value ="Bid">Bid</option>
    						<option value ="Unknonwn">Unknown</option>
    						</select>
    						</div>
    						<div class="form-group">
    						<label for="some">Purchased Date<span style="color:red; font-size:18px;"> *</span></label>
    						<input value = '' placeholder=''  name="purchaseDate" type="date" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Delivery Date<span style="color:red; font-size:18px;"> *</span></label>
    						<input value = '' placeholder=''  name="DeliveryDate" type="date" class="form-control" id = "assetClass" required>
    						</div>	
    					</div>
    					<div class="form-group">
    						<label for="some">Remark</label>
    						<textarea placeholder=''  name="remark" type="text" class="form-control" id = "assetClass"></textarea>
    						</div>
    					</div>
    					</div>
    					<div class="card-footer">
    					<c:if test = "${create == '1'}">
    					<input value="Submit" type = "submit" class="btn custom_button_branding"/>
						</c:if>
    					</div>
    				</form>
    				<%}else{ %>
    				<form action="../POCS/update" method = "POST">
    				<!-- hiddens -->
    				<input value="<c:out value="${editPo.id}" />" placeholder=''  name="id" type="hidden" class="form-control" id = "assetClass" required>
    				<!-- hiddens -->
    					<div class="card-body" style="overflow-y: scroll !important; height:450px;">
    					<div class="row">
    					<div class= "col-md-6">
    						<div class="form-group">
    						<label for="some">Purchase Order Number(Bid Award No.)<span style="color:red; font-size:18px;"> *</span></label>
    						<input readonly value="<c:out value="${editPo.poCode}" />" placeholder=''  name="orderId" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label>Sub-Category<span style="color:red; font-size:18px;"> *</span></label>
    						<input value = "<c:out value="${poSub.subCatCode}" />-<c:out value="${poSub.subCatDesc}" />" id ="subCat" type ="text"  class="form-control"
    						 readonly required></input>
    						 <input type="hidden" name="subCategory" value = "<c:out value="${editPo.subCategory}" />"/>
    						</div>
    						<div class="form-group">
    						<label>Unit Measurement<span style="color:red; font-size:18px;"> *</span></label>
    						<select name="unitMeasurement" class="form-control">
    						<c:forEach var = "unit" items = "${unitList}">
    						<c:if test ="${editPo.unitMeasurement == unit.id }">
    						<option selected value ="<c:out value ="${unit.id}"/>"><c:out value ="${unit.unitDesc}"/></option>
    						</c:if>
    						<c:if test ="${editPo.unitMeasurement != unit.id }">
    						<option value ="<c:out value ="${unit.id}"/>"><c:out value ="${unit.unitName}"/>--<c:out value ="${unit.unitDesc}"/></option>
    						</c:if>
    						</c:forEach>
    						</select>
    						</div>
    						<div class="form-group">
    						<label for="some">Quantity<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editPo.quantity}" />"  id ="qty" oninput = "calcTot()" placeholder='No of items'  name="quantity" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Unit Price<span style="color:red; font-size:18px;"> *</span></label>
    						<input  id ="up" oninput = "calcTot()" value="<c:out value="${editPo.fcurrency1}" />" placeholder='No of items'  name="unitPrice" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class = "col-md-6">
    						<div class="form-group">
    						<label for="some">Total Price</label>
    						<input id="tp" name="totalPrice" value="<c:out value="${editPo.total}" />" type="hidden" class="form-control"  required readonly>
    						<input onchange="calcTot2()" id="yp" value="<c:out value="${editPo.fcurrency2}" />"  name="yyyy" type="text" class="form-control"  required readonly>
    						</div>
    						<div class="form-group">
    						<label>Supplier<span style="color:red; font-size:18px;"> *</span></label>
    						<select name="supplier" class="form-control">
    						<c:forEach var = "supplier" items = "${supplierList}">
    						<c:if test = "${editPo.supplier == supplier.id}">
    						<option selected value ="<c:out value ="${supplier.id}"/>"><c:out value ="${supplier.supplierName}"/></option>
    						</c:if>
    						<c:if test = "${editPo.supplier != supplier.id}">
    						<option value ="<c:out value ="${supplier.id}"/>"><c:out value ="${supplier.supplierName}"/></option>
    						</c:if>
    						</c:forEach>
    						</select>
    						</div>
    						<div class="form-group">
    						<label>Purchase Type<span style="color:red; font-size:18px;"> *</span></label>
    						<select name="purchaseType" class="form-control">
    						<c:if test = "${editPo.purchaseTypes == 'Direct'}">
    						<option selected value ="Direct">Direct</option>
    						<option value ="Bid">Bid</option>
    						<option value ="Unknonwn">Unknown</option>
    						</c:if>
    						<c:if test = "${editPo.purchaseTypes == 'Bid'}">
    						<option value ="Direct">Direct</option>
    						<option selected value ="Bid">Bid</option>
    						<option value ="Unknonwn">Unknown</option>
    						</c:if>
    						<c:if test = "${editPo.purchaseTypes == 'Unknown'}">
    						<option value ="Direct">Direct</option>
    						<option selected value ="Bid">Bid</option>
    						<option value ="Unknonwn">Unknown</option>
    						</c:if>
    						</select>
    						</div>
    						<div class="form-group">
    						<label for="some">Purchased Date<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editPo.orderDate}" />" placeholder=''  name="purchaseDate" type="date" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Delivery Date<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editPo.deliveryDate}" />" value = '' placeholder=''  name="DeliveryDate" type="date" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					</div>
    						<div class="form-group">
    						<label for="some">Remark</label>
    						<textarea  placeholder=''  name="remark" type="text" class="form-control" id = "assetClass"> <c:out value="${editPo.description}" /></textarea>
    						</div>
    					</div>
    					<div class="card-footer">
    						<input value="Update" type = "submit" class="btn custom_button_branding"/>
    						<button class="btn btn-default"><a style="text-decoration:none;" href="../views/famsPO.jsp">Cancel</a></button>
    						
    					</div>
    				</form>
    				<%} %>
    			</div>
    		</div>
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Purchase Orders</h3>
    				<br><br>
    				<form id ="srform" method = "post" action = "../POCS/srch"> 			
    				<div class=""><input  value="<c:out value ="${asc.poCode}"/>" id = "srinput" name ="sr" placeholder = "Search By PO Code" type = "text" class = "form-control"/></div>
    				</form>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>PO No.</th>
		    					<th style="">Order Type</th>
		    					<th style="">Sub-Category</th>
		    					<th colspan="3">Action</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("searchResult") == null){ %>
	    					<%if(request.getAttribute("poList") != null){ %>
	    					<%int counter = 0; %>
	    					<c:forEach var = "po" items = "${poList}">
	    					<tr>
	    						<td style=""><%out.println(++counter);%></td>
		    					<td><c:out value ="${po.poCode}"/></td>
		    					<td><c:out value ="${po.purchaseTypes}"/></td>
		    					<td><c:out value ="${po.subCatNameHolder}"/></td>
		    					<td><a href = "../POCS/view?id=<c:out value ="${po.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${update == '1'}">
		    					<td><a href = "../POCS/edit?id=<c:out value ="${po.id}"/>"><i class="far fa-edit"></i></a></td>
								</c:if>
								<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../POCS/delete?id=<c:out value ="${po.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					<%}else{ %>
	    					<%int counter = 0; %>
	    					<%if(request.getAttribute("none") != null){ %>
	    							<p style="color:gray;text-align:center;">Oops none found!!</p>
	    							<%}%>
	    							<c:forEach var = "po" items = "${searchResult}">
	    					<tr>
	    						<td style=""><%out.println(++counter);%></td>
		    					<td><c:out value ="${po.poCode}"/></td>
		    					<td><c:out value ="${po.purchaseTypes}"/></td>
		    					<td><c:out value ="${po.subCatNameHolder}"/></td>
		    				    <td><a href = "../POCS/view?id=<c:out value ="${po.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<td><a href = "../POCS/edit?id=<c:out value ="${po.id}"/>"><i class="far fa-edit"></i></a></td>
		    					<td><a onclick="return deleteMe()" href = "../POCS/delete?id=<c:out value ="${po.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
	    					
	    					</tr>
	    					</c:forEach>
	    					<%} %>
	    					</tbody>
    					</table>
    					<%if(totalPoCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">No Unauthorized Record</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    					<%if(request.getAttribute("stotal") == null){ %>
    						<button type = "button" class="btn custom_button_branding">Unauthorized : <%out.println(totalPoCount);%></button>
    						<%}else{ %>
    						<button type = "button" class="btn custom_button_branding">Search Result : <%out.println(request.getAttribute("stotal"));%></button>
    						<%} %>
    					</div>
    				</form>
    			</div>
    		</div>
    	</div>
    </div>
    
    </section>
    </div>
    <script type="text/javascript">
    function calcTot(){
    	var unitPrice = document.getElementById("up").value;
    	var quantity  = document.getElementById("qty").value;
    	document.getElementById("tp").value = reverseFormat(document.getElementById("up")) * quantity;
    	document.getElementById("yp").value = reverseFormat(document.getElementById("up")) * quantity;
    	calc2(document.getElementById("yp"));
    	/* let value = document.getElementById("up").value;
		// remove any non-digit characters
		value = value.replace(/\D/g, "");
		// add comma after every three digits
		value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		// set the value of the input element
		document.getElementById("up").value = value; */
    	let value = document.getElementById("up").value;
    	// remove any non-digit characters except the decimal point
    	value = value.replace(/[^0-9.]/g, "");
    	// split the value into integer and fractional parts
    	let parts = value.split(".");
    	// add comma after every three digits in the integer part
    	parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    	// join the parts with a decimal point
    	value = parts.join(".");
    	// set the value of the input element
    	document.getElementById("up").value = value;	
    }
   function calc2(input){
	 	let value = input.value;
    	// remove any non-digit characters except the decimal point
    	value = value.replace(/[^0-9.]/g, "");
    	// split the value into integer and fractional parts
    	let parts = value.split(".");
    	// add comma after every three digits in the integer part
    	parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    	// join the parts with a decimal point
    	value = parts.join(".");
    	// set the value of the input element
    	input.value = value;	
   }
    function reverseFormat(input) {
    	// get the value of the input element
    	let value = input.value;
    	// remove any commas
    	value = value.replace(/,/g, "");
    	// set the value of the input element
    	input.value = value;
    	return input.value;
    	}
    function setSubCategory(){
    	var parent = document.getElementById("parent");
    	var input = document.createElement("input");
    	input.type = "hidden";
    	input.value = "default value";
    	input.name = "subCatIsSelected";
    	parent.appendChild(input);

    }
    </script>
<!--

//-->


<!-- modal start -->
<div class="modal fade" id="objprp">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Purchase Order Detail</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
   			 <form action = "../POCS/auth" method="POST">
   			 <input name="id" type="hidden" value="<c:out value="${objprp.id}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-4">
   			 	<p><strong>Purchase Order No.</strong> : <c:out value="${objprp.poCode }"></c:out></p>
   			 	<p><strong>Sub Category</strong> : <c:out value="${asro.subCatCode }"></c:out>-<c:out value="${asro.subCatDesc }"></c:out></p>
   			 	<p><strong>Unit Measurement</strong> : <c:out value="${unit.unitName }"></c:out></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Quantity</strong> : <c:out value="${objprp.quantity }"></c:out></p>
   			 	<p><strong>Unit Price</strong> : <c:out value="${objprp.fcurrency1 }"></c:out></p>
   			 	<p><strong>Total Price</strong> : <c:out value="${objprp.fcurrency2 }"></c:out></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Supplier</strong> : <c:out value="${supp.supplierName }"></c:out></p>
   			 	<p><strong>Purchase Date</strong> : <c:out value="${objprp.orderDate }"></c:out></p>
   			 	<p><strong>Delivery Date</strong> : <c:out value="${objprp.deliveryDate }"></c:out></p>
   			 	<p><strong>Remark</strong> : <c:out value="${objprp.description }"></c:out></p>
   			 	</div>
   			 </div>
   			 <c:if test = "${objprp.authStatus == 'U' }">
   			 	<select id="sellect" name="action" class="">
		    	<option value="0">Authorize</option>
		    	<option value ="1">Reject</option>
		    	</select>
		    	<textarea placeholder='Why are you rejecting?' id='rejection-comment' name='rejection-comment' style='display:none;'></textarea>
		    	<button id="bttn" class="btn btn-print btn-sm btn-light" type="submit" value = "save">Authorize</button>
   			 </c:if>
		    	<br>
			    </form>
            <div class="modal-footer justify-content-between">
              <p><strong>Created At</strong> : <c:out value="${objprp.createdAt }"></c:out></p>
              <p><strong>Maker</strong> : <c:out value="${objprp.maker }"></c:out></p>  
              <p><strong>Checker</strong> : <c:out value="${objprp.checker }"></c:out></p>
              <c:if test = "${objprp.authStatus == 'U' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-warning">Unauthorized</span></p>
              </c:if>
              <c:if test = "${objprp.authStatus == 'A' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-info">Authorized</span></p>
              </c:if>
              <c:if test = "${objprp.authStatus == 'R' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-danger">Rejected</span></p>
              <p><strong>Reason for Rejection</strong> : <c:out value="${objprp.rejection_reaon}"></c:out></p>
              </c:if>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      </div>
<!-- modal end -->

<!-- modal for searching asset sub category -->
 <div class="modal fade" id="modal-searching">
     <div class="modal-dialog">
     <%if(request.getAttribute("fromServer") != null){ %>
     <form action="POCS/fltr" method="post">
     <%}else{ %>
     <form action="../POCS/fltr" method="post">
     <%} %>
    <div class="modal-content">
  
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Filter Issues</h5>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
      </div>
      <div class="modal-body">
      <p class='text-danger bg-light text-center' id="message"></p>
           <%
	if (request.getAttribute("error") != null)
		out.println("<p class='text-danger bg-light text-center'>"
				+ request.getAttribute("error") + "</p>");
     %>
     <h6 id="pid" style="display:none; color:green;">Tag No is never used or only used ALONE.</h6>
<div class="row" id="parent">
<%if(request.getAttribute("fCatList") == null){ %>
	<div class = "col-md-4">
	<label>Class</label>
	<select id="ssearch" name='class' class="form-control" onchange="this.form.submit()">
		<c:forEach var="aClass" items="${aClassList}">
			<option value="<c:out value ="${aClass.id}"/>"><c:out value ="${aClass.assetClassDesc}"/></option>
		</c:forEach>
	</select>
	</div>
	<div class = "col-md-4">
	<label>Category</label>
	<select id="ssearch" name='category' class="form-control" onchange="this.form.submit()">
	<c:forEach var="aCat" items="${aCatList}">
			<option value="<c:out value ="${aCat.id}"/>"><c:out value ="${aCat.assetCatDesc}"/></option>
		</c:forEach>
	
	</select>
	</div>
	<div class = "col-md-4">
	<label>Sub-Category</label>
	<select id="ssearch" name='subCategory' class="form-control" onchange="setSubCategory()">
	<c:forEach var="aSubCat" items="${aSubCatList}">
			<option value="<c:out value ="${aSubCat.id}"/>"><c:out value ="${aSubCat.subCatDesc}"/></option>
		</c:forEach>
	</select>
	</div>
	<%}else { %>
	<div class = "col-md-4">
	<label>Class</label>
	<select id="ssearch" name='class' class="form-control" onchange="this.form.submit()">
		<c:forEach var="fClass" items="${fClassList}">
		<c:if test="${fClass.id == sClass.id}">
			<option value="<c:out value ="${fClass.id}"/>" selected><c:out value ="${fClass.assetClassDesc}"/></option>
		</c:if>
		<c:if test="${fClass.id != sClass.id }">
			<option value="<c:out value ="${fClass.id}"/>"><c:out value ="${fClass.assetClassDesc}"/></option>
		</c:if>
		</c:forEach>
	</select>
	</div>
	<div class = "col-md-4">
	<label>Category</label>
	<select id="ssearch" name='category' class="form-control" onchange="this.form.submit()">
	<c:forEach var="fCat" items="${fCatList}">
		<c:if test = "${fCat.id == sCategory.id }">
			<option value="<c:out value ="${fCat.id}"/>" selected><c:out value ="${fCat.assetCatDesc}"/></option>
		</c:if>
		<c:if test = "${fCat.id != sCategory.id }">
			<option value="<c:out value ="${fCat.id}"/>"><c:out value ="${fCat.assetCatDesc}"/></option>
		</c:if>
		</c:forEach>
	
	</select>
	</div>
	<div class = "col-md-4">
	<label>Sub-Category</label>
	<select id="ssearch" name='subCategory' class="form-control"  onchange="setSubCategory()">
	<c:forEach var="fSubCat" items="${fSubCatList}">
			<option id="selectedSubCat" value="<c:out value ="${fSubCat.id}"/>"><c:out value ="${fSubCat.subCatDesc}"/></option>
		</c:forEach>
	</select>
	</div>
	<%} %>
</div>
      </div>
      <div class="modal-footer">
      <%
      	request.setAttribute("branchCode",session.getAttribute("sBranchCode"));
		request.setAttribute("dept",session.getAttribute("sEmpDept"));
		//request.setAttribute("typeUser",session.getAttribute("sTypeUser"));
		request.setAttribute("userId",session.getAttribute("sUserId"));
		//System.out.println("typeuser for showIssue"+session.getAttribute("sTypeUser"));
      
      %>
         <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <%-- <input type="hidden" name= "tuser" value="<%out.println(session.getAttribute("sTypeUser"));%>"> --%>
        <input type="hidden" name= "suser" value="<%out.println(session.getAttribute("sUserId"));%>">
        <input type="hidden" name= "ddept" value="<%out.println(session.getAttribute("sEmpDept"));%>">
         <input type="hidden" name= "bbranch" value="<%out.println(session.getAttribute("sBranchCode"));%>">
         <input type = "hidden" name = "fromTaskView" value = "taskview"/>
        <input type="submit" value = "Select" class="btn custom_button_branding">
      </div>
    </div>
    </form>
  </div>
        <!-- /.modal-dialog -->
      </div>
    
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->