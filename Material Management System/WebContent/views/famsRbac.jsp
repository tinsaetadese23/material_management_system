 <%if(request.getAttribute("pagesList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../RBACS");
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
            <h1 class="m-0">Roles Based Access Control</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Roles</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
    
    <%
    int totalRolesCount = 0;
    int totalRoleDefCount = 0;
    int totalAccessSize = 0;
    int counter = 0;
    if(request.getAttribute("pagesList") != null){
    	totalRolesCount = Integer.parseInt(request.getAttribute("totRolesSize").toString());
    	totalRoleDefCount = Integer.parseInt(request.getAttribute("totRolDefnSize").toString());
    	totalAccessSize = Integer.parseInt(request.getAttribute("totAccessSize").toString());

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
     <%if(request.getAttribute("rolesObj") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#rolesObj" data-backdrop="static" data-keyboard="false">
                  
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
    		<div class="">
    		<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">User Role Mappings</h3>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:200px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th style="">User ID</th>
		    					<th>Role ID</th>
		    					<th colspan="1">Action</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("accessList") != null){
	    						counter = 0;
	    						%>
	    					<c:forEach var = "access" items = "${accessList}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
	    						<td><c:out value ="${access.username}"/></td>
		    					<td><c:out value ="${access.rolename}"/></td>
		    					<td><a onclick="return deleteMe()" href = "../RBACS/delm1?id=<c:out value ="${access.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					</tbody>
    					</table>
    					<%if(totalAccessSize == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">There are 0 Mappings.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    						<button type = "button" class="btn btn-info">Total : <%out.println(totalAccessSize);%></button>
    						<button id='bModal' type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-newUserRole" data-backdrop="static" data-keyboard="false">
                            + Add Mappings
                		    </button>
    					</div>
    				</form>
    			</div>
    		</div>
    		<div class = "">
    		<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Role Definition</h3>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:200px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Role Name</th>
		    					<th style="">Role Desc</th>
		    					<th colspan="1">Action</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("rolDefinitionList") != null){
	    						counter = 0;
	    						%>
	    					<c:forEach var = "rolDefn" items = "${rolDefinitionList}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
		    					<td><c:out value ="${rolDefn.roleName}"/></td>
		    					<td><c:out value ="${rolDefn.roleDefinition}"/></td>
		    					<td><a onclick="return deleteMe()" href = "../RBACS/delm2?id=<c:out value ="${rolDefn.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>		    			
		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					</tbody>
    					</table>
    					<%if(totalRoleDefCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">There are 0 Definitions.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    						<button type = "button" class="btn btn-info">Total : <%out.println(totalRoleDefCount);%></button>
    						<button id='bModal' type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-newRole" data-backdrop="static" data-keyboard="false">
                            + Add Roles
                		    </button>
    					</div>
    				</form>
    			</div>
    		</div>
    		</div>
    		<div class = "col-md-6">
    		<div class = "">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Role-Function Mappings</h3>
    				</div>
    			<form method="post" action = "../RBACS/find">
    		   <input style="margin-top:10px;width:150px;margin-left:10px;display:iniline;" placeholder ="#Role ID" value ="" name="rolId" type="text" class="form-control" id = "assetClass" required>
    			<button style="margin-left:10px;" type="submit" class="btn btn-default" style="">Search</button>
    			</form>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:480px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Function</th>
		    					<th>Create</th>
		    					<th>Update</th>
		    					<th>Delete</th>
		    					<th>View</th>
		    					<th>Auth</th>
		    					<th colspan="2">Actions</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("roleLists") != null){ 
	    					counter = 0;
	    					%>
	    					<c:forEach var = "roles" items = "${roleLists}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
		    					<td><c:out value ="${roles.pageIdNameHolder}"/></td>
		    					<td><c:out value ="${roles.create}"/></td>
		    					<td><c:out value ="${roles.update}"/></td>
		    					<td><c:out value ="${roles.delete}"/></td>
		    					<td><c:out value ="${roles.view}"/></td>
		    					<td><c:out value ="${roles.auth}"/></td>
		    					<td><a href = "../RBACS/edit?id=<c:out value ="${roles.id}"/>"><i class="far fa-edit"></i></a></td>
		    					<td><a onclick="return deleteMe()" href = "../RBACS/delete?id=<c:out value ="${roles.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					</tbody>
    					</table>
    					<%if(totalRolesCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">There are 0 Roles.</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    						<button type = "button" class="btn btn-info">Total : <%out.println(totalRolesCount);%></button>
    						<button id='bModal' type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-roles" data-backdrop="static" data-keyboard="false">
                            + Add Mapping
                		    </button>
    					</div>
    				</form>
    			</div>
    		</div>
    		<div class = "">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">User Creation</h3>
    				</div>
    			<div class = "card-body">
    			<form method="post" action = "../RBACS/emp">
    		   		<div class = "row">
    		   			<label>Employee Id</label>
    		   			<div class = "col-sm-4">
    		   			 	<input name = "emp" type = "number" class = "form-control">
    		   			 	
    		   			</div>
    		   			<div class = "col-sm-2">
    		   			<input type = "submit" value= "Fetch" class = "form-control">
    		   			</div>
    		   		</div>
    		   	</form>
    		   	<form method= "post" action ="../RBACS/user">
    		   			<%if(request.getAttribute("emp") != null){ %>
    		   				<%if(request.getAttribute("ue") != null){%>
    		   					<br>
    		   					<p>Employee Found and User Exists. <a data-toggle="modal" data-target="#modal-resetPassword" data-backdrop="static" data-keyboard="false" href="#" style="color:red;">Reset Password?</a></p>
    		   				<%}else{ %>
    		   			<br>
    						<div class = "row">
    							<div class = "col-sm-4">
	    							<p>Employee Found!</p>
	    							 <div class = "form-group">
	    					 		<label>ID</label>
	    					 		<p><c:out value="${emp.id}"></c:out></p>
	    							 </div>
	    						    <div class = "form-group">
	    					 		<label>Full Name</label>
	    					 		<p><c:out value="${emp.fullName}"></c:out></p>
	    					        </div>
	    					         <div class = "form-group">
	    					 		<label>Position</label>
	    					 		<p><c:out value="${emp.position}"></c:out></p>
	    					        </div>
	    							</div>
    							<div class = "col-sm-4">
    							<input value = "<c:out value="${emp.id}"></c:out>" name = "empId" autocomplete="off" type = "hidden" class = "form-control"/>
    								<p>Credentials</p>
	    							<div class = "form-group">
	    					 		<label>User Name</label>
	    					 		<input name = "username" autocomplete="off" type = "text" class = "form-control"/>
	    							 </div>
	    						    <div class = "form-group">
	    					 		<label>Password</label>
	    					 		<input name = "password" autocomplete="off" type = "password" class = "form-control"/>
	    					        </div>
	    							</div>
    							</div>
    							<input type = "submit" value = "Create User" class = "form-control col-sm-3">
    					<%}
    		   			}else{%>
    					 <p>Employee not Found!</p>
    					<%}%>
  				</form>
    		   		</div>
    			</div>
    		</div>
    			
    		</div>
    	</div>
    </div>
    
    </section>
    </div>
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
    
     <div class="modal fade" id="modal-roles">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Role</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    				<form method="POST" action = "../RBACS/create">
    						<div class="form-group">
    						<label>Role</label>
    						<select name="rolId" class="form-control">
    						<%if(request.getAttribute("rolDefinitionList") != null ) {%>
    						<c:forEach var ="roleDefn" items ="${rolDefinitionList}">
    						<option value ="<c:out value ="${roleDefn.id}"/>"><c:out value ="${roleDefn.roleName}"/></option>
    						</c:forEach>
    						<%} %>
    						</select>
    						</div>
    						<div class="form-group">
    						<label>Function</label>
    						<select name="rolFunction" class="form-control">
    						<%if(request.getAttribute("pagesList") != null ) {%>
    						<c:forEach var ="pages" items ="${pagesList}">
    						<option value ="<c:out value ="${pages.id}"/>"><c:out value ="${pages.pageName}"/></option>
    						</c:forEach>
    						<%} %>
    						</select>
    						</div>
    						 <div class="form-group">
                       		  <div class="form-check">
                          	  <input value="ve" name="access" class="form-check-input" type="checkbox" checked>
                         	   <label class="form-check-label">View</label>
                       		   </div>
                        	   <div class="form-check">
                       		   <input value="cr" name="access" class="form-check-input" type="checkbox" >
                        	   <label class="form-check-label">Create</label>
                        	    </div>
                        	   <div class="form-check">
                        	   <input value = "up" name="access" class="form-check-input" type="checkbox">
                          	   <label class="form-check-label">Update</label>
                      		   </div>
                      		   <div class="form-check">
                        	   <input value="dl" name = "access" class="form-check-input" type="checkbox">
                          	   <label class="form-check-label">Delete</label>
                      		   </div>
                      		   <div class="form-check">
                        	   <input value="au" name = "access" class="form-check-input" type="checkbox">
                          	   <label class="form-check-label">Authorize</label>
                      		   </div>
                      		</div>
    						<button class="btn btn-primary">Save</button>
    						<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    				</form>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
      
           <div class="modal fade" id="modal-newRole">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Role Definition</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    				<form method="POST" action = "../RBACS/createRole">
    						<div class="form-group">
    						<label for="some">Role Name</label>
    						<input name = "roleName" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Description</label>
    						<input name = "roleDefn" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<button class="btn btn-primary">Save</button>
    						<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    				</form>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
    
     <div class="modal fade" id="modal-newUserRole">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">User Role Mapping</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    				<form method="POST" action = "../RBACS/createAccess">
    							<div class="form-group">
    						<label>User ID</label>
    						<select name="userId" class="form-control">
    						<%if(request.getAttribute("userList") != null ) {%>
    						<c:forEach var ="userList" items ="${userList}">
    						<option value ="<c:out value ="${userList.id}"/>"><c:out value ="${userList.username}"/></option>
    						</c:forEach>
    						<%} %>
    						</select>
    						</div>
    							<div class="form-group">
    						<label>Role</label>
    						<select name="rolId" class="form-control">
    						<%if(request.getAttribute("rolDefinitionList") != null ) {%>
    						<c:forEach var ="roleDefn" items ="${rolDefinitionList}">
    						<option value ="<c:out value ="${roleDefn.id}"/>"><c:out value ="${roleDefn.roleName}"/></option>
    						</c:forEach>
    						<%} %>
    						</select>
    						</div>
    						<button class="btn btn-primary">Save</button>
    						<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    				</form>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
      <!-- modal for editing roles -->
      <div class="modal fade" id="rolesObj">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Role Function Mapping</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    				<form method="POST" action = "../RBACS/update">
    						<div class="form-group">
    						<label>Role</label>
    						<select name="rolId" class="form-control">
    						<%if(request.getAttribute("rolesObj") != null ) {%>
    						<c:forEach var ="roleDefn" items ="${rolesObj}">
    						<option  value ="<c:out value ="${roleDefn.rolId}"/>"><c:out value ="${roleDefn.roleNameHolder}"/></option>
    						</c:forEach>
    						<%} %>
    						</select>
    						</div>
    						<div class="form-group">
    						<label>Function</label>
    						<select name="rolFunction" class="form-control">
    						<%if(request.getAttribute("rolesObj") != null ) {%>
    						<c:forEach var ="pages" items ="${rolesObj}">
    						<option value ="<c:out value ="${pages.pageId}"/>"><c:out value ="${pages.pageIdNameHolder}"/></option>
    						</c:forEach>
    						<%} %>
    						</select>
    						</div>
    					<c:forEach var ="roles" items ="${rolesObj}">
    						 <div class="form-group">
                       		  <div class="form-check">
                       		  <c:if test = "${roles.view == 1}">
                          	  <input value="ve" name="access" class="form-check-input" type="checkbox" checked>
                          	  </c:if>
                          	  <c:if test = "${roles.view == 0}">
                          	  <input value="ve" name="access" class="form-check-input" type="checkbox" >
                          	  </c:if>
                         	   <label class="form-check-label">View</label>
                       		   </div>
                        	   <div class="form-check">
                        	   <c:if test = "${roles.create == 1}">
                       		   <input value="cr" name="access" class="form-check-input" type="checkbox" checked >
                       		   </c:if>
                       		    <c:if test = "${roles.create == 0}">
                       		   <input value="cr" name="access" class="form-check-input" type="checkbox"  >
                       		   </c:if>
                        	   <label class="form-check-label">Create</label>
                        	    </div>
                        	   <div class="form-check">
                        	   <c:if test = "${roles.update == 1}">
                        	   <input value = "up" name="access" class="form-check-input" type="checkbox" checked>
                        	   </c:if>
                        	   <c:if test = "${roles.update == 0}">
                        	   <input value = "up" name="access" class="form-check-input" type="checkbox" >
                        	   </c:if>
                          	   <label class="form-check-label">Update</label>
                      		   </div>
                      		   <div class="form-check">
                      		   <c:if test = "${roles.delete == 1}">
                        	   <input value="dl" name = "access" class="form-check-input" type="checkbox" checked>
                        	   </c:if>
                        	   <c:if test = "${roles.delete == 0}">
                        	   <input value="dl" name = "access" class="form-check-input" type="checkbox" >
                        	   </c:if>
                          	   <label class="form-check-label">Delete</label>
                      		   </div>
                      		   <div class="form-check">
                      		   <c:if test = "${roles.auth == 1}">
                        	   <input value="au" name = "access" class="form-check-input" type="checkbox" checked>
                        	   </c:if>
                        	   <c:if test = "${roles.auth == 0}">
                        	   <input value="au" name = "access" class="form-check-input" type="checkbox" >
                        	   </c:if>
                          	   <label class="form-check-label">Auth</label>
                      		   </div>
                      		</div>
    						<input type = "hidden" name = "id" value = "<c:out value ="${roles.id}"/>"/>
    					    <input type = "hidden" name = "rolFunction" value = "<c:out value ="${roles.pageId}"/>"/>
    						<input type = "hidden" name = "rolName" value = "<c:out value ="${roles.roleNameHolder}"/>"/>
    					    
    						
                      	</c:forEach>
    						<button class="btn btn-primary">Update</button>
    						<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    				</form>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
           <div class="modal fade" id="modal-resetPassword">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">User password reset</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    				<form method="POST" action = "../RBACS/reset">
    						<div class="form-group">
    						<label for="some">Username</label>
    						<input readonly value = "<%out.println(request.getAttribute("user"));%>" name = "username" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Password</label>
    						<input name = "password" type="password" class="form-control" id = "assetClass" required>
    						</div>
    						<button class="btn btn-primary">Reset</button>
    						<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    				</form>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
    
   