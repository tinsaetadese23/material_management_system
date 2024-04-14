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

import model.AssetCategory;
import model.Message;
import model.Roles;
import model.Supplier;
import repository.AssetCategoryRepository;
import repository.AuthorizationRepository;
import repository.RBACRepository;
import repository.SupplierRepository;

/**
 * Servlet implementation class SupplierControllerServlet
 */
@WebServlet("/SupplierControllerServlet")
public class SupplierControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String page = "";
    private String whoami;
    private int uid;
    private boolean canupdate = false;
    private boolean candelete = false;
    private boolean canauth = false;
    private boolean caninsert = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupplierControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			page = "views/famsSupplier.jsp";
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			String path = request.getPathInfo();
			if(path ==  null) {
				path = "default";
			}
			switch(path) {
			case "/edit" :
				findOne(request, response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				deleteSupplier(request,response);
				break;
			default :
				fetchAll(request,response,page);
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
		//doGet(request, response);
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			String path = request.getPathInfo();
			switch(path) {
			case "/create" :
				createSupplier(request, response);
				break;
			case "/edit" :
				findOne(request, response);
				break;
			case "/update" :
				updateSupplier(request,response);
				break;
			case "/delete" :
				deleteSupplier(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/search" :
				doSearching(request,response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request,response);
		}
	}
	public void AcessFindeer(HttpServletRequest request, HttpServletResponse response) {
		//
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		for (int i = 0; i < userRoleLists.size(); i++) {
			if(userRoleLists.get(i).getPageIdNameHolder().equals("SUPP")) {
				if(userRoleLists.get(i).getCreate() == 1) {
					caninsert = true;
				}
				if(userRoleLists.get(i).getUpdate() == 1) {
					canupdate = true;
				}
				if(userRoleLists.get(i).getDelete() == 1) {
					candelete = true;
				}
				if(userRoleLists.get(i).getAuth() == 1) {
					canauth = true;
				}	
				//break;
			}
		}
	}
	public void doSearching(HttpServletRequest request,HttpServletResponse response) {
		SupplierRepository supprep = new SupplierRepository();
        String sc = request.getParameter("sr");
        Supplier supp = new Supplier(); supp.setSupplierName(sc);request.setAttribute("asc", supp);
		List<Supplier> supplier = supprep.fetchSupplierName(sc);
		request.setAttribute("searchResult", supplier);
		if(supplier.size() == 0) {
			request.setAttribute("none", "none");
		}
		System.out.println("Size of result is"+supplier.size());
		request.setAttribute("stotal", supplier.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsSupplier.jsp";
		fetchAll(request,response,page);
	}
	
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_reason = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new SupplierRepository().fetchOne(id).getMaker())) {
				if(ar.authorize("famsSupplier", id,action,whoami,rejection_reason)) {
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
				}else {
					msg.setErrorMsg("Error occured while Authorizing!");
				}
			}else {
				msg.setErrorMsg("Access Right Unvailable");
			}
			canauth = false;
		}else {
			msg.setErrorMsg("Access Right Not Available");
		}
		String page = "../views/famsSupplier.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAll(request,response,page);
	}
	
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		
		SupplierRepository sr = new SupplierRepository();
		Supplier editSupplier = sr.edit(id);
		request.setAttribute("objprp", editSupplier);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsSupplier.jsp";
		fetchAll(request,response,page);
	}
	
	public void fetchAll(HttpServletRequest request, HttpServletResponse response, String page) {
		SupplierRepository sr = new SupplierRepository();
		List<Supplier> supplierList = sr.fetch();
		request.setAttribute("supplierList", supplierList);
		request.setAttribute("total", supplierList.size());
		//request.setAttribute("onServer", true);
		//fetching roles
		RBACRepository rbacRepository = new RBACRepository();
		List<Roles> userRoleLists = new ArrayList<>();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		dispatcher(request,response,page);
	}
	public void createSupplier(HttpServletRequest request, HttpServletResponse response) {
		Supplier supplier = new Supplier();
		supplier.setSupplierName(request.getParameter("supplierName").trim());
		supplier.setContactPerson(request.getParameter("contactPerson"));
		supplier.setContactNumber(request.getParameter("contactNumber"));
		supplier.setEmail(request.getParameter("email"));
		supplier.setZipCode(request.getParameter("zipCode"));
		supplier.setAddress(request.getParameter("address"));
		supplier.setWebsite(request.getParameter("website"));
		supplier.setMaker(whoami);
		

		Message msg = new Message();
		if(caninsert) {
			SupplierRepository sr = new SupplierRepository();
			if(!sr.isSupplierExists(supplier)) {
				if(sr.save(supplier)) {
					msg.setSuccessMsg("Record saved successfully!");
				}else {
					msg.setErrorMsg("Error while saving a record!");
				}
			}else {
				msg.setErrorMsg("Record Already Exists");
			}
			caninsert = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		
		page = "../views/famsSupplier.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAll(request,response,page);
		
	}
	public void updateSupplier(HttpServletRequest request, HttpServletResponse response){
		Supplier supplier = new Supplier();
		supplier.setId(Integer.parseInt(request.getParameter("id").toString()));
		supplier.setSupplierName(request.getParameter("supplierName"));
		supplier.setContactPerson(request.getParameter("contactPerson"));
		supplier.setContactNumber(request.getParameter("contactNumber"));
		supplier.setEmail(request.getParameter("email"));
		supplier.setZipCode(request.getParameter("zipCode"));
		supplier.setAddress(request.getParameter("address"));
		supplier.setWebsite(request.getParameter("website"));
		
		SupplierRepository sr = new SupplierRepository();
		Message msg = new Message();
		
		if(canupdate) {
			if(whoami.equals(sr.fetchOne(supplier.getId()).getMaker())) {
				if(sr.update(supplier)) {
					msg.setSuccessMsg("Record update successfully!");
				}else {
					msg.setErrorMsg("Error while updating a record!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canupdate = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		
		page = "../views/famsSupplier.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAll(request,response,page);
	}
	public void deleteSupplier(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		SupplierRepository sr = new SupplierRepository();
		
		Message msg = new Message();
		
		if(candelete) {
			if(whoami.equals(sr.fetchOne(id).getMaker())) {
				if(sr.delete(id)) {
					msg.setSuccessMsg("Record Deleted Successfully");	
					}else {
					msg.setErrorMsg("Error Occured while deleting the record!");
					}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		request.setAttribute("msg",msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsSupplier.jsp";
		fetchAll(request,response,page);
		
	}
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		
		SupplierRepository sr = new SupplierRepository();
		Supplier editSupplier = sr.edit(id);
		request.setAttribute("onServer", "yes");
		request.setAttribute("editSupplier", editSupplier);
		String page = "../views/famsSupplier.jsp";
		fetchAll(request,response,page);
	}
	public void dispatcher(HttpServletRequest request, HttpServletResponse response,String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			ds.forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
