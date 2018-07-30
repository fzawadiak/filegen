package com.tensoli.filegen;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tensoli.filegen.data.PartyHolder;

public class Create {
	public static void main(String[] args) throws Exception {
		if(args.length != 3) {
			usage();
			return;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		
		PartyHolder parties = new PartyHolder();
		parties.readFromCSV("accounts.csv");
	    
	    String fileType = args[0];
	    int count = Integer.parseInt(args[1]);
	    int files = Integer.parseInt(args[2]);
	    
	    AbstractCreator creator;
	    
	    if("mt101".equals(fileType)) {
	    	creator = new CreateMt101();
	    } else if("pain001".equals(fileType)) {
	    	creator = new CreatePain001();
	    } else if("mt940".equals(fileType)) {
	    	creator = new CreateMt940();
		} else {
	    	usage();
	    	return;
	    }
	    
	    String suffix = creator.getFileSuffix();
	    String ts = formatter.format(new Date());
	    
	    creator.setParties(parties);
	    for(int fn=0; fn<files; fn++) {
	    	String fname = String.format("%s-%s-%06d.%s", fileType, ts, fn, suffix);
	    	creator.generateFile(fname, count);
	    }
	}
	
	private static void usage() {
		System.err.println("Usage: filegen create [mt101|mt940|pain001] <payments> <files>");
	}
}
