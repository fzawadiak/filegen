package com.tensoli.filegen;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tensoli.filegen.data.PartyHolder;

public abstract class AbstractCreator {
	private SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
	PartyHolder parties;
	
	String title = "Payment";
	Double amount = 1500.00;
	String currency;
	boolean split = false;
	
	public abstract void generateFile(String name, int payments) throws IOException;
	public abstract String getFileSuffix();
	
	public PartyHolder getParties() {
		return parties;
	}
	public void setParties(PartyHolder parties) {
		this.parties = parties;
	}
	public String getDate() {
		return formatter.format(new Date());
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public boolean isSplit() {
		return split;
	}
	public void setSplit(boolean split) {
		this.split = split;
	}
}
