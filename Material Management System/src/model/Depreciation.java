package model;

public class Depreciation {

private int id;
private String code;
private String description;
private float rate;
private String createdAt;
private String maker;
private String checker;



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
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public float getRate() {
	return rate;
}
public void setRate(float rate) {
	this.rate = rate;
}
}
