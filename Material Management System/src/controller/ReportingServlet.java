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
import model.AssetSubCategory;
import model.PurchaseOrder;
import model.Reporting;
import model.Roles;
import model.Stock;
import repository.AssetCategoryRepository;
import repository.PORepository;
import repository.RBACRepository;
import repository.ReportingRepositoy;

/**
 * Servlet implementation class ReportingServlet
 */
@WebServlet("/ReportingServlet")
public class ReportingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private String whoami;
	private int uid;
    public ReportingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession mySession = request.getSession();
		whoami = (String)mySession.getAttribute("username");
		// TODO Auto-generated method stub
		try {
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			request.setAttribute("userRoleLists", userRoleLists);
			request.setAttribute("onServer", "yes");
			String path = request.getPathInfo();
			String page = "../views/categoryperbrn.jsp";
			switch(path) {
			case "/cpb" :
				loadAssetCategory(request,response,page);
				break;
			case "/rp1" :
				reportCategoryPerBranch(request,response);
				break;
			case "/print" :
				print(request,response);
				break;
			case "/grv" :
				String ppage = "../views/goodreceiving.jsp";
				loadAssetCategory(request,response,ppage);
				break;
			case "/rp2" :
				reportGoodReceiving(request,response);
				break;
			case "/print2" :
				print2(request,response);
				break;
			case "/fs" :
				fetchSubUnderPo(request,response);
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
		HttpSession mySession = request.getSession();
		whoami = (String)mySession.getAttribute("username");
		try {
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			request.setAttribute("userRoleLists", userRoleLists);
			request.setAttribute("onServer", "yes");
			String path = request.getPathInfo();
			switch(path) {
			case "rp1" :
				System.out.println("method rp1 called");
				reportCategoryPerBranch(request,response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
	}
	public void fetchPo(HttpServletRequest request,HttpServletResponse response) {
		ReportingRepositoy rr = new ReportingRepositoy();
		List<PurchaseOrder> polist = rr.fetchpo();
		request.setAttribute("polist", polist);
	}
	public void reportGoodReceiving(HttpServletRequest request,HttpServletResponse response) {
		ReportingRepositoy rs = new ReportingRepositoy();
			String rc = request.getParameter("rc");
			
			List<Stock> stocklist = rs.GRVReport(rc);
			System.out.println("size of stock is from grv "+stocklist.size());
			if(stocklist.size() != 0) {
				request.setAttribute("stocklist", stocklist);
				request.setAttribute("alltotal", stocklist.get(stocklist.size()-1).getRtotoal());	
				request.setAttribute("total", stocklist.size());
			}
			Stock stcObj = new Stock();
			stcObj.setRecievingCode(rc);
			request.setAttribute("stcObj",stcObj);
			loadAssetCategory(request,response,"../views/goodreceiving.jsp");		
	}
	public void print2(HttpServletRequest request, HttpServletResponse response) {
		ReportingRepositoy rs = new ReportingRepositoy();
		String rc = request.getParameter("rc");
		List<Stock> stocklist = rs.GRVReport(rc);
		request.setAttribute("stocklist", stocklist);
		System.out.println("size of stock is "+stocklist.size());
		if(stocklist.size() != 0) {
			request.setAttribute("alltotal", stocklist.get(stocklist.size()-1).getRtotoal());	
			request.setAttribute("total", stocklist.size());
		}
		loadAssetCategory(request,response,"../views/invoice-print3.jsp");
	}
	public void print(HttpServletRequest request, HttpServletResponse response) {
		String category = request.getParameter("category");
		String dfrom = request.getParameter("from");
		String dto = request.getParameter("to");
		ReportingRepositoy rs = new ReportingRepositoy();
		List<Reporting> rcpb = rs.categoryPerBranch(category,dfrom,dto);
		request.setAttribute("rcpb", rcpb);
		request.setAttribute("total", rcpb.size());
		if(rcpb.size() != 0) {
			request.setAttribute("alltotal", rcpb.get(rcpb.size()-1).getAlltotal());	
		}
		String page = "../views/invoice-print2.jsp";
		//request.setAttribute("category", category);
		loadAssetCategory(request,response, page);
	}
	public void reportCategoryPerBranch(HttpServletRequest request, HttpServletResponse response) {
		String category = request.getParameter("category");
		String dfrom = request.getParameter("from");
		String dto = request.getParameter("to");
		ReportingRepositoy rs = new ReportingRepositoy();
		List<Reporting> rcpb = rs.categoryPerBranch(category,dfrom,dto);
		request.setAttribute("rcpb", rcpb);
		request.setAttribute("total", rcpb.size());
		if(rcpb.size() != 0) {
			request.setAttribute("alltotal", rcpb.get(rcpb.size()-1).getAlltotal());	
		}
		String page = "../views/categoryperbrn.jsp";
		Reporting rpr = new Reporting();
		rpr.setRcategory(category);
		rpr.setRfrom(dfrom);
		rpr.setRto(dto);
		request.setAttribute("rpr", rpr);
		loadAssetCategory(request,response, page);
	}
	public void loadAssetCategory(HttpServletRequest request, HttpServletResponse response,String page) {
		List<AssetCategory> ac = new AssetCategoryRepository().fetchForReport();
		request.setAttribute("assetCategory",ac );
		ReportingRepositoy rr = new ReportingRepositoy();
		List<PurchaseOrder> polist = rr.fetchpo();
		request.setAttribute("polist", polist);
		if(polist.size() > 0) {
			List<AssetSubCategory> subcats = rr.fetch(polist.get(0).getId());
			request.setAttribute("subcats", subcats);
		}
		System.out.println("size of category" +ac.size());
		//dispatcher(request,response,page);
		//new LoginControllerServlet().fetchUserRoles(request,response,page);
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		System.out.println(userRoleLists.size());
		dispatcher(request,response,page);
	}
	public void dispatcher(HttpServletRequest request, HttpServletResponse response,String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			ds.forward(request, response);
			System.out.println("Dispatched");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadPoandSub(HttpServletRequest request, HttpServletResponse response,String page) {
		List<AssetCategory> ac = new AssetCategoryRepository().fetchForReport();
		request.setAttribute("assetCategory",ac );
		ReportingRepositoy rr = new ReportingRepositoy();
		List<PurchaseOrder> polist = rr.fetchpoForRec();
		request.setAttribute("polist", polist);
		if(polist.size() > 0) {
			List<AssetSubCategory> subcats = rr.pfetchSub2(polist.get(0).getId());
			request.setAttribute("subcats", subcats);
		}
		System.out.println("size of category" +ac.size());
		//dispatcher(request,response,page);
		//new LoginControllerServlet().fetchUserRoles(request,response,page);
	}
	public void fetchSubUnderPo(HttpServletRequest request,HttpServletResponse response) {
		ReportingRepositoy rs = new ReportingRepositoy();
		if(request.getParameter("su") != null) {
			int id = Integer.parseInt(request.getParameter("pos"));
			int sub = Integer.parseInt(request.getParameter("subcat"));
			PurchaseOrder poObj = new PORepository().fetchOne(id);
			request.setAttribute("poObj", poObj);
			List<AssetSubCategory> ssubcats = rs.fetch(id);
			request.setAttribute("ssubcats", ssubcats);
			request.setAttribute("searchResult", "searchResult");
			//loadAssetCategory(request,response,"../views/goodreceiving.jsp");
			new StockControllerServlet().fetchAll(request, response, "../views/famsStock.jsp");
			
		}else {
			String rc = request.getParameter("rc");
			
			List<Stock> stocklist = rs.GRVReport(rc);
			request.setAttribute("stocklist", stocklist);
			System.out.println("size of stock is "+stocklist.size());
			if(stocklist.size() != 0) {
				request.setAttribute("alltotal", stocklist.get(stocklist.size()-1).getRtotoal());	
				request.setAttribute("total", stocklist.size());
			}
			//loadAssetCategory(request,response,"../views/goodreceiving.jsp");
		}
		
	}
	

}
