package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Unit;

public class UnitRepository {
	private boolean connCreated;
	DbConnection dbConn;
	public UnitRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(Unit unit) {
		String squery = "insert into assetUnitf(unitName, unitDesc,maker) values (?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, unit.getUnitName());
			pst.setString(2, unit.getUnitDesc());
			pst.setString(3, unit.getMaker());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean isUnitExists(Unit unit) {
		String squery  = "select * from assetUnitf where unitName = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, unit.getUnitName());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean update(Unit unit) {
		String squery = "update assetUnitf set unitName = ?, unitDesc = ? where id = ? and authStatus != 'A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, unit.getUnitName());
			pst.setString(2, unit.getUnitDesc());
			pst.setInt(3, unit.getId());
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
	public Unit edit(int id) {
		String squery = "SELECT TOP (1000) [id]\r\n" + 
				"      ,[unitName]\r\n" + 
				"      ,[unitDesc]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,convert(varchar,  createdAt, 13) as createdAt\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"  FROM assetUnitf where id = ?";
		Unit unit = new Unit();	
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
			unit.setId(rs.getInt("id"));
			unit.setUnitName(rs.getString("unitName"));
			unit.setUnitDesc(rs.getString("unitDesc"));
			unit.setAuthStatus(rs.getString("authStatus"));
			unit.setCreatedAt(rs.getString("createdAt"));
			unit.setMaker(rs.getString("maker"));
			unit.setChecker(rs.getString("checker"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return unit;
	}
	
	public boolean updateUnit(Unit unit) {
		String squery = "update AssetSubCategory set AssetSubCategoryCode = ?, AssetSubCategoryDesc = ? where id = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			//pst.setString(1, AssetSubCategory.getAssetSubCategoryCode());
			//pst.setString(2, AssetSubCategory.getAssetSubCategoryDesc());
			//pst.setInt(3, AssetSubCategory.getId());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean delete(int id) {
		String squery = "delete from assetUnitf where id = ? and authStatus != 'A'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			if(pst.executeUpdate() > 0) {
				return true;
			}else {
				return false;
			}
			//return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Unit> fetch() {
		List<Unit> unitList = new ArrayList<>();
		String squery = "select * from assetUnitf where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String unitName = rs.getString("unitName");
				String unitDesc = rs.getString("unitDesc");
				
				
				
				Unit unit = new Unit();
				unit.setId(id);
				unit.setUnitName(unitName);
				unit.setUnitDesc(unitDesc);
				
				unitList.add(unit);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return unitList;
	}
	public List<Unit> fetchUnathorized() {
		List<Unit> unitList = new ArrayList<>();
		String squery = "select * from assetUnitf where authStatus = 'U'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String unitName = rs.getString("unitName");
				String unitDesc = rs.getString("unitDesc");
				
				
				
				Unit unit = new Unit();
				unit.setId(id);
				unit.setUnitName(unitName);
				unit.setUnitDesc(unitDesc);
				
				unitList.add(unit);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return unitList;
	}
	public List<Unit> fetchByAuth() {
		List<Unit> unitList = new ArrayList<>();
		String squery = "select * from assetUnitf where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String unitName = rs.getString("unitName");
				String unitDesc = rs.getString("unitDesc");
				
				
				
				Unit unit = new Unit();
				unit.setId(id);
				unit.setUnitName(unitName);
				unit.setUnitDesc(unitDesc);
				
				unitList.add(unit);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return unitList;
	}
	public Unit fetchOne(int id) {
		String squery = "select * from AssetSubCategory where id = ?";
		Unit AssetSubCategory = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int idd = rs.getInt("id");
				String AssetSubCategoryCode = rs.getString("AssetSubCategoryCode");
				String AssetSubCategoryDesc = rs.getString("AssetSubCategoryDesc");
				
				Unit unit = new Unit();
				//AssetSubCategory.setId(id);
				//AssetSubCategory.setAssetSubCategoryCode(AssetSubCategoryCode);
				//AssetSubCategory.setAssetSubCategoryDesc(AssetSubCategoryDesc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return AssetSubCategory;
	}
	
}
