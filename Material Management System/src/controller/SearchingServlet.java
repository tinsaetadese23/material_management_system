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
import model.Roles;
import repository.AssetRepository;
import repository.RBACRepository;

/*import model.Analytics;
import model.DbConnection;
import model.Issue;
import model.IssueAssign;
import model.Searching;*/

/**
 * Servlet implementation class SearchingServlet
 */
@WebServlet("/SS")
public class SearchingServlet extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     *
     */
	private String whoami;
	private int uid;
    public SearchingServlet() {
        super();
 
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession mySession = request.getSession();
		whoami = (String)mySession.getAttribute("username");
		try {
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			String page = "/views/famsAsset.jsp";
			fetchRoles(request,response,page);
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
		//doGet(request, response);
		try {
			HttpSession mySession = request.getSession();
			whoami = (String)mySession.getAttribute("username");
			uid = Integer.parseInt(mySession.getAttribute("uid").toString());
			System.out.println("Post Method Called");
			String path = request.getPathInfo();
			request.setAttribute("onServer", "yes");
			switch(path) {
			case "/fetch" :
				doSearchingForTaskView(request, response);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher ds = request.getRequestDispatcher("../views/sessionTimeout.jsp");
			ds.forward(request, response);
		}
			
	}	
	
	private void fetchRoles(HttpServletRequest request, HttpServletResponse response,String page) {
		System.out.println("I am inside this method");
		List<Roles> userRoleLists = new ArrayList<>();
		RBACRepository rbacRepository = new RBACRepository();
		userRoleLists = rbacRepository.fetchAccessForUser(uid);
		request.setAttribute("userRoleLists", userRoleLists);
		request.setAttribute("ss", "ss");
		RequestDispatcher ds = request.getRequestDispatcher(page); 
		try {
			ds.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/***********************************************************************************************************************************************/
/****************************************Searching For Task View Page**************************************************************************/
/**********************************************************************************************************************************************/
	
	public void doSearchingForTaskView(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("This is only for tassk view filter");
	//	int id = Integer.parseInt(request.getParameter("suser").toString().trim());
		String searchText = request.getParameter("searchText");
		String dFrom = request.getParameter("from");
		String dTo = request.getParameter("to");
		String status = request.getParameter("sstatus");
		String squery = "";
		//Syst7
		if(status == null) {
			status = "all";
		}
		System.out.println("Status five is"+status);
		if(status.equals("all")) {
			if(!request.getParameter("tag").toString().trim().equals("")) {
				String tag = request.getParameter("tag").toString().trim();
				System.out.println("Issue id is given");
				squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
						",famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
						"where sysGenTag = '"+tag+"'";
				//where famsStock.sysGenTag = ?
			}else {
				//case one
				if(searchText != "" && dFrom != "" && dTo != "") {
					System.out.println("Case 1");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"')  and try_convert(date,famsStock.createdAt) <= '"+dTo+"' and try_convert(date,famsStock.createdAt) >= '"+dFrom+"'";

				}
				//case two
				else if(searchText == "" && dFrom != "" && dTo != "") {
					System.out.println("Case 2");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where  try_convert(date,famsStock.createdAt) <= '"+dTo+"' and try_convert(date,famsStock.createdAt) >= '"+dFrom+"'";
				}
				else if(searchText == "" && dFrom == "" && dTo != "") {
					System.out.println("Case 3");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where  try_convert(date,famsStock.createdAt) <= '"+dTo+"'";
				}
				else if(searchText == "" && dFrom != "" && dTo == "") {
					System.out.println("Case 4");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where try_convert(date,famsStock.createdAt) >= '"+dFrom+"'";

				}
				else if(searchText != "" && dFrom != "" && dTo == "") {
					System.out.println("Case 5");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"')  and try_convert(date,famsStock.createdAt) >= '"+dFrom+"'";
				}
				else if (searchText != "" && dFrom == "" && dTo != ""){
					System.out.println("Case 6");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"')  and try_convert(date,famsStock.createdAt) <= '"+dTo+"'";
				}
				else if(searchText != "" && dFrom == "" && dTo == "") {
					System.out.println("Case 7 and typeuser from server" );
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							",famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"where famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"'";
				}
				else
				{
					System.out.println("My search criteria is giveb here");
					squery = "select famsStock.*, assetSubCat.* from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id";
				}
		}	
		}
		
		/*****************************************If status is given*****************************************/
		else
		{
			if(!request.getParameter("tag").toString().trim().equals("")) {
				String tag = request.getParameter("tag").toString().trim();
				System.out.println("Issue id is given");
				squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
						",famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
						"where sysGenTag = '"+tag+"'";
			}else {
				//case one
				if(searchText != "" && dFrom != "" && dTo != "") {
					System.out.println("Case 1");
					if(status.equals("ISSUED")) {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join \r\n" + 
								"						assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"					   inner JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
								"					   inner JOIN branch ON branch.branchId = hremployees.empBranch \r\n" + 
								"					  INNER join managementirectorate ON hremployees.managerid = managementirectorate.MDirectorateID \r\n" + 
								"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR \r\n" + 
								"						famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"' OR branch.branchCode = '"+searchText+"' or managementirectorate.MDirectorate = '"+searchText+"') and try_convert(date,famsStock.createdAt) >= '"+dFrom+"' and try_convert(date,famsStock.createdAt) <= '"+dTo+"'";
					}else {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"						where (famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"')  and try_convert(date,famsStock.createdAt) <= '"+dTo+"' and try_convert(date,famsStock.createdAt) >= '"+dFrom+"' and famsStock.status='"+status+"'";
					}
				}
				//case two
				else if(searchText == "" && dFrom != "" && dTo != "") {
					System.out.println("Case 2");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where  try_convert(date,famsStock.createdAt) <= '"+dTo+"' and try_convert(date,famsStock.createdAt) >= '"+dFrom+"' and famsStock.status='"+status+"'";
				}
				else if(searchText == "" && dFrom == "" && dTo != "") {
					System.out.println("Case 3");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where  try_convert(date,famsStock.createdAt) <= '"+dTo+"' and famsStock.status='"+status+"'";
				}
				else if(searchText == "" && dFrom != "" && dTo == "") {
					System.out.println("Case 4");
					squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
							"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
							"						where  try_convert(date,famsStock.createdAt) >= '"+dFrom+"' and famsStock.status='"+status+"'";

				}
				else if(searchText != "" && dFrom != "" && dTo == "") {
					System.out.println("Case 5");
					if(status.equals("ISSUED")) {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join \r\n" + 
								"						assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"					   inner JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
								"					   inner JOIN branch ON branch.branchId = hremployees.empBranch \r\n" + 
								"					  INNER join managementirectorate ON hremployees.managerid = managementirectorate.MDirectorateID \r\n" + 
								"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR \r\n" + 
								"						famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"' OR branch.branchCode = '"+searchText+"' or managementirectorate.MDirectorate = '"+searchText+"') and try_convert(date,famsStock.createdAt) >= '"+dFrom+"'";
					}else {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"						where (famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"')  and try_convert(date,famsStock.createdAt) >= '"+dFrom+"' and  famsStock.status='"+status+"'";
					}
					
				}
				else if (searchText != "" && dFrom == "" && dTo != ""){
					System.out.println("Case 6");
					if(status.equals("ISSUED")) {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join \r\n" + 
								"						assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"					   inner JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
								"					   inner JOIN branch ON branch.branchId = hremployees.empBranch \r\n" + 
								"					  INNER join managementirectorate ON hremployees.managerid = managementirectorate.MDirectorateID \r\n" + 
								"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR \r\n" + 
								"						famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"' OR branch.branchCode = '"+searchText+"' or managementirectorate.MDirectorate = '"+searchText+"') and try_convert(date,famsStock.createdAt) <= '"+dTo+"'";
					}else {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"						where (famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"')  and try_convert(date,famsStock.createdAt) <= '"+dTo+"' and  famsStock.status='"+status+"')";
					}
					
				}
				else if(searchText != "" && dFrom == "" && dTo == "") {
					System.out.println("Case 7 and  from taskview" );
					if(status.equals("ISSUED")) {
					 squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								"						,famsStock.unitPrice,famsStock.status from famsStock inner join \r\n" + 
								"						assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"					   inner JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
								"					   inner JOIN branch ON branch.branchId = hremployees.empBranch \r\n" + 
								"					  INNER join managementirectorate ON hremployees.managerid = managementirectorate.MDirectorateID \r\n" + 
								"						where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR \r\n" + 
								"						famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"' OR branch.branchCode = '"+searchText+"' or managementirectorate.MDirectorate = '"+searchText+"')";
					}else {
						squery = "select famsStock.sysGenTag,assetSubCat.subCatCode,famsStock.serialNo,famsStock.requisitionNo,famsStock.authStatus,famsStock.receivingCode,issueref\r\n" + 
								",famsStock.unitPrice,famsStock.status from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
								"where (famsStock.requisitionNo = '"+searchText+"' OR famsStock.issueref = '"+searchText+"' OR famsStock.maker = '"+searchText+"' OR famsStock.serialNo = '"+searchText+"' OR assetSubCat.subCatCode = '"+searchText+"') and famsStock.status = '"+status+"'";
					}
				}
				else
				{
					System.out.println("My search criteria is here on status search");
					squery = "select famsStock.*, assetSubCat.* from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id where status = '"+status+"'";
				}
		}
		}
		/*****************************************end of if status is given *********************************/
			try {
				System.out.println("Searching reached here!!");
				AssetRepository asr = new AssetRepository();
				String tag = request.getParameter("tag");
				List<Asset> lisOfAssets = asr.fetchAssets(tag, squery);
				System.out.println("The given tag number is"+tag);
				request.setAttribute("lsAsset", lisOfAssets);
				request.setAttribute("total",lisOfAssets.size());
				System.out.println("Total size of assets"+lisOfAssets.size());
				request.setAttribute("onServer", "yes");
				//RequestDispatcher ds = request.getRequestDispatcher("../views/famsAsset.jsp"); 
				//ds.forward(request, response);
				String page  =  "../views/famsAsset.jsp";
				fetchRoles(request,response,page);
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	
}
