  <%if(request.getAttribute("depreciationList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../DCS");
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
            <h1 class="m-0">Depreciation Management</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Depreciation</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
    
    <%
    int totalAssetClassCount = 0;
    if(request.getAttribute("depreciationList") != null){
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
    <div class="container-fluid">
    	<div class = "row">
    		<div class = "col-md-6">
    			<div class = "card card-primary">
    				<div class="card-header">
    				<h3 class="card-title">Asset Class</h3>
    				</div>
    				<%if(request.getAttribute("editDepreciation")== null){ %>
    				<form method="POST" action = "../DCS/create">
    					<div class="card-body">
    						<div class="form-group">
    						<label for="some">Depreciation Code</label>
    						<input name="deprCode" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Depreciation Description</label>
    						<input name = "deptDesc" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Depreciation rate</label>
    						<input placeholder = 'only decimal points' name = "deprRate" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    						<input style="pointer-events:none;" type = "submit" class="btn btn-primary" value="Add"/>
    					</div>
    				</form>
    				<%}else{ %>
    					<form method="POST" action = "../DCS/update">
    					<!-- hiddens-->
    					<input value="<c:out value="${editDepreciation.id}" />" name = "id" type="hidden" class="form-control" id = "assetClass" required>
    					<!-- hiddens -->
    					<div class="card-body">
    						<div class="form-group">
    						<label for="some">Depreciation Code</label>
    						<input value="<c:out value="${editDepreciation.code}" />" name="deprCode" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Depreciation Description</label>
    						<input value="<c:out value="${editDepreciation.description}" />" name = "deptDesc" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Depreciation rate</label>
    						<input value="<c:out value="${editDepreciation.rate}" />" placeholder = 'only decimal points' name = "deprRate" type="text" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    						<input style="pointer-events:none;" type = "submit" class="btn btn-primary" value="Update"/>
    					</div>
    				</form>
    				
    				<%}%>
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
		    					<th>Depr. Code</th>
		    					<th style="">Depr. Desc</th>
		    					<th style="">Depr. Rate</th>
		    					<th colspan="2">Actions</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("depreciationList") != null){ %>
	    					<c:forEach var = "depr" items = "${depreciationList}">
	    					<tr>
	    						<td style="">1</td>
		    					<td><c:out value ="${depr.code}"/></td>
		    					<td><c:out value ="${depr.description}"/></td>
		    					<td><c:out value ="${depr.rate}"/></td>
		    					<td style="pointer-events:none;"><form action = "../DCS/edit" method="POST">
		    					<input type ="hidden" name="id" value="<c:out value ="${depr.id}"/>"/>
		    					<button style="background-color:white;border:1px solid white;" type="submit"><i class="far fa-edit"></i></button>
		    					</form></td>
		    					<td style="pointer-events:none;"><form action = "../DCS/delete" method="POST" onsubmit="return deleteMe()">
		    					<input type ="hidden" name="id" value="<c:out value ="${depr.id}"/>"/>
		    					<button style="background-color:white;border:1px solid white;" type="submit"><i class="fas fa-times btn-danger"></i></button>
		    					</form></td>
		    					
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					</tbody>
    					</table>
    					<%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">There are 0 Depreciations.</p>
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