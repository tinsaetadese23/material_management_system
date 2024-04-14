package model;

public class AssetSubCategory {
	private int id;
	private String subCatCode;
	private String subCatDesc;
	private int subCatCategory;

	private int mode;
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
	//tempholder
	private String tmpHolder;
	
	public String getTmpHolder() {
		return tmpHolder;
	}
	public void setTmpHolder(String tmpHolder) {
		this.tmpHolder = tmpHolder;
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
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	private String subCategoryNameHolder;
	
	public String getSubCategoryNameHolder() {
		return subCategoryNameHolder;
	}
	public void setSubCategoryNameHolder(String subCategoryNameHolder) {
		this.subCategoryNameHolder = subCategoryNameHolder;
	}
	public int getSubCatCategory() {
		return subCatCategory;
	}
	public void setSubCatCategory(int subCatCategory) {
		this.subCatCategory = subCatCategory;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubCatCode() {
		return subCatCode;
	}
	public void setSubCatCode(String subCatCode) {
		this.subCatCode = subCatCode;
	}
	public String getSubCatDesc() {
		return subCatDesc;
	}
	public void setSubCatDesc(String subCatDesc) {
		this.subCatDesc = subCatDesc;
	}
	

}
