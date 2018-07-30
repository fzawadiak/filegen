package com.tensoli.filegen;

import java.io.FileWriter;
import java.io.IOException;

import com.tensoli.filegen.model.Message;
import com.tensoli.filegen.model.Transaction;
import com.tensoli.filegen.mt101.SerializerMt101;

public class CreateMt101 extends AbstractCreator {	
	public void generateFile(String name, int payments) throws IOException {
		FileWriter writer = new FileWriter(name);
		
		SerializerMt101 ser = new SerializerMt101();
		
		Message msg = new Message();
		parties.fillRandomParty(msg);
		msg.setExecutionDate(getDate());
		
		ser.serializeHeader(msg, payments, writer);
		
		for(int i=0; i<payments; i++) {
			Transaction tx = new Transaction();
			tx.setCurrency("CHF");
			tx.setTitle("Payment");
			tx.setAmount(1500.00);
			parties.fillRandomParty(tx);
			
			ser.serializeTransaction(msg, tx, writer);
		}
		
		ser.serializeFooter(msg, payments, writer);
		
		writer.close();
	}
	
	public String getFileSuffix() {
		return "txt";
	}
}
