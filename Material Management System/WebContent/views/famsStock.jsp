<%if(request.getAttribute("stockList") == null){
    System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../SCS");
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
            <h1 class="m-0">Good Receiving</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Asset Receiving</li>
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
    	<c:if test = "${roles.create == 1 && roles.pageIdNameHolder == 'ASSBULK'}">
    	<c:set var="create" value="1" />
    	</c:if>
    	<c:if test = "${roles.update == 1 && roles.pageIdNameHolder == 'ASSBULK'}">
    	<c:set var="update" value="1" />
    	</c:if>
    	<c:if test = "${roles.delete == 1 && roles.pageIdNameHolder == 'ASSBULK'}">
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
     <%if(request.getAttribute("poObj") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#poObj" data-backdrop="static" data-keyboard="false">
                  
                </button>
                <script type="text/javascript">
                window.onload = function(){
                document.getElementById('bModal').style.display="none";
                document.getElementById('bModal').click();
                }
                </script>
    <%}%>
       <%if(request.getAttribute("none") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#fetchpo" data-backdrop="static" data-keyboard="false">
                  
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
        <%if(request.getAttribute("srq") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-fsearching" data-backdrop="static" data-keyboard="false">
                  
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
    				<%if(request.getAttribute("oneAssetCategory")== null){%>
    				<form action="../SCS/create" method = "POST" id="myform">
    					<div class="card-body" style="overflow-y: scroll !important; height:450px;">
    					
                		    <!-- modal -->
      <div class="modal fade" id="modal-newUserRole">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Role</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    							<div class="form-group" id="div1">
    						<label>Serial No.</label>
							<!-- <input value="" name="assetCatDepr" type="number" class="form-control" id = "assetClass" required> -->    						
							</div>
    						<button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Attach Serial</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      			<div class="row">
      				<label>Select Purchase Order</label>
      				<div class ="form-group">
      				<!-- 	<button  id='bModal' type="button" class="btn" data-toggle="modal" data-target="#fetchpo" data-backdrop="static" data-keyboard="false">
  							<i class="nav-icon fas fa-search"></i>
							</button> -->
							<button  id='bModal' type="button" class="btn" data-toggle="modal" data-target="#modal-fsearching" data-backdrop="static" data-keyboard="false">
  							<i class="nav-icon fas fa-search"></i>
							</button>
      			</div>
      			</div>
      			<c:if test = "${subCatDetail.mode == 0}">
    					<div class = "row">
    					<div class = "col-md-4">
    						<div class ="form-group">
    						<label>Receiving Quantity<span style="color:red; font-size:18px;"> *</span></label>
    					   <input name = "rqty" required onkeyup = "addingSerial()" id="qty" value="" type = "text" min="1" max="" class="form-control"/>
    					   </div>
    					</div>
    					<div class = "col-md-4">
    						<div class="form-group">
    						<label id="tester" for="some">Serial<span style="color:red; font-size:18px;"> *</span></label><br>
							<button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-newUserRole" data-backdrop="static" data-keyboard="false">
                            + Attach Serials
                		    </button>    						
                		    </div>
    					</div>
    					</div>
                		    </c:if>
      			<c:if test = "${subCatDetail.mode == 1 || subCatDetail.mode == 3 || subCatDetail.mode == 4}">
                		    <div class="form-group">
    						<label>Receiving Quantity<span style="color:red; font-size:18px;"> *</span></label>
							<input  id="rqty" value=""   placeholder=''  name="rqty" type="text" class="form-control" id = "assetClass" required>  						
                		    </div>
                		    </c:if>
      			<div class ="row">
      			<div class = "col-md-4">
      			 <!-- end of modal -->
		      			 <div class="form-group">
		      					<label for="some">PO Number</label>
		    						<input  required readonly value="<c:out value="${poObjRec.poCode}"></c:out>"  placeholder=''  name="poNumber" type="text" class="form-control" id = "assetClass" required>
		    						<input  type="hidden" value="<c:out value="${poObjRec.id}"></c:out>"  placeholder=''  name="id" type="text" class="form-control" id = "assetClass" required>
		      				</div>
    						<div id='div2' class="form-group">
    						<label for="some">Bulk Receiving Code</label>
    						<input readonly value="<c:out value="${subCatDetail.tmpHolder}"></c:out>"  name="receivingCode" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Sub-Category</label>
    						<input readonly value="<c:out value="${poObjRec.subCategory}"></c:out>"  placeholder=''  name="subcat" type="hidden" class="form-control" id = "assetClass" required>
    						<input readonly value="<c:out value="${subCatDetail.subCatDesc}"></c:out>"  placeholder=''  name="showsubcat" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Supplier</label>
    						<input readonly value="<c:out value="${poObjRec.supplier}"></c:out>"  placeholder=''  name="supplier" type="hidden" class="form-control" id = "assetClass" required>
    						<input readonly value="<c:out value="${suppDetail.supplierName}"></c:out>"  placeholder=''  name="showsupplier" type="text" class="form-control" id = "assetClass" required>
    						</div>
      			</div>
      			<div class="col-md-4">
      			<div class="form-group">
    						<label  for="some">Unit Measurement</label>
    						<input readonly value="<c:out value="${poObjRec.unitMeasurement}"></c:out>"  placeholder=''  name="unitMeasurement" type="hidden" class="form-control" id = "assetClass" required>
    						<input readonly value="<c:out value="${unitDetail.unitDesc}"></c:out>"  placeholder=''  name="showunitMeasurement" type="text" class="form-control" id = "assetClass" required>
    						</div>
      						<div class="form-group">
    						<label for="some">Quantity</label>
    						<input readonly id="qtytest" value="<c:out value="${poObjRec.unRegisteredItem}"></c:out>"   placeholder=''  name="qty" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Unit Price</label>
    						<input readonly value="<c:out value="${poObjRec.unitPrice}"></c:out>"  placeholder=''  name="unitPrice" type="hidden" class="form-control" id = "assetClass" required>
    						<input readonly value="<c:out value="${poObjRec.fcurrency1}"></c:out>"  placeholder=''  name="yyyy" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Total Price</label>
    						<input readonly value="<c:out value="${poObjRec.total}"></c:out>"  placeholder=''  name="totalPrice" type="hidden" class="form-control" id = "assetClass" required>
    						<input readonly value="<c:out value="${poObjRec.fcurrency2}"></c:out>"  placeholder=''  name="yyyy" type="text" class="form-control" id = "assetClass" required>
    						</div>
      			</div>
      			<div class = "col-md-4">
      			<div class="form-group">
    						<label for="some">Receiving Date<span style="color:red; font-size:18px;"> *</span></label>
    						<input value = '' placeholder=''  name="rdate" type="date" class="form-control" id = "assetClass" required>
    						</div>
      			<div class="form-group">
    						<label for="some">CSI No. / CI No.<span style="color:red; font-size:18px;"> *</span></label>
    						<input value = '' placeholder=''  name="recieptNo" type="text" class="form-control" id = "assetClass" required>
    						<p style="color:red;">Type NA if not available</p>
    						</div>
    						<div class="form-group">
    						<label for="some">Checked By<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editPo.checkedBy}" />" value = '' placeholder=''  name="checkedBy" type="text" class="form-control" id = "assetClass" >
    						</div>
    						<div class="form-group">
    						<label for="some">Inspected By<span style="color:red; font-size:18px;"> *</span></label>
    						<input value="<c:out value="${editPo.insepectedBy}" />" value = '' placeholder=''  name="inspectedBy" type="text" class="form-control" id = "assetClass" >
    						</div>
      			</div>
      			</div>
      			<div>
      			<c:if test = "${subCatDetail.mode == 0 }">
      			<!-- <input id = "tst" value="itm" type = "hidden"/> -->
      			<ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
	                  <li onclick="leaveVeh();" class="nav-item">
	                    <a  class="nav-link active" id="custom-tabs-two-home-tab" data-toggle="pill" href="#custom-tabs-two-home" role="tab" aria-controls="custom-tabs-two-home" aria-selected="true">IT Equipment</a>
	                  </li>
	                  <li  onclick="leaveIt();" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Motors and Vehicle</a>
	                  </li>
	                  <li style="pointer-events:none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Furniture and Fitting</a>
	                  </li>
	                   <li style="pointer-events:none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-lb" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Land and Building</a>
	                  </li>
	                   <li style="pointer-events:none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-other" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">CHEQUE/PASS</a>
	                  </li>
	                </ul>
      			</c:if>
      			<c:if test = "${subCatDetail.mode == 1 }">
      			<!-- <input id = "tst" value="lots" type = "hidden"/> -->
      			<ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
	                  <li style="pointer-events: none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-home-tab" data-toggle="pill" href="#custom-tabs-two-home" role="tab" aria-controls="custom-tabs-two-home" aria-selected="true">IT Equipment</a>
	                  </li>
	                  <li style="pointer-events: none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Motors and Vehicle</a>
	                  </li>
	                   <li onclick="furn()"  class="nav-item">
	                    <a class="nav-link active" id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Furniture and Fitting</a>
	                  </li>
	                    <li onclick="lanb()" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-lb" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Land and Building</a>
	                  </li>
	                   <li class="nav-item" style="pointer-events: none;">
	                    <a class="nav-link" id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-other" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Other</a>
	                  </li>
	                </ul>
      			</c:if>
      			<c:if test = "${subCatDetail.mode == 4}">
      			<!-- <input id = "tst" value="chq" type = "hidden"/> -->
      			<ul class="nav nav-tabs" id="custom-tabs-two-tab" role="tablist">
	                  <li style="pointer-events: none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-home-tab" data-toggle="pill" href="#custom-tabs-two-home" role="tab" aria-controls="custom-tabs-two-home" aria-selected="true">IT Equipment</a>
	                  </li>
	                  <li style="pointer-events: none;" class="nav-item">
	                    <a class="nav-link" id="custom-tabs-two-messages-tab" data-toggle="pill" href="#custom-tabs-two-messages" role="tab" aria-controls="custom-tabs-two-messages" aria-selected="false">Motors and Vehicle</a>
	                  </li>
	                   <li  class="nav-item" style="pointer-events: none;">
	                    <a class="nav-link " id="custom-tabs-two-settings-tab" data-toggle="pill" href="#custom-tabs-two-settings" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Furniture and Fitting</a>
	                  </li>
	                    <li class="nav-item" style="pointer-events: none;">
	                    <a class="nav-link" id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-lb" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Land and Building</a>
	                  </li>
	                   <li onclick="chqb()" class="nav-item">
	                    <a class="nav-link active" id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-other" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">CHeque/Passbk</a>
	                  </li>
	                  <li class="nav-item">
	                    <a class="nav-link " id="custom-tabs-two-other-tab" data-toggle="pill" href="#custom-tabs-two-otherr" role="tab" aria-controls="custom-tabs-two-settings" aria-selected="false">Other</a>
	                  </li>
	                </ul>
      			</c:if>
	                <%if(request.getAttribute("poObjRec") != null){ %>
	                <div class="tab-content" id="custom-tabs-two-tabContent">
                  <div  class="tab-pane fade show active" id="custom-tabs-two-home" role="tabpanel" aria-labelledby="custom-tabs-two-home-tab">
                  		<div id="itt" class ="row">
                  			<div class="col-md-4">
                  			<div id="itB"  class="form-group">
	    						<label for="some">Item Description</label>
	    						<input  value="" id = "item"  name="itemDesc" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    						<div id="itB"  class="form-group">
	    						<label for="some">Memory (GB)</label>
	    						<input value="" id = "memo"  name="memo" type="text" class="form-control" id = "assetClass" >
	    						</div>
                  			</div>
                  			<div class="col-md-4">
	    					<div id="itB"  class="form-group">
	    						<label for="some">Model</label>
	    						<input  value="" id = "model"  name="model" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    						<div id="itB"  class="form-group">
	    						<label for="some">Storage (GB)</label>
	    						<input value="" id = "storage"  name="storage" type="text" class="form-control" id = "assetClass" >
	    						</div>
                  			</div>
                  			<div class="col-md-4">
                  			<div id="itB"  class="form-group">
	    						<label for="some">Processor</label>
	    						<input value="" id = "area"  name="model" type="text" class="form-control" id = "assetClass" >
	    						</div>
                  			</div>
                  		</div>	  `	
                  </div>
                  <div  class="tab-pane fade" id="custom-tabs-two-messages" role="tabpanel" aria-labelledby="custom-tabs-two-messages-tab">
                  	 	<div id="vv" class ="row">
                  			<div class="col-md-4">
                  			<div id="itemve"  class="form-group">
	    						<label for="some">Item</label>
	    						<input   value="" id = "area"  name="itemDescve" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id="capa"  class="form-group">
	    						<label for="some">Capacity</label>
	    						<input value="" id = "area"  name="capacity" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                  			</div>
                  			<div class="col-md-4">
	    					<div id="modelve"  class="form-group">
	    						<label for="some">Model</label>
	    						<input  value="" id = "area"  name="modelve" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id="wheels"  class="form-group">
	    						<label for="some">No Of Wheels</label>
	    						<input value="" id = "area"  name="wheels" type="text" class="form-control" id = "assetClass" >
	    						</div>
                  			</div>
                  			<div class="col-md-4">
                  			<div id="engine"  class="form-group">
	    						<label for="some">Engine Type</label>
	    						<input value="" id = "area"  name="engine" type="text" class="form-control" id = "assetClass" >
	    						</div>
	    					<div id="seats"  class="form-group">
	    						<label for="some">No of Seats</label>
	    						<input value="" id = "area"  name="seats" type="text" class="form-control" id = "assetClass" >
	    						</div>
                  			</div>
                  		</div>	
                  </div>
                  <div  class="tab-pane fade" id="custom-tabs-two-settings" role="tabpanel" aria-labelledby="custom-tabs-two-settings-tab">
                       <div class = "row">
                   	<div class = "col-md-4">
                   		<div id="itemve"  class="form-group">
	    						<label for="some">Item Specification</label>
	    						<input   value="" id = "area"  name="itemDescve" type="text" class="form-control" id = "assetClass" >
	    						</div>		
                   	</div>
                   	<div class = "col-md-4">
                   				<div id="itemve"  class="form-group">
	    						<label for="some">Width</label>
	    						<input   value="" id = "area"  name="width" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                   	</div>
                   	<div class = "col-md-4">
                   				<div id="itemve"  class="form-group">
	    						<label for="some">Height</label>
	    						<input   value="" id = "area"  name="height" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                   	</div>
                   </div>	
                  </div>
                   <div  class="tab-pane fade" id="custom-tabs-two-lb" role="tabpanel" aria-labelledby="custom-tabs-two-lb-tab">
                   <div class = "row">
                   	<div class = "col-md-4">
                   		<div id="itemve"  class="form-group">
	    						<label for="some">Item Specification</label>
	    						<input   value="" id = "area"  name="itemDescve" type="text" class="form-control" id = "assetClass" >
	    						</div>	
	    						<div id="itemve"  class="form-group">
	    						<label for="some">Area</label>
	    						<input   value="" id = "area"  name="itemDescve" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                   	</div>
                   	<div class = "col-md-4">
                   				<div id="itemve"  class="form-group">
	    						<label for="some">Latitude</label>
	    						<input   value="" id = "area"  name="itemDescve" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                   				<div id="itemve"  class="form-group">
	    						<label for="some">Latitude</label>
	    						<input   value="" id = "area"  name="itemDescve" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                   	</div>
                   </div>	
                  </div>
                  <div  class="tab-pane fade" id="custom-tabs-two-other" role="tabpanel" aria-labelledby="custom-tabs-two-other-tab">
                  <c:if test = "${subCatDetail.mode == 4}">
                  	<div class = "row">
                  	<div class = "col-md-6">
                  		<div id="itemve"  class="form-group">
	    						<label for="some">First No.</label>
	    						<input required  value="" id = "fno"  name="fno" type="text" class="form-control" id = "assetClass" >
	    						</div>	
                  	</div>
                 	 <div class = "col-md-6">
                  		<div id="itemve"  class="form-group">
	    						<label for="some">Last No</label>
	    						<input required  value="" id = "lno"  name="lno" type="text" class="form-control" id = "assetClass" >
	    						</div>
                  	</div>
                  </div>	 
                  </c:if>
                  </div>
                  </div>
                  <%}%>
    			</div>			
    					</div>
    					<div class="card-footer">
    					<%if(request.getAttribute("poObjRec") != null){ %>
    					<c:if test = "${create == '1'}">
    					<input value="Receive" type = "submit" class="btn custom_button_branding"/>
						</c:if>
    						<%} %>
    					</div>
    				</form>
    				<%}else{ %>
    				<form action="../BCS/create" method = "POST">
    					<div class="card-body" style="overflow-y: scroll !important; height:450px;">
    						<div class="form-group">
    						<label>Category Class</label>
    						<select name="assetCatClass" class="form-control">
    						<%if(request.getAttribute("assetClass") != null){ %>
    						<c:forEach var = "asset" items = "${assetClass}">
    						<option value ="<c:out value ="${asset.id}"/>"><c:out value ="${asset.assetClassCode}"/></option>
    						</c:forEach>
    						<%}%>
    						</select>
    						</div>
    						<div class="form-group">
    						<label for="some">Category Name</label>
    						<input value="<c:out value ="${oneAssetCategory.assetCatName}"/>" name="assetCatName" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Category Description</label>
    						<input value ="<c:out value ="${oneAssetCategory.assetCatDesc}"/>" name="assetCatDesc" type="text" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Category Maintenance</label>
    						<input value="<c:out value ="${oneAssetCategory.assetCatMaint}"/>" name="assetCatMaint" type="number" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label for="some">Category Lifetime</label>
    						<input value="<c:out value ="${oneAssetCategory.assetCatLife}"/>" name="assetCatLife" type="number" class="form-control" id = "assetClass" required>
    						</div>
    						<div class="form-group">
    						<label>Category Depreciation</label>
    						<input value="<c:out value ="${oneAssetCategory.assetCatCatDepr}"/>" name="assetCatDepr" type="number" class="form-control" id = "assetClass" required>
    						</div>
    					</div>
    					<div class="card-footer">
    						<input type ="hidden" name="id" value="<c:out value ="${oneAssetCategory.id}"/>"/>
    						<input value="Update Category" type = "submit" class="btn btn-primary"/>
    						<button class="btn btn-default"><a style="text-decoration:none;" href="../views/famsAssetClass.jsp">Go to New</a></button>
    					</div>
    				</form>
    				<%} %>
    			</div>
    		</div>
    		<div class = "col-md-6">
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Show Stock</h3>
    				<br><br>
    				<form id ="srform" method = "post" action = "../SCS/search"> 			
    				<div class=""><input  value="<c:out value ="${asc.status}"/>" id = "srinput" name ="sr" placeholder = "Search By Receiving code or po code" type = "text" class = "form-control"/></div>
    				</form>
    				</div>
    				<form>
    					<div class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-bordered">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Receiving Code</th>
		    					<th style="">Total Item</th>
		    					<th class="text-center" colspan="2">Action</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("searchResult") == null){ %>
	    					<%if(request.getAttribute("stockList") != null){ %>
	    					<%int counter = 0; %>
	    					<c:forEach var = "stc" items = "${stockList}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
		    					<td><c:out value ="${stc.recievingCode}"/></td>
		    					<td><c:out value ="${stc.quantity }"/></td>
		    					<td><a href = "../SCS/view?rc=<c:out value ="${stc.recievingCode}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../SCS/delete?rc=<c:out value ="${stc.recievingCode}"/>"><i class="fas fa-times btn-danger"></i></a></td>
								</c:if>
	    					</tr>
	    					</c:forEach>
	    					<%}%>
	    					<%}else{%>
	    					<%int counter = 0; %>
	    						<%if(request.getAttribute("none") != null){ %>
	    							<p style="color:gray;text-align:center;">Oops none found!!</p>
	    							<%}%>
	    						<c:forEach var = "stc" items = "${searchResult}">
	    					<tr>
	    						<td style=""><%out.println(++counter); %></td>
		    					<td><c:out value ="${stc.recievingCode}"/></td>
		    					<td><c:out value ="${stc.quantity }"/></td>
		    					<td><a href = "../SCS/view?rc=<c:out value ="${stc.recievingCode}"/>"><i class="far fa-eye"></i></a></td>
		    					<c:if test = "${delete == '1'}">
		    					<td><a onclick="return deleteMe()" href = "../SCS/delete?rc=<c:out value ="${stc.recievingCode}"/>"><i class="fas fa-times btn-danger"></i></a></td>
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
    	var total = document.getElementById("tp").value = unitPrice*quantity;
    	
    }
    </script>
<!--

//-->

<script>
function addingSerial(){
	const qty = document.getElementById("qty").value;
	var counter = 0;
	var tester = document.getElementById("tester");
	if(tester != null){
		var celement = document.getElementById("div1");
		celement.innerHTML = "";
	for (var i = 0; i< qty; i++){
		const para = document.createElement("input");
		//const node = document.createTextNode("This is a new paragraph.");
		//para.appendChild(node);
		//para.value = "";
		para.setAttribute("class", "form-control");
		para.setAttribute("placeholder",'#No. '+ ++counter);
		para.setAttribute("name", "serial");
		para.setAttribute("id","srr");
		para.required = true;
		const element = document.getElementById("div1");
		element.appendChild(para);
	}
	}else{
		const para = document.createElement("input")
		para.setAttribute("name", "nonSerial");
		para.setAttribute("type", "hidden");
		const element = document.getElementById("div2");
		element.appendChild(para);
	}

}
addingSerial();
/* if(document.getElementById("srr") != null){
	alert("working...");
	var form = document.getElementById("myform");
	form.addEventListener("submit", function(e){
		var serial = document.getElementById("srr").value;
		if (serial === "") {
			alert("Please fill all the fields");
			e.preventDefault();
			}
	});
} */
</script>
       <div class="modal fade" id="poObj">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Purchase Order Information</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
            <%if(request.getAttribute("none") == null){ %>
            <p>Purchase Order(1)</p>
            <hr>
    	   <div class ="row">
    	   <div class="col-md-2">
    	   <label>PO No.</label>
    	   <p><c:out value="${poObj.poCode}"></c:out></p>
    	   </div>
    	   <div class="col-md-3">
    	   <label>Measurement</label>
    	   <p><c:out value="${unit.unitName}"></c:out></p>
    	   </div>
    	   <div class="col-md-3">
    	   <label>Unit Price</label>
    	   <p><c:out value="${poObj.fcurrency1}"></c:out></p>
    	   </div>
    	   <div class="col-md-2">
    	   <label>Qty</label>
    	   <p><c:out value="${poObj.quantity}"></c:out></p>
    	   </div>
    	   <div class="col-md-2">
    	   <label>Total</label>
    	   <p><c:out value="${poObj.fcurrency2}"></c:out></p>
    	   </div>
    	   </div>
    	  <p>Supplier(2)</p>
    	  <hr>
    	    <div class ="row">
    	   <div class="col-md-4">
    	   <label>Name</label>
    	   <p><c:out value="${supp.supplierName}"></c:out></p>
    	   </div>
    	   <div class="col-md-4">
    	   <label>Contact</label>
    	   <p><c:out value="${supp.contactNumber}"></c:out></p>
    	   </div>
    	   <div class="col-md-4">
    	   <label>Address</label>
    	   <p><c:out value="${supp.address}"></c:out></p>
    	   </div>
    	   </div>
    	   <p>Classification(3)</p>
    	   <hr>
    	    <div class ="row">
    	   <div class="col-md-4">
    	   <label>Category</label>
    	   <p><c:out value="${asubcat.subCategoryNameHolder}"></c:out></p>
    	   </div>
    	   <div class="col-md-4">
    	   <label>SubCategory</label>
    	   <p><c:out value="${asubcat.subCatCode}"></c:out></p>
    	   </div>
    	   <div class="col-md-4">
    	   <label>Remaining</label>
    	   <p><c:out value="${poObj.unRegisteredItem}"></c:out></p>
    	   </div>
    	   </div>
    	   <%}else{ %>
    	   <p>Sorry no record found</p>
    	   <%} %>
            </div>
            <div class="modal-footer justify-content-between">
              <button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <form action = "../SCS/recieving" method="POST">
		    					<input type ="hidden" name="id" value="<c:out value ="${poObj.id}"/>"/>
								<button style="float:right;" type="submit" class="btn custom_button_branding">Copy to Receiving</button>	
			    </form>
              
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <script>
      document.getElementById("custom-tabs-two-home").style.display = "none";  
      function leaveVeh(){
    	  alert("YOU ARE REGISTERING IT EQUIP");
    	  document.getElementById("custom-tabs-two-home").style.display = "block";  
    	  document.getElementById("custom-tabs-two-messages").style.display = "none";
      }
      function leaveIt(){
    	  alert("YOU ARE REGISTERING VEHICLE");
    	  document.getElementById("custom-tabs-two-home").style.display = "none";  
    	  document.getElementById("custom-tabs-two-messages").style.display = "block"; 
    	  
    	const para = document.createElement("input");
  		para.setAttribute("type", "hidden");
  		para.setAttribute("name", "veh");
  		para.setAttribute("value","veh");
  		const element = document.getElementById("vv");
  		element.appendChild(para);
      }
      function furn(){
    	  const para = document.createElement("input");
    		para.setAttribute("type", "hidden");
    		para.setAttribute("name", "furn");
    		para.setAttribute("value","furn");
    		const element = document.getElementById("vv");
    		element.appendChild(para);
      }
      function lanb(){
    	  const para = document.createElement("input");
  		para.setAttribute("type", "hidden");
  		para.setAttribute("name", "lanb");
  		para.setAttribute("value","lanb");
  		const element = document.getElementById("vv");
  		element.appendChild(para);
      }
      function chqb(){
    	  const para = document.createElement("input");
    	  fno.setAttribute("required","");
    	  lno.setAttribute("required","");
    		para.setAttribute("type", "hidden");
    		para.setAttribute("name", "chqb");
    		para.setAttribute("value","chqb");
    		const element = document.getElementById("vv");
    		element.appendChild(para); 
    	  
      }
      </script>
      <!-- modal fetch po -->
      <div class="modal fade" id="fetchpo">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Purchase Order Information</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
             <form action = "../POCS/search" method = "POST">
               <div class ="row">
            	<div class="col-md-3">
            	 <div class="form-group">
            		<input required placeholder="Enter PO Number here." class="form-control" type ="text" name="pocode" value=""/>
            	 </div>
            	</div>
            	<div class="col-md-3">
            	<button class="btn custom_button_branding" type = "submit" value="Fetch">Fetch</button>
            	</div>
            </div>
            	<%if(request.getAttribute("nones") != null){ %>
            	<p style="color:red;">No record is found. Try Again!</p>
            	<%} %>
           </form>
            </div>
            <div class="modal-footer justify-content-between">
              
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!--  -->
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
   			 <form action = "../SCS/auth" method="POST">
   			 <input name="rc" type="hidden" value="<c:out value="${objprp.recievingCode}"/>"/>
   			 <div class="row">
   			 	<div class="col-md-4">	
   			 	<p><strong>Receiving Code</strong> : <c:out value="${objprp.recievingCode}"></c:out></p>
   			 	<p><strong>Sub-Cat Code</strong> : <c:out value="${ftest.subCatCode}"></c:out></p>
   			 	<p><strong>Sub-Cat Desc</strong> : <c:out value="${ftest.subCatDesc}"></c:out></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Quantity</strong> : <c:out value="${objprp.quantity}"></c:out></p>
   			 	<p><strong>Unit Price</strong> : <c:out value="${objprp.fcurrency1}"></c:out></p>
   			 	<p><strong>Total Price</strong> : <c:out value="${objprp.fcurrency2}"></c:out></p>
   			 	<c:set var="c" value="${objprp.quantity - 1}" />
   			 	<p><strong>Serial No</strong> : <c:out value="${objprp.serialNo}"></c:out> and <span style="color:red;"><c:out value="${c}"></c:out><i>  more</i></span></p>
   			 	</div>
   			 	<div class="col-md-4">
   			 	<p><strong>Status</strong> : <c:out value="${objprp.status }"></c:out></p>
   			 	<p><strong>Inspected By</strong> : <c:out value="${objprp.inspectedBy }"></c:out></p>
   			 	<p><strong>Checked By</strong> : <c:out value="${objprp.checkedBy }"></c:out></p> 	
   			 	<p><strong>Issued To</strong> : <c:out value="${objprp.issuedTo}"></c:out></p>
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
              <c:if test = "${objprp.authStatus == 'AR' }">
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


   <div class="modal fade" id="modal-fsearching">
     <div class="modal-dialog">
     <form id="myformm" action="../SCS/src" method="GET">
    <div class="modal-content">
  
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Filter Options</h5>
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
<div class="row" id = "divm">
<div class = "col-md-4">
<label>Purchase Orders</label>
	<select id="pos" name="pos" class="form-control">
    <c:forEach var ="po" items ="${polist}">
    <c:if test = "${po.poCode == poObjjj.poCode }">
    <option selected  value="<c:out value="${po.poCode}"/>"><c:out value="${po.poCode}"/></option>
    </c:if>
     <c:if test = "${po.poCode != poObjjj.poCode }">
    <option   value="<c:out value="${po.poCode}"/>"><c:out value="${po.poCode}"/></option>
    </c:if>
    </c:forEach>
</select>
</div>
<div class = "col-md-4">
<label>Sub Category</label>
	<select name="subcat" class="form-control">
	<%if(request.getAttribute("ssubcats") == null){ %>
    <c:forEach var ="subcat" items ="${subcats}">
    <option  value="<c:out value="${subcat.id}"/>"><c:out value="${subcat.subCatCode}"/>(<c:out value="${subcat.subCatDesc}"/>)</option>
    </c:forEach>
    <%}else{ %>
    <c:forEach var ="subcat" items ="${ssubcats}">
    <option  value="<c:out value="${subcat.id}"/>"><c:out value="${subcat.subCatCode}"/>(<c:out value="${subcat.subCatDesc}"/>)</option>
    </c:forEach>
    <%} %>
</select>
</div>
</div>
      </div>
      <div class="modal-footer">
      <input type="submit" value = "Fetch" class="btn custom_button_branding">
      </div>
    </div>
    </form>
  </div>
        <!-- /.modal-dialog -->
      </div>
      <script>
      const dropdown = document.getElementById("pos");
      dropdown.addEventListener("change",function(){
      	const para = document.createElement("input");
  		//const node = document.createTextNode("This is a new paragraph.");
  		//para.appendChild(node);
  		para.setAttribute("type", "hidden");
  		para.setAttribute("name", "su");
  		para.setAttribute("value","su");
  		const element = document.getElementById("divm");
  		element.appendChild(para);
      	document.getElementById("myformm").submit();
      });
      </script>
    <!-- Import footer -->
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->