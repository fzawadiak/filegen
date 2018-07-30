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
	Template header = Velocity.getTemplate("templates/mt940-hd.vm");
	Template transaction = Velocity.getTemplate("templates/mt940-tx.vm");
	Template footer = Velocity.getTemplate("templates/mt940-ft.vm");
	
	public String serialize(Statement msg, List<Transaction> txs) {
		VelocityContext context = new VelocityContext();
		context.put("statement", msg);
		context.put("count", txs.size());

		double delta = 0.0;
		
		StringWriter sw = new StringWriter();
		header.merge(context, sw);
		for(Transaction tx : txs) {
			delta += tx.getAmount();
			context.put("transaction", tx);
			context.put("seq", System.nanoTime());
			transaction.merge(context, sw);
		}
		msg.setClosingBalance(msg.getOpeningBalance()+delta);
		footer.merge(context, sw);
	
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
		
		transaction.merge(context, w);
	}
	
	public void serializeFooter(Statement msg, int count, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("statement", msg);
		context.put("count", count);
		
		footer.merge(context, w);
	}
}
