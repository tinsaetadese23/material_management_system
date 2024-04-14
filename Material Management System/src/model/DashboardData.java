package model;

public class DashboardData {

	private int id;
	private int totalAsset;
	private int totalAssignedAsset;
	private int totalSupplier;
	private int totalInStockAsset;
	
	public int getTotalInStockAsset() {
		return totalInStockAsset;
	}
	public void setTotalInStockAsset(int totalInStockAsset) {
		this.totalInStockAsset = totalInStockAsset;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalAsset() {
		return totalAsset;
	}
	public void setTotalAsset(int totalAsset) {
		this.totalAsset = totalAsset;
	}
	public int getTotalAssignedAsset() {
		return totalAssignedAsset;
	}
	public void setTotalAssignedAsset(int totalAssignedAsset) {
		this.totalAssignedAsset = totalAssignedAsset;
	}
	public int getTotalSupplier() {
		return totalSupplier;
	}
	public void setTotalSupplier(int totalSupplier) {
		this.totalSupplier = totalSupplier;
	}
	
}
