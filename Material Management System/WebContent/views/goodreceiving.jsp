      <%if(request.getAttribute("userRoleLists") == null){
   // System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../RS/grv");
   ds.forward(request,response);
    }%>
  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ page import="java.util.Date" %>
  <jsp:include page="headerLayoutComp.jsp"></jsp:include>
  <!-- end of import header -->
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Good Receiving Voucher</h1>
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
         <%
    int totalAssetClassCount = 0;
    if(request.getAttribute("stocklist") != null){
    totalAssetClassCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
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
       <%if(request.getAttribute("searchResult") != null){%>
    	 <button id='bModal' type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-searching" data-backdrop="static" data-keyboard="false">
                  
                </button>
                <script type="text/javascript">
                window.onload = function(){
                document.getElementById('bModal').style.display="none";
                document.getElementById('bModal').click();
                }
                </script>
    <%}%>
    <section class="content">
    <div class="row">
      				<label style="margin-left:10px;"></label>
      				<div class ="form-group">
      					<button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#modal-searching" id="btnfilter">
                 <i class="nav-icon fas fa-search"></i>
                </button>
      			</div>
      			</div>
     <div class="invoice p-3 mb-3">
              <!-- title row -->
              <div class="row">
                <div class="col-12">
                  <h4 style="text-align:center;">
                    <i class=""></i> SIINQEE BANK S.C
                    <pre style="text-align:center;">Good Receiving Voucher(GRV)</pre>
                  </h4>
                </div>
              </div>
              <!-- info row -->
              <div class="row invoice-info">
                  <div class="col-sm-4 invoice-col">
                  <address>
                  <%int counter = 0; %>
                  <c:forEach var = "voucher" items = "${stocklist}">
                  <%if(counter == 0){%>
                    <strong>Purchase Order/Bid Award No:</strong><c:out value="${voucher.issueRef}"></c:out><br>
                    <strong>Received From[Supplier Name]:</strong><c:out value="${voucher.serialNo}"></c:out><br>
                    <%}counter++;%>
                    </c:forEach>
                  </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
            
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
                 <%int cccounter = 0;%>
                  <c:forEach var = "voucher" items = "${stocklist}">
                  <%if(cccounter == 0){%>
                  <b>Date:</b> <c:out value=
                  "${voucher.purchaseDate}"></c:out><br>
                  <strong>GRV No:</strong><c:out value="${voucher.recievingCode}"></c:out><br>
                  <strong>Credit/Cash Sales Invoice No:</strong> <c:out value="${voucher.authStatus}"></c:out>
                  <%}cccounter++;%>
                    </c:forEach>
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->

              <!-- Table row -->
              <div class="row" id = "myDiv">
                <div class="col-12 table-responsive">
                  <table class="table table-striped">
                    <thead>
                   <tr>
   					<th style="width">No.</th>
   					<th>Code</th>
   					<th>Item Description</th>
   					<th>Measurement</th>
   					<th>Quantity</th>
   					<th>Unit Price</th>
   					<th style="">Total Price</th>
   					<th style="">Remark</th>
	    		 </tr>
                    </thead>
                    <tbody>
                   <%if(request.getAttribute("stocklist") != null){ %>
  					<%int dounter = 0; %>
  					<c:forEach var = "report" items = "${stocklist}">
  					<tr>
  						<td style=""><%out.println(++dounter);%></td>
   					<td><c:out value ="${report.status}"/></td>
   					<td><c:out value ="${report.decription}"/></td>
   					<td><c:out value ="${report.brnstatus}"/></td>
   					<td><c:out value ="${report.quantity}"/></td>
   					<td><c:out value ="${report.unitPrice}"/></td>
   					<td><c:out value ="${report.totalPrice}"/></td>
   					<td></td>
   					<%-- <td><form action = "../ACCS/edit" method="POST">
   					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
   					<button style="background-color:white;border:1px solid white;" type="submit"><i class="far fa-edit"></i></button>
   					</form></td>
   					<td><form action = "../ACCS/delete" method="POST" onsubmit="return deleteMe()">
   					<input type ="hidden" name="id" value="<c:out value ="${asset.id}"/>"/>
   					<button style="background-color:white;border:1px solid white;" type="submit"><i class="fas fa-times btn-danger"></i></button>
   					</form></td> --%>
   					
  					</tr>
  					</c:forEach>
  					<%}%>
                			 <tr>
               				 <td colspan="6" style="">Total</td>
               				 <td colspan="6"><%if(request.getAttribute("alltotal") != null){out.println(request.getAttribute("alltotal"));} %></td>
               			</tr>
                    </tbody>
                  </table>
                  <%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">No Data to Display</p>
	    					<%} %>
                </div>
                 <div style = "border:1px solid gray;margin-left:5px;width:100%;">
                <p class="text-center"><strong>List of serials</strong></p>
                <table>
                <tr>
                <c:forEach var = "report" items = "${stocklist}">	
                <td> <c:out value ="${report.assetSpec}"/></td>
                </c:forEach>
                </tr>
                </table>
                </div><br>
               
                <!-- /.col -->
              </div>
              <!-- /.row -->
              <!-- /.row -->

              <!-- this row will not appear when printing -->
              <div class="row no-print">
                <div class="col-12">
                  <a href="../RS/print2?rc=<c:out value ="${stcObj.recievingCode}"/>"
                   rel="noopener" target="_blank" class="btn btn-default"><i class="fas fa-print"></i> Print</a>
                </div>
              </div>
            </div>
    </section>
    </div>
    
    <!-- modal fetch po -->
       <div class="modal fade" id="modal-searching">
     <div class="modal-dialog">
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
            <form id="myform" action="../RS/rp2" method="GET">
               <div class ="row">
            	<div class="col-md-6">
            	 <div class="form-group">
            		<input required placeholder="Enter GRV number" class="form-control" type ="text" name="rc" value=""/>
            	 </div>
            	</div>
            	<div class="col-md-3">
            	<button class="btn btn-default" type = "submit" value="Fetch">Fetch</button>
            	</div>
            </div>
            	<%if(request.getAttribute("none") != null){ %>
            	<p style="color:red;">No record is found. Try Again!</p>
            	<%} %>
           </form>
      </div>
      <div class="modal-footer">
      </div>
    </div>
  </div>
        <!-- /.modal-dialog -->
      </div>
      <!--  -->
    <!-- Import footer -->
    <script>
    const dropdown = document.getElementById("pos");
    dropdown.addEventListener("change",function(){
    	const para = document.createElement("input");
		//const node = document.createTextNode("This is a new paragraph.");
		//para.appendChild(node);
		para.setAttribute("type", "hidden");
		para.setAttribute("name", "su");
		para.setAttribute("value","su");
		const element = document.getElementById("div1");
		element.appendChild(para);
    	document.getElementById("myform").submit();
    });
    function printDiv() {
	 var divContents = document.getElementById("myDiv").innerHTML;
	  var printWindow = window.open('', '', 'height=400,width=800');
	  printWindow.document.write('<html><head><title><h1>SIINQEE BANK</h1></title>');
	  printWindow.document.write('<style>@media print {header {position: fixed; top: 0; left: 0; right: 0; height: 50px; background-color: #ccc;} footer {position: fixed; bottom: 0; left: 0; right: 0; height: 50px; background-color: #ccc;}}</style>');
	  printWindow.document.write('</head><body>');
	  printWindow.document.write('<header><strong>SIINQEE BANK FIXED ASSET MANAGEMENT SYSTEM</strong></header>');
	  printWindow.document.write(divContents);
	  printWindow.document.write('<footer>Siinqee bank</footer>');
	  printWindow.document.write('</body></html>');
	  printWindow.document.close();
	  printWindow.print();
	}
    function exportToExcel() {
    	var html = document.getElementById("myDiv").innerHTML;
    	  var link = document.createElement('a');
    	  link.download = "file.xls";
    	  link.href = 'data:application/vnd.ms-excel,' + encodeURIComponent(html);
    	  link.style.border = "1px solid #000";
    	  link.click();
    	}
    </script>
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
