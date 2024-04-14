<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
 <title>Application Submission</title>
  <link rel="stylesheet" href="../resources/loanCss.css">
  <link rel="stylesheet" href="resources/loanCss.css">
 </head>
<style type="text/css">
.alert {
  padding: 10px;
  background-color: #f44336;
  color: white;
  opacity: 1;
  transition: opacity 0.6s;
  margin-bottom: 15px;
}

.alert.success {background-color: #04AA6D;}
.alert.info {background-color: #2196F3;}
.alert.warning {background-color: #ff9800;}

.closebtn {
  margin-left: 15px;
  color: white;
  font-weight: bold;
  float: right;
  font-size: 22px;
  line-height: 20px;
  cursor: pointer;
  transition: 0.3s;
}

.closebtn:hover {
  color: black;
}
</style>
<script>
  function returnToPage() {
    // Return to the "LoanApplication.jsp" page
    window.location.href = "LoanApplication.jsp";
  }
</script>
    <script>
        // Set tab1 as active by default
        document.getElementById('tab1').classList.add('active');
    </script>
   </head>

		<a   id="openModal" class="active_link"></a>  

<!-- Automatically changing tabs based on their id -->
 <%if(request.getAttribute("msg") != null){%>
                <script type="text/javascript">
                window.onload = function(){
                document.getElementById('t2').classList.add('active');
                document.getElementById('t2').click();
                //document.get
                }
                </script>
    <%}%>
<!--  -->
	
	<div id="myModal1" class="modal">
	<h2> Loan application registration </h2>
		<div class="modal-content">
			<div class="modal-header">
			 
				
				<ul class="tab-links">
					<li class="tab-link active" data-tab="tab1">New </li>
					<li id="t2" class="tab-link" data-tab="tab2">Modify</li>
					<li id="t3" class="tab-link" data-tab="tab3">Pending</li>
					<li class="tab-link" data-tab="tab4">Approved</li>
					
					<li class="tab-link" data-tab="tab5">Closed</li>
					<li class="tab-link" data-tab="tab6">Days per customers</li>
					<li class="tab-link" data-tab="tab7">Change Status</li>
					
				</ul>
				 
			</div>
			<div class="modal-body">
		<h3 id='dt'>Loan Application | New</h3>
<%if(request.getAttribute("msg") != null){ %>
			<div class="alert warning">
  			<span class="closebtn">&times;</span>  
  			<strong>Success!</strong> Indicates a successful or positive action.
			</div>
			<br>
			<%} %>
	<div id="messageModal" class="modal" style="display: none;">
		<div class="modal-content">
			<span class="close" onclick="closeModal()">&times;</span>
			
			<p id="messageText"></p>
			<button onclick="returnToPage()">OK</button>
		</div>
	</div>
			<div class="containertable">
				<div class="tab-content active" id="tab1">
				<div class="container">
						<form action="../LAS" method="post">
							<input type="hidden" name="action" value="insert">
							<div class="form-group">
							
								<div class="form-group">
									<label for="customerID">Customer ID<span style="color:red"> *</span></label> <input type="text"
										id="customerID" name="customerID" required class="fr">
								</div>
								
								
								<div class="form-group">
									<label for="applicationID">Application ID<span style="color:red"> *</span></label> <input type="text" name="applicationID" id="applicationID" value="" required>
								</div>
								
								<div class="form-group">
									<label for="loanProduct">FullName<span style="color:red"> *</span></label> <input
										type="text" name="loanProduct" id="loanProduct" class="fr">
								</div>
								 
								
								<input type="hidden" name="loanOfficerID" value="">
								<div class="form-group">
    <label for="productID">TIN<span style="color:red"> *</span></label>
    <input type="text" name="productID" id="productID" pattern="\d{10}" title=" 10 digits" oninput="limitLength(this, 10)" required>
</div>

<script>
function limitLength(element, maxLength) {
    if (element.value.length > maxLength) {
        element.value = element.value.slice(0, maxLength);
    }
}
</script>

								<input type="hidden" name="branchID" value="">
								
								<div class="form-group">
									<label for="loanAmount">Loan Amount<span style="color:red"> *</span></label> <input type="text"
										name="loanAmount" id="loanAmount" required class="fr">
								</div> 
								
								<input type="hidden" name="loanAmount4" id="loanAmount4"  value="" >
									
									<div class="form-group">
									<label for="loanAmount3">Product Code<span style="color:red"> *</span></label> 							<div class="combobox-container">
  <select>
 <c:forEach var ="pr" items = "${productList}">
 <option value=""><c:out value ="${pr.productCode}"/></option>
 </c:forEach>
  </select>
</div>
								</div>
								<div class="form-group">
						 			<label for="loanAmount2">Branch Code<span style="color:red"> *</span></label> <input type="text"
										name="loanAmount2" id="loanAmount2"  value="" required class="fr"> 
								</div>
								
							<div class="form-group">
    <label for="remark">Remark:</label>
    <textarea name="loanAmount1" id="loanAmount1" rows="2" ></textarea>
</div>
							
								
								
								<input type="hidden" name="status" value="Under Review">
									
								
								 
	
							 <div class="form-group">
							  <br/>
							
							 <div class="form-group">
							 
							 </div>
							 <div class="form-group">
							 <br/>
							 </div>
							 <button type="submit" class="view-button">Register</button> 
							 </div>
							</div>
							<div class = "form-group lt">
							<div>
							<div class="containertable"><p style="text-align:center; margin-top:32px;"><strong>Required Fields</strong></p></div>
							<ul>
							<li><Strong style="color:red;">Customer Id</Strong> is a mandatory field(VALID)
							<li><Strong style="color:red;">Loan amount</Strong> is a mandatory field(VALID)
							<li><Strong style="color:red;">Full name </Strong> is a mandatory field(VALID)
							<li><Strong style="color:red;">TIN No</Strong> should be maximum of 10digits(VALID)
							</li>
							</ul>
							</div>
							</div>
							 
						</form>
					</div>
				</div>
				</div>
				<div class="tab-content" id="tab2"> 
					<div class="containertable">
					 <form   method="post" action="" onsubmit="switchToTab2()">
			<div class="form-group">
			<div class="form-group">		 
    <label for="selectDate">From Date:</label>
    <input type="date" name="selectDate" />
</div>

	<div class="form-group">		 
    <label for="selectDate">To Date:</label>
    <input type="date" name="selectDate1" />
</div>
<div class="form-group">
    <input type="submit" value="View" name="btn2" class="view-button" onclick="returnToTab('tab2')">
</div>
    
    </div>
</form>
						<table>
							<thead>
							
								<tr>
									<th>ID</th>
									<th>Cif</th>
									<th>FName</th>
									
									<th>Brcode</th>
									<th>Amount</th>
									<th>Product Code</th>
									 
									 
									<th>Status</th>
								 <th>Change Status</th> 
									 
									<th>Action</th>


								</tr>
							</thead>
							<tbody>
								
								<tr>
									<td>Sample one</td>
									
									 

									
									<td><select name="newStatus" id="newStatus"
										contenteditable="true">
										 <option value="">Select Status</option>
											<option value="Transfer File to Head Office  ">Transfer</option>
											<option value="Rejected">Rejected</option>
									</select></td>
									
									<td><a href="#" onclick="submitForm();" class="button-update">Submit</a>
  <script type="text/javascript">
    function submitForm(applicationID) {
      var selectElement = document.getElementById('newStatus');
      var selectedValue = selectElement.value;

      var form = document.createElement('form');
      form.method = 'post';
      form.action = 'ServletRejectorApproved';

      var inputApplicationID = document.createElement('input');
      inputApplicationID.type = 'hidden';
      inputApplicationID.name = 'applicationID';
      inputApplicationID.value = applicationID;

      var inputSelectedValue = document.createElement('input');
      inputSelectedValue.type = 'hidden';
      inputSelectedValue.name = 'selectedValue';
      inputSelectedValue.value = selectedValue;

      form.appendChild(inputApplicationID);
      form.appendChild(inputSelectedValue);
      document.body.appendChild(form);

      form.submit();
    }
  </script></td>
					
									<td>	<%-- Button to trigger the action --%> <a
										href="CustomerGetDaya.jsp?applicationID=<param"
										class="button">Edit</a> </td>
										 
											
								</tr>
							</tbody>
						</table>
					</div>

				</div>

				<div class="tab-content" id="tab3">
 

					<div class="containertable">
					  <form   method="post" action="" onsubmit="switchToTab2()">
			<div class="form-group">
			<div class="form-group">		 
    <label for="selectDate">From Date:</label>
    <input type="date" name="selectDate" />
</div>

	<div class="form-group">		 
    <label for="selectDate">To Date:</label>
    <input type="date" name="selectDate1" />
</div>
<div class="form-group">
    <input type="submit" value="View" name="btn2" class="view-button"></div>
    
    </div>
</form>
						<table>
							<thead>
								<tr>
									 
									<th>customerID</th>
									<th>FullName</th>
									<th>BrCode</th>
									<th> Amount</th>
									<th>ProductCode</th>
									 
									 
									<th>Status</th>
									<th>Select Action</th>


								</tr>
							</thead>
							<tbody>
								<tr>
									 
									<td>data</td>
									

									<td><select name="newStatus" id="newStatus"
										contenteditable="true">
										 
											<option value="Approved  ">Approved</option>
											<option value="Rejected">Rejected</option>
									</select></td>
									<td><a href="#" onclick="submitForm()" class="button-update">Submit</a>
  <script type="text/javascript">
    function submitForm(applicationID) {
      var selectElement = document.getElementById('newStatus');
      var selectedValue = selectElement.value;

      var form = document.createElement('form');
      form.method = 'post';
      form.action = 'ServletRejectorApproved';

      var inputApplicationID = document.createElement('input');
      inputApplicationID.type = 'hidden';
      inputApplicationID.name = 'applicationID';
      inputApplicationID.value = applicationID;

      var inputSelectedValue = document.createElement('input');
      inputSelectedValue.type = 'hidden';
      inputSelectedValue.name = 'selectedValue';
      inputSelectedValue.value = selectedValue;

      form.appendChild(inputApplicationID);
      form.appendChild(inputSelectedValue);
      document.body.appendChild(form);

      form.submit();
    }
  </script></td>
								</tr>
								
							</tbody>
						</table>
					</div>
				</div>
				
				<div class="tab-content" id="tab4">
				 
           
          <div class="containertable">
          	 <form   method="post" action="" onsubmit="switchToTab2()">
			<div class="form-group">
			<div class="form-group">		 
    <label for="selectDate">From Date:</label>
    <input type="date" name="selectDate" />
</div>

	<div class="form-group">		 
    <label for="selectDate">To Date:</label>
    <input type="date" name="selectDate1" />
</div>
<div class="form-group">
    <input type="submit" value="View" name="btn2" class="view-button"></div>
    
    </div>
</form>
						<table>
							<thead>
								<tr>
									<th>ID</th>
									<th>customerID</th>
									<th>Full Name</th>
									
									<th>Branch Code</th>
									<th>Loan Amount</th>
									<th>Product Code</th>
									 
									 
									<th>Status</th>
								 
									 
									 


								</tr>
							</thead>
							<tbody>
								
								<tr>
									<td>sample</td>
									
									 

								
									 
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="tab-content" id="tab5">
				
				
				<div class="containertable">
          	 <form   method="post" action="" onsubmit="switchToTab2()">
			<div class="form-group">
			<div class="form-group">		 
    <label for="selectDate">From Date:</label>
    <input type="date" name="selectDate" />
</div>

	<div class="form-group">		 
    <label for="selectDate">To Date:</label>
    <input type="date" name="selectDate1" />
</div>
<div class="form-group">
    <input type="submit" value="View" name="btn2" class="view-button"></div>
    
    </div>
</form>
						<table>
							<thead>
								<tr>
									<th>ID</th>
									<th>customerID</th>
									<th>Full Name</th>
									
									<th>BrCode</th>
									<th>Loan Amount</th>
									<th>Product Code</th>
									 
									 
									<th>Status</th>
								 
									 
									 


								</tr>
							</thead>
							<tbody>
								
								<tr>
									<td>sample 1</td>
																		 
<td><select name="newStatus" id="newStatus"
										contenteditable="true">
										 
											<option value="Closed  ">Closed</option>
											<option value="Declined by Customer">Declined</option>
									</select></td>
									<td><a href="#" onclick="submitForm();" class="button-update">Submit</a>
  <script type="text/javascript">
    function submitForm(applicationID) {
      var selectElement = document.getElementById('newStatus');
      var selectedValue = selectElement.value;

      var form = document.createElement('form');
      form.method = 'post';
      form.action = 'ServletRejectorApproved';

      var inputApplicationID = document.createElement('input');
      inputApplicationID.type = 'hidden';
      inputApplicationID.name = 'applicationID';
      inputApplicationID.value = applicationID;

      var inputSelectedValue = document.createElement('input');
      inputSelectedValue.type = 'hidden';
      inputSelectedValue.name = 'selectedValue';
      inputSelectedValue.value = selectedValue;

      form.appendChild(inputApplicationID);
      form.appendChild(inputSelectedValue);
      document.body.appendChild(form);

      form.submit();
    }
  </script></td>
									 
								</tr>
								
							</tbody>
						</table>
					</div>
					
				</div>
				<div class="tab-content" id="tab6">
					<div class="containertable">
          	 <form   method="post" action="" onsubmit="switchToTab2()">
			<div class="form-group">
			<div class="form-group">		 
    <label for="selectDate">From Date:</label>
    <input type="date" name="selectDate" />
</div>

	<div class="form-group">		 
    <label for="selectDate">To Date:</label>
    <input type="date" name="selectDate1" />
</div>
<div class="form-group">
    <input type="submit" value="View" name="btn2" class="view-button"></div>
    
    </div>
</form>
						<table>
							<thead>
								<tr>
									<th>Cif</th>
									<th>Full Name</th>
									<th>Amount</th>
									
									<th>Applied</th>
									<th>Started</th>
									<th> Completed date</th>
									 
									 
									<th>No Days</th>
								 
									 
									 


								</tr>
							</thead>
							<tbody>
								
								<tr>
									<td>Sample</td>
									
									 

								
									 
								</tr>
							</tbody>
						</table>
					</div>
					</div>
			</div>
		</div>
	</div>
	 
	
	
	<script>
  function closeModal() {
    var modal = document.getElementById("messageModal");
    modal.style.display = "none";
  }
  
</script>
<script>
  function returnToTab(tabID) {
    // Return to the "LoanApplication.jsp" page with the specified tab ID
    window.location.href = "LoanApplication.jsp?tab=" + tabID;
  }
</script>


	<script>
  var modal = document.getElementById("messageModal");
  var closeButton = document.getElementsByClassName("close")[0];
  var messageText = document.getElementById("messageText");

  // Retrieve the message and messageClass from the server-side
  var message = "";
  var messageClass = "";
 // var modalDisplay = "";

  // Update the message text based on the server-side value
  messageText.innerText = message;

  // Check if messageClass is not null and not an empty string
  if (messageClass && messageClass.trim() !== "") {
    // Update the modal class based on the server-side value
    modal.className = "modal " + messageClass;
  }

  // Check if modalDisplay is "block" to show the modal
  if (modalDisplay === "block") {
    modal.style.display = "block";
  }

  closeButton.onclick = function() {
    modal.style.display = "none";
  };
</script>
	<script>
var message = ""; // Assuming you have the appropriate message value

// Check if the message is "Success" and display the modal
if (message === "Success") {
    var modal = document.getElementById("Modalmessage");
    var closeBtn = modal.querySelector(".close");
     

    modalMessage.innerHTML = message;
    modal.style.display = "block";

    // Close the modal when the close button is clicked
    closeBtn.onclick = function() {
        modal.style.display = "none";
    }

    // Close the modal when the user clicks outside of it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}
</script>
	<script>
document.getElementById("openModal").addEventListener("click", function() {
	  document.getElementById("myModal1").style.display = "block";
	});

	document.querySelector(".close").addEventListener("click", function() {
	  document.getElementById("myModal1").style.display = "none";
	});

	document.querySelectorAll(".tab-link").forEach(function(tab) {
	  tab.addEventListener("click", function() {
	    var tabId = this.getAttribute("data-tab");
	    document.querySelectorAll(".tab-link").forEach(function(tab) {
	      tab.classList.remove("active");
	    });
	    document.querySelectorAll(".tab-content").forEach(function(content) {
	      content.classList.remove("active");
	    });
	    this.classList.add("active");
	    document.getElementById(tabId).classList.add("active");
	  });
	});
	</script>

<script>
const input = document.getElementById('combobox-input');
const list = document.getElementById('combobox-list');
const options = list.getElementsByTagName('li');

input.addEventListener('input', function() {
  const filter = input.value.toLowerCase();
  
  for (let i = 0; i < options.length; i++) {
    const optionText = options[i].textContent.toLowerCase();
    
    if (optionText.indexOf(filter) > -1) {
      options[i].style.display = '';
    } else {
      options[i].style.display = 'none';
    }
  }
  
  list.style.display = 'block';
});

for (let i = 0; i < options.length; i++) {
  options[i].addEventListener('click', function() {
    input.value = this.textContent;
    list.style.display = 'none';
  });
}
</script>
<script>
  document.addEventListener('DOMContentLoaded', function () {
    // Check if there is a returnTo parameter in the URL
    const urlParams = new URLSearchParams(window.location.search);
    const returnToTab = urlParams.get('returnTo');

    // If returnToTab is specified and it's tab2, click on tab2
    if (returnToTab === 'tab2') {
      document.querySelector('.tab-links li[data-tab="tab2"]').click();
    }
  });

  function switchToTab2() {
    // Append returnTo parameter to the form action URL
    const form = document.querySelector('form');
    form.action = form.action + '?returnTo=tab2';
  }
</script>
<script>
var close = document.getElementsByClassName("closebtn");
var i;

for (i = 0; i < close.length; i++) {
  close[i].onclick = function(){
    var div = this.parentElement;
    div.style.opacity = "0";
    setTimeout(function(){ div.style.display = "none"; }, 30);
  }
}
</script>
</body>
</html>