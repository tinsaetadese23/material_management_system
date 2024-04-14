package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.AssetSubCategory;
import model.Print;

public class AuthorizationRepository {
	private boolean connCreated;
	DbConnection dbConn;
	AssetCategoryRepository assetCategoryRepo = new AssetCategoryRepository();
	public AuthorizationRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public boolean authorize(String table, int id,int action,String whoami,String rejection_comment) {
		String squery = "";
		switch(action) {
		case 0 :
			squery = "update "+table+" set authStatus = 'A', checker = ? where id = ? and authStatus = 'U' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set authStatus = 'R', checker = ?, rejection_comment = '"+rejection_comment+"' where id = ? and authStatus = 'U' and maker != ?";
			break;
		}
		try {
	
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setInt(2, id);
		pst.setString(3,whoami);
		if(pst.executeUpdate() > 0) {
			return true;
		}else {
			return false;
		}	
		
		}
		catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean authorize2(String table, int id,int action,String authStatus,String whoami) {
		String squery = "";
		switch(action) {
		case 0 :
			squery = "update "+table+" set authStatus = '"+authStatus+"', checker = ? where id = ? and authStatus = 'U' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set authStatus = 'R', checker = ? where id = ? and authStatus = 'U' and maker != ?";
			break;
		}
		try {
	
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setInt(2, id);
		pst.setString(3,whoami);
		if(pst.executeUpdate() > 0) {
			return true;
		}else {
			return false;
		}	
		
		}
		catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean authorize3(String table, String issueref,int action,String authStatus,String whoami,String rejection_comment) {
		String squery = "";
		boolean flag = false;
		String outerSquery = "SELECT id FROM famsStock WHERE issueref = ?";
		switch(action) {
		case 0 :
			squery = "update "+table+" set sysGenTag = ?, issueref = ? , authStatus = '"+authStatus+"', checker = ? where id = ? and authStatus = 'UI' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set sysGenTag = ?,issueref=?, authStatus = 'R', checker = ?, rejection_comment = '"+rejection_comment+"' where id = ? and authStatus = 'UI' and maker != ?";
			break;
		}
		try {			
		PreparedStatement outerPst = dbConn.connection.prepareStatement(outerSquery);
		outerPst.setString(1, issueref);
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		ResultSet rs = outerPst.executeQuery();
		while(rs.next()) {
			if(action == 0) {
				pst.setString(1, findNextTag(issueref));
				pst.setString(2, findNextIV(findSubCatMode(rs.getInt(1)),findExistingIV(rs.getInt(1))));
				pst.setString(3, whoami);
				pst.setInt(4, rs.getInt(1));
				
				pst.setString(5,whoami);
			}else {
				pst.setString(1, "");
				pst.setString(2, "");
				pst.setString(3, whoami);
				pst.setInt(4, rs.getInt(1));
				pst.setString(5,whoami);
			}
			if(pst.executeUpdate() > 0) {
				flag = true;
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public Print authorizeAndPrint(String table, String issueref,int action,String authStatus,String whoami,String rejection_comment) {
		String squery = "";
		boolean flag = false;
		String outerSquery = "SELECT id FROM famsStock WHERE issueref = ?";
		Print print = new Print();
		switch(action) {
		case 0 :
			squery = "update "+table+" set sysGenTag = ?, issueref = ? , authStatus = '"+authStatus+"', checker = ? where id = ? and authStatus = 'UI' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set sysGenTag = ?,issueref=?, authStatus = 'R', checker = ?, rejection_comment = '"+rejection_comment+"' where id = ? and authStatus = 'UI' and maker != ?";
			break;
		}
		try {			
		PreparedStatement outerPst = dbConn.connection.prepareStatement(outerSquery);
		outerPst.setString(1, issueref);
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		ResultSet rs = outerPst.executeQuery();
		while(rs.next()) {
			if(action == 0) {
				pst.setString(1, findNextTag(issueref));
				pst.setString(2, findNextIV(findSubCatMode(rs.getInt(1)),findExistingIV(rs.getInt(1))));
				//input for printing method
				print.setIssueref(findNextIV(findSubCatMode(rs.getInt(1)),findExistingIV(rs.getInt(1))));
				print.setSubCat(findSubCatMode(rs.getInt(1)));
				//end of input for printing method
				pst.setString(3, whoami);
				pst.setInt(4, rs.getInt(1));
				
				pst.setString(5,whoami);
			}else {
				pst.setString(1, "");
				pst.setString(2, "");
				pst.setString(3, whoami);
				pst.setInt(4, rs.getInt(1));
				pst.setString(5,whoami);
			}
			if(pst.executeUpdate() > 0) {
				flag = true;
				print.setIs_printed(true);
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return print;
	}
	
	public int findSubCatMode(int id) {
		String squery = "SELECT assetSubCat.mode FROM famsStock INNER JOIN\r\n" + 
				"assetSubCat ON famsStock.subCategory = assetSubCat.id \r\n" + 
				"WHERE famsStock.id = ?";
		int mode = 0;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs  = pst.executeQuery();
			while(rs.next()) {
				mode = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mode;
	}
	public int findSubCatModeForExisting(int id) {
		String squery = "SELECT TOP 1 assetSubCat.mode FROM famsStock INNER JOIN \r\n" + 
				"assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"WHERE requisitionNo  IN \r\n" + 
				"(SELECT requisitionNo FROM famsStock where famsStock.id = ?) AND famsStock.status = 'ISSUED'   \r\n" + 
				"AND famsStock.authStatus = 'AI'";
		int mode = 0;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs  = pst.executeQuery();
			while(rs.next()) {
				mode = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mode;
	}
	public String findExistingIV(int id) {
		String squery = "SELECT top 1 issueref FROM famsStock WHERE status = 'ISSUED' AND authStatus = 'AI'\r\n" + 
				"AND requisitionNo = (SELECT requisitionNo FROM famsStock WHERE id = ?)";
		String iv = "";
		
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			System.out.println("existing :" +findSubCatModeForExisting(id));
			System.out.println("current :" +findSubCatMode(id));
			while(rs.next()) {
				if(Math.abs(findSubCatModeForExisting(id)-findSubCatMode(id)) == 1 || Math.abs(findSubCatModeForExisting(id)-findSubCatMode(id)) == 0) {
					iv = rs.getString(1);
				}	
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return iv;	
	}
	public String findNextIV(int mode,String iv) {
		int submode = 0;
		switch(mode) {
		case 0:
			submode = 1;
			break;
		case 1:
			submode = 0;
			break;
		case 3:
			submode = 4;
			break;
		case 4:
			submode = 3;
			break;
		}
		String squery = "SELECT\r\n" + 
				"COALESCE(COUNT(DISTINCT requisitionNo), 0) AS count\r\n" + 
				"FROM famsStock INNER JOIN assetSubCat ON\r\n" + 
				"famsStock.subCategory=assetSubCat.id WHERE assetSubCat.mode IN (?,?) and famsStock.authStatus = 'AI' and status = 'ISSUED'";
		int i = 0;
		String ivv = "0";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1, mode);
		pst.setInt(2, submode);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			if(!iv.equals("")) {
				ivv = iv;
				break;
			}else {
				i = rs.getInt(1);
				i = i+1;
				
				ivv = ivv+i;
			}
         
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ivv;
	}
	public String findNextTag(String issuref) {
		String squery = "SELECT assetSubCat.subCatCode,sum(quantity)+1 AS counter,hremployees.brachcode,DATEPART (yyyy, GETDATE ()) \r\n" + 
				"AS currentYear FROM famsStock\r\n" + 
				"INNER JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
				"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"wHERE subCategory IN (SELECT TOP 1 subCategory\r\n" + 
				"FROM famsStock WHERE issueref=?) AND famsStock.status = 'ISSUED' AND  \r\n" + 
				"famsStock.authStatus = 'AI' GROUP BY hremployees.brachcode, assetSubCat.subCatCode ";
		String tag = findFirstTag(issuref);
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, issuref);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				tag = "SB/"+rs.getString(1)+"/"+rs.getInt(2)+"/"+rs.getString(3)+"/"+rs.getInt(4);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tag;
	}
	public String findFirstTag(String issuref) {
		String squery = "SELECT assetSubCat.subCatCode,sum(quantity)+1 AS counter,hremployees.brachcode,DATEPART (yyyy, GETDATE ()) \r\n" + 
				"AS currentYear FROM famsStock\r\n" + 
				"INNER JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid\r\n" + 
				"INNER JOIN assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
				"wHERE subCategory IN (SELECT TOP 1 subCategory\r\n" + 
				"FROM famsStock WHERE issueref=?) AND famsStock.status = 'ISSUED' AND  \r\n" + 
				"famsStock.authStatus = 'UI' GROUP BY hremployees.brachcode, assetSubCat.subCatCode ";
		String tag = "";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, issuref);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				tag = "SB/"+rs.getString(1)+"/"+(rs.getInt(2)-(rs.getInt(2)-1))+"/"+rs.getString(3)+"/"+rs.getInt(4);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tag;
	}
	public boolean authorize4(String table, int id,int action,String authStatus,String whoami) {
		String squery = "";
		switch(action) {
		case 0 :
			squery = "update "+table+" set authStatus = '"+authStatus+"',status = 'ISSUED', checker = ? where id = ? and authStatus = 'UTRN' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set authStatus = 'R',status = 'ISSUED' checker = ? where id = ? and authStatus = 'UTRN' and maker != ?";
			break;
		}
		try {
	
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setInt(2, id);
		pst.setString(3,whoami);
		if(pst.executeUpdate() > 0) {
			return true;
		}else {
			return false;
		}	
		
		}
		catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean authorize5(String table, int id,int action,String authStatus,String whoami) {
		String squery = "";
		switch(action) {
		case 0 :
			squery = "update "+table+" set authStatus = '"+authStatus+"', checker = ? where id = ? and authStatus = 'UDIS' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set authStatus = 'R', checker = ? where id = ? and authStatus = 'UDIS' and maker != ?";
			break;
		}
		try {
	
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setInt(2, id);
		pst.setString(3,whoami);
		if(pst.executeUpdate() > 0) {
			return true;
		}else {
			return false;
		}	
		
		}
		catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public boolean authorize6(String table, int id,int action,String authStatus,String whoami) {
		String squery = "";
		switch(action) {
		case 0 :
			squery = "update "+table+" set authStatus = '"+authStatus+"', checker = ? where id = ? and authStatus = 'UR' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set authStatus = 'R', checker = ? where id = ? and authStatus = 'UR' and maker != ?";
			break;
		}
		try {
	
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setInt(2, id);
		pst.setString(3,whoami);
		if(pst.executeUpdate() > 0) {
			return true;
		}else {
			return false;
		}	
		
		}
		catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	
	public boolean authorizeStock(String table, String rc ,int action,String authStatus,String whoami,String rejection_comment) {
		String squery = "";
		String rcUpdate = findExistingRc(rc);
		if(rcUpdate.equals("")) {
			rcUpdate = findNextRc(rc);
		}
		switch(action) {
		case 0 :
			squery = "update  "+table+" set authStatus = '"+authStatus+"',receivingCode = '"+rcUpdate+"', checker = ? where receivingCode = ? and authStatus = 'U' and maker != ?";
			break;
		case 1 :
			squery = "update "+table+" set authStatus = 'R',receivingCode = '',recieptNo='',checker = ?, rejection_comment = '"+rejection_comment+"' where receivingCode = ? and authStatus = 'U' and maker != ?";
			break;
		}
		try {
	
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setString(2, rc);
		pst.setString(3,whoami);
		if(pst.executeUpdate() > 0) {
			return true;
		}else {
			return false;
		}	
		
		}
		catch(Exception e) {
			e.printStackTrace();
		   return false;
		}
	}
	public String findExistingRc(String rc) {
		String squery = "SELECT TOP 1 receivingCode FROM famsStock WHERE recieptNo IN \r\n" + 
				"(SELECT recieptNo FROM famsStock WHERE receivingCode = ? and recieptNo not in ('NA','na'))\r\n" + 
				"AND authStatus= 'AR' AND status = 'AVAILABLE'";
		String rn = "";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, rc);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				rn = rs.getString(1);
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rn;
	}
	public String findFirstRc(String rc) {
		String squery = "SELECT count(DISTINCT(receivingCode)) FROM famsStock \r\n" + 
				"WHERE authStatus = 'U' AND status = 'AVAILABLE'";
		String tag = "";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			//pst.setString(1, rc);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				tag = "00"+((rs.getInt(1)+1)-(rs.getInt(1)));
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tag;
	}
	public String findNextRc(String rc) {
		String squery = "SELECT count(DISTINCT(receivingCode))+1 FROM famsStock \r\n" + 
				"WHERE authStatus = 'AR' AND status = 'AVAILABLE'";
		String tag = findFirstRc(rc);
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			//pst.setString(1, rc);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				tag = "00"+(rs.getInt(1));
				break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tag;
	}
	
	
/*	public boolean rollBackIssueToAvail() {
		String squery = "";
		
	}*/
	public boolean passetToPriviousState(int id, String squery) {
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			if(pst.executeUpdate() > 0) {
				System.out.println("Returning asset on "+id+" to previous state is successfull");
				return true;
			}else {
				System.out.println("Returning asset on "+id+" to previous state is not successfull");
				return false;
			}
		}catch(Exception e) {
			System.out.println("Returning asset on "+id+" to previous state is not successfull");
			e.printStackTrace();
			return false;
		}
	}
	public boolean assetToPriviousState(String issueref, String squery) {
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, issueref);
			if(pst.executeUpdate() > 0) {
				System.out.println("Returning asset on "+issueref+" to previous state is successfull");
				return true;
			}else {
				System.out.println("Returning asset on "+issueref+" to previous state is not successfull");
				return false;
			}
		}catch(Exception e) {
			System.out.println("Returning asset on "+issueref+" to previous state is not successfull");
			e.printStackTrace();
			return false;
		}
	}
	public boolean pdeleteHistoryOnQueuee(int id , String squery) {
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			pst.setInt(2, id);
			if(pst.executeUpdate() > 0) {
				System.out.println("Deleting record on famsStock_history on "+id+" is successfull");
				return true;
			}else {
				System.out.println("Deleting record on famsStock_history on "+id+" is not successfull");
				return false;
			}
		}catch(Exception e) {
			System.out.println("Deleting record on famsStock_history on "+id+" is not successfull");
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteHistoryOnQueuee(String issueref , String squery) {
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, issueref);
			pst.setString(2, issueref);
			if(pst.executeUpdate() > 0) {
				System.out.println("Deleting record on famsStock_history on "+issueref+" is successfull");
				return true;
			}else {
				System.out.println("Deleting record on famsStock_history on "+issueref+" is not successfull");
				return false;
			}
		}catch(Exception e) {
			System.out.println("Deleting record on famsStock_history on "+issueref+" is not successfull");
			e.printStackTrace();
			return false;
		}
	}
	public int findSubCatWithIssueref(String issueref) {
		int subcat = 0;
		String sql = "SELECT subCategory FROM famsStock WHERE issueref = ? AND status = 'ISSUED' and authStatus = 'UI'\r\n" + 
				"";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(sql);
			pst.setString(1, issueref);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				subcat = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return subcat;
	}
	public boolean returningNonSerialToPrevState(int subcat, String issueref) {
		String squery = "DELETE FROM famsStock WHERE issueref = ? AND status = 'ISSUED' AND authStatus = 'UI' AND subCategory = ?";
		boolean flag = false;
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, issueref);
		pst.setInt(2, subcat);
		if(pst.executeUpdate() > 0) {
		flag = true;	
		}
		}catch(Exception e) {
		e.printStackTrace();
		}
		return flag;
	}
  public boolean clearIssueref(String issueref) {
	  String squery = "Update famsStock set issueref = '' where issueref = ?";
	  try {
		  PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		  pst.setString(1, issueref);
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

}
