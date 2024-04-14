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
import model.AssetCategory;
import model.AssetClass;
import model.AssetSubCategory;
import model.Employee;
import model.ITEquip;
import model.LandandBuilding;
import model.Message;
import model.Print;
import model.PurchaseOrder;
import model.Roles;
import model.Stock;
import model.Supplier;
import model.Unit;
import model.Vehicle;
import repository.AssetCategoryRepository;
import repository.AssetClassRepository;
import repository.AssetRepository;
import repository.AssetSubCatRepository;
import repository.AuthorizationRepository;
import repository.PORepository;
import repository.RBACRepository;
import repository.StockRepository;
import repository.SupplierRepository;
import repository.UnitRepository;

/**
 * Servlet implementation class AssetControllerServlet
 */
@WebServlet("/AssetControllerServlet")
public class AssetControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String whoami;  
    private boolean canupdate = false;
    private boolean candelete = false;
    private boolean canauth = false;
    private boolean caninsert = false;
    private int uid;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssetControllerServlet() {
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
			String page = "views/famsAssetAssignment.jsp";
			System.out.println("I am dispatching to"+ page);
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
			try {
				uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			}catch(Exception e) {
				response.getWriter().append("Served at: ").append(request.getContextPath());
			}
			AcessFindeer(request,response);
			String action = request.getPathInfo();
			
			switch(action) {		
			case "/create" :
				createAsset(request, response);
				break;
			case "/cr" :
				System.out.println("it works man");
				break;
			case "/fbc" :
				fetchBulkCode(request,response);
				break;
			case "/tag" :
				findTag(request,response);
				break;
			case "/trn" :
				//processTransfer(request,response);
				break;
			case "/ret" :
				processReturn(request, response);
				break;
			case "/dis" :
				manualDisposal(request, response);
				break;
			case "/tt" :
				asssignAsset(request,response);
				break;
			case "/view" :
				prepareForView(request,response);
				break;
			case "/auth" :
				authorize(request,response);
				break;
			case "/search" :
				doSearching(request,response);
				break;
			case "/fltr" :
				subCategoryFetcher(request, response);
				break;
			
			}
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("onServer", "yes");
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
		
	}
	public void pdelete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id").toString());
		AuthorizationRepository ar = new AuthorizationRepository();
		String sq1 = "UPDATE famsStock\r\n" + 
				"SET famsStock.issuedTo = famsStock_history.issuedTo,\r\n" + 
				"famsStock.status = famsStock_history.status, famsStock.authStatus = famsStock_history.authStatus, famsStock.maker = famsStock_history.maker,\r\n" + 
				"famsStock.checker = famsStock_history.checker,famsStock.issueref = famsStock_history.issueref, famsStock.sysGenTag = famsStock_history.sysGenTag\r\n" + 
				"FROM  famsStock\r\n" + 
				"INNER JOIN famsStock_history \r\n" + 
				"ON famsStock.id = famsStock_history.id\r\n" + 
				"WHERE famsStock_history.id = ? and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='AR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'UI'\r\n" + 
				"";
		String sq2 = "delete from famsStock_history where ((famsStock_history.id = ? and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='AR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'UI')\r\n" + 
				"or (famsStock_history.id = ? and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='UI' AND famsStock_history.col1 = 'AVAILABLE' and col3 = 'AR'))";
		Message msg = new Message();
		if(ar.passetToPriviousState(id, sq1)) {
			if(ar.pdeleteHistoryOnQueuee(id, sq2)) {
				msg.setSuccessMsg("Record Deleted Successfully!");
			}else {
				msg.setErrorMsg("Error Occured While Deleting the record");
				System.out.println("Couldnt delete from famsStock_history on "+id);
			}
		}else {
			msg.setErrorMsg("Error Occured While Returning Asset to previous state");
			System.out.println("Error Occured While Returning Asset to previous state "+id);
		}
		
		String page = "../views/famsAssetAssignment.jsp";
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);
	}
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		//int id = Integer.parseInt(request.getParameter("id").toString());
		String issueref = request.getParameter("issueref");
		AuthorizationRepository ar = new AuthorizationRepository();
		int subcatt = ar.findSubCatWithIssueref(issueref);
		System.out.println("the subcat id is "+subcatt);
		System.out.println("the issueref value is "+issueref);
		String sq1 = "UPDATE famsStock\r\n" + 
				"SET famsStock.issuedTo = famsStock_history.issuedTo,\r\n" + 
				"famsStock.status = famsStock_history.status,famsStock.createdAt = famsStock_history.createdAt, famsStock.authStatus = famsStock_history.authStatus, famsStock.maker = famsStock_history.maker,\r\n" + 
				"famsStock.checker = famsStock_history.checker, famsStock.sysGenTag = famsStock_history.sysGenTag\r\n" + 
				"FROM  famsStock\r\n" + 
				"INNER JOIN famsStock_history \r\n" + 
				"ON famsStock.id = famsStock_history.id\r\n" + 
				"WHERE famsStock_history.id in(select id from famsStock where issueref = ?) and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='AR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'UI'\r\n" + 
				"";
		String sq2 = "delete from famsStock_history where ((famsStock_history.id in (select id from famsStock where issueref = ?) and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='AR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'UI')\r\n" + 
				"or (famsStock_history.id in(select id from famsStock where issueref = ?) and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='UI' AND famsStock_history.col1 = 'AVAILABLE' and col3 = 'AR'))";
	   
		Message msg = new Message();
		if(candelete) {
			if(whoami.equals(new StockRepository().fetchByIssueref(issueref).getMaker())) {
				if(new AssetSubCatRepository().fetchOne(subcatt).getMode() == 0) {
					if(ar.assetToPriviousState(issueref, sq1)) {
						if(ar.deleteHistoryOnQueuee(issueref, sq2)) {
							if(ar.clearIssueref(issueref)) {
								msg.setSuccessMsg("Record Deleted Successfully!");
							}else {
								msg.setErrorMsg("Error Occured while clearing issue with issueref no "+issueref);
							}
						}else {
							msg.setErrorMsg("Error Occured While Deleting the record from history table");
							System.out.println("Couldnt delete from famsStock_history on "+issueref);
						}
					}else {
						msg.setErrorMsg("Error Occured While Returning Asset to previous state");
						System.out.println("Error Occured While Returning Asset to previous state "+issueref);
					}
				}else {
					if(ar.returningNonSerialToPrevState(subcatt, issueref)) {
						msg.setSuccessMsg("Record Deleted Successfully!");
					}else {
						msg.setErrorMsg("Error Occured While Returning Asset to previous state");
					}
				}
			}else {
				msg.setErrorMsg("Access Ritht Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		String page = "../views/famsAssetAssignment.jsp";
		request.setAttribute("msg", msg);
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);
	}
	
	public void reject(HttpServletRequest request, HttpServletResponse response, String rejection_comment) {
		//int id = Integer.parseInt(request.getParameter("id").toString());
		String issueref = request.getParameter("issueref");
		AuthorizationRepository ar = new AuthorizationRepository();
		int subcatt = ar.findSubCatWithIssueref(issueref);
		System.out.println("the subcat id is "+subcatt);
		System.out.println("the issueref value is "+issueref);
		String sq1 = "UPDATE famsStock\r\n" + 
				"SET famsStock.rejection_comment = '"+rejection_comment+"', famsStock.issuedTo = famsStock_history.issuedTo,\r\n" + 
				"famsStock.status = famsStock_history.status,famsStock.createdAt = famsStock_history.createdAt, famsStock.authStatus = famsStock_history.authStatus, famsStock.maker = famsStock_history.maker,\r\n" + 
				"famsStock.checker = famsStock_history.checker, famsStock.sysGenTag = famsStock_history.sysGenTag\r\n" + 
				"FROM  famsStock\r\n" + 
				"INNER JOIN famsStock_history \r\n" + 
				"ON famsStock.id = famsStock_history.id\r\n" + 
				"WHERE famsStock_history.id in(select id from famsStock where issueref = ?) and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='AR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'UI'\r\n" + 
				"";
		String sq2 = "delete from famsStock_history where ((famsStock_history.id in (select id from famsStock where issueref = ?) and famsStock_history.status = 'AVAILABLE' and famsStock_history.authStatus='AR' AND famsStock_history.col1 = 'ISSUED' and col3 = 'UI')\r\n" + 
				"or (famsStock_history.id in(select id from famsStock where issueref = ?) and famsStock_history.status = 'ISSUED' and famsStock_history.authStatus='UI' AND famsStock_history.col1 = 'AVAILABLE' and col3 = 'AR'))";
	   
		Message msg = new Message();
		if(candelete) {
			if(!whoami.equals(new StockRepository().fetchByIssueref(issueref).getMaker())) {
				if(new AssetSubCatRepository().fetchOne(subcatt).getMode() == 0) {
					if(ar.assetToPriviousState(issueref, sq1)) {
						if(ar.deleteHistoryOnQueuee(issueref, sq2)) {
							if(ar.clearIssueref(issueref)) {
								msg.setSuccessMsg("Record Rejected Successfully!");
							}else {
								msg.setErrorMsg("Error Occured while clearing issue with issueref no "+issueref);
							}
						}else {
							msg.setErrorMsg("Error Occured While Deleting the record from history table");
							System.out.println("Couldnt delete from famsStock_history on "+issueref);
						}
					}else {
						msg.setErrorMsg("Error Occured While Returning Asset to previous state");
						System.out.println("Error Occured While Returning Asset to previous state "+issueref);
					}
				}else {
					if(ar.returningNonSerialToPrevState(subcatt, issueref)) {
						msg.setSuccessMsg("Record Deleted Successfully!");
					}else {
						msg.setErrorMsg("Error Occured While Returning Asset to previous state");
					}
				}
			}else {
				msg.setErrorMsg("Access Ritht Unavailable");
			}
			candelete = false;
		}else {
			msg.setErrorMsg("Access Right Unavailable");
		}
		
		String page = "../views/famsAssetAssignment.jsp";
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
			if(userRoleLists.get(i).getPageIdNameHolder().equals("ASSISS")) {
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
	public void doSearching(HttpServletRequest request,HttpServletResponse response) {
		StockRepository sr = new StockRepository();
        String sc = request.getParameter("sr");
        Stock ascat = new Stock(); ascat.setStatus(sc);request.setAttribute("asc", ascat);
		List<Stock> stc = sr.searchByIssueRef(sc);
		request.setAttribute("searchResult", stc);
		if(stc.size() == 0) {
			request.setAttribute("none", "none");
		}
		request.setAttribute("stotal", stc.size());
		System.out.println("Size of result is"+stc.size());
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetAssignment.jsp";
		fetchAllAssets(request,response,page);
	}
	public void authorize(HttpServletRequest request, HttpServletResponse response) {
		String issueref = request.getParameter("issueref");
		System.out.println("The issue ref no is "+issueref);
		String rejection_comment = request.getParameter("rejection-comment");
		int action = Integer.parseInt(request.getParameter("action"));
		AuthorizationRepository ar = new AuthorizationRepository();
		Message msg = new Message();
		if(canauth) {
			if(!whoami.equals(new StockRepository().fetchByIssueref(issueref).getMaker())) {
				if(action == 0) {
					Print p = ar.authorizeAndPrint("famsStock",issueref,action,"AI",whoami,rejection_comment);
					if(p.isIs_printed()) {
						//msg.setSuccessMsg("Record Authorized Successfully");
						msg.setSuccessMsg(action == 0 ? "Record Authorized Successfully!" : "Record Rejected Successfully!");
						BudgetControllerServlet bgc = new BudgetControllerServlet();
						bgc.directPrintingFromAuthorization(p.getIssueref(), request,response,p.getSubCat());
					}else {
						msg.setErrorMsg("Error occured while Authorizing!");
						String page = "../views/famsAssetAssignment.jsp";
						request.setAttribute("msg", msg);
						request.setAttribute("onServer", "yes");
						fetchAllAssets(request,response,page);
					}
				}else {
					reject(request,response,rejection_comment);	
				}
			}else {
				msg.setErrorMsg("Access Right Unavailable");
				String page = "../views/famsAssetAssignment.jsp";
				request.setAttribute("msg", msg);
				request.setAttribute("onServer", "yes");
				fetchAllAssets(request,response,page);
			}
			canauth = false;
		}else {
			msg.setErrorMsg("Access Right Not Available");
			String page = "../views/famsAssetAssignment.jsp";
			request.setAttribute("msg", msg);
			request.setAttribute("onServer", "yes");
			fetchAllAssets(request,response,page);
		}
	}
	public void prepareForView(HttpServletRequest request, HttpServletResponse response) {
		String issueref = request.getParameter("issueref");
		StockRepository sr = new StockRepository();
		Stock objprp = sr.fetchByIssueref(issueref);
		AssetSubCategory subCatDetail = new AssetSubCatRepository().fetchOne(objprp.getSubCategoryCode());
		request.setAttribute("subCatDetail", subCatDetail);
		request.setAttribute("objprp", objprp);
		request.setAttribute("ftest", new AssetSubCatRepository().fetchOne(objprp.getSubCategoryCode()));
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetAssignment.jsp";	
		fetchAllAssets(request,response,page);

	}
	public void findTag(HttpServletRequest request, HttpServletResponse response){
		String tag  = request.getParameter("tag");
		String page = "";
		Employee emp2 = new Employee();
		AssetRepository asr = new AssetRepository();
		if(request.getParameter("populate") != null || request.getParameter("su") != null) {
	    emp2 = asr.empInfo(Integer.parseInt(request.getParameter("empId").toString()));
		}
		System.out.println("tag "+tag);
		System.out.println("emp2 branch"+emp2.getBranch());

		Asset ast  = asr.findAssetByTag(tag);
		Employee emp = asr.empInfo(ast.getId());
		System.out.println("Emp name"+emp.getFullName());
		System.out.println("ast name"+ast.getAssetSubCategory());
		System.out.println("Emp name"+emp.getFullName());
		System.out.println("emp id"+ast.getId());
		
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
			page = "../views/famsManualDisposal.jsp";
		}
		else {
			 page = "../views/famsAssetTransfer.jsp";	
		}
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);		
	}
	public void processReturn(HttpServletRequest request, HttpServletResponse response) {
		AssetRepository asr = new AssetRepository();
		Message msg = new Message();
		if(asr.returnToStore(request.getParameter("tag"),whoami)) {
			msg.setSuccessMsg("Returning Item to store successful");
		}else {
			msg.setSuccessMsg("Returning Item to store is not successful");
		}
		request.setAttribute("msg",msg);
		String page = "../views/famsAssetReturn.jsp";
		fetchAllAssets(request,response,page);
	}
	public void manualDisposal(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Manual disposal called!");
		AssetRepository asr = new AssetRepository();
		String tag = request.getParameter("tag");
		System.out.print("tag no is "+tag);
		Message msg = new Message();
		if(asr.isAssetReturned(tag)){
			if(asr.dispose(tag,whoami,whoami)) {
				msg.setSuccessMsg("Asset Disposal is successful!");
			}else {
				msg.setErrorMsg("Error occurred while disposing asset");
			}
		}else {
			msg.setErrorMsg("Asset must returned before disposal");
		}
		request.setAttribute("onServer", "yes");
		request.setAttribute("msg",msg);
		String page = "../views/famsManualDisposal.jsp";
		fetchAllAssets(request,response,page);
	}
	public void createAsset(HttpServletRequest request, HttpServletResponse response) {
		if(request.getParameter("assetClass") != null){
			if(request.getParameter("assetCategory") != null) {
				int classId = Integer.parseInt(request.getParameter("assetClass").toString());
				int catId = Integer.parseInt(request.getParameter("assetCategory").toString());
				System.out.println(classId);
				AssetClassRepository assetClassRepo = new AssetClassRepository();
				AssetCategoryRepository assetCatRepo = new AssetCategoryRepository();
				AssetSubCatRepository assetSubCatRepo = new AssetSubCatRepository();
				AssetClass assetClassObj = assetClassRepo.fetchOne(classId);
				AssetCategory assetCatObj = assetCatRepo.fetchOne(catId);
				
				List<AssetCategory> assetCatList = assetCatRepo.fetchByClassId(classId);
				List<AssetSubCategory> assetSubCatList = assetSubCatRepo.fetchByCatId(catId);
				
				request.setAttribute("assetCatList", assetCatList);
				request.setAttribute("assetClassObj", assetClassObj);
				request.setAttribute("assetSubCatList", assetSubCatList);
				request.setAttribute("assetCatObj",assetCatObj);
				
				String page = "../views/famsAsset.jsp";
				System.out.println(assetClassObj.getAssetClassCode());
				fetchAllAssets(request,response,page);
			}else {
				int assetId = Integer.parseInt(request.getParameter("assetClass").toString());
				System.out.println(assetId);
				AssetClassRepository assetClassRepo = new AssetClassRepository();
				AssetCategoryRepository assetCatRepo = new AssetCategoryRepository();
				AssetClass assetClassObj = assetClassRepo.fetchOne(assetId);
				
				List<AssetCategory> assetCatList = assetCatRepo.fetchByClassId(assetId);
				
				request.setAttribute("assetCatList", assetCatList);
				request.setAttribute("assetClassObj", assetClassObj);
				
				String page = "../views/famsAsset.jsp";
				request.setAttribute("onServer", "yes");
				System.out.println(assetClassObj.getAssetClassCode());
				fetchAllAssets(request,response,page);	
			}
		}
	}
	
	public void fetchBulkCode(HttpServletRequest request, HttpServletResponse response) {
		AssetSubCatRepository ascr = new AssetSubCatRepository();
		Stock stockObj = new Stock();
		ITEquip it = new ITEquip();
		Vehicle vehicle = new Vehicle();
		LandandBuilding lb = new LandandBuilding();
		String issueRef = new StockRepository().fetchIssueRef();
		if(request.getParameter("su") != null || request.getParameter("populate") != null) {
		  String cat = request.getParameter("cat");
		  String tag = request.getParameter("tag");
		  String remark = request.getParameter("remark");
		  String rc = request.getParameter("receivingCode");
		  int subCat = Integer.parseInt(request.getParameter("assetSubCat"));
		  stockObj.setSysGeneratedTag(tag);
		  stockObj.setComment(remark);
		  stockObj.setRecievingCode(rc);
		  stockObj.setId(subCat);
		  String ast = "";
		  String serial = "";
		 // int assetCat = Integer.parseInt(request.getParameter("assetSubCat"));
		  int empId = Integer.parseInt(request.getParameter("empId"));
		  stockObj.setIssuedTo(empId);
		  stockObj.setCat(cat);
		  System.out.println("Current category"+stockObj.getCat());
		  switch(cat) {
		  case "IT Equipment" :
			  String model = request.getParameter("model");
			  serial = request.getParameter("serial");
			  it.setModel(model);
			  ast = "model : "+model+" serial : "+serial;
			  stockObj.setSerialNo(serial);
			  request.setAttribute("serialNo", serial);
			  break;
		  case "Vehicle" :
			  serial = request.getParameter("serial");
			  String color = request.getParameter("color");
			  String cc = request.getParameter("cc");
			  String plateno = request.getParameter("plateno");
			  String engine = request.getParameter("engine");
			  String modelname = request.getParameter("modelname");
			  String wheels = request.getParameter("wheels");
			  String seat = request.getParameter("seat");
			  String enginetype = request.getParameter("enginetype");	
			  request.setAttribute("serialNo", serial);
			  stockObj.setSerialNo(serial);
			  vehicle.setChassisNo(serial);vehicle.setColor(color);vehicle.setCc(cc);
			  vehicle.setPlateNo(plateno);vehicle.setEnginCapacity(engine);vehicle.setEngineType(enginetype);
			  vehicle.setNoOfWheerls(wheels);vehicle.setNoOfSeat(seat);vehicle.setModel(modelname);
			  ast =  "chassisNo: "+ serial+" color : "+color+" cc : "+cc+" platno : "+plateno+" engine : "+engine+" model : "+modelname+" wheels : "+wheels+" seat : "+seat+" engine type : "+enginetype;
			  break;
		  case "Land and Building" :
			  System.out.println("landmark!!");
			  String lat = request.getParameter("lat");	 
			  String longg = request.getParameter("long");	 
			  String area = request.getParameter("area");	
			  lb.setLbArea(area);lb.setLbLatitude(lat);lb.setLbLongitude(longg);
			  ast = "lat : "+lat+" lang : "+longg+" area : "+area;
			  break;
		  }
		  
		  
		  stockObj.setAssetSpec(ast);
		  StockRepository sr = new StockRepository();
		  Message msg = new Message();
		  if(request.getParameter("su") != null) {
			  if(ascr.fetchOne(subCat).getMode() == 1) {
				  if(sr.issueAssetWithSerialNo(stockObj,issueRef,whoami)) {
					  System.out.println("Dear I did came her with serial");
					  msg.setSuccessMsg("Asset Issued Successfully!!"); 
				  }else {
				 msg.setErrorMsg("Error Occcured while issuing asset"); 
				  }
			  }else {
				  if(sr.issueAssetWithSerialNo(stockObj,issueRef,whoami)) {
					  System.out.println("Dear I came her with no serial");
					  msg.setSuccessMsg("Asset Issued Successfully!!"); 
				  }else {
				 msg.setErrorMsg("Error Occcured while issuing asset"); 
				  }
			  }
			  
			 request.setAttribute("msg", msg); 
		  }else {
			String subCatt = request.getParameter("assetSubCat");
			int id = Integer.parseInt(request.getParameter("empId"));
			StockRepository srr = new StockRepository();
			AssetSubCategory asc = new AssetSubCategory();
			AssetSubCatRepository asr = new AssetSubCatRepository();
			asc = asr.fetchOne(Integer.parseInt(subCatt));
			Employee emp  = srr.fetchEmployeeData(id);
			request.setAttribute("sysGenTag", tag);
			request.setAttribute("employee", emp);
			request.setAttribute("stockObj", stockObj);
			request.setAttribute("it", it);
			request.setAttribute("subCat", asc);
			request.setAttribute("vehObj", vehicle);
			request.setAttribute("lb", lb);
			//request.setAttribute("msgg", msg);
		  }
		}
	else {
			String subCat = request.getParameter("assetSubCat");
			AssetSubCategory asc = new AssetSubCategory();
			AssetSubCatRepository asr = new AssetSubCatRepository();
		//	asc.setId(Integer.parseInt(subCat));
			asc = asr.fetchOne(Integer.parseInt(subCat));
			StockRepository sr = new StockRepository();
			int rc = sr.findBySubCat(Integer.parseInt(subCat));
			//String serialNo = sr.fetchSerial(Integer.parseInt(subCat));
			String sysGenTag = sr.fethcTagNumber();
			String bankName = "SIB-";
			String formattedTag = bankName.concat(asc.getSubCatCode())+"-".concat(sysGenTag); 
			request.setAttribute("sysGenTag", formattedTag);
			request.setAttribute("subCat", asc);
			//request.setAttribute("serialNo", serialNo);
			request.setAttribute("rc", rc);
			
		}
		request.setAttribute("onServer", "yes");
		String page = "../views/famsAssetIssuance.jsp";
		fetchAllAssets(request,response,page);
	}
	
	public void asssignAsset(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Issue Assset has been called!");
		String page = "../views/famsAssetAssignment.jsp";
		String issueRef = new StockRepository().fetchIssueRef();
		String fmode = "";
		if(request.getParameter("su") != null) {
			System.out.println("Requester finder");
			int id = Integer.parseInt(request.getParameter("empId"));
			String brncode  = request.getParameter("brncode");
			fetchEmployeeData(request,response,id);
		}
		else if(request.getParameter("populate") != null) {
			System.out.println("Asset Finder!");
			int id = Integer.parseInt(request.getParameter("empId"));
			fetchEmployeeData(request,response,id);
			String subCat = request.getParameter("assetSubCat");
			fmode = request.getParameter("fmode");
			System.out.println("the fmode is "+fmode);
			int subcat = Integer.parseInt(subCat);
			int qty = Integer.parseInt(request.getParameter("qty").toString());
			AssetSubCategory asc = new AssetSubCategory();
			AssetSubCatRepository asr = new AssetSubCatRepository();
		//	asc.setId(Integer.parseInt(subCat));
			asc = asr.fetchOne(Integer.parseInt(subCat));
			//request.setAttribute("asc", asc);
			System.out.println("Do i have informations i need?");
			String brncode = request.getParameter("brncode");
			System.out.println("branchcode is"+ brncode);
			StockRepository sr = new StockRepository();
			Message msg = new Message();
			List<Asset> listOfSerials = new ArrayList<>();
			List<Asset> listOfTags = new ArrayList<>();
			int fetchMode = Integer.parseInt(request.getParameter("fmode").toString());
			System.out.println("The fetch method is "+fetchMode);
			if(sr.doIHaveRequestedQuanity(subcat, qty)) {
				if(asc.getMode()==0) {
					 if(fetchMode == 2) {request.setAttribute("manual", "manual");}
					 listOfSerials = sr.fetchSerial(subcat,qty,fetchMode);
					 listOfTags = sr.fetchTags(listOfSerials.size(),asc.getSubCatCode(),brncode);
				}else {
					if(fetchMode == 2) {fetchMode = 0;}
					 listOfSerials = sr.fetchSerial(subcat,qty,fetchMode);
					 System.out.println("Quntity "+qty);
					 int diff = sr.totalAvilable(subcat) - sr.totalissued(subcat);
					// System.out.println("Limit exceed");
					 if(qty > diff) {
						 msg.setErrorMsg("Minimize quantity to the available amount of item in stock");
						// System.out.println("Limit exceed");
						 request.setAttribute("msg", msg);
					 }else {
						 listOfTags = sr.fetchTags(qty,asc.getSubCatCode(),brncode);
					 }
					
				}
				request.setAttribute("afound","yes");
				request.setAttribute("qnf", "("+qty+") Required Quantity of Asset Found!");
			}else {
				msg.setErrorMsg("Supplied quantity is greater than available quantity");
				request.setAttribute("qnf", "Supplied quantity is greater than available quantity");
			}
			
			System.out.println("Total list of tags is"+listOfTags.size());
			//String sysGenTag = sr.fethcTagNumber();
			//String bankName = "SIB-";
		//	String formattedTag = bankName.concat(asc.getSubCatCode())+"-".concat(sysGenTag); 
			
			request.setAttribute("listSerials",listOfSerials);
			request.setAttribute("tags", listOfTags);
			request.setAttribute("subCat", asc);
			request.setAttribute("qty", qty);;
			//request.setAttribute("serialNo", serialNo);
		}
		else {
			AssetSubCatRepository ascr = new AssetSubCatRepository();
			Stock stockObj = new Stock();
			ITEquip it = new ITEquip();
			Vehicle vehicle = new Vehicle();
			LandandBuilding lb = new LandandBuilding();
			String cat = request.getParameter("cat");
			String remark = request.getParameter("remark");
			int subCat = Integer.parseInt(request.getParameter("assetSubCat"));
			  stockObj.setComment(remark);
			  stockObj.setId(subCat);
			  String ast = "";
			  int empId = Integer.parseInt(request.getParameter("empId"));
			  stockObj.setIssuedTo(empId);
			  stockObj.setCat(cat);
			  System.out.println("Current category"+stockObj.getCat());
			  
			 StockRepository sr = new StockRepository();
			 Message msg = new Message();
			  
			String [] serials = request.getParameterValues("lsserial");
			String [] tags = request.getParameterValues("lstags");
			//System.out.println("Serials length is"+serials.length);
			//System.out.println("subcat"+stockObj.getId());
			String req = request.getParameter("reqno");
			String brncode = request.getParameter("brncode");
			stockObj.setReqNo(req);
			if(sr.validateRequisition(req, brncode)) {
				if(ascr.fetchOne(subCat).getMode() == 0) {
					if(caninsert) {
						for(int i = 0; i<serials.length;i++) {
							stockObj.setSerialNo(serials[i]);
							stockObj.setSysGeneratedTag(tags[i]);
							System.out.println("subcat--"+stockObj.getId());
							System.out.println("serial--"+stockObj.getSerialNo());
							if(sr.issueAssetWithSerialNo(stockObj,issueRef,whoami)) {
								  msg.setSuccessMsg("Asset with serial Issued Successfully!! Issue Ref No is "+issueRef); 
								 // page = "../views/storeIssueVoucher.jsp";
								  //System.out.println("The size of Issue Voucher is "+issueVoucher.size());
							  }else {
							 msg.setErrorMsg("Error Occcured while issuing asset"); 
							  }
						}
						caninsert = false;
					}else {
						msg.setErrorMsg("Access Right Unavailable");
					}
					
				}
				//non serial registration
				else {
					int qty = Integer.parseInt(request.getParameter("qty").toString().trim());
					if(caninsert) {
						if(ascr.fetchOne(subCat).getMode() == 4) 
						{
							System.out.println("Issuing assets with chequa and passbook");
							stockObj.setAssetSpec("First No. ["+request.getParameter("fno")+"] Last No. ["+request.getParameter("lno")+"]");
							for(int i = 0;i<qty;i++) {
								stockObj.setSysGeneratedTag(tags[i]);
								stockObj.setMaker(whoami);
								 if(sr.issueAssetWithCheque(stockObj,issueRef,qty/qty)) {
									  System.out.println("Dear I came her with no serial");
									  msg.setSuccessMsg("Asset with out serial Issued Successfully!!"); 
								  }else {
								 msg.setErrorMsg("Error Occcured while issuing asset"); 
								  }
							}
						
						}else {
							System.out.println("Issuing assets with lots.");
							for(int i = 0;i<qty;i++) {
								stockObj.setSysGeneratedTag(tags[i]);
								stockObj.setMaker(whoami);
								 if(sr.issueAssetWithNOSerialNo(stockObj,issueRef,qty/qty)) {
									  System.out.println("Dear I came her with no serial");
									  msg.setSuccessMsg("Asset with out serial Issued Successfully!!"); 
								  }else {
								 msg.setErrorMsg("Error Occcured while issuing asset"); 
								  }
							}
						}
						caninsert = false;
					}else {
						msg.setErrorMsg("Access Right Unavailable");
					}
				  }
			}else {
				msg.setInfo("Requisition No is Invalid.");
			}
			request.setAttribute("msg", msg);
		}
		request.setAttribute("fmode", fmode);
		request.setAttribute("onServer", "yes");
		fetchAllAssets(request,response,page);
	}
	public void fetchEmployeeData(HttpServletRequest request,HttpServletResponse response,int id) {
		StockRepository srr = new StockRepository();
		//Employee emp  = srr.fetchEmployeeData(id);
		String brncode = request.getParameter("brncode");
		Employee emp = srr.fetchEmployeeDataOnIssue(id, brncode);
		Message msg = new Message();
		if(emp == null) {
			 msg.setErrorMsg("Employee not found!");
			 request.setAttribute("msg", msg);
		}
		request.setAttribute("employee", emp);
		request.setAttribute("brncode", brncode);
	}
	
	public void fetchAllAssets(HttpServletRequest request, HttpServletResponse response,String page) {
		AssetSubCatRepository aSubCatRepository = new AssetSubCatRepository();
		//start of asset load optimization
		AssetSubCatRepository aSubCatRepo = new AssetSubCatRepository();
		AssetClassRepository aClassRepo = new AssetClassRepository();
		AssetCategoryRepository aCatRepo = new AssetCategoryRepository();
		UnitRepository uRepo = new UnitRepository();
		//commenting this line
		List<AssetClass> aClassList = aClassRepo.fetchByAuth();
		List<AssetCategory> aCatList = aCatRepo.fetchByClassId(aClassList.get(0).getId());
		List<AssetSubCategory> aSubCatList = aSubCatRepo.fetchByCatId(aCatList.get(0).getId());
		request.setAttribute("aClassList", aClassList);
		request.setAttribute("aCatList", aCatList);
		request.setAttribute("aSubCatList", aSubCatList);
		//end of asset load optimization
		//List<AssetSubCategory> assetSubCatList = aSubCatRepository.fetch();
		List<AssetSubCategory> assetSubCatList = aSubCatRepository.fetchForIssue();
		StockRepository sr = new StockRepository();
		List<Stock> stockList = sr.fetchUnathorizedIssuance();
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
	public void subCategoryFetcher(HttpServletRequest request, HttpServletResponse response) {
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
		request.setAttribute("afound","afound");
		request.setAttribute("qnf","qnf");
		String page = "../views/famsAssetAssignment.jsp";
		int id = Integer.parseInt(request.getParameter("empId"));
		String brncode  = request.getParameter("brncode");
		fetchEmployeeData(request,response,id);
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
