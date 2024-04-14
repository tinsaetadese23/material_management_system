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

import model.AssetSubCategory;
import model.Message;
import model.Roles;
import model.Stock;
import repository.AssetSubCatRepository;
import repository.RBACRepository;
import repository.StockRepository;

/**
 * Servlet implementation class BranchLevelStatusChange
 */
@WebServlet("/BranchLevelStatusChange")
public class BranchLevelStatusChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int uid;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BranchLevelStatusChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());fetchAllAssets
		try {
			HttpSession mySession = request.getSession();
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			String page = "views/brnstatus.jsp";
			fetchAllAssets(request,response,page);
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
		request.setAttribute("onServer","yes");
		try {
			HttpSession mySession = request.getSession();
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			request.setAttribute("onServer", "true");
			String action = request.getPathInfo();
			switch(action) {
			case "/sch" :
				statusChange(request,response);
				break;
			case "/sch2" :
				statusChange2(request,response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
		
	}
	
	public void statusChange(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		StockRepository sr = new StockRepository();
		Message msg = new Message();
		String status = request.getParameter("status");
		if(sr.updateBrnStatus(status, id)) {
			msg.setSuccessMsg("Status Changed Successfully!");
		}else {
			msg.setErrorMsg("Error occured while status change");
		}
		request.setAttribute("msg", msg);
		String page = "../views/brnstatus.jsp";
		request.setAttribute("onServer", true);
		fetchAllAssets(request,response,page);
	}
	public void statusChange2(HttpServletRequest request, HttpServletResponse response) {
		Message msg   = new Message();
		if(request.getParameter("subcat") == null) {
		msg.setErrorMsg("Invalid SubCategory");
		request.setAttribute("msg", msg);
		}else {
			Integer subcat = Integer.parseInt(request.getParameter("subcat").toString());
			StockRepository sr = new StockRepository();
			List<Stock> stockList = sr.assetAssignedToCurrentUser(uid,subcat);
			request.setAttribute("stockList", stockList);
		}
		String page = "../views/brnstatus.jsp";
		fetchAllAssets(request,response,page);
	}
	public void fetchAllAssets(HttpServletRequest request, HttpServletResponse response,String page) {
		AssetSubCatRepository aSubCatRepository = new AssetSubCatRepository();
	
		List<AssetSubCategory> assetSubCatList = aSubCatRepository.fetch();
		StockRepository sr = new StockRepository();
		//List<Stock> stockList = sr.assetAssignedToCurrentUser(uid);
		List<Stock> stockList = sr.loadDistinctSubCatForBranch(uid);
		request.setAttribute("assetSubCatList", assetSubCatList);
		request.setAttribute("stockListt", stockList);
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		//request.setAttribute("onServer", true);
		dispatch(request,response,page);
		
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			//request.setAttribute("onServer","true");
			ds.forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
