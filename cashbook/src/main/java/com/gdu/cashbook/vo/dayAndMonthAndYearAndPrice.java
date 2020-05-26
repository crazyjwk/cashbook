package com.gdu.cashbook.vo;

public class dayAndMonthAndYearAndPrice {
	private int day;
	private int month;
	private int year;
	private int price;
	private String date;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
		return "MonthAndPrice [day=" + day + ", month=" + month + ", year=" + year + ", price=" + price + ", date="
				+ date + "]";
	}
	
}
