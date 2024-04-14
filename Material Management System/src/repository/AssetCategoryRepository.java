package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AssetCategory;
import model.AssetClass;

public class AssetCategoryRepository {
	private boolean connCreated;
	DbConnection dbConn;
	AssetClassRepository assetClassRepo = new AssetClassRepository();
	public AssetCategoryRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean save(AssetCategory assetCat) {
		String squery = "insert into assetCat(assetCatCode,assetCatDesc,assetCatMaint,assetCatLife,assetCatDepr,assetCatClass,maker) VALUES (?,?,?,?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,assetCat.getAssetCatName());
			pst.setString(2,assetCat.getAssetCatDesc());
			pst.setString(3, assetCat.getAssetCatMaint());
			pst.setString(4, assetCat.getAssetCatLife());
			pst.setInt(5, assetCat.getAssetCatCatDepr());
			pst.setInt(6, assetCat.getAssetClass());
			pst.setString(7, assetCat.getMaker());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean assetCategoryAlreadyExists(AssetCategory assetCat) {
		String squery = "select * from assetCat where assetCatCode = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareCall(squery);
			pst.setString(1, assetCat.getAssetCatName());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean update(AssetCategory assetCat) {
		String squery = "update assetCat set assetCatCode = ?,assetCatDesc=?,assetCatMaint=?,assetCatLife=?,assetCatDepr=?,assetCatClass=? where id=? and authStatus !='A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, assetCat.getAssetCatName());
			pst.setString(2, assetCat.getAssetCatDesc());
			pst.setString(3, assetCat.getAssetCatMaint());
			pst.setString(4, assetCat.getAssetCatLife());
			pst.setInt(5, assetCat.getAssetCatCatDepr());
			pst.setInt(6, assetCat.getAssetClass());
			pst.setInt(7, assetCat.getId());
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
		String squery = "delete from assetCat where id = ? and authStatus != 'A'";
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
	public List<AssetCategory> fetch() {
		List<AssetCategory> assetCatList = new ArrayList<>();
		String squery = "select * from assetCat where authStatus = 'U'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetCatCode = rs.getString("assetCatCode");
				String assetCatDesc = rs.getString("assetCatDesc");
				String assetCatMaint = rs.getString("assetCatMaint");
				int assetCatCatDepr = rs.getInt("assetCatDepr");
				String assetCatLife = rs.getString("assetCatLife");
				int assetCatClass = rs.getInt("assetCatClass");
				
				AssetClass assetClassNameHolder = assetClassRepo.fetchOne(assetCatClass);
				
				AssetCategory  assetCategory = new AssetCategory();
				assetCategory.setId(id);
				assetCategory.setAssetCatName(assetCatCode);
				assetCategory.setAssetCatDesc(assetCatDesc);
				assetCategory.setAssetCatMaint(assetCatMaint);
				assetCategory.setAssetCatLife(assetCatLife);
				assetCategory.setAssetCatCatDepr(assetCatCatDepr);
				assetCategory.setAssetClassNameHolder(new AssetClassRepository().fetchOne(assetCatClass).getAssetClassCode());
				assetCatList.add(assetCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetCatList;
	}
	public List<AssetCategory> fetchForReport() {
		List<AssetCategory> assetCatList = new ArrayList<>();
		String squery = "select * from assetCat where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetCatCode = rs.getString("assetCatCode");
				String assetCatDesc = rs.getString("assetCatDesc");
				String assetCatMaint = rs.getString("assetCatMaint");
				int assetCatCatDepr = rs.getInt("assetCatDepr");
				String assetCatLife = rs.getString("assetCatLife");
				int assetCatClass = rs.getInt("assetCatClass");
				
				AssetClass assetClassNameHolder = assetClassRepo.fetchOne(assetCatClass);
				
				AssetCategory  assetCategory = new AssetCategory();
				assetCategory.setId(id);
				assetCategory.setAssetCatName(assetCatCode);
				assetCategory.setAssetCatDesc(assetCatDesc);
				assetCategory.setAssetCatMaint(assetCatMaint);
				assetCategory.setAssetCatLife(assetCatLife);
				assetCategory.setAssetCatCatDepr(assetCatCatDepr);
				assetCategory.setAssetClassNameHolder(new AssetClassRepository().fetchOne(assetCatClass).getAssetClassCode());
				assetCatList.add(assetCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetCatList;
	}
	public List<AssetCategory> fetchByCatCode(String assetCatCod) {
		List<AssetCategory> assetCatList = new ArrayList<>();
		String squery = "select * from assetCat where assetCatCode like '"+assetCatCod+"%'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetCatCode = rs.getString("assetCatCode");
				String assetCatDesc = rs.getString("assetCatDesc");
				String assetCatMaint = rs.getString("assetCatMaint");
				int assetCatCatDepr = rs.getInt("assetCatDepr");
				String assetCatLife = rs.getString("assetCatLife");
				int assetCatClass = rs.getInt("assetCatClass");
				
				AssetClass assetClassNameHolder = assetClassRepo.fetchOne(assetCatClass);
				
				AssetCategory  assetCategory = new AssetCategory();
				assetCategory.setId(id);
				assetCategory.setAssetCatName(assetCatCode);
				assetCategory.setAssetCatDesc(assetCatDesc);
				assetCategory.setAssetCatMaint(assetCatMaint);
				assetCategory.setAssetCatLife(assetCatLife);
				assetCategory.setAssetCatCatDepr(assetCatCatDepr);
				assetCatList.add(assetCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetCatList;
	}
	public List<AssetCategory> fetchByAuth() {
		List<AssetCategory> assetCatList = new ArrayList<>();
		String squery = "select * from assetCat where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetCatCode = rs.getString("assetCatCode");
				String assetCatDesc = rs.getString("assetCatDesc");
				String assetCatMaint = rs.getString("assetCatMaint");
				int assetCatCatDepr = rs.getInt("assetCatDepr");
				String assetCatLife = rs.getString("assetCatLife");
				int assetCatClass = rs.getInt("assetCatClass");
				
				AssetClass assetClassNameHolder = assetClassRepo.fetchOne(assetCatClass);
				
				AssetCategory  assetCategory = new AssetCategory();
				assetCategory.setId(id);
				assetCategory.setAssetCatName(assetCatCode);
				assetCategory.setAssetCatDesc(assetCatDesc);
				assetCategory.setAssetCatMaint(assetCatMaint);
				assetCategory.setAssetCatLife(assetCatLife);
				assetCategory.setAssetCatCatDepr(assetCatCatDepr);
				assetCatList.add(assetCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetCatList;
	}
	public AssetCategory fetchOne(int id) {
		String squery = "SELECT [id]\r\n" + 
				"      ,[assetCatCode]\r\n" + 
				"      ,[assetCatDesc]\r\n" + 
				"      ,[assetCatMaint]\r\n" + 
				"      ,[assetCatLife]\r\n" + 
				"      ,[assetCatDepr]\r\n" + 
				"      ,[assetCatClass]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      , convert(varchar,  createdAt, 13) as createdAt\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[rejection_comment]\r\n" + 
				"  FROM [dbo].[assetCat] where id = ?";
		AssetCategory assetCategory = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int id = rs.getInt("id");
				String assetCatCode = rs.getString("assetCatCode");
				String assetCatDesc = rs.getString("assetCatDesc");
				String assetCatMaint = rs.getString("assetCatMaint");
				int assetCatCatDepr = rs.getInt("assetCatDepr");
				String assetCatLife = rs.getString("assetCatLife");
				int assetCatClass = rs.getInt("assetCatClass");
				
				assetCategory = new AssetCategory();
				assetCategory.setId(id);
				assetCategory.setAssetCatName(assetCatCode);
				assetCategory.setAssetCatDesc(assetCatDesc);
				assetCategory.setAssetCatMaint(assetCatMaint);
				assetCategory.setAssetCatLife(assetCatLife);
				assetCategory.setAssetCatCatDepr(assetCatCatDepr);
				assetCategory.setAssetClass(assetCatClass);
				assetCategory.setAuthStatus(rs.getString("authStatus"));
				assetCategory.setMaker(rs.getString("maker"));
				assetCategory.setChecker(rs.getString("checker"));
				assetCategory.setCreatedAt(rs.getString("createdAt"));
				AssetClassRepository ac = new AssetClassRepository();
				AssetClass acl =	ac.fetchOne(assetCatClass);
				assetCategory.setAssetClassNameHolder(new AssetClassRepository().fetchOne(assetCatClass).getAssetClassCode());
			//	assetCategory.setAssetClassNameHolder(acl.getAssetClassCode());
			    assetCategory.setDeprNameHolder(new DepreciationRepository().edit(assetCatCatDepr).getCode());
			    assetCategory.setRejection_reaon(rs.getString("rejection_comment"));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetCategory;
	}
	
	/***this method is being used by assetControllerSerrvlet************/
	public List<AssetCategory> fetchByClassId(int assetId) {
		List<AssetCategory> assetCatList = new ArrayList<>();
		String squery = "select * from assetCat where assetCatClass = ?";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, assetId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetCatCode = rs.getString("assetCatCode");
				String assetCatDesc = rs.getString("assetCatDesc");
				String assetCatMaint = rs.getString("assetCatMaint");
				int assetCatCatDepr = rs.getInt("assetCatDepr");
				String assetCatLife = rs.getString("assetCatLife");
				int assetCatClass = rs.getInt("assetCatClass");
				
				AssetClass assetClassNameHolder = assetClassRepo.fetchOne(assetCatClass);
				
				AssetCategory  assetCategory = new AssetCategory();
				assetCategory.setId(id);
				assetCategory.setAssetCatName(assetCatCode);
				assetCategory.setAssetCatDesc(assetCatDesc);
				assetCategory.setAssetCatMaint(assetCatMaint);
				assetCategory.setAssetCatLife(assetCatLife);
				assetCategory.setAssetCatCatDepr(assetCatCatDepr);
				assetCategory.setAssetClassNameHolder(assetClassNameHolder.getAssetClassCode());
				
				assetCatList.add(assetCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetCatList;
	}
}
