package controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import model.AssetSubCategory;
import model.DashboardData;
import model.FamsSession;
import model.Login;
import model.Message;
import model.Reporting;
import model.Roles;
import repository.AssetSubCatRepository;
import repository.LoginRepository;
import repository.RBACRepository;
import repository.ReportingRepositoy;
import repository.StockRepository;
import repository.SupplierRepository;

/**
 * Servlet implementation class LoginControllerServlet
 */
/*@WebServlet("/LoginControllerServlet")*/
public class LoginControllerServlet extends HttpServlet {
	Message msg;
	private static final long serialVersionUID = 1L;
	private int uid;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String path = request.getPathInfo();
		if(path == null) {
			path = "default";
		}
		switch(path) {
		case "/ch":
			fetchUserRoles(request,response,"../views/changepasswor.jsp");
			break;
			default :
				logout(request,response);	
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String path = request.getPathInfo();
		if(path == null) {
			path = "default";
		}
		switch(path) {
		case "/chn" :
			String page1 = "../views/famsLogin.jsp";
			changePassword(request,response,page1);
			break;
		case "/ch" :
			String page2 = "../views/changepasswor.jsp";
			changePassword(request,response,page2);
			break;
			default :
				isUserExist(request, response);
				break;
		}
	}
	
	public void isUserExist(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String page = "../views/famsLogin.jsp";
		msg = new Message();
		
		Login loginObj = new Login();
		loginObj.setUsername(username);
		loginObj.setPassword(password);
		
		LoginRepository loginRepository = new LoginRepository();
		if(loginRepository.fetchOne(loginObj) != null) {
			if(loginRepository.isLogExist(loginObj.getUsername())) {
				msg.setSuccessMsg("You are a valid user. Stay Tuned!");
				FamsSession famsSession= new FamsSession();
				famsSession.setSessionUsername(loginObj.getUsername());
				famsSession.setSessionUsername(loginObj.getUsername());
				
				HttpSession mySession = request.getSession();
				mySession.setAttribute("username", famsSession.getSessionUsername());
				mySession.setAttribute("uid",loginRepository.fetchUserId(loginObj.getUsername()));
				System.out.println("user id is"+loginRepository.fetchUserId(loginObj.getUsername()));
				request.setAttribute("session", famsSession);
				request.setAttribute("username", famsSession.getSessionUsername());
				
				ReportingRepositoy rr = new ReportingRepositoy();
				List<Reporting> repoList = rr.assetPerClass();
				List<Reporting> catList = rr.assignedAssetPerCategory();
				List<Reporting> acatList = rr.availableAssetPerCategory();
				request.setAttribute("acatList", acatList);
				request.setAttribute("catList", catList);
				request.setAttribute("repoList", repoList);
				
				uid = Integer.parseInt(mySession.getAttribute("uid").toString());
				DashboardData dd = new DashboardData();
				StockRepository sr = new StockRepository();
				dd.setTotalAsset(sr.totalAsset());
				dd.setTotalAssignedAsset(sr.totalAssignedAsset());
				dd.setTotalInStockAsset(sr.totalInstockAset());
				dd.setTotalSupplier(new SupplierRepository().totalSupp());
				AssetSubCatRepository asr = new AssetSubCatRepository();
				List<AssetSubCategory> asubList = asr.assetsPerSubCat();
				System.out.println("Size of report "+asubList.size());
				//System.out.println("SubcatCode "+asubList.size());

				request.setAttribute("asubList", asubList);
				request.setAttribute("onServer", "yes");
				request.setAttribute("dashboarddata", dd);
				page = "../views/famsSession.jsp";
				//System.out.println("from my session "+mySession.getAttribute("username"));
			}else {
				System.out.println("Changin Password In Progress By"+loginObj.getUsername());
				HttpSession mySession = request.getSession();
				mySession.setAttribute("username", loginObj.getUsername());
				msg.setSuccessMsg("This is your First Login. Change Password!");
				request.setAttribute("fl","firslogin");
				request.setAttribute("msg", msg);
				page = "../views/famsLogin.jsp";
			}
			
		}else {
			msg.setErrorMsg("Invalid Username or Password!");
		}
		
		
		request.setAttribute("msg", msg);
		fetchUserRoles(request,response,page);
	}
	 public void changePassword(HttpServletRequest request, HttpServletResponse response,String page) {
		 HttpSession mySession = request.getSession();
		 String whoami = (String)mySession.getAttribute("username");
		 	HttpSession fusession = request.getSession();
			String fuser = (String)fusession.getAttribute("fuser");
			//String page = "../views/famsLogin.jsp";
			msg = new Message();
			Login loginObj = new Login();
			loginObj.setUsername(whoami);
			LoginRepository loginRepository = new LoginRepository();
			
		   //getUriPattern(request,response);
		   System.out.println("Change Password method has been called by"+ whoami);
		   String priviousPassword = request.getParameter("ppassword").toString().trim();
	   	   String newPassword = request.getParameter("npassword");
	   	   String confNewPassword = request.getParameter("cpassword");
	   	   String sessPassword = loginRepository.checkPassword(whoami,priviousPassword);  
	   	   System.out.println("the session password afer decryption is:"+sessPassword);
	   		try {
	   			if(priviousPassword != "" && newPassword != "" && confNewPassword != "") {
	   				//{
	   					System.out.println("Fields are not null passed!");
	   					if(newPassword.trim().equals(confNewPassword.trim())){
	   						System.out.println("Password Match Confirmation passed!");
	   						if(priviousPassword.trim().equals(sessPassword.trim())){
	   					 	if(!newPassword.trim().equals(priviousPassword.trim())){
	   				 		if(loginRepository.updatePassword(newPassword,whoami)){
	   				 		System.out.println("Password changed passed!");
	   				 		LocalDateTime myDateObj = LocalDateTime.now();
	   				 		if(loginRepository.insertLogTime(loginObj.getUsername(), myDateObj, myDateObj)) {
	   				 	    request.setAttribute("onServer", "yes");
	   				 	 	msg.setSuccessMsg("Password Changed");
	   				 	 	page = "../views/famsLogin.jsp";
	   				 	    fusession.invalidate();
	   				 	    mySession.invalidate();
	   				 	 }else {
	   				 	    request.setAttribute("onServer", "yes");
	   				 	 	msg.setErrorMsg("Updating Log Time Failed");
							request.setAttribute("fl", "yes");
	   				 	 }
	   				 		}else {
	   				 		request.setAttribute("onServer", "yes");
	   				 		msg.setErrorMsg("Password Change Failed");
	   				 	 	request.setAttribute("fl","yes");
								
	   				 		}
	   					 	}else {
	   					 	 request.setAttribute("onServer", "yes");
	   					     msg.setErrorMsg("Previous password used");
	   					     request.setAttribute("fl", "yes");
							// page = "";		
	   					 	}
	   						
	   					}else {
	   					    request.setAttribute("onServer", "notnull");
	   						msg.setErrorMsg("Incorrect Old Password");
	   						request.setAttribute("fl", "yes");
	   		   			    //page = "";
	   					}
	   				}else{
	   				request.setAttribute("onServer", "notnull");
	   				request.setAttribute("fl", "yes");
	   				msg.setErrorMsg("Password Mismatch");
	   		   		//page = "";
	   					}
	   				//}
	   			}else {
	   			 request.setAttribute("onServer", "yes");
	   			 msg.setErrorMsg("One or more field are empty");
	   			 request.setAttribute("fl", "yes");
	   			}
	   		}catch(Exception e) {
	   			e.printStackTrace();
	   		}
	   		
	   		request.setAttribute("msg", msg);
			fetchUserRoles(request,response,page);
	   }
	
	public void fetchUserRoles(HttpServletRequest request, HttpServletResponse response,String page) {
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		if(userRoleLists.size() != 0) {
			for(int i = 0; i< userRoleLists.size(); i++) {
				 if(userRoleLists.get(i).getView() == 1 && userRoleLists.get(i).getPageIdNameHolder() == "DBRD") {
					request.setAttribute("DA", "Exists"); 
					System.out.println("DA ACCESS EXISTS");
				 }
			}
		}
		request.setAttribute("userRoleLists", userRoleLists);
		System.out.println(userRoleLists.size());
		dispatch(request,response,page);
		
	}
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String page = "../views/famsLogin.jsp";
		HttpSession mySession = request.getSession();
		mySession.invalidate();
		request.setAttribute("logout","yes");
		dispatch(request, response,page);
		
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response,String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			request.setAttribute("onServer", "yes");
			ds.forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
