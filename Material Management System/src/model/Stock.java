package model;

public class Stock {

	int id;
	String recievingCode;
	String decription;
	int SubCategoryCode;  
	float unitPrice;
	float totalPrice;
	int unitMeasurement;
	int supplierId;
	int purchaseId;
	int storeKeepreId;
	String comment;
	String status;
	String createdAt;
	String purchaseDate;
	int quantity;
	String serialNo;
	String assetSpec;
	int issuedTo;
	String sysGeneratedTag;
	//radiobuttonholder
	private String cat;
	private String rejection_reaon;
	
	public String getRejection_reaon() {
		return rejection_reaon;
	}
	public void setRejection_reaon(String rejection_reaon) {
		this.rejection_reaon = rejection_reaon;
	}
	public String getInspectedBy() {
		return inspectedBy;
	}
	public void setInspectedBy(String inspectedBy) {
		this.inspectedBy = inspectedBy;
	}
	public String getCheckedBy() {
		return checkedBy;
	}
	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	private String item;
	private String model;
	
	private int mode;
	private String authStatus;
	private String maker;
	private String checker;
	private String itemStatus;
	private String brnstatus;
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	private String issueRef;
	private float rtotoal;
	private String inspectedBy;
	private String checkedBy;
	private String reieptNo;
	private String reqNo;
	private String fcurrency1;
	private String fcurrency2;
	
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
	public String getReieptNo() {
		return reieptNo;
	}
	public void setReieptNo(String reieptNo) {
		this.reieptNo = reieptNo;
	}
	public float getRtotoal() {
		return rtotoal;
	}
	public void setRtotoal(float rtotoal) {
		this.rtotoal = rtotoal;
	}
	public String getIssueRef() {
		return issueRef;
	}
	public void setIssueRef(String issueRef) {
		this.issueRef = issueRef;
	}
	public String getBrnstatus() {
		return brnstatus;
	}
	public void setBrnstatus(String brnstatus) {
		this.brnstatus = brnstatus;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
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
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getSysGeneratedTag() {
		return sysGeneratedTag;
	}
	public void setSysGeneratedTag(String sysGeneratedTag) {
		this.sysGeneratedTag = sysGeneratedTag;
	}
	public int getIssuedTo() {
		return issuedTo;
	}
	public void setIssuedTo(int issuedTo) {
		this.issuedTo = issuedTo;
	}
	public String getAssetSpec() {
		return assetSpec;
	}
	public void setAssetSpec(String assetSpec) {
		this.assetSpec = assetSpec;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecievingCode() {
		return recievingCode;
	}
	public void setRecievingCode(String recievingCode) {
		this.recievingCode = recievingCode;
	}
	public String getDecription() {
		return decription;
	}
	public void setDecription(String decription) {
		this.decription = decription;
	}
	public int getSubCategoryCode() {
		return SubCategoryCode;
	}
	public void setSubCategoryCode(int subCategoryCode) {
		SubCategoryCode = subCategoryCode;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getUnitMeasurement() {
		return unitMeasurement;
	}
	public void setUnitMeasurement(int unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}
	public int getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	public int getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}
	public int getStoreKeepreId() {
		return storeKeepreId;
	}
	public void setStoreKeepreId(int storeKeepreId) {
		this.storeKeepreId = storeKeepreId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	
}
