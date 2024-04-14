package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AssetCategory;
import model.AssetClass;
import model.AssetSubCategory;
import model.PurchaseOrder;
import model.Reporting;
import model.Stock;

public class ReportingRepositoy {
	private boolean connCreated;
	DbConnection dbConn;
	
	public ReportingRepositoy() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<Reporting> categoryPerBranch(String category,String dfrom, String dto) {
		List<Reporting> categoryPerclass = new ArrayList<>();
		String squery = "";
		if(dfrom == "" && dto == "") {
			System.out.println("Case 0 on processed!");
			 squery = "select distinct(branch.branchName),sum(famsStock.quantity*famsStock.unitPrice) as TotalPrice,assetCat.assetCatCode from famsStock\r\n" + 
						"inner join hremployees on famsStock.issuedTo = hremployees.Employeeid\r\n" + 
						"inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
						"inner join assetCat on assetSubCat.subCatCategory = assetCat.id\r\n" + 
						"inner join branch on hremployees.empBranch = branch.branchId where assetCat.id = '"+category+"' group by branch.branchName,famsStock.issuedTo, assetCatCode";
		}else if(dfrom != "" && dto == "") {
			System.out.println("Case 1 on processed!");
			 squery = "select distinct(branch.branchName),sum(famsStock.quantity*famsStock.unitPrice) as TotalPrice,assetCat.assetCatCode from famsStock\r\n" + 
						"inner join hremployees on famsStock.issuedTo = hremployees.Employeeid\r\n" + 
						"inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
						"inner join assetCat on assetSubCat.subCatCategory = assetCat.id\r\n" + 
						"inner join branch on hremployees.empBranch = branch.branchId where assetCat.id = '"+category+"' and famsStock.createdAt >= '"+dfrom+"' group by branch.branchName,famsStock.issuedTo, assetCatCode";
		}else if (dfrom == "" && dto != "") {
			System.out.println("Case 2 on processed!");
			 squery = "select distinct(branch.branchName),sum(famsStock.quantity*famsStock.unitPrice) as TotalPrice,assetCat.assetCatCode from famsStock\r\n" + 
						"inner join hremployees on famsStock.issuedTo = hremployees.Employeeid\r\n" + 
						"inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
						"inner join assetCat on assetSubCat.subCatCategory = assetCat.id\r\n" + 
						"inner join branch on hremployees.empBranch = branch.branchId where assetCat.id = '"+category+"' and famsStock.createdAt <= '"+dto+"' group by branch.branchName,famsStock.issuedTo, assetCatCode";
		}else {
			System.out.println("Case 3 processed!");
			 squery = "select distinct(branch.branchName),sum(famsStock.quantity*famsStock.unitPrice) as TotalPrice,assetCat.assetCatCode from famsStock\r\n" + 
					   "inner join hremployees on famsStock.issuedTo = hremployees.Employeeid\r\n" + 
					   "inner join assetSubCat on famsStock.subCategory = assetSubCat.id\r\n" + 
					   "inner join assetCat on assetSubCat.subCatCategory = assetCat.id\r\n" + 
					   "inner join branch on hremployees.empBranch = branch.branchId where assetCat.id = '"+category+"' and (famsStock.createdAt <= '"+dto+"' and famsStock.createdAt >= '"+dfrom+"') group by branch.branchName,famsStock.issuedTo, assetCatCode";
		}
		
		PreparedStatement pst;
		float alltotal = 0;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			//pst.setString(1, category);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Reporting rp = new Reporting();
				rp.setRbranch(rs.getString("branchName"));
				rp.setRcategory(rs.getString("assetCatCode"));
				rp.setRtotal(rs.getFloat("TotalPrice"));
				alltotal = alltotal + rs.getFloat("TotalPrice");
				rp.setAlltotal(alltotal);
				categoryPerclass.add(rp);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return categoryPerclass;
	}
	public List<PurchaseOrder> fetchpo() {
		List<PurchaseOrder> poList = new ArrayList<>();
		String squery = "select PurchaseOrders.* from famsStock \r\n" + 
				"inner join PurchaseOrders on famsStock.orderId = PurchaseOrders.id\r\n" + 
				"where famsStock.authStatus not in ('U')";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				PurchaseOrder po = new PurchaseOrder();
				//po.setSubCat(rs.getString("subCatCode"));
				po.setId(rs.getInt("id"));
				po.setDescription(rs.getString("description"));
				po.setPurchaseTypes(rs.getString("purchaseTypes"));
				po.setSubCategory(rs.getInt("Sub_category"));
				po.setSupplier(rs.getInt("Supplierid"));
				po.setCreatedAt(rs.getString("Order_Date"));
				po.setDeliveryDate(rs.getString("Delivery_Date"));
				po.setUnitPrice(rs.getFloat("unit_price"));
				po.setQuantity(rs.getInt("quantity"));
				po.setUnitMeasurement(rs.getInt("unitofmeasurement"));
				po.setTotal(rs.getFloat("unit_price")*rs.getInt("quantity"));
	            po.setPurchaeOrderstatus(rs.getString("PurchaseOrderstatus"));
	           // po.(rs.getInt("maker"));
	            po.setPoCode(rs.getString("poCode"));
	            po.setUnRegisteredItem(new StockRepository().findUnregisteredItem(rs.getInt("id")));
				
	            poList.add(po);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return poList;
	}
	public List<PurchaseOrder> pfetchpoForRec() {
		List<PurchaseOrder> poList = new ArrayList<>();
		String squery = "select * from PurchaseOrders where authStatus = 'A'";
		String squery2 = "select * from PurchaseOrders where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				PurchaseOrder po = new PurchaseOrder();
				//po.setSubCat(rs.getString("subCatCode"));
				po.setId(rs.getInt("id"));
				po.setDescription(rs.getString("description"));
				po.setPurchaseTypes(rs.getString("purchaseTypes"));
				po.setSubCategory(rs.getInt("Sub_category"));
				po.setSupplier(rs.getInt("Supplierid"));
				po.setCreatedAt(rs.getString("Order_Date"));
				po.setDeliveryDate(rs.getString("Delivery_Date"));
				po.setUnitPrice(rs.getFloat("unit_price"));
				po.setQuantity(rs.getInt("quantity"));
				po.setUnitMeasurement(rs.getInt("unitofmeasurement"));
				po.setTotal(rs.getFloat("unit_price")*rs.getInt("quantity"));
	            po.setPurchaeOrderstatus(rs.getString("PurchaseOrderstatus"));
	           // po.(rs.getInt("maker"));
	            po.setPoCode(rs.getString("poCode"));
	            po.setUnRegisteredItem(new StockRepository().findUnregisteredItem(rs.getInt("id")));
				
	            poList.add(po);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return poList;
	}
	public List<PurchaseOrder> fetchpoForRec() {
		List<PurchaseOrder> poList = new ArrayList<>();
		String squery = "select * from PurchaseOrders where authStatus = 'A'";
		String squery2 = "select distinct poCode from PurchaseOrders where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery2);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				PurchaseOrder po = new PurchaseOrder();
				/*//po.setSubCat(rs.getString("subCatCode"));
				po.setId(rs.getInt("id"));
				po.setDescription(rs.getString("description"));
				po.setPurchaseTypes(rs.getString("purchaseTypes"));
				po.setSubCategory(rs.getInt("Sub_category"));
				po.setSupplier(rs.getInt("Supplierid"));
				po.setCreatedAt(rs.getString("Order_Date"));
				po.setDeliveryDate(rs.getString("Delivery_Date"));
				po.setUnitPrice(rs.getFloat("unit_price"));
				po.setQuantity(rs.getInt("quantity"));
				po.setUnitMeasurement(rs.getInt("unitofmeasurement"));
				po.setTotal(rs.getFloat("unit_price")*rs.getInt("quantity"));
	            po.setPurchaeOrderstatus(rs.getString("PurchaseOrderstatus"));
	           // po.(rs.getInt("maker"));
*/	            po.setPoCode(rs.getString("poCode"));
	           /* po.setUnRegisteredItem(new StockRepository().findUnregisteredItem(rs.getInt("id")));*/
				
	            poList.add(po);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return poList;
	}
	public List<AssetSubCategory> fetch(int poid) {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select assetSubCat.* from famsStock \r\n" + 
				"inner join assetSubCat on famsStock.subCategory = assetSubCat.id \r\n" + 
				"where famsStock.orderId = ?";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, poid);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				//AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				//assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public List<AssetSubCategory> pfetchSub2(int poid) {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select assetSubCat.* from PurchaseOrders \r\n" + 
				"inner join assetSubCat on PurchaseOrders.Sub_category = assetSubCat.id \r\n" + 
				"where PurchaseOrders.id = ?";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, poid);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				//AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				//assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public List<AssetSubCategory> fetchSub2(String poCode) {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select assetSubCat.* from PurchaseOrders \r\n" + 
				"inner join assetSubCat on PurchaseOrders.Sub_category = assetSubCat.id \r\n" + 
				"where PurchaseOrders.poCode = ?";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, poCode);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				//AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				//assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public List<Stock> GRVReport(String rc) {
		List<Stock> stockList = new ArrayList<>();
		float total  = 0;
		String squery = "SELECT purchaseOrders.poCode,famsStock.subCategory,famsStock.maker,famsStock.checker,famsStock.receivingCode,famsSupplier.supplierName,famsStock.recieptNo, assetSubCat.subCatCode,assetSubCat.subCatDesc,assetUnitf.unitName,    \r\n" + 
				"sum(famsStock.quantity) AS qty,famsStock.unitPrice, FORMAT(famsStock.createdAt, 'dd/MM/yyyy') as dd\r\n" + 
				"FROM famsStock\r\n" + 
				"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"INNER JOIN assetUnitf ON famsStock.unitMeasurement = assetUnitf.id\r\n" + 
				"INNER JOIN purchaseOrders ON famsStock.orderId = PurchaseOrders.id\r\n" + 
				"INNER JOIN famsSupplier ON purchaseOrders.Supplierid = famsSupplier.id \r\n" + 
				"WHERE famsStock.receivingCode = ? and famsStock.status IN ('AVAILABLE','ISSUED') and famsStock.authStatus IN ('AR','UI','AI','UTRN','ATRN')\r\n" + 
				"GROUP BY assetSubCat.subCatCode,assetSubCat.subCatDesc,assetUnitf.unitName ,famsStock.unitPrice,\r\n" + 
				"purchaseOrders.poCode,famsStock.subCategory,famsSupplier.supplierName ,famsStock.recieptNo,famsStock.receivingCode,\r\n" + 
				"famsStock.maker,famsStock.checker,FORMAT(famsStock.createdAt, 'dd/MM/yyyy')";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, rc);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setStatus(rs.getString("subCatCode"));
				stc.setDecription(rs.getString("subCatDesc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setAssetSpec(serials(rs.getString("receivingCode"),rs.getInt("subCategory"),new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getMode()));
				stc.setBrnstatus(rs.getString("unitName"));
				stc.setPurchaseDate(rs.getString("dd"));
				stc.setQuantity(rs.getInt("qty"));
				stc.setUnitPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("supplierName"));
				stc.setIssueRef(rs.getString("poCode"));
				stc.setAuthStatus(rs.getString("recieptNo"));
				stc.setTotalPrice(rs.getInt("qty") * rs.getFloat("unitPrice"));
				stc.setMaker(rs.getString("maker"));
				stc.setChecker(rs.getString("checker"));
				total = total + rs.getInt("qty") * rs.getFloat("unitPrice");
				stc.setRtotoal(total);
				
				stockList.add(stc);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public String serials(String rc,int subcat,int mode) {
		String serials = "";
		String squery = "";
		if(mode == 4) {
			squery = "SELECT assetSpecificText FROM famsStock\r\n" + 
					"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
					"WHERE  receivingCode = ? AND mode IN (SELECT\r\n" + 
					"mode FROM assetSubCat WHERE id = '"+subcat+"') and subCategory = '"+subcat+"'";
		}else {
			squery = "SELECT concat(serialNo, ', ') FROM famsStock\r\n" + 
					"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
					"WHERE serialNo != 'NONSERIAL' AND receivingCode = ? AND mode IN (SELECT\r\n" + 
					"mode FROM assetSubCat WHERE id = '"+subcat+"') and subCategory = '"+subcat+"'";
		}
	    try {
	    PreparedStatement pst = dbConn.connection.prepareStatement(squery);
	    pst.setString(1, rc);
	    ResultSet rs  = pst.executeQuery();
	    while(rs.next()) {
	    	serials = serials + rs.getString(1);
	    }
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    return serials;
	}
	public List<Reporting> assetPerClass(){
		String squery = "select assetClass.assetClassCode,assetClassDesc, count(assetClass.id) as total from famsStock inner join assetSubCat on\r\n" + 
				"famsStock.subCategory  = assetSubCat.id\r\n" + 
				"inner join assetCat on\r\n" + 
				"assetSubCat.subCatCategory = assetCat.id\r\n" + 
				"inner join assetClass on \r\n" + 
				"assetCat.assetCatClass = assetClass.id where famsStock.authStatus in ('A','AR','AI','ATRN') group by assetClass.assetClassCode, assetClassDesc";
		List<Reporting> repoList = new ArrayList<>();
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Reporting repo = new Reporting();
				repo.setClassCode(rs.getString(1));
				repo.setClassDesc(rs.getString(2));
				repo.setTotal(rs.getInt(3));
				
				repoList.add(repo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return repoList;
	}
	public List<Reporting> assignedAssetPerCategory(){
		String squery = "select assetCat.assetCatCode,assetCat.assetCatDesc, count(assetCat.id) as total from famsStock \r\n" + 
				"inner join assetSubCat on\r\n" + 
				"famsStock.subCategory  = assetSubCat.id\r\n" + 
				"inner join assetCat on\r\n" + 
				"assetSubCat.subCatCategory = assetCat.id  where famsStock.authStatus in ('AR','AI','ART') and famsStock.status = 'ISSUED'\r\n" + 
				"group by assetCat.assetCatCode, assetCat.assetCatDesc";
		List<Reporting> repoList = new ArrayList<>();
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Reporting repo = new Reporting();
				repo.setClassCode(rs.getString(1));
				repo.setClassDesc(rs.getString(2));
				repo.setTotal(rs.getInt(3));
				
				repoList.add(repo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return repoList;
	}
	public List<Reporting> availableAssetPerCategory(){
		String squery = "select assetCat.assetCatCode,assetCat.assetCatDesc, count(assetCat.id) as total from famsStock \r\n" + 
				"inner join assetSubCat on\r\n" + 
				"famsStock.subCategory  = assetSubCat.id\r\n" + 
				"inner join assetCat on\r\n" + 
				"assetSubCat.subCatCategory = assetCat.id  where famsStock.authStatus in ('AR','AI','ART') and famsStock.status = 'AVAILABLE'\r\n" + 
				"group by assetCat.assetCatCode, assetCat.assetCatDesc";
		List<Reporting> repoList = new ArrayList<>();
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Reporting repo = new Reporting();
				repo.setClassCode(rs.getString(1));
				repo.setClassDesc(rs.getString(2));
				repo.setTotal(rs.getInt(3));
				
				repoList.add(repo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return repoList;
	}
	
}
