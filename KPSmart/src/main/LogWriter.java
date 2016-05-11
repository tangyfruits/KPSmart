package main;



import event.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;


public class LogWriter {
	
	// VARIABLES
	private String logFileName;
	private File logFile;
	
	private String mailTag = "<mail>" +
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
	private String legTag = "<leg>" +
								"<to>%s</to>" +
								"<from>%s</from>" +
							 "</leg";
	private String costTag = "<cost>" +
								"<company>%s</company>" +
								"<to>%s</to>" +
								"<from>%s</from>" +
								"<type>%s</type>" +
								"<priority>%s</priority>" +
								"<weightCost>%s</weightCost>" +
								"<volumeCost>%s</volumeCost>" +
								"<maxWeight>%s</maxWeight>" +
								"<maxVolume>%s</maxVolume>" +
								"<duration>%s</duration>" +
								"<frequency>%s</frequency>" +
								"<day>%s</day>" +
							  "</mail>";
	private String priceTag = "<price>" +
							     "<to>%s</to>" +
								 "<from>%s</from>" +
								 "<priority>%s</priority>" +
								 "<weightCost>%s</weightCost>" +
								 "<volumeCost>%s</volumeCost>" +
							   "</price>";
	private String discontinueTag = "<discontinue>" +
								       "<company>%s</company>" +
								       "<to>%s</to>" +
								       "<from>%s</from>" +
								       "<type>%s</type>" +
								     "</discontinue>";
	
	// CONSTRUCTOR
	public LogWriter(String logFileName) {
		this.logFileName = logFileName;
		this.logFile = new File(logFileName);
	}
	
	
	
	// METHODS
	public void thing() {
		
		Document doc;
		
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			// root element
			Element cars = doc.createElement("cars");
			doc.appendChild(cars);
		
			//  supercars element
			Element supercar = doc.createElement("supercars");
			doc.appendChild(supercar);
		

			// carname element
			Element carname = doc.createElement("carname");
			carname.appendChild(doc.createTextNode("Ferrari 101"));
			
			supercar.appendChild(carname);
			
			 // write the content into xml file
	         Transformer transformer = TransformerFactory.newInstance().newTransformer();
	         
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File("../../cars.xml"));
	         
	         transformer.transform(source, result);
	         
	         // Output to console for testing
	         StreamResult consoleResult = new StreamResult(System.out);
	         transformer.transform(source, consoleResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		

	}
	
	
	public boolean logFileExists() {
		
		
		return true;
	}
	
	
	public void writeDelivery(MailEvent event) {
		
		
		String s = String.format(mailTag, "2352");
	}
	
	public void writeRoute(CostEvent event) {
		
	}
	public void writeRoute(Route route) {
		
	}
	
	public void writeCustomerPrice(PriceEvent event) {
		
		String s = String.format(priceTag, event.getOrigin(),
										   event.getDestination(),
										   event.getPriority(),
										   event.getWeightCost(),
										   event.getVolumeCost());
		
		// Write s to File
		
		
	}
	public void writeCustomerPrice(CustomerPrice price) {
		
	}
	
	public void writeDiscontinue(DiscontinueEvent event) {
		
	}
	
}
