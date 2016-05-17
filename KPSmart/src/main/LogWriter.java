package main;

import event.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/*
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
*/
import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;



public class LogWriter {
	
	// VARIABLES
	private String logFileName;
	private File logFile;
	/*	private static FileWriter writer; // */
	
	//*
	private Transformer transformer;
	private DocumentBuilder docBuilder;
	private Document doc;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    // */
    
	// CONSTRUCTORS
	public LogWriter(File logFile) throws Exception {
		this.logFileName = logFile.getName();
		this.logFile = logFile;
		
		/*
		writer = new FileWriter(logFileName, true);
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
		writer.write("test");
		System.out.println("WRITER MADE: "+ logFileName);
		writer.close();
		//*/
		
		//*
		docBuilder = docBuilderFactory.newDocumentBuilder();
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        doc = ensureLogExists();
        //*/

	}
	public LogWriter(String logFileName) throws Exception {
		this(new File(logFileName));
	}
	
	// METHODS
	public Document ensureLogExists() {
		// All gee in the directoree
				if (!logFile.isFile()) {
					try {
						logFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.print("Error creating log file in LogWriter");
					}
				}
		
		// Make log all good with XML Parser
		try {
			doc = docBuilder.parse(logFile);
		} catch (SAXException e) {
			doc = docBuilder.newDocument();
		} catch (IOException e) {
			System.out.println("--->ERROR! There's some problem with the logfile. IT can't be read by the XML Reader.");
			doc = docBuilder.newDocument();
			e.printStackTrace();
		}
		
		doc.normalizeDocument();
		
		return doc;
	}
	
	// Event Writers
	/**
	 * Writes a mail delivery event to the log file.
	 * 
	 * 	<mail>
	 *		<day>%s</day>
	 *		<legs>
	 *			<leg>
	 *				<to>%s</to>
	 *				<from>%s</from>
 	 *			</leg>
 	 *			<leg>
	 *				<to>%s</to>
	 *				<from>%s</from>
 	 *			</leg>
	 *		</legs>
	 *		<weight>%s</weight>
	 *		<volume>%s</volume>
	 *		<priority>%s</priority>
	 *		<price>%s</price>
	 *		<cost>%s</cost>
	 *		<duration>%s</duration>
	 *	</mail>
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void writeDelivery(MailEvent event) throws Exception {
		/*
		writer = new FileWriter(logFileName, true);
		String mail = "";
		
		String mailPt1 =  "<mail>\n" +
						  "  <day>%s</day>\n" +
				 		  "    <legs>\n";
		String leg = 	  "      <leg>\n" +
		 		  		  "        <to>%s</to>\n" +
		 		  		  "        <from>%s</from>\n" +
		 		  		  "      </leg>\n";
		String mailPt2 =  "    </legs>\n" +
				 		  "  <weight>%f</weight>\n" +
				 		  "  <volume>%f</volume>\n" +
				 		  "  <priority>%s</priority>\n" +
				 		  "  <price>%f</price>\n" +
				 		  "  <cost>%f</cost>\n" +
		 				  "  <duration>%d</duration>\n" +
		 				  "</mail>\n";
		
		mail += String.format(mailPt1, event.getDay());
		
		for (Leg legObject : event.getLegList()) {
			mail += String.format(leg, legObject.getTo(), legObject.getFrom());
		}
		
		mail += String.format(mailPt2, event.getWeight(),
									   event.getVolume(),
									   event.getPriority(),
									   event.getPrice(),
									   event.getCost(),
									   event.getDuration());
		ensureLogExists();
		writer.write(mail);
		System.out.print("Printed to "+logFileName+":\n"+mail);
		writer.close();
		//*/
		
		//*
		
		// Make document + Elements
		doc = ensureLogExists();
		
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
        doc.appendChild(mail);
        mail.appendChild(day);
        mail.appendChild(legs);
        mail.appendChild(weight);
        mail.appendChild(volume);
        mail.appendChild(priority);
        mail.appendChild(price);
        mail.appendChild(cost);
        mail.appendChild(duration);
        
        // Write the content into xml file
        doc.normalizeDocument();
        DOMSource source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
        try {
      	  transformer.transform(source, output);
        } catch (TransformerException e) {
      	  e.printStackTrace();
        }
		
        // Output to console for testing 
        StreamResult consoleResult =  new StreamResult(System.out);
        try {
      	  transformer.transform(source, consoleResult);
        } catch (TransformerException e) {
      	  e.printStackTrace();
        }
        // */
	}
	
	/**
	 * Writes cost event to log file.
	   <cost>
		  <company>%s</company>
		  <to>%s</to>
		  <from>%s</from>
		  <type>%s</type>
		  <priority>%s</priority>
		  <weightCost>4.0</weightCost>
		  <volumeCost>6.0</volumeCost>
		  <maxWeight>400.0</maxWeight>
		  <maxVolume>150.0</maxVolume>
		  <duration>6.0</duration>
		  <frequency>12.0</frequency>
		  <day>Thursday</day>
		</cost>
	 * 
	 * @param event
	 * @throws IOException 
	 */
	public void writeRoute(CostEvent event) throws Exception {
		/*
		writer = new FileWriter(logFileName, true);
		String cost = "<cost>\n" +
					  "  <company>%s</company>\n" +
					  "  <to>%s</to>\n" +
					  "  <from>%s</from>\n" +
					  "  <type>%s</type>\n" +
					  "  <priority>%s</priority>\n" +
					  "  <weightCost>%f</weightCost>\n" +
					  "  <volumeCost>%f</volumeCost>\n" +
					  "  <maxWeight>%d</maxWeight>\n" +
					  "  <maxVolume>%d</maxVolume>\n" +
					  "  <duration>%d</duration>\n" +
					  "  <frequency>%d</frequency>\n" +
					  "  <day>%s</day>\n" +
					  "</cost>\n";
		
		cost = String.format(cost, event.getCompany(),
								   event.getDestination(),
								   event.getOrigin(),
								   event.getType(),
								   event.getPriority(),
								   event.getWeightCost(),
								   event.getVolumeCost(),
								   event.getMaxWeight(),
								   event.getMaxVolume(),
								   event.getDuration(),
								   event.getFrequency(),
								   event.getDay());
		ensureLogExists();
		writer.write(cost);
		System.out.println(cost);
		writer.close();
		//*/
		
		//*
		// Get/Make Document
		doc = ensureLogExists();
		
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
        maxWeight.appendChild(doc.createTextNode(Double.toString(event.getMaxWeight())));
        maxVolume.appendChild(doc.createTextNode(Double.toString(event.getMaxVolume())));
        duration.appendChild(doc.createTextNode(Double.toString(event.getDuration())));
        frequency.appendChild(doc.createTextNode(Double.toString(event.getFrequency())));
        day.appendChild(doc.createTextNode(event.getDay()));
        
        // add tags together
        doc.appendChild(cost);
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

        // Write the content into xml file
        doc.normalizeDocument();
        DOMSource source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
        try {
      	  transformer.transform(source, output);
        } catch (TransformerException e) {
      	  e.printStackTrace();
        }
        
        // Output to console for testing
        StreamResult consoleResult =  new StreamResult(System.out);
        try {
      	  transformer.transform(source, consoleResult);
        } catch (TransformerException e) {
      	  e.printStackTrace();
        }
        //*/
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
	 * Writes price event to the log file.
	 * <cost>
		  <company>%s</company>
		  <to>%s</to>
		  <from>%s</from>
		  <type>%s</type>
		  <priority>%s</priority>
		  <weightCost>20.8</weightCost>
		  <volumeCost>56.2</volumeCost>
		  <maxWeight>43.0</maxWeight>
		  <maxVolume>123.0</maxVolume>
		  <duration>33.0</duration>
		  <frequency>2.0</frequency>
		  <day>%s</day>
		</cost>
	 * @throws IOException 
	 * 
	 */
	public void writeCustomerPrice(PriceEvent event) throws Exception {
		/*
		writer = new FileWriter(logFileName, true);
		String price = "<price>\n" +
				  	   "  <to>%s</to>\n" +
					   "  <from>%s</from>\n" +
					   "  <priority>%s</priority>\n" +
					   "  <weightCost>%f</weightCost>\n" +
					   "  <volumeCost>%f</volumeCost>\n" +
					   "</price>\n";
		
		price = String.format(price, event.getDestination(),
									 event.getOrigin(),
									 event.getPriority(),
									 event.getWeightCost(),
									 event.getVolumeCost());
		ensureLogExists();
		writer.write(price);
		System.out.println(price);
		writer.close();
		//*/
		
		//*
		// Get/Make Document
		doc = ensureLogExists();
		
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
        doc.appendChild(price);
        price.appendChild(to);
        price.appendChild(from);
        price.appendChild(priority);
        price.appendChild(weightCost);
        price.appendChild(volumeCost);

        // Write the content into xml file
        doc.normalizeDocument();
        DOMSource source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
        transformer.transform(source, output);

        
        // Output to console for testing
        StreamResult consoleResult =  new StreamResult(System.out);
        try {
      	  transformer.transform(source, consoleResult);
        } catch (TransformerException e) {
      	  e.printStackTrace();
        }
        // */
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
	 * Writes discontinue event to log file.
<discontinue>
  <company>NZ Post</company>
  <to>Wellington</to>
  <from>Christchurch</from>
  <type>Sea</type>
</discontinue>
	 * @throws IOException 
	 * 
	 */
	public void writeDiscontinue(DiscontinueEvent event) throws Exception {
		/*
		writer = new FileWriter(logFileName, true);
		String disc = "<discontinue>\n" +
					  "  <company>%s</company>\n" +
					  "  <to>%s</to>\n" +
					  "  <from>%s</from>\n" +
					  "  <type>%s</type>\n" +
					  "</discontinue>\n";
		
		disc = String.format(disc, event.getCompany(),
								   event.getTo(),
								   event.getFrom(),
								   event.getType());
		ensureLogExists();
		writer.write(disc);
		System.out.println(disc);
		writer.close();
		//*/
		
		//* 
		// Make document
		doc = ensureLogExists();
	  
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
	  	doc.appendChild(discontinue);
	  	discontinue.appendChild(company);
	  	discontinue.appendChild(to);
	  	discontinue.appendChild(from);
	  	discontinue.appendChild(type);
	
	  	// Write the content into xml file
	  	doc.normalizeDocument();
	  	DOMSource source = new DOMSource(doc);
	  	StreamResult output = new StreamResult(logFile);
		transformer.transform(source, output);
	
	  	// Output to console for testing
	  	StreamResult consoleResult =  new StreamResult(System.out);
	  	try {
		  	transformer.transform(source, consoleResult);
	  	} catch (TransformerException e) {
		  	e.printStackTrace();
	  	}
	  	// */
	}
	
	public void clearFile() {
		try {
			PrintWriter pw = new PrintWriter(logFileName);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error occured when clearing logfile. Logfile could not be cleared.");
		}
	}
}
