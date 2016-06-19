package controller;

import java.util.HashMap;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.LogStepper;
import main.Main;

public class HistoryController extends NavBarController {

	// FIELDS
	private LogStepper stepper;
	//private HashMap<String,String> map;
	private BooleanProperty hasNext = new SimpleBooleanProperty(false);
	private BooleanProperty hasPrev = new SimpleBooleanProperty(false);
	// FXML ELEMENTS
	@FXML
	private Label logtype;
	@FXML
	private VBox displayBox;
	@FXML
	private Button previous, next, first, last;
	
	// CONSTRUCTOR
	public HistoryController(Main main) {
		this.main = main;
		stepper = new LogStepper(main.getLogFile());
	}
	
	// METHODS
	public void initData() {
		HashMap<String, String> item = stepper.latestEvent();
		display(item);
	}
	    
	// FXML Methods
	@FXML
	private void displayFirst(){
		display(stepper.firstEvent());
	}
	@FXML
	private void displayLast(){
		display(stepper.latestEvent());
	}
	@FXML
	private void displayNext(){
		if (stepper.isNext()) {
			display(stepper.nextEvent());
		}
	}
	@FXML
	private void displayPrevious(){
		if (stepper.isPrev()) {
			display(stepper.prevEvent());
		}
	}
	
	// Display Methods
	public void display(HashMap<String,String> item){
		next.disableProperty().bind(hasNext);
		last.disableProperty().bind(hasNext);
		previous.disableProperty().bind(hasPrev);
		first.disableProperty().bind(hasPrev);
		if(stepper.isNext()){
			hasNext.set(false);
		}
		else{
			hasNext.set(true);
		}
		if(stepper.isPrev()){
			hasPrev.set(false);
		}
		else{
			hasPrev.set(true);
		}
		displayBox.getChildren().clear();
		switch (item.get("eventType")) {
		case "mail":
			displayMail(item);
			break;
		case "cost":
			displayCost(item);
			break;
		case "price":
			displayPrice(item);
			break;
		case "discontinue":
			displayDiscontinue(item);
			break;
		default:
			System.out.println("Something fucked up");
			break;
		}
	}
	public void displayMail(HashMap<String, String> item) {
		logtype.setText("Transport Cost");
		Text a = new Text("Origin: " + item.get("origin"));
		displayBox.getChildren().add(a);	
		a = new Text("Destination: " + item.get("destination"));
		displayBox.getChildren().add(a);
		a = new Text("Logged: " + item.get("logged"));
		displayBox.getChildren().add(a);
		a = new Text("Weight: " + item.get("weight"));
		displayBox.getChildren().add(a);
		a = new Text("Volume: " + item.get("volume"));
		displayBox.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		displayBox.getChildren().add(a);
		a = new Text("Duration: " + item.get("duration"));
		displayBox.getChildren().add(a);
		a = new Text(" ");
		displayBox.getChildren().add(a);
		
		a = new Text("Legs:");
		displayBox.getChildren().add(a);
		
		for(int i =0; i<Integer.parseInt(item.get("legnum")); i++){
			int leg = i;
			a = new Text("	To: " + item.get("leg " +leg +" to"));
			displayBox.getChildren().add(a);
			a = new Text("	From: " + item.get("leg " +leg +" from"));
			displayBox.getChildren().add(a);
			a = new Text("	Type: " + item.get("leg " +leg +" type"));
			displayBox.getChildren().add(a);
			a = new Text("	Company: " + item.get("leg " +leg +" company"));
			displayBox.getChildren().add(a);
			a = new Text("	Cost: " + item.get("leg " +leg +" cost"));
			displayBox.getChildren().add(a);
			a = new Text("	Price: " + item.get("leg " +leg +" price"));
			displayBox.getChildren().add(a);
			if (Integer.parseInt(item.get("legnum"))-1!=leg) {
				a = new Text("	- - - - - -  ");
				displayBox.getChildren().add(a);
			}
		}
		
		
				
		displayBox.setSpacing(5);
	}
	public void displayCost(HashMap<String, String> item) {
		logtype.setText("Transport Cost");
		Text a = new Text("To: " + item.get("to"));
		displayBox.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		displayBox.getChildren().add(a);		
		a = new Text("Comapny: " + item.get("company"));
		displayBox.getChildren().add(a);
		a = new Text("Type: " + item.get("type"));
		displayBox.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		displayBox.getChildren().add(a);
		a = new Text("Weight Cost: " + item.get("weightCost"));
		displayBox.getChildren().add(a);
		a = new Text("Volume Cost: " + item.get("volumeCost"));
		displayBox.getChildren().add(a);
		a = new Text("Max Weight: " + item.get("maxWeight"));
		displayBox.getChildren().add(a);
		a = new Text("Max Volume: " + item.get("maxVolume"));
		displayBox.getChildren().add(a);
		a = new Text("Duration: " + item.get("duration"));
		displayBox.getChildren().add(a);
		a = new Text("Frequency: " + item.get("frequency"));
		displayBox.getChildren().add(a);
		a = new Text("Day: " + item.get("day"));
		displayBox.getChildren().add(a);
		a = new Text("Hour: " + item.get("hour"));
		displayBox.getChildren().add(a);
				
		displayBox.setSpacing(5);
	}
	public void displayPrice(HashMap<String, String> item) {
		logtype.setText("Customer Price");
		Text a = new Text("To: " + item.get("to"));
		displayBox.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		displayBox.getChildren().add(a);		
		a = new Text("Priority: " + item.get("priority"));
		displayBox.getChildren().add(a);
		a = new Text("Weight Cost: " + item.get("weightCost"));
		displayBox.getChildren().add(a);
		a = new Text("Volume Cost: " + item.get("volumeCost"));
		displayBox.getChildren().add(a);
						
		displayBox.setSpacing(5);

	}
	public void displayDiscontinue(HashMap<String, String> item) {
		logtype.setText("Discontinue Route");
		Text a = new Text("To: " + item.get("to"));
		displayBox.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		displayBox.getChildren().add(a);		
		a = new Text("Type: " + item.get("type"));
		displayBox.getChildren().add(a);
		a = new Text("Comapny: " + item.get("company"));
		displayBox.getChildren().add(a);
						
		displayBox.setSpacing(5);
	}
}
