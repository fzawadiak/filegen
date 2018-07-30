package com.tensoli.filegen;

import java.io.FileReader;
import java.io.FileWriter;

import com.tensoli.filegen.pain002.AbstractBuilder;
import com.tensoli.filegen.pain002.GroupLevelBuilder;
import com.tensoli.filegen.pain002.PaymentLevelBuilder;

public class ProcessPain001 {
	public static void main(String[] args) throws Exception {
		if(args.length != 3) {
			usage();
			return;
		}
		
		String type = args[0];
		AbstractBuilder builder = null;
		
		if("group".equals(type))
			builder = new GroupLevelBuilder();
		else if("payment".equals(type))
			builder = new PaymentLevelBuilder();
		else {
			usage();
			return;
		}
		
		FileReader in = new FileReader(args[1]);
		FileWriter out = new FileWriter(args[2]);
		
		builder.parse(in, out);
		
		in.close();
		out.close();
	}
	
	private static void usage() {
		System.err.println("Usage: filegen process <group|payment> <infile> <outfile>");
	}
}
