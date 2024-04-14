    <%if(request.getAttribute("supplierList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../SUCS");
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
            <h1 class="m-0">Supplier Registration</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Supplier</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
        <%
    int totalSuppCount = 0;
    if(request.getAttribute("total") != null){
    totalSuppCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
    
     <!-- Main content -->
    <section class="content">
     <c:forEach var="roles" items ="${userRoleLists}">
    	<c:if test = "${roles.create == 1 && roles.pageIdNameHolder == 'SUPP'}">
    	<c:set var="create" value="1" />
    	</c:if>
    	<c:if test = "${roles.update == 1 && roles.pageIdNameHolder == 'SUPP'}">
    	<c:set var="update" value="1" />
    	</c:if>
    	<c:if test = "${roles.delete == 1 && roles.pageIdNameHolder == 'SUPP'}">
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
    <div class="container-fluid">
    	<div class = "row">
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header custom_card_branding">
    				<h3 class="card-title">Supplier</h3>
    				</div>
    				<%if(request.getAttribute("editSupplier")== null){ %>
    				<form action="../SUCS/create" method = "POST">
    					<div class="card-body">
    					<div class = "row">
    					<div class = "col-md-6">
    						<div class="form-group">
    						<label for="some">Supplier Name<span style="color:red; font-size:18px;"> *</span></label>
    						<input placeholder=''  name="supplierName" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Contact Person<span style="color:red; font-size:18px;"> *</span></label>
    						<input id ="up" oninput = "calcTot()" placeholder=''  name="contactPerson" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Phone Number<span style="color:red; font-size:18px;"> *</span></label>
    						<input id ="up" oninput = "calcTot()" placeholder=''  name="contactNumber" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Email</label>
    						<input id ="qty" oninput = "calcTot()" placeholder=''  name="email" type="email" class="form-control" id = "assetClass" >
    						</div>
    					</div>
    					<div class = "col-md-6">
    						<div class="form-group">
    						<label for="some">Address<span style="color:red; font-size:18px;"> *</span></label>
    						<input  value="" placeholder=''  name="address" type="text" class="form-control" id = "assetClass"  required>
    						</div>
    						<div class="form-group">
    						<label for="some">Post No.</label>
    						<input value = "" placeholder=''  name="zipCode" type="text" class="form-control" id = "assetClass" >
    						</div>
    						<div class="form-group">
    						<label for="some">Website</label>
    						<input value = '' placeholder=''  name="website" type="text" class="form-control" id = "assetClass" >
    						</div>
    					</div>
    					
    					</div>
    					</div>
    					<div class="card-footer">
    					<c:if test = "${create == '1'}">
    					<input value="Add Supplier" type = "submit" class="btn custom_button_branding"/>
						</c:if>
    					</div>
    				</form>
    				<%}else{ %>
    					<form action="../SUCS/update" method = "POST">
    					<!-- hiddens -->
    					<input value="<c:out value="${editSupplier.id}"/>" placeholder=''  name="id" type="hidden" class="form-control" id = "assetClass" >
    					<!-- hiddens -->
    					<div class="card-body">
    					<div class = "row">
    					<div class="col-md-6">
    						<div class="form-group">
    						<label for="some">Supplier Name<span style="color:red; font-size:18px;"> *</span></label>
    						<input readonly value="<c:out value="${editSupplier.supplierName}"/>" placeholder=''  name="supplierName" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Contact Person<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editSupplier.contactPerson}"/>" id ="up" oninput = "calcTot()" placeholder=''  name="contactPerson" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Phone Number<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editSupplier.contactNumber}"/>" id ="up" oninput = "calcTot()" placeholder=''  name="contactNumber" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Email</label>
    						<input  value="<c:out value="${editSupplier.email}"/>" id ="qty" oninput = "calcTot()" placeholder=''  name="email" type="text" class="form-control" id = "assetClass" >
    						</div>
    					</div>
    					<div class = "col-md-6">
    						<div class="form-group">
    						<label for="some">Address<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editSupplier.address}"/>" value="" placeholder=''  name="address" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Post No.</label>
    						<input value="<c:out value="${editSupplier.zipCode}"/>" value = '' placeholder=''  name="zipCode" type="text" class="form-control" id = "assetClass" >
    						</div>
    						<div class="form-group">
    						<label for="some">Website</label>
    						<input value="<c:out value="${editSupplier.website}"/>" value = '' placeholder=''  name="website" type="text" class="form-control" id = "assetClass">
    						</div>
    					</div>
    					</div>
    					</div>
    					<div class="card-footer">
    						<input value="Update" type = "submit" class="btn custom_button_branding"/>
    						<a class = "btn btn-default" style="text-decoration:none;" href="../views/famsSupplier.jsp">Cancel</a>
    						
    					</div>
    				</form>
    				<%} %>
    			</div>
    		</div>
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Show Suppliers</h3>
    				<br><br>
    				<form id ="srform" method = "post" action = "../SUCS/search"> 			
    				<div class=""><input  value="<c:out value ="${asc.supplierName}"/>" id = "srinput" name ="sr" placeholder = "Search By Supplier Name" type = "text" class = "form-control"/></div>
    				</form>
    				</div>
    				<form>
    					<div class="card-body">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th style="width:60px">Supplier Name</th>
		    					<th style="">Contact Person</th>
		    					<th style="">Contact Number</th>
		    					<th class="text-center" colspan="3">Actions</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("searchResult") == null){ %>
	    					<%if(request.getAttribute("supplierList") != null){%>
	    					<%int counter = 0; %>
	    					<c:forEach var = "supp" items = "${supplierList}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
		    					<td><c:out value ="${supp.supplierName}"/></td>
		    					<td><c:out value ="${supp.contactPerson}"/></td>
		    					<td><c:out value ="${supp.contactNumber}"/></td>
		    					<td><a href = "../SUCS/view?id=<c:out value ="${supp.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${update == '1'}">
		    					<td><a href = "../SUCS/edit?id=<c:out value ="${supp.id}"/>"><i class="far fa-edit"></i></a></td>
								</c:if>
								<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../SUCS/delete?id=<c:out value ="${supp.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					<%}else{ %>
	    					<%int counter = 0; %>
	    						<%if(request.getAttribute("none") != null){ %>
	    							<p style="color:gray;text-align:center;">Oops none found!!</p>
	    							<%}%>
	    						<c:forEach var = "supp" items = "${searchResult}">
	    					<tr>
	    						<td style=""><%out.println(++counter);%></td>
		    					<td><c:out value ="${supp.supplierName}"/></td>
		    					<td><c:out value ="${supp.contactPerson}"/></td>
		    					<td><c:out value ="${supp.contactNumber}"/></td>
		    					<td><a href = "../SUCS/view?id=<c:out value ="${supp.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${update == '1'}">
		    					<td><a href = "../SUCS/edit?id=<c:out value ="${supp.id}"/>"><i class="far fa-edit"></i></a></td>
								</c:if>
								<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../SUCS/delete?id=<c:out value ="${supp.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					
	    					</tr>
	    					</c:forEach>
	    					<%} %>
	    					</tbody>
    					</table>
    					<%if(totalSuppCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">No Unathorized Record.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    					<%if(request.getAttribute("stotal") == null){ %>
    						<button type = "button" class="btn custom_button_branding">Unauthorized : <%out.println(totalSuppCount);%></button>
    						<%}else{ %>
    						<button type = "button" class="btn custom_button_branding">Search Result : <%out.println(request.getAttribute("stotal"));%></button>
    						<%}%>
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
    	var total = document.getElementById("tp").value = unitPrice*quantity;
    	
    }
    </script>

<!-- modal begin -->
<div class="modal fade" id="objprp">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Supplier Detail</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
   			 <form action = "../SUCS/auth" method="POST">
   			 <input name="id" type="hidden" value="<c:out value="${objprp.id}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-6">
   			 	<p><strong>Supplier Name</strong> : <c:out value="${objprp.supplierName }"></c:out></p>
   			 	<p><strong>Contact Person</strong> : <c:out value="${objprp.contactPerson }"></c:out></p>
   			 	<p><strong>Contact</strong> : <c:out value="${objprp.contactNumber }"></c:out></p>
   			 	</div>
   			 	<div class="col-md-6">
   			 	<p><strong>Email</strong> : <c:out value="${objprp.email }"></c:out></p>
   			 	<p><strong>Address</strong> : <c:out value="${objprp.address }"></c:out></p>
   			 	<p><strong>ZipCode</strong> : <c:out value="${objprp.zipCode }"></c:out></p>
   			 	<p><strong>Website</strong> : <c:out value="${objprp.website }"></c:out></p>
   			 	</div>
   			 </div>
   			 <c:if test = "${objprp.authStatus == 'U' }">
   			 	<select id = "sellect" name="action" class="">
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

    
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->