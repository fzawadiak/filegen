package com.tensoli.filegen;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.tensoli.filegen.data.PartyHolder;

public class Main {
	public static void main(String[] args) throws Exception {
		if(args.length != 3) {
			usage();
			return;
		}
		
		PartyHolder parties = new PartyHolder();
		parties.readFromCSV("accounts.csv");
		
		Properties p = new Properties();
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
	    Velocity.init(p);
	    
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
	    
	    creator.setParties(parties);
	    for(int fn=0; fn<files; fn++) {
	    	String fname = String.format("%s-%06d.%s", fileType, fn, suffix);
	    	creator.generateFile(fname, count);
	    }
	}
	
	private static void usage() {
		System.err.println("Usage: filegen [mt101|mt940|pain001] <payments> <files>");
	}
}
