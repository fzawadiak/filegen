package com.tensoli.filegen.mt940;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.tensoli.filegen.model.Statement;
import com.tensoli.filegen.model.Transaction;

public class SerializerMt940 {
	static int seq = 0;
	
	static Template header = Velocity.getTemplate("templates/mt940-hd.vm");
	static Template transaction = Velocity.getTemplate("templates/mt940-tx.vm");
	static Template footer = Velocity.getTemplate("templates/mt940-ft.vm");
	
	public String serialize(Statement msg, List<Transaction> txs) {
		StringWriter sw = new StringWriter();
		
		serializeHeader(msg, txs.size(), sw);
		
		for(Transaction tx : txs)
			serializeTransaction(msg,  tx, sw);
		
		serializeFooter(msg, txs.size(), sw);
		
		return sw.toString();
	}
	
	public void serializeHeader(Statement msg, int count, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("statement", msg);
		context.put("count", count);
		
		header.merge(context, w);
	}
	
	public void serializeTransaction(Statement msg, Transaction tx, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("statement", msg);
		context.put("transaction", tx);
		context.put("seq", seq++);
		
		transaction.merge(context, w);
	}
	
	public void serializeFooter(Statement msg, int count, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("statement", msg);
		context.put("count", count);
		
		footer.merge(context, w);
	}
}
