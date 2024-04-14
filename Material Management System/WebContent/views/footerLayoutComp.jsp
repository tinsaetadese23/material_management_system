    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <div class="modal fade" id="modal-default">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Confirmation</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
    	   <%if(request.getAttribute("msg") != null){ %>
                <p><c:out value ="${msg.successMsg}"/></p>
                <p style="color:red;"><c:out value ="${msg.errorMsg}"/></p>
                <p style="color:red;"><c:out value ="${msg.info}"/></p>
                
                <%}%>
            </div>
            <div class="modal-footer justify-content-between">
              <button style="float:right;" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
          
      <script>
      function deleteMe(){
    	 var message = confirm("Are you sure to Delete the record");
    	 if(message){
    		return true; 
    	 }else{
    		return false; 
    	 }
      }
      </script>
       <script>
   function submitFormOnValueChange() {
	// get the form element by id
	var form = document.getElementById("srform");
	// get the input element by id
	var input = document.getElementById("srinput");
	// add an event listener for the input change
	input.addEventListener("keyup", function() {
	// validate the input value
	if (input.value != "") {
	// submit the form
	form.submit();
	}else{
		var url = new URL(window.location.href);
		var path = url.pathname;
		console.log(path);
		switch(path){
		case "/FixedAsset/SCS/search" :
			window.location.href = "../views/famsStock.jsp";
			break;
		case "/FixedAsset/ACCS/search" :
			window.location.href = "../views/famsAssetClass.jsp";
			break;
		case "/FixedAsset/ACACS/search" :
			window.location.href = "../views/famsAssetCategory.jsp";
			break;
		case "/FixedAsset/ASCCS/search" :
			window.location.href = "../views/famsAssetSubCategory.jsp";
			break;
		case "/FixedAsset/POCS/srch" :
			window.location.href = "../views/famsPO.jsp";
			break;
		case "/FixedAsset/SUCS/search" :
			window.location.href = "../views/famsSupplier.jsp";
			break;
		case "/FixedAsset/ACS/search" :
		window.location.href = "../views/famsAssetIssuance.jsp";
			break;
		}
	}
	});
	}
   submitFormOnValueChange();
   </script>
	<script>
	console.log("i am called");
	var select = document.getElementById("sellect");

	// get the button element by id
	var button = document.getElementById("bttn");
	var rejection_comment = document.getElementById("rejection-comment");

	// add an event listener for the change event on the select element
	select.addEventListener("change", function() {
	// get the selected option value
	var optionValue = select.value;
	switch(optionValue){
	case "0" :
		button.innerHTML = "Authorize";
		rejection_comment.style.display = 'none';
		break;
	case "1" :
		button.innerHTML = "Reject";
		rejection_comment.style.display = 'block';
		break;
	}
	});	
	</script>
	<script>
	function formatInput() {
		// get the value of the input element
		input = document.getElementById("fi");
		let value = input.value;
		// remove any non-digit characters
		value = value.replace(/\D/g, "");
		// add comma after every three digits
		value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		// set the value of the input element
		input.value = value;
		}
	</script>
<footer class="main-footer">
    <strong>Copyright &copy; 2023-2024 <a href="#">MMS</a>.</strong>
    All rights reserved.
    <div class="float-right d-none d-sm-inline-block">
      <b>Version</b> 1.0.0
    </div>
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->

<!-- jQuery -->
<script src="../resources/plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="../resources/plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="../resources/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- ChartJS -->
<script src="../resources/plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script src="../resources/plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script src="../resources/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="../resources/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<!-- jQuery Knob Chart -->
<script src="../resources/plugins/jquery-knob/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script src="../resources/plugins/moment/moment.min.js"></script>
<script src="../resources/plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="../resources/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script src="../resources/plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script src="../resources/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="../resources/dist/js/adminlte.js"></script>
<!-- AdminLTE for demo purposes -->
<!-- <script src="../resources/dist/js/demo.js"></script> -->
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="../resources/dist/js/pages/dashboard.js"></script>
</body>
</html>
