 <%if(request.getAttribute("assetCategory") == null){
   // System.out.println("sending request to asset controller JSP...");
    RequestDispatcher ds = request.getRequestDispatcher("../RS/cpb");
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
            <h1 class="m-0">Category Per Branch</h1>
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
    if(request.getAttribute("rcpb") != null){
    totalAssetClassCount = Integer.parseInt(request.getAttribute("total").toString());
    }
    %>
    
    
     <!-- Main content -->
    <section class="content">
    <%if(request.getAttribute("message") != null){%>
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
          <div class = "col-md-12">
          	 <button type="button" class="btn btn-secondary" data-toggle="modal" data-target="#modal-searching" id="btnfilter">
                 <i class="nav-icon fas fa-search"></i>
                </button>
    			<div class = "card">
    				<div class="card-header">
    				<h3 class="card-title">Show Assets</h3>
    				</div>
    				<form>
    					<div id="myDiv" class="card-body" style="overflow-y: scroll !important; max-height:450px;">
    					<table class="table table-striped">
	    					<thead>
	    					<tr>
		    					<th style="width">No.</th>
		    					<th>Branch Name</th>
		    					<th style="">Total Price</th>
		    					<th style="">Remark</th>
	    					</tr>
	    					</thead>
	    					<tbody>
	    					<%if(request.getAttribute("rcpb") != null){ %>
	    					<%int counter = 0; %>
	    					<c:forEach var = "report" items = "${rcpb}">
	    					<tr>
	    						<td style=""><%out.println(++counter);%></td>
		    					<td><c:out value ="${report.rbranch}"/></td>
		    					<td><c:out value ="${report.rtotal}"/></td>
		    					<td><c:out value ="${report.rcategory}"/></td>
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
                  				 <td colspan="2" style="">Total</td>
                  				 <td colspan="2"><%if(request.getAttribute("alltotal") != null){out.println(request.getAttribute("alltotal"));} %></td>
                  			</tr>
                    
	    					</tbody>
    					</table>
    					<%if(totalAssetClassCount == 0){ %>
	    					<p style="color:gray;padding-top:8px;text-align:center;">No Data to Display</p>
	    					<%} %>
    					</div>
    					<div class="card-footer">
    						<button type = "button" class="btn btn-info">Total : <%out.println(totalAssetClassCount);%></button>
    						  <button class="btn btn-print btn-sm btn-light" onclick="printDiv()">
      						  <i class="fa fa-print"></i>
     						   Print
     						   </button>
    						<button onclick="exportToExcel()" class="btn btn-print btn-sm btn-light">
  							<i class="fa fa-download"></i> Export to Excel
							</button>
    					</div>
    				</form>
    			</div>
    		</div>
    	</div>
    </div>
    
    </section>
    </div>
    <!-- Import footer -->
    <!--Modal Start  -->
	  <div class="modal fade" id="modal-searching">
     <div class="modal-dialog">
     <form action="../RS/rp1" method="GET">
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
<div class="row">
<div class = "col-md-4">
<label>Category</label>
	<select name="category" class="form-control">
    <c:forEach var ="category" items ="${assetCategory}">
    <option  value="<c:out value="${category.id}"/>"><c:out value="${category.assetCatName}"/></option>
    </c:forEach>
</select>
</div>
<div  class = "col-md-4">
<label>From</label>
<input id="fsearch"  type="date" class="form-control" name = "from" /> 
</div>
<div class = "col-md-4">
<label>To</label>
<input value="<%Date today = new Date();out.println(today);%>" id="tsearch" type="date" class="form-control" name = "to"/> 
</div>
</div>
      </div>
      <div class="modal-footer">
      <input type="submit" value = "Fetch" class="btn btn-primary">
      </div>
    </div>
    </form>
  </div>
        <!-- /.modal-dialog -->
      </div>
    <!--Modal End  -->
    <!-- end of searching modal -->
    <<script type="text/javascript">
function validateSearch(){
	//alert('hello');
	var msearch = document.getElementById("msearch");
	var fsearch = document.getElementById("fsearch");
	var tsearch = document.getElementById("tsearch");
	var ssearch = document.getElementById("ssearch");
	var pid = document.getElementById("pid");
	msearch.disabled = true;
	msearch.value = "";
	ssearch.disabled = true;
	ssearch.value= "";
	tsearch.readOnly = true;
	tsearch.value = "";
	fsearch.readOnly = true;
	fsearch.value = "All";
	pid.style.display = "block";
	if(isearch.value == ""){
		pid.display = "none";
		msearch.disabled = false;
		ssearch.disabled = false;
    	tsearch.readOnly = false;
    	fsearch.readOnly = false;
    	pid.style.display = "none";

		
	}

}
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