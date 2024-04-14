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
import model.PurchaseOrder;
import model.Roles;
import model.Stock;
import model.Supplier;
import model.Unit;
import repository.AssetCategoryRepository;
import repository.AssetClassRepository;
import repository.AssetSubCatRepository;
import repository.AuthorizationRepository;
import repository.PORepository;
import repository.RBACRepository;
import repository.StockRepository;
import repository.SupplierRepository;
import repository.UnitRepository;

/**
 * Servlet implementation class PurchaseOrderControllerServlet
 */
@WebServlet("/PurchaseOrderControllerServlet")
public class PurchaseOrderControllerServlet extends HttpServlet {
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
    public PurchaseOrderControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//System.out.println("we are proudly here");
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			page = "views/famsPO.jsp";
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				deletePo(request,response);
				break;
			case "/edit" :
				findOne1(request,response);
				break;
			default :
				fetchAll(request,response,page);
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
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			String path = request.getPathInfo();
			switch(path) {
			case "/create" :
				createPO(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/recieving" :
				recievingBulk(request,response);
				break;
			case "/edit" :
				findOne1(request,response);
				break;
			case "/update" :
				updatePo(request, response);
					break;
			case "/delete" :
				deletePo(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/search" :
				//System.out.println("Thank god we are here!!");
				searchByPoCode(request,response);
				break;
			case "/srch" :
				doSearching(request,response);
				break;
			case "/fltr":
				subCategoryFetcher(request, response,"../views/famsPO.jsp");
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
		
	}
	public void AcessFindeer(HttpServletRequest request, HttpServletResponse response) {
		//
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		for (int i = 0; i < userRoleLists.size(); i++) {
			if(userRoleLists.get(i).getPageIdNameHolder().equals("PO")) {
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
		PORepository por = new PORepository();
        String sc = request.getParameter("sr");
        PurchaseOrder po = new PurchaseOrder(); po.setPoCode(sc);request.setAttribute("asc", po);
		List<PurchaseOrder> purchasOrder = por.fetchByPoCodeS(sc);
		request.setAttribute("searchResult", purchasOrder);
		if(purchasOrder.size() == 0) {
			request.setAttribute("none", "none");
		}
		System.out.println("Size of result is"+purchasOrder.size());
		request.setAttribute("stotal", purchasOrder.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsPO.jsp";
		fetchAll(request,response,page);
	}
	
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		System.out.println("The purchase order id is "+id);
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_reason = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new PORepository().fetchOne(id).getMaker())) {
				if(ar.authorize("purchaseOrders",id,action,whoami,rejection_reason)) {
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
		
		String page = "../views/famsPO.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAll(request,response,page);
	}
	
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		PORepository por = new PORepository();
		PurchaseOrder poObj = por.fetchOne(id);
		SupplierRepository sr = new SupplierRepository();
		Supplier supp = sr.fetchOne(poObj.getSupplier());
		UnitRepository ur = new UnitRepository();
		AssetSubCatRepository asr = new AssetSubCatRepository();
		AssetSubCategory asro = asr.fetchOne(poObj.getSubCategory());
		request.setAttribute("asro", asro);
		Unit unit = ur.edit(poObj.getUnitMeasurement());
		request.setAttribute("unit", unit);
		request.setAttribute("supp", supp);
		//request.setAttribute("poObj", poObj);
		request.setAttribute("onServer", "yes");
		request.setAttribute("objprp", poObj);
		page = "../views/famsPO.jsp";
		fetchAll(request,response,page);
	}
	public void searchByPoCode(HttpServletRequest request, HttpServletResponse response) {
		String poCode = request.getParameter("pocode");
		PORepository por = new PORepository();
		PurchaseOrder poObj = new PurchaseOrder();
		//poObj =	por.fetchByPoCode(poCode);
		SupplierRepository sr;
		AssetSubCatRepository asr;
		Supplier supp = new Supplier();
		AssetSubCategory asubcat = new AssetSubCategory();
		if(poObj == null){
			request.setAttribute("none","none");
		}else {
			sr = new SupplierRepository();
			asr = new AssetSubCatRepository();
			supp = sr.fetchOne(poObj.getSupplier());
			asubcat = asr.fetchOne(poObj.getSubCategory());
		}
		UnitRepository ur = new UnitRepository();
		Unit unit = ur.edit(poObj.getUnitMeasurement());
		request.setAttribute("unit", unit);
		request.setAttribute("supp", supp);
		request.setAttribute("poObj", poObj);
		request.setAttribute("asubcat", asubcat);
		//request.setAttribute("objprp", poObj);
		request.setAttribute("onServer", "yes");
		//System.out.println(poObj.getAuthStatus());
		page = "../views/famsStock.jsp";
		//fetchAll(request,response,page);
		new StockControllerServlet().fetchAll(request, response, page);
	}
	public void findOne1(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		PORepository por = new PORepository();
		PurchaseOrder poObj = por.fetchOne(id);
		AssetSubCatRepository asr = new AssetSubCatRepository();
		AssetSubCategory asc = asr.fetchOne(poObj.getSubCategory());
		request.setAttribute("poSub", asc);
		request.setAttribute("editPo", poObj);
		request.setAttribute("onServer", "yes");
		page = "../views/famsPO.jsp";
		fetchAll(request,response,page);
	}
	public void recievingBulk(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		PORepository por = new PORepository();
		PurchaseOrder poObj = por.fetchOne(id);
		AssetSubCatRepository asr = new AssetSubCatRepository();
		AssetSubCategory subCatDetail = asr.fetchOne(poObj.getSubCategory());
		System.out.println("Debugg "+subCatDetail.getSubCatCode());
		request.setAttribute("subCatDetail", subCatDetail);
		request.setAttribute("poObjRec", poObj);
		request.setAttribute("onServer", "yes");
		page = "../views/famsStock.jsp";
		new StockControllerServlet().fetchAll(request, response, page);
		//System.out.println("purchae type is"+poObj.getPurchaseTypes());
	}

	public void createPO(HttpServletRequest request, HttpServletResponse response) {
		PurchaseOrder po = new PurchaseOrder();
		po.setPoCode(request.getParameter("orderId"));
		po.setDescription(request.getParameter("remark"));
		po.setPurchaseTypes(request.getParameter("purchaseType"));
		po.setSubCategory(Integer.parseInt(request.getParameter("subCategory").toString()));
		po.setSupplier(Integer.parseInt(request.getParameter("supplier").toString()));
		po.setOrderDate(request.getParameter("purchaseDate"));
		po.setDeliveryDate(request.getParameter("DeliveryDate"));
		po.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		po.setUnitPrice(Float.parseFloat(request.getParameter("totalPrice")) / Integer.parseInt(request.getParameter("quantity")));
		po.setUnitMeasurement(Integer.parseInt(request.getParameter("unitMeasurement").toString()));
		po.setTotal(0);
		po.setPurchaeOrderstatus("APPROVED");
		po.setMaker(whoami);
		
		PORepository por = new PORepository();
		Message msg = new Message();
		if(caninsert) {
			if(!por.isPoExist(po.getPoCode(), po.getSubCategory())) {
				if(por.save(po)) {
					msg.setSuccessMsg("Record Saved Successfully");
				}else {
					msg.setErrorMsg("Error while saving data");
				}
			}else {
				msg.setErrorMsg("Purchase Order with current sub-category already exists");
			}
			caninsert = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		//System.out.println("the message is "+msg.getSuccessMsg());
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		page = "../views/famsPO.jsp";
		fetchAll(request,response,page);
	}
	public void updatePo(HttpServletRequest request, HttpServletResponse response) {
		PurchaseOrder po = new PurchaseOrder();
		po.setId(Integer.parseInt(request.getParameter("id").toString()));
		po.setPoCode(request.getParameter("orderId"));
		po.setDescription(request.getParameter("remark"));
		po.setPurchaseTypes(request.getParameter("purchaseType"));
		po.setSubCategory(Integer.parseInt(request.getParameter("subCategory").toString()));
		po.setSupplier(Integer.parseInt(request.getParameter("supplier").toString()));
		po.setOrderDate(request.getParameter("purchaseDate"));
		po.setDeliveryDate(request.getParameter("DeliveryDate"));
		po.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		po.setUnitPrice(Float.parseFloat(request.getParameter("totalPrice")) / Integer.parseInt(request.getParameter("quantity")));
		po.setUnitMeasurement(Integer.parseInt(request.getParameter("unitMeasurement").toString()));
		po.setInsepectedBy(request.getParameter("inspectedBy"));
		po.setCheckedBy(request.getParameter("checkedBy"));
		//po.setTotal(0);
		po.setPurchaeOrderstatus("APPROVED");
		po.setMaker(whoami);
		
		PORepository por = new PORepository();
		Message msg = new Message();
		
		if(canupdate) {
			if(whoami.equals(por.fetchOne(po.getId()).getMaker())) {
				if(por.update(po)) {
					msg.setSuccessMsg("Record Update Successfully");
				}else {
					msg.setErrorMsg("Error while updating  Record");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canupdate = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		
		//System.out.println("the message is "+msg.getSuccessMsg());
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		page = "../views/famsPO.jsp";
		fetchAll(request,response,page);
	}
	
	public void fetchAll(HttpServletRequest request, HttpServletResponse response,String page) {
		AssetSubCatRepository aSubCatRepo = new AssetSubCatRepository();
		AssetClassRepository aClassRepo = new AssetClassRepository();
		AssetCategoryRepository aCatRepo = new AssetCategoryRepository();
		UnitRepository uRepo = new UnitRepository();
		//commenting this line
		List<AssetClass> aClassList = aClassRepo.fetchByAuth();
		List<AssetCategory> aCatList = aCatRepo.fetchByClassId(aClassList.get(0).getId());
		List<AssetSubCategory> aSubCatList = aSubCatRepo.fetchByCatId(aCatList.get(0).getId());
		List<Unit> unitList = uRepo.fetchByAuth();
		SupplierRepository sr = new SupplierRepository();
		List<Supplier> supplierList = sr.fetchSuppByAuth();
		PORepository por = new PORepository();
		List<PurchaseOrder> poList = por.fetch();
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		request.setAttribute("supplierList", supplierList);
		request.setAttribute("aSubCatList", aSubCatList);
		request.setAttribute("unitList", unitList);
		request.setAttribute("poList", poList);
		//request.setAttribute("onServer", true);
		//added to handle purchase order search performance
		request.setAttribute("aClassList", aClassList);
		request.setAttribute("aCatList", aCatList);
		//end
		request.setAttribute("total",poList.size());
		dispatch(request,response,page);
		
	}
	public void subCategoryFetcher(HttpServletRequest request, HttpServletResponse response, String page) {
		System.out.println("We are filtering subcategory");
		int classId = Integer.parseInt(request.getParameter("class").toString());
		int catId = Integer.parseInt(request.getParameter("category").toString());
		int subCatId = Integer.parseInt(request.getParameter("subCategory").toString());
		
		AssetClass aClass = new AssetClass();
		aClass.setId(classId);
		AssetCategory aCategory =  new AssetCategory();
		aCategory.setId(catId);
		AssetSubCategory aSubCategory = new AssetSubCategory();
		
		AssetSubCatRepository aSubCatRepo = new AssetSubCatRepository();
		AssetClassRepository aClassRepo = new AssetClassRepository();
		AssetCategoryRepository aCatRepo = new AssetCategoryRepository();
		UnitRepository uRepo = new UnitRepository();
		
		aSubCategory.setId(subCatId);
		aSubCategory.setSubCatCode(aSubCatRepo.fetchOne(subCatId).getSubCatCode()+"-");
		aSubCategory.setSubCatDesc(aSubCatRepo.fetchOne(subCatId).getSubCatDesc());
		//commenting this line
		//filtered lists
		List<AssetClass> fClassList = aClassRepo.fetchByAuth();
		List<AssetCategory> fCatList = aCatRepo.fetchByClassId(classId);
		List<AssetSubCategory> fSubCatList = aSubCatRepo.fetchByCatId(catId);
		
		List<Unit> unitList = uRepo.fetchByAuth();
		SupplierRepository sr = new SupplierRepository();
		List<Supplier> supplierList = sr.fetchSuppByAuth();
		PORepository por = new PORepository();
		List<PurchaseOrder> poList = por.fetch();
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		request.setAttribute("supplierList", supplierList);
		request.setAttribute("unitList", unitList);
		request.setAttribute("poList", poList);
		if(request.getParameter("subCatIsSelected") == null) {
			request.setAttribute("filter", "filter");	
		}
		request.setAttribute("selectedClass", classId);
		request.setAttribute("sClass", aClass);
		request.setAttribute("sCategory",aCategory);
		//request.setAttribute("onServer", true);
		//added to handle purchase order search performance
		request.setAttribute("fClassList", fClassList);
		request.setAttribute("fCatList", fCatList);
		request.setAttribute("fSubCatList", fSubCatList);
		request.setAttribute("sSubCat", aSubCategory);
		//end
		request.setAttribute("total",poList.size());
		dispatch(request,response,page);
		
	}
	public void deletePo(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		Message msg = new Message();
		PORepository por = new PORepository();
		if(candelete) {
			if(whoami.equals(por.fetchOne(id).getMaker())) {
				if(por.delete(id)) {
					msg.setSuccessMsg("Record Deleted Successfully");
				}else {
					msg.setErrorMsg("Error Occured while deleting the record");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavaialable");
		}
		
		
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		page = "../views/famsPO.jsp";
		fetchAll(request,response,page);
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			request.setAttribute("onServer","true");
			ds.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
