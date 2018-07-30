package com.tensoli.filegen.pain002;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.tensoli.filegen.model.PaymentBlock;
import com.tensoli.filegen.model.PaymentInfo;

public class SerializerPain002 {
	Template header = Velocity.getTemplate("templates/pain002-hd.vm");
	Template transaction = Velocity.getTemplate("templates/pain002-tx.vm");
	Template footer = Velocity.getTemplate("templates/pain002-ft.vm");
	
	public String serialize(PaymentBlock msg, List<PaymentInfo> txs) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		context.put("count", txs.size());

		StringWriter sw = new StringWriter();
		header.merge(context, sw);
		for(PaymentInfo tx : txs) {
			context.put("transaction", tx);
			transaction.merge(context, sw);
		}
		footer.merge(context, sw);
	
		return sw.toString();
	}
	
	public void serializeHeader(PaymentBlock msg, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		
		header.merge(context, w);
	}
	
	public void serializeTransaction(PaymentInfo tx, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("transaction", tx);
		
		transaction.merge(context, w);
	}
	
	public void serializeFooter(PaymentBlock msg, Writer w) {
		VelocityContext context = new VelocityContext();
		context.put("message", msg);
		
		footer.merge(context, w);
	}
}
