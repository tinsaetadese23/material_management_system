    <%if(request.getAttribute("assetSubCatList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../ARCS");
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
    if(request.getAttribute("total") != null){
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
            <div class="card  card-tabs">
              <div class="card-header custom_card_branding p-0 pt-1">
                <ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
                  <li class="pt-2 px-3"><h3 class="card-title">Asset Return</h3></li>
                  <li class="nav-item">
                    <a class="nav-link links_on_tab_branding active" id="custom-tabs-two-home-tab" data-toggle="pill" href="#custom-tabs-two-home" role="tab" aria-controls="custom-tabs-two-home" aria-selected="true">Asset Classification</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link links_on_tab_branding" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Asset Ownership</a>
                  </li>
                </ul>
              </div>
              <div class="card-body">
                <div class="tab-content" id="custom-tabs-two-tabContent">
                  <div class="tab-pane fade show active" id="custom-tabs-two-home" role="tabpanel" aria-labelledby="custom-tabs-two-home-tab">
    					<form method="post" action = "../ARCS/tag">
    					<div id="div1" class="form-group">
    						<label for="some">Tag</label><br>
    						<input type = "hidden" name ="tr" value ="tr"/>
    						<input style="display:inline-bock" name="tag" type="text" class="" id = "assetClass" required>
    						<button type="submit" class="btn btn-print btn-sm btn-light">
  							<i class="nav-icon fas fa-search"></i> populate
							</button>
    						</div>
    					</form>	
    					<div class="form-group">
    						<label>Tag</label>
    						<input value="<c:out value ="${ast.tagNumber}"/>" readonly type = "text" class="form-control">
    						</div>
    					<div class="form-group">
    						<label>Asset Sub Category</label>
    						<input value="<c:out value ="${ast.assetSubCategory}"/>-<c:out value ="${ast.remark}"/>" readonly type = "text"/ class="form-control">
    						</div>
    					<div class="from-group">
    						<label>Receiving Code</label>
    						<input value="<c:out value ="${ast.status}"/>" readonly type="text" name="receivingCode" class="form-control"/>
    					</div>
    					<div class="from-group">
    						<label>Asset Specifications</label>
    						<p><c:out value ="${ast.assetSpecificText}"/></p>
    					</div>
                  </div>
                  <div  class="tab-pane fade" id="custom-tabs-two-settings" role="tabpanel" aria-labelledby="custom-tabs-two-settings-tab">
				  	<form action = "../ARCS/ret" method = "post">
				  	  <div class="row">
				  	  	 <p>Currently Hold By</p>
				  	  	 <input  value="<c:out value ="${ast.tagNumber}"/>" name="tag" type="hidden" class="" id = "assetClass" required>
  						 <div class="form-group">
    						<hr>
    						<p><strong>ID :</strong></p><c:out value="${emp.id}"></c:out>
    						<p><strong>Name :</strong></p><c:out value="${emp.fullName}"></c:out>
    						<p><strong>Branch :</strong></p>
    						<c:out value="${emp.branch}"></c:out>
    						<p><strong>Dep't :</strong></p>
    						<c:out value="${emp.dept}"></c:out>
				  	  	</div>
				  	  </div>
				  	 
    					<div id = "divsub" class="form-group">
    					<c:if test = "${create == '1'}">
    					<input onclick="checkMe();" value="Return to store"  class="btn links_on_tab_branding" type="submit" class="form-control" id = "assetClass" />
						</c:if>
    						</div>
    				</form>
                  </div>
                </div>
              </div>
              <!-- /.card -->
            </div>
          </div>
          <div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Returns</h3>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Sub-Cat Code</th>
		    					<th style="">Sub-Cat Desc</th>
		    					<th style="">status</th>
		    					<th colspan="3">Action</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("stockList") != null){ %>
	    					<c:forEach var = "stc" items = "${stockList}">
	    					<tr>
	    						<td style="">1</td>
		    					<td><c:out value ="${stc.item}"/></td>
		    					<td><c:out value ="${stc.assetSpec}"/></td>
		    					<td><c:out value ="${stc.status}"/></td>
		    					<td><a href = "../ARCS/view?id=<c:out value ="${stc.id}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../ARCS/delete?id=<c:out value ="${stc.id}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					</tr>
	    					</c:forEach>
	    					<%}%>
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
   			 <form action = "../ARCS/auth" method="POST">
   			 <input name="id" type="hidden" value="<c:out value="${objprp.id}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-4">
   			 	<p><strong>Sub-Cat Code</strong> : <c:out value="${subCatDetail.subCatCode}"></c:out></p>
   			 	<p><strong>Sub-Cat Desc</strong> : <c:out value="${subCatDetail.subCatDesc}"></c:out></p>
   			 	<p><strong>Asset Specific Text</strong> : <c:out value="${objprp.assetSpec}"></c:out></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Unit Price</strong> : <c:out value="${objprp.unitPrice}"></c:out></p>
   			 	<p><strong>Serial No</strong> : <c:out value="${objprp.serialNo}"></c:out></p>
   			 	<p><strong>Sys Generated Tag</strong> : <c:out value="${objprp.sysGeneratedTag}"></c:out></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Issued To</strong> : <c:out value="${objprp.issuedTo}"></c:out></p>
   			 	<p><strong>Status</strong> : <c:out value="${objprp.status }"></c:out></p>
   			 	<p><strong>Branch Level Status</strong> : <c:out value="${objprp.status}"></c:out></p>
   			 	</div>
   			 </div>
   			 <c:if test = "${objprp.authStatus == 'UR' }">
   			 	<select id="sellect" name="action" class="">
		    	<option value="0">Authorize</option>
		    	<option value ="1">Reject</option>
		    	</select>
		    	<button id="bttn" class="btn btn-print btn-sm btn-light" type="submit" value = "save">Authorize</button>
   			 </c:if>
		    	<br>
			    </form>
            <div class="modal-footer justify-content-between">
              <p><strong>Created At</strong> : <c:out value="${objprp.createdAt }"></c:out></p>
              <p><strong>Maker</strong> : <c:out value="${objprp.maker }"></c:out></p>  
              <p><strong>Checker</strong> : <c:out value="${objprp.checker }"></c:out></p>
              <c:if test = "${objprp.authStatus == 'UR' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-warning">Unauthorized</span></p>
              </c:if>
              <c:if test = "${objprp.authStatus == 'AR' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-info">Authorized</span></p>
              </c:if>
              <c:if test = "${objprp.authStatus == 'R' }">
              <p><strong>Status</strong> : <span class="btn btn-print btn-sm btn-danger">Rejected</span></p>
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
    <script>
    function checkMe(){
    	const para = document.createElement("input");
		//const node = document.createTextNode("This is a new paragraph.");
		//para.appendChild(node);
		para.setAttribute("type", "hidden");
		para.setAttribute("name", "su");
		para.setAttribute("value","su");
		const element = document.getElementById("divsub");
		element.appendChild(para);
    }
    function checkMe00(){
    	const para = document.createElement("input");
		//const node = document.createTextNode("This is a new paragraph.");
		//para.appendChild(node);
		para.setAttribute("type", "hidden");
		para.setAttribute("name", "populate");
		para.setAttribute("value","populate");
		const element = document.getElementById("div22");
		element.appendChild(para);
    }
    </script>