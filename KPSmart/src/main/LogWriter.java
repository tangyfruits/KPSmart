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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class LogWriter {
	
	// VARIABLES
	private String logFileName;
	private File logFile;
	private BufferedWriter writer;
	
	private Transformer transformer;
	private DocumentBuilder docBuilder;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    
	// CONSTRUCTORS
	public LogWriter(String logFileName) {
		this.logFileName = logFileName;
		this.logFile = new File(logFileName);
		
        try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
	        transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public LogWriter(File logFile) {
		this.logFileName = logFile.getName();
		this.logFile = logFile;
		
        try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
	        transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// METHODS
	public void ensureLogExists() {
		if (!logFile.isFile()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("Error creating log file in LogWriter");
			}
		}
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
	 *		<to>%s</to>
	 *		<from>%s</from>
	 *		<weight>%s</weight>
	 *		<volume>%s</volume>
	 *		<priority>%s</priority>
	 *		<price>%s</price>
	 *		<cost>%s</cost>
	 *		<duration>%s</duration>
	 *	</mail>
	 * 
	 * @param event
	 */
	public void writeDelivery(MailEvent event) {
		
		// Make document + Elements
        Document doc = docBuilder.newDocument();
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
	
	public void writeRoute(CostEvent event) {
		
		// Get/Make Document
		Document doc = docBuilder.newDocument();
		
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
	}
	public void writeRoute(Route route) {
		
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
	
	public void writeCustomerPrice(PriceEvent event) {
		
		// Get/Make Document
		Document doc = docBuilder.newDocument();
		
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
	public void writeCustomerPrice(CustomerPrice price) {
		PriceEvent event = new PriceEvent(price.getOrigin().getName(), 
										  price.getDestination().getName(), 
										  price.getPriority(), 
										  price.getWeightCost(), 
										  price.getVolumeCost());
		writeCustomerPrice(event);
		
	}
	
	public void writeDiscontinue(DiscontinueEvent event) {
	      
    	  // Make document
          Document doc = docBuilder.newDocument();
          
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
	
	public void clearFile() {
		try {
			PrintWriter pw = new PrintWriter(logFileName);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
