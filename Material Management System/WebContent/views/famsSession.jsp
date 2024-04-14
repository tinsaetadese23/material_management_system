<%if(request.getAttribute("session") != null){ 
	session.setAttribute("isAlive","yes");
	session.setAttribute("username",request.getAttribute("username"));
	System.out.println("Session Has Been Created at");
	RequestDispatcher ds  = request.getRequestDispatcher("tester.jsp");
	   ds.forward(request,response); 
}else{
	 RequestDispatcher ds  = request.getRequestDispatcher("..views/famsLogin.jsp");
	   ds.forward(request,response);  
}
%>
