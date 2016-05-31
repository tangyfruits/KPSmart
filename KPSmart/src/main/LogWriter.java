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
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.TextStyle;
import java.util.Locale;

public class LogWriter {
	
	// Console Logging for Debugging
	private static boolean LOGS = true;
	
	// FIELDS
	private File logFile;
	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;
	private Document doc;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private Transformer transformer;

	// CONSTRUCTORS
	public LogWriter(File file) throws Exception {
		this.logFile = file;
		
		docBuilder = docBuilderFactory.newDocumentBuilder();
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        ensureLogExists();
        
        // TODO Stop error writing to stderr. Errors are being caught. 
        docBuilder.setErrorHandler(new ErrorHandler() {
        	/**/
        	@Override
            public void warning(SAXParseException e) throws SAXException {
        		System.out.println("some errors yo");
        		//throw e;
            }/**/
        	
        	/**/
            @Override
            public void fatalError(SAXParseException e) throws SAXException {
            	System.out.println("some errors yo");
            	//throw e;
            }/**/
            
            /**/
            @Override
            public void error(SAXParseException e) throws SAXException {
            	System.out.println("some errors yo");
            	//throw e;
            }/**/
        });
	}
	
	// METHODS
	public void ensureLogExists() throws Exception {
		
		if (LOGS) {System.out.println("ENSURE:           "+logFile.getName());}
		
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

		} catch (SAXParseException e) {
			if (LOGS) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			/*System.out.println("^^Ignore this error. It's cool. It's been handled. We just couldn't \n" +
								"figure out how to stop it logging in the console. We good. :)");*/
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
	 		<from> TODO
	 		<to> TODO
	 		<legs>
	 			<leg>
	 				<to>%s</to>
	 				<from>%s</from>
	 				<type>%s</type>
	 				<company>%s</company>
	 				<cost>%s</cost>
	 				<price>%s</price>
 	 			</leg>
 	 			<leg>
	 				<to>%s</to>
	 			<from>%s</from>
 	 			</leg>
	 		</legs>
			<weight>%s</weight>
	 		<volume>%s</volume>
	 		<priority>%s</priority>
	 		<duration>%s</duration>
	 	</mail>
	 * ----------------------
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void writeDelivery(DeliveryRequest event) throws Exception {
		
		doc = docBuilder.parse(logFile);
		Element events = doc.getDocumentElement();
		
		// Make Elements
        Element mail = doc.createElement("mail");
        Element day = doc.createElement("day");
        Element legs = doc.createElement("legs");
        Element weight = doc.createElement("weight");
        Element volume = doc.createElement("volume");
        Element priority = doc.createElement("priority");
        Element duration = doc.createElement("duration");
        
        // Add text values to tags
        day.appendChild(doc.createTextNode(event.getLogTime().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
        weight.appendChild(doc.createTextNode(Double.toString(event.getWeight())));
        volume.appendChild(doc.createTextNode(Double.toString(event.getVolume())));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        duration.appendChild(doc.createTextNode(Double.toString(event.getDuration())));
        
        // Make leg Elements
    	for (Leg legObject : event.getLegs()) {
    		Element leg = doc.createElement("leg");
            Element to = doc.createElement("to");
            Element from = doc.createElement("from");
            Element company = doc.createElement("company");
            Element cost = doc.createElement("cost");
            Element price = doc.createElement("price");
            
            to.appendChild(doc.createTextNode(legObject.getDestination().getName()));
            from.appendChild(doc.createTextNode(legObject.getOrigin().getName()));
            company.appendChild(doc.createTextNode(legObject.getCompany()));
            cost.appendChild(doc.createTextNode(Double.toString(legObject.getCost())));
            price.appendChild(doc.createTextNode(Double.toString(legObject.getPrice())));
            
            leg.appendChild(to);
            leg.appendChild(from);
            leg.appendChild(company);
            leg.appendChild(cost);
            leg.appendChild(price);
            legs.appendChild(leg);
    	}
        
        // Add tags together
        events.appendChild(mail);
        mail.appendChild(day);
        
        mail.appendChild(legs);
        mail.appendChild(weight);
        mail.appendChild(volume);
        mail.appendChild(priority);
        mail.appendChild(duration);
        
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
	public void writeRoute(Route event) throws Exception {
		
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
        to.appendChild(doc.createTextNode(event.getDestination().getName()));
        from.appendChild(doc.createTextNode(event.getOrigin().getName()));
        type.appendChild(doc.createTextNode(event.getType()));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        weightCost.appendChild(doc.createTextNode(Double.toString(event.getWeightCost())));
        volumeCost.appendChild(doc.createTextNode(Double.toString(event.getVolumeCost())));
        maxWeight.appendChild(doc.createTextNode(Integer.toString(event.getMaxWeight())));
        maxVolume.appendChild(doc.createTextNode(Integer.toString(event.getMaxVolume())));
        duration.appendChild(doc.createTextNode(Integer.toString(event.getDuration())));
        frequency.appendChild(doc.createTextNode(Integer.toString(event.getFrequency())));
        day.appendChild(doc.createTextNode(event.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
        
        // Add tags together
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
	public void writeCustomerPrice(CustomerPrice event) throws Exception {
		
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
        to.appendChild(doc.createTextNode(event.getDestination().getName()));
        from.appendChild(doc.createTextNode(event.getOrigin().getName()));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        weightCost.appendChild(doc.createTextNode(Double.toString(event.getWeightCost())));
        volumeCost.appendChild(doc.createTextNode(Double.toString(event.getVolumeCost())));
        
        // Add tags together
        price.appendChild(to);
        price.appendChild(from);
        price.appendChild(priority);
        price.appendChild(weightCost);
        price.appendChild(volumeCost);
        events.appendChild(price);
        
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
	public void writeDiscontinue(DiscontinueRoute event) throws Exception {
		
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
		PrintWriter pw = new PrintWriter(logFile.getName());
		pw.close();
	}
}
