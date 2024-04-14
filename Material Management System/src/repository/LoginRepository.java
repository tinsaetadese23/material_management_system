package repository;

import java.security.Key;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

import model.Employee;
import model.Login;

public class LoginRepository {
	private boolean connCreated;
	DbConnection dbConn;
	
	public LoginRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public boolean save(Login login) {
	return false;
}
public List<Login> fetch(){
	List<Login> logins = new ArrayList<>();
	
	return logins;
}
public Login fetchOne(Login login) {
	Login loginObj = null;
	boolean flag = false;
	String squery = "Select username, password from users where username = ? and password = ?";
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1,login.getUsername());
		pst.setString(2, encryptPassword(login.getPassword(),getSecretKeyFromPassword(login.getPassword())));
		ResultSet rs = pst.executeQuery();
		
		while(rs.next()) {
			loginObj = new Login();
			loginObj.setUsername(rs.getString("username"));
		/*	if(loginObj.getPassword().equals(decryptPassword(rs.getString("password"),getSecretKeyFromPassword(loginObj.getPassword())))) {
				//if(loginObj.getPassword().equals(rs.getString("password"))) {
				System.out.println("password match");			
			}else {
				System.out.println("password mismatch");
				loginObj = null;
			}*/
		}
		    
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return loginObj;
}
public boolean isLogExist(String username) {
	boolean userExist = false;
	String query = "select * from [log] where username=?";
	try {
		PreparedStatement pstmn = dbConn.connection.prepareStatement(query);
		pstmn.setString(1, username);
		ResultSet rs = pstmn.executeQuery();
		if (rs.next()) {
			userExist = true;
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return userExist;
}
public boolean updateLog(String username, LocalDateTime ldt) {
	boolean rowAffected = false;
	String query = "UPDATE [log] set startedAt=?,loggedOut=? WHERE username=?";
	try {
		DateTimeFormatter myFormatObj = DateTimeFormatter
				.ofPattern("yyyy/MM/dd HH:mm:ss");
		PreparedStatement stmnt = dbConn.connection.prepareStatement(query);
		stmnt.setString(1, ldt.format(myFormatObj));
		stmnt.setString(2, ldt.format(myFormatObj));
		stmnt.setString(3, username);
		if (stmnt.executeUpdate() > 0)
			rowAffected = true;

	} catch (Exception e) {
		e.printStackTrace();
	}
	return rowAffected;
}
public String checkPassword(String username,String prevPass){
	  String query = "SELECT password FROM users WHERE username = ?";
	  String password = "";
	  String enteredPass = "";
	  try {
	    PreparedStatement pst = dbConn.connection.prepareStatement(query);
	    pst.setString(1, username);
	    ResultSet rs = pst.executeQuery();
	    enteredPass = encryptPassword(prevPass,getSecretKeyFromPassword(prevPass));
	    while(rs.next()){
	    	System.out.println("equality check...");
	      password = rs.getString("password");
	      if(!password.equals(enteredPass)) 
	      {
	    	  password="";
	    	  System.out.println("we are not equal");
	      }else {
	    	  password = prevPass;
	    	  System.out.println("we are equal");
	      }
	    }
	    System.out.print("from db"+ password);
	  } catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    password = "";
	  }
	 // System.out.println("entered password: "+enteredPass);
	 // System.out.println("db password: "+password);
	  return password;
	}
public int fetchUserId(String username){
	  String query = "SELECT userId FROM users WHERE username = ?";
	  int uid = 0;
	  try {
	    PreparedStatement pst = dbConn.connection.prepareStatement(query);
	    pst.setString(1, username);
	    ResultSet rs = pst.executeQuery();
	    while(rs.next()){
	      uid = rs.getInt("userId");
	    }
	    System.out.print("from db"+ uid);
	  } catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	  }
	  return uid;
	}
public boolean updatePassword(String password,String username){
	boolean rowUpdated = false;
	System.out.println("from db method u : "+username);
	System.out.println("from db method p: "+password);
	String query = "update [users] set password=? where username=?";
	try{
		PreparedStatement pst = dbConn.connection.prepareStatement(query);
		pst.setString(1,encryptPassword(password,getSecretKeyFromPassword(password)));
		pst.setString(2,username);
		if(pst.executeUpdate() > 0) {
			rowUpdated = true;	
		}
		else
			System.out.println("error");
	}catch(Exception e){
		e.printStackTrace();
	}
	//System.out.println("from db method: "+rowUpdated);
	return rowUpdated;
}
public boolean insertLogTime(String username, LocalDateTime myDateObj,
		LocalDateTime myDateObj2) {
	boolean rowAffected = false;
	String query = "INSERT INTO [log] (username,startedAt,loggedOut) VALUES (?,?,?)";
	try {
		DateTimeFormatter myFormatObj = DateTimeFormatter
				.ofPattern("yyyy/MM/dd HH:mm:ss");
		PreparedStatement pstmnt = dbConn.connection.prepareStatement(query);
		pstmnt.setString(1, username);
		pstmnt.setString(2, myDateObj.format(myFormatObj));
		pstmnt.setString(3, myDateObj2.format(myFormatObj));
		if (pstmnt.executeUpdate() > 0)
			rowAffected = true;
	} catch (Exception e) {
		e.printStackTrace();
	}

	return rowAffected;
}


public boolean createUser(String username, String password, int empId, String maker) {
	String query = "insert into users (username, password, empId, maker) values (?,?,?,?)";
	boolean flag = false;
	try {
		PreparedStatement pst = dbConn.connection.prepareStatement(query);
		pst.setString(1, username);
		pst.setString(2, encryptPassword(password,getSecretKeyFromPassword(password)));
		pst.setInt(3, empId);
		pst.setString(4, maker);
		if(pst.executeUpdate() > 0) {
			flag = true;
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	return flag;
}
public boolean updatePassword(String password,String username,String whoami){
	boolean rowUpdated = false;
	System.out.println("from db method u : "+username);
	System.out.println("from db method p: "+password);
	String query = "update [users] set password=?, maker = ? where username=?";
	try{
		PreparedStatement pst = dbConn.connection.prepareStatement(query);
		pst.setString(1,encryptPassword(password,getSecretKeyFromPassword(password)));
		pst.setString(2,whoami);
		pst.setString(3, username);
		if(pst.executeUpdate() > 0)
		 rowUpdated = true;
		else
			System.out.println("error");
	}catch(Exception e){
		e.printStackTrace();
	}
	//System.out.println("from db method: "+rowUpdated);
	return rowUpdated;
}

public Employee fetchEmployeeDataForUserCreation(int empId) {
	String squery = "select employee.empId,employee.empName,branch.branchCode,branchName,department.deptName from employee\r\n" + 
			"inner join branch on employee.empBranch = branch.branchId\r\n" + 
			"inner join department on employee.empDept = department.deptId where empId = ?";
	String squery3 = "SELECT hremployees.Employeeid, hremployees.position, concat(FirstName,' ',middlename,' ',lastname) AS\r\n" + 
			"empname,brachcode, managerid,managementirectorate.MDirectorate AS 'departmentname'\r\n" + 
			"FROM hremployees INNER JOIN managementirectorate ON\r\n" + 
			"hremployees.managerid = managementirectorate.MDirectorateID WHERE hremployees.Employeeid = ? AND ( hremployees.managerid IN ('SinB22010ITMPM','SinB22015ITAMS','SinB2202PAF','SinB22022PSM') OR \r\n" + 
			"hremployees.position LIKE  'Branch Man%')";
	Employee emp = null;
	PreparedStatement pst;
	try {
		pst = dbConn.connection.prepareStatement(squery3);
		pst.setInt(1,empId);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
		{
			emp = new Employee();
			/*emp.setBranch(rs.getString("branchCode") +"("+rs.getString("branchName") + ")");
			emp.setFullName(rs.getString("empName"));
			emp.setDept(rs.getString("deptName"));
			emp.setId(rs.getInt("empId"));*/
			emp.setBranch(rs.getString("brachcode"));
			emp.setFullName(rs.getString("empname"));
			emp.setDept(rs.getString("departmentname"));
			emp.setId(rs.getInt("Employeeid"));
			emp.setPosition(rs.getString("position"));
			
		}
			
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	return emp;
}

//a method that encrypts a password using a secret key
public static String encryptPassword(String password, Key secretKey) throws Exception {
	try {
		// Create a cipher instance with AES algorithm
		Cipher cipher = Cipher.getInstance("AES");
		// Initialize the cipher with the secret key in encryption mode
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		// Encrypt the password bytes using the cipher
		byte[] encryptedBytes = cipher.doFinal(password.getBytes());
		// Encode the encrypted bytes to a Base64 string
		String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);
		// Return the encrypted string
		return encryptedString;
		} catch (Exception e) {
		// Handle any exceptions
		e.printStackTrace();
		return null;
		}
}

//a method that decrypts a password using a secret key
public static String decryptPassword(String encryptedPassword, Key secretKey) throws Exception {
	try {
		// Create a cipher instance with AES algorithm
		Cipher cipher = Cipher.getInstance("AES");
		// Initialize the cipher with the secret key in decryption mode
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// Decode the encrypted string to a byte array
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
		// Decrypt the encrypted bytes using the cipher
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		// Convert the decrypted bytes to a string
		String decryptedString = new String(decryptedBytes);
		// Return the decrypted string
		return decryptedString;
		} catch (Exception e) {
		// Handle any exceptions
		e.printStackTrace();
		return null;
		}
}

public static SecretKey getSecretKeyFromPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
	//define a fixed salt value for simplicity (you should use a random salt in practice)
	byte[] salt = "12345678".getBytes();
	//define the iteration count and the key length
	int iterationCount = 65536;
	int keyLength = 256;
	//create a key specification with the password, salt, iteration count, and key length
	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
	//create a secret key factory with the PBKDF2 algorithm
	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	//generate a secret key from the specification
	byte[] keyBytes = factory.generateSecret(spec).getEncoded();
	//create an AES key from the bytes
	SecretKey secret = new SecretKeySpec(keyBytes, "AES");
	return secret;
	}

/*
 * byte[] keyBytes = Base64.getDecoder().decode(secret);

//create an AES key from the bytes
SecretKey aesKey = new SecretKeySpec(keyBytes, "AES");

 * 
 */


}
