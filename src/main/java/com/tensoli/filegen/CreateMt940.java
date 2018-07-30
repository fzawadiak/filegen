package com.tensoli.filegen;

import java.io.FileWriter;
import java.io.IOException;

import com.tensoli.filegen.model.Party;
import com.tensoli.filegen.model.Statement;
import com.tensoli.filegen.model.Transaction;
import com.tensoli.filegen.mt940.SerializerMt940;

public class CreateMt940 extends AbstractCreator {
	public void generateFile(String name, int payments) throws IOException {
		FileWriter writer = new FileWriter(name);
		
		SerializerMt940 ser = new SerializerMt940();
		
		Party p = new Party();
		parties.fillRandomParty(p);
		
		Statement stmt = new Statement();
		stmt.setAccount(p.getAccount());
		stmt.setCurrency("CHF");
		stmt.setOpeningBalance(0.0);
		stmt.setOpeningDate(getDate());
		stmt.setClosingDate(getDate());
		
		ser.serializeHeader(stmt, payments, writer);
		
		for(int i=0; i<payments; i++) {
			Transaction tx = new Transaction();
			tx.setCurrency("CHF");
			tx.setTitle("Payment");
			tx.setAmount(1500.00);
			parties.fillRandomParty(tx);
			
			ser.serializeTransaction(stmt, tx, writer);
		}
		
		ser.serializeFooter(stmt, payments, writer);
		
		writer.close();
	}
	
	public String getFileSuffix() {
		return "txt";
	}
}
