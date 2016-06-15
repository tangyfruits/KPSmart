package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.LogStepper;

public class HistoryController implements Initializable {

	private Main main;
	private LogStepper stepper;
	
	@FXML
	private VBox box;
	

	public HistoryController(Main main) {
		this.main = main;
		stepper = new LogStepper(main.getFile());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void initData() {
//		HashMap<String, String> item = stepper.latestEvent();
//		switch (item.get("type")) {
//		case "mail":
//			displayMail(item);
//			break;
//		case "cost":
//			displayCost(item);
//			break;
//		case "price":
//			displayPrice(item);
//			break;
//		case "discontinue":
//			displayDiscontinue(item);
//			break;
//		default:
//
//			break;
//		}
		HashMap<String,String> map = new HashMap<>();
		map.put("type", "cost");
		map.put("to", 		  "Wellington");
		map.put("from", 	  "Auckland");
		map.put("company", 	  "UPS");
		map.put("type", 	  "Air");
		map.put("priority",   "Domestic Air");
		map.put("weightCost", "3.5");
		map.put("volumeCost", "2.8");
		map.put("maxWeight",  "15");
		map.put("maxVolume",  "15");
		map.put("duration",   "12");
		map.put("frequency",  "14");
		map.put("day", 		  "Thursday");
		map.put("hour", 	  "12");
		
		displayDiscontinue(map);
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

	private boolean firstview = true;

	@FXML
	private Button previous, next, readLog;
	@FXML
	private Text text1, text2, text3, text4, text5, text6, text7, text8, text9,
			text10, text11, text12, text13, text14, text15, text16, text17,
			text18, text19, text20, text21, text22, text23, text24, text25,
			text26, text27, text28, text29, text30, text31, text32, text33,
			text34;
	@FXML
	private Label logtype;

	@FXML
	private void readLogEvent(ActionEvent event) {
		if (firstview) {
			readLog.setVisible(false);
			firstview = false;
			// readLogEvent(event);
		}
	}

	public void displayMail(HashMap<String, String> item) {
//		logtype.setText("Transport Cost");
//		Text a = new Text("To: " + item.get("to"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("From: " + item.get("from"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);		
//		a = new Text("Comapny: " + item.get("company"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Type: " + item.get("type"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Priority: " + item.get("priority"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Weight Cost: " + item.get("weightCost"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Volume Cost: " + item.get("volumeCost"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Max Weight: " + item.get("maxWeight"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Max Volume: " + item.get("maxVolume"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Duration: " + item.get("duration"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Frequency: " + item.get("frequency"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Day: " + item.get("day"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//		a = new Text("Hour: " + item.get("hour"));
//		a.setFont(Font.font ("Gulim", 18));
//		box.getChildren().add(a);
//				
//		box.setSpacing(5);
	}

	public void displayCost(HashMap<String, String> item) {
		logtype.setText("Transport Cost");
		Text a = new Text("To: " + item.get("to"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);		
		a = new Text("Comapny: " + item.get("company"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Type: " + item.get("type"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Weight Cost: " + item.get("weightCost"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Volume Cost: " + item.get("volumeCost"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Max Weight: " + item.get("maxWeight"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Max Volume: " + item.get("maxVolume"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Duration: " + item.get("duration"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Frequency: " + item.get("frequency"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Day: " + item.get("day"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Hour: " + item.get("hour"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
				
		box.setSpacing(5);
	}

	public void displayPrice(HashMap<String, String> item) {
		logtype.setText("Customer Price");
		Text a = new Text("To: " + item.get("to"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);		
		a = new Text("Type: " + item.get("type"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Priority: " + item.get("priority"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Weight Cost: " + item.get("weightCost"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Volume Cost: " + item.get("volumeCost"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
						
		box.setSpacing(5);

	}

	public void displayDiscontinue(HashMap<String, String> item) {
		logtype.setText("Discontinue Route");
		Text a = new Text("To: " + item.get("to"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("From: " + item.get("from"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);		
		a = new Text("Type: " + item.get("type"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
		a = new Text("Comapny: " + item.get("company"));
		a.setFont(Font.font ("Gulim", 18));
		box.getChildren().add(a);
						
		box.setSpacing(5);
	}
}
