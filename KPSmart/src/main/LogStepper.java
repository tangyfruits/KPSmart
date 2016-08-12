package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class LogStepper {

	// FIELDS
	DocumentBuilderFactory dbf;
	DocumentBuilder db;
	Document doc;
	File logfile;
	ArrayList<Node> events;
	int position;

	// CONSTRUCTOR
	public LogStepper(File logfile) {
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(logfile);
			Element parent = doc.getDocumentElement();

			NodeList temp = parent.getChildNodes();
			events = new ArrayList<>();

			for (int i = 0; i < temp.getLength(); i++) {
				if (temp.item(i).getNodeType() != Node.TEXT_NODE) {
					events.add(temp.item(i));
				}
			}
			position = events.size() - 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// METHODS
	/**
	 * Update log stepper if there have been new events written since it has
	 * been last used.
	 */
	public void refresh() {
		try {
			doc = db.parse(logfile);
			Element parent = doc.getDocumentElement();
			NodeList temp = parent.getChildNodes();
			System.out.println("Temp:" + temp.getLength());
			events = new ArrayList<>();

			for (int i = 0; i < temp.getLength(); i++) {
				if (temp.item(i).getNodeType() != Node.TEXT_NODE) {
					events.add(temp.item(i));
				}
			}
			position = events.size() - 1;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element parent = doc.getDocumentElement();
		NodeList temp = parent.getChildNodes();
		System.out.println("Temp:" + temp.getLength());
		events = new ArrayList<>();

		for (int i = 0; i < temp.getLength(); i++) {
			if (temp.item(i).getNodeType() != Node.TEXT_NODE) {
				events.add(temp.item(i));
			}
		}
		position = events.size() - 1;
	}

	// Event getters and checkers
	public HashMap<String, String> latestEvent() {
		HashMap<String, String> event = null;
		position = events.size() - 1;
		Element e;
		if (position >= 0) {
			if (events.get(position).getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) events.get(position);
				event = nodeToHashMap(e);
			} else {
				System.out.println("Not an Element!");
			}
		}
		return event;
	}

	public HashMap<String, String> firstEvent() {
		HashMap<String, String> event = null;
		position = 0;
		Element e;
		if (position >= 0) {
			if (events.get(position).getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) events.get(position);
				event = nodeToHashMap(e);
			} else {
				System.out.println("Not an Element!");
			}
		}
		return event;
	}

	public HashMap<String, String> prevEvent() {
		HashMap<String, String> event = null;
		if (isPrev()) {
			position--;
			Element e;
			if (events.get(position).getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) events.get(position);
				event = nodeToHashMap(e);
			} else {
				System.out.println("Not an Element!");
			}
		}
		return event;
	}

	public HashMap<String, String> nextEvent() {
		HashMap<String, String> event = null;
		if (isNext()) {
			position++;
			Element e;
			if (events.get(position).getNodeType() == Node.ELEMENT_NODE) {
				e = (Element) events.get(position);
				event = nodeToHashMap(e);
			} else {
				System.out.println("Not an Element!");
			}
		}
		return event;
	}

	public boolean isNext() {
		boolean next = true;
		if (position >= events.size() - 1) {
			return false;
		}
		return next;
	}

	public boolean isPrev() {
		boolean prev = true;
		if (position <= 0) {
			prev = false;
		}
		return prev;
	}

	// Local utility method
	private HashMap<String, String> nodeToHashMap(Element node) {
		Element event = node;
		HashMap<String, String> map = new HashMap<String, String>();

		switch (node.getNodeName()) {
		case "mail":
			map.put("eventType", "mail");
			map.put("logged", event.getElementsByTagName("logged").item(0).getTextContent());
			map.put("user", event.getElementsByTagName("user").item(0).getTextContent());
			map.put("destination", event.getElementsByTagName("to").item(0).getTextContent());
			map.put("origin", event.getElementsByTagName("from").item(0).getTextContent());

			map.put("legnum", Integer.toString(event.getElementsByTagName("leg").getLength()));

			NodeList legs = event.getElementsByTagName("leg");
			for (int i = 0; i < legs.getLength(); i++) {
				Element leg = (Element) legs.item(i);
				String legNo = "leg " + i;
				map.put(legNo + " to", leg.getElementsByTagName("to").item(0).getTextContent());
				map.put(legNo + " from", leg.getElementsByTagName("from").item(0).getTextContent());
				map.put(legNo + " type", leg.getElementsByTagName("type").item(0).getTextContent());
				map.put(legNo + " company", leg.getElementsByTagName("company").item(0).getTextContent());
				map.put(legNo + " cost", leg.getElementsByTagName("cost").item(0).getTextContent());
				map.put(legNo + " price", leg.getElementsByTagName("price").item(0).getTextContent());
			}

			map.put("weight", event.getElementsByTagName("weight").item(0).getTextContent());
			map.put("volume", event.getElementsByTagName("volume").item(0).getTextContent());
			map.put("priority", event.getElementsByTagName("priority").item(0).getTextContent());
			map.put("duration", event.getElementsByTagName("duration").item(0).getTextContent());
			break;

		case "cost":
			map.put("eventType", "cost");
			map.put("to", event.getElementsByTagName("to").item(0).getTextContent());
			map.put("from", event.getElementsByTagName("from").item(0).getTextContent());
			map.put("logged", event.getElementsByTagName("logged").item(0).getTextContent());
			map.put("user", event.getElementsByTagName("user").item(0).getTextContent());
			map.put("company", event.getElementsByTagName("company").item(0).getTextContent());
			map.put("type", event.getElementsByTagName("type").item(0).getTextContent());
			map.put("priority", event.getElementsByTagName("priority").item(0).getTextContent());
			map.put("weightCost", event.getElementsByTagName("weightCost").item(0).getTextContent());
			map.put("volumeCost", event.getElementsByTagName("volumeCost").item(0).getTextContent());
			map.put("maxWeight", event.getElementsByTagName("maxWeight").item(0).getTextContent());
			map.put("maxVolume", event.getElementsByTagName("maxVolume").item(0).getTextContent());
			map.put("duration", event.getElementsByTagName("duration").item(0).getTextContent());
			map.put("frequency", event.getElementsByTagName("frequency").item(0).getTextContent());
			map.put("day", event.getElementsByTagName("day").item(0).getTextContent());
			map.put("hour", event.getElementsByTagName("hour").item(0).getTextContent());
			break;

		case "price":
			map.put("eventType", "price");
			map.put("to", event.getElementsByTagName("to").item(0).getTextContent());
			map.put("from", event.getElementsByTagName("from").item(0).getTextContent());
			map.put("logged", event.getElementsByTagName("logged").item(0).getTextContent());
			map.put("user", event.getElementsByTagName("user").item(0).getTextContent());
			map.put("priority", event.getElementsByTagName("priority").item(0).getTextContent());
			map.put("weightCost", event.getElementsByTagName("weightCost").item(0).getTextContent());
			map.put("volumeCost", event.getElementsByTagName("volumeCost").item(0).getTextContent());
			break;

		case "discontinue":
			map.put("eventType", "discontinue");
			map.put("company", event.getElementsByTagName("company").item(0).getTextContent());
			map.put("logged", event.getElementsByTagName("logged").item(0).getTextContent());
			map.put("user", event.getElementsByTagName("user").item(0).getTextContent());
			map.put("to", event.getElementsByTagName("to").item(0).getTextContent());
			map.put("from", event.getElementsByTagName("from").item(0).getTextContent());
			map.put("type", event.getElementsByTagName("type").item(0).getTextContent());
			break;

		default:
			map = null;
			System.out.println("Log Stepper Error. Unknown event type: " + node.getNodeName());
			break;
		}
		return map;
	}
}
