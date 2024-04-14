package model;

public class AssetClass {
private int id;
private String assetClassCode;
private String assetClassDesc;
private String authStatus;
private String maker;
private String checker;
private String createdAt;
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
public String getAssetClassCode() {
	return assetClassCode;
}
public void setAssetClassCode(String assetClassCode) {
	this.assetClassCode = assetClassCode;
}
public String getAssetClassDesc() {
	return assetClassDesc;
}
public void setAssetClassDesc(String assetClassDesc) {
	this.assetClassDesc = assetClassDesc;
}

}
