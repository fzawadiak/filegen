package com.tensoli.filegen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import com.tensoli.filegen.pain002.PaymentLevelBuilder;

public class MonitorPain001 {
	public static void main(String[] args) throws Exception {
		if(args.length != 2) {
			usage();
			return;
		}
		
		Path indir = Paths.get(args[0]);
		Path outdir = Paths.get(args[1]);
		
		FileSystem fs = indir.getFileSystem();
		WatchService watcher = fs.newWatchService();
		indir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		
		System.out.println("Starting watching directory: "+indir);
		
		for(;;) {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event: key.pollEvents()) {
				Path infile = indir.resolve((Path)event.context());
				Path outfile = outdir.resolve(infile.getFileName());
				
				System.out.println(String.format("Processing %s -> %s", infile, outfile));
				
				try {
					process(infile.toFile(), outfile.toFile());
				} catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	private static void process(File infile, File outfile) throws Exception {
		FileReader in = new FileReader(infile);
		FileWriter out = new FileWriter(outfile);
		
		PaymentLevelBuilder builder = new PaymentLevelBuilder();
		builder.parse(in, out);
		
		in.close();
		out.close();
	}
	
	private static void usage() {
		System.err.println("Usage: filegen monitor <indir> <outdir>");
	}
}
