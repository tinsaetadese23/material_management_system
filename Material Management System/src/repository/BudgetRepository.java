package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Budget;
import model.Unit;

public class BudgetRepository {
	private boolean connCreated;
	DbConnection dbConn;
	public BudgetRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean save(Budget budget) {
		String squery = "INSERT INTO [dbo].[famsBudgetapproval]\r\n" + 
				"           ([budget_name]\r\n" + 
				"           ,[budget_amount]\r\n" + 
				"           ,[branchcode]\r\n" + 
				"           ,[departmentid]\r\n" + 
				"           ,[Budget_types]\r\n" + 
				"           ,[status])\r\n" + 
				"     VALUES\r\n" + 
				"           (?,?,?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, budget.getBudgetName());
			pst.setInt(2, budget.getBudgetAmount());
			pst.setString(3, budget.getBudgetType());
			pst.setString(4, budget.getBranchCode());
			pst.setString(5, budget.getDeptId());
			pst.setString(6, budget.getStatus());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean edit(Unit unit) {
		
		return false;
	}
	
	public boolean update(Unit unit) {
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
		String squery = "delete from assetUnit where id = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			pst.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public List<Unit> fetch() {
		List<Unit> unitList = new ArrayList<>();
		String squery = "select * from assetUnit";
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
