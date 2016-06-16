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
		public final String eventType = "mail";
		public String getEventType() {
			return eventType;
		}
		public String toString() {
			String str = "MAIL EVENT\n";
			str += "log : " + logTime.toString() + "\n";
			str += "orig: " + origin + "\n";
			str += "dest: " + destination + "\n";
			for (Leg leg : legs) {
				str += leg.toString();
			}
			str += "weig: " + Double.toHexString(weight) + "\n";
			str += "volu: " + Double.toString(volume) + "\n";
			str += "prio: " + priority + "\n";
			str += "dura: " + Integer.toString(duration) + "\n";
			return str;
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
		public String toString() {
			String str = "LEG EVENT\n";
			str += "orig: " + origin + "\n";
			str += "dest: " + destination + "\n";
			str += "type: " + type + "\n";
			str += "comp: " + company + "\n";
			str += "cost: $" + cost + "\n";
			str += "pric: $" + price + "\n";
			return str;
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
		public String toString() {
			String str = "COST EVENT\n";
			str += "orig: " + origin + "\n";
			str += "dest: " + destination + "\n";
			str += "comp: " + company + "\n";
			str += "type: " + type + "\n";
			str += "prio: " + priority + "\n";
			str += "Wcst: $" + weightCost + "\n";
			str += "Vcst: $" + volumeCost + "\n";
			str += "maxW: " + maxWeight + " kg\n";
			str += "maxV: " + maxVolume + "kg\n";
			str += "dura: " + duration + "\n";
			str += "freq: " + frequency + "\n";
			str += "day : " + day + "\n";
			str += "time: " + startTime + "\n";
			return str;
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
		public String toString() {
			String str = "PRICE EVENT\n";
			str += "orig: " + origin + "\n";
			str += "dest: " + destination + "\n";
			str += "prio: " + priority + "\n";
			str += "Wcst: $" + weightCost + "\n";
			str += "Vcst: $" + volumeCost + "\n";
			return str;
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
		public String toString() {
			String str = "DISCONT. EVENT\n";
			str += "orig: " + origin + "\n";
			str += "dest: " + destination + "\n";
			str += "comp: " + company + "\n";
			str += "type: " + type + "\n";
			return str;
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
            	System.out.println("-------OPEN: \""+tagName+"\"");
            	
            	if (currentEvent == null) {         		// Lvl 1 tags: <mail>, <price>, <cost>, <discontinue>
            		setCurrentEvent(tagName);
            		
            	} else if (currentVariable == "") {   		// Lvl 2 tags: <legs>, <to>, <from>, etc
            		setCurrentVariable(tagName);
            		
            	} else if (!inLeg) {						// Lvl 3 tags: <leg>
        			setCurrentEvent(tagName);
            		 
            	} else if (legVariable == "") { 			// Lvl 4 tags: ...<to>, <from>, etc
	            		legVariable = tagName;
	            
            	} else {
            		System.out.println("There has been an error!! (Opening Tag)");
            	}
            
            // TEXT
            } else if (xmlEvent == XMLStreamConstants.CHARACTERS) {
            	String characters = event.asCharacters().getData().trim();
            	
            	if (!characters.equals("")) {
            		System.out.println("--------TXT: \""+characters.trim());
            		setVariable(characters);
            	}
            
            // CLOSING TAG	
            } else if (xmlEvent == XMLStreamConstants.END_ELEMENT) {
            	String endTagName = event.asEndElement().getName().getLocalPart();
            	System.out.println("------CLOSE: \""+endTagName+"\"");
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
            	} else if (endTagName.equals("events")) {
            		System.out.println("'events' Tag Closed");
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
     	   	inLeg = true; 
		} else if (xmlTagName.equalsIgnoreCase("cost")) {
			cost = new CostEvent();
     	   	currentEvent = cost;
		} else if (xmlTagName.equalsIgnoreCase("price")) {
			price = new PriceEvent();
     	   	currentEvent = price;
		} else if (xmlTagName.equalsIgnoreCase("discontinue")) {
     	   	discont = new DiscontinueEvent();
			currentEvent = discont;
		} else if (xmlTagName.equalsIgnoreCase("events")) {
			System.out.println("'events' Tag Opened");
		} else {
			System.out.println("Error setting current event: Invalid tagname: \""+xmlTagName+"\"");
		}
	}
	
	// Set Event Variable (step 2)
	private void setCurrentVariable(String varName) {
		if (varName == "legs" && currentEvent.getEventType().equals("mail")) {
			mail.legs = new ArrayList<Leg>();
		}
			currentVariable = varName.trim();
	}
	
	// Write Event Details (step 3)
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
			System.out.println("Error in adding variable. Current Event doesn't exist. (data=\""+data+"\"");
		}
	}
	private void setMailVariable(String data) {
		switch (currentVariable) {
		case "logged":
			mail.logTime = LocalDateTime.parse(data);
			break;
		case "to":
			mail.destination = data;
			break;
		case "from":
			mail.origin = data;
			break;
		/*case "legs":
			mail.legs = new ArrayList<Leg>();
			break;*/
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
			System.out.println("Error. Invalid MailEvent Var: \""+currentVariable+"\" : \""+data+"\"");
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
				System.out.println("Error. Invalid LegEvent Var: \""+legVariable+"\" : \""+data+"\"");
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
			break;
		default:
			System.out.println("Error. Invalid CostEvent Var: \""+currentVariable+"\" : \""+data+"\"");
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
			System.out.println("Error. Invalid PriceEvent VAR: \""+currentVariable+"\" : \""+data+"\"");
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
			System.out.println("Error. Invalid DiscontinueEvent var: \""+currentVariable+"\" : \""+data+"\"");
			break;
		}
	}	
	
	// Put Events into System (step 4)
	private void appendLeg() {
		// Get the leg objects from the main class (or make new ones)
		/*
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
		/**/
		// TODO Remove this once hooked up to main properly. Alt way for testing only!
		Location from = new Location(leg.origin);
		Location to = new Location(leg.destination);
		
		Leg legObject = new Leg(from, to, leg.type, leg.company, leg.cost, leg.price);
		mail.legs.add(legObject);
		leg = null;
		currentEvent = mail;
	}
	private void sendEvent() {
		switch (currentEvent.getEventType()) {
		case "cost":
			System.out.println(cost);
			
			/*
			main.logTransportCostUpdate(cost.origin, cost.destination, 
					cost.company, cost.type, cost.weightCost, cost.volumeCost, 
					cost.maxWeight, cost.maxVolume, cost.duration, cost.frequency, 
					cost.day, cost.startTime, true);
			/**/
			cost = null;
			break;
		case "mail":
			System.out.println(mail);
			
			/*
			main.logDeliveryRequest(mail.logTime, mail.origin, mail.destination, mail.legs,
					mail.weight, mail.volume, mail.priority, mail.duration, true);
			/**/
			mail = null;
			break;
		case "price":
			System.out.println(price);
			
			/*
			main.logCustomerPriceUpdate(price.origin, price.destination, 
					price.priority, price.weightCost, price.volumeCost, true);
			/**/
			price = null;
			break;
		case "discontinue":
			System.out.println(discont);
			
			/*
			main.logRouteDiscontinued(discont.origin, discont.destination, 
					discont.company, discont.type, true);
			/**/
			discont = null;
			break;
		default:
			System.out.println("Shit's fuct yo.");
		}
	}	
}
