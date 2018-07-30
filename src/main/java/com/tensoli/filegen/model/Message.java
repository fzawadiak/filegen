package com.tensoli.filegen.model;

public class Message extends Party {
	String senderReference = "S"+System.nanoTime();
	String customerReference = "C"+System.nanoTime();
	String executionDate;

	public String getSenderReference() {
		return senderReference;
	}
	public void setSenderReference(String senderReference) {
		this.senderReference = senderReference;
	}
	public String getCustomerReference() {
		return customerReference;
	}
	public void setCustomerReference(String customerReference) {
		this.customerReference = customerReference;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getExecutionDate() {
		return executionDate;
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
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
