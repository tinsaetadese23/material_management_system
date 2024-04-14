package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.AssetSubCategory;
import model.PurchaseOrder;
import model.Supplier;
import model.Unit;

public class PORepository {
	private boolean connCreated;
	DbConnection dbConn;
	
	public PORepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(PurchaseOrder po) {
		String squery = "INSERT INTO [dbo].[PurchaseOrders]\r\n" + 
				"           ([description]\r\n" + 
				"           ,[purchasetypes]\r\n" + 
				"           ,[Sub_category]\r\n" + 
				"           ,[Supplierid]\r\n" + 
				"           ,[Order_Date]\r\n" + 
				"           ,[Delivery_Date]\r\n" + 
				"           ,[unit_price]\r\n" + 
				"           ,[quantity]\r\n" + 
				"           ,[unitofmeasurement]\r\n" + 
				"           ,[PurchaseOrderstatus]\r\n" + 
				"           ,[maker]\r\n" +
				"           ,[poCode])\r\n" + 
				"     VALUES\r\n" + 
				"           (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, po.getDescription());
			pst.setString(2, po.getPurchaseTypes());
			pst.setInt(3, po.getSubCategory());
			pst.setInt(4, po.getSupplier());
			pst.setString(5,po.getOrderDate());
			pst.setString(6, po.getDeliveryDate());
			pst.setFloat(7, po.getUnitPrice());
			pst.setInt(8, po.getQuantity());
			pst.setInt(9, po.getUnitMeasurement());
			pst.setString(10, po.getPurchaeOrderstatus());
			pst.setString(11,po.getMaker());
			pst.setString(12,po.getPoCode());
			
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean isPoExist(String poCode, int sub) {
		String squery = "select * from purchaseOrders where poCode = ? and sub_Category = ?\r\n" + 
				"";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, poCode);
			pst.setInt(2,sub);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean update(PurchaseOrder po) {
		String squery = "UPDATE [dbo].[PurchaseOrders]\r\n" + 
				"   SET [description] = ?\r\n" + 
				"      ,[purchasetypes] = ?\r\n" + 
				"      ,[Sub_category] = ?\r\n" + 
				"      ,[Supplierid] = ?\r\n" + 
				"      ,[Order_Date] = ?\r\n" + 
				"      ,[Delivery_Date] = ?\r\n" + 
				"      ,[unit_price] = ?\r\n" + 
				"      ,[quantity] = ?\r\n" + 
				"      ,[unitofmeasurement] = ?\r\n" + 
				"      ,[PurchaseOrderstatus] = ?\r\n" + 
				"      ,[maker] = ?\r\n" + 
				"      ,[inspectedBy] = ?\r\n" + 
				"      ,[checkedBy] = ?\r\n" + 
				"      ,[poCode] = ?\r\n" + 
				"      where id = ? and authStatus != 'A' and maker = '"+po.getMaker()+"'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, po.getDescription());
			pst.setString(2, po.getPurchaseTypes());
			pst.setInt(3, po.getSubCategory());
			pst.setInt(4, po.getSupplier());
			pst.setString(5,po.getOrderDate());
			pst.setString(6, po.getDeliveryDate());
			pst.setFloat(7, po.getUnitPrice());
			pst.setInt(8, po.getQuantity());
			pst.setInt(9, po.getUnitMeasurement());
			pst.setString(10, po.getPurchaeOrderstatus());
			pst.setString(11,po.getMaker());
			pst.setString(12, po.getInsepectedBy());
			pst.setString(13,po.getCheckedBy());			
			pst.setString(14,po.getPoCode());
			pst.setInt(15, po.getId());
			
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
	public boolean delete(int id) {
		String squery = "delete from PurchaseOrders where id  = ? and authStatus != 'A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
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
	public List<PurchaseOrder> fetch() {
		List<PurchaseOrder> poList = new ArrayList<>();
		String squery = "select PurchaseOrders.*,assetSubCat.subCatCode from PurchaseOrders\r\n" + 
				"inner join assetSubCat on PurchaseOrders.Sub_category = assetSubCat.id where PurchaseOrders.authStatus = 'U'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				PurchaseOrder po = new PurchaseOrder();
				po.setSubCat(rs.getString("subCatCode"));
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
	            po.setSubCatNameHolder(new AssetSubCatRepository().fetchOne(rs.getInt("Sub_category")).getSubCatDesc());
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
	public List<PurchaseOrder> fetchByPoCodeS(String pocode) {
		List<PurchaseOrder> poList = new ArrayList<>();
		String squery = "select PurchaseOrders.*,assetSubCat.subCatCode from PurchaseOrders\r\n" + 
				"inner join assetSubCat on PurchaseOrders.Sub_category = assetSubCat.id where poCode like '"+pocode+"%'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				PurchaseOrder po = new PurchaseOrder();
				po.setSubCat(rs.getString("subCatCode"));
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
	public PurchaseOrder fetchOne(int id) {
		String squery = "\r\n" + 
				"SELECT [id]\r\n" + 
				"      ,[description]\r\n" + 
				"      ,[purchasetypes]\r\n" + 
				"      ,[Sub_category]\r\n" + 
				"      ,[Supplierid]\r\n" + 
				"      ,convert(varchar,  Order_Date, 13) as Order_Date\r\n" + 
				"      ,convert(varchar,  Delivery_Date, 13) as Delivery_Date\r\n" + 
				"      ,[unit_price]\r\n" + 
				"      ,[quantity]\r\n" + 
				"      ,[unitofmeasurement]\r\n" + 
				"      ,[PurchaseOrderstatus]\r\n" + 
				"      ,[poCode]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[rejection_comment]\r\n" + 
				"      ,convert(varchar, createdAt, 13) as createdAt\r\n" + 
				"  FROM [dbo].[PurchaseOrders] where id =?";
		PurchaseOrder po = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				po = new PurchaseOrder();
				po.setId(rs.getInt("id"));
				po.setDescription(rs.getString("description"));
				po.setPurchaseTypes(rs.getString("purchaseTypes"));
				po.setSubCategory(rs.getInt("Sub_category"));
				po.setSupplier(rs.getInt("Supplierid"));
				po.setCreatedAt(rs.getString("createdAt"));
				po.setOrderDate(rs.getString("Order_Date"));
				po.setDeliveryDate(rs.getString("Delivery_Date"));
				po.setFcurrency1(formatCurrency(rs.getFloat("unit_price")));
				po.setUnitPrice(rs.getFloat("unit_price"));
				po.setQuantity(rs.getInt("quantity"));
				po.setUnitMeasurement(rs.getInt("unitofmeasurement"));
				po.setFcurrency2(formatCurrency(rs.getFloat("unit_price")*rs.getInt("quantity")));
				po.setTotal(rs.getFloat("unit_price")*rs.getInt("quantity"));
				po.setPurchaeOrderstatus(rs.getString("PurchaseOrderstatus"));
	            po.setPoCode(rs.getString("poCode"));
	            po.setAuthStatus(rs.getString("authStatus"));
	            po.setMaker(rs.getString("maker"));
	            po.setChecker(rs.getString("checker"));
	            po.setInsepectedBy(rs.getString("maker"));
	            po.setCheckedBy(rs.getString("maker"));
	            System.out.println("po code is" +rs.getString("poCode"));
	            po.setUnRegisteredItem(new StockRepository().findUnregisteredItem(id));
	            po.setSubCatNameHolder(new AssetSubCatRepository().fetchOne(rs.getInt("Sub_category")).getSubCatCode());
	            po.setRejection_reaon(rs.getString("rejection_comment"));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return po;
	}
	public PurchaseOrder pfetchByPoCode(int id,int sub) {
		String squery = "\r\n" + 
				"SELECT [id]\r\n" + 
				"      ,[description]\r\n" + 
				"      ,[purchasetypes]\r\n" + 
				"      ,[Sub_category]\r\n" + 
				"      ,[Supplierid]\r\n" + 
				"      ,[Order_Date]\r\n" + 
				"      ,[Delivery_Date]\r\n" + 
				"      ,[unit_price]\r\n" + 
				"      ,[quantity]\r\n" + 
				"      ,[unitofmeasurement]\r\n" + 
				"      ,[PurchaseOrderstatus]\r\n" + 
				"      ,[poCode]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[inspectedBy]\r\n" + 
				"      ,[checkedBy]\r\n" + 
				"      ,convert(varchar, createdAt, 13) as createdAt\r\n" + 
				"  FROM [dbo].[PurchaseOrders] where id =? and authStatus = 'A' and Sub_category = ?";
		PurchaseOrder po = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			pst.setInt(2, sub);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				po = new PurchaseOrder();
				po.setId(rs.getInt("id"));
				po.setDescription(rs.getString("description"));
				po.setPurchaseTypes(rs.getString("purchaseTypes"));
				po.setSubCategory(rs.getInt("Sub_category"));
				po.setSupplier(rs.getInt("Supplierid"));
				po.setCreatedAt(rs.getString("createdAt"));
				po.setOrderDate(rs.getString("Order_Date"));
				po.setDeliveryDate(rs.getString("Delivery_Date"));
				po.setUnitPrice(rs.getFloat("unit_price"));
				po.setFcurrency1(formatCurrency(rs.getFloat("unit_price")));
				po.setQuantity(rs.getInt("quantity"));
				po.setUnitMeasurement(rs.getInt("unitofmeasurement"));
				po.setTotal(rs.getFloat("unit_price")*rs.getInt("quantity"));
				po.setFcurrency2(formatCurrency(rs.getFloat("unit_price")*rs.getInt("quantity")));
	            po.setPurchaeOrderstatus(rs.getString("PurchaseOrderstatus"));
	            po.setPoCode(rs.getString("poCode"));
	            po.setAuthStatus(rs.getString("authStatus"));
	            po.setMaker(rs.getString("maker"));
	            po.setChecker(rs.getString("checker"));
	            po.setInsepectedBy(rs.getString("inspectedBy"));
	            po.setCheckedBy(rs.getString("checkedBy"));
	            System.out.println("po code is" +rs.getString("poCode"));
	            po.setUnRegisteredItem(new StockRepository().findUnregisteredItem(po.getId()));
	            
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return po;
	}
	public PurchaseOrder fetchByPoCode(String poCode,int sub) {
		String squery = "\r\n" + 
				"SELECT [id]\r\n" + 
				"      ,[description]\r\n" + 
				"      ,[purchasetypes]\r\n" + 
				"      ,[Sub_category]\r\n" + 
				"      ,[Supplierid]\r\n" + 
				"      ,[Order_Date]\r\n" + 
				"      ,[Delivery_Date]\r\n" + 
				"      ,[unit_price]\r\n" + 
				"      ,[quantity]\r\n" + 
				"      ,[unitofmeasurement]\r\n" + 
				"      ,[PurchaseOrderstatus]\r\n" + 
				"      ,[poCode]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,convert(varchar, createdAt, 13) as createdAt\r\n" + 
				"  FROM [dbo].[PurchaseOrders] where poCode =? and authStatus = 'A' and Sub_category = ?";
		PurchaseOrder po = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, poCode);
			pst.setInt(2, sub);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				po = new PurchaseOrder();
				po.setId(rs.getInt("id"));
				po.setDescription(rs.getString("description"));
				po.setPurchaseTypes(rs.getString("purchaseTypes"));
				po.setSubCategory(rs.getInt("Sub_category"));
				po.setSupplier(rs.getInt("Supplierid"));
				po.setCreatedAt(rs.getString("createdAt"));
				po.setOrderDate(rs.getString("Order_Date"));
				po.setDeliveryDate(rs.getString("Delivery_Date"));
				po.setUnitPrice(rs.getFloat("unit_price"));
				po.setFcurrency1(formatCurrency(rs.getFloat("unit_price")));
				po.setQuantity(rs.getInt("quantity"));
				po.setUnitMeasurement(rs.getInt("unitofmeasurement"));
				po.setTotal(rs.getFloat("unit_price")*rs.getInt("quantity"));
				po.setFcurrency2(formatCurrency(rs.getFloat("unit_price")*rs.getInt("quantity")));
	            po.setPurchaeOrderstatus(rs.getString("PurchaseOrderstatus"));
	            po.setPoCode(rs.getString("poCode"));
	            po.setAuthStatus(rs.getString("authStatus"));
	            po.setMaker(rs.getString("maker"));
	            po.setChecker(rs.getString("checker"));
	            po.setInsepectedBy(rs.getString("maker"));
	            po.setCheckedBy(rs.getString("maker"));
	            System.out.println("po code is" +rs.getString("poCode"));
	            po.setUnRegisteredItem(new StockRepository().findUnregisteredItem(po.getId()));
	            
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return po;
	}
	public static String formatCurrency(float number) {
		// create a decimal format object with the desired pattern
		DecimalFormat df = new DecimalFormat("#,###.00");
		// return the formatted string
		return df.format(number);
		}
}
