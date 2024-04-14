package model;

public class PurchaseOrder {
	int id;
	String description;
	String purchaseTypes;
	int subCategory;
	float unitPrice;
	int quantity;
	int unitMeasurement;
	float total;
	String purchaeOrderstatus;
	String createdAt;
	String deliveryDate;
	int supplier;
	String poCode;
	String subCat;
	private String orderDate;
	private String authStatus;
	private String maker;
	private String checker;
	private String subCatNameHolder;
	private String insepectedBy;
	private String checkedBy;
	private String fcurrency1;
	private String fcurrency2;
	private String rejection_reaon;
	
	public String getRejection_reaon() {
		return rejection_reaon;
	}
	public void setRejection_reaon(String rejection_reaon) {
		this.rejection_reaon = rejection_reaon;
	}
	public String getFcurrency1() {
		return fcurrency1;
	}
	public void setFcurrency1(String fcurrency1) {
		this.fcurrency1 = fcurrency1;
	}
	public String getFcurrency2() {
		return fcurrency2;
	}
	public void setFcurrency2(String fcurrency2) {
		this.fcurrency2 = fcurrency2;
	}
	public String getInsepectedBy() {
		return insepectedBy;
	}
	public void setInsepectedBy(String insepectedBy) {
		this.insepectedBy = insepectedBy;
	}
	public String getCheckedBy() {
		return checkedBy;
	}
	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	public String getSubCatNameHolder() {
		return subCatNameHolder;
	}
	public void setSubCatNameHolder(String subCatNameHolder) {
		this.subCatNameHolder = subCatNameHolder;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
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
	public String getSubCat() {
		return subCat;
	}
	public void setSubCat(String subCat) {
		this.subCat = subCat;
	}
	//holder
	int unRegisteredItem;
	
	//subCatModeHolder
	public int getUnRegisteredItem() {
		return unRegisteredItem;
	}
	public void setUnRegisteredItem(int unRegisteredItem) {
		this.unRegisteredItem = unRegisteredItem;
	}
	public String getPoCode() {
		return poCode;
	}
	public void setPoCode(String poCode) {
		this.poCode = poCode;
	}
	public int getSupplier() {
		return supplier;
	}
	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPurchaseTypes() {
		return purchaseTypes;
	}
	public void setPurchaseTypes(String purchaseTypes) {
		this.purchaseTypes = purchaseTypes;
	}
	public int getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(int subCategory) {
		this.subCategory = subCategory;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnitMeasurement() {
		return unitMeasurement;
	}
	public void setUnitMeasurement(int unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public String getPurchaeOrderstatus() {
		return purchaeOrderstatus;
	}
	public void setPurchaeOrderstatus(String purchaeOrderstatus) {
		this.purchaeOrderstatus = purchaeOrderstatus;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	
}