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
import model.DashboardData;
import model.Reporting;
import model.Roles;
import model.Stock;
import repository.AssetSubCatRepository;
import repository.RBACRepository;
import repository.ReportingRepositoy;
import repository.StockRepository;
import repository.SupplierRepository;

/**
 * Servlet implementation class DashboardManagementController
 */
@WebServlet("/DashboardManagementController")
public class DashboardManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String whoami;
	private int uid;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardManagementController() {
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
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			System.out.println("we were once here from get");
			String page = "views/tester.jsp";
			fetchDashboardData(request,response,page);
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
		//fetchDashboardData(request, response,"views/tester.jsp");
		try {
			HttpSession mySession = request.getSession();
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			whoami = (String)mySession.getAttribute("username");
			System.out.println("we were once here from post");
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
		
	}
	public void fetchDashboardData(HttpServletRequest request, HttpServletResponse response,String page) {
		DashboardData dd = new DashboardData();
		StockRepository sr = new StockRepository();
		ReportingRepositoy rr = new ReportingRepositoy();
		dd.setTotalAsset(sr.totalAsset());
		dd.setTotalAssignedAsset(sr.totalAssignedAsset());
		dd.setTotalInStockAsset(sr.totalInstockAset());
		dd.setTotalSupplier(new SupplierRepository().totalSupp());
		AssetSubCatRepository asr = new AssetSubCatRepository();
		List<AssetSubCategory> asubList = asr.assetsPerSubCat();
		List<Reporting> repoList = rr.assetPerClass();
		List<Reporting> catList = rr.assignedAssetPerCategory();
		List<Reporting> acatList = rr.availableAssetPerCategory();
		RBACRepository rbacRepository = new RBACRepository();
		List<Roles> userRoleLists = new ArrayList<>();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		if(userRoleLists.size() != 0) {
			for(int i = 0; i< userRoleLists.size(); i++) {
				 if(userRoleLists.get(i).getView() == 1 && userRoleLists.get(i).getPageIdNameHolder() == "DBRD") {
					request.setAttribute("DA", "exists"); 
					System.out.println("DA ACCESS EXISTS");
				 }
			}
		}
		//List<Stock> listStocks = sr.assetAssignedToCurrentUser(uid);
		request.setAttribute("acatList", acatList);
		request.setAttribute("catList", catList);
		request.setAttribute("repoList", repoList);
		//request.setAttribute("listStocks", listStocks);
		//System.out.println("Size from stock "+listStocks.size());
		request.setAttribute("userRoleLists", userRoleLists);
		request.setAttribute("onServer", true);
		request.setAttribute("asubList", asubList);
		request.setAttribute("dashboarddata", dd);
		dispatch(request,response,page);
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response,String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			ds.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
