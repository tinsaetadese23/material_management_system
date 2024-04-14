package model;

public class Access {

	private int id;
	private int userId;
	private int rolId;
	private String username;
	private String rolename;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getRolId() {
		return rolId;
	}
	public void setRolId(int rolId) {
		this.rolId = rolId;
	}
	
	
}
