    <%if(request.getAttribute("assetSubCatList") == null){
    System.out.println("sending request to asset controller...");
    request.setAttribute("pp","pp");
    RequestDispatcher ds = request.getRequestDispatcher("../ACS/abc");
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
    if(request.getAttribute("stockList") != null){
    totalAssetClassCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
    
    
     <!-- Main content -->
    <section class="content">
    <c:forEach var="roles" items ="${userRoleLists}">
    	<c:if test = "${roles.create == 1 && roles.pageIdNameHolder == 'ASSISS'}">
    	<c:set var="create" value="1" />
    	</c:if>
    	<c:if test = "${roles.update == 1 && roles.pageIdNameHolder == 'ASSISS'}">
    	<c:set var="update" value="1" />
    	</c:if>
    	<c:if test = "${roles.delete == 1 && roles.pageIdNameHolder == 'ASSISS'}">
    	<c:set var="delete" value="1" />
    	</c:if>
    </c:forEach>
       <%if(request.getAttribute("qnf") != null){%>
    <script type="text/javascript">
    window.onload = function(){
        //document.getElementById('bModal').style.display="none";
        document.getElementById('custom-tabs-two-messages-tab').click();
        }
    </script>
    <% }%>
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
     <%if(request.getAttribute("afound") != null || request.getAttribute("qnf") != null){%>
     			<script type="text/javascript">
                window.onload = function(){
                //alert("i am working till here");
                document.getElementById('custom-tabs-two-messages-tab').click();
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
		 <div class="col-12 col-sm-6">
            <div class="card card-tabs">
              <div class="card-header custom_card_branding p-0 pt-1">
                <ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
                  <li class="pt-2 px-3"><h3 class="card-title">Asset Assignment</h3></li>
                  <li class="nav-item">
                    <a class="nav-link active links_on_tab_branding" id="custom-tabs-two-home-tab" data-toggle="pill" href="#custom-tabs-two-home" role="tab" aria-controls="custom-tabs-two-home" aria-selected="true">Find Requester</a>
                  </li>
                  <%if(request.getAttribute("employee") != null){%>
                  <%if(request.getAttribute("afound") != null){%>
                  	<li class="nav-item" >
                    <a  class="nav-link links_on_tab_branding" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Find Asset</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link links_on_tab_branding" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Issue Asset</a>
                  </li>
                  <%}else{ %>
                   <li class="nav-item">
                    <a class="nav-link links_on_tab_branding" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Find Asset</a>
                  </li>
                  <li class="nav-item" style="pointer-events: none;">
                    <a class="nav-link links_on_tab_branding" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Issue Asset</a>
                  </li>
                  <%}}else{ %>
                  <li class="nav-item" style="pointer-events: none;">
                    <a class="nav-link links_on_tab_branding" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Find Asset</a>
                  </li>
                  <li class="nav-item" style="pointer-events: none;">
                    <a class="nav-link links_on_tab_branding" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Issue Asset</a>
                  </li>
                  <%}%>
                </ul>
              </div>
              <div class="card-body">
                <div class="tab-content" id="custom-tabs-two-tabContent">
                  <div class="tab-pane fade show active" id="custom-tabs-two-home" role="tabpanel" aria-labelledby="custom-tabs-two-home-tab">
    				<form method = "post" action = "../ACS/tt">
    				<div class = "row">
    					<div class = "col-md-4">
    						<div id="div02" class="form-group">
    						<label for="some">Employee Id</label><span style="color:red;font-size:18px;text-alignment:center;"> *</span><br>
    						<input class="form-control" value="<c:out value= "${employee.id }"/>"  name="empId" type="number" class="" id = "assetClass" min="1" required>
    						</div>
    					</div>
    					<div class = "col-md-4">
    						<div class="form-group">
    						<label for = "some">Branch Code</label><span style="color:red;font-size:18px;text-alignment:center;"> *</span><br>
    						<input value="<c:out value= "${employee.branch}"/>" class="form-control"  type = "text" name = "brncode" required/>
    						</div>
    					</div>
    				</div>
    					<div class = "form-group">
    						<button onclick = "checkMe()" type="submit" class="btn btn-print btn-sm btn-light">
  							<i class="nav-icon fas fa-search"></i> populate
							</button>
    						</div>
  						 <div class="form-group">
    						<label for="some">Employee Information</label>
    						<hr>
    						<p><strong>ID :</strong></p><c:out value="${employee.id}"></c:out>
    						<p><strong>Name :</strong></p><c:out value="${employee.fullName}"></c:out>
    						<p><strong>Branch :</strong></p>
    						<c:out value="${employee.branch}"></c:out> (<c:out value="${employee.position}"></c:out>)
    						<p><strong>Dep't :</strong></p>
    						<c:out value="${employee.dept}"></c:out>
    						</div>
    					</form>
                  </div>
                  <div class="tab-pane fade" id="custom-tabs-two-messages" role="tabpanel" aria-labelledby="custom-tabs-two-messages-tab">
			  			<form method = "post" action = "../ACS/tt">
			  			<!-- hiddens -->
			  			<input value="<c:out value= "${employee.id }"/>" style="display:inline-bock" name="empId" type="hidden" class="" id = "assetClass" required>
			  			<input value="<c:out value= "${employee.branch }"/>" style="display:inline-bock" name="brncode" type="hidden" class="" id = "assetClass" required>
			  			<!-- hiddens -->
    					<div class="form-group">
    						<label>Asset Sub Category</label>
    						<%-- <select name="assetSubCat" class="form-control">
    						<%if(request.getAttribute("assetSubCatList") != null){ %>
    						<c:forEach var = "assetSubCat" items = "${assetSubCatList}">
    						<c:if test="${assetSubCat.id == subCat.id || assetSubCat.id == stockObj.id}">
    						<option selected value ="<c:out value ="${assetSubCat.id}"/>"><c:out value ="${assetSubCat.subCatCode}"/>-<c:out value ="${assetSubCat.subCatDesc}"/></option>
    						</c:if>
    						<c:if test="${assetSubCat.id != subCat.id }">
    						<option value ="<c:out value ="${assetSubCat.id}"/>"><c:out value ="${assetSubCat.subCatCode}"/>-<c:out value ="${assetSubCat.subCatDesc}"/></option>
    						</c:if>
    						</c:forEach>
    						<%}%>
    						</select> --%>
    						<%if(request.getAttribute("subCat") != null) {%>
    						<input value = "<c:out value="${subCat.subCatCode}"/>-<c:out value="${subCat.subCatDesc}"/>" id ="subCat" type ="text"  class="form-control"
    						 readonly required></input>
    						 <input type="hidden" name="assetSubCat" value = "<c:out value="${subCat.id}" />"/>
    						<%}else{ %>
    						<input type="hidden" name="assetSubCat" value = "<c:out value="${sSubCat.id}" />"/>
    						<input value = "<c:out value="${sSubCat.subCatCode}"/>-<c:out value="${sSubCat.subCatDesc}"/>"
    						id ="subCat" type ="text" name="" class="form-control" readonly placeholder="subCategory" required/>
    						<%} %>
    						<button type="button" class="btn custom_button_branding" data-toggle="modal" data-target="#modal-searching" id="btnfilter">
                			 <i class="nav-icon fas fa-search"></i>
               				</button>
    						</div>
    					<div class="from-group">
    						<label>Required Quantity</label>
    						<%if(request.getAttribute("qty") != null){ %>
    						<input onkeyup="callme()" value="<%out.println(request.getAttribute("qty"));%>"  type="text" name="qty" class="form-control" required/>
    						<span style="color:red;"><%out.println(request.getAttribute("qnf"));%></span>
    						<%}else{ %>
    						<input value="1"  type="text" name="qty" class="form-control"/>
    						<%} %>
    					</div>
    					<div class ="form-group">
    						<label>Serial Generation Mode</label>
    						<select class="form-control" name = "fmode">
    						<option value="0">Default(FIFO)</option>
    						<option value="1">LIFO</option>
    						<option value="2">manual</option>
    						</select>
    					</div>
    					<div id="div01" class  = "form-group">
    					<br>
    					<button onclick = "checkMe00()" type="submit" class="btn btn-print btn-sm btn-light">
  							<i class="nav-icon fas fa-search"></i> populate
							</button>
    					</div>
    				</form>
                  </div>
                  <div  class="tab-pane fade" id="custom-tabs-two-settings" role="tabpanel" aria-labelledby="custom-tabs-two-settings-tab">
				  	 <form method = "post" action = "../ACS/tt">
				  	 <!-- hiddens -->
				  	<input value="<c:out value= "${employee.id }"/>" style="display:inline-bock" name="empId" type="hidden" class="" id = "assetClass" required>
				  	<input name="assetSubCat"  value ="<c:out value ="${subCat.id}"/>" type = "hidden"/>
				  	<input value="<c:out value= "${employee.branch }"/>" style="display:inline-bock" name="brncode" type="hidden" class="" id = "assetClass" required>
				  	 <%if(request.getAttribute("qty") != null){ %>
    						<input value="<%out.println(request.getAttribute("qty"));%>"  type="hidden" name="qty" class="form-control"/>
    						<%}else{ %>
    						<input value="1"  type="hidden" name="qty" class="form-control"/>
    						<%} %>
				  	 <!-- hiddens -->
				  			<hr>
				  		
				  		<p>Asset : <c:out value ="${subCat.subCatDesc}"></c:out></p>
				  		<p> Supplied Quantity : <%out.println(request.getAttribute("qty"));%></p>
				  		<p>Issued To Branch : <c:out value= "${employee.branch}"/></p>
				  		<p>Representative Employee : <c:out value= "${employee.fullName}"/></p>
				  		<c:if test = "${subCat.mode == 4}">
				  		<label>First No.</label><input type ="text" name = "fno" required><br>
				  		<label>Last No.</label>	<input type ="text" name = "lno" required><br>
				  		</c:if>
				  		<label>Requisition No.</label><input type ="text"/ name = "reqno" required><br>
				  		<div class="row">
				  		<div class="col-md-8">
					  		<div  id=""  class="form-group">
	    						<label for="some">TAG(unordered)</label>
	    						<select   name = "lstags" class = "form-control" multiple = "multiple">
	    						<%int count = 0; %>
	    						<%if(request.getAttribute("manual") != null){%>
	    						 <c:forEach var = "tag" items = "${tags}">
	    						 <option value="<c:out value="${tag.tagNumber}"/>"">(<%out.println(++count);%>)<c:out value="${tag.tagNumber}"></c:out></option>
	    						 </c:forEach>
	    						<%}else{ %>
	    						 <c:forEach var = "tag" items = "${tags}">
	    						<option selected style="pointer-events: none;" value="<c:out value="${tag.tagNumber}"/>"">(<%out.println(++count);%>)<c:out value="${tag.tagNumber}"></c:out></option>
	    						 </c:forEach>
	    						<%}%>
	    						</select>
	    						</div>	
				  		</div>
				  		<div class="col-md-4">
				  				<div  id=""  class="form-group">
	    						<label for="some">List of serials</label>
	    						<select name="lsserial" class = "form-control" multiple="multiple">
	    						<%int counter = 0; %>
	    						<%if(request.getAttribute("manual") != null){%>
	    							<c:forEach var = "serials" items = "${listSerials}">
	    						 <option value="<c:out value="${serials.serialNum}"/>">(<%out.println(++counter);%>)<c:out value="${serials.serialNum}"></c:out></option>
	    						 </c:forEach>
	    						<%}else{%>
	    							<c:forEach var = "serials" items = "${listSerials}">
	    						 <option style="pointer-events: none;"  selected value="<c:out value="${serials.serialNum}"/>">(<%out.println(++counter);%>)<c:out value="${serials.serialNum}"></c:out></option>
	    						 </c:forEach>
	    						<%} %>
	    						</select>
	    						</div>
				  		</div>
				  		<%-- <div style="overflow-y: scroll !important; max-height:300px;" class="form-group">
				  			<table class=" table table-bordered">
				  			<thead>
				  			<tr>
				  			<th>No.</th>
				  			<th>Asset Specific Text.</th>
				  			</tr>
				  			</thead>
				  			<%int ccount = 0; %>
				  			 <c:forEach var = "serials" items = "${listSerials}">
				  			 <tr>
				  			    <td><%out.println(++ccount);%></td>
	    						<td> <p><c:out value="${serials.remark}"></c:out><p></p></td>
	    						</tr>
	    						 </c:forEach>
	    						 </table>
				  		</div> --%>
				  		</div>
    					<div class="form-group">
    					<c:if test = "${create == '1'}">
    					<input value="Issue Asset"  class="btn btn-primary" type="submit" class="form-control" id = "assetClass" />
						</c:if>
    						</div>
    						</form>
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
    				<h3 class="card-title">Show Issuance</h3>
    				<br><br>
    				<form id ="srform" method = "post" action = "../ACS/search"> 			
    				<div class=""><input  value="<c:out value ="${asc.status}"/>" id = "srinput" name ="sr" placeholder = "Search By Issue Reference" type = "text" class = "form-control"/></div>
    				</form>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Issue Ref</th>
		    					<th style="">Total Item Issued</th>
		    					<th style="">status</th>
		    					<th colspan="3">Action</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("searchResult") == null){ %>
	    					<%if(request.getAttribute("stockList") != null){ %>
	    					<%int ccount = 0; %>
	    					<c:forEach var = "stc" items = "${stockList}">
	    					<tr>
	    						<td style=""><%out.println(++ccount);%></td>
		    					<td><c:out value ="${stc.issueRef}"/></td>
		    					<td><c:out value ="${stc.quantity }"/></td>
		    					<td><c:out value ="${stc.status}"/></td>
		    					<td><a href = "../ACS/view?issueref=<c:out value ="${stc.issueRef}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../ACS/delete?issueref=<c:out value ="${stc.issueRef}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					<%}else{ %>
	    					<%if(request.getAttribute("none") != null){ %>
	    							<p style="color:gray;text-align:center;">Oops none found!!</p>
	    							<%}%>
	    							<%int ccount = 0; %>
	    						<c:forEach var = "stc" items = "${searchResult}">
	    					<tr>
	    						<td style=""><%out.println(++ccount);%></td>
		    					<td><c:out value ="${stc.issueRef}"/></td>
		    					<td><c:out value ="${stc.supplierId }"/></td>
		    					<td><c:out value ="${stc.status}"/></td>
		    					<td><a href = "../ACS/view?id=<c:out value ="${stc.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../ACS/delete?issueref=<c:out value ="${stc.issueRef}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					</tr>
	    					</c:forEach>
	    					<%} %>
	    					</tbody>
    					</table>
    					<%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">No Unauthorized Record</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    					<%if(request.getAttribute("stotal") == null){%>
    						<button type = "button" class="btn custom_button_branding">Unauthorized : <%out.println(totalAssetClassCount);%></button>
    						<%}else{%>
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
    
    
    <!-- modal start -->
<div class="modal fade" id="objprp">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Asset Detail</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
   			 <form action = "../ACS/auth" method="POST">
   			 <input name="issueref" type="hidden" value="<c:out value="${objprp.issueRef}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-4">
   			 	<p><strong>Sub-Cat Code</strong> : <c:out value="${subCatDetail.subCatCode}"></c:out></p>
   			 	<p><strong>Sub-Cat Desc</strong> : <c:out value="${subCatDetail.subCatDesc}"></c:out></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Quantity</strong> : <c:out value="${objprp.quantity}"></c:out></p>
   			 	<p><strong>Unit Price</strong> : <c:out value="${objprp.fcurrency1}"></c:out></p>
   			 	<p><strong>Total Price</strong> : <c:out value="${objprp.fcurrency2}"></c:out></p>
   			 	<c:set var="c" value="${objprp.quantity - 1}" />
   			 	<p><strong>Serial No</strong> : <c:out value="${objprp.serialNo}"></c:out> and <span style="color:red;"><c:out value="${c}"></c:out><i>  more</i></span></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Requisition No.</strong> : <c:out value="${objprp.reqNo}"></c:out></p>
   			 	<p><strong>Issue ref.</strong> : <c:out value="${objprp.issueRef}"></c:out></p>
   			 	<p><strong>Issued To</strong> : <c:out value="${objprp.issuedTo}"></c:out></p>
   			 	<p><strong>Status</strong> : <c:out value="${objprp.status }"></c:out></p>
   			 	</div>
   			 </div>
   			 <c:if test = "${objprp.authStatus == 'UI' }">
   			 	<select id = "sellect" name="action" class="">
		    	<option value="0">Authorize</option>
		    	<option value ="1">Reject</option>
		    	</select>
		    	<textarea placeholder='Why are you rejecting?' id='rejection-comment' name='rejection-comment' style='display:none;'></textarea>
		    	<button id = "bttn" class="btn btn-print btn-sm btn-light" type="submit" value = "save">Authorize</button>
   			 </c:if>
		    	<br>
			    </form>
            <div class="modal-footer justify-content-between">
              <p><strong>Created At</strong> : <c:out value="${objprp.createdAt }"></c:out></p>
              <p><strong>Maker</strong> : <c:out value="${objprp.maker }"></c:out></p>  
              <p><strong>Checker</strong> : <c:out value="${objprp.checker }"></c:out></p>
              <c:if test = "${objprp.authStatus == 'UI' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-warning">Unauthorized</span></p>
              </c:if>
              <c:if test = "${objprp.authStatus == 'AI' }">
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
     <form action="ACS/fltr" method="post">
     <%}else{ %>
     <form action="../ACS/fltr" method="post">
     <%} %>
    <div class="modal-content">
  
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Filter Assets</h5>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
      </div>
      <div class="modal-body">
      <input value="<c:out value= "${employee.id }"/>" style="display:inline-bock" name="empId" type="hidden" class="" id = "assetClass" required>
			  			<input value="<c:out value= "${employee.branch }"/>" style="display:inline-bock" name="brncode" type="hidden" class="" id = "assetClass" required>
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
	<select id="ssearch" name='class' class="form-control"  onchange="this.form.submit()">
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
		document.getElementById("landD").style.display = "none";
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
    				document.getElementById("landD").style.display = "none";
    				break;
    			case "Furniture and Fitting" :
    				alert("You are registering Office Equipment!");
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
    				document.getElementById("landD").style.display = "block";
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
    				document.getElementById("landD").style.display = "none";
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
    				document.getElementById("landD").style.display = "none";
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
		const element = document.getElementById("div02");
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
    function callme(){
    	iat = document.getElementById("custom-tabs-two-settings-tab");
    	iat.style.pointerEvents = "none";
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