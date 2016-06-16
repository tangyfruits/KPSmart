package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.LogStepper;
import main.Main;

public class HistoryController implements Initializable {

	private Main main;
	private LogStepper stepper;
	
	private HashMap<String,String> map;
	
	@FXML
	private VBox box;
	
	public HistoryController(Main main) {
		this.main = main;
		stepper = new LogStepper(new File("KPSmart/src/tests/test_input.xml"));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void initData() {
		HashMap<String, String> item = stepper.latestEvent();
		display(item);
	}

	/**
	 * NAV BAR BUTTONS
	 * 
	 * @throws IOException
	 */
	@FXML
	private void historyButtonAction(ActionEvent event) throws IOException {

		FXMLLoader history = new FXMLLoader(getClass().getResource(
				"/views/readlog.fxml"));
		history.setController(new HistoryController(main));
		Parent historyGUI = history.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(historyGUI);
		stage.setScene(scene);
		HistoryController cont = history.getController();
      	cont.initData();
		stage.show();
	}

	@FXML
	private void reportButtonAction(ActionEvent event) throws IOException {
		FXMLLoader reports = new FXMLLoader(getClass().getResource(
				"/views/reports.fxml"));
		reports.setController(new ReportsController(main));
		Parent reportsGUI = reports.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(reportsGUI);
		stage.setScene(scene);
		ReportsController cont = reports.getController();
		cont.initData();
		stage.show();
	}

	@FXML
	private void logoutButtonAction(ActionEvent event) throws IOException {
		main.logout();
		FXMLLoader login = new FXMLLoader(getClass().getResource(
				"/views/login.fxml"));
		login.setController(new LoginController(main));
		Parent loginGUI = login.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(loginGUI);
		stage.setScene(scene);
		stage.show();
	}

	/** LOG EVENT MENU ITEM */
	@FXML
	MenuButton logeventmenu;

	@FXML
	private void deliveryRequestAction(ActionEvent event) throws IOException {
		FXMLLoader delivery = new FXMLLoader(getClass().getResource(
				"/views/deliveryrequest.fxml"));
		delivery.setController(new FormController(main));
		Parent deliveryGUI = delivery.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(deliveryGUI);

		FormController controller = delivery.getController();
		controller.initDropdown();

		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void discontinueTransportAction(ActionEvent event)
			throws IOException {
		logeventmenu.setText("Discontinue Transport");

		FXMLLoader discontinue = new FXMLLoader(getClass().getResource(
				"/views/discontinuetransport.fxml"));
		discontinue.setController(new FormController(main));
		Parent discontinueGUI = discontinue.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(discontinueGUI);
		stage.setScene(scene);
		FormController controller = discontinue.getController();
		controller.initDropdown();
		stage.show();
	}

	@FXML
	private void transportRouteAction(ActionEvent event) throws IOException {
		logeventmenu.setText("Transport Route");

		FXMLLoader route = new FXMLLoader(getClass().getResource(
				"/views/transportroute.fxml"));
		route.setController(new FormController(main));
		Parent routeGUI = route.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(routeGUI);
		stage.setScene(scene);
		FormController controller = route.getController();
		controller.initDropdownWithOther();
		controller.timeMenu();
		stage.show();
	}

	@FXML
	private void priceUpdateAction(ActionEvent event) throws IOException {
		logeventmenu.setText("Customer Price Update");

		FXMLLoader price = new FXMLLoader(getClass().getResource(
				"/views/priceupdate.fxml"));
		price.setController(new FormController(main));
		Parent priceGUI = price.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(priceGUI);
		stage.setScene(scene);
		FormController controller = price.getController();
		controller.initDropdownWithOther();
		stage.show();
	}

	/*
	 * Elements
	 */
	
	@FXML
	private Button previous, next, first, last;
	
	private BooleanProperty hasNext = new SimpleBooleanProperty(false);
	private BooleanProperty hasPrev = new SimpleBooleanProperty(false);

	
	@FXML
	private Label logtype;
	
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
	
	/*
	 * Display of different event types
	 */
	

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
		box.getChildren().clear();
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
		box.getChildren().add(a);	
		a = new Text("Destination: " + item.get("destination"));
		box.getChildren().add(a);
		a = new Text("Logged: " + item.get("logged"));
		box.getChildren().add(a);
		a = new Text("Weight: " + item.get("weight"));
		box.getChildren().add(a);
		a = new Text("Volume: " + item.get("volume"));
		box.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		box.getChildren().add(a);
		a = new Text("Duration: " + item.get("duration"));
		box.getChildren().add(a);
		a = new Text(" ");
		box.getChildren().add(a);
		
		a = new Text("Legs:");
		box.getChildren().add(a);
		
		for(int i =0; i<Integer.parseInt(item.get("legnum")); i++){
			int leg = i;
			a = new Text("	To: " + item.get("leg " +leg +" to"));
			box.getChildren().add(a);
			a = new Text("	From: " + item.get("leg " +leg +" from"));
			box.getChildren().add(a);
			a = new Text("	Type: " + item.get("leg " +leg +" type"));
			box.getChildren().add(a);
			a = new Text("	Company: " + item.get("leg " +leg +" company"));
			box.getChildren().add(a);
			a = new Text("	Cost: " + item.get("leg " +leg +" cost"));
			box.getChildren().add(a);
			a = new Text("	Price: " + item.get("leg " +leg +" price"));
			box.getChildren().add(a);
			if (Integer.parseInt(item.get("legnum"))-1!=leg) {
				a = new Text("	- - - - - -  ");
				box.getChildren().add(a);
			}
		}
		
		
				
		box.setSpacing(5);
	}

	public void displayCost(HashMap<String, String> item) {
		logtype.setText("Transport Cost");
		Text a = new Text("To: " + item.get("to"));
		box.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		box.getChildren().add(a);		
		a = new Text("Comapny: " + item.get("company"));
		box.getChildren().add(a);
		a = new Text("Type: " + item.get("type"));
		box.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		box.getChildren().add(a);
		a = new Text("Weight Cost: " + item.get("weightCost"));
		box.getChildren().add(a);
		a = new Text("Volume Cost: " + item.get("volumeCost"));
		box.getChildren().add(a);
		a = new Text("Max Weight: " + item.get("maxWeight"));
		box.getChildren().add(a);
		a = new Text("Max Volume: " + item.get("maxVolume"));
		box.getChildren().add(a);
		a = new Text("Duration: " + item.get("duration"));
		box.getChildren().add(a);
		a = new Text("Frequency: " + item.get("frequency"));
		box.getChildren().add(a);
		a = new Text("Day: " + item.get("day"));
		box.getChildren().add(a);
		a = new Text("Hour: " + item.get("hour"));
		box.getChildren().add(a);
				
		box.setSpacing(5);
	}

	public void displayPrice(HashMap<String, String> item) {
		logtype.setText("Customer Price");
		Text a = new Text("To: " + item.get("to"));
		box.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		box.getChildren().add(a);		
		a = new Text("Type: " + item.get("type"));
		box.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		box.getChildren().add(a);
		a = new Text("Weight Cost: " + item.get("weightCost"));
		box.getChildren().add(a);
		a = new Text("Volume Cost: " + item.get("volumeCost"));
		box.getChildren().add(a);
						
		box.setSpacing(5);

	}

	public void displayDiscontinue(HashMap<String, String> item) {
		logtype.setText("Discontinue Route");
		Text a = new Text("To: " + item.get("to"));
		box.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		box.getChildren().add(a);		
		a = new Text("Type: " + item.get("type"));
		box.getChildren().add(a);
		a = new Text("Comapny: " + item.get("company"));
		box.getChildren().add(a);
						
		box.setSpacing(5);
	}
}
