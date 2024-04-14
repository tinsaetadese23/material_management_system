package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Access;
import model.Employee;
import model.Message;
import model.Pages;
import model.RoleDefinition;
import model.Roles;
import model.User;
import repository.LoginRepository;
import repository.RBACRepository;
import repository.StockRepository;

/**
 * Servlet implementation class RBACControllerServlet
 */
//@WebServlet("/RBACControllerServlet")
public class RBACControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String whoami;
	private int uid;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RBACControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			System.out.println("We are proudly here!");
			String page = "views/famsRbac.jsp";
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) { 
			case "/edit" :
				prepareForUpdate(request,response);
				break;
			case "/delete" :
				deleteRole(request,response);
				break;
			case "/delm1" :
				deleteUserRoleMapping(request,response);
				break;
			case "/delm2" :
				deleteRoleDefinition(request,response);
				break;
			default :
				fetchAllPages(request,response,page);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			String action = request.getPathInfo();
			switch(action){
			case "/create" :
				createRole(request,response);
				break;
			case "/createRole" :
				createRoleDefinition(request,response);
				break;
			case "/createAccess" :
				createAccess(request,response);
				break;
			case "/find" :
				findOne(request,response);
				break;
			case "/edit" :
				prepareForUpdate(request,response);
				break;
			case "/update" :
				updateRoles(request,response);
				break;
			case "/emp" :
				findEmployeeId(request,response);
				break;
			case "/reset" :
				passwordReset(request,response);
				break;
			case "/user" :
				createUser(request,response);
				break;
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request,response);
		}
		
	}
	public void deleteUserRoleMapping(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		RBACRepository rr = new RBACRepository();
		Message msg = new Message();
		if(rr.deleteFromUserRoleMapping(id)) {
			msg.setSuccessMsg("User-Role Mapping Record Deleted Successfully");
		}else {
			msg.setErrorMsg("Error Occurred while deleting the record");
		}
		request.setAttribute("msg", msg);
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
	}
	//deleteRoleDefinition
	public void deleteRoleDefinition(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		RBACRepository rr = new RBACRepository();
		Message msg = new Message();
		if(rr.deleteFromRoleDefinition(id)) {
			msg.setSuccessMsg("Role Definition Record Deleted Successfully");
		}else {
			msg.setErrorMsg("Error Occurred while deleting the record");
		}
		request.setAttribute("msg", msg);
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
	}
	public void createUser(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username").toString().toLowerCase().trim();
		String password = request.getParameter("password");
		int empId = Integer.parseInt(request.getParameter("empId").toString());
		Message msg = new Message();
		if(new RBACRepository().isUserNameTaken(username)) {
			msg.setErrorMsg("oops! username " +"\""+username+"\""+ " is taken");
		}else {
			if(new LoginRepository().createUser(username, password, empId, whoami)) {
				msg.setSuccessMsg("User Created Successfully!");
			}else {
				msg.setErrorMsg("Error Occurre while creating the user");
			}
		}
		String page = "../views/famsRbac.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAllPages(request,response,page);
		
	}
	public void passwordReset(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Message msg = new Message();
		if(new LoginRepository().updatePassword(password, username,whoami)) {
			msg.setSuccessMsg("Password resetted successfully!");
		}else {
			msg.setErrorMsg("Error occured while resetttig the password");
		}
		request.setAttribute("msg", msg);
		request.setAttribute("onServer","yes");
		fetchAllPages(request,response,"../views/famsRbac.jsp");
	}
	public void findEmployeeId(HttpServletRequest request, HttpServletResponse response) {
		int empId = Integer.parseInt(request.getParameter("emp"));
		StockRepository sr = new StockRepository();
		LoginRepository rr = new LoginRepository();
		Employee emp  = rr.fetchEmployeeDataForUserCreation(empId);
		
		if(!new RBACRepository().fetchUserWithEmp(empId).equals("")) {
			request.setAttribute("ue", "true");
			request.setAttribute("user", new RBACRepository().fetchUserWithEmp(empId));
			System.out.println("The user id is" + new RBACRepository().fetchUserWithEmp(empId));
		}
		if(emp != null) {
			request.setAttribute("emp", emp);
		}else {
			System.out.println("Employee not found");
		}
		String  page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
		
	}
	
	public void deleteRole(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		RBACRepository rr = new RBACRepository();
		Message msg = new Message();
		if(rr.deleteRoles(id)) {
			msg.setSuccessMsg("Role Deleted Successfully!");
		}else {
			msg.setErrorMsg("Error while deleting");
		}
		request.setAttribute("msg", msg);
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
		
	}
	
	public void fetchAllPages(HttpServletRequest request, HttpServletResponse response,String page) {
		List<Pages> pagesList = new ArrayList<>();
		List<Roles> rolesList = new ArrayList<>();
		List<RoleDefinition> rolDefinitionList = new ArrayList<>();
		List<User> userList = new ArrayList<>();
		List<Access> accessList = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		pagesList = rbacRepository.fetchAllPages();
		rolesList = rbacRepository.fetchAllRoles();
		rolDefinitionList = rbacRepository.fetchAllRoleDefinition();
		userList = rbacRepository.fetchAllUsers();
		accessList = rbacRepository.fetchAllAccess();
		
		request.setAttribute("accessList", accessList);
		request.setAttribute("pagesList", pagesList);
	//	request.setAttribute("rolesList", rolesList);
		request.setAttribute("rolDefinitionList",rolDefinitionList);
		request.setAttribute("userList", userList);
		request.setAttribute("totAccessSize", accessList.size());
		request.setAttribute("totUserSize",userList.size());
		request.setAttribute("totPageSize",pagesList.size());
		request.setAttribute("totRolesSize",rolesList.size());
		request.setAttribute("totRolDefnSize",rolDefinitionList.size());
		request.setAttribute("onServer", true);
		List<Roles> userRoleLists = new ArrayList<>();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		if(request.getAttribute("dsend") != null) {
			
			List<Roles> rolesListt = new ArrayList<>();
			RBACRepository rr = new RBACRepository();
			rolesListt = rr.findOne(request.getAttribute("dsend").toString());
			request.setAttribute("roleLists", rolesListt);
		}
		dispatch(request,response,page); 
	
	}
	
	public void createRole(HttpServletRequest request, HttpServletResponse response) {
		int rolId = Integer.parseInt(request.getParameter("rolId").toString());
		int rolFunction = Integer.parseInt(request.getParameter("rolFunction").toString());
		String access[] = request.getParameterValues("access");
		int ve=0,cr=0,up=0,dl=0,au = 0;
		
		for(int i = 0;i < access.length; i++) {
		switch(access[i]) {
		case "ve" :
			ve = 1;
			break;
		case "cr" :
			cr = 1;
			break;	
		case "up" :
			up = 1;
			break;
		case "dl" :
			dl = 1;
			break;	
		case "au" :
			au = 1;
			break;
			}
		}
		
		Roles roles = new Roles();
		roles.setRolId(rolId);
		roles.setPageId(rolFunction);
		roles.setCreate(cr);
		roles.setView(ve);
		roles.setUpdate(up);
		roles.setDelete(dl);
		roles.setAuth(au);
		
		RBACRepository rbacRepository = new RBACRepository();
		Message msg = new Message();
		if(!rbacRepository.isRoleExists(roles)) {
			if(rbacRepository.saveRoles(roles,whoami)) {
				msg.setSuccessMsg("Record Saved Successfully!");
			}else {
				msg.setErrorMsg("Error occure while saving the data");
			}
		}else {
			msg.setErrorMsg("Role to Function Mapping Already Exists");
		}
		
		request.setAttribute("msg", msg);
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);

	}
	
	public void updateRoles(HttpServletRequest request, HttpServletResponse response){
		int rolId = Integer.parseInt(request.getParameter("id").toString());
		int rolFunction = Integer.parseInt(request.getParameter("rolFunction").toString());
		String access[] = request.getParameterValues("access");
		int ve=0,cr=0,up=0,dl=0,au=0;
		
		for(int i = 0;i < access.length; i++) {
		switch(access[i]) {
		case "ve" :
			ve = 1;
			break;
		case "cr" :
			cr = 1;
			break;	
		case "up" :
			up = 1;
			break;
		case "dl" :
			dl = 1;
			break;	
		case "au" :
			au = 1;
			break;
			}
		}
		
		Roles roles = new Roles();
		roles.setId(rolId);
		roles.setPageId(rolFunction);
		roles.setCreate(cr);
		roles.setView(ve);
		roles.setUpdate(up);
		roles.setDelete(dl);
		roles.setAuth(au);
		
		RBACRepository rbacRepository = new RBACRepository();
		Message msg = new Message();
		if(rbacRepository.updateRoles(roles)) {
			msg.setSuccessMsg("Record Updated Successfully!");
		}else {
			msg.setErrorMsg("Error occure while updating the data");
		}
		
		System.out.println("the role id is"+rolId);
		request.setAttribute("msg", msg);
		request.setAttribute("dsend", request.getAttribute("rolId"));
		System.out.println("the rolename is "+request.getAttribute("rolId"));
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
	}
	
	public void createRoleDefinition(HttpServletRequest request, HttpServletResponse response) {
		String roleName = request.getParameter("roleName");
		String roleDesc = request.getParameter("roleDefn");
		
		RoleDefinition roleDefn = new RoleDefinition();
		roleDefn.setRoleName(roleName);
		roleDefn.setRoleDefinition(roleDesc);
		
		
		RBACRepository rbacRepository = new RBACRepository();
		Message msg = new Message();
		if(!rbacRepository.isRoleDefinitionExists(roleDefn)) {
			if(rbacRepository.saveRoleDefinition(roleDefn)) {
				msg.setSuccessMsg("Record Saved Succeesfully");
			}else {
				msg.setErrorMsg("Error Ocurred while saving!");
			}
		}else {
			msg.setErrorMsg("Role Definition Already Exists");
		}
		
		request.setAttribute("msg", msg);
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
		
		
	}
	public void createAccess(HttpServletRequest request, HttpServletResponse response) {
		int userId = Integer.parseInt(request.getParameter("userId").toString());
		int rolId = Integer.parseInt(request.getParameter("rolId").toString());
		
		Access access = new Access();
		access.setUserId(userId);
		access.setRolId(rolId);
		
		RBACRepository rbacRepository = new RBACRepository();
		Message msg = new Message();
		if(!rbacRepository.isUserRoleMappingExists(access)) {
			if(rbacRepository.saveAccessToUser(access)){
				msg.setSuccessMsg("Record Saved Succeesfully");
			}else {
				msg.setErrorMsg("Error Ocurred while saving!");
			}
		}else {
			msg.setErrorMsg("User Role Mapping Already Exists!");
		}
		
		request.setAttribute("msg", msg);
		String page = "../views/famsRbac.jsp";
		fetchAllPages(request,response,page);
		
		
	}
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		String rolId = request.getParameter("rolId");
		List<Roles> rolesList = new ArrayList<>();
		RBACRepository rr = new RBACRepository();
		rolesList = rr.findOne(rolId);
		
		request.setAttribute("roleLists", rolesList);
		String page = "../views/famsRbac.jsp";
		System.out.println("Total number of roles"+rolesList.size());
		fetchAllPages(request,response,page);
			
	}
	public void prepareForUpdate(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		List<Roles> rolesObj = new ArrayList<>();
		RBACRepository rr = new RBACRepository();
		rolesObj = rr.findOne2(id);
		
		request.setAttribute("rolesObj", rolesObj);
		String page = "../views/famsRbac.jsp";
		System.out.println("Total number of roles"+rolesObj.size());
		fetchAllPages(request,response,page);
		
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
		RequestDispatcher ds = request.getRequestDispatcher(page);
		ds.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
