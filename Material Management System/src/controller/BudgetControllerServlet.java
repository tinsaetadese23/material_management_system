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

import model.AssetClass;
import model.Budget;
import model.Message;
import model.Roles;
import model.Stock;
import repository.AssetClassRepository;
import repository.BudgetRepository;
import repository.RBACRepository;
import repository.StockRepository;

/**
 * Servlet implementation class BudgetControllerServlet
 */
@WebServlet("/BudgetControllerServlet")
public class BudgetControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 	private String whoami;  
	    private int uid;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BudgetControllerServlet() {
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
			String path = request.getPathInfo();
			String page = "views/storeIssueVoucher.jsp";
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/print" :
				print(request,response);
				break;
			case "/printfa" :
				printfa(request,response);
				break;
			case "/fa" :
				System.out.println("I am here bro");
				page = "../views/faStoreIssueVoucher.jsp";
				fetchAll(request,response,page);
				break;
				default:
					fetchAll( request,response,page);
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
			request.setAttribute("onServer", "yes");
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			String action = request.getPathInfo();
			switch(action)
			{
			case "/search" :
				searchRefNO(request,response);
				break;
			case "/ssearch" :
				ssearchRefNO(request,response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request,response);
		}
		
	}
	public void print(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("printing in progress");
		String ref = request.getParameter("issueref");
		StockRepository sr = new StockRepository();
		List<Stock> issueVoucher = sr.issueVoucher(ref);
		if(issueVoucher.size() == 0) {
			request.setAttribute("none", "yes");
		}else {
			request.setAttribute("total", issueVoucher.get(issueVoucher.size()-1).getTotalPrice());
		}
		request.setAttribute("issueV", issueVoucher);
		String page = "../views/invoice-print.jsp";
		dispatch(request,response,page);
	}
	public void printfa(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("printing in progress");
		String ref = request.getParameter("issueref");
		StockRepository sr = new StockRepository();
		List<Stock> issueVoucher = sr.sissueVoucher(ref);
		if(issueVoucher.size() == 0) {
			request.setAttribute("none", "yes");
		}else {
			request.setAttribute("total", issueVoucher.get(issueVoucher.size()-1).getTotalPrice());
		}
		request.setAttribute("issueV", issueVoucher);
		String page = "../views/invoice-print-fa.jsp";
		dispatch(request,response,page);
	}
	public void searchRefNO(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("we are budgetizna;s");
		String ref = request.getParameter("issueref");
		System.out.println("The issu ref no is"+ref);
		StockRepository sr = new StockRepository();
		List<Stock> issueVoucher = sr.issueVoucher(ref);
		if(issueVoucher.size() == 0) {
			System.out.println("The issu ref on if is"+ref);
			request.setAttribute("none", "yes");
		}else {
			System.out.println("The issu ref on else"+ref);
			request.setAttribute("total", sr.formatCurrency(issueVoucher.get(issueVoucher.size()-1).getRtotoal()));
		}
		System.out.println("The issu ref no outside"+ref);
		request.setAttribute("issueref", ref);
		request.setAttribute("issueV", issueVoucher);
		request.setAttribute("onServer", "yes");
		String page = "../views/storeIssueVoucher.jsp";
		fetchAll(request,response,page);
	}
	public void ssearchRefNO(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("we are budgetizna;s");
		String ref = request.getParameter("issueref");
		System.out.println("The issu ref no is"+ref);
		StockRepository sr = new StockRepository();
		List<Stock> issueVoucher = sr.sissueVoucher(ref);
		if(issueVoucher.size() == 0) {
			System.out.println("The issu ref on if is"+ref);
			request.setAttribute("none", "yes");
		}else {
			System.out.println("The issu ref on else"+ref);
			request.setAttribute("total", sr.formatCurrency(issueVoucher.get(issueVoucher.size()-1).getRtotoal()));
		}
		System.out.println("The issu ref no outside"+ref);
		request.setAttribute("issueref", ref);
		request.setAttribute("issueV", issueVoucher);
		request.setAttribute("onServer", "yes");
		String page = "../views/faStoreIssueVoucher.jsp";
		fetchAll(request,response,page);
	}
	public void directPrintingFromAuthorization(String refno,HttpServletRequest request, HttpServletResponse response,int subCat) {
		StockRepository sr = new StockRepository();
		List<Stock> issueVoucher = new ArrayList<Stock>();
		if(subCat == 0 || subCat == 1) {
			 issueVoucher = sr.sissueVoucher(refno);	
		}else {
			 issueVoucher = sr.issueVoucher(refno);
		}
		if(issueVoucher.size() == 0) {
			System.out.println("The issu ref on if is"+refno);
			request.setAttribute("none", "yes");
		}else {
			System.out.println("The issu ref on else"+refno);
			request.setAttribute("total", sr.formatCurrency(issueVoucher.get(issueVoucher.size()-1).getRtotoal()));
		}
		System.out.println("The issu ref no outside"+refno);
		request.setAttribute("issueref", refno);
		request.setAttribute("issueV", issueVoucher);
		request.setAttribute("onServer", "yes");
		String page = "../views/faStoreIssueVoucher.jsp";
		fetchAll(request,response,page);
	}
	
	public void fetchAll(HttpServletRequest request, HttpServletResponse response, String page) {
		//new LoginControllerServlet().fetchUserRoles(request, response, page);
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		dispatch(request,response,page);
	}
	
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			request.setAttribute("userRoleLists", userRoleLists);
			ds.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
