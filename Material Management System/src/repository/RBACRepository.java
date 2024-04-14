package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Access;
import model.Pages;
import model.RoleDefinition;
import model.Roles;
import model.User;

public class RBACRepository {
	private boolean connCreated;
	DbConnection dbConn;
	
	public RBACRepository() {
		try {
			dbConn = new DbConnection();
			connCreated = dbConn.isConnected();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Pages> fetchAllPages(){
		List<Pages> pagesList = new ArrayList<>();
		String squery = "select * from sysPages";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				Pages page = new Pages();
				page.setId(rs.getInt("id"));
				page.setPageName(rs.getString("pageName"));
				page.setPageUrl(rs.getString("pageUrl"));
				
				pagesList.add(page);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pagesList;
	}
	public List<Roles> fetchAllRoles(){
		List<Roles> rolesList = new ArrayList<>();
		String squery = "select * from roles";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				Roles roles = new Roles();
				roles.setId(rs.getInt("id"));
				roles.setCreate(rs.getInt("cr"));
				roles.setUpdate(rs.getInt("up"));
				roles.setView(rs.getInt("ve"));
				roles.setDelete(rs.getInt("dl"));
				roles.setAuth(rs.getInt("au"));
				roles.setRolId(rs.getInt("roleId"));
				roles.setPageId(rs.getInt("sysPageId"));
				
				rolesList.add(roles);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return rolesList;
	}
	
	public List<RoleDefinition> fetchAllRoleDefinition(){
		List<RoleDefinition> roleDefintionList = new ArrayList<>();
		String squery = "select * from roleDefinition";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				RoleDefinition rolDef = new RoleDefinition();
				rolDef.setId(rs.getInt("id"));
				rolDef.setRoleName(rs.getString("rolName"));
				rolDef.setRoleDefinition(rs.getString("rolDesc"));
				
				roleDefintionList.add(rolDef);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return roleDefintionList;
	}
	public List<User> fetchAllUsers(){
		List<User> userList = new ArrayList<>();
		String squery = "select * from users";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("userId"));
				user.setUsername(rs.getString("username"));
				
				userList.add(user);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	public List<Access> fetchAllAccess(){
		List<Access> accessList = new ArrayList<>();
		String squery = "select * from userRoleMapping";
		String squery2 = "SELECT userRoleMapping.id, roleDefinition.rolName, users.username from userRoleMapping \r\n" + 
				"INNER JOIN roleDefinition ON userRoleMapping.roId = roleDefinition.id\r\n" + 
				"INNER JOIN users ON userRoleMapping.userId = users.userId";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery2);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				Access access = new Access();
				access.setId(rs.getInt(1));
				access.setRolename(rs.getString(2));
				access.setUsername(rs.getString(3));
				
				accessList.add(access);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return accessList;
	}
	public List<Roles> fetchAccessForUser(int id){
		List<Roles> userRolesList = new ArrayList<>();
		String squery = "select roles.ve,roles.cr,roles.up,roles.dl,roles.au,sysPages.pageName from roles\r\n" + 
				"inner join sysPages on roles.sysPageId = sysPages.id\r\n" + 
				"where roleId in (select roleDefinition.id from userRoleMapping\r\n" + 
				"inner join roleDefinition on userRoleMapping.roId = roleDefinition.id where userId = ?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				Roles roles = new Roles();
				roles.setCreate(rs.getInt("cr"));
				roles.setUpdate(rs.getInt("up"));
				roles.setDelete(rs.getInt("dl"));
				roles.setView(rs.getInt("ve"));
				roles.setAuth(rs.getInt("au"));
				roles.setPageIdNameHolder(rs.getString("pageName"));
				userRolesList.add(roles);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return userRolesList;
	}
	
	public List<Roles> findOne(String rl){
		List<Roles> userRolesList = new ArrayList<>();
		String squery = "select roles.* ,sysPages.pageName from roles\r\n" + 
				"inner join sysPages on roles.sysPageId = sysPages.id\r\n" + 
				"where roleId in (select id from roleDefinition where rolName = ?)";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, rl);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				Roles roles = new Roles();
				roles.setId(rs.getInt("id"));
				roles.setCreate(rs.getInt("cr"));
				roles.setUpdate(rs.getInt("up"));
				roles.setDelete(rs.getInt("dl"));
				roles.setView(rs.getInt("ve"));
				roles.setAuth(rs.getInt("au"));
				roles.setPageIdNameHolder(rs.getString("pageName"));
				roles.setRoleNameHolder(rs.getString("roleId"));
				
				userRolesList.add(roles);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return userRolesList;
	}
	
	public List<Roles> findOne2(int id){
		List<Roles> userRolesList = new ArrayList<>();
		String squery = "select roles.*, sysPages.pageName,roleDefinition.rolName\r\n" + 
				"from roles \r\n" + 
				"inner join sysPages on Roles.sysPageId = sysPages.id\r\n" + 
				"inner join roleDefinition on Roles.roleId = roleDefinition.id where roles.id = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, id);
			ResultSet rs =  pst.executeQuery();
			while(rs.next()) {
				Roles roles = new Roles();
				roles.setId(rs.getInt("id"));
				roles.setCreate(rs.getInt("cr"));
				roles.setUpdate(rs.getInt("up"));
				roles.setDelete(rs.getInt("dl"));
				roles.setView(rs.getInt("ve"));
				roles.setAuth(rs.getInt("au"));
				roles.setPageIdNameHolder(rs.getString("pageName"));
				roles.setPageId(rs.getInt("sysPageId"));
				roles.setRoleNameHolder(rs.getString("rolName"));
				roles.setRolId(rs.getInt("roleId"));
				
				System.out.println("Db genereted msg"+roles.getRoleNameHolder());
				
				userRolesList.add(roles);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return userRolesList;
	}
	
	
	public boolean saveRoles(Roles roles,String whoami) {
		String squery = "insert into roles(cr,up,dl,ve,au,roleId,sysPageId,maker,checker) values(?,?,?,?,?,?,?,?,?)";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1, roles.getCreate());
		pst.setInt(2, roles.getUpdate());
		pst.setInt(3, roles.getDelete());
		pst.setInt(4, roles.getView());
		pst.setInt(5, roles.getAuth());
		pst.setInt(6, roles.getRolId());
		pst.setInt(7, roles.getPageId());
		pst.setString(8,whoami);
		pst.setString(9, whoami);
		pst.executeUpdate();
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean isRoleExists(Roles roles) {
		String squery = "SELECT * from roles WHERE roleId = ? AND sysPageId = ?";
		System.out.println("ROLE ID: "+roles.getRolId());
		System.out.println("PAGE ID: "+roles.getPageId());
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, roles.getRolId());
			pst.setInt(2, roles.getPageId());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean deleteRoles(int id) {
		String squery = "delete from roles where id = ?";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1,id);
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
	public boolean deleteFromUserRoleMapping(int id) {
		String squery = "delete from userRoleMapping where id = ?";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1,id);
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
	public boolean deleteFromRoleDefinition(int id) {
		String squery = "delete from roleDefinition where id = ?";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1,id);
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
	public boolean updateRoles(Roles roles) {
		//String squery = "insert into roles(cr,up,dl,ve,roleId,sysPageId) values(?,?,?,?,?,?)";
		String squery = "update roles set cr = ?, up = ?, dl= ?, ve = ?,au = ? where id = ?";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1, roles.getCreate());
		pst.setInt(2, roles.getUpdate());
		pst.setInt(3, roles.getDelete());
		pst.setInt(4, roles.getView());
		pst.setInt(5, roles.getAuth());
		pst.setInt(6, roles.getId());
		pst.executeUpdate();
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveRoleDefinition(RoleDefinition roleDefn) {
		String squery = "insert into roleDefinition (rolName,rolDesc) values(?,?)";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setString(1, roleDefn.getRoleName());
		pst.setString(2, roleDefn.getRoleDefinition());
		pst.executeUpdate();
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean isRoleDefinitionExists(RoleDefinition roleDefn) {
		String squery = "select * from roleDefinition where rolName = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, roleDefn.getRoleName());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean isUserRoleMappingExists(Access access) {
		String squery = "select * from userRoleMapping where roId = ? and userId = ?";
		boolean flag = false;
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, access.getRolId());
			pst.setInt(2,access.getUserId());
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				flag = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public boolean saveAccessToUser(Access access) {
		String squery = "insert into userRoleMapping(roId,userId) values(?,?)";
		try {
		PreparedStatement pst = dbConn.connection.prepareStatement(squery);
		pst.setInt(1, access.getRolId());
		pst.setInt(2, access.getUserId());
		pst.executeUpdate();
		return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public String fetchUserWithEmp(int empID){
		String squery = "select username from users where empId = ?";
		System.out.println("Finding employee with user id... "+empID);
		String user = "";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setInt(1, empID);
			ResultSet rs =  pst.executeQuery();
			if(rs.next()) {
				user = rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	public boolean isUserNameTaken(String username){
		boolean user_is_taken = false;
		String squery = "select lower(username) from users where username = ?";
		try {
			PreparedStatement pst = dbConn.connection.prepareStatement(squery);
			pst.setString(1, username);
			ResultSet rs =  pst.executeQuery();
			if(rs.next()) {
				user_is_taken = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return user_is_taken;
	}
}
