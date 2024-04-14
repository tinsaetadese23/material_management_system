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
import model.CheckBook;
import model.Furniture;
import model.ITEquip;
import model.LandandBuilding;
import model.Message;
import model.PurchaseOrder;
import model.Roles;
import model.Stock;
import model.Supplier;
import model.Unit;
import model.Vehicle;
import repository.AssetCategoryRepository;
import repository.AssetSubCatRepository;
import repository.AuthorizationRepository;
import repository.PORepository;
import repository.RBACRepository;
import repository.ReportingRepositoy;
import repository.StockRepository;
import repository.SupplierRepository;
import repository.UnitRepository;

/**
 * Servlet implementation class StockControllerServlet
 */
//@WebServlet("/StockControllerServlet")
public class StockControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String page = "";
    private String whoami;   
    private int uid;
    private boolean canupdate = false;
    private boolean candelete = false;
    private boolean canauth = false;
    private boolean caninsert = false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockControllerServlet() {
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
			page = "views/famsStock.jsp";
			String path = request.getPathInfo();
			if(path == null) {
				path = "default";
			}
			switch(path) {
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				deleteStock(request,response);
				break;
			case "/src" :
				fetchSubUnderPo(request,response);
				break;
			default :
				fetchAll(request,response,page);
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
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			AcessFindeer(request,response);
			String path = request.getPathInfo();
			System.out.println("the path is "+path);
			switch(path) {
			case "/create" :
				createStock(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/delete" :
				deleteStock(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/search":
				doSearching(request,response);
				break;
			case "/recieving" :
				recievingBulk(request,response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("onServer","yes");
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
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSBULK")) {
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
		request.setAttribute("userRoleLists", userRoleLists);
	}
	public void doSearching(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("Searching has started...");
		StockRepository ssr = new StockRepository();
        String sc = request.getParameter("sr");
        Stock stock = new Stock(); stock.setStatus(sc);request.setAttribute("asc", stock);
		List<Stock> stocks = ssr.fetchByRecCode(sc);
		request.setAttribute("searchResult", stocks);
		if(stocks.size() == 0) {
			request.setAttribute("nones", "none");
		}
		System.out.println("Size of result is"+stocks.size());
		request.setAttribute("stotal", stocks.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsStock.jsp";
		fetchAll(request,response,page);
	}
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		String rc = request.getParameter("rc");
		System.out.println("The Stock id is "+rc);
		int action = Integer.parseInt(request.getParameter("action"));
		String rejection_comment = request.getParameter("rejection-comment");
		AuthorizationRepository ar = new AuthorizationRepository();
		System.out.println("My uid is"+uid);
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new StockRepository().fetchByReceivingCode(rc).getMaker())) {
				if(ar.authorizeStock("famsStock",rc,action,"AR",whoami,rejection_comment)) {
					msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
				}else {
					msg.setErrorMsg("Error occured while Authorizing!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			canauth = false;
		}else {
			msg.setErrorMsg("Access Right Unavailbale");
		}
		String page = "../views/famsStock.jsp";
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg", msg);
		fetchAll(request,response,page);
	}
	public void deleteStock(HttpServletRequest request, HttpServletResponse response) {
		//int id = Integer.parseInt(request.getParameter("id").toString());
		String rc = request.getParameter("rc");
		StockRepository sr = new StockRepository();
		Message msg = new Message();
		if(candelete) {
			if(whoami.equals(sr.fetchByReceivingCode(rc).getMaker())) {
				if(sr.delete(rc)) {
					msg.setSuccessMsg("Record Deleted Successfully");
				}else {
					msg.setErrorMsg("Error Occured while deleting the record!");
				}
			}else {
				msg.setErrorMsg("Access Right Unavailbale");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		page = "../views/famsStock.jsp";
		fetchAll(request,response,page);
	}
	public void fetchAll(HttpServletRequest request, HttpServletResponse response,String page) {
		UnitRepository uRepo = new UnitRepository();
		List<Unit> unitList = uRepo.fetch();
		StockRepository sr = new StockRepository();
		List<Stock> stockList = sr.fetch();
		//fetching roles
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		//request.setAttribute("onServer", true);
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		request.setAttribute("stockList", stockList);
		request.setAttribute("unitList", unitList);
		request.setAttribute("total", stockList.size());
		//new ReportingServlet().loadPoandSub(request, response, page);
		loadPoandSub(request,response);
		dispatch(request,response,page);
		
	}
	public void loadPoandSub(HttpServletRequest request, HttpServletResponse response) {
		List<AssetCategory> ac = new AssetCategoryRepository().fetchForReport();
		request.setAttribute("assetCategory",ac );
		ReportingRepositoy rr = new ReportingRepositoy();
		List<PurchaseOrder> polist = rr.fetchpoForRec();
		request.setAttribute("polist", polist);
		if(polist.size() > 0) {
			List<AssetSubCategory> subcats = rr.fetchSub2(polist.get(0).getPoCode());
			request.setAttribute("subcats", subcats);
		}
		System.out.println("size of category" +ac.size());
		//dispatcher(request,response,page);
		//new LoginControllerServlet().fetchUserRoles(request,response,page);
	}
	public void recievingBulk(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		PORepository por = new PORepository();
		PurchaseOrder poObj = por.fetchOne(id);
		AssetSubCatRepository asr = new AssetSubCatRepository();
		AssetSubCategory subCatDetail = asr.fetchOne(poObj.getSubCategory());
		Unit unitDetail = new UnitRepository().edit(poObj.getUnitMeasurement());
		Supplier suppDetail = new SupplierRepository().fetchOne(poObj.getSupplier());
		subCatDetail.setTmpHolder(new StockRepository().generateReceivingCode(subCatDetail.getId(),subCatDetail.getSubCatCode(), poObj.getPoCode()));
		System.out.println("Debugg "+subCatDetail.getSubCatCode());
		request.setAttribute("unitDetail", unitDetail);
		request.setAttribute("suppDetail", suppDetail);
		request.setAttribute("subCatDetail", subCatDetail);
		request.setAttribute("poObjRec", poObj);
		request.setAttribute("onServer", "yes");
		page = "../views/famsStock.jsp";
		fetchAll(request, response, page);
		//System.out.println("purchae type is"+poObj.getPurchaseTypes());
	}
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String page) {
		try {
			RequestDispatcher ds = request.getRequestDispatcher(page);
			ds.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void createStock(HttpServletRequest request, HttpServletResponse response) {
		Message msg = new Message();
		Stock stock = new Stock();
		ITEquip it = new ITEquip();
		Vehicle vehicle = new Vehicle();
		LandandBuilding lb = new LandandBuilding();
		Furniture fr = new Furniture();
		CheckBook cb = new CheckBook();
		String ast = "";
		it.setItem_Desc(request.getParameter("itemDesc"));
		it.setModel(request.getParameter("model"));
		it.setMemory(request.getParameter("memo"));
		it.setStorage(request.getParameter("storage"));
		vehicle.setItem(request.getParameter("itemDescve"));
		vehicle.setModel(request.getParameter("modelve"));
		vehicle.setNoOfWheerls(request.getParameter("wheels"));
		vehicle.setEngineType(request.getParameter("engine"));
		vehicle.setNoOfSeat(request.getParameter("seats"));
		vehicle.setEnginCapacity(request.getParameter("capacity"));
		lb.setLbArea(request.getParameter("area"));
		lb.setLbLatitude(request.getParameter("latitude"));
		lb.setLbLongitude(request.getParameter("longitude"));
		lb.setItemDesc(request.getParameter("width"));
		cb.setFno(request.getParameter("fno"));
		cb.setLno(request.getParameter("lno"));
		fr.setItemDes(request.getParameter("itemDescve"));
		fr.setHeight(request.getParameter("height"));
		fr.setWidth(request.getParameter("width"));
		
		stock.setInspectedBy(request.getParameter("inspectedBy"));
		stock.setCheckedBy(request.getParameter("checkedBy"));
		stock.setReieptNo(request.getParameter("recieptNo"));
		//fr.setHeight(request.getParameter("width"));
		//fr
		
		if(request.getParameter("veh") != null) {
			
			System.out.println("Registering vehicle");
			stock.setItem(vehicle.getItem());
			stock.setModel(vehicle.getModel());
			 ast =  "Item : ["+vehicle.getItem()+"] model : ["+vehicle.getModel()+"] Engine : ["+vehicle.getEngineType()+"] Capacity : ["+vehicle.getEnginCapacity()+"] No of wheels : ["+vehicle.getNoOfWheerls()+"] No of Seats : ["+vehicle.getNoOfSeat()+"]";
		}else if(request.getParameter("furn") != null) {
			ast = "Item : ["+fr.getItemDes()+"] Width : ["+fr.getWidth()+"] Height : ["+fr.getHeight()+"]";
		}else if(request.getParameter("lanb") != null) {
			ast = "Item : ["+lb.getItemDesc()+"] Area : ["+lb.getLbArea()+"] Latitude : ["+lb.getLbLatitude()+"] Longitude : ["+lb.getLbLongitude()+"]";
		}else if(request.getParameter("chqb") != null) {
			ast = "First No. : ["+cb.getFno()+"] Last No. : ["+cb.getLno()+"]";
		}else {
			stock.setItem(request.getParameter("itemDesc"));
			stock.setModel(request.getParameter("model"));
			System.out.println("It Equip vehicle");
			 ast =  "Item : ["+it.getItem_Desc()+"] model : ["+it.getModel()+"] Memory : ["+it.getMemory()+"] Storage : ["+it.getStorage()+"]";
		}
		if(new AssetSubCatRepository().fetchOne(Integer.parseInt(request.getParameter("subcat"))).getMode() == 1 || new AssetSubCatRepository().fetchOne(Integer.parseInt(request.getParameter("subcat"))).getMode() == 4 || new AssetSubCatRepository().fetchOne(Integer.parseInt(request.getParameter("subcat"))).getMode() == 3){
			stock.setRecievingCode(request.getParameter("receivingCode"));
			stock.setSubCategoryCode(Integer.parseInt(request.getParameter("subcat")));
			stock.setUnitMeasurement(Integer.parseInt(request.getParameter("unitMeasurement")));
			stock.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice")));
			int qty = Integer.parseInt(request.getParameter("qty").toString());
			int rqty = Integer.parseInt(request.getParameter("rqty").toString());
			stock.setQuantity(rqty);
			stock.setPurchaseId(Integer.parseInt(request.getParameter("id")));
			stock.setAssetSpec(ast);
			stock.setItem(request.getParameter("itemDesc"));
			stock.setPurchaseDate(request.getParameter("rdate"));
			//System.out.println("Purchase Order Number is"+stock.getPurchaseId());
		//	stock.setPurchaseDate(request.getParameter("purchaseDate"));
			stock.setSupplierId(Integer.parseInt(request.getParameter("supplier")));
			stock.setSerialNo("NONSERIAL");
			if(rqty <= qty) {
			StockRepository sr = new StockRepository();
			if(caninsert) {
				if(sr.save(stock,whoami)) {
					msg.setSuccessMsg("Records Saved Successfully");
				}else {
					msg.setSuccessMsg("Error Occured!");
				}
				caninsert = false;
			}else {
				msg.setErrorMsg("Access Right Unavailable");
			}
			}else {
				msg.setErrorMsg("Receiving quantity cannot be greater than PO quantity!");
			}
			//System.out.println("Just Thank you for coming");
		}else{
		int rqty = Integer.parseInt(request.getParameter("rqty"));
		int pqty = Integer.parseInt(request.getParameter("qty"));
		if(rqty > pqty) {
			msg.setErrorMsg("Receiving quantity should not exceed the purchase order amount");
		}else {
			String serials[] = request.getParameterValues("serial");
			System.out.println("the size of serials is "+serials.length);
			int unregCounter = 0;
			
			//checking for the repeated serials 
			for(int i = 0; i<serials.length; i++) {
				stock.setSubCategoryCode(Integer.parseInt(request.getParameter("subcat")));
				stock.setSerialNo(serials[i]);
				if(new StockRepository().isSerialRepeated(stock.getSerialNo(), stock.getSubCategoryCode())) {
					serials[0] = stock.getSerialNo();
				}
			}		
			for(int i = 0; i< serials.length; i++) {
				stock.setRecievingCode(request.getParameter("receivingCode"));
				stock.setSubCategoryCode(Integer.parseInt(request.getParameter("subcat")));
				stock.setUnitMeasurement(Integer.parseInt(request.getParameter("unitMeasurement")));
				stock.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice")));
				stock.setPurchaseDate(request.getParameter("rdate"));
				int qty = Integer.parseInt(request.getParameter("qty").toString());
				stock.setQuantity(qty/qty);
				stock.setPurchaseId(Integer.parseInt(request.getParameter("id")));
			//	stock.setPurchaseDate(request.getParameter("purchaseDate"));
				stock.setSupplierId(Integer.parseInt(request.getParameter("supplier")));
				stock.setSerialNo(serials[i]);
				stock.setAssetSpec(ast);
				
				if(new StockRepository().containsDuplicates(serials) != "-1"){
					msg.setErrorMsg("Serial Repeated Under Same Sub-Categrory at: serialNo "+ new StockRepository().containsDuplicates(serials));
					break;
				}
				if(new StockRepository().isSerialRepeated(stock.getSerialNo(), stock.getSubCategoryCode())) {
						msg.setErrorMsg("Serial Exists Under Same Sub-Category at: serialNo "+stock.getSerialNo());
						break;
				}else {
					if(!stock.getSerialNo().equals("")) {
						if(caninsert) {
							StockRepository sr = new StockRepository();
							if(sr.save(stock,whoami)) {
								msg.setSuccessMsg("Records Saved Successfully");
							}else {
								msg.setErrorMsg("Saving Record Failed at"+serials[i]);
							}
						}else {
							msg.setErrorMsg("Access Right Unavailable");
						}
						}else {
							++unregCounter;
							continue;
							}
				}

				
				System.out.println("BulkRecievingCode :"+msg.getSuccessMsg());
			//	System.out.println("SerialNo :"+stock.getSerialNo());
				
			}
			caninsert = false;
			if(unregCounter > 0) {
				if(unregCounter == serials.length) {
				msg.setErrorMsg("Minimum one serial must be registered!");	
				}
				msg.setInfo("Serial for "+unregCounter+ " items not registered");
			}	
		}
	}
		System.out.println("The message from success :"+msg.getSuccessMsg());
		System.out.println("The message from Error :"+msg.getErrorMsg());
		request.setAttribute("onServer", "yes");
		page = "../views/famsStock.jsp";
		request.setAttribute("msg", msg);
		fetchAll(request,response,page);
		
		
	}
	
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		String rc = request.getParameter("rc");
		StockRepository sr = new StockRepository();
		Stock objprp = sr.fetchByReceivingCode(rc);
		request.setAttribute("objprp", objprp);
		request.setAttribute("ftest", new AssetSubCatRepository().fetchOne(objprp.getSubCategoryCode()));
		request.setAttribute("onServer", "yes");
		page = "../views/famsStock.jsp";	
		fetchAll(request,response,page);

	}
	public void fetchSubUnderPo(HttpServletRequest request,HttpServletResponse response) {
		ReportingRepositoy rs = new ReportingRepositoy();
		if(request.getParameter("su") != null) {
			System.out.println("am on if");
			//int id = Integer.parseInt(request.getParameter("pos"));
			//int sub = Integer.parseInt(request.getParameter("subcat"));
			String poCode = request.getParameter("pos");
			//PurchaseOrder poObjjj = new PORepository().fetchOne(id);
			PurchaseOrder poObjjj = new PurchaseOrder();poObjjj.setPoCode(poCode);
			request.setAttribute("poObjjj", poObjjj);
			List<AssetSubCategory> ssubcats = rs.fetchSub2(poCode);
			request.setAttribute("ssubcats", ssubcats);
			request.setAttribute("srq", "srq");
			//loadAssetCategory(request,response,"../views/goodreceiving.jsp");
			request.setAttribute("onServer", "yes");
			fetchAll(request, response, "../views/famsStock.jsp");
			
		}else {
			System.out.println("am off if");
			/*int id = Integer.parseInt(request.getParameter("pos"));
			int sub = Integer.parseInt(request.getParameter("subcat"));
			
			List<Stock> stocklist = rs.GRVReport(id, sub);
			request.setAttribute("stocklist", stocklist);
			System.out.println("size of stock is "+stocklist.size());
			if(stocklist.size() != 0) {
				request.setAttribute("alltotal", stocklist.get(stocklist.size()-1).getRtotoal());	
				request.setAttribute("total", stocklist.size());
			}
			Stock stcObj = new Stock();
			stcObj.setPurchaseId(id);
			stcObj.setSubCategoryCode(sub);
			request.setAttribute("stcObj",stcObj);*/
			//loadAssetCategory(request,response,"../views/goodreceiving.jsp");
			searchByPoCode(request,response);
		}
		
	}
	public void searchByPoCode(HttpServletRequest request, HttpServletResponse response) {
		//int id = Integer.parseInt(request.getParameter("pos"));
		String poCode = request.getParameter("pos");
		int sub = Integer.parseInt(request.getParameter("subcat"));
		
		//String poCode = request.getParameter("pocode");
		PORepository por = new PORepository();
		PurchaseOrder poObj = new PurchaseOrder();
		//poObj =	por.fetchByPoCode(id,sub);
		poObj = por.fetchByPoCode(poCode, sub);
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
		fetchAll(request, response, page);
	}

}
