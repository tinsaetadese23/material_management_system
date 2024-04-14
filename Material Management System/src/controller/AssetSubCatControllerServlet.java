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
import model.AssetSubCategory;
import model.Message;
import model.Roles;
import repository.AssetCategoryRepository;
import repository.AssetClassRepository;
import repository.AssetSubCatRepository;
import repository.AuthorizationRepository;
import repository.RBACRepository;

/**
 * Servlet implementation class AssetSubCatControllerServlet
 */
//@WebServlet("/AssetSubCatControllerServlet")
public class AssetSubCatControllerServlet extends HttpServlet {
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
    public AssetSubCatControllerServlet() {
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
			System.out.println("we are proudly here");
			String page = "views/famsAssetSubCategory.jsp";
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/edit" :
				findOne(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				deleteAssetSubCat(request,response);
				break;	
				default :
				fetchAllAssetSubCat( request,  response,page);		
			}
		}catch(Exception e)
		{
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
				//String action = request.getServletPath();
		try {
			String action = request.getPathInfo();
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			//System.out.println("Action came with " + action);

			switch(action){
			case "/create" :
				createAssetSubCat(request,response);
				break;
			case "/edit" :
				findOne(request,response);
				break;
			case "/update" :
				updateAssetSubCat(request,response);
				break;
			case "/delete" :
				deleteAssetSubCat(request,response);
				break;	
			case "/view" :
				prepareForView(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/search" :
				doSearching(request, response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds  = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request,response);
		}
				
	}
	public void AcessFindeer(HttpServletRequest request, HttpServletResponse response) {
		//
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		for (int i = 0; i < userRoleLists.size(); i++) {
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSSUBCAT")) {
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
		AssetSubCatRepository asubcat = new AssetSubCatRepository();
        String sc = request.getParameter("sr");
        AssetSubCategory assub = new AssetSubCategory(); assub.setSubCatCode(sc);;request.setAttribute("asc", assub);
		List<AssetSubCategory> assetSubCategory = asubcat.fetchBySubCatCode(sc);
		request.setAttribute("searchResult", assetSubCategory);
		if(assetSubCategory.size() == 0) {
			request.setAttribute("none", "none");
		}
		System.out.println("Size of result is"+assetSubCategory.size());
		request.setAttribute("stotal", assetSubCategory.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetSubCategory.jsp";
		fetchAllAssetSubCat(request,response,page);
	}
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_reason = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new AssetSubCatRepository().fetchOne(id).getMaker())) {
				if(ar.authorize("assetSubCat", id,action,whoami,rejection_reason)) {
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
		
		String page = "../views/famsAssetSubCategory.jsp";
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		fetchAllAssetSubCat(request,response,page);
	}
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		AssetSubCatRepository asr = new AssetSubCatRepository();
		AssetSubCategory aSubCat = new AssetSubCategory();
		aSubCat = asr.edit(id);
		request.setAttribute("objprp", aSubCat);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetSubCategory.jsp";
		fetchAllAssetSubCat(request,response,page);
	}
	public  void createAssetSubCat(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("CreateSubCategory has been called!");
		boolean isCreated = false;
		
		String message = "";
		AssetSubCategory assetSubCatObj = new AssetSubCategory();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetSubCatRepository assetSubCatRepository = new AssetSubCatRepository();
		
		String subCatCode = request.getParameter("subCatCode").trim();
		String subCatDesc  = request.getParameter("subCatDesc");
		int subCatCategory = Integer.parseInt(request.getParameter("subCatCategory").toString().trim());
		int mode = Integer.parseInt(request.getParameter("mode").toString());
		
		assetSubCatObj.setSubCatCode(subCatCode);
		assetSubCatObj.setSubCatDesc(subCatDesc);
		assetSubCatObj.setSubCatCategory(subCatCategory);
		assetSubCatObj.setMode(mode);
		assetSubCatObj.setMaker(whoami);
			
		Message msg = new Message();
		if(caninsert) {
			if(!assetSubCatRepository.isSubCatExists(assetSubCatObj)) {
				if(assetSubCatRepository.save(assetSubCatObj)) {
					msg.setSuccessMsg("New Asset Sub-Category Has Been Created Successfully!!");
					System.out.print(message);
					}
					else
					{
				    msg.setSuccessMsg("Error Occured while Creating New Asset Category");	
				    //System.out.print(message);
					}
			}else {
				msg.setErrorMsg("Record Already Exsts");
			}
			caninsert = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		//for fetching
		List<AssetCategory> listOfAssetCategory = assetCategoryRepository.fetch();
		List<AssetSubCategory> listOfAssetSubCat = assetSubCatRepository.fetch();
		
		request.setAttribute("assetSubCat", listOfAssetSubCat);
		request.setAttribute("assetCategory", listOfAssetCategory);
		request.setAttribute("total", listOfAssetCategory.size());
		//end of fetching
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetSubCategory.jsp";
		fetchAllAssetSubCat(request,response,page);
	}
	
	public void fetchAllAssetSubCat(HttpServletRequest request, HttpServletResponse response,String page) {
		System.out.println("fetchAssetSubCategory has been called!");
		boolean isFetched = false;
		
		String message = "";
		AssetSubCatRepository assetSubCatRepository = new AssetSubCatRepository();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		
		List<AssetSubCategory> listOfAssetSubCat = assetSubCatRepository.fetch();
		List<AssetCategory> assetCategory = assetCategoryRepository.fetchByAuth();
		
		request.setAttribute("assetCategory", assetCategory);
		request.setAttribute("total", listOfAssetSubCat.size());
		request.setAttribute("assetSubCat", listOfAssetSubCat);
		System.out.println("we have "+listOfAssetSubCat.size());
		//request.setAttribute("onServer", true);
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		dispatch(request,response,page);
 
	}
	public void deleteAssetSubCat(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("Delete AssetSubCategory method has been called!");
		AssetSubCatRepository assetSubCatRepository = new AssetSubCatRepository();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();

		Message msg = new Message();
		if(candelete) {
			if(whoami.equals(assetSubCatRepository.fetchOne(id).getMaker())) {
				if(assetSubCatRepository.delete(id)) {
					msg.setSuccessMsg("Asset Sub-Category Deleted Successfully!");
				}else {
					msg.setErrorMsg("Error Occure while deleting!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
				
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetSubCategory.jsp";
		fetchAllAssetSubCat(request,response,page);
	}
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		AssetSubCatRepository asr = new AssetSubCatRepository();
		AssetSubCategory aSubCat = new AssetSubCategory();
		aSubCat = asr.edit(id);
		request.setAttribute("editSubCat", aSubCat);
		String page = "../views/famsAssetSubCategory.jsp";
		request.setAttribute("onServer", "yes");
		fetchAllAssetSubCat(request,response,page);
		
	}
	public void updateAssetSubCat(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("Update has  been called!");
		AssetSubCategory assetSubCatObj = new AssetSubCategory();
		AssetCategoryRepository assetCategoryRepository = new AssetCategoryRepository();
		AssetSubCatRepository assetSubCatRepository = new AssetSubCatRepository();
		
		String subCatCode = request.getParameter("subCatCode").trim();
		String subCatDesc  = request.getParameter("subCatDesc");
		int subCatCategory = Integer.parseInt(request.getParameter("subCatCategory").toString().trim());
		int mode = Integer.parseInt(request.getParameter("mode").toString());
		int id = Integer.parseInt(request.getParameter("id").toString());
		
		assetSubCatObj.setSubCatCode(subCatCode);
		assetSubCatObj.setSubCatDesc(subCatDesc);
		assetSubCatObj.setSubCatCategory(subCatCategory);
		assetSubCatObj.setMode(mode);
		assetSubCatObj.setId(id);
			
		Message msg = new Message();
		if(canupdate) {
			if(whoami.equals(assetSubCatRepository.fetchOne(id).getMaker())) {
				if(assetSubCatRepository.update(assetSubCatObj)) {
					msg.setSuccessMsg("Asset Sub Category Has Been Updated");
					//System.out.print(message);
					}
					else
					{
				    msg.setSuccessMsg("Error Occured While Update Asset Sub Category!");	
					}
			}else {
				msg.setErrorMsg("Access Right Unavailble");
			}
			canupdate = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}

		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetSubCategory.jsp";
		fetchAllAssetSubCat(request,response,page);
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			RequestDispatcher ds =  request.getRequestDispatcher(page);
			ds.forward(request, response);
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
