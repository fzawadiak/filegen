package com.tensoli.filegen;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tensoli.filegen.data.PartyHolder;

public abstract class AbstractCreator {
	private SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
	PartyHolder parties;
	
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
}
