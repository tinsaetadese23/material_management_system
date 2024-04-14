package model;

public class Supplier {
int id;
String supplierName;
String contactPerson;
String contactNumber;
String email;
String address;
String zipCode;
String website;
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
public String getSupplierName() {
	return supplierName;
}
public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
}
public String getContactPerson() {
	return contactPerson;
}
public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
}
public String getContactNumber() {
	return contactNumber;
}
public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getZipCode() {
	return zipCode;
}
public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}
public String getWebsite() {
	return website;
}
public void setWebsite(String website) {
	this.website = website;
}


}
