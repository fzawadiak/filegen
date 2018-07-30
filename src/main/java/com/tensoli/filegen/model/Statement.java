package com.tensoli.filegen.model;

public class Statement {	
	String reference = "S"+System.nanoTime();
	String account;
	String currency;
	String openingDate;
	String closingDate;
	Integer number = 0;
	Double openingBalance = 0.0;
	Double closingBalance = 0.0;
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}
	public String getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	public Double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(Double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public Double getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(Double closingBalance) {
		this.closingBalance = closingBalance;
	}
	public String getShortDate() {
		return openingDate.substring(2);
	}
	public String getOpeningBalanceText() {
		return String.format("%.2f",openingBalance).replace('.', ',');
	}
	public String getClosingBalanceText() {
		return String.format("%.2f",closingBalance).replace('.', ',');
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
}
