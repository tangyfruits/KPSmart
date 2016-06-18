package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
	@FXML 
	private Button history;
	
	private BooleanProperty isManager = new SimpleBooleanProperty(false);

	@FXML
	private MenuButton accounts;
	
	public HistoryController(Main main) {
		this.main = main;
		stepper = new LogStepper(main.getLogFile());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		isManager.set(main.getCurrentUser().isManager());
		history.visibleProperty().bind(isManager);
		
		MenuItem changePassword = new MenuItem("Change Password");
		changePassword.setOnAction(changePassAction());
		accounts.getItems().add(changePassword);

		if (main.getCurrentUser().isManager()) {
			MenuItem addUser = new MenuItem("Add User");
			addUser.setOnAction(addUserAction());
			accounts.getItems().add(addUser);
			MenuItem editUser = new MenuItem("Edit User");
			editUser.setOnAction(editUserAction());
			accounts.getItems().add(editUser);
		}
	}

	public void initData() {
		HashMap<String, String> item = stepper.latestEvent();
		display(item);
	}
	    
    /** NAV BAR BUTTONS 
     * @throws IOException */
    
    @FXML
    private void historyButtonAction(ActionEvent event) throws IOException{
   	
    	FXMLLoader history = new FXMLLoader(getClass().getResource("/views/readlog.fxml"));
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
    private void reportButtonAction(ActionEvent event) throws IOException{
    	FXMLLoader reports = new FXMLLoader(getClass().getResource("/views/reports.fxml"));
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
    private void logoutButtonAction(ActionEvent event) throws IOException{
    	main.logout();
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/views/login.fxml"));
    	login.setController(new LoginController(main));
    	Parent loginGUI = login.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(loginGUI);
      	stage.setScene(scene);
    	stage.show();
    }
	/** ACCOUNTS MENU ITEM */

	@FXML
		private EventHandler<ActionEvent> addUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader addUser = new FXMLLoader(getClass().getResource(
						"/views/adduser.fxml"));
				addUser.setController(new AccountsController(main));
				Parent addUserGUI = null;
				try {
					addUserGUI = addUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(addUserGUI);
				stage.setScene(scene);
				stage.show();
			}
		};
	}
	
	private EventHandler<ActionEvent> editUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader editUser = new FXMLLoader(getClass().getResource(
						"/views/edituser.fxml"));
				editUser.setController(new AccountsController(main));
				Parent editUserGUI = null;
				try {
					editUserGUI = editUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(editUserGUI);
				stage.setScene(scene);
				stage.show();
			}
		};
	}
	
	private EventHandler<ActionEvent> changePassAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader changePass = new FXMLLoader(getClass().getResource(
						"/views/changepass.fxml"));
				changePass.setController(new AccountsController(main));
				Parent changePassGUI = null;
				try {
					changePassGUI = changePass.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(changePassGUI);
				stage.setScene(scene);
				stage.show();
			}
		};
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
		controller.initReq();
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
		controller.initRoute();
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
		controller.initPrice();
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
