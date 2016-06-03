package main;

import event.*;

import java.io.File;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.XMLEvent;

public class LogReader {
	
	// LOCAL CLASSES
	class MailEvent implements Event {
		public LocalDateTime logTime;
		public String origin;
		public String destination;
		public ArrayList<Leg> legs;
		public double weight;
		public double volume;
		public String priority;
		public int duration;
		public final String eventType = "cost";
		public String getEventType() {
			return eventType;
		}
	}
	class LegEvent implements Event {
		public String origin;
		public String destination;
		public String type;
		public String company;
		public double cost;
		public double price;
		public final String eventType = "leg";
		public String getEventType() {
			return eventType;
		}
	}
	class CostEvent implements Event {
		public String origin;
		public String destination;
		public String company;
		public String type;
		public String priority;
		public double weightCost;
		public double volumeCost;
		public int maxWeight;
		public int maxVolume;
		public int duration;
		public int frequency;
		public DayOfWeek day;
		public int startTime;
		public final String eventType = "cost";
		public String getEventType() {
			return eventType;
		}
	}
	class PriceEvent implements Event {
		public String origin;
		public String destination;
		public String priority;
		public double weightCost;
		public double volumeCost;
		public final String eventType = "price";
		public String getEventType() {
			return eventType;
		}
	}
	class DiscontinueEvent implements Event {
		public String origin;
		public String destination;
		public String company;
		public String type;
		public final String eventType = "discontinue";
		public String getEventType() {
			return eventType;
		}
	}
		
	// FIELDS
	// Tools
	private Main main;
	private XMLInputFactory inputFactory;
	private XMLEventReader eventReader;
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
	
	// CONSTRUCTOR
	public LogReader(File logFile, Main mainClass) {
		try {
			// Set Tools
			this.main = mainClass;
			this.inputFactory = XMLInputFactory.newInstance();
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

            // OPENING TAG
            if(xmlEvent == XMLStreamConstants.START_ELEMENT) {
            	String tagName = event.asStartElement().getName().getLocalPart();
            	   
            	if (currentEvent == null) {
            		// Lvl 1 tags: <mail>, <price>, <cost>, <discontinue>
            		setCurrentEvent(tagName);
            	} else if (currentVariable == "") {
            		// Lvl 2 tags: <legs>, <to>, <from>, etc
            		currentVariable = tagName;
            	} else if (currentEvent.getEventType() == "mail" && currentVariable == "legs") {
            		if (tagName == "leg" && inLeg == false) {
            			// Lvl 3 tags: <leg>
            			setCurrentEvent(tagName);
                		inLeg = true;  
	            	} else if (legVariable == "") {
	            		// Lvl 4 tags: ...<to>, <from>, etc
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
            	//String endTagName = event.asEndElement().getName().getLocalPart();

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
	
	// Event Starter (step 1)
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
	
	// Write Event Details (step 2)
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
			mail.logTime = LocalDateTime.parse(data); // TODO How storing time thus how read it?
			break;
		case "to":
			mail.destination = data;
			break;
		case "from":
			mail.origin = data;
			break;
		case "legs":
			mail.legs = new ArrayList<Leg>();
			break;
		case "weight":
			mail.weight = Double.parseDouble(data);
			break;
		case "volume":
			mail.volume = Double.parseDouble(data);
			break;
		case "priority":
			mail.priority = data;
			break;
		case "duration":
			mail.duration = Integer.parseInt(data);
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
				leg.destination = data;
				break;
			case "from":
				leg.origin = data;
				break;
			case "type":
				leg.type = data;
				break;
			case "company":
				leg.company = data;
				break;
			case "cost":
				leg.cost = Double.parseDouble(data);
				break;
			case "price":
				leg.price = Double.parseDouble(data);
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
			cost.origin = data;
			break;
		case "to":
			cost.destination = data;
			break;
		case "company":
			cost.company = data;
			break;
		case "type":
			cost.type = data;
			break;
		case "priority":
			cost.priority = data;
			break;
		case "weightCost":
			cost.weightCost = Double.parseDouble(data);
			break;
		case "volumeCost":
			cost.volumeCost = Double.parseDouble(data);
			break;
		case "maxWeight":
			cost.maxWeight = Integer.parseInt(data);
			break;
		case "maxVolume":
			cost.maxVolume = Integer.parseInt(data);
			break;
		case "duration":
			cost.duration = Integer.parseInt(data);
			break;
		case "frequency":
			cost.frequency = Integer.parseInt(data);
			break;
		case "day":
			cost.day = DayOfWeek.valueOf(data.toUpperCase());
			break;
		case "hour":
			cost.startTime = Integer.parseInt(data);
		default:
			System.out.println("Error. Invalid Variable recieved for CostEvent");
			break;
		}
	}
	private void setPriceVariable(String data) {
		switch (currentVariable) {
		case "from":
			price.origin = data;
			break;
		case "to":
			price.destination = data;
			break;
		case "priority":
			price.priority = data;
			break;
		case "weightCost":
			price.weightCost = Double.parseDouble(data);
			break;
		case "volumeCost":
			price.volumeCost = Double.parseDouble(data);
			break;
		default:
			System.out.println("Error. Invalid Variable recieved for PriceEvent: "+currentVariable+" : "+data);
			break;
		}
	}
	private void setDiscontinueVariable(String data) {
		switch (currentVariable) {
		case "from":
			discont.origin = data;
			break;
		case "to":
			discont.destination = data;
			break;
		case "company":
			discont.company = data;
			break;
		case "type":
			discont.type = data;
			break;
		default:
			System.out.println("Error. Invalid DiscontinueEvent var: "+currentVariable+" : "+data);
			break;
		}
	}	
	
	// Put Events into System (step 3)
	private void sendEvent() {
		switch (currentEvent.getEventType()) {
		case "cost":
			System.out.println();
			main.logTransportCostUpdate(cost.origin, cost.destination, 
					cost.company, cost.type, cost.weightCost, cost.volumeCost, 
					cost.maxWeight, cost.maxVolume, cost.duration, cost.frequency, 
					cost.day, cost.startTime);
			cost = null;
			break;
		case "mail":
			System.out.println();
			main.logDeliveryRequest(mail.logTime, mail.origin, mail.destination, mail.legs,
					mail.weight, mail.volume, mail.priority, mail.duration);
			mail = null;
			break;
		case "price":
			System.out.println();
			main.logCustomerPriceUpdate(price.origin, price.destination, 
					price.priority, price.weightCost, price.volumeCost);
			price = null;
			break;
		case "discontinue":
			System.out.println();
			/*main.logRouteDiscontinued();*/
			discont = null;
			break;
		default:
			System.out.println("Shit's fuct yo.");
		}
	}
	private void appendLeg() {
		Location from = main.getLocation(leg.origin);
		if (from == null) {
			from = new Location(leg.origin);
			main.addLocation(from);
		}
		Location to  = main.getLocation(leg.destination);
		if (to == null) {
			to = new Location(leg.destination);
			main.addLocation(to);
		}
		
		Leg legObject = new Leg(from, to, leg.type, leg.company, leg.cost, leg.price);
		mail.legs.add(legObject);
		leg = null;
		currentEvent = mail;
	}
}
