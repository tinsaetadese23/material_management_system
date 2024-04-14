package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Asset;
import model.Employee;

public class AssetRepository {
private boolean connCreated;
DbConnection dbConn;

public AssetRepository() {
	try {
		dbConn = new DbConnection();
		connCreated = dbConn.isConnected();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public boolean save(Asset asset) {
	String squery = "insert into asset assetcate =? assetclass = ? assetDepr = ?";
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		//pst.setString(1, asset.getAssetCategory());
		//pst.setString(2, asset.getAssetCategory());
		//pst.setString(3, asset.);
		pst.executeUpdate();
		return true;
		
	}catch(Exception e) {
		e.printStackTrace();
	   return false;
	}
}
public boolean crossCheckPurchaseOrder(Asset asset) {
	int purchaseId = Integer.parseInt(asset.getPurchaseOrder());
	int numOfAssUndPur = 0;
	int amountPurchase = 0;
	boolean flag = true;
	String squery = "";
	try {
	PreparedStatement pst = dbConn.connection.prepareStatement(squery);
	pst.setInt(1, purchaseId);
	ResultSet rs = pst.executeQuery();
	while(rs.next()) {
	if(numOfAssUndPur < amountPurchase)	{
		flag = true;
	}
	}
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return flag;
}
public Asset findAssetByTag(String tag) {
	String sq = "select famsStock.sysGenTag, famsStock.receivingCode, famsStock.assetSpecificText,famsStock.issuedTo,\r\n" + 
			"assetSubCat.subCatCode,assetSubCat.subCatDesc from famsStock \r\n" + 
			"inner join assetSubCat on famsStock.subCategory = assetSubCat.id  where sysGenTag = ? and famsStock.authStatus in ('A','AR','AI','ATRN','ART')";
	Asset ast = new Asset();
	try {
	PreparedStatement pst = dbConn.connection.prepareStatement(sq);
	pst.setString(1, tag);
	ResultSet rs = pst.executeQuery();
	while(rs.next()) {
	 ast = new Asset();
	ast.setAssetSubCategory(rs.getString("subCatCode"));
	ast.setAssetSpecificText(rs.getString("assetSpecificText"));
	ast.setRemark(rs.getString("subCatDesc"));
	ast.setId(rs.getInt("issuedTo"));//setId should be setAssignedTo with type int
	ast.setTagNumber(rs.getString("sysGenTag"));
	ast.setStatus(rs.getString("receivingCode")); //there shd be rc attribute fo this
	}
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return ast;
}
public Asset findAssetByTagForReturn(String tag) {
	String sq = "select famsStock.sysGenTag, famsStock.receivingCode, famsStock.assetSpecificText,famsStock.issuedTo,\r\n" + 
			"assetSubCat.subCatCode, assetSubCat.subCatDesc from famsStock \r\n" + 
			"inner join assetSubCat on famsStock.subCategory = assetSubCat.id  where sysGenTag = ? and (famsStock.authStatus = 'AI' or famsStock.authStatus = 'ATRN' ) and famsStock.status = 'ISSUED'";
	Asset ast = new Asset();
	try {
	PreparedStatement pst = dbConn.connection.prepareStatement(sq);
	pst.setString(1, tag);
	ResultSet rs = pst.executeQuery();
	while(rs.next()) {
	 ast = new Asset();
	ast.setAssetSubCategory(rs.getString("subCatCode"));
	ast.setRemark(rs.getString("subCatDesc"));
	ast.setAssetSpecificText(rs.getString("assetSpecificText"));
	ast.setId(rs.getInt("issuedTo"));//setId should be setAssignedTo with type int
	ast.setTagNumber(rs.getString("sysGenTag"));
	ast.setStatus(rs.getString("receivingCode")); //there shd be rc attribute fo this
	}
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	return ast;
}

public boolean update(String tag, int issuedto,String whoami) {
	String squery = "update famsStock set status = 'ISSUED-ON-TRN', issuedTo = ?, authStatus = 'UTRN', maker = ?, checker = '' where sysGenTag = ?";
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1, issuedto);
		pst.setString(2, whoami);
		pst.setString(3, tag);
		//pst.setString(3, asset.);
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
public boolean returnToStore(String tag,String whoami) {
	String squery = "update famsStock set status = 'AVAILABLE', authStatus = 'UR', issuedTo='', maker =?,checker = '' where sysGenTag = ?";
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(2, tag);
		pst.setString(1, whoami);
		//pst.setString(3, asset.);
		pst.executeUpdate();
		return true;
		
	}catch(Exception e) {
		e.printStackTrace();
	   return false;
	}
}
public boolean dispose(String tag,String whoami,String reason) {
	String squery = "update famsStock set status = 'DISPOSED',issuedTo = '',authStatus = 'UDIS', maker=?,checker = '',reasonForDis=? where sysGenTag = ?";
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, whoami);
		pst.setString(2, reason);
		pst.setString(3, tag);
		//pst.setString(3, asset.);
		pst.executeUpdate();
		return true;
		
	}catch(Exception e) {
		e.printStackTrace();
	   return false;
	}
}
public boolean delete(int id) {
	return false;
}
public Asset findOne(int id) {
	Asset asset = new Asset();
	
	return asset;
}
public List<Asset> findAll() {
	List<Asset> assetList = new ArrayList<>();
	
	return assetList;
}

public Employee empInfo(int empId){
	String squery = "select employee.empId, employee.empName,branch.branchCode,branch.branchName,department.deptName from employee \r\n" + 
			"inner join branch on employee.empBranch = branch.branchId\r\n" + 
			"inner join department on employee.empDept = department.deptId\r\n" + 
			"where empId = ?";
	Employee emp = new Employee();
	try{
	PreparedStatement pst = dbConn.connection.prepareStatement(squery);
	pst.setInt(1,empId);
	
	ResultSet rs = pst.executeQuery();
	while(rs.next()){
		emp.setId(rs.getInt("empId"));
		emp.setFullName(rs.getString("empName"));
		emp.setBranch(rs.getString("branchCode")+"("+rs.getString("branchName")+")");
		emp.setDept(rs.getString("deptName"));
	}
	
	}catch(Exception e) {
	e.printStackTrace();	
	}
	
	return emp;
}

public boolean isAssetReturned(String tag) {
	String squery = "select status from famsStock where status = 'YYYYYYY' and sysGenTag = ?";
	boolean assetReturned = false;
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, tag);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			assetReturned = true;
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	return (assetReturned);
}

public List<Asset> fetchAssets(String tag, String query) {
	 List<Asset>  assetslist = new ArrayList<>();
//String query = "select issueAssign.issueAssignId,issueAssign.issueIds,issue.issueSubject,issue.status,datediff(minute,GETDATE(),issue.createdAt) as diffrr from issueAssign inner join users on users.userId = issueAssign.issueAssignedTo  inner join issue on issueAssign.issueIds = issue.issueId where users.userId = ?";
	String squery2  = " SELECT concat('SB/',assetSubCat.subCatCode,'/',?,'/' ,hremployees.brachcode,'/',format(getdate(),'yyyy')) AS tag from famsStock inner join assetSubCat on famsStock.subCategory = assetSubCat.id \r\n" + 
			"  INNER JOIN hremployees ON famsStock.issuedTo = hremployees.Employeeid \r\n" + 
			"  WHERE assetSubCat.subCatCode= ? and famsStock.status = 'ISSUED' AND famsStock.authStatus='AI' GROUP BY assetSubCat.subCatCode, hremployees.brachcode  ";
	String squery3 = "SELECT   assetSubCat.subCatCode,  sum(quantity) AS ccount FROM famsStock INNER JOIN \r\n" + 
			"assetSubCat ON famsStock.subCategory = assetSubCat.id\r\n" + 
			"WHERE  famsStock.status = 'ISSUED' AND famsStock.authStatus = 'AI' GROUP BY  assetSubCat.subCatCode";
	String ttag = "";
	int ccount = 0;
	 try {
		PreparedStatement pstmnt = dbConn.connection.prepareStatement(query);
		//pstmnt.setString(1, tag);
		ResultSet rs = pstmnt.executeQuery();
		while (rs.next()) {
			Asset asset = new Asset();
			asset.setTagNumber(rs.getString("sysGenTag"));
			asset.setAssetSubCategory(rs.getString("subCatCode"));
			asset.setSerialNum(rs.getString("serialNo"));
			asset.setAssetSpecificText(rs.getString("issueref"));
			asset.setUnitPrice(rs.getFloat("unitPrice"));
			asset.setModel(rs.getString("status"));
			asset.setAssetClass(rs.getString("receivingCode"));
			asset.setAssignedTo(rs.getString("authStatus"));
			asset.setDepr(rs.getString("requisitionNo"));
			
			PreparedStatement pstmnt2 = dbConn.connection.prepareStatement(squery2);
			PreparedStatement pstmnt3 = dbConn.connection.prepareStatement(squery3);
			//pstmnt3.setString(1, (rs.getString("subCatCode")));
			pstmnt2.setString(2, rs.getString("subCatCode"));
			ResultSet rs3 = pstmnt3.executeQuery();
			while(rs3.next()) {
			ccount = rs3.getInt(2);	
			pstmnt2.setInt(1, ccount+1);
			ResultSet rs2  = pstmnt2.executeQuery();
			while (rs2.next()) {
				ttag = rs2.getString(1);
			}
			}
			
			asset.setStatus(ttag);
	       assetslist.add(asset);
	      }
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return assetslist;

}




}
