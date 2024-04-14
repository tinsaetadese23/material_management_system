package model;

public class Unit {

	private int id;
	private String unitName;
	private String unitDesc;
	private String authStatus;
	private String createdAt;
	private String maker;
	private String checker;
	private String rejection_reaon;
	
	
	
	public String getRejection_reaon() {
		return rejection_reaon;
	}
	public void setRejection_reaon(String rejection_reaon) {
		this.rejection_reaon = rejection_reaon;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitDesc() {
		return unitDesc;
	}
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	
	
}
