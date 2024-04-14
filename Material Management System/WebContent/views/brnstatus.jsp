      <%if(request.getAttribute("stockListt") == null){
   // System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../BLSC");
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
            <h1 class="m-0">Assets Issued to you and your branch</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Your Assets</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->
    
    
     <!-- Main content -->
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
    <section class="content">
    <form action = "../BLSC/sch2" method = "post">
    <div class="row">
    	<div class = "col-md-3 form-group">
    	<select class = "form-control" name ="subcat">
    	<c:forEach var = "asset" items = "${stockListt}">
    	<option value="<c:out value ="${asset.subCategoryCode}"/>"><c:out value ="${asset.decription}"/></option>
    	</c:forEach>
    	</select>
    	</div>
    	<div class = "col-md-1">
    	<input type = "submit" class= "btn btn-primary" value="fetch" type = "submit"/>
    	</div>
    </div>
    </form>
    <%if(request.getAttribute("stockList") == null){ %>
    	
    <%}else{ %>
    <div class="row">
    	<c:forEach var = "asset" items = "${stockList}">
    	<div class="col-md-3">
            <!-- DIRECT CHAT WARNING -->
            <div class="card card-info direct-chat direct-chat-warning shadow">
              <div class="card-header">
                <h3 class="card-title"><c:out value="${asset.decription}"></c:out></h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" title="Click Here To Change Status" data-widget="chat-pane-toggle">
                   <c:out value="${asset.brnstatus}"></c:out> <i class="right fas fa-angle-left"></i>
                  </button>
                </div>
              </div>
              <!-- /.card-header -->
              <div class="card-body">
                <!-- Conversations are loaded here -->
                <div class="direct-chat-messages">
                  <!-- Message. Default to the left -->
                  <div class="direct-chat-msg">
                    <label>Price</label>
                    <p><c:out value="${asset.totalPrice}"></c:out></p>
                    <label>Serial No</label>
                    <p><c:out value="${asset.serialNo}"></c:out></p>
                    <label>UIID </label>
                    <p><c:out value="${asset.sysGeneratedTag}"></c:out></p>
                  </div>
                  <!-- /.direct-chat-msg -->

                  <!-- Message to the right -->
                  <div class="direct-chat-msg right">
                    
                  </div>
                  <!-- /.direct-chat-msg -->
                </div>
                <!--/.direct-chat-messages-->

                <!-- Contacts are loaded here -->
                <div class="direct-chat-contacts">
                  <ul class="contacts-list">
                    <li>
                        <div class="contacts-list-info">
                          <form action = "../BLSC/sch" method ="post">
                          <input name="id" type = "hidden" value="<c:out value="${asset.id }"/>"/>
                          	<div class ="form-group">
                          		<label>Status</label>
                          		<Select class ="form-control" name = "status">
                          			<option value="FUNCTIONAL">FUNCTIONAL</option>
                          			<option value="SEMI-FUNCTIONAL">SEMI-FUNCTIONAL</option>
                          			<option value="NON-FUNCTIONAL">NON-FUNCTIONAL</option>
                          			<option value="LOST">LOST</option>
                          		</Select>
                          	</div>
                          	<button class ="btn btn-secondary" type="submit">Update Status</button>
                         </form>
                        </div>
                    </li>
                    <!-- End Contact Item -->
                  </ul>
                  <!-- /.contatcts-list -->
                </div>
                <!-- /.direct-chat-pane -->
              </div>
              <!-- /.card-body -->
              <div class="card-footer">
                <form action="#" method="post">
                  <div class="input-group">
                    </span>
                  </div>
                </form>
              </div>
              <!-- /.card-footer-->
            </div>
            <!--/.direct-chat -->
          </div>
    	
    	</c:forEach>
    	</div>
    <%} %>
    </section>
    </div>
    
    
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
