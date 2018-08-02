package com.tensoli.filegen.pain002;

import java.io.Reader;
import java.io.Writer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tensoli.filegen.model.PaymentBlock;
import com.tensoli.filegen.model.PaymentInfo;

public class GroupLevelBuilder extends AbstractBuilder {
	private class HandlerPain001 extends DefaultHandler {
		private final Writer sw;
		private String currentElement;
		private PaymentBlock pb;
		private int payments = 0;
		private final SerializerPain002 out = new SerializerPain002();
		
		public HandlerPain001(Writer sw) {
			this.sw = sw;
		}
		
		@Override
		public void startDocument() throws SAXException {
			pb = new PaymentBlock();
		}
		@Override
		public void endDocument() throws SAXException {
			out.serialize(pb, sw);
		}
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if(currentElement == null)
				return;
			
			String text = new String(ch,start,length);
			
			if("MsgId".equalsIgnoreCase(currentElement)) {
				pb.setMessageId(text);
			}
		}
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			currentElement = qName;
			if("CdtTrfTxInf".equalsIgnoreCase(currentElement)) {
				payments++;
			}
		}
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			currentElement = null;
		}
		
		public int getPayments() {
			return payments;
		}
	}
	public int parse(Reader in, Writer out) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    SAXParser parser = spf.newSAXParser();
	    HandlerPain001 handler = new HandlerPain001(out);
	    parser.parse(new InputSource(in), handler);
	    
	    return handler.getPayments();
	}
}
