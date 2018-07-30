package com.tensoli.filegen;

import java.io.File;

import com.tensoli.filegen.data.PartyHolder;
import com.tensoli.filegen.model.PartyCode;

public class Prepare {
	public static void main(String[] args) throws Exception {
		PartyHolder parties = new PartyHolder();
		parties.readFromCSV("accounts.csv");
		
		for(PartyCode party : parties.getParties()) {
			String code = party.getCode();
			File dir = new File(code);
			if(!dir.exists())
				dir.mkdirs();
		}
	}
}
