 <%if(request.getAttribute("assetUnit") == null){
    System.out.println("sending request to asset controller JSP...");
    RequestDispatcher ds = request.getRequestDispatcher("../UCS");
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
            <h1 class="m-0">Asset Unit Management</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Asset Unit</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
    
    <%
    int totalAssetClassCount = 0;
    int counter = 0;
    if(request.getAttribute("assetUnit") != null){
    totalAssetClassCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
    
    
     <!-- Main content -->
    <section class="content">
    <c:forEach var="roles" items ="${userRoleLists}">
    	<c:if test = "${roles.create == 1 && roles.pageIdNameHolder == 'ASSUN'}">
    	<c:set var="create" value="1" />
    	</c:if>
    	<c:if test = "${roles.update == 1 && roles.pageIdNameHolder == 'ASSUN'}">
    	<c:set var="update" value="1" />
    	</c:if>
    	<c:if test = "${roles.delete == 1 && roles.pageIdNameHolder == 'ASSUN'}">
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
    				<h3 class="card-title">Unit</h3>
    				</div>
    				<%if(request.getAttribute("editUnit")== null){%>
    				<form method="POST" action = "../UCS/create">
    					<div class="card-body">
    						<div class="form-group">
    						<label for="some">Unit Code/Name<span style="color:red; font-size:18px;"> *</span></label>
    						<input name="unitName" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Unit Description<span style="color:red; font-size:18px;"> *</span></label>
    						<input name = "unitDesc" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    					<c:if test = "${create == '1'}">
    					<input type = "submit" class="btn custom_button_branding" value="Add Unit"/>
						</c:if>
    					</div>
    				</form>
    				<%}else{%>
    				<form method="POST" action = "../UCS/update">
    				<!-- hiddens -->
    					<input value="<c:out value="${editUnit.id}"/>" name="id" type="hidden" class="form-control" id = "assetClass" required>
    				<!-- hiddens -->
    					<div class="card-body">
    						<div class="form-group">
    						<label for="some">Unit Code/Name<span style="color:red; font-size:18px;"> *</span></label>
    						<input readonly value = "<c:out value ="${editUnit.unitName}"/>" name="unitName" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Unit Description<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editUnit.unitDesc}"/>" name = "unitDesc" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    						<input type = "submit" class="btn btn-primary" value="Update"/>
    						<button class="btn btn-default"><a style="text-decoration:none;" href="../views/famsAssetUnit.jsp">Cancel</a></button>
    					</div>
    				</form>
    				
    				<%}%>
    			</div>
    		</div>
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Show Units</h3>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Unit Code/Name</th>
		    					<th style="">Unit Desc</th>
		    					<th colspan="2">Actions</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("assetUnit") != null){ %>
	    					<c:forEach var = "asset" items = "${assetUnit}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
		    					<td><c:out value ="${asset.unitName}"/></td>
		    					<td><c:out value ="${asset.unitDesc}"/></td>
		    					<%-- <td><form action = "../UCS/view" method="POST">
		    					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
		    					<button style="background-color:white;border:1px solid white;" type="submit"><i class="far fa-eye"></i></button>
		    					</form></td> --%>
		    					<td><a href="../UCS/view?id=<c:out value ="${asset.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${update == '1'}">
		    					<td><a href="../UCS/edit?id=<c:out value ="${asset.id}"/>"><i class="far fa-edit"></i></a></td>
								</c:if>
								<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href="../UCS/delete?id=<c:out value ="${asset.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					</tbody>
    					</table>
    					<%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">There are 0 Units.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    						<button type = "button" class="btn custom_button_branding">Total : <%out.println(totalAssetClassCount);%></button>
    					</div>
    				</form>
    			</div>
    		</div>
    	</div>
    </div>
    
    </section>
    </div>
    <!-- modal begin -->
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
   			 <form action = "../UCS/auth" method="POST">
   			 <input name="id" type="hidden" value="<c:out value="${objprp.id}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-6"><p><strong>Unit Name</strong> : <c:out value="${objprp.unitName }"></c:out></p></div>
   			 	<div class="col-md-6"><strong>Unit Description</strong> : <c:out value="${objprp.unitDesc }"></c:out></div>
   			 </div>
   			 <c:if test = "${objprp.authStatus == 'U' }">
   			 	<select id = "sellect" name="action" class="">
		    	<option value="0">Authorize</option>
		    	<option value ="1">Reject</option>
		    	</select>
		    	<textarea placeholder='Why are you rejecting?' id='rejection-comment' name='rejection-comment' style='display:none;'></textarea>
		    	<button id= "bttn" class="btn btn-print btn-sm btn-light" type="submit" value = "save">Authorize</button>
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
      </div>
    <!-- modal end -->
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
    
   