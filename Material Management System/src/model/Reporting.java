package model;

public class Reporting {

	//report category per branch
	private String rbranch;
	private String rcategory;
	private float rtotal;
	private float alltotal;
	private String rfrom;
	private String rto;
	private int total;
	
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	//reports for dashboard
	private String classCode;
	private String classDesc;
	
	
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassDesc() {
		return classDesc;
	}
	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}
	public String getRfrom() {
		return rfrom;
	}
	public void setRfrom(String rfrom) {
		this.rfrom = rfrom;
	}
	public String getRto() {
		return rto;
	}
	public void setRto(String rto) {
		this.rto = rto;
	}
	public float getAlltotal() {
		return alltotal;
	}
	public void setAlltotal(float alltotal) {
		this.alltotal = alltotal;
	}
	public String getRbranch() {
		return rbranch;
	}
	public void setRbranch(String rbranch) {
		this.rbranch = rbranch;
	}
	public String getRcategory() {
		return rcategory;
	}
	public void setRcategory(String rcategory) {
		this.rcategory = rcategory;
	}
	public float getRtotal() {
		return rtotal;
	}
	public void setRtotal(float rtotal) {
		this.rtotal = rtotal;
	}
}
