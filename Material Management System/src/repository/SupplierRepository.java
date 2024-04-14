package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Supplier;
import model.Unit;

public class SupplierRepository {
	private boolean connCreated;
	DbConnection dbConn;

	public SupplierRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(Supplier supplier) {
		String squery = "INSERT INTO [dbo].[famsSupplier]\r\n" + 
				"           ([supplierName]\r\n" + 
				"           ,[contactPerson]\r\n" + 
				"           ,[contactNumber]\r\n" + 
				"           ,[email]\r\n" + 
				"           ,[address]\r\n" + 
				"           ,[zipCode]\r\n" + 
				"           ,[website]\r\n" + 
				"           ,[maker])\r\n" +
				"     VALUES\r\n" + 
				"           (?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,supplier.getSupplierName());
			pst.setString(2, supplier.getContactPerson());
			pst.setString(3, supplier.getContactNumber());
			pst.setString(4, supplier.getEmail());
			pst.setString(5,supplier.getAddress());
			pst.setString(6, supplier.getZipCode());
			pst.setString(7, supplier.getWebsite());
			pst.setString(8, supplier.getMaker());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean isSupplierExists(Supplier supplier) {
		String squery = "select * from famsSupplier where supplierName = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, supplier.getSupplierName());
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean update(Supplier supplier) {
		String squery = "update famsSupplier set supplierName = ?, contactPerson = ?, contactNumber =?,email=?,address=?,zipCode=?,website=? where id = ? and authStatus != 'A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,supplier.getSupplierName());
			pst.setString(2, supplier.getContactPerson());
			pst.setString(3, supplier.getContactNumber());
			pst.setString(4, supplier.getEmail());
			pst.setString(5,supplier.getAddress());
			pst.setString(6, supplier.getZipCode());
			pst.setString(7, supplier.getWebsite());
			pst.setInt(8, supplier.getId());
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
	public Supplier edit(int id) {
		String squery = "SELECT [id]\r\n" + 
				"      ,[supplierName]\r\n" + 
				"      ,[contactPerson]\r\n" + 
				"      ,[contactNumber]\r\n" + 
				"      ,[email]\r\n" + 
				"      ,[address]\r\n" + 
				"      ,[zipCode]\r\n" + 
				"      ,[website]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,convert(varchar, createdAt, 13) as createdAt\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[rejection_comment]\r\n" + 
				"  FROM [dbo].[famsSupplier] where id = ?";
		Supplier supp = new Supplier();	
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
			supp.setId(rs.getInt("id"));
			supp.setSupplierName(rs.getString("supplierName"));
			supp.setContactPerson(rs.getString("contactPerson"));
			supp.setContactNumber(rs.getString("contactNumber"));
			supp.setEmail(rs.getString("email"));
			supp.setAddress(rs.getString("address"));
			supp.setZipCode(rs.getString("zipCode"));
			supp.setWebsite(rs.getString("website"));
			supp.setAuthStatus(rs.getString("authStatus"));
			supp.setCreatedAt(rs.getString("createdAt"));
			supp.setMaker(rs.getString("maker"));
			supp.setChecker(rs.getString("checker"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return supp;
	}
	
	public boolean delete(int id) {
		String squery = "delete from famsSupplier where id = ? and authStatus != 'A'";
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
	public List<Supplier> fetch() {
		List<Supplier> supplierList = new ArrayList<>();
		String squery = "select * from famsSupplier where authStatus = 'U'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				Supplier supplier = new Supplier();
				supplier.setId(rs.getInt("id"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setContactPerson(rs.getString("contactPerson"));
				supplier.setContactNumber(rs.getString("contactNumber"));
				supplier.setEmail(rs.getString("email"));
				supplier.setAddress(rs.getString("address"));
				supplier.setZipCode(rs.getString("zipCode"));
				supplier.setWebsite(rs.getString("website"));
				supplier.setAuthStatus(rs.getString("authStatus"));
				
				supplierList.add(supplier);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return supplierList;
	}
	public List<Supplier> fetchSuppByAuth() {
		List<Supplier> supplierList = new ArrayList<>();
		String squery = "select * from famsSupplier where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				Supplier supplier = new Supplier();
				supplier.setId(rs.getInt("id"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setContactPerson(rs.getString("contactPerson"));
				supplier.setContactNumber(rs.getString("contactNumber"));
				supplier.setEmail(rs.getString("email"));
				supplier.setAddress(rs.getString("address"));
				supplier.setZipCode(rs.getString("zipCode"));
				supplier.setWebsite(rs.getString("website"));
				supplier.setAuthStatus(rs.getString("authStatus"));
				
				supplierList.add(supplier);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return supplierList;
	}
	public List<Supplier> fetchSupplierName(String suppname) {
		List<Supplier> supplierList = new ArrayList<>();
		String squery = "select * from famsSupplier where supplierName like '"+suppname+"%'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				Supplier supplier = new Supplier();
				supplier.setId(rs.getInt("id"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setContactPerson(rs.getString("contactPerson"));
				supplier.setContactNumber(rs.getString("contactNumber"));
				supplier.setEmail(rs.getString("email"));
				supplier.setAddress(rs.getString("address"));
				supplier.setZipCode(rs.getString("zipCode"));
				supplier.setWebsite(rs.getString("website"));
				supplier.setAuthStatus(rs.getString("authStatus"));
				
				supplierList.add(supplier);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return supplierList;
	}
	public List<Supplier> fetchByAuth() {
		List<Supplier> supplierList = new ArrayList<>();
		String squery = "select * from famsSupplier where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
			
				Supplier supplier = new Supplier();
				supplier.setId(rs.getInt("id"));
				supplier.setSupplierName(rs.getString("supplierName"));
				supplier.setContactPerson(rs.getString("contactPerson"));
				supplier.setContactNumber(rs.getString("contactNumber"));
				supplier.setEmail(rs.getString("email"));
				supplier.setAddress(rs.getString("address"));
				supplier.setZipCode(rs.getString("zipCode"));
				supplier.setWebsite(rs.getString("website"));
				supplier.setAuthStatus(rs.getString("authStatus"));
				
				supplierList.add(supplier);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return supplierList;
	}
	public Supplier fetchOne(int id) {
		String squery = "select * from famsSupplier where id = ?";
		Supplier supp = new Supplier();
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int idd = rs.getInt("id");
				supp.setSupplierName(rs.getString("supplierName"));
				supp.setContactNumber(rs.getString("contactNumber"));
				supp.setContactPerson(rs.getString("contactPerson"));
				supp.setEmail(rs.getString("email"));
				supp.setAddress(rs.getString("address"));
				supp.setZipCode(rs.getString("zipCode"));
				supp.setWebsite(rs.getString("website"));
				supp.setMaker(rs.getString("maker"));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return supp;
	}
	public int totalSupp() {
		String squery = "select count(*) as 'total' from famsSupplier";
		int totalSup = 0;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				totalSup = rs.getInt("total");
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return totalSup;
	}
	
}
