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

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.tensoli.filegen.pain002.PaymentLevelBuilder;

public class MonitorPain001 implements FileAlterationListener {
	final Path indir;
	final Path outdir;
	
	public MonitorPain001(Path indir, Path outdir) {
		this.indir = indir;
		this.outdir = outdir;
	}
	
	public static void main(String[] args) throws Exception {
		if(args.length != 3) {
			usage();
			return;
		}
		
		String method = args[0];
		Path indir = Paths.get(args[1]);
		Path outdir = Paths.get(args[2]);
		
		MonitorPain001 monitor = new MonitorPain001(indir, outdir);
		
		if("watch".equals(method))
			monitor.watchFileWatcher();
		else if("pool".equals(method))
			monitor.watchApache();
		else
			usage();
	}
	
	private void watchApache() throws Exception {
		System.out.println("Starting pooling directory: "+indir);
		
		FileAlterationObserver fao = new FileAlterationObserver(indir.toFile());
		fao.addListener(this);
		FileAlterationMonitor monitor = new FileAlterationMonitor(1000);
		monitor.addObserver(fao);
		monitor.start();
	}
	
	private void process(Path infile, Path outfile) {
		System.out.println(String.format("Processing %s -> %s", infile, outfile));
		
		try {
			process(infile.toFile(), outfile.toFile());
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private void watchFileWatcher() throws Exception {
		System.out.println("Starting watching directory: "+indir);
		
		FileSystem fs = indir.getFileSystem();
		WatchService watcher = fs.newWatchService();
		indir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		
		for(;;) {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event: key.pollEvents()) {
				Path infile = indir.resolve((Path)event.context());
				Path outfile = outdir.resolve(infile.getFileName());
				
				process(infile, outfile);
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
		System.err.println("Usage: filegen monitor <watch|pool> <indir> <outdir>");
	}

	@Override
	public void onDirectoryChange(File arg0) {
		
	}

	@Override
	public void onDirectoryCreate(File arg0) {
		
	}

	@Override
	public void onDirectoryDelete(File arg0) {

	}

	@Override
	public void onFileChange(File arg0) {
	
	}

	@Override
	public void onFileCreate(File file) {
		Path infile = file.toPath();
		Path outfile = outdir.resolve(infile.getFileName());
		
		process(infile, outfile);
	}

	@Override
	public void onFileDelete(File arg0) {

	}

	@Override
	public void onStart(FileAlterationObserver arg0) {

	}

	@Override
	public void onStop(FileAlterationObserver arg0) {

	}
}
