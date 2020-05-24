package com.gdu.cashbook.vo;

public class Cashbook {
	private int cashbookNo;
	private String memberId;
	private String cashbookTitle;
	private String cashbookDate;
	private String cashbookGoal;
	private String cashbookImg;
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCashbookTitle() {
		return cashbookTitle;
	}
	public void setCashbookTitle(String cashbookTitle) {
		this.cashbookTitle = cashbookTitle;
	}
	public String getCashbookYear() {
		return cashbookDate;
	}
	public void setCashbookYear(String cashbookDate) {
		this.cashbookDate = cashbookDate;
	}
	public String getCashbookGoal() {
		return cashbookGoal;
	}
	public void setCashbookGoal(String cashbookGoal) {
		this.cashbookGoal = cashbookGoal;
	}
	public String getCashbookImg() {
		return cashbookImg;
	}
	public void setCashbookImg(String cashbookImg) {
		this.cashbookImg = cashbookImg;
	}
	@Override
	public String toString() {
		return "Cashbook [cashbookNo=" + cashbookNo + ", memberId=" + memberId + ", cashbookTitle=" + cashbookTitle
				+ ", cashbookDate=" + cashbookDate + ", cashbookGoal=" + cashbookGoal + "]";
	}
}
