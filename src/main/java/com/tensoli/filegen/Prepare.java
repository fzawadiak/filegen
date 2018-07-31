package com.tensoli.filegen;

import java.io.File;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.beust.jcommander.ParameterException;
import com.tensoli.filegen.data.PartyHolder;
import com.tensoli.filegen.model.PartyCode;

public class Prepare {
	@Parameter(names = "-accounts", description = "Location of accounts CSV")
	public String accounts = "accounts.csv";
	
	public static void main(String[] args) throws Exception {
		Prepare options = new Prepare();
        JCommander cmd = JCommander.newBuilder()
            .addObject(options)
            .build();
        
        try {
        	cmd.parse(args);
        } catch(ParameterException e) {
			usage(cmd);
			return;
		}
		
		PartyHolder parties = new PartyHolder();
		parties.readFromCSV(options.accounts);
		
		for(PartyCode party : parties.getParties()) {
			String code = party.getCode();
			File dir = new File(code);
			if(!dir.exists())
				dir.mkdirs();
		}
	}
	
	private static void usage(JCommander cmd) {
		System.err.println("Usage: filegen prepare [options]");
		System.err.println("Options:");
		for(ParameterDescription opt : cmd.getParameters()) {
			System.err.println(String.format("  %-10s %s", opt.getLongestName(), opt.getDescription()));
		}
	}
}
