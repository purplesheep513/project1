package com.koreait.semipro;

public class MemberDupChk {
String ID;
String email;


public MemberDupChk() {
}
public MemberDupChk(String iD, String email) {
	ID = iD;
	this.email = email;
}
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
}
