package main;

import event.*;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class LogReader {
	
	// VARIABLES
	private File logFile;
	private SAXParserFactory saxParserFactory;
	private SAXParser saxParser;
	
	// CONSRUCTORS
	public LogReader(File logFile) {
		try {
			this.logFile = logFile;
			this.saxParserFactory = SAXParserFactory.newInstance();
			this.saxParser = saxParserFactory.newSAXParser();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// METHODS
	private MailEvent readMail() {
		//MailEvent mail = new MailEvent();
	}
	
}

class UserHandler extends DefaultHandler {
	
}
