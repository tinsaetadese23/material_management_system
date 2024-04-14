package model;

public class Message {
private String successMsg;
private String errorMsg;
private String info;

public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public String getSuccessMsg() {
	return successMsg;
}
public void setSuccessMsg(String successMsg) {
	this.successMsg = successMsg;
}
public String getErrorMsg() {
	return errorMsg;
}
public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
}
}
