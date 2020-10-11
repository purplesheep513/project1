package com.koreait.semipro;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DrinkAdmin {
	private SimpleIntegerProperty drink_idx;
	private SimpleStringProperty drink_name, one_price, half_price;
	
	public DrinkAdmin() {
		this.drink_idx =  new SimpleIntegerProperty();
		this.drink_name = new SimpleStringProperty();
		this.one_price = new SimpleStringProperty();
		this.half_price = new SimpleStringProperty();

	}
	public DrinkAdmin(int drink_idx, String drink_name, String one_price, String half_price) {
		
		this.drink_idx = new SimpleIntegerProperty(drink_idx);
		this.drink_name = new SimpleStringProperty(drink_name);
		this.one_price = new SimpleStringProperty(one_price);
		this.half_price = new SimpleStringProperty(half_price);
	}
	public String getDrink_name() {
		return drink_name.get();
	}
	public void setDrink_name(String drink_name) {
		this.drink_name.set(drink_name);;
	}
	public int getDrink_idx() {
		return drink_idx.get();
	}
	public void setDrink_idx(int drink_idx) {
		this.drink_idx.set(drink_idx);;
	}
	public String getOne_price() {
		return one_price.get();
	}
	public void setOne_price(String one_price) {
		this.one_price.set(one_price);;
	}
	public String getHalf_price() {
		return half_price.get();
	}
	public void setHalf_price(String half_price) {
		this.half_price.set(half_price);;
	}
	
	
}
