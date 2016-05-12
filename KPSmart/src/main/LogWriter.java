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
import java.io.PrintWriter;


public class LogWriter {
	
	// VARIABLES
	private String logFileName;
	private File logFile;
	private Transformer transformer;
	private DocumentBuilder docBuilder;
	private TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    
	// CONSTRUCTOR
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
	
	// METHODS
	// TODO Mail Delivery - Incomplete
	public void writeDelivery(MailEvent event) {
		
		String mailTag = "<mail>" +
							"<day>%s</day>" +
							"<to>%s</to>" +
							"<from>%s</from>" +
							"<weight>%s</weight>" +
							"<volume>%s</volume>" +
							"<priority>%s</priority>" +
							"<price>%s</price>" +
							"<cost>%s</cost>" +
							"<duration>%s</duration>" +
						  "</mail>";
		
		String legTag = "<leg>" +
							"<to>%s</to>" +
							"<from>%s</from>" +
						 "</leg";
		
		// Make document
        Document doc = docBuilder.newDocument();
        
        // Make Elements
        Element mail = doc.createElement("mail");
        
        Element day = doc.createElement("day");
        
        Element weight = doc.createElement("weight");
        Element volume = doc.createElement("volume");
        Element priority = doc.createElement("priority");
        Element price = doc.createElement("price");
        Element cost = doc.createElement("cost");
        Element duration = doc.createElement("duration");
        
        // Make leg elements
        Element leg = doc.createElement("leg");
        
        Element legTo = doc.createElement("to");
        Element legFrom = doc.createElement("from");
        
        
        // Add text values to tags
        day.appendChild(doc.createTextNode(event.getCompany()));
        
        to.appendChild(doc.createTextNode(event.getDestination()));
        from.appendChild(doc.createTextNode(event.getOrigin()));
        
        weight.appendChild(doc.createTextNode(event.getType()));
        volume.appendChild(doc.createTextNode(event.getPriority()));
        priority.appendChild(doc.createTextNode(Double.toString(event.getWeightCost())));
        price.appendChild(doc.createTextNode(Double.toString(event.getVolumeCost())));
        cost.appendChild(doc.createTextNode(Double.toString(event.getMaxWeight())));
        duration.appendChild(doc.createTextNode(Double.toString(event.getMaxVolume())));
        
        for (int i = 0; i < 6; i++) {
            leg.appendChild(legTo);
            leg.appendChild(legFrom);
        }
        
        // add tags together
        doc.appendChild(mail);
        
        mail.appendChild(day);

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
		
		
		
	}
	
	// Routes / Transport Costs
	public void writeRoute(CostEvent event) {
		
		// Make document
        Document doc = docBuilder.newDocument();
        
        // Make Elements
        Element discontinue = doc.createElement("discontinue");
        
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
        doc.appendChild(discontinue);
        
        discontinue.appendChild(company);
        discontinue.appendChild(to);
        discontinue.appendChild(from);
        discontinue.appendChild(type);
        discontinue.appendChild(priority);
        discontinue.appendChild(weightCost);
        discontinue.appendChild(volumeCost);
        discontinue.appendChild(maxWeight);
        discontinue.appendChild(maxVolume);
        discontinue.appendChild(duration);
        discontinue.appendChild(frequency);
        discontinue.appendChild(day);

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
	
	// TODO
	public void writeRoute(Route route) {
		
	}
	
	// Customer Prices - 1/2 done
	public void writeCustomerPrice(PriceEvent event) {
		
		// Make document
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
		
		
	}
	
	// TODO
	public void writeCustomerPrice(CustomerPrice price) {
		
	}
	
	// Discontinue - done
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
	}
	
	// done
	public void clearFile() {
		try {
			PrintWriter pw = new PrintWriter(logFileName);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
