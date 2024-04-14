package model;

public class Asset {
private int id;
private String assetClass;
private String assetCategory;
private String assetSubCategory;
private String purchaseOrder;
private String dateOfPurchase;
private String model;
private String tagNumber;
private String assignedTo;
private String assignedToType;
private double purchaseCost;
private String storeKeeper;
private String dept;
private String purchaserName;
private String status;
private String remark;
private String serialNum;
private String depr;
private int assSubCat;
private float unitPrice;
private String rejection_reaon;




public String getRejection_reaon() {
	return rejection_reaon;
}
public void setRejection_reaon(String rejection_reaon) {
	this.rejection_reaon = rejection_reaon;
}
public float getUnitPrice() {
	return unitPrice;
}
public void setUnitPrice(float unitPrice) {
	this.unitPrice = unitPrice;
}
public int getAssSubCat() {
	return assSubCat;
}
public void setAssSubCat(int assSubCat) {
	this.assSubCat = assSubCat;
}
private String assetSpecificText;




public String getAssetSpecificText() {
	return assetSpecificText;
}
public void setAssetSpecificText(String assetSpecificText) {
	this.assetSpecificText = assetSpecificText;
}
/**************Handling things**********/


public String getDepr() {
	return depr;
}
public void setDepr(String depr) {
	this.depr = depr;
}
public String getAssetClass() {
	return assetClass;
}
public void setAssetClass(String assetClass) {
	this.assetClass = assetClass;
}
public String getAssetCategory() {
	return assetCategory;
}
public void setAssetCategory(String assetCategory) {
	this.assetCategory = assetCategory;
}
public String getAssetSubCategory() {
	return assetSubCategory;
}
public void setAssetSubCategory(String assetSubCategory) {
	this.assetSubCategory = assetSubCategory;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getPurchaseOrder() {
	return purchaseOrder;
}
public void setPurchaseOrder(String purchaseOrder) {
	this.purchaseOrder = purchaseOrder;
}
public String getDateOfPurchase() {
	return dateOfPurchase;
}
public void setDateOfPurchase(String dateOfPurchase) {
	this.dateOfPurchase = dateOfPurchase;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}
public String getTagNumber() {
	return tagNumber;
}
public void setTagNumber(String tagNumber) {
	this.tagNumber = tagNumber;
}
public String getAssignedTo() {
	return assignedTo;
}
public void setAssignedTo(String assignedTo) {
	this.assignedTo = assignedTo;
}
public String getAssignedToType() {
	return assignedToType;
}
public void setAssignedToType(String assignedToType) {
	this.assignedToType = assignedToType;
}
public double getPurchaseCost() {
	return purchaseCost;
}
public void setPurchaseCost(double purchaseCost) {
	this.purchaseCost = purchaseCost;
}
public String getStoreKeeper() {
	return storeKeeper;
}
public void setStoreKeeper(String storeKeeper) {
	this.storeKeeper = storeKeeper;
}
public String getDept() {
	return dept;
}
public void setDept(String dept) {
	this.dept = dept;
}
public String getPurchaserName() {
	return purchaserName;
}
public void setPurchaserName(String purchaserName) {
	this.purchaserName = purchaserName;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getSerialNum() {
	return serialNum;
}
public void setSerialNum(String serialNum) {
	this.serialNum = serialNum;
}

}

