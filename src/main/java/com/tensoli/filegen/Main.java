package com.tensoli.filegen;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class Main {
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			usage();
			return;
		}
		
		Properties p = new Properties();
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		p.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
	    Velocity.init(p);
		
		String[] newargs = new String[args.length-1];
		String command = args[0];
		System.arraycopy(args, 1, newargs, 0, newargs.length);
		
		if("create".equals(command))
			Create.main(newargs);
		else if("process".equals(command))
			ProcessPain001.main(newargs);
		else if("monitor".equals(command))
			MonitorPain001.main(newargs);
		else if("prepare".equals(command))
			Prepare.main(newargs);
		else
			usage();

	}
	
	private static void usage() {
		System.err.println("Usage: filegen <create|process|monitor|prepare>");
	}
}
