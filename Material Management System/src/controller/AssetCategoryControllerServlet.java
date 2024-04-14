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
import model.AssetClass;
import model.Depreciation;
import model.Message;
import model.Roles;
import repository.AssetCategoryRepository;
import repository.AssetClassRepository;
import repository.AuthorizationRepository;
import repository.DepreciationRepository;
import repository.RBACRepository;

/**
 * Servlet implementation class AssetCategoryControllerServlet
 */
/*@WebServlet("/AssetCategoryControllerServlet")*/
public class AssetCategoryControllerServlet extends HttpServlet {
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
    public AssetCategoryControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			System.out.println("we are proudly here!!");
			String page = "views/famsAssetCategory.jsp";
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			for (int i = 0; i < userRoleLists.size(); i++) {
				if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSCAT")) {
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
			//fetchAllAssetCategory( request,  response,page);
			String path = request.getPathInfo();
			if(path == null) {
				path ="default";
			}
			switch(path){
			case "/view" :
				prepareForView(request,response);
				break;
			case "/edit" :
				findOne(request,response);
				break;
			case "/delete" :
				deleteAssetCat(request,response);
				break;
				default :
					fetchAllAssetCategory( request,  response,page);
					break;
			}
		}catch(Exception e) {
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String action = request.getServletPath();
		try {
			String action = request.getPathInfo();
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			//System.out.println("Action came with " + action);
	        //roles
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			for (int i = 0; i < userRoleLists.size(); i++) {
				if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSCAT")) {
					if(userRoleLists.get(i).getCreate() == 1) {
						System.out.println("i can creqte");
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
			//
			switch(action) {
			case "/create" :
				createAssetCat(request,response);
				break;
			case "/edit" :
				findOne(request,response);
				break;
			case "/update" :
				updateAssetCat(request,response);
				break;
			case "/delete" :
				deleteAssetCat(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/search" :
				System.out.println("welcome" +request.getParameter("sr"));
				doSearching(request,response);
				break;
				
			}
		}catch(Exception e) {
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}	
			
	}
	public void doSearching(HttpServletRequest request,HttpServletResponse response) {
		AssetCategoryRepository ascr = new AssetCategoryRepository();
        String sc = request.getParameter("sr");
        AssetCategory ascat = new AssetCategory(); ascat.setAssetCatName(sc);request.setAttribute("asc", ascat);
		List<AssetCategory> assetCategory = ascr.fetchByCatCode(sc);
		request.setAttribute("searchResult", assetCategory);
		if(assetCategory.size() == 0) {
			request.setAttribute("none", "none");
		}
		System.out.println("Size of result is"+assetCategory.size());
		request.setAttribute("stotal", assetCategory.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetCategory.jsp";
		fetchAllAssetCategory(request,response,page);
	}
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_reason = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new AssetCategoryRepository().fetchOne(id).getMaker())) {
				if(ar.authorize("assetCat", id,action,whoami,rejection_reason)) {
					//msg.setSuccessMsg("Record Authorized Successfully");
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
				}else {
					msg.setErrorMsg("Error occured while Authorizing!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailbale");
			}
			canauth = false;
		}else {
			msg.setErrorMsg("Access Right Not Available");
		}
		String page = "../views/famsAssetCategory.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAllAssetCategory(request,response,page);
	}
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("findOne AssetCategory method has been called!");
		
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetCategory assetCategoryObj = assetCategoryRepository.fetchOne(id);
		request.setAttribute("objprp", assetCategoryObj);
		request.setAttribute("onServer", "yes");
		
		String page = "../views/famsAssetCategory.jsp";
		fetchAllAssetCategory(request,response,page);
	}
	public  void createAssetCat(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("createAssetCategory has been called!");
		boolean isCreated = false;
		
		//String message = "";
		AssetCategory assetCategoryObj = new AssetCategory();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		String assetCatName = request.getParameter("assetCatName").trim();
		String assetCatDesc  = request.getParameter("assetCatDesc");
		String assetCatMaint = request.getParameter("assetCatMaint");
		String assetCatLife = request.getParameter("assetCatLife");
		int asseCatDepr = Integer.parseInt(request.getParameter("depr").toString());
		int assetCatClass = Integer.parseInt(request.getParameter("assetCatClass"));
		
		assetCategoryObj.setAssetCatName(assetCatName); 
		assetCategoryObj.setAssetCatDesc(assetCatDesc);
		assetCategoryObj.setAssetCatMaint(assetCatMaint);
		assetCategoryObj.setAssetCatLife(assetCatLife);
		assetCategoryObj.setAssetCatCatDepr(asseCatDepr);
		assetCategoryObj.setAssetClass(assetCatClass);
		assetCategoryObj.setMaker(whoami);
		
		Message msg = new Message();
		if(caninsert) {
			if(!assetCategoryRepository.assetCategoryAlreadyExists(assetCategoryObj)) {
				if(assetCategoryRepository.save(assetCategoryObj)) {
					msg.setSuccessMsg("New Asset Category Has Been Created Successfully!!")	;
					//System.out.print(message);
					}
					else
					{
					msg.setErrorMsg("Erro while Creating");
					//System.out.print(message);
					}	
			}else
			{
				msg.setErrorMsg("Record Already Exists");
			}
			caninsert = false;
		}
		else {
				msg.setErrorMsg("Access Right Unavailable");
		}
		
		
		//for fetching
		List<AssetClass> assetClass = assetClassRepository.fetchAuthorized();
		List<AssetCategory> listOfAssetCategory = assetCategoryRepository.fetch();
		request.setAttribute("assetClass", assetClass);
		request.setAttribute("assetCategory", listOfAssetCategory);
		request.setAttribute("total", listOfAssetCategory.size());
		//end of fetching
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetCategory.jsp";
		fetchAllAssetCategory(request,response,page);
	}
	
	public void fetchAllAssetCategory(HttpServletRequest request, HttpServletResponse response,String page) {
		System.out.println("fetchAllAssetCategoryMethod has been called!");
		boolean isFetched = false;
		
		String message = "";
		AssetCategory assetCategoryObj = new AssetCategory();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		List<AssetCategory> listOfAssetCategory = assetCategoryRepository.fetch();
		List<AssetClass> assetClass = assetClassRepository.fetchByAuth();
		System.out.println("asset class size =+"+ assetClass.size());
		
		DepreciationRepository dsr = new DepreciationRepository();
		List<Depreciation> depreciationList = dsr.fetch();
		request.setAttribute("depreciationList",depreciationList);
		
		request.setAttribute("total", listOfAssetCategory.size());
		request.setAttribute("assetCategory", listOfAssetCategory);
		request.setAttribute("assetClass", assetClass);
		//request.setAttribute("onServer", true);
		
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		//String page = "views/famsAssetCategory.jsp";
		dispatch(request,response,page);
 
	}
	
	
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("findOne AssetCategory method has been called!");
		
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		AssetCategory assetCategoryObj = assetCategoryRepository.fetchOne(id);
		
		List<AssetCategory> listOfAssetCategory = assetCategoryRepository.fetch();
		List<AssetClass> assetClass = assetClassRepository.fetchAuthorized();
		
		request.setAttribute("oneAssetCategory", assetCategoryObj);
		request.setAttribute("total", listOfAssetCategory.size());
		request.setAttribute("assetCategory", listOfAssetCategory);
		request.setAttribute("oneAssetCategory", assetCategoryObj);
		request.setAttribute("assetClass", assetClass);
		request.setAttribute("onServer", "yes");
		System.out.println("maintenance" +assetCategoryObj.getAssetCatMaint());	
		String page = "../views/famsAssetCategory.jsp";
		fetchAllAssetCategory(request,response, page);
	}
	
	public void updateAssetCat(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("UpdateCategory has been called!");
		int id = Integer.parseInt(request.getParameter("id").toString());
		boolean isCreated = false;
		
		String message = "";
		AssetCategory assetCategoryObj = new AssetCategory();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		String assetCatName = request.getParameter("assetCatName").trim();
		String assetCatDesc  = request.getParameter("assetCatDesc");
		String assetCatMaint = request.getParameter("assetCatMaint");
		String assetCatLife = request.getParameter("assetCatLife");
		int asseCatDepr = Integer.parseInt(request.getParameter("assetCatDepr").toString());
		int assetCatClass = Integer.parseInt(request.getParameter("assetCatClass"));
		
		assetCategoryObj.setId(id);
		assetCategoryObj.setAssetCatName(assetCatName); 
		assetCategoryObj.setAssetCatDesc(assetCatDesc);
		assetCategoryObj.setAssetCatMaint(assetCatMaint);
		assetCategoryObj.setAssetCatLife(assetCatLife);
		assetCategoryObj.setAssetCatCatDepr(asseCatDepr);
		assetCategoryObj.setAssetClass(assetCatClass);
		
		Message msg = new Message();
		if(canupdate) {
			if(whoami.equals(assetCategoryRepository.fetchOne(id).getMaker())) {
				if(assetCategoryRepository.update(assetCategoryObj)) {
					msg.setSuccessMsg("New Asset Category Has Been Updated Successfully!!");
					System.out.print(message);
					}
					else
					{
					msg.setErrorMsg("Error Occured while Updating  Asset Category");
					System.out.print(message);
					}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}	
			canupdate = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		
		//for fetching
		List<AssetClass> assetClass = assetClassRepository.fetchAuthorized();
		List<AssetCategory> listOfAssetCategory = assetCategoryRepository.fetch();
		request.setAttribute("assetClass", assetClass);
		request.setAttribute("assetCategory", listOfAssetCategory);
		request.setAttribute("total", listOfAssetCategory.size());
		//end of fetching
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetCategory.jsp";
		fetchAllAssetCategory(request,response,page);
	}
	public void deleteAssetCat(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("Delete AssetCategory method has been called!");
		String message = "";
		AssetCategoryRepository AssetCategoryRepository = new AssetCategoryRepository();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		Message msg = new Message();
		if(candelete) {
			if(whoami.equals(AssetCategoryRepository.fetchOne(id).getMaker())) {
				if(AssetCategoryRepository.delete(id)) {
					msg.setSuccessMsg("Asset Class Deleted Successfully!");
				}else {
					msg.setErrorMsg("Error Occured while deleting asset class");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		
		List<AssetCategory> listOfAssetCategory = AssetCategoryRepository.fetch();
		List<AssetClass> assetClass = assetClassRepository.fetch();
		
		request.setAttribute("assetClass", assetClass);
		request.setAttribute("msg", msg);
		request.setAttribute("total", listOfAssetCategory.size());
		request.setAttribute("assetCategory", listOfAssetCategory);
		request.setAttribute("onServer", "yes");
		
		String page = "../views/famsAssetCategory.jsp";
		fetchAllAssetCategory(request,response, page);
	}
	
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			request.setAttribute("userRoleLists", userRoleLists);
			RequestDispatcher ds =  request.getRequestDispatcher(page);
			ds.forward(request, response);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
