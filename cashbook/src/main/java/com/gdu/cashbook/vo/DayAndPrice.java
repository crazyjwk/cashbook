package com.gdu.cashbook.vo;

public class DayAndPrice {
	private int day;
	private int price;
	private String date;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DayAndPrice [day=" + day + ", price=" + price + "]";
	}
}
