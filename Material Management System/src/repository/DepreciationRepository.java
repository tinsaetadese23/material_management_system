package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Asset;
import model.AssetClass;
import model.Depreciation;

public class DepreciationRepository {
	private boolean connCreated;
	DbConnection dbConn;
	
	public DepreciationRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean save(Depreciation depreciation) {
		String squery = "INSERT INTO [dbo].[assetDepreciation]\r\n" + 
				"           ([deprCode]\r\n" + 
				"           ,[deprDesc]\r\n" + 
				"           ,[deprRate]\r\n" + 
				"           ,[maker])\r\n" + 
				"     VALUES\r\n" + 
				"           (?,?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,depreciation.getCode());
			pst.setString(2, depreciation.getDescription());
			pst.setFloat(3, depreciation.getRate());
			pst.setString(4, depreciation.getMaker());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public Depreciation edit(int id) {
		String squery = "select * from assetDepreciation where id = ?";
		Depreciation depr = new Depreciation();	
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				depr.setId(rs.getInt("id"));
				depr.setCode(rs.getString("deprCode"));
				depr.setDescription(rs.getString("deprDesc"));
				depr.setRate(rs.getFloat("deprRate"));
				
			
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return depr;
		
	}
	
	public boolean update(Depreciation depreciation) {
		String squery = "update assetDepreciation set deprCode = ?, deprDesc = ?, deprRate = ? where id = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,depreciation.getCode());
			pst.setString(2,depreciation.getDescription());
			pst.setFloat(3, depreciation.getRate());
			pst.setInt(4, depreciation.getId());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean delete(int id) {
		String squery = "delete from assetDepreciation where id = ?";
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
	public List<Depreciation> fetch() {
		List<Depreciation> depreciationList = new ArrayList<>();
		String squery = "SELECT [id]\r\n" + 
				"      ,[deprCode]\r\n" + 
				"      ,[deprDesc]\r\n" + 
				"      ,[deprRate]\r\n" + 
				"  FROM [dbo].[assetDepreciation]";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String deprCode = rs.getString("deprCode");
				String deprDesc = rs.getString("deprDesc");
				float deprRate = rs.getFloat("deprRate");
				
				
				Depreciation depreciation = new Depreciation();
				depreciation.setId(id);
				depreciation.setCode(deprCode);
				depreciation.setDescription(deprDesc);
				depreciation.setRate(deprRate);
				
				depreciationList.add(depreciation);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return depreciationList;
	}
	public AssetClass fetchOne(int id) {
		String squery = "select * from assetClass where id = ?";
		AssetClass assetClass = null;
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				//int idd = rs.getInt("id");
				String assetClassCode = rs.getString("assetClassCode");
				String assetClassDesc = rs.getString("assetClassDesc");
				
				assetClass = new AssetClass();
				assetClass.setId(id);
				assetClass.setAssetClassCode(assetClassCode);
				assetClass.setAssetClassDesc(assetClassDesc);
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetClass;
	}
	
	public void closeConnToDb() {
		try {
			dbConn.connection.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}