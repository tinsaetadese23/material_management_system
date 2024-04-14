      <%if(request.getAttribute("userRoleLists") == null){
   // System.out.println("sending request to asset controller...");
    RequestDispatcher ds = request.getRequestDispatcher("../BCS/fa");
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
    <section class="content">
    <div class="row">
      				<label style="margin-left:10px;">Select By Issue Ref No.</label>
      				<div class ="form-group">
      					<button  id='bModal' type="button" class="btn" data-toggle="modal" data-target="#fetchpo" data-backdrop="static" data-keyboard="false">
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
                    <pre style="text-align:center;">Store Issue Voucher</pre>
                    <small class="float-right"><%Date today = new Date();out.println(today);%></small>
                  </h4>
                </div>
                <!-- /.col -->
              </div>
              <!-- info row -->
              <div class="row invoice-info">
                <div class="col-sm-4 invoice-col">
                  From
                  <address>
                    <strong>Siinqee Bank S.C</strong><br>
                    ODA TOWER,KAZANCHIS AREA<br>
                    ADDIS ABABA
                  </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
                  Issued To
                  <address>
                  <%int counter = 0; %>
                  <c:forEach var = "voucher" items = "${issueV}">
                  <%if(counter == 0){%>
                    <strong><c:out value="${voucher.decription}"></c:out></strong><br>
                    <c:out value="${voucher.status}"></c:out><br>
                    <%}counter++;%>
                    </c:forEach>
                  </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col">
                 <%int ccounter = 0;%>
                  <c:forEach var = "voucher" items = "${issueV}">
                  <%if(ccounter == 0){%>
                  <b>Issue Ref No:</b> <c:out value=
                  "${voucher.issueRef}"></c:out><br>
                  <b>Issued Date:</b><c:out value=
                  "${voucher.createdAt}"></c:out><br>
                  <b>Responsible Person:</b> <c:out value="${voucher.item}"></c:out>
                  <%}ccounter++;%>
                    </c:forEach>
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->

              <!-- Table row -->
              <div class="row">
                <div class="col-12 table-responsive">
                  <table class="table table-striped">
                    <thead>
                    <tr>
                      <th>S/No.</th>
                      <th>Code</th>
                      <th>Item Description</th>
                      <th>Requisition No</th>
                      <th>Measurement</th>
                      <th>Quantity</th>
                      <th>UnitPrice</th>
                      <th>Total Price</th>
                      <th>List of Tags</th>
                      <th>Remark</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var = "voucher" items = "${issueV}">
                    <tr>
                      <td><% int ccc = 0;out.println(++ccc);%></td>
                      <td><c:out value="${voucher.authStatus}"></c:out></td>
                      <td><c:out value="${voucher.cat}"></c:out></td>
                      <td><c:out value="${voucher.reqNo}"></c:out></td>
                      <td><c:out value="${voucher.comment}"></c:out></td>
                      <td><c:out value="${voucher.quantity}"></c:out></td>
                      <td><c:out value="${voucher.fcurrency1}"></c:out></td>
                      <td><c:out value="${voucher.fcurrency2}"></c:out></td>
                      <td><c:out value="${voucher.sysGeneratedTag}"></c:out></td>
                      <td></td>
                    </tr>
                   </c:forEach>
                   <tr>
                   <td colspan="7" style="text-align:center;">Total</td>
                   <td><%if(request.getAttribute("total") != null){out.println(request.getAttribute("total"));} %></td>
                   </tr>
                    </tbody>
                  </table>
                </div>
                <!-- /.col -->
              </div>
              <!-- /.row -->
              <!-- /.row -->

              <!-- this row will not appear when printing -->
              <div class="row no-print">
                <div class="col-12">
                  <a href="../BCS/printfa?issueref=<%out.println(request.getAttribute("issueref"));%>" rel="noopener" target="_blank" class="btn btn-default"><i class="fas fa-print"></i> Print</a>
                </div>
              </div>
            </div>
    </section>
    </div>
    
    <!-- modal fetch po -->
      <div class="modal fade" id="fetchpo">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 style="text-align:center" class="modal-title">Issue Ref. No.</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
             <form action = "../BCS/ssearch" method = "POST">
               <div class ="row">
            	<div class="col-md-3">
            	 <div class="form-group">
            		<input required placeholder="Enter Issue Ref No" class="form-control" type ="text" name="issueref" value=""/>
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
            <div class="modal-footer justify-content-between">
              
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!--  -->
    <!-- Import footer -->
    <script>
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
    </script>
      <jsp:include page="footerLayoutComp.jsp"></jsp:include>
    <!-- end of import footer -->
