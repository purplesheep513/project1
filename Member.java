package com.koreait.semipro;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {
	private SimpleStringProperty memName, memId, memEmail;
	private SimpleIntegerProperty idx, coupon10, coupon30;
	
	public Member() {
		this.idx = new SimpleIntegerProperty();
		this.memName = new SimpleStringProperty();
		this.memId = new SimpleStringProperty();
		this.memEmail = new SimpleStringProperty();
		this.coupon10 = new SimpleIntegerProperty();
		this.coupon30 = new SimpleIntegerProperty();
	}
	

	public Member(int idx, String memName, String MemId, String memEmail, int coupon30, int coupon10) {
		this.idx = new SimpleIntegerProperty(idx); 
		this.memName = new SimpleStringProperty(memName);
		this.memId = new SimpleStringProperty(MemId);
		this.memEmail = new SimpleStringProperty(memEmail);
		this.coupon30 = new SimpleIntegerProperty(coupon30);
		this.coupon10 = new SimpleIntegerProperty(coupon10);
	}
	
	public int getIdx() {
		return idx.get();
	}
	public void setIdx(int idx) {
		this.idx.set(idx);
	}
	public String getMemName() {
		return memName.get();
	}
	public void setMemName(String memName) {
		this.memName.set(memName);
	}
	public String getMemId() {
		return memId.get();
	}
	public void setMemId(String memId) {
		this.memId.set(memId);
	}
	public String getMemEmail() {
		return memEmail.get();
	}
	public void setMemEmail(String memEmail) {
		this.memEmail.set(memEmail);
	}
	public int getCoupon10() {
		return coupon10.get();
	}
	public void setCoupon10(int coupon10) {
		this.coupon10.set(coupon10);
	}
	public int getCoupon30() {
		return coupon30.get();
	}
	public void setCoupon30(int coupon30) {
		this.coupon30.set(coupon30);
	}
	
}
