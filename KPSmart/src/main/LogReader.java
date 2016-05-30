package main;

import event.*;

import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class LogReader {
	
	// VARIABLES
	// Tools
	private Main main;
	private File logFile;
	private SAXParserFactory saxParserFactory;
	private SAXParser saxParser;
	private XMLInputFactory inputFactory;
	private XMLEventReader eventReader;
	
	// Current Variables
	private Event currentEvent;
	private String currentVariable;
	private boolean inLeg;
	private String legVariable;
	
	// CONSRUCTORS
	public LogReader(File logFile, Main mainClass) {
		try {
			this.main = mainClass;
			this.logFile = logFile;
			this.saxParserFactory = SAXParserFactory.newInstance();
			this.saxParser = saxParserFactory.newSAXParser();
			this.inputFactory = XMLInputFactory.newInstance();
	        this.eventReader = inputFactory.createXMLEventReader(new FileReader("input.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// METHODS
	// Main Method
	public void parseFile() {
		currentEvent = null;
		currentVariable = "";
		inLeg = false;
		legVariable = "";

		while(eventReader.hasNext()){
			
			XMLEvent event = eventReader.nextEvent();
			int xmlEvent = event.getEventType();
            

            // OPENING TAG
            if(xmlEvent == XMLStreamConstants.START_ELEMENT) {
            	String tagName = event.asStartElement().getName().getLocalPart();
            	   
            	if (currentEvent == null) {
            		setCurrentEvent(tagName);

            	} else if (currentVariable == "") {
            		currentVariable = tagName;

            	} else if (currentEvent.getEventType() == "mail" && currentVariable == "legs") {
            		if (tagName == "leg" && inLeg == false) {
                		inLeg = true;  

	            	} else if (legVariable == "") {
	            		legVariable = tagName;
	            	}

            	} else {
            		System.out.println("There has been an error!! (Opening Tag)");
            	}
            
            // TEXT
            } else if (xmlEvent == XMLStreamConstants.CHARACTERS) {
            	String characters = event.asCharacters().getData();
            	setVariable(characters);
            
            // CLOSING TAG	
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
            	String endTagName = event.asEndElement().getName().getLocalPart();

            	if (legVariable != "") {
            		
            		legVariable = "";
   				} else if (inLeg) {

	   				inLeg = false;
	   			} else if (currentVariable != "") {

    	   			currentVariable = "";
	   			} else if (currentEvent != null) {
            		
            		currentVariable = "";
            	} else {
            		System.out.println("There has been an error!!! (Closing Tag)");
            	}
            }
		}
	}
	
	// Helpers
	private void setCurrentEvent(String tagName) {
		if (tagName.equalsIgnoreCase("mail")) {
			currentEvent = new MailEvent();
		} else if (tagName.equalsIgnoreCase("cost")) {
     	   	currentEvent = new CostEvent();
		} else if (tagName.equalsIgnoreCase("price")) {
     	   	currentEvent = new PriceEvent();
		} else if (tagName.equalsIgnoreCase("discontinue")) {
     	   	currentEvent = new DiscontinueEvent();
		} else {
			System.out.println("Error setting the current event!");
		}
	}
	
	private void setVariable(String data) {
		if (currentEvent != null) {
			switch (currentEvent.getEventType()) {
			case "mail":
				setMailVariable(data);
				break;
			case "price":
				this.setPriceVariable(data);
				break;
			case "cost":
				this.setCostVariable(data);
				break;
			case "discontinue":
				this.setDiscontinueVariable(data);
				break;
			}
		} else {
			System.out.println("Error in adding variable yo. Current Event doesn't exist");
		}
	}
	
	private void setMailVariable(String data) {
		switch (currentVariable) {
			case "day":
				break;
			case "legs":
				break;
			case "weight":
				break;
			case "volume":
				break;
			case "priority":
				break;
			case "price":
				break;
			case "cost":
				break;
			case "duration":
				break;
			case "":
				break;
		}
	}
	private void setLegVariable(String data) {
		
	}
	private void setCostVariable(String data) {
		
	}
	private void setPriceVariable(String data) {
		
	}
	private void setDiscontinueVariable(String data) {
		
	}
	
	
	private void sendEvent() {
		switch (currentEvent.getEventType()) {
		case "cost":
			main.logTransportCostUpdate();
			break;
		case "mail":
			main.logDeliveryRequest();
			break;
		case "price":
			main.logCustomerPriceUpdate();
			break;
		case "discontinue":
			main.logRouteDiscontinued();
			break;
		}
	}
}
