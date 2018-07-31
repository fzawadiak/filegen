package com.tensoli.filegen.mt101;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.tensoli.filegen.model.Message;
import com.tensoli.filegen.model.Transaction;

public class SerializerMt101 {
	static Template header = Velocity.getTemplate("templates/mt101-hd.vm");
	static Template transaction = Velocity.getTemplate("templates/mt101-tx.vm");
	static Template footer = Velocity.getTemplate("templates/mt101-ft.vm");
	
	public String serialize(Message msg, List<Transaction> txs) {
		StringWriter sw = new StringWriter();
		
		serializeHeader(msg, txs.size(), sw);
		
		for(Transaction tx : txs)
			serializeTransaction(msg,  tx, sw);
		
		serializeFooter(msg, txs.size(), sw);
		
		return sw.toString();
	}
	
	public void serializeHeader(Message msg, int count, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		context.put("count", count);
		
		header.merge(context, w);
	}
	
	public void serializeTransaction(Message msg, Transaction tx, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		context.put("transaction", tx);
		
		transaction.merge(context, w);
	}
	
	public void serializeFooter(Message msg, int count, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		context.put("count", count);
		
		footer.merge(context, w);
	}
}
