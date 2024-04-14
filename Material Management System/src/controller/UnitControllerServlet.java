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

import model.Message;
import model.Roles;
import model.Unit;
import repository.AuthorizationRepository;
import repository.RBACRepository;
import repository.UnitRepository;

/**
 * Servlet implementation class UnitControllerServlet
 */
//*@WebServlet*/("/UnitControllerServlet")
public class UnitControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String whoami; 
    private int uid;
    private boolean canupdate = false;
    private boolean candelete = false;
    private boolean canauth = false;
    private boolean caninsert = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnitControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("we are proudly here");
		try {
			String page = "views/famsAssetUnit.jsp";
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/delete" :
				deleteUnit(request,response);
			break;
			case "/edit" :
				findOne(request,response);
					break;
			case "/view" :
				prepareForView(request,response);
				break;
				default:
				fetchUnits(request, response,page);
				break;
					
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());	
			AcessFindeer(request,response);
			String action = request.getPathInfo();
			switch(action) {
			case "/create" :
				createUnit(request,response);
			break;
			case "/delete" :
				deleteUnit(request,response);
			break;
			case "/edit" :
				findOne(request,response);
					break;
			case "/update" :
				updateUnit(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
		
	}
	public void AcessFindeer(HttpServletRequest request, HttpServletResponse response) {
		//
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		for (int i = 0; i < userRoleLists.size(); i++) {
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSUN")) {
				if(userRoleLists.get(i).getCreate() == 1) {
					caninsert = true;
				}
				if(userRoleLists.get(i).getUpdate() == 1) {
					canupdate = true;
				}
				if(userRoleLists.get(i).getDelete() == 1) {
					candelete = true;
				}
				if(userRoleLists.get(i).getView() == 1) {
					canauth = true;
				}	
				//break;
			}
		}
	}
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_reason = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new UnitRepository().edit(id).getMaker())) {
				if(ar.authorize("assetUnitf", id,action,whoami,rejection_reason)) {
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
				}else {
					msg.setErrorMsg("Error occured while Authorizing!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canauth = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		String page = "../views/famsAssetUnit.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchUnits(request,response,page);
	}
	
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		UnitRepository ur = new UnitRepository();
		Unit editUnit = ur.edit(id);
		request.setAttribute("objprp", editUnit);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetUnit.jsp";
		fetchUnits(request,response,page);
	}
	public void createUnit(HttpServletRequest request, HttpServletResponse response) {
		//receiving request-date
		String unitName = request.getParameter("unitName");
		String unitDesc = request.getParameter("unitDesc");
		String message = "";
		
		Unit unit = new Unit();
		unit.setUnitName(unitName);
		unit.setUnitDesc(unitDesc);
		unit.setMaker(whoami);
		
		Message msg = new Message();
		UnitRepository unitRepository = new UnitRepository();
		if(caninsert) {
			if(!unitRepository.isUnitExists(unit)) {
				if(unitRepository.save(unit)) {
					msg.setSuccessMsg("New Record has been created successfully!");	
					}else {
					msg.setErrorMsg("Error occured while creating new Record!");
					}
			}else {
				msg.setErrorMsg("Record Already Exists");
			}
			caninsert = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		System.out.println("the message from db is "+message);
		request.setAttribute("message", message);
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetUnit.jsp";
		fetchUnits(request,response,page);
		//dispatch(request,response,page);
		
	}
	public void fetchUnits(HttpServletRequest request, HttpServletResponse response,String page){
		
		UnitRepository unitRepository = new UnitRepository();
		List<Unit> unit = unitRepository.fetchUnathorized();
		
		request.setAttribute("total", unit.size()); 
		request.setAttribute("assetUnit", unit);
		//String page = "views/famsAssetUnit.jsp";
		//fetching roles
		//request.setAttribute("onServer", true);
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		dispatch(request,response,page);
		
		
	}
	public void deleteUnit(HttpServletRequest request, HttpServletResponse response) {
		Integer id = Integer.parseInt(request.getParameter("id").toString());
	
		Message msg = new Message();
		UnitRepository unitRepository = new UnitRepository();
		if(candelete) {
			if(whoami.equals(unitRepository.edit(id).getMaker())) {
				if(unitRepository.delete(id)) {
					msg.setSuccessMsg("Record Deleted Succussfully!");
				}else {
					msg.setErrorMsg("Error Occured While Deleting the Record!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}	
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		String page = "../views/famsAssetUnit.jsp";
		fetchUnits(request,response,page);
		
		
		
	}
	public void updateUnit(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		String unitName = request.getParameter("unitName");
		String unitDesc = request.getParameter("unitDesc");
		String message = "";
		
		Unit unit = new Unit();
		unit.setId(id);
		unit.setUnitName(unitName);
		unit.setUnitDesc(unitDesc);
		
		Message msg = new Message();
		UnitRepository unitRepository = new UnitRepository();
		if(canupdate) {
			if(whoami.equals(unitRepository.edit(id).getMaker())) {
				if(unitRepository.update(unit)){
					msg.setSuccessMsg("Record has been updated successfully!");	
					}else {
					msg.setErrorMsg("Error occured while updating the Record!");
					}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canupdate = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable.");
		}
		
		
		request.setAttribute("msg", msg);
		//request.setAttribute("assetUnit", "assetUnit");
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetUnit.jsp";
		fetchUnits(request,response,page);
		
	}
	
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		UnitRepository ur = new UnitRepository();
		Unit editUnit = ur.edit(id);
		request.setAttribute("editUnit", editUnit);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetUnit.jsp";
		fetchUnits(request,response,page);
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response,String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			ds.forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
