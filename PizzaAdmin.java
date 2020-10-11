package com.koreait.semipro;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PizzaAdmin {
	private SimpleIntegerProperty pizza_idx;
	private SimpleStringProperty pizza_name, pizza_info, pizza_price;
	
	public PizzaAdmin() {
		this.pizza_idx =  new SimpleIntegerProperty();
		this.pizza_name = new SimpleStringProperty();
		this.pizza_info = new SimpleStringProperty();
		this.pizza_price = new SimpleStringProperty();
	}
	
	public PizzaAdmin(int pizza_idx, String pizza_name, String pizza_info,
			String pizza_price) {
		this.pizza_idx = new SimpleIntegerProperty(pizza_idx);
		this.pizza_name = new SimpleStringProperty(pizza_name);
		this.pizza_info = new SimpleStringProperty(pizza_info);
		this.pizza_price = new SimpleStringProperty(pizza_price);
	}
	public int getPizza_idx() {
		return pizza_idx.get();
	}
	public void setPizza_idx(int pizza_idx) {
		this.pizza_idx.set(pizza_idx);;
	}
	public String getPizza_name() {
		return pizza_name.get();
	}
	public void setPizza_name(String pizza_name) {
		this.pizza_name.set(pizza_name);;
	}
	public String getPizza_info() {
		return pizza_info.get();
	}
	public void setPizza_info(String pizza_info) {
		this.pizza_info.set(pizza_info);;
	}
	public String getPizza_price() {
		return pizza_price.get();
	}
	public void setPizza_price(String pizza_price) {
		this.pizza_price.set(pizza_price);;
	}
	
	
}
