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
	private static boolean LOGS = false;
	
	// FIELDS
	private File logFile;
	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;
	private Document doc;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
	private Transformer transformer;

	// CONSTRUCTORS
	public LogWriter(File file) {
		this.logFile = file;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			docBuilder.setErrorHandler(new ErrorHandler() {
	            @Override
	            public void fatalError(SAXParseException e) throws SAXException {
	            	throw e;
	            }
	            @Override
	            public void error(SAXParseException e) throws SAXException {
	            	throw e;
	            }
				@Override
				public void warning(SAXParseException e) throws SAXException {
					throw e;
				}
	        });
	        transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		} catch(Exception e) {
			System.out.println("Error constructing the LogWriter");
			e.printStackTrace();
		}
        ensureLogExists();
	}
	public LogWriter(File file, boolean logging) {
		this(file);
		LOGS = logging;
	}
	
	// METHODS
	public void ensureLogExists() {
		
		if (LOGS) {System.out.println("ENSURE:           "+logFile.getName());}
		
		// Create Log File if it doesn't exist
		if (!logFile.isFile()) {
			try {
				if (LOGS) {System.out.println("   File: created.");}
				logFile.createNewFile();
				doc = docBuilder.newDocument();
				Element events = doc.createElement("events");
				doc.appendChild(events);
				
				DOMSource    source = new DOMSource(doc);
		        StreamResult output = new StreamResult(logFile);
		      	try {
					transformer.transform(source, output);
				} catch (TransformerException e1) {
					e1.printStackTrace();
				}
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
			if (LOGS) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

			// Create new Document
			doc = docBuilder.newDocument();
			Element events = doc.createElement("events");
			doc.appendChild(events);
			
			DOMSource    source = new DOMSource(doc);
	        StreamResult output = new StreamResult(logFile);
	      	try {
				transformer.transform(source, output);
			} catch (TransformerException e1) {
				e1.printStackTrace();
			}

	      	if (LOGS) {System.out.println("   Parse: failed. new doc made");}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Console Logging for Testing
		if (LOGS) {
			DOMSource    source = new DOMSource(doc);
			StreamResult consoleResult =  new StreamResult(System.out);
	      	try {
				transformer.transform(source, consoleResult);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
	      	System.out.println("---------------");
		}
	}
	
	// Event Writers
	/**
	 * Writes Mail Delivery Event to the log file.
	 * 
	 * ------- FORMAT -------
	 * <pre>
	 * {@literal
	 * <mail>
	 *   <logged>%s</logged>
	 *   <to>%s</to>
	 *   <from>%s</from>
	 *   <legs>
	 *     <leg>
	 *       <to>%s</to>
	 *       <from>%s</from>
	 *       <type>%s</type>
	 *       <company>%s</company>
	 *       <cost>%f</cost>
	 *       <price>%f</price>
 	 *     </leg>
 	 *     <leg>
	 *       <to>%s</to>
	 *       <from>%s</from>
	 *       <type>%s</type>
	 *       <company>%s</company>
	 *       <cost>%f</cost>
	 *       <price>%f</price>
 	 *     </leg>
	 *   </legs>
	 *   <weight>%f</weight>
	 *   <volume>%f</volume>
	 *   <priority>%s</priority>
	 *   <duration>%d</duration>
	 * </mail>
	 * }
	 * </pre>
	 * ----------------------
	 * 
	 * @param event The delivery request event to write to the log file
	 * @throws IOException 
	 */
	public void writeDeliveryRequest(DeliveryRequest event) {
		
		try {
			doc = docBuilder.parse(logFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element events = doc.getDocumentElement();
		
		// Make Elements
        Element mail = doc.createElement("mail");
        Element logged = doc.createElement("logged");
        Element to = doc.createElement("to");
        Element from = doc.createElement("from");
        Element legs = doc.createElement("legs");
        Element weight = doc.createElement("weight");
        Element volume = doc.createElement("volume");
        Element priority = doc.createElement("priority");
        Element duration = doc.createElement("duration");
        
        // Find Primary Origin and Final Destination
        String origin = "";
        String destination = "";
        
        // Make leg Elements
    	for (Leg legObject : event.getLegs()) {
    		Element leg = doc.createElement("leg");
            Element legTo = doc.createElement("to");
            Element legFrom = doc.createElement("from");
            Element legType = doc.createElement("type");
            Element legCompany = doc.createElement("company");
            Element legCost = doc.createElement("cost");
            Element legPrice = doc.createElement("price");
            
            if (origin == "") {
            	origin = legObject.getOrigin().getName();
            }
            destination = legObject.getDestination().getName();
            
            legTo.appendChild(doc.createTextNode(legObject.getDestination().getName()));
            legFrom.appendChild(doc.createTextNode(legObject.getOrigin().getName()));
            legType.appendChild(doc.createTextNode(legObject.getType()));
            legCompany.appendChild(doc.createTextNode(legObject.getCompany()));
            legCost.appendChild(doc.createTextNode(Double.toString(legObject.getCost())));
            legPrice.appendChild(doc.createTextNode(Double.toString(legObject.getPrice())));
            
            leg.appendChild(legTo);
            leg.appendChild(legFrom);
            leg.appendChild(legType);
            leg.appendChild(legCompany);
            leg.appendChild(legCost);
            leg.appendChild(legPrice);
            legs.appendChild(leg);
    	}
        
    	 // Add text values to tags
    	logged.appendChild(doc.createTextNode(event.getLogTime().toString()));
        to.appendChild(doc.createTextNode(destination));
        from.appendChild(doc.createTextNode(origin));
        weight.appendChild(doc.createTextNode(Double.toString(event.getWeight())));
        volume.appendChild(doc.createTextNode(Double.toString(event.getVolume())));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        duration.appendChild(doc.createTextNode(Integer.toString(event.getDuration())));
    	
        // Add tags together
        events.appendChild(mail);
        mail.appendChild(logged);
        mail.appendChild(to);
        mail.appendChild(from);
        mail.appendChild(legs);
        mail.appendChild(weight);
        mail.appendChild(volume);
        mail.appendChild(priority);
        mail.appendChild(duration);
        
        // Write to XML file
        DOMSource    source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
   	    try {
			transformer.transform(source, output);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
   	    // Console Logging for Testing
   	    if (LOGS) {
   	    	System.out.println("\n----------mail");
	        StreamResult consoleResult =  new StreamResult(System.out);
	      	try {
				transformer.transform(source, consoleResult);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
   	    }
	}

	/**
	 * Writes Cost Event to log file in the following format
	 * 
	 * <pre>
	 * {@literal
	 * <cost>
	 *   <to>%s</to>
	 *   <from>%s</from>
	 *   <company>%s</company>
	 *   <type>%s</type>
	 *   <priority>%s</priority>
	 *   <weightCost>%f</weightCost>
	 *   <volumeCost>%f</volumeCost>
	 *   <maxWeight>%d</maxWeight>
	 *   <maxVolume>%d</maxVolume>
	 *   <duration>%d</duration>
	 *   <frequency>%d</frequency>
	 *   <day>%s</day>
	 *   <hour>%d</hour>
	 * </cost>
	 * }
	 * </pre>
	 * 
	 * @param event The cost event (route) to write to the log file
	 */
	public void writeRoute(Route event) {
		
		try {
			doc = docBuilder.parse(logFile);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element events = doc.getDocumentElement();
		
		// Make Elements
        Element cost = doc.createElement("cost");
        Element to = doc.createElement("to");
        Element from = doc.createElement("from");
        Element company = doc.createElement("company");
        Element type = doc.createElement("type");
        Element priority = doc.createElement("priority");
        Element weightCost = doc.createElement("weightCost");
        Element volumeCost = doc.createElement("volumeCost");
        Element maxWeight = doc.createElement("maxWeight");
        Element maxVolume = doc.createElement("maxVolume");
        Element duration = doc.createElement("duration");
        Element frequency = doc.createElement("frequency");
        Element day = doc.createElement("day");
        Element hour = doc.createElement("hour");
        
        // Add text values to tags
        to.appendChild(doc.createTextNode(event.getDestination().getName()));
        from.appendChild(doc.createTextNode(event.getOrigin().getName()));
        company.appendChild(doc.createTextNode(event.getCompany()));
        type.appendChild(doc.createTextNode(event.getType()));
        priority.appendChild(doc.createTextNode(event.getPriority()));
        weightCost.appendChild(doc.createTextNode(Double.toString(event.getWeightCost())));
        volumeCost.appendChild(doc.createTextNode(Double.toString(event.getVolumeCost())));
        maxWeight.appendChild(doc.createTextNode(Integer.toString(event.getMaxWeight())));
        maxVolume.appendChild(doc.createTextNode(Integer.toString(event.getMaxVolume())));
        duration.appendChild(doc.createTextNode(Integer.toString(event.getDuration())));
        frequency.appendChild(doc.createTextNode(Integer.toString(event.getFrequency())));
        day.appendChild(doc.createTextNode(event.getDay().getDisplayName(TextStyle.FULL, Locale.ENGLISH)));
        hour.appendChild(doc.createTextNode(Integer.toString(event.getStartTime())));
        
        // Add tags together
        cost.appendChild(to);
        cost.appendChild(from);
        cost.appendChild(company);
        cost.appendChild(type);
        cost.appendChild(priority);
        cost.appendChild(weightCost);
        cost.appendChild(volumeCost);
        cost.appendChild(maxWeight);
        cost.appendChild(maxVolume);
        cost.appendChild(duration);
        cost.appendChild(frequency);
        cost.appendChild(day);
        cost.appendChild(hour);
        events.appendChild(cost);
        
        // Write to XML file
        DOMSource    source = new DOMSource(doc);
        StreamResult output = new StreamResult(logFile);
      	try {
			transformer.transform(source, output);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
        
      	// Console Logging for Testing
      	if (LOGS) {
      		System.out.println("\n----------cost1");
	        StreamResult consoleResult =  new StreamResult(System.out);
	      	try {
				transformer.transform(source, consoleResult);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
      	}
	}

	/**
	 * Writes Price Event to the log file.
	 * 
	 * ------- FORMAT -------
	 * <pre>
	 * {@literal
	 * <price>
	 *   <to>%s</to>
	 *   <from>%s</from>
	 *   <type>%s</type>
	 *   <priority>%s</priority>
	 *   <weightCost>%f</weightCost>
	 *   <volumeCost>%f</volumeCost>
	 * </price>
	 * }
	 * </pre>
	 * ----------------------
	 * 
	 * @param event The price event to write to the log file
	 * @throws IOException 
	 */
	public void writeCustomerPrice(CustomerPrice event) {
		
		try {
			doc = docBuilder.parse(logFile);
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
        try {
			transformer.transform(source, output);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
        
        // Console Logging for Testing
        if(LOGS) {
        	System.out.println("\n----------price");
	        StreamResult consoleResult =  new StreamResult(System.out);
	      	try {
				transformer.transform(source, consoleResult);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
        }
	}

	/**
	 * Writes Discontinue Event to log file.
	 * 
	 * ------- FORMAT -------
	 * <pre>
	 * {@literal
	 * <discontinue>
	 *   <company>%s</company>
	 *   <to>%s</to>
	 *   <from>%s</from>
	 *   <type>%s</type>
	 * </discontinue>
	 * }
	 * </pre>
	 * ----------------------
	 *
	 * @param event The discontinue event to write to the log file
	 * @throws IOException 
	 */
	public void writeDiscontinue(DiscontinueRoute event) {
		
		try {
			doc = docBuilder.parse(logFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Node events = doc.getDocumentElement();
	  
	  	// Make Elements
	  	Element discontinue = doc.createElement("discontinue");
	  	Element to = doc.createElement("to");
	  	Element from = doc.createElement("from");
	  	Element company = doc.createElement("company");
	  	Element type = doc.createElement("type");
	  	
	  	// Add text values to tags
	  	to.appendChild(doc.createTextNode(event.getDestination().getName()));
	  	from.appendChild(doc.createTextNode(event.getOrigin().getName()));
	  	company.appendChild(doc.createTextNode(event.getCompany()));
	  	type.appendChild(doc.createTextNode(event.getType()));
	  	
	  	// add tags together
	  	discontinue.appendChild(to);
	  	discontinue.appendChild(from);
	  	discontinue.appendChild(company);
	  	discontinue.appendChild(type);
	  	events.appendChild(discontinue);
	  	
	  	// Write to XML file
	  	DOMSource    source = new DOMSource(doc);
	  	StreamResult output = new StreamResult(logFile);
		try {
			transformer.transform(source, output);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	
		// Console Logging for Testing
		if(LOGS) {
			System.out.println("\n----------disc.");
		  	StreamResult consoleResult =  new StreamResult(System.out);
			try {
				transformer.transform(source, consoleResult);
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void clearFile() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(logFile.getName());
		pw.close();
	}
}
