package com.tensoli.filegen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterDescription;
import com.tensoli.filegen.data.PartyHolder;

public class Create {
	@Parameter
	public List<String> parameters = new ArrayList<>();
	@Parameter(names = "-amount", description = "Amount for payments")
	public Double amount; 
	@Parameter(names = "-currency", description = "Currency for payments")
	public String currency;
	@Parameter(names = "-title", description = "Title for payments")
	public String title;
	@Parameter(names = "-split", description = "Split by channel based on CODE")
	public boolean split = false;
	@Parameter(names = "-delay", description = "Delay in ms between file creation")
	public Integer delay;
	@Parameter(names = "-accounts", description = "Location of accounts CSV")
	public String accounts = "accounts.csv";
	
	public static void main(String[] args) throws Exception {
		Create options = new Create();
        JCommander cmd = JCommander.newBuilder()
            .addObject(options)
            .build();
        cmd.parse(args);
        
		if(options.parameters.size() != 3) {
			usage(cmd);
			return;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		
		PartyHolder parties = new PartyHolder();
		parties.readFromCSV(options.accounts);
	    
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
	    	usage(cmd);
	    	return;
	    }
	    
	    if(options.amount!=null)
	    	creator.setAmount(options.amount);
	    if(options.title!=null)
	    	creator.setTitle(options.title);
	    if(options.currency!=null)
	    	creator.setCurrency(options.currency);
	    creator.setSplit(options.split);
	    
	    String suffix = creator.getFileSuffix();
	    String ts = formatter.format(new Date());
	    
	    creator.setParties(parties);
	    for(int fn=0; fn<files; fn++) {
	    	String fname = String.format("%s-%s-%06d.%s", fileType, ts, fn, suffix);
	    	creator.generateFile(fname, count);
	    	
	    	if(options.delay!=null)
	    		Thread.sleep(options.delay);
	    }
	}
	
	private static void usage(JCommander cmd) {
		System.err.println("Usage: filegen create [mt101|mt940|pain001] <payments> <files> [options]");
		System.err.println("Options:");
		for(ParameterDescription opt : cmd.getParameters()) {
			System.err.println(String.format("  %-10s %s", opt.getLongestName(), opt.getDescription()));
		}
	}
}
