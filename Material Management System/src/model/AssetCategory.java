package model;

public class AssetCategory {

	private int id;
	private String assetCatName;
	private String assetCatDesc;
	private String assetCatMaint;
	private String assetCatLife;
	private int  assetCatCatDepr;
	private int assetClass;
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
	private String assetClassNameHolder;
	private String deprNameHolder;
	
	
	
	
	public String getDeprNameHolder() {
		return deprNameHolder;
	}
	public void setDeprNameHolder(String deprNameHolder) {
		this.deprNameHolder = deprNameHolder;
	}
	public String getAssetClassNameHolder() {
		return assetClassNameHolder;
	}
	public void setAssetClassNameHolder(String assetClassNameHolder) {
		this.assetClassNameHolder = assetClassNameHolder;
	}
	public int getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(int assetClass) {
		this.assetClass = assetClass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAssetCatName() {
		return assetCatName;
	}
	public void setAssetCatName(String assetCatName) {
		this.assetCatName = assetCatName;
	}
	public String getAssetCatDesc() {
		return assetCatDesc;
	}
	public void setAssetCatDesc(String assetCatDesc) {
		this.assetCatDesc = assetCatDesc;
	}
	public String getAssetCatMaint() {
		return assetCatMaint;
	}
	public void setAssetCatMaint(String assetCatMaint) {
		this.assetCatMaint = assetCatMaint;
	}
	public String getAssetCatLife() {
		return assetCatLife;
	}
	public void setAssetCatLife(String assetCatLife) {
		this.assetCatLife = assetCatLife;
	}
	public int getAssetCatCatDepr() {
		return assetCatCatDepr;
	}
	public void setAssetCatCatDepr(int assetCatCatDepr) {
		this.assetCatCatDepr = assetCatCatDepr;
	}

}
