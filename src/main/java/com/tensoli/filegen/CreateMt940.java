package com.tensoli.filegen;

import java.io.FileWriter;
import java.io.IOException;

import com.tensoli.filegen.model.Party;
import com.tensoli.filegen.model.Statement;
import com.tensoli.filegen.model.Transaction;
import com.tensoli.filegen.mt940.SerializerMt940;

public class CreateMt940 extends AbstractCreator {
	public CreateMt940() {
		currency = "CHF";
	}
	
	public void generateFile(String name, int payments) throws IOException {
		SerializerMt940 ser = new SerializerMt940();
		
		Party p = new Party();
		String code = parties.fillRandomParty(p);
		String account = p.getAccount();
		
		FileWriter writer = new FileWriter(isSplit()?code + "/" + name : name);
		
		Statement stmt = new Statement();
		stmt.setAccount(p.getAccount());
		stmt.setCurrency(currency);
		stmt.setOpeningBalance(0.0);
		stmt.setClosingBalance(amount*payments);
		stmt.setOpeningDate(getDate());
		stmt.setClosingDate(getDate());
		
		ser.serializeHeader(stmt, payments, writer);
		
		for(int i=0; i<payments; i++) {
			Transaction tx = new Transaction();
			tx.setCurrency(currency);
			tx.setTitle(title);
			tx.setAmount(amount);
			parties.fillRandomParty(tx, account);
			
			ser.serializeTransaction(stmt, tx, writer);
		}
		
		ser.serializeFooter(stmt, payments, writer);
		
		writer.close();
	}
	
	public String getFileSuffix() {
		return "txt";
	}
}
