package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Asset;
import model.AssetClass;
import model.Employee;
import model.PurchaseOrder;
import model.Stock;

public class StockRepository {
	private boolean connCreated;
	DbConnection dbConn;
	public StockRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean save(Stock stock,String maker) {
		String sq1 = "";
		String squery = "INSERT INTO [dbo].[famsStock]\r\n" + 
				"           ([receivingCode]\r\n" + 
				"           ,[subCategory]\r\n" + 
				"           ,[unitMeasurement]\r\n" + 
				"           ,[unitPrice]\r\n" + 
				"           ,[quantity]\r\n" + 
				"           ,[orderId]\r\n" + 
				"           ,[purchasedDate]\r\n" + 
				"           ,[supplierId]\r\n" + 
				"           ,[remark]\r\n" + 
				"           ,[assetSpecificText]\r\n" + 
				"           ,[item_desc]\r\n" + 
				"           ,[model]\r\n" + 
				"           ,[serialNo]\r\n" + 
				"           ,[inspectedBy]\r\n" + 
				"           ,[recieptNo]\r\n" + 
				"           ,[checkedBy]\r\n" + 
				"           ,[maker])\r\n" + 
				"     VALUES\r\n" + 
				"           (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, stock.getRecievingCode());
			pst.setInt(2,stock.getSubCategoryCode());
			pst.setInt(3,stock.getUnitMeasurement());
			pst.setFloat(4, stock.getUnitPrice());
			pst.setInt(5, stock.getQuantity());
			pst.setInt(6, stock.getPurchaseId());
			pst.setString(7, stock.getPurchaseDate());
			pst.setInt(8, stock.getSupplierId());
			pst.setString(9, stock.getComment());
			pst.setString(10, stock.getAssetSpec());
			pst.setString(11,stock.getItem());
			pst.setString(12,stock.getModel());
			pst.setString(13,stock.getSerialNo());
			pst.setString(14, stock.getInspectedBy());
			pst.setString(15, stock.getReieptNo());
			pst.setString(16, stock.getCheckedBy());
			pst.setString(17, maker);
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean edit(AssetClass assetClass) {
		
		return false;
	}
	
	public boolean update(AssetClass assetClass) {
		String squery = "update assetClass set assetClassCode = ?, assetClassDesc = ? where id = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, assetClass.getAssetClassCode());
			pst.setString(2, assetClass.getAssetClassDesc());
			pst.setInt(3, assetClass.getId());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean delete(String rc) {
		String squery = "delete from famsStock where receivingCode = ? and authStatus != 'AR'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, rc);
			if(pst.executeUpdate() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Stock> fetch() {
		List<Stock> stockList = new ArrayList<>();
		String squery = "select receivingCode, sum(quantity) as 'total','unathorized' as authStatus from famsStock where authStatus = 'U' group by receivingCode";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				/*stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));*/
				stc.setRecievingCode(rs.getString(1));
				stc.setQuantity(rs.getInt(2));
				stc.setStatus(rs.getString(3));
				/*stc.setMaker(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatDesc());
				stc.setChecker(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatCode());*/
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public List<Stock> fetchByRecCode(String rc) {
		List<Stock> stockList = new ArrayList<>();
		String squery = "select receivingCode, sum(quantity) as 'total',authStatus  from famsStock where (receivingCode like '"+rc+"%' or orderId like '"+rc+"') and (authStatus = 'AR' or authStatus = 'U' or authStatus = 'R') group by receivingCode,authStatus";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				/*stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));*/
				stc.setRecievingCode(rs.getString(1));
				stc.setQuantity(rs.getInt(2));
				if(rs.getString(3).equals("U")) {
					stc.setStatus("Unathorized");
				}else {
					stc.setStatus("Authorized");
				}
				/*stc.setMaker(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatDesc());
				stc.setChecker(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatCode());*/
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public List<Stock> fetchUnathorizedIssuance() {
		List<Stock> stockList = new ArrayList<>();
		String squery2 = "SELECT issueref,count(quantity) as total,'Unauthorized' as authStatus from famsStock where  authStatus = 'UI' group by issueref";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery2);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				/*stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setIssueRef(rs.getString("issueref"));*/
				stc.setIssueRef(rs.getString(1));
				stc.setQuantity(rs.getInt(2));
				stc.setStatus(rs.getString(3));
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public List<Stock> searchByIssueRef(String srchC) {
		List<Stock> stockList = new ArrayList<>();
		String squery = "SELECT distinct(issueRef)\r\n" + 
				"	  , [id],[receivingCode] ,[subCategory] ,[unitMeasurement] ,[unitPrice] ,[quantity] ,[orderId] ,[purchasedDate] ,[supplierId] ,[remark]\r\n" + 
				"      ,[serialNo] ,[assetSpecificText]    ,[recievedBy]    ,[status]    ,[authStatus]    ,[issuedTo]    ,[sysGenTag]    ,[item_desc]    ,[model] ,[createdAt]\r\n" + 
				"      ,[maker] ,[checker] ,[brnstatus]\r\n" + 
				"  FROM [FAMS].[dbo].[famsStock] where (authStatus = 'UI' or authStatus = 'AI' or authStatus = 'R') and issueref like '"+srchC+"%' group by issueref,[id] ,[receivingCode],[subCategory] ,[unitMeasurement] ,[unitPrice] ,[quantity] ,[orderId]\r\n" + 
				"      ,[purchasedDate] ,[supplierId],[remark],[serialNo],[assetSpecificText] ,[recievedBy],[status],[authStatus],[issuedTo],[sysGenTag]\r\n" + 
				"      ,[item_desc] ,[model]  ,[createdAt] ,[maker] ,[checker] ,[brnstatus]";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setIssueRef(rs.getString("issueref"));
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public List<Stock> fetchUnathorizedReturn() {
		List<Stock> stockList = new ArrayList<>();
		String squery = "select * from famsStock where authStatus = 'UR'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatDesc());
				stc.setItem(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatCode());
				stc.setRecievingCode(rs.getString("receivingCode"));
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public List<Stock> fetchUnathorizedDisposal() {
		List<Stock> stockList = new ArrayList<>();
		String squery = "select * from famsStock where authStatus = 'UDIS'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatDesc());
				stc.setItem(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatCode());
				stc.setRecievingCode(rs.getString("receivingCode"));
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public List<Stock> fetchUnathorizedTransfer() {
		List<Stock> stockList = new ArrayList<>();
		String squery = "select * from famsStock where authStatus = 'UTRN'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getSubCatDesc());
				stc.setRecievingCode(rs.getString("receivingCode"));
				stockList.add(stc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockList;
	}
	public Stock fetchOne(int id) {
		String squery = "select * from famsStock where id = ?";
		System.out.println("stockid is "+id);
		Stock stc = new Stock();
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1,id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSubCategoryCode(Integer.parseInt(rs.getString("subCategory")));
				stc.setUnitPrice(rs.getFloat("unitPrice"));
				stc.setTotalPrice(rs.getInt("quantity")*rs.getFloat("unitPrice"));
				stc.setQuantity(rs.getInt("quantity"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setCreatedAt(rs.getString("createdAt"));
				stc.setAuthStatus(rs.getString("authStatus"));
				stc.setMaker(rs.getString("maker"));
				stc.setChecker(rs.getString("checker"));
				stc.setBrnstatus(rs.getString("brnstatus"));
				stc.setInspectedBy(rs.getString("inspectedBy"));
				stc.setCheckedBy(rs.getString("checkedBy"));
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stc;
	}
	public Stock fetchByReceivingCode(String rc) {
		String squery = "select * from famsStock where receivingCode = ?";
		String squery1 = "select sum(quantity) as 'total' from famsStock where receivingCode = ?";
		System.out.println("stockid is "+rc);
		Stock stc = new Stock();
		PreparedStatement pst,pst2;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst2 = dbConn.connection.prepareStatement(squery1);
			pst.setString(1,rc);
			pst2.setString(1,rc);
			ResultSet rs = pst.executeQuery();
			ResultSet rs2 = pst2.executeQuery();
			System.out.println("sample total quantity is 0");
			int totalquantity = 0;
			while(rs2.next())
			{
				totalquantity = rs2.getInt(1);	
			}
			System.out.println("The total quantity is"+totalquantity);
			
			while(rs.next())
			{
				
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSubCategoryCode(Integer.parseInt(rs.getString("subCategory")));
				stc.setFcurrency1(formatCurrency(rs.getFloat("unitPrice")));
				stc.setFcurrency2(formatCurrency(totalquantity*rs.getFloat("unitPrice")));
				stc.setQuantity(totalquantity);
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setCreatedAt(rs.getString("createdAt"));
				stc.setAuthStatus(rs.getString("authStatus"));
				stc.setMaker(rs.getString("maker"));
				stc.setChecker(rs.getString("checker"));
				stc.setBrnstatus(rs.getString("brnstatus"));
				stc.setInspectedBy(rs.getString("inspectedBy"));
				stc.setCheckedBy(rs.getString("checkedBy"));
				stc.setRejection_reaon(rs.getString("rejection_comment"));
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stc;
	}
	public Stock fetchByIssueref(String issueref) {
		String squery = "select * from famsStock where issueref = ?";
		String squery1 = "select count(quantity) as total from famsStock where issueref = ?";
		System.out.println("Issueref is "+issueref);
		Stock stc = new Stock();
		PreparedStatement pst,pst1;
		int totaquantity = 0;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst1 = dbConn.connection.prepareStatement(squery1);
			pst.setString(1,issueref);
			pst1.setString(1, issueref);
			ResultSet rs = pst.executeQuery();
			ResultSet rs1 = pst1.executeQuery();
			while(rs1.next()) {
				totaquantity = rs1.getInt(1);
			}
			
			while(rs.next())
			{
				
				stc.setId(rs.getInt("id"));
				stc.setIssueRef(issueref);
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSubCategoryCode(Integer.parseInt(rs.getString("subCategory")));
				stc.setUnitPrice(rs.getFloat("unitPrice"));
				stc.setTotalPrice(totaquantity*rs.getFloat("unitPrice"));
				stc.setQuantity(totaquantity);
				stc.setFcurrency1(formatCurrency(rs.getFloat("unitPrice")));
				stc.setFcurrency2(formatCurrency(totaquantity*rs.getFloat("unitPrice")));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setCreatedAt(rs.getString("createdAt"));
				stc.setAuthStatus(rs.getString("authStatus"));
				stc.setMaker(rs.getString("maker"));
				stc.setChecker(rs.getString("checker"));
				stc.setBrnstatus(rs.getString("brnstatus"));
				stc.setInspectedBy(rs.getString("inspectedBy"));
				stc.setCheckedBy(rs.getString("checkedBy"));
				stc.setReqNo(rs.getString("requisitionNo"));
				stc.setRejection_reaon(rs.getString("rejection_comment"));
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stc;
	}
	
	public int findUnregisteredItem(int poNum) {
		String squery = "SELECT quantity- (SELECT  CASE\r\n" + 
				"				WHEN EXISTS (SELECT 1 FROM purchaseOrders p  WHERE p.id = ?) THEN COALESCE(SUM(c.quantity),0)\r\n" + 
				"				ELSE 0\r\n" + 
				"			END AS Result \r\n" + 
				"				FROM famsStock c  WHERE c.orderId=? and c.serialNo != '') as remItem  , COALESCE(((select (count(serialNo)) from famsStock \r\n" + 
				"				inner join purchaseOrders on famsStock.orderId = purchaseOrders.id \r\n" + 
				"				where serialNo != ''  and PurchaseOrders.id = ?\r\n" + 
				"			 group by PurchaseOrders.quantity)),0)  FROM PurchaseOrders WHERE id= ?";
		int unregisteredItems = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, poNum);
			pst.setInt(2,poNum);
			pst.setInt(3, poNum);
			pst.setInt(4,poNum);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				unregisteredItems = Math.abs(rs.getInt("remItem"));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return unregisteredItems;
	}
	
	public int findBySubCat(int subCatId){
		String squery = "select top 1 receivingCode from famsStock where subCategory = ? and status = 'AVAILABLE' order by createdAt asc" + 
				"";
		int rc = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, subCatId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int idd = rs.getInt("id");
				rc = rs.getInt("receivingCode");
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return rc;
	}
	//fetch the first inserted serial no from the given recieving code
	public List<Asset> fetchSerial(int subcat, int qty,int fetchMode) {
		System.out.println("we recieved subcatcode "+subcat);
		System.out.println("we recieved qty "+qty);
		String test = "";
		switch(fetchMode) {
		case 0 :
			test = "select TOP "+qty+" * from famsStock where subCategory = ?  and status = 'AVAILABLE' and authStatus= 'AR' order by createdAt asc";
			break;
		case 1 :
			test = "select TOP "+qty+" * from famsStock where subCategory = ?  and status = 'AVAILABLE' and authStatus= 'AR' order by createdAt desc";
			break;
		case 2 :
			test = "select * from famsStock where subCategory = ?  and status = 'AVAILABLE' and authStatus= 'AR'";
			break;
		}
		String squery = "select TOP 1 serialNo from famsStock where receivingCode = ? and status = 'available' order by createdAt Asc";
		List<Asset> listSerials = new ArrayList<>();
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(test);
			pst.setInt(1,subcat);
			//pst.setInt(2, subcat);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{

				Asset ast = new Asset();
				//int idd = rs.getInt("id");
				String serial = rs.getString("serialNo");
				System.out.println("serials --"+serial);
				ast.setSerialNum(serial);
				ast.setRemark(rs.getString("assetSpecificText"));
				listSerials.add(ast);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return listSerials;
	}
	public String fethcTagNumber() {
		String squery = "select top 1 LEFT(CONVERT(VARCHAR(255), NEWID()), 6) + '-' + SUBSTRING(CONVERT(VARCHAR(255), NEWID()), 7, 6) as 'sysGenTag' from famsStock";
		String sysGenTag = "";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			//pst.setInt(1, rc);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int idd = rs.getInt("id");
				sysGenTag = rs.getString("sysGenTag");
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return sysGenTag;
	}
	public List<Asset> fetchTags(int size,String subcat,String brnCode) {
		System.out.println("The SubCat to be issued id: "+subcat);
		String squery = "select top "+size+" LEFT(CONVERT(VARCHAR(255), NEWID()), 6) + '-' + SUBSTRING(CONVERT(VARCHAR(255), NEWID()), 7, 2) as 'sysGenTag' from sys.objects";
		List<Asset> tags = new ArrayList<>();
		PreparedStatement pst;
		//find the year
		LocalDate date = LocalDate.now();
		int year = date.getYear();
		try {
			pst = dbConn.connection.prepareStatement(squery);
			//pst.setInt(1, rc);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int idd = rs.getInt("id");
				Asset ast = new Asset();
				//String sysGenTag = rs.getString("sysGenTag");
				String sysGenTag = "SB/"+subcat+"/*/"+brnCode+"/"+year;
				ast.setTagNumber(sysGenTag);
				
				tags.add(ast);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return tags;
	}
	public String fetchIssueRef() {
		String squery = "select top 1 LEFT(CONVERT(VARCHAR(255), NEWID()), 6) as ref ";
		String ref="";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				ref = rs.getString("ref");				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return ref;
	}
	public Employee fetchEmployeeData(int empId) {
		String squery = "select employee.empId,employee.empName,branch.branchCode,branchName,department.deptName from employee\r\n" + 
				"inner join branch on employee.empBranch = branch.branchId\r\n" + 
				"inner join department on employee.empDept = department.deptId where empId = ?";
		String squery3 = "SELECT hremployees.Employeeid,  concat(FirstName,' ',middlename,' ',lastname) as empname,brachcode, managerid,managementirectorate.MDirectorate AS 'departmentname' FROM\r\n" + 
				"  hremployees INNER JOIN managementirectorate ON hremployees.managerid = managementirectorate.MDirectorateID WHERE hremployees.Employeeid = ?";
		Employee emp = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery3);
			pst.setInt(1,empId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				emp = new Employee();
				/*emp.setBranch(rs.getString("branchCode") +"("+rs.getString("branchName") + ")");
				emp.setFullName(rs.getString("empName"));
				emp.setDept(rs.getString("deptName"));
				emp.setId(rs.getInt("empId"));*/
				emp.setBranch(rs.getString(3));
				emp.setFullName(rs.getString(2));
				emp.setDept(rs.getString(5));
				emp.setId(rs.getInt(1));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return emp;
	}
	public Employee fetchEmployeeDataOnIssue(int empId,String brncode) {
		String squery = "select employee.empId,employee.empName,branch.branchCode,branchName,department.deptName from employee\r\n" + 
				"inner join branch on employee.empBranch = branch.branchId\r\n" + 
				"inner join department on employee.empDept = department.deptId where employee.empId =  ? and branch.branchCode = ?";
		String squery3 = "SELECT hremployees.Employeeid,  concat(FirstName,' ',middlename,' ',lastname) as empname,brachcode, managerid,managementirectorate.MDirectorate AS 'departmentname' FROM\r\n" + 
				"hremployees INNER JOIN managementirectorate ON hremployees.managerid = managementirectorate.MDirectorateID WHERE hremployees.Employeeid = ? and brachcode = ?";
		Employee emp = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery3);
			pst.setInt(1,empId);
			pst.setString(2, brncode);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				emp = new Employee();
				/*emp.setBranch(rs.getString("branchCode") +"("+rs.getString("branchName") + ")");
				emp.setFullName(rs.getString("empName"));
				emp.setDept(rs.getString("deptName"));
				emp.setId(rs.getInt("empId"));*/
				emp.setBranch(rs.getString(3));
				emp.setPosition(rs.getString(4));
				emp.setFullName(rs.getString(2));
				emp.setDept(rs.getString(5));
				emp.setId(rs.getInt(1));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return emp;
	}
	
	public boolean issueAssetWithSerialNo(Stock stock,String issueRef,String whoami) {
		String squery = "update famsStock set status = 'ISSUED', issuedTo=?,authStatus='U', sysGenTag=? where subCategory = ? and receivingCode = ? and serialNo = ?";
		String ssquery = "update famsStock set requisitionNo =?, createdAt = current_timestamp, status = 'ISSUED',issuedTo=?,authStatus='UI', sysGenTag=?, issueref='"+issueRef+"',maker='"+whoami+"',checker='' where subCategory = ? and serialNo = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(ssquery);
			//pst.setString(1, stock.getAssetSpec());
			pst.setString(1, stock.getReqNo());
			pst.setInt(2,stock.getIssuedTo());
			pst.setString(3, stock.getSysGeneratedTag());
			pst.setInt(4, stock.getId());
			//pst.setString(5, stock.getRecievingCode());
			pst.setString(5, stock.getSerialNo());
			
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean issueAssetWithNOSerialNo(Stock stock,String issueref,int qty) {
		//System.out.println("The difference is"+(ta-ti));
		String squery = "insert INTO [dbo].[famsStock]  \r\n" + 
				"				      (  \r\n" + 
				"					  [receivingCode]  \r\n" + 
				"				      ,[subCategory]  \r\n" + 
				"				      ,[unitMeasurement]  \r\n" + 
				"				      ,[unitPrice]  \r\n" + 
				"				      ,[quantity]  \r\n" + 
				"				      ,[orderId]  \r\n" + 
				"				      ,[purchasedDate]  \r\n" + 
				"				      ,[supplierId]  \r\n" + 
				"				      ,[remark]  \r\n" + 
				"				      ,[recieptNo]  \r\n" + 
				"				      ,[serialNo]  \r\n" + 
				"				      ,[inspectedBy]  \r\n" + 
				"				      ,[checkedBy]  \r\n" + 
				"				      ,[assetSpecificText]  \r\n" + 
				"				      ,[recievedBy]  \r\n" + 
				"				      ,[createdAt]  \r\n" + 
				"				      ,[maker]  \r\n" + 
				"				      ,[checker]  \r\n" + 
				"				      ,[status]  \r\n" + 
				"				      ,[authStatus]  \r\n" + 
				"				      ,[requisitionNo]  \r\n" + 
				"				      ,[issuedTo]  \r\n" + 
				"				      ,[issueref]  \r\n" + 
				"				      ,[sysGenTag]  \r\n" + 
				"					  )  \r\n" + 
				"				SELECT top 1   \r\n" + 
				"				       [receivingCode]  \r\n" + 
				"				      ,[subCategory]  \r\n" + 
				"				      ,[unitMeasurement]  \r\n" + 
				"				      ,[unitPrice]  \r\n" + 
				"				      ,?  \r\n" + 
				"				      ,[orderId]  \r\n" + 
				"				      ,[purchasedDate]  \r\n" + 
				"				      ,[supplierId]  \r\n" + 
				"				      ,[remark]  \r\n" + 
				"				      ,[recieptNo]  \r\n" + 
				"				      ,[serialNo]  \r\n" + 
				"				      ,[inspectedBy]  \r\n" + 
				"				      ,[checkedBy]  \r\n" + 
				"				      ,[assetSpecificText]  \r\n" + 
				"				      ,[recievedBy]  \r\n" + 
				"				      , current_timestamp \r\n" + 
				"				      ,?  \r\n" + 
				"				      ,''  \r\n" + 
				"				      ,'ISSUED'  \r\n" + 
				"				      ,'UI'  \r\n" + 
				"				      ,?  \r\n" + 
				"				      ,? \r\n" + 
				"				      ,'"+issueref+"'\r\n" + 
				"				      ,?  \r\n" + 
				"				  FROM [dbo].[famsStock]  \r\n" + 
				"				  where (select ass.ST1-ass.ST2 from\r\n" + 
				"(SELECT subCategory as 'diff',\r\n" + 
				"SUM(CASE WHEN (STATUS = 'AVAILABLE' and authStatus = 'AR') or (STATUS = 'RETURNED' and authStatus = 'ART')   THEN quantity ELSE 0 END ) AS ST1,\r\n" + 
				"SUM(CASE WHEN (STATUS = 'ISSUED' and authStatus in ('AI')) THEN quantity ELSE 0 END ) AS ST2\r\n" + 
				"FROM \r\n" + 
				"DBO.famsStock where serialNo = 'NONSERIAL' and subCategory = ?\r\n" + 
				"GROUP BY subCategory ) as ass)>0 and subCategory =?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, qty);
			pst.setString(2, stock.getMaker());
			pst.setString(3, stock.getReqNo());
			pst.setInt(4,stock.getIssuedTo());
			int ta = totalAvilable(stock.getId());
			int ti = totalissued(stock.getId());
			int diff = ta-ti;
			pst.setString(5, stock.getSysGeneratedTag());
			pst.setInt(6, stock.getId());
			pst.setInt(7, stock.getId());
			if(pst.executeUpdate() > 0) {
				//System.out.println("The difference is"+(diff));
				return true;
			}else {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean issueAssetWithCheque(Stock stock,String issueref,int qty) {
		//System.out.println("The difference is"+(ta-ti));
		String squery = "insert INTO [dbo].[famsStock]  \r\n" + 
				"				      (  \r\n" + 
				"					  [receivingCode]  \r\n" + 
				"				      ,[subCategory]  \r\n" + 
				"				      ,[unitMeasurement]  \r\n" + 
				"				      ,[unitPrice]  \r\n" + 
				"				      ,[quantity]  \r\n" + 
				"				      ,[orderId]  \r\n" + 
				"				      ,[purchasedDate]  \r\n" + 
				"				      ,[supplierId]  \r\n" + 
				"				      ,[remark]  \r\n" + 
				"				      ,[recieptNo]  \r\n" + 
				"				      ,[serialNo]  \r\n" + 
				"				      ,[inspectedBy]  \r\n" + 
				"				      ,[checkedBy]  \r\n" + 
				"				      ,[assetSpecificText]  \r\n" + 
				"				      ,[recievedBy]  \r\n" + 
				"				      ,[createdAt]  \r\n" + 
				"				      ,[maker]  \r\n" + 
				"				      ,[checker]  \r\n" + 
				"				      ,[status]  \r\n" + 
				"				      ,[authStatus]  \r\n" + 
				"				      ,[requisitionNo]  \r\n" + 
				"				      ,[issuedTo]  \r\n" + 
				"				      ,[issueref]  \r\n" + 
				"				      ,[sysGenTag]  \r\n" + 
				"					  )  \r\n" + 
				"				SELECT top 1   \r\n" + 
				"				       [receivingCode]  \r\n" + 
				"				      ,[subCategory]  \r\n" + 
				"				      ,[unitMeasurement]  \r\n" + 
				"				      ,[unitPrice]  \r\n" + 
				"				      ,?  \r\n" + 
				"				      ,[orderId]  \r\n" + 
				"				      ,[purchasedDate]  \r\n" + 
				"				      ,[supplierId]  \r\n" + 
				"				      ,[remark]  \r\n" + 
				"				      ,[recieptNo]  \r\n" + 
				"				      ,[serialNo]  \r\n" + 
				"				      ,[inspectedBy]  \r\n" + 
				"				      ,[checkedBy]  \r\n" + 
				"				      ,'"+stock.getAssetSpec()+"'  \r\n" + 
				"				      ,[recievedBy]  \r\n" + 
				"				      , current_timestamp \r\n" + 
				"				      ,?  \r\n" + 
				"				      ,''  \r\n" + 
				"				      ,'ISSUED'  \r\n" + 
				"				      ,'UI'  \r\n" + 
				"				      ,?  \r\n" + 
				"				      ,? \r\n" + 
				"				      ,'"+issueref+"'\r\n" + 
				"				      ,?  \r\n" + 
				"				  FROM [dbo].[famsStock]  \r\n" + 
				"				  where (select ass.ST1-ass.ST2 from\r\n" + 
				"(SELECT subCategory as 'diff',\r\n" + 
				"SUM(CASE WHEN (STATUS = 'AVAILABLE' and authStatus = 'AR') or (STATUS = 'RETURNED' and authStatus = 'ART')   THEN quantity ELSE 0 END ) AS ST1,\r\n" + 
				"SUM(CASE WHEN (STATUS = 'ISSUED' and authStatus in ('AI')) THEN quantity ELSE 0 END ) AS ST2\r\n" + 
				"FROM \r\n" + 
				"DBO.famsStock where serialNo = 'NONSERIAL' and subCategory = ?\r\n" + 
				"GROUP BY subCategory ) as ass)>0 and subCategory =?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, qty);
			pst.setString(2, stock.getMaker());
			pst.setString(3, stock.getReqNo());
			pst.setInt(4,stock.getIssuedTo());
			int ta = totalAvilable(stock.getId());
			int ti = totalissued(stock.getId());
			int diff = ta-ti;
			pst.setString(5, stock.getSysGeneratedTag());
			pst.setInt(6, stock.getId());
			pst.setInt(7, stock.getId());
			if(pst.executeUpdate() > 0) {
				//System.out.println("The difference is"+(diff));
				return true;
			}else {
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean issueAssetWithNoSerialNo(Stock stock) {
		String squery = "update famsStock set status = 'ISSUED', assetSpecificText = ?,issuedTo=?,authStatus='U', sysGenTag=? where subCategory = ? and receivingCode = ? and serialNo = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, stock.getAssetSpec());
			pst.setInt(2,stock.getIssuedTo());
			pst.setString(3, stock.getSysGeneratedTag());
			pst.setInt(4, stock.getId());
			pst.setString(5, stock.getRecievingCode());
			pst.setString(6, stock.getSerialNo());
			
			//pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public int totalAvilable(int sucat) {
		String squery = "select  sum(quantity) as 'totavail' from famsStock where serialNo = 'NONSERIAL' and status = 'AVAILABLE' and  subCategory = ? and authStatus in ('A','AR')";
		int total = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, sucat);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				total = rs.getInt("totavail");
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return total;
	}
	public int totalissued(int sucat) {
		String squery = "select COALESCE(sum(quantity),0) as'totalissu' from famsStock where serialNo = 'NONSERIAL' and status = 'ISSUED' and  subCategory = ? and authStatus in ('A','AI','ATRN','AR')";
		int total = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, sucat);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				total = rs.getInt("totalissu");
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return total;
	}
	
	
	
	///////////////////////////////////////////////////
	public int totalAsset() {
		String squery = "select sum(quantity) as 'total'  from famsStock where  authStatus in ('A','AR','AI','ATRN')";
		int total = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				total = rs.getInt("total");
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return total;
	}
	public int totalAssignedAsset() {
		String squery = "select sum(quantity) as 'total' from famsStock where status = 'ISSUED' and authStatus in ('A','AR','AI','ATRN')";
		int total = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				total = rs.getInt("total");
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return total;
	}
	public int totalInstockAset() {
		String squery = "select sum(quantity) as 'total' from famsStock where status = 'AVAILABLE' and authStatus in ('A','AR','AI','ATRN')";
		int total = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				total = rs.getInt("total");
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return total;
	}
	
	public List<Stock> assetAssignedToCurrentUser(int userId,int subcat){
		String squery = "select * from famsStock where issuedTo = ?";
		String squery2 = "select famsStock.*,assetSubCat.subCatDesc from famsStock INNER JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
				"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"WHERE hremployees.brachcode IN (SELECT hremployees.brachcode FROM users INNER JOIN hremployees ON users.empId = hremployees.Employeeid \r\n" + 
				"WHERE users.userId = ?) AND famsStock.authStatus in ('AI','ATRN') and famsStock.subCategory = ?";
		List<Stock> stockWithCurrentUser = new ArrayList<>();
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery2);
			pst.setInt(1, userId);
			pst.setInt(2, subcat);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setId(rs.getInt("id"));
				stc.setUnitMeasurement(rs.getInt("unitMeasurement"));
				stc.setSupplierId(rs.getInt("subCategory"));
				stc.setTotalPrice(rs.getFloat("unitPrice"));
				stc.setSerialNo(rs.getString("serialNo"));
				stc.setSysGeneratedTag(rs.getString("sysGenTag"));
				stc.setIssuedTo(rs.getInt("issuedTo"));
				stc.setStatus(rs.getString("status"));
				stc.setAssetSpec(rs.getString("assetSpecificText"));
				stc.setItem(rs.getString("item_desc"));
				stc.setRecievingCode(rs.getString("receivingCode"));
				stc.setBrnstatus(rs.getString("brnstatus"));
				stc.setDecription(rs.getString("subCatDesc"));
				
				stockWithCurrentUser.add(stc);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockWithCurrentUser;
	}
	public List<Stock> loadDistinctSubCatForBranch(int userId)
	{
		String squery = "SELECT famsStock.subCategory, assetSubCat.subCatDesc FROM famsStock\r\n" + 
				"INNER JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
				"INNER JOIN  assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"WHERE hremployees.brachcode IN (SELECT hremployees.brachcode FROM users\r\n" + 
				"WHERE users.userId = ?) AND famsStock.authStatus in ('AI','ATRN') GROUP BY famsStock.subCategory,assetSubCat.subCatDesc";
		PreparedStatement pst ;
		List<Stock> stockList = new ArrayList<>();
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Stock stc = new Stock();
				stc.setSubCategoryCode(rs.getInt("subCategory"));
				stc.setDecription(rs.getString("subCatDesc"));
				
				stockList.add(stc);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return stockList;
	}
	public boolean updateBrnStatus(String status, int id) {
		String squery = "update famsStock set brnstatus = ? where id = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, status);
			pst.setInt(2, id);
			if(pst.executeUpdate() > 0) {
				return true;
			}else
			{
				return false;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public List<Stock> issueVoucher(String issueRef){
		float total = 0;
		String squery2 = "select famsStock.subCategory,famsStock.maker,assetSubCat.subCatCode,famsStock.requisitionNo,FORMAT(famsStock.createdAt, 'dd/MM/yyyy') as dd, famsStock.checker, assetSubCat.subCatDesc,famsStock.issueref,   \r\n" + 
				"				famsStock.unitPrice as unitPrice,count(famsStock.quantity) as quantity,branch.branchName,branch.branchCode,  \r\n" + 
				"				concat(hremployees.FirstName,' ',hremployees.middlename,' ',hremployees.lastname)  \r\n" + 
				"				as 'empName',assetUnitf.unitName from famsStock   \r\n" + 
				"				inner join hremployees on famsStock.issuedTo = hremployees.Employeeid  \r\n" + 
				"				inner join branch on hremployees.empBranch =  branch.branchId  \r\n" + 
				"				inner join assetSubCat on famsStock.subCategory = assetSubCat.id    \r\n" + 
				"				inner join assetUnitf on famsStock.unitMeasurement = assetUnitf.id where   \r\n" + 
				"				famsStock.issueref = '"+issueRef+"' and assetSubCat.mode IN (3,4) and famsStock.authStatus = 'AI' group by famsStock.subCategory, famsStock.requisitionNo, branch.branchName,  \r\n" + 
				"				famsStock.issueref, hremployees.FirstName,branch.branchCode,assetUnitf.unitName,  \r\n" + 
				"				assetSubCat.subCatDesc,famsStock.unitPrice ,famsStock.maker,famsStock.checker,  \r\n" + 
				"				concat(hremployees.FirstName,' ',hremployees.middlename,' ',hremployees.lastname),assetSubCat.subCatCode,FORMAT(famsStock.createdAt, 'dd/MM/yyyy')\r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"";
		List<Stock> stockWithCurrentUser = new ArrayList<>();
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery2);
			//pst.setInt(1, issueTo);
			//pst.setInt(2, subcategory);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setReqNo(rs.getString("requisitionNo"));
				stc.setCat(rs.getString("subCatDesc"));
				stc.setUnitPrice(rs.getFloat("unitPrice"));
				stc.setFcurrency1(formatCurrency(rs.getFloat("unitPrice")));
				stc.setQuantity(rs.getInt("quantity"));
				stc.setDecription(rs.getString("branchName"));
				stc.setStatus(rs.getString("branchCode"));
				stc.setItem(rs.getString("empName"));
				stc.setComment(rs.getString("unitName"));	
				stc.setIssueRef(rs.getString("issueref"));
				stc.setMaker(rs.getString("maker"));
				stc.setChecker(rs.getString("checker"));
				stc.setTotalPrice(rs.getFloat("unitPrice")*rs.getInt("quantity"));
				stc.setFcurrency2(formatCurrency(stc.getTotalPrice()));
				stc.setCreatedAt(rs.getString("dd"));
				stc.setAuthStatus(rs.getString("subCatCode"));
				total = total+stc.getTotalPrice();
				stc.setRtotoal(total);
				if(new AssetSubCatRepository().fetchOne(rs.getInt("subCategory")).getMode() == 4) {
				stc.setAssetSpec(lsserials(rs.getInt("subCategory")));	
				}else {
				stc.setAssetSpec("");	
				}
				System.out.println("the total sum of report is "+total);
				stockWithCurrentUser.add(stc);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockWithCurrentUser;
	}
	String lsserials(int subcat) {
		String squery = "select assetSpecificText from famsStock where subCategory = ?";
		String ast = "";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, subcat);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ast = rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ast;
	}
	public List<Stock> sissueVoucher(String issueRef){
		float total = 0;
		String squery2 = "select famsStock.subCategory, famsStock.maker,assetSubCat.subCatCode,famsStock.requisitionNo,FORMAT(famsStock.createdAt, 'dd/MM/yyyy') as dd, famsStock.checker, assetSubCat.subCatDesc,famsStock.issueref,   \r\n" + 
				"				famsStock.unitPrice as unitPrice,count(famsStock.quantity) as quantity,branch.branchName,branch.branchCode,  \r\n" + 
				"				concat(hremployees.FirstName,' ',hremployees.middlename,' ',hremployees.lastname)  \r\n" + 
				"				as 'empName',assetUnitf.unitName from famsStock   \r\n" + 
				"				inner join hremployees on famsStock.issuedTo = hremployees.Employeeid  \r\n" + 
				"				inner join branch on hremployees.empBranch =  branch.branchId  \r\n" + 
				"				inner join assetSubCat on famsStock.subCategory = assetSubCat.id    \r\n" + 
				"				inner join assetUnitf on famsStock.unitMeasurement = assetUnitf.id where   \r\n" + 
				"				famsStock.issueref = '"+issueRef+"' and assetSubCat.mode IN (0,1) and famsStock.authStatus = 'AI' group by famsStock.subCategory,famsStock.requisitionNo, branch.branchName,  \r\n" + 
				"				famsStock.issueref, hremployees.FirstName,branch.branchCode,assetUnitf.unitName,  \r\n" + 
				"				assetSubCat.subCatDesc,famsStock.unitPrice ,famsStock.maker,famsStock.checker,  \r\n" + 
				"				concat(hremployees.FirstName,' ',hremployees.middlename,' ',hremployees.lastname),assetSubCat.subCatCode,FORMAT(famsStock.createdAt, 'dd/MM/yyyy')\r\n" + 
				"				\r\n" + 
				"				\r\n" + 
				"";
		List<Stock> stockWithCurrentUser = new ArrayList<>();
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery2);
			//pst.setInt(1, issueTo);
			//pst.setInt(2, subcategory);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Stock stc = new Stock();
				stc.setReqNo(rs.getString("requisitionNo"));
				stc.setCat(rs.getString("subCatDesc"));
				stc.setUnitPrice(rs.getFloat("unitPrice"));
				stc.setFcurrency1(formatCurrency(rs.getFloat("unitPrice")));
				stc.setQuantity(rs.getInt("quantity"));
				stc.setDecription(rs.getString("branchName"));
				stc.setStatus(rs.getString("branchCode"));
				stc.setItem(rs.getString("empName"));
				stc.setComment(rs.getString("unitName"));	
				stc.setIssueRef(rs.getString("issueref"));
				stc.setMaker(rs.getString("maker"));
				stc.setChecker(rs.getString("checker"));
				stc.setTotalPrice(rs.getFloat("unitPrice")*rs.getInt("quantity"));
				stc.setFcurrency2(formatCurrency(stc.getTotalPrice()));
				stc.setCreatedAt(rs.getString("dd"));
				stc.setAuthStatus(rs.getString("subCatCode"));
				stc.setSysGeneratedTag(fetchTags(rs.getString("requisitionNo"),rs.getInt("subCategory"),rs.getInt("quantity")));
				//stc.setAssetSpec(fethSerials(rs.getInt("subCategory")));	
				total = total+stc.getTotalPrice();
				stc.setRtotoal(total);
				System.out.println("the total sum of report is "+total);
				stockWithCurrentUser.add(stc);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return stockWithCurrentUser;
	}
	public String fethSerials(String tag) {
		String allSerial = "";
		//int counter = 0;
		String squery = "select serialNo from famsStock where sysGenTag = ? and serialNo not in ('NONSERIAL')";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, tag);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				allSerial = allSerial + rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return allSerial;
	}
	public String fetchTags(String reqNo,int subCat,int qty) {
		String allTags = "";
		int counter = 1;
		//int counter = 0;
		String squery = "SELECT sysGenTag as tag FROM famsStock\r\n" + 
				"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"WHERE requisitionNo = ? AND mode IN (0,1) and famsStock.subCategory = '"+subCat+"'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, reqNo);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				if(counter <= qty)
				allTags = allTags +"TAG["+ rs.getString("tag")+"]  SER["+fethSerials(rs.getString("tag"))+"],";
				counter++;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return allTags;
	}
	
	public String generateReceivingCode(int id,String subCat, String poNumber) {
		String squery = "select top 1 LEFT(CONVERT(VARCHAR(255), NEWID()), 6) + '-' + SUBSTRING(CONVERT(VARCHAR(255), NEWID()), 7, 2) as 'rc' from sys.objects";
		String rc = "";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				rc = rs.getString("rc");
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return "GRN/"+subCat+"/"+rc;
	}
	
	public boolean isSerialRepeated(String serialNo, int subCat) {
		String squery = "select * from famsStock where serialNo = ? and subCategory = ?";
		boolean flag = false;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, serialNo);
			pst.setInt(2, subCat);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				flag = true;
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return flag;
	}
	public String containsDuplicates(String[] x) {
		String flag = "-1";
		HashSet<String> hs = new HashSet<>();
		for(int i = 0; i<x.length; i++) {
		if(!hs.add(x[i])){
		flag = x[i];
		}
		}
		return flag;
		}
	
	public boolean doIHaveRequestedQuanity(int subCat, int qty) {
		String squery = "select sum(quantity) as 'total' from famsStock where status = 'AVAILABLE' and authStatus = 'AR' and subCategory = ?";
		boolean flag = false;
		int eqty = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, subCat);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				eqty  = rs.getInt(1);
			}
			
			if(eqty > qty) {
				flag = true;
			}
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return flag;
	}
	public boolean validateRequisition(String req, String brncode) {
		String squery = "SELECT requisitionNo FROM famsStock\r\n" + 
				"INNER JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
				"WHERE hremployees.brachcode != ? AND requisitionNo = ?";
		boolean flag = true;
		int eqty = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, req);
			pst.setString(2,brncode);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				flag = false;
			}
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return flag;
	}
	
	public  String formatCurrency(float number) {
		// create a decimal format object with the desired pattern
		DecimalFormat df = new DecimalFormat("#,###.00");
		// return the formatted string
		return df.format(number);
		}
}

