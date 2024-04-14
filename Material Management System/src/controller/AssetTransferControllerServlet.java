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

import model.Asset;
import model.AssetSubCategory;
import model.Employee;
import model.Message;
import model.Roles;
import model.Stock;
import repository.AssetRepository;
import repository.AssetSubCatRepository;
import repository.AuthorizationRepository;
import repository.RBACRepository;
import repository.StockRepository;

/**
 * Servlet implementation class AssetTransferControllerServlet
 */
//@WebServlet("/AssetTransferControllerServlet")
public class AssetTransferControllerServlet extends HttpServlet {
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
    public AssetTransferControllerServlet() {
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
			AcessFindeer(request,response);
			String page = "views/famsAssetTransfer.jsp";
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				delete(request,response);
				break;
			default :
				fetchAllAssets(request,response,page);
			}
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("onServer", "yes");
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request, response);
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
			AcessFindeer(request,response);
			String action = request.getPathInfo();
			switch(action) {
			case "/tag" :
				findTag(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/delete" :
				delete(request,response);
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
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSTRN")) {
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
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		AuthorizationRepository ar = new AuthorizationRepository();
		String sq1 = "UPDATE famsStock \r\n" + 
				"				SET famsStock.issuedTo = famsStock_history.issuedTo, \r\n" + 
				"				famsStock.status = famsStock_history.status, famsStock.authStatus = famsStock_history.authStatus, famsStock.maker = famsStock_history.maker, \r\n" + 
				"				famsStock.checker = famsStock_history.checker,famsStock.issueref = famsStock_history.issueref, famsStock.sysGenTag = famsStock_history.sysGenTag \r\n" + 
				"				FROM  famsStock \r\n" + 
				"				INNER JOIN famsStock_history  \r\n" + 
				"				ON famsStock.id = famsStock_history.id \r\n" + 
				"				WHERE famsStock_history.id = ? and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='AI' AND famsStock_history.col1 = 'ISSUED-ON-TRN' and col3 ='UTRN'";
		String sq2 = "delete from famsStock_history where ((famsStock_history.id = ? and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='AI' AND\r\n" + 
				"famsStock_history.col1 = 'ISSUED-ON-TRN' and col3 = 'UTRN')\r\n" + 
				"or (famsStock_history.id = ?\r\n" + 
				"and famsStock_history.status = 'ISSUED-ON-TRN' and famsStock_history.authStatus='UTRN' AND famsStock_history.col1 = 'ISSUED' and col3 = 'AI'))";
		Message msg = new Message();
		if(candelete) {
			if(whoami.equals(new StockRepository().fetchOne(id).getMaker())) {
				if(ar.passetToPriviousState(id, sq1)) {
					if(ar.pdeleteHistoryOnQueuee(id, sq2)) {
						msg.setSuccessMsg("Record Deleted Successfully!");
					}else {
						msg.setErrorMsg("Error Occured While Deleting the record");
						System.out.println("Couldnt delete from famsStock_history on "+id);
					}
				}else {
					msg.setErrorMsg("Error Occured While Returning Asset to previous state");
					System.out.println("Couldnt Update from famsStock on "+id);
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}

			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		String page = "../views/famsAssetTransfer.jsp";
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);
	}
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("The Stock id is "+id);
		int action = Integer.parseInt(request.getParameter("action"));
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new StockRepository().fetchOne(id).getMaker())) {
				if(ar.authorize4("famsStock",id,action,"ATRN",whoami)) {
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
				}else {
					msg.setErrorMsg("Error occured while Authorizing!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canauth= false;
		}else {
			canauth = false;
		}
		String page = "../views/famsAssetTransfer.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAllAssets(request,response,page);
	}
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		StockRepository sr = new StockRepository();
		Stock objprp = sr.fetchOne(id);
		request.setAttribute("objprp", objprp);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetTransfer.jsp";	
		fetchAllAssets(request,response,page);
	}
	public void findTag(HttpServletRequest request, HttpServletResponse response){
		Message msg = new Message();
		String tag  = request.getParameter("tag");
		String page = "";
		Employee emp2 = new Employee();
		AssetRepository asr = new AssetRepository();
		if(request.getParameter("populate") != null || request.getParameter("su") != null) {
	    emp2 = new StockRepository().fetchEmployeeDataOnIssue(Integer.parseInt(request.getParameter("empId").toString()),request.getParameter("brncode"));
	    if(emp2 != null) {System.out.println("employee has been found!");request.setAttribute("efound", "efound");}
	    else {msg.setErrorMsg("Employee Not Found"); request.setAttribute("msg", msg);}
		}
		//System.out.println("tag "+tag);
		//System.out.println("emp2 branch"+emp2.getBranch());

		Asset ast  = asr.findAssetByTagForReturn(tag);
		Employee emp = new StockRepository().fetchEmployeeData(ast.getId());
		if(ast.getTagNumber() == null) {
			msg.setErrorMsg("Asset with UUID: "+tag +" not found");
			request.setAttribute("msg", msg);
		}else {
			request.setAttribute("found", "found");
		}
		//System.out.println("Emp name"+emp.getFullName());
		//System.out.println("ast name"+ast.getAssetSubCategory());
		//System.out.println("Emp name"+emp.getFullName());
		//System.out.println("emp id"+ast.getId());
		
		if(request.getParameter("su") != null) {
			System.out.println("Entered this section");
			if(caninsert) {
				if(Integer.parseInt(request.getParameter("pempid")) != emp2.getId()) {
					if(asr.update(tag,emp2.getId(),whoami)){
						msg.setSuccessMsg("Asset Transfer Successfull");
						emp = new Employee();
						emp2 = new Employee();
						ast = new Asset();
						
					}else {
						msg.setErrorMsg("Asset Transfer Unsuccessful ");
						emp = new Employee();
						emp2 = new Employee();
						ast = new Asset();
					}
				}else {
					msg.setErrorMsg("Asset Being Issued to the previous holder.");
					request.setAttribute("msg", msg);
				}
				caninsert = false;
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
		request.setAttribute("msg",msg);
		}

		request.setAttribute("emp", emp);
		request.setAttribute("emp2", emp2);
		request.setAttribute("ast", ast);
		if(request.getParameter("tr") != null) {
			// page = "../views/famsAssetReturn.jsp";
		}else if (request.getParameter("ds") != null)
		{
			//page = "../views/famsManualDisposal.jsp";
		}
		else {
			 page = "../views/famsAssetTransfer.jsp";	
		}
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);		
	}
	
	public void fetchAllAssets(HttpServletRequest request, HttpServletResponse response,String page) {
		AssetSubCatRepository aSubCatRepository = new AssetSubCatRepository();
	
		List<AssetSubCategory> assetSubCatList = aSubCatRepository.fetch();
		StockRepository sr = new StockRepository();
		List<Stock> stockList = sr.fetchUnathorizedTransfer();
		request.setAttribute("total", stockList.size());
		request.setAttribute("assetSubCatList", assetSubCatList);
		request.setAttribute("stockList", stockList);
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
			request.setAttribute("onServer","true");
			ds.forward(request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
