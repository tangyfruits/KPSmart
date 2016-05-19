package main;

import event.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class LogWriter {
	
	// Console Logging for Debugging
	private static boolean LOGS = false;
	
	// VARIABLES
	private String logFileName;
	private File logFile;
	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;
	private Document doc;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private Transformer transformer;

	// CONSTRUCTORS
	public LogWriter(File logFile) throws Exception {
		this.logFileName = logFile.getName();
		this.logFile = logFile;
		
		docBuilder = docBuilderFactory.newDocumentBuilder();
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        ensureLogExists();
        //doc = docBuilder.parse(logFile);

	}
	public LogWriter(String logFileName) throws Exception {
		this(new File(logFileName));
	}
	
	// METHODS
	@SuppressWarnings("fallthrough")
	public void ensureLogExists() throws Exception {
		
		if (LOGS) {System.out.println("ENSURE:           "+logFileName);}
		
		// Create Log File if it doesn't exist
		if (!logFile.isFile()) {
			try {
				if (LOGS) {System.out.println("   File: created.");}
				logFile.createNewFile();
			} catch (IOException e) {
				//e.printStackTrace();
				if (LOGS) {System.out.print("Error creating log file in LogWriter");}
			}
		} else {
			if (LOGS) {System.out.println("   File: exists.");}
		}
		
		// Make Log File DOM Compliant
		try {
			if (LOGS) {System.out.println("   Parse: Attempt");}
			doc = docBuilder.parse(logFile);
			if (LOGS) {System.out.println("   Parse: succ :)");}

		} catch (SAXException e) {
			//System.out.println(e.getMessage());
			System.out.println("^^Ignore this error. It's cool. It's been handled. We just couldn't \nfigure out how to stop it logging in the console. We good. :)");
			doc = docBuilder.newDocument();
			Element events = doc.createElement("events");
			doc.appendChild(events);
			
			DOMSource    source = new DOMSource(doc);
	        StreamResult output = new StreamResult(logFile);
	      	transformer.transform(source, output);

	      	if (LOGS) {System.out.println("   Parse: failed. new doc made");}
		}
		
		// Console Logging for Testing
		if (LOGS) {
			DOMSource    source = new DOMSource(doc);
			StreamResult consoleResult =  new StreamResult(System.out);
	      	transformer.transform(source, consoleResult);
	      	System.out.println("---------------");
		}
	}
	
	// Event Writers
	/**
	 * Writes Mail Delivery Event to the log file.
	 * 
	 * ------- FORMAT -------
	   <mail>
	 		<day>%s</day>
	 		<legs>
	 			<leg>
	 				<to>%s</to>
	 				<from>%s</from>
 	 			</leg>
 	 			<leg>
	 				<to>%s</to>
	 			<from>%s</from>
 	 			</leg>
	 		</legs>
			<weight>%s</weight>
	 		<volume>%s</volume>
	 		<priority>%s</priority>
	 		<price>%s</price>
	 		<cost>%s</cost>
	 		<duration>%s</duration>
	 	</mail>
	 * ----------------------
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void writeDelivery(MailEvent event) throws Exception {
		doc = docBuilder.parse(logFile);
		Element events = doc.getDocumentElement();
		
        Element mail = doc.createElement("mail");
        Element day = doc.createElement("day");
        Element legs = doc.createElement("legs");
        Element weight = doc.createElement("weight");
        Element volume = doc.createElement("volume");
        Element priority = doc.createElement("priority");
        Element price = doc.createElement("price");
        Element cost = doc.createElement("cost");
        Element duration = doc.createElement("duration");
        
        // Make leg Elements
    	for (Leg legObject : event.getLegList()) {
    		Element leg = doc.createElement("leg");
            Element to = doc.createElement("to");
            Element from = doc.createElement("from");
            to.appendChild(doc.createTextNode(legObject.getTo()));
            from.appendChild(doc.createTextNode(legObject.getFrom()));
            leg.appendChild(to);
            leg.appendChild(from);
            legs.appendChild(leg);
    	}
        
        // Add text values to tags
        day.appendChild(doc.createTextNode(event.getDay()));
        weight.appendChild(doc.createTextNode(Double.toString(event.getWeight())));
        volume.appendChild(doc.createTextNode(Double.toString(event.getVolume())));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        price.appendChild(doc.createTextNode(Double.toString(event.getPrice())));
        cost.appendChild(doc.createTextNode(Double.toString(event.getCost())));
        duration.appendChild(doc.createTextNode(Double.toString(event.getDuration())));
        
        // add tags together
        events.appendChild(mail);
        mail.appendChild(day);
        mail.appendChild(legs);
        mail.appendChild(weight);
        mail.appendChild(volume);
        mail.appendChild(priority);
        mail.appendChild(price);
        mail.appendChild(cost);
        mail.appendChild(duration);
        
        
        //doc.normalizeDocument();
        
        // Write to XML file
        DOMSource    source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
   	    transformer.transform(source, output);
		
   	    // Console Logging for Testing
   	    if (LOGS) {
	        StreamResult consoleResult =  new StreamResult(System.out);
	      	transformer.transform(source, consoleResult);
   	    }
	}
	/**
	 * Writes Cost Event to log file.
	 * 
	 * ------- FORMAT -------
	   <cost>
		  <company>%s</company>
		  <to>%s</to>
		  <from>%s</from>
		  <type>%s</type>
		  <priority>%s</priority>
		  <weightCost>%f</weightCost>
		  <volumeCost>%f</volumeCost>
		  <maxWeight>%d</maxWeight>
		  <maxVolume>%d</maxVolume>
		  <duration>%d</duration>
		  <frequency>%d</frequency>
		  <day>Thursday</day>
		</cost>
	 * ----------------------
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void writeRoute(CostEvent event) throws Exception {
		
		doc = docBuilder.parse(logFile);
		Element events = doc.getDocumentElement();
		
		// Make Elements
        Element cost = doc.createElement("cost");
        Element company = doc.createElement("company");
        Element to = doc.createElement("to");
        Element from = doc.createElement("from");
        Element type = doc.createElement("type");
        Element priority = doc.createElement("priority");
        Element weightCost = doc.createElement("weightCost");
        Element volumeCost = doc.createElement("volumeCost");
        Element maxWeight = doc.createElement("maxWeight");
        Element maxVolume = doc.createElement("maxVolume");
        Element duration = doc.createElement("duration");
        Element frequency = doc.createElement("frequency");
        Element day = doc.createElement("day");
        
        // Add text values to tags
        company.appendChild(doc.createTextNode(event.getCompany()));
        to.appendChild(doc.createTextNode(event.getDestination()));
        from.appendChild(doc.createTextNode(event.getOrigin()));
        type.appendChild(doc.createTextNode(event.getType()));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        weightCost.appendChild(doc.createTextNode(Double.toString(event.getWeightCost())));
        volumeCost.appendChild(doc.createTextNode(Double.toString(event.getVolumeCost())));
        maxWeight.appendChild(doc.createTextNode(Integer.toString(event.getMaxWeight())));
        maxVolume.appendChild(doc.createTextNode(Integer.toString(event.getMaxVolume())));
        duration.appendChild(doc.createTextNode(Integer.toString(event.getDuration())));
        frequency.appendChild(doc.createTextNode(Integer.toString(event.getFrequency())));
        day.appendChild(doc.createTextNode(event.getDay()));
        
        // add tags together
        cost.appendChild(company);
        cost.appendChild(to);
        cost.appendChild(from);
        cost.appendChild(type);
        cost.appendChild(priority);
        cost.appendChild(weightCost);
        cost.appendChild(volumeCost);
        cost.appendChild(maxWeight);
        cost.appendChild(maxVolume);
        cost.appendChild(duration);
        cost.appendChild(frequency);
        cost.appendChild(day);
        events.appendChild(cost);
        
        //doc.normalizeDocument();
        
        // Write to XML file
        DOMSource    source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
      	transformer.transform(source, output);
        
      	// Console Logging for Testing
      	if (LOGS) {
	        StreamResult consoleResult =  new StreamResult(System.out);
	      	transformer.transform(source, consoleResult);
      	}
	}
	public void writeRoute(Route route) throws Exception {
		
		CostEvent event = new CostEvent(route.getOrigin().getName(), 
										route.getDestination().getName(), 
										route.getCompany(), 
										route.getType(), 
										route.getPriority(), 
										route.getWeightCost(), 
										route.getVolumeCost(), 
										route.getMaxWeight(), 
										route.getMaxVolume(), 
										route.getDuration(), 
										route.getFrequency(), 
										route.getDay());
		
		writeRoute(event);
	}
	/**
	 * Writes Price Event to the log file.
	 * 
	 * ------- FORMAT -------
	   <price>
		  <to>%s</to>
		  <from>%s</from>
		  <type>%s</type>
		  <priority>%s</priority>
		  <weightCost>%f</weightCost>
		  <volumeCost>%f</volumeCost>
		</price>
	 * ----------------------
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void writeCustomerPrice(PriceEvent event) throws Exception {
		
		doc = docBuilder.parse(logFile);
		Node events = doc.getDocumentElement();
		
		// Make Elements
        Element price = doc.createElement("price");
        Element to = doc.createElement("to");
        Element from = doc.createElement("from");
        Element priority = doc.createElement("priority");
        Element weightCost = doc.createElement("weightCost");
        Element volumeCost = doc.createElement("volumeCost");
        
        // Add text values to tags
        to.appendChild(doc.createTextNode(event.getDestination()));
        from.appendChild(doc.createTextNode(event.getOrigin()));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        weightCost.appendChild(doc.createTextNode(Double.toString(event.getWeightCost())));
        volumeCost.appendChild(doc.createTextNode(Double.toString(event.getVolumeCost())));
        
        // add tags together
        price.appendChild(to);
        price.appendChild(from);
        price.appendChild(priority);
        price.appendChild(weightCost);
        price.appendChild(volumeCost);
        events.appendChild(price);
        
        //doc.normalizeDocument();
        
        // Write to XML file
        DOMSource    source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
        transformer.transform(source, output);
        
        // Console Logging for Testing
        if(LOGS) {
	        StreamResult consoleResult =  new StreamResult(System.out);
	      	transformer.transform(source, consoleResult);
        }
	}
	public void writeCustomerPrice(CustomerPrice price) throws Exception {
		PriceEvent event = new PriceEvent(price.getOrigin().getName(), 
										  price.getDestination().getName(), 
										  price.getPriority(), 
										  price.getWeightCost(), 
										  price.getVolumeCost());
		writeCustomerPrice(event);
	}
	/**
	 * Writes Discontinue Event to log file.
	 * 
	 * ------- FORMAT -------
	   <discontinue>
  	 	  <company>NZ Post</company>
  		  <to>Wellington</to>
  		  <from>Christchurch</from>
  		  <type>Sea</type>
	   </discontinue>
	 * ----------------------
	 *
	 * @param event
	 * @throws IOException 
	 */
	public void writeDiscontinue(DiscontinueEvent event) throws Exception {
		
		doc = docBuilder.parse(logFile);
		Node events = doc.getDocumentElement();
	  
	  	// Make Elements
	  	Element discontinue = doc.createElement("discontinue");
	  	Element company = doc.createElement("company");
	  	Element to = doc.createElement("to");
	  	Element from = doc.createElement("from");
	  	Element type = doc.createElement("type");
	  	
	  	// Add text values to tags
	  	company.appendChild(doc.createTextNode(event.getCompany()));
	  	to.appendChild(doc.createTextNode(event.getTo()));
	  	from.appendChild(doc.createTextNode(event.getFrom()));
	  	type.appendChild(doc.createTextNode(event.getType()));
	  	
	  	// add tags together
	  	discontinue.appendChild(company);
	  	discontinue.appendChild(to);
	  	discontinue.appendChild(from);
	  	discontinue.appendChild(type);
	  	events.appendChild(discontinue);
	  	
	  	//doc.normalizeDocument();
	  	
	  	// Write to XML file
	  	DOMSource    source = new DOMSource(doc);
	  	StreamResult output = new StreamResult(logFile);
		transformer.transform(source, output);
	
		// Console Logging for Testing
		if(LOGS) {
		  	StreamResult consoleResult =  new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		}
	}
	
	
	public void clearFile() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(logFileName);
		pw.close();
	}
}
