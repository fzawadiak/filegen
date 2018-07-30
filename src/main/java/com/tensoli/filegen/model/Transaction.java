package com.tensoli.filegen.model;

public class Transaction extends Party {
	String reference = "T"+System.nanoTime();
	String currency;
	Double amount;
	String title;
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLine1() {
		return name;
	}
	public String getLine2() {
		return address;
	}
	public String getLine3() {
		return zip + " " + city;
	}
	public String getLine4() {
		return country;
	}
	public String getAmountText() {
		return String.format("%.2f",amount).replace('.', ',');
	}
}
