package com.koreait.semipro;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ToppingAdmin {
	private SimpleIntegerProperty topping_idx;
	private SimpleStringProperty topping_name, topping_price;
	
	
	public ToppingAdmin() {
		this.topping_idx =  new SimpleIntegerProperty();
		this.topping_name = new SimpleStringProperty();
		this.topping_price = new SimpleStringProperty();
	}
	public ToppingAdmin(int topping_idx, String topping_name,
			String topping_price) {
		super();
		this.topping_idx = new SimpleIntegerProperty(topping_idx);
		this.topping_name =  new SimpleStringProperty(topping_name);
		this.topping_price =  new SimpleStringProperty(topping_price);
	}
	public int getTopping_idx() {
		return topping_idx.get();
	}
	public void setTopping_idx(int topping_idx) {
		this.topping_idx.set(topping_idx);
	}
	public String getTopping_name() {
		return topping_name.get();
	}
	public void setTopping_name(String topping_name) {
		this.topping_name.set(topping_name);;
	}
	public String getTopping_price() {
		return topping_price.get();
	}
	public void setTopping_price(String topping_price) {
		this.topping_price.set(topping_price);
	}
	
	
}
