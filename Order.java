package com.koreait.semipro;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
	private SimpleStringProperty productName;
	private SimpleIntegerProperty productNum, price;
	
	public Order(String productName, int productNum, int price) {
		this.productName = new SimpleStringProperty(productName);
		this.productNum = new SimpleIntegerProperty(productNum);
		this.price = new SimpleIntegerProperty(price);
	}

	public Order() {
		this.productName = new SimpleStringProperty();
		this.productNum = new SimpleIntegerProperty();
		this.price = new SimpleIntegerProperty();
	}

	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String productName) {
		this.productName.set(productName);;
	}

	public int getProductNum() {
		return productNum.get();
	}

	public void setProductNum(int productNum) {
		this.productNum.set(productNum);;
	}

	public int getPrice() {
		return price.get();
	}

	public void setPrice(int price) {
		this.price.set(price);;
	}

	
}
