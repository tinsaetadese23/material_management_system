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
 * Servlet implementation class AssetReturnControllerServlet
 */
/*@WebServlet("/AssetReturnControllerServlet")*/
public class AssetReturnControllerServlet extends HttpServlet {
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
    public AssetReturnControllerServlet() {
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
			AcessFindeer(request,response);
			String page = "views/famsAssetReturn.jsp";
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path){
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				delete( request,  response);
				break;
			default :
				fetchAllAssets(request,response,page);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds  = request.getRequestDispatcher("views/sessionTimeout.jsp");
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
			AcessFindeer(request,response);
			String action = request.getPathInfo();
			switch(action) {
			case "/tag" :
				findTag(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/ret" :
				processReturn(request, response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds  = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request,response);
		}
		
	}
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		AuthorizationRepository ar = new AuthorizationRepository();
		String sq1 = "UPDATE famsStock   \r\n" + 
				"								SET famsStock.issuedTo = famsStock_history.issuedTo,   \r\n" + 
				"								famsStock.status = famsStock_history.status, famsStock.authStatus = famsStock_history.authStatus, famsStock.maker = famsStock_history.maker,   \r\n" + 
				"								famsStock.checker = famsStock_history.checker,famsStock.issueref = famsStock_history.issueref, famsStock.sysGenTag = famsStock_history.sysGenTag   \r\n" + 
				"								FROM  famsStock   \r\n" + 
				"								INNER JOIN famsStock_history    \r\n" + 
				"								ON famsStock.id = famsStock_history.id   \r\n" + 
				"								WHERE famsStock_history.id = ? and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='AI' AND famsStock_history.col1 = 'AVAILABLE' and col3 ='UR'";
		String sq2 = "delete from famsStock_history where ((famsStock_history.id = ? and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='AI' AND  \r\n" + 
				"				famsStock_history.col1 = 'AVAILABLE' and col3 = 'UR')  \r\n" + 
				"				or (famsStock_history.id = ?  \r\n" + 
				"				and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='UR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'AI'))";
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
		
		String page = "../views/famsAssetReturn.jsp";
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);
	}
	public void AcessFindeer(HttpServletRequest request, HttpServletResponse response) {
		//
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		for (int i = 0; i < userRoleLists.size(); i++) {
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSRET")) {
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
		System.out.println("The Stock id is "+id);
		int action = Integer.parseInt(request.getParameter("action"));
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new StockRepository().fetchOne(id).getMaker())) {
				if(ar.authorize6("famsStock",id,action,"ART",whoami)) {
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
				}else {
					msg.setErrorMsg("Error occured while Authorizing!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canauth = false;
		}else {
			msg.setErrorMsg("Access Right Not Available");
		}
		
		String page = "../views/famsAssetReturn.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAllAssets(request,response,page);
	}
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		StockRepository sr = new StockRepository();
		Stock objprp = sr.fetchOne(id);
		request.setAttribute("objprp", objprp);
		request.setAttribute("subCatDetail", new AssetSubCatRepository().fetchOne(objprp.getSubCategoryCode()));
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetReturn.jsp";	
		fetchAllAssets(request,response,page);
	}
	public void findTag(HttpServletRequest request, HttpServletResponse response){
		String tag  = request.getParameter("tag");
		String page = "";
		Employee emp2 = new Employee();
		AssetRepository asr = new AssetRepository();
		if(request.getParameter("populate") != null || request.getParameter("su") != null) {
	   // emp2 = asr.empInfo(Integer.parseInt(request.getParameter("empId").toString()));
		  emp2 = new StockRepository().fetchEmployeeData(Integer.parseInt(request.getParameter("empId").toString()));
		}
		System.out.println("tag "+tag);
		System.out.println("emp2 branch"+emp2.getBranch());

		//Asset ast  = asr.findAssetByTag(tag); we comment this and added the line below
		Asset ast  = asr.findAssetByTagForReturn(tag);
		Employee emp = new StockRepository().fetchEmployeeData(ast.getId());
		//System.out.println("Emp name"+emp.getFullName());
		//System.out.println("ast name"+ast.getAssetSubCategory());
		//System.out.println("Emp name"+emp.getFullName());
		//System.out.println("emp id"+ast.getId());
		
		Message msg = new Message();
		if(request.getParameter("su") != null) {
			System.out.println("Entered this section");
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
		request.setAttribute("msg",msg);
		}

		request.setAttribute("emp", emp);
		request.setAttribute("emp2", emp2);
		request.setAttribute("ast", ast);
		if(request.getParameter("tr") != null) {
			 page = "../views/famsAssetReturn.jsp";
		}else if (request.getParameter("ds") != null)
		{
			//page = "../views/famsManualDisposal.jsp";
		}
		else {
			// page = "../views/famsAssetTransfer.jsp";	
		}
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);		
	}
	
	public void fetchAllAssets(HttpServletRequest request, HttpServletResponse response,String page) {
		AssetSubCatRepository aSubCatRepository = new AssetSubCatRepository();
	
		List<AssetSubCategory> assetSubCatList = aSubCatRepository.fetch();
		StockRepository sr = new StockRepository();
		List<Stock> stockList = sr.fetchUnathorizedReturn();
		request.setAttribute("assetSubCatList", assetSubCatList);
		request.setAttribute("stockList", stockList);
		//fetching roles
		request.setAttribute("total", stockList.size());
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		//request.setAttribute("onServer", true);
		dispatch(request,response,page);
		
	}
	public void processReturn(HttpServletRequest request, HttpServletResponse response) {
		AssetRepository asr = new AssetRepository();
		Message msg = new Message();
		if(caninsert) {
			if(asr.returnToStore(request.getParameter("tag"),whoami)) {
				msg.setSuccessMsg("Returning Item to store successful");
			}else {
				msg.setSuccessMsg("Returning Item to store is not successful");
			}
			caninsert = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		request.setAttribute("msg",msg);
		String page = "../views/famsAssetReturn.jsp";
		fetchAllAssets(request,response,page);
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
