package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AssetCategory;
import model.AssetSubCategory;

public class AssetSubCatRepository {

	private boolean connCreated;
	DbConnection dbConn;
	AssetCategoryRepository assetCategoryRepo = new AssetCategoryRepository();
	public AssetSubCatRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(AssetSubCategory assetSubCategory) {
		String squery = "insert into assetSubCat (subCatCode,subCatDesc,subCatCategory,mode,maker) VALUES (?,?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, assetSubCategory.getSubCatCode());
			pst.setString(2, assetSubCategory.getSubCatDesc());
			pst.setInt(3, assetSubCategory.getSubCatCategory());
			pst.setInt(4, assetSubCategory.getMode());
			pst.setString(5, assetSubCategory.getMaker());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean isSubCatExists(AssetSubCategory assetSubCat) {
		String squery = "Select * from assetSubCat where subCatCode = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, assetSubCat.getSubCatCode());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public AssetSubCategory edit(int id) {
		String squery = "SELECT [id]\r\n" + 
				"      ,[subCatCode]\r\n" + 
				"      ,[subCatDesc]\r\n" + 
				"      ,[subCatCategory]\r\n" + 
				"      ,[mode]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,convert (varchar, createdAt, 13) as createdAt\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[rejection_comment]\r\n" + 
				"  FROM [dbo].[assetSubCat] where id = ?";
		AssetSubCategory asc = new AssetSubCategory();	
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				asc.setId(rs.getInt("id"));
				asc.setSubCatCode(rs.getString("subCatCode"));
				asc.setSubCatDesc(rs.getString("subCatDesc"));
				asc.setSubCatCategory(rs.getInt("subCatCategory"));
				asc.setSubCategoryNameHolder(new AssetCategoryRepository().fetchOne(rs.getInt("subCatCategory")).getAssetCatName());
				asc.setMode(rs.getInt("mode"));
				asc.setAuthStatus(rs.getString("authStatus"));
				asc.setCreatedAt(rs.getString("createdAt"));
				asc.setMaker(rs.getString("maker"));
				asc.setChecker(rs.getString("checker"));
				asc.setRejection_reaon(rs.getString("rejection_comment"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return asc;
		
	}
	
	public boolean update(AssetSubCategory asc) {
		String squery = "UPDATE assetSubCat SET subCatCode = ?, subCatDesc = ?, subCatCategory = ?, mode = ? where id = ? and authStatus != 'A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,asc.getSubCatCode());
			pst.setString(2,asc.getSubCatDesc());
			pst.setInt(3, asc.getSubCatCategory());
			pst.setInt(4, asc.getMode());
			pst.setInt(5, asc.getId());
		//	pst.executeUpdate();
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
		String squery = "delete from assetSubCat where id = ? and authStatus != 'A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			//pst.executeUpdate();
			if(pst.executeUpdate() > 0) {
				return true;
			}else{
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<AssetSubCategory> fetch() {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select * from assetSubCat where authStatus = 'U'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public List<AssetSubCategory> fetchForIssue() {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select top 1 assetSubCat.id,assetSubCat.subCatCode,assetSubCat.subCatDesc,assetSubCat.subCatCategory,assetSubCat.mode\r\n" + 
				" from famsStock inner join \r\n" + 
				"assetSubCat on famsStock.subCategory = assetSubCat.id \r\n" + 
				"where famsStock.status = 'AVAILABLE' and famsStock.authStatus in('A', 'AR') group by assetSubCat.id,assetSubCat.subCatCode,assetSubCat.subCatDesc,assetSubCat.subCatCategory,assetSubCat.mode";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public List<AssetSubCategory> fetchBySubCatCode(String subcat) {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select * from assetSubCat where subCatCode like '"+subcat+"%'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public List<AssetSubCategory> fetchByAuth() {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select top 1 * from assetSubCat where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	public AssetSubCategory fetchOne(int idd) {
		String squery = "select * from assetSubCat where id = ?";
		AssetSubCategory assetSubCategory = new AssetSubCategory();;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, idd);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				String maker = rs.getString("maker");
				int subCatCategory = rs.getInt("subCatCategory");
				int mode = rs.getInt("mode");
				
				AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
			//	assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setMode(mode);
				assetSubCategory.setMaker(maker);
				assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategory;
	}
	
	/**********this method is used by assetClassController*************/
	public List<AssetSubCategory> fetchByCatId(int catId) {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select * from assetSubCat where subCatCategory = ?";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1,catId);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String subCatCode = rs.getString("subCatCode");
				String subCatDesc = rs.getString("subCatDesc");
				int subCatCategory = rs.getInt("subCatCategory");
				
				AssetCategory assetCateNameHolder = assetCategoryRepo.fetchOne(subCatCategory);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				assetSubCategory.setSubCatDesc(subCatDesc);
				assetSubCategory.setSubCategoryNameHolder(assetCateNameHolder.getAssetCatName());;
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	
	public List<AssetSubCategory> assetsPerSubCat() {
		List<AssetSubCategory> assetSubCategoryList = new ArrayList<>();
		String squery = "select top 1  sum(quantity) as total, subCategory  from famsStock group by subCategory order by total desc";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("total");
				int subCatID = rs.getInt("subCategory");
				String subCatCode = this.fetchOne(subCatID).getSubCatCode();
				
				System.out.println("SubcatCode "+subCatCode);
				
				AssetSubCategory assetSubCategory = new AssetSubCategory();
				assetSubCategory.setId(id);
				assetSubCategory.setSubCatCode(subCatCode);
				
				assetSubCategoryList.add(assetSubCategory);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetSubCategoryList;
	}
	
}
