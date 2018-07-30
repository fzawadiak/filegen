package com.tensoli.filegen.pain002;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tensoli.filegen.model.PaymentBlock;
import com.tensoli.filegen.model.PaymentInfo;

public class BuilderPain001 {
	private class HandlerPain001 extends DefaultHandler {
		private final StringWriter sw;
		private String currentElement;
		private PaymentBlock pb;
		private PaymentInfo pi;
		private final SerializerPain002 out = new SerializerPain002();
		
		public HandlerPain001(StringWriter sw) {
			this.sw = sw;
		}
		
		@Override
		public void startDocument() throws SAXException {
			pb = new PaymentBlock();
		}
		@Override
		public void endDocument() throws SAXException {
			out.serializeFooter(pb,sw);
		}
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if(currentElement == null)
				return;
			
			String text = new String(ch,start,length);
			
			if("MsgId".equalsIgnoreCase(currentElement)) {
				pb.setMessageId(text);
			} else if("PmtInfId".equalsIgnoreCase(currentElement)) {
				pb.setPaymentId(text);
				out.serializeHeader(pb,sw);
			} else if("InstrId".equalsIgnoreCase(currentElement)) {
				pi.setId(text);
			} else if("EndToEndId".equalsIgnoreCase(currentElement)) {
				pi.setEndToEndId(text);
			}
		}
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			currentElement = qName;
			if("CdtTrfTxInf".equalsIgnoreCase(currentElement)) {
				pi = new PaymentInfo();
			}
		}
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			currentElement = null;
			if("CdtTrfTxInf".equalsIgnoreCase(qName)) {
				out.serializeTransaction(pi,sw);
			}
		}
		
	}
	public String parse(String msg) throws Exception {
		SAXParserFactory spf = SAXParserFactory.newInstance();
	    SAXParser parser = spf.newSAXParser();
	    StringWriter sw = new StringWriter();
	    parser.parse(new InputSource(new StringReader(msg)), new HandlerPain001(sw));
		return sw.toString();
	}
}
