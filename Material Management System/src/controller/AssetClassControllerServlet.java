package controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import model.AssetClass;
import model.Message;
import model.Roles;
import repository.AssetClassRepository;
import repository.AuthorizationRepository;
import repository.RBACRepository;

/*@WebServlet("/ACCS")*/
public class AssetClassControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String whoami;
	private int uid;
	private boolean canupdate;
    private boolean candelete;
    private boolean canauth;
    private boolean caninsert;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetClassControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
      //  System.out.println("I dont know when the call me");
    }
    
     

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		 canupdate = false;
	     candelete = false;
	     canauth = false;
	     caninsert = false;  

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String action = request.getPathInfo();
		//System.out.println("the path is " +action);
		try {
			String page = "views/famsAssetClass.jsp";
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			System.out.println("My User id is"+uid);
			List<Roles> userRoleLists = new ArrayList<>();
			RBACRepository rbacRepository = new RBACRepository();
			userRoleLists = rbacRepository.fetchAccessForUser(uid);
			for (int i = 0; i < userRoleLists.size(); i++) {
				if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSCLA")) {
					System.out.println("The "+i+ "Index Record values on create is "+userRoleLists.get(i).getCreate());
					System.out.println("can i create asset "+caninsert);
					if(userRoleLists.get(i).getCreate() == 1) {
						System.out.println("So i can't get through this If");
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
			
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/edit" :
				findOne(request,response);
				break;
			case "/delete" :
				deleteAssetClass(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			default :
				fetchAllAssetClass( request,  response,page);
			}
		}catch(Exception e) {
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
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
		HttpSession mySession = request.getSession();
		whoami = (String)mySession.getAttribute("username");
		String action = request.getPathInfo();
		System.out.println("My user id is " + uid);
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		for (int i = 0; i < userRoleLists.size(); i++) {
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSCLA")) {
				System.out.println("The "+i+ "Index Record values on create is "+userRoleLists.get(i).getCreate());
				System.out.println("can i create asset "+caninsert);
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
		switch(action) {
		case "/create" :
			createAssetClass(request,response);
			break;
		case "/edit" :
			findOne(request,response);
			break;
		case "/update" :
			updateAssetClass(request,response);
			break;
		case "/delete" :
			deleteAssetClass(request,response);
			break;
		case "/auth" :
			athorize(request,response);
			break;
		case "/view" :
			prepareForView(request,response);
			break;
		case "/search" :
			System.out.println("welcome" +request.getParameter("sr"));
			doSearching(request,response);
			break;
			
			
		}
	}catch(Exception e) {
		e.printStackTrace();
		RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
		ds.forward(request,response);
	}
	

	}
	public void doSearching(HttpServletRequest request,HttpServletResponse response) {
		AssetClassRepository asr = new AssetClassRepository();
        String sc = request.getParameter("sr");
        AssetClass asc = new AssetClass(); asc.setAssetClassCode(sc);request.setAttribute("asc", asc);
		List<AssetClass> assetClass = asr.fetchByClassCode(sc);
		request.setAttribute("searchResult", assetClass);
		if(assetClass.size() == 0) {
			request.setAttribute("none", "none");
		}
		System.out.println("Size of result is"+assetClass.size());
		request.setAttribute("stotal", assetClass.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetClass.jsp";
		fetchAllAssetClass(request,response,page);
	}
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("findOne AssetClass method has been called!");
		
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		AssetClass assetClassObj = assetClassRepository.fetchOne(id);
		
		List<AssetClass> listOfAssetClass = assetClassRepository.fetch();
		
		request.setAttribute("objprp", assetClassObj);
		request.setAttribute("total", listOfAssetClass.size());
		request.setAttribute("assetClass", listOfAssetClass);
		request.setAttribute("onServer", "yes");
		System.out.println(assetClassObj.getAssetClassCode());	
		String page = "../views/famsAssetClass.jsp";
		dispatch(request,response, page); 
	}
	
	public void athorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_comment = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new AssetClassRepository().fetchOne(id).getMaker())) {
				if(ar.authorize("assetClass", id,action,whoami,rejection_comment)) {
					//msg.setSuccessMsg("Record Authorized Successfully");
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Reject Successfully!");
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
		
		String page = "../views/famsAssetClass.jsp";
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		fetchAllAssetClass(request,response,page);
	}
	public  void createAssetClass(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("createAssetClass has been called!");
		boolean isCreated = false;
		
		String message = "";
		AssetClass assetClassObj = new AssetClass();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		String assetClass = request.getParameter("assetClass").trim();
		String assetDescription  = request.getParameter("assetDescription");
		
		assetClassObj.setAssetClassCode(assetClass); 
		assetClassObj.setAssetClassDesc(assetDescription);
		assetClassObj.setMaker(whoami);
		Message msg = new Message();
		
		if(caninsert) {
			if(!assetClassRepository.isAccestExists(assetClassObj)) {
				if(assetClassRepository.save(assetClassObj)) {
					msg.setSuccessMsg("New Asset Class Has Been Created Successfully!!");
					System.out.print(message);
					}
					else
					{
					msg.setErrorMsg("Error Occured while Creating New Asset Class");
					System.out.print(message);
					}
			}else {
				msg.setErrorMsg("Record Already Exists");
			}
			caninsert = false;
		}else {
			msg.setErrorMsg("Access Right Unavialable");
		}
		
		//for fetching
		List<AssetClass> listOfAssetClass = assetClassRepository.fetch();
		request.setAttribute("assetClass", listOfAssetClass);
		request.setAttribute("total", listOfAssetClass.size());
		//end of fetching
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetClass.jsp";
		fetchAllAssetClass(request,response,page);
	}
	
	public void fetchAllAssetClass(HttpServletRequest request, HttpServletResponse response,String page) {
		System.out.println("fetchAllAssetClassMethod has been called!");
		boolean isFetched = false;
		
		String message = "";
		AssetClass assetClassObj = new AssetClass();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		List<AssetClass> listOfAssetClass = assetClassRepository.fetch();
		
		request.setAttribute("total", listOfAssetClass.size());
		request.setAttribute("assetClass", listOfAssetClass);
		//request.setAttribute("onServer", true);
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		dispatch(request,response,page);
 
	}
	
	
	public void findOne(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("findOne AssetClass method has been called!");
		
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		AssetClass assetClassObj = assetClassRepository.fetchOne(id);
		
		List<AssetClass> listOfAssetClass = assetClassRepository.fetch();
		
		request.setAttribute("oneAssetClass", assetClassObj);
		request.setAttribute("total", listOfAssetClass.size());
		request.setAttribute("assetClass", listOfAssetClass);
		request.setAttribute("onServer", "yes");
		System.out.println(assetClassObj.getAssetClassCode());	
		String page = "../views/famsAssetClass.jsp";
		dispatch(request,response, page);
	}
	
	public void updateAssetClass(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Update Asset Class has been called!");
		//boolean isCreated = false;
		
		String message = "";
		AssetClass assetClassObj = new AssetClass();
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		String assetClass = request.getParameter("assetClass").trim();
		String assetDescription  = request.getParameter("assetDescription");
		int id = Integer.parseInt(request.getParameter("id").toString().trim());
		
		assetClassObj.setAssetClassCode(assetClass); 
		assetClassObj.setAssetClassDesc(assetDescription);
		assetClassObj.setId(id);
		assetClassObj.setMaker(whoami);
		
		Message msg = new Message();
		if(canupdate) {
			if(whoami.equals(assetClassRepository.fetchOne(id).getMaker())) {
				if(assetClassRepository.update(assetClassObj)) {
					msg.setSuccessMsg("Asset Class Has Been updated Successfully!!");
					System.out.print(message);
					}
					else
					{
					msg.setErrorMsg("Error Occured while Updating  Asset Class");
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
		List<AssetClass> listOfAssetClass = assetClassRepository.fetch();
		request.setAttribute("assetClass", listOfAssetClass);
		request.setAttribute("total", listOfAssetClass.size());
		//end of fetching
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetClass.jsp";
		fetchAllAssetClass(request,response,page);
	}
	public void deleteAssetClass(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("Delete AssetClass method has been called!");
		String message = "";
		AssetClassRepository assetClassRepository = new AssetClassRepository();
		
		Message msg = new Message();
		if(candelete) {
			if(whoami.equals(assetClassRepository.fetchOne(id).getMaker())) {
				if(assetClassRepository.delete(id)) {
					msg.setSuccessMsg("Asset Class Deleted Successfully!");
				}else {
					msg.setSuccessMsg("Error Occured while deleting asset class");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		List<AssetClass> listOfAssetClass = assetClassRepository.fetch();
		
		request.setAttribute("msg", msg);
		request.setAttribute("total", listOfAssetClass.size());
		request.setAttribute("assetClass", listOfAssetClass);
		request.setAttribute("onServer", "yes");
		
		
		String page = "../views/famsAssetClass.jsp";
		dispatch(request,response, page);
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
