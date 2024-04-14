      <%if(request.getAttribute("asubList") == null){
   // System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../DMC");
   ds.forward(request,response);
    }%>
    <%boolean dbrd = false; %>
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
            <h1 class="m-0">Dashboard</h1>
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
    <!-- /.content-header -->
    
    
     <!-- Main content -->
    <section class="content">
     <%if(request.getAttribute("userRoleLists") != null){%>
            <c:forEach var="roles" items ="${userRoleLists}">
            <c:if test = "${roles.view == 1 && roles.pageIdNameHolder == 'DBRD'}">
            <%if(!dbrd){ %>
    <div class="row">
          <div class="col-12 col-sm-6 col-md-3">
            <div class="info-box">
              <span class="info-box-icon bg-primary elevation-1"><i class="fas fa-cog"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">ASSETS</span>
                <span class="info-box-number">
                  <small class="badge badge-primary right"><c:out value="${dashboarddata.totalAsset}"></c:out></small>
                </span>
              </div>
              <!-- /.info-box-content -->
            <a href="../views/famsAsset.jsp" class="small-box-footer">View <i class="fas fa-arrow-circle-right"></i></a>
            </div>
            <!-- /.info-box -->
          </div>
          <!-- /.col -->
          <div class="col-12 col-sm-6 col-md-3">
            <div class="info-box mb-3">
              <span class="info-box-icon bg-danger elevation-1"><i class="fas fa-columns"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Assigned Assets</span>
                <span class="info-box-number">
                <small class="badge badge-danger right"><c:out value="${dashboarddata.totalAssignedAsset }"></c:out></small>
                </span>
              </div>
              <!-- /.info-box-content -->
            </div>
            <!-- /.info-box -->
          </div>
          <!-- /.col -->

          <!-- fix for small devices only -->
          <div class="clearfix hidden-md-up"></div>

          <div class="col-12 col-sm-6 col-md-3">
            <div class="info-box mb-3">
              <span class="info-box-icon bg-success elevation-1"><i class="fas fa-shopping-cart"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">In-Stock Assets</span>
                <span class="info-box-number">
               <small class="badge badge-success right"><c:out value="${dashboarddata.totalInStockAsset }"></c:out></small>
                </span>
              </div>
              <!-- /.info-box-content -->
             </div>
            <!-- /.info-box -->
          </div>
          <!-- /.col -->
          <div class="col-12 col-sm-6 col-md-3">
            <div class="info-box mb-3">
              <span class="info-box-icon bg-secondary elevation-1"><i class="fas fa-users"></i></span>

              <div class="info-box-content">
                <span class="info-box-text">Suppliers</span>
                <span>
               <small class="badge badge-secondary right"> <c:out value="${dashboarddata.totalSupplier}"></c:out></small>
                </span>
              </div>
              <!-- /.info-box-content -->
             <a href="../views/famsSupplier.jsp" class="small-box-footer">View <i class="fas fa-arrow-circle-right"></i></a>
            </div>
            <!-- /.info-box -->
          </div>
          <!-- /.col -->
        </div>
        <!-- /.row -->
      <div class ="row">
      	<div class = "col-md-4">
      			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Assets Per Class</h3>
    				</div>
    			<div class="card-body" style="overflow-y: scroll !important; max-height:250px;">
    				<c:forEach var = "asset" items = "${repoList}">
		    			<c:out value ="${asset.classDesc}"/>
		    			<span class="badge badge-info right">  <c:out value ="${asset.total}"/></span>
		    			<br>
	    			</c:forEach>
    			
    					</div>
    					<div class="card-footer">
    					</div>
    			</div>
      	</div>
      	<div class = "col-md-4">
      		<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Assigned Assets Per Category</h3>
    				</div>
    			<div class="card-body" style="overflow-y: scroll !important; max-height:550px;">
    			<table class="table table-striped">
    			<thead>
    				<tr>
    				 <th>Category</th>
    				 <th>Total</th>
    				</tr>
    			</thead>
    				<c:forEach var = "asset" items = "${catList}">
    				<tr>
    					<td><c:out value ="${asset.classDesc}"/></td>
    					<td><c:out value ="${asset.total}"/></td>
    				</tr>
    				
	    			</c:forEach>
	    		</table>
    			
    					</div>
    					<div class="card-footer">
    					</div>
    			</div>
      	</div>
      	<div class = "col-md-4">
      		<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">In-Stock Asset Per Category</h3>
    				</div>
    			<div class="card-body" style="overflow-y: scroll !important; max-height:350px;">
    				<table class="table table-striped">
    			<thead>
    				<tr>
    				 <th>Category</th>
    				 <th>Total</th>
    				</tr>
    			</thead>
    				<c:forEach var = "asset" items = "${acatList}">
    				<tr>
    					<td><c:out value ="${asset.classDesc}"/></td>
    					<td><c:out value ="${asset.total}"/></td>
    				</tr>
    				
	    			</c:forEach>
	    		</table>
    			
    					</div>
    					<div class="card-footer">
    					</div>
    			</div>
      	</div>
      </div>
      <div class ="row" style="margin-top:-250px;">
      	<div class ="col-md-4">
      	<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Assets Issued to you</h3>
    				</div>
    			<div class="card-body" style="overflow-y: scroll !important; max-height:150px;">
    			<%int counter = 0; %>
    				<c:forEach var = "asset" items = "${listStocks}">
    					<p><%out.println(++counter+":-");%><c:out value ="${asset.sysGeneratedTag}"/>(<c:out value ="${asset.comment}"/>)<p>
	    			</c:forEach>
    			
    					</div>
    					<div class="card-footer">
    					</div>
    			</div>
    		</div>	
      </div>
      <% dbrd = true;}%>
    </c:if>
    <c:if test = "${roles.view == 0 && roles.pageIdNameHolder == 'DBRD'}">
    </c:if>
    </c:forEach>
    <%}%>
    <%if(request.getAttribute("DA") == null){ %>
    <p class= "text-center" style = "color:gray;">DASHBOARD ACCESS MIGHT NOT AVAILABLE!</p>
    <%}else{%>
    <p class= "text-center" style = "color:gray;">DASHBOARD ACCESS MIGHT AVAILABLE!</p>
    <%} %>
    </section>
    </div>
    
    
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
