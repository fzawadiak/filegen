package com.tensoli.filegen;

import java.io.FileWriter;
import java.io.IOException;

import com.tensoli.filegen.model.Message;
import com.tensoli.filegen.model.Transaction;
import com.tensoli.filegen.mt101.SerializerMt101;

public class CreateMt101 extends AbstractCreator {	
	public void generateFile(String name, int payments) throws IOException {
		SerializerMt101 ser = new SerializerMt101();
		
		Message msg = new Message();
		String code = parties.fillRandomParty(msg);
		msg.setExecutionDate(getDate());
		
		FileWriter writer = new FileWriter(code + "/" + name);
		
		ser.serializeHeader(msg, payments, writer);
		
		for(int i=0; i<payments; i++) {
			Transaction tx = new Transaction();
			tx.setCurrency("CHF");
			tx.setTitle("Payment");
			tx.setAmount(1500.00);
			parties.fillRandomParty(tx, code);
			
			ser.serializeTransaction(msg, tx, writer);
		}
		
		ser.serializeFooter(msg, payments, writer);
		
		writer.close();
	}
	
	public String getFileSuffix() {
		return "txt";
	}
}
