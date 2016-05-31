package main;

import event.*;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
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
	private XMLInputFactory inputFactory;
	private XMLEventReader eventReader;
	//private File logFile;
	
	// Current Events
	private Event currentEvent;
	private MailEvent mail;
	private LegEvent leg;
	private CostEvent cost;
	private PriceEvent price;
	private DiscontinueEvent discont;
	
	// Current Variables
	private String currentVariable;
	private boolean inLeg;
	private String legVariable;
	
	// CONSRUCTORS
	public LogReader(File logFile, Main mainClass) {
		try {
			// Set Tools
			this.main = mainClass;
			this.inputFactory = XMLInputFactory.newInstance();
			
			//this.logFile = logFile;
	        this.eventReader = inputFactory.createXMLEventReader(new FileReader(logFile));

		} catch (Exception e) {
			System.out.println("Error in LogReader constructor");
			e.printStackTrace();
		}
	}
	
	// METHODS
	// Main Method
	public void parseFile() throws Exception {
        mail = null;
        cost = null;
        price = null;
        discont = null;
        currentEvent = null;
        currentVariable = "";
        inLeg = false;
        legVariable = "";

		while(eventReader.hasNext()){
			
			XMLEvent event = eventReader.nextEvent();
			int xmlEvent = event.getEventType();

            // OPENING TAG - 
            if(xmlEvent == XMLStreamConstants.START_ELEMENT) {
            	String tagName = event.asStartElement().getName().getLocalPart();
            	   
            	if (currentEvent == null) {
            		// <mail>, <price>, <cost>, <discontinue>
            		setCurrentEvent(tagName);
            	} else if (currentVariable == "") {
            		// <legs>, <to>, <from>, etc
            		currentVariable = tagName;
            	} else if (currentEvent.getEventType() == "mail" && currentVariable == "legs") {
            		if (tagName == "leg" && inLeg == false) {
            			// <leg>
            			setCurrentEvent(tagName);
                		inLeg = true;  
	            	} else if (legVariable == "") {
	            		// ...<to>, <from>, etc
	            		legVariable = tagName;
	            	}
            	} else {
            		System.out.println("There has been an error!! (Opening Tag)");
            	}
            
            // TEXT - DONE
            } else if (xmlEvent == XMLStreamConstants.CHARACTERS) {
            	String characters = event.asCharacters().getData();
            	setVariable(characters);
            
            // CLOSING TAG	
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
            	String endTagName = event.asEndElement().getName().getLocalPart();

            	if (legVariable != "") {
            		legVariable = "";
            	} else if (inLeg) {
   					appendLeg();
	   				inLeg = false;
            	} else if (currentVariable != "") {
    	   			currentVariable = "";
            	} else if (currentEvent != null) {
            		sendEvent();
            		currentEvent = null;
            	} else {
            		System.out.println("There has been an error!!! (Closing Tag)");
            	}
            }
		}
	}
	
	// Helpers
	// Event Initialisers
	private void setCurrentEvent(String xmlTagName) {
		if (xmlTagName.equalsIgnoreCase("mail")) {
			mail = new MailEvent();
			currentEvent = mail;
		} else if (xmlTagName.equalsIgnoreCase("leg")) {
			leg = new LegEvent();
     	   	currentEvent = leg;
		} else if (xmlTagName.equalsIgnoreCase("cost")) {
			cost = new CostEvent();
     	   	currentEvent = cost;
		} else if (xmlTagName.equalsIgnoreCase("price")) {
			price = new PriceEvent();
     	   	currentEvent = price;
		} else if (xmlTagName.equalsIgnoreCase("discontinue")) {
     	   	discont = new DiscontinueEvent();
			currentEvent = discont;
		} else {
			System.out.println("Error setting the current event!");
		}
	}
	
	// Variable Setters
	private void setVariable(String data) {
		if (currentEvent != null) {
			switch (currentEvent.getEventType()) {
			case "mail":
				setMailVariable(data);
				break;
			case "leg":
				setLegVariable(data);
				break;
			case "price":
				setPriceVariable(data);
				break;
			case "cost":
				setCostVariable(data);
				break;
			case "discontinue":
				setDiscontinueVariable(data);
				break;
			}
		} else {
			System.out.println("Error in adding variable. Current Event doesn't exist");
		}
	}
	
	private void setMailVariable(String data) {
		switch (currentVariable) {
		case "day":
			mail.setDay(data);
			break;
		case "legs":
			mail.setLegList(new ArrayList<LegEvent>());
			break;
		case "weight":
			mail.setWeight(Double.parseDouble(data));
			break;
		case "volume":
			mail.setVolume(Double.parseDouble(data));
			break;
		case "priority":
			mail.setPriority(data);
			break;
		case "price":
			mail.setPrice(Double.parseDouble(data));
			break;
		case "cost":
			mail.setCost(Double.parseDouble(data));
			break;
		case "duration":
			mail.setDuration(Integer.parseInt(data));
			break;
		default:
			System.out.println("Error. Invalid Variable recieved for MailEvent");
			break;
		}
	}
	private void setLegVariable(String data) {
		if (leg != null) {
			switch(legVariable) {
			case "to":
				leg.setDestination(data);
				break;
			case "from":
				leg.setOrigin(data);
				break;
			case "type":
				leg.setType(data);
				break;
			case "company":
				leg.setCompany(data);
				break;
			case "cost":
				leg.setCost(Double.parseDouble(data));
				break;
			case "price":
				leg.setPrice(Double.parseDouble(data));
				break;
			default:
				System.out.println("Error. Invalid Variable recieved for LegEvent");
				break;
			}
		} else {
			System.out.println("Error. Leg Event doesn't exist. Couldn't append variable to leg.");
		}
	}
	private void setCostVariable(String data) {
		switch (currentVariable) {
		case "from":
			cost.setOrigin(data);
			break;
		case "to":
			cost.setDestination(data);
			break;
		case "company":
			cost.setCompany(data);
			break;
		case "type":
			cost.setType(data);
			break;
		case "priority":
			cost.setPriority(data);
			break;
		case "weightCost":
			cost.setWeightCost(Double.parseDouble(data));
			break;
		case "volumeCost":
			cost.setVolumeCost(Double.parseDouble(data));
			break;
		case "maxWeight":
			cost.setMaxWeight(Integer.parseInt(data));
			break;
		case "maxVolume":
			cost.setMaxVolume(Integer.parseInt(data));
			break;
		case "duration":
			cost.setDuration(Integer.parseInt(data));
			break;
		case "frequency":
			cost.setFrequency(Integer.parseInt(data));
			break;
		case "day":
			cost.setDay(data);
			break;
		default:
			System.out.println("Error. Invalid Variable recieved for CostEvent");
			break;
		}
	}
	private void setPriceVariable(String data) {
		switch (currentVariable) {
		case "from":
			price.setOrigin(data);
			break;
		case "to":
			price.setDestination(data);
			break;
		case "priority":
			price.setPriority(data);
			break;
		case "weightCost":
			price.setWeightCost(Double.parseDouble(data));
			break;
		case "volumeCost":
			price.setVolumeCost(Double.parseDouble(data));
			break;
		default:
			System.out.println("Error. Invalid Variable recieved for PriceEvent: "+currentVariable+" : "+data);
			break;
		}
	}
	private void setDiscontinueVariable(String data) {
		switch (currentVariable) {
		case "from":
			discont.setFrom(data);
			break;
		case "to":
			discont.setTo(data);
			break;
		case "company":
			discont.setCompany(data);
			break;
		case "type":
			discont.setType(data);
			break;
		default:
			System.out.println("Error. Invalid DiscontinueEvent var: "+currentVariable+" : "+data);
			break;
		}
	}	
	
	// Event finishers
	private void sendEvent() {
		switch (currentEvent.getEventType()) {
		case "cost":
			System.out.println();
			//main.logTransportCostUpdate();
			cost = null;
			break;
		case "mail":
			System.out.println();
			//main.logDeliveryRequest();
			mail = null;
			break;
		case "price":
			System.out.println();
			//main.logCustomerPriceUpdate();
			price = null;
			break;
		case "discontinue":
			System.out.println();
			//main.logRouteDiscontinued();
			discont = null;
			break;
		}
	}
	private void appendLeg() {
		mail.addLeg(leg);
		currentEvent = mail;
	}
}
