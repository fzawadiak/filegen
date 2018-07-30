package com.tensoli.filegen.pain001;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.tensoli.filegen.model.Message;
import com.tensoli.filegen.model.Party;
import com.tensoli.filegen.model.Transaction;

public class SerializerPain001 {
	Template header = Velocity.getTemplate("templates/pain001-hd.vm");
	Template transaction = Velocity.getTemplate("templates/pain001-tx.vm");
	Template footer = Velocity.getTemplate("templates/pain001-ft.vm");
	
	public String serialize(Party msg, List<Transaction> txs) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		context.put("count", txs.size());

		StringWriter sw = new StringWriter();
		header.merge(context, sw);
		for(Transaction tx : txs) {
			context.put("transaction", tx);
			transaction.merge(context, sw);
		}
		footer.merge(context, sw);
	
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
