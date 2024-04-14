   <%if(request.getAttribute("assetClass") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../ACCS");
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
            <h1 class="m-0">Asset Class Management</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Asset Class</li>
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
    <c:forEach var="roles" items ="${userRoleLists}">
    	<c:if test = "${roles.create == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
    	<c:set var="create" value="1" />
    	</c:if>
    	<c:if test = "${roles.update == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
    	<c:set var="update" value="1" />
    	</c:if>
    	<c:if test = "${roles.delete == 1 && roles.pageIdNameHolder == 'ASSCLA'}">
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
    				<h3 class="card-title">Asset Class</h3>
    				</div>
    				<%if(request.getAttribute("oneAssetClass")== null){ %>
    				<form method="POST" action = "../ACCS/create">
    					<div class="card-body">
    						<div class="form-group">
    						<label for="some">Asset Class Code<span style="color:red; font-size:18px;"> *</span></label>
    						<input name="assetClass" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Asset Description<span style="color:red; font-size:18px;"> *</span></label>
    						<input name = "assetDescription" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    					<c:if test = "${create ==  '1'}">
    						<input type = "submit" class="btn custom_button_branding" value="Add Class"/>
    					</c:if>
    					</div>
    				</form>
    				<%}else{ %>
    				 <form method="POST" action = "../ACCS/update">
    					<div class="card-body">
    						<div class="form-group">
    						<label for="some">Asset Class Code<span style="color:red; font-size:18px;"> *</span></label>
    						<input readonly value ="<c:out value ="${oneAssetClass.assetClassCode}"/>" name="assetClass" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Asset Description<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value ="${oneAssetClass.assetClassDesc}"/>" name = "assetDescription" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    						<input type = "submit" class="btn btn-primary" value="Update"/>
    						<input type ="hidden" name="id" value="<c:out value ="${oneAssetClass.id}"/>"/>
    						<a class = "btn btn-default" style="text-decoration:none;" href="../views/famsAssetClass.jsp">Cancel</a>
    					</div>
    				</form>
    				
    				<%}%>
    			</div>
    		</div>
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Show Asset Class</h3>
    				<br><br>
    				<form id ="srform" method = "post" action = "../ACCS/search"> 			
    				<div class=""><input  value="<c:out value ="${asc.assetClassCode}"/>" id = "srinput" name ="sr" placeholder = "searc by class code" type = "text" class = "form-control"/></div>
    				</form>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Class Code</th>
		    					<th style="">Class Desc</th>
		    					<th class="text-center" colspan="3">Actions</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("searchResult") == null){ %>
	    					<%if(request.getAttribute("assetClass") != null){ %>
	    					<%int counter = 0; %>
	    					<c:forEach var = "asset" items = "${assetClass}">
	    					<tr>
	    						<td style=""><%out.println(++counter);%></td>
		    					<td><c:out value ="${asset.assetClassCode}"/></td>
		    					<td><c:out value ="${asset.assetClassDesc}"/></td>
		    					<td><a href = "../ACCS/view?id=<c:out value ="${asset.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${update ==  '1'}">
		    					<td><a href = "../ACCS/edit?id=<c:out value ="${asset.id}"/>"><i class="far fa-edit"></i></a></td>
		    					</c:if>
		    					<c:if test = "${delete ==  '1'}">
		    					<td><a onclick="return deleteMe()" href = "../ACCS/delete?id=<c:out value ="${asset.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
		    					</c:if>
		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					<%}else{ %>
	    					<%int counter = 0; %>
	    						<%if(request.getAttribute("none") != null){ %>
	    							<p style="color:gray;text-align:center;">Oops none found!!</p>
	    							<%}%>
	    							<c:forEach var = "asset" items = "${searchResult}">
	    					<tr>
	    						<td style=""><%out.println(++counter);%></td>
		    					<td><c:out value ="${asset.assetClassCode}"/></td>
		    					<td><c:out value ="${asset.assetClassDesc}"/></td>
		    					<td><a href = "../ACCS/view?id=<c:out value ="${asset.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${update ==  '1'}">
		    					<td><a href = "../ACCS/edit?id=<c:out value ="${asset.id}"/>"><i class="far fa-edit"></i></a></td>
		    					</c:if>
		    					<c:if test = "${delete ==  '1'}">
		    					<td><a onclick="return deleteMe()" href = "../ACCS/delete?id=<c:out value ="${asset.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
		    					</c:if>	
	    					</tr>
	    					</c:forEach>
	    					<%} %>
	    					</tbody>
    					</table>
    					<%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">No Unauthorized Record.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    					<%if(request.getAttribute("stotal") == null){ %>
    						<button type = "button" class="btn custom_button_branding">Unauthorized : <%out.println(totalAssetClassCount);%></button>
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
    <!-- modals -->
    <div class="modal fade" id="objprp">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Asset Class</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
   			 <form action = "../ACCS/auth" method="POST">
   			 <input name="id" type="hidden" value="<c:out value="${objprp.id}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-6"><p><strong>Class Name</strong> : <c:out value="${objprp.assetClassCode }"></c:out></p></div>
   			 	<div class="col-md-6"><strong>Description</strong> : <c:out value="${objprp.assetClassDesc }"></c:out></div>
   			 </div>
   			 <c:if test = "${objprp.authStatus == 'U' }">
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
    <!-- end modals -->
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->