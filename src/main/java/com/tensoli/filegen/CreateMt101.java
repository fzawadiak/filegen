package com.tensoli.filegen;

import java.io.FileWriter;
import java.io.IOException;

import com.tensoli.filegen.model.Message;
import com.tensoli.filegen.model.Transaction;
import com.tensoli.filegen.mt101.SerializerMt101;

public class CreateMt101 extends AbstractCreator {
	public CreateMt101() {
		currency = "CHF";
	}
	
	public void generateFile(String name, int payments) throws IOException {
		SerializerMt101 ser = new SerializerMt101();
		
		Message msg = new Message();
		String code = parties.fillRandomParty(msg);
		String account = msg.getAccount();
		msg.setExecutionDate(getDate());
		
		FileWriter writer = new FileWriter(isSplit()?code + "/" + name : name);
		
		ser.serializeHeader(msg, payments, writer);
		
		for(int i=0; i<payments; i++) {
			Transaction tx = new Transaction();
			tx.setCurrency(currency);
			tx.setTitle(title);
			tx.setAmount(amount);
			parties.fillRandomParty(tx, account);
			
			ser.serializeTransaction(msg, tx, writer);
		}
		
		ser.serializeFooter(msg, payments, writer);
		
		writer.close();
	}
	
	public String getFileSuffix() {
		return "txt";
	}
}
