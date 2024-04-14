package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.jdbc.PreparedStatement;

import model.AssetClass;

public class AssetClassRepository {
	
	private boolean connCreated;
	DbConnection dbConn;
	public AssetClassRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean save(AssetClass assetClass) {
		String squery = "insert into assetClass (assetClassCode,assetClassDesc,maker) VALUES (?,?,?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, assetClass.getAssetClassCode());
			pst.setString(2, assetClass.getAssetClassDesc());
			pst.setString(3, assetClass.getMaker());
			pst.executeUpdate();
			return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean isAccestExists(AssetClass assetClass) {
		String squery = "select * from assetClass where assetClassCode = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1,assetClass.getAssetClassCode());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean update(AssetClass assetClass) {
		String squery = "update assetClass set assetClassCode = ?, assetClassDesc = ?, authStatus = 'U',checker = '' where id = ? and authStatus != 'A' and maker='"+assetClass.getMaker()+"'";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, assetClass.getAssetClassCode());
			pst.setString(2, assetClass.getAssetClassDesc());
			pst.setInt(3, assetClass.getId());
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
		String squery = "delete from assetClass where id = ? and authStatus != 'A'";
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
	public List<AssetClass> fetch() {
		List<AssetClass> assetClassList = new ArrayList<>();
		String squery = "select * from assetClass where authStatus = 'U'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetClassCode = rs.getString("assetClassCode");
				String assetClassDesc = rs.getString("assetClassDesc");
				
				AssetClass assetClass = new AssetClass();
				assetClass.setId(id);
				assetClass.setAssetClassCode(assetClassCode);
				assetClass.setAssetClassDesc(assetClassDesc);
				
				assetClassList.add(assetClass);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetClassList;
	}
	public List<AssetClass> fetchAuthorized() {
		List<AssetClass> assetClassList = new ArrayList<>();
		String squery = "select * from assetClass where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetClassCode = rs.getString("assetClassCode");
				String assetClassDesc = rs.getString("assetClassDesc");
				
				AssetClass assetClass = new AssetClass();
				assetClass.setId(id);
				assetClass.setAssetClassCode(assetClassCode);
				assetClass.setAssetClassDesc(assetClassDesc);
				
				assetClassList.add(assetClass);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetClassList;
	}
	public List<AssetClass> fetchByAuth() {
		List<AssetClass> assetClassList = new ArrayList<>();
		String squery = "select * from assetClass where authStatus = 'A'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetClassCode = rs.getString("assetClassCode");
				String assetClassDesc = rs.getString("assetClassDesc");
				
				AssetClass assetClass = new AssetClass();
				assetClass.setId(id);
				assetClass.setAssetClassCode(assetClassCode);
				assetClass.setAssetClassDesc(assetClassDesc);
				
				assetClassList.add(assetClass);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetClassList;
	}
	public AssetClass fetchOne(int id) {
		String squery = "SELECT [id]\r\n" + 
				"      ,[assetClassCode]\r\n" + 
				"      ,[assetClassDesc]\r\n" + 
				"      ,[authStatus]\r\n" + 
				"      ,convert(varchar,  createdAt, 13) as createdAt\r\n" + 
				"      ,[maker]\r\n" + 
				"      ,[checker]\r\n" + 
				"      ,[rejection_comment]\r\n" + 
				"  FROM [dbo].[assetClass] where id = ?";
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
				assetClass.setAuthStatus(rs.getString("authStatus"));
				assetClass.setMaker(rs.getString("maker"));
				assetClass.setChecker(rs.getString("checker"));
				assetClass.setCreatedAt(rs.getString("createdAt"));
				assetClass.setRejection_reaon(rs.getString("rejection_comment"));
				
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetClass;
	}
	public List<AssetClass> fetchByClassCode (String acc) {
		List<AssetClass> assetClassList = new ArrayList<>();
		String squery = "select * from assetClass where assetClassCode like '"+acc+"%'";
		PreparedStatement pst;
		try {
			pst = dbConn.connection.prepareStatement(squery);
			//pst.setString(1, acc);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				int id = rs.getInt("id");
				String assetClassCode = rs.getString("assetClassCode");
				String assetClassDesc = rs.getString("assetClassDesc");
				
				AssetClass assetClass = new AssetClass();
				assetClass.setId(id);
				assetClass.setAssetClassCode(assetClassCode);
				assetClass.setAssetClassDesc(assetClassDesc);
				
				assetClassList.add(assetClass);
			}
				
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return assetClassList;
	}
	
	
}
