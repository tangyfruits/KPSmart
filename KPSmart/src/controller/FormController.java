package controller;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.ResourceBundle;

import event.CustomerPrice;
import event.DeliveryRequest;
import event.DiscontinueRoute;
import event.Route;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Location;
import main.Main;
import main.RouteDisplay;

public class FormController implements Initializable {

	private Main main;
	
	@FXML 
	private Button history;
	
	private BooleanProperty isManager = new SimpleBooleanProperty(false);

	@FXML
	private MenuButton accounts;

	public FormController(Main main) {
		this.main = main;
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

	private ArrayList<Location> locs;
	@FXML
	private MenuButton originMenu;
	@FXML
	private MenuButton destinationMenu;

	private String selectedOrigin = "";
	private String selectedDest = "";

	@FXML
	private TextField otherOrigin;
	@FXML
	private TextField otherDest;

	private BooleanProperty hasOtherOrigin = new SimpleBooleanProperty(false);
	private BooleanProperty hasOtherDest = new SimpleBooleanProperty(false);

	public void initDropdown() {
		locs = main.getLocations();
		EventHandler<ActionEvent> originAction = setSelectedOrigin();
		EventHandler<ActionEvent> destAction = setSelectedDest();

		for (Location l : locs) {
			MenuItem i = new MenuItem(l.getName());
			i.setUserData(l.getName());
			i.setOnAction(originAction);
			originMenu.getItems().add(i);
			MenuItem k = new MenuItem(l.getName());
			k.setOnAction(destAction);
			destinationMenu.getItems().add(k);
		}
	}

	public void initDropdownWithOther() {
		otherOrigin.visibleProperty().bind(hasOtherOrigin);
		otherDest.visibleProperty().bind(hasOtherDest);
		locs = main.getLocations();
		EventHandler<ActionEvent> originAction = setSelectedOrigin();
		EventHandler<ActionEvent> destAction = setSelectedDest();
		EventHandler<ActionEvent> otherOriginAction = setOtherOrigin();
		EventHandler<ActionEvent> otherDestAction = setOtherDest();

		for (Location l : locs) {
			MenuItem i = new MenuItem(l.getName());
			i.setUserData(l.getName());
			i.setOnAction(originAction);
			originMenu.getItems().add(i);
			MenuItem k = new MenuItem(l.getName());
			k.setOnAction(destAction);
			destinationMenu.getItems().add(k);
		}

		MenuItem otherOrigin = new MenuItem("Other");
		otherOrigin.setUserData("Other");
		otherOrigin.setOnAction(otherOriginAction);
		originMenu.getItems().add(otherOrigin);

		MenuItem otherDest = new MenuItem("Other");
		otherDest.setUserData("Other");
		otherDest.setOnAction(otherDestAction);
		destinationMenu.getItems().add(otherDest);
	}

	private EventHandler<ActionEvent> setSelectedOrigin() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				String loc = mItem.getText();
				selectedOrigin = loc;
				originMenu.setText(loc);
				hasOtherOrigin.set(false);
			}
		};
	}

	private EventHandler<ActionEvent> setOtherDest() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				String loc = mItem.getText();
				destinationMenu.setText(loc);
				selectedDest = loc;
				hasOtherDest.set(true);
			}
		};
	}

	private EventHandler<ActionEvent> setOtherOrigin() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				String loc = mItem.getText();
				originMenu.setText(loc);
				hasOtherOrigin.set(true);
				selectedOrigin = loc;
			}
		};
	}

	private EventHandler<ActionEvent> setSelectedDest() {

		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				String loc = mItem.getText();
				selectedDest = loc;
				destinationMenu.setText(loc);
				hasOtherDest.set(false);
			}
		};
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
	
	/** ACCOUNTS MENU ITEM */

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

	/** Priority Menu */

	@FXML
	private CheckMenuItem airPriority;
	@FXML
	private CheckMenuItem stdPriority;
	@FXML
	private MenuButton prioritymenu;

	private String priority = "";

	@FXML
	private void stdPriorityAction(ActionEvent event) {
		priority = "Standard";
		prioritymenu.setText("Standard");
	}

	@FXML
	private void airPriorityAction(ActionEvent event) {
		priority = "Air";
		prioritymenu.setText("Air");
	}

	/** Type Menu */

	@FXML
	private CheckMenuItem airType;
	@FXML
	private CheckMenuItem landType;
	@FXML
	private CheckMenuItem seaType;
	@FXML
	private MenuButton typemenu;

	private String type = "";

	@FXML
	private void airTypeAction(ActionEvent event) {
		type = "Air";
		typemenu.setText("Air");
	}

	@FXML
	private void landTypeAction(ActionEvent event) {
		type = "Land";
		typemenu.setText("Land");
	}

	@FXML
	private void seaTypeAction(ActionEvent event) {
		type = "Sea";
		typemenu.setText("Sea");
	}

	/** Text fields */
	@FXML
	private TextField company;
	@FXML
	private TextField maxweight;
	@FXML
	private TextField maxvolume;
	@FXML
	private TextField weightcost;
	@FXML
	private TextField volumecost;
	@FXML
	private TextField duration;
	@FXML
	private TextField frequency;

	/** Day Menu */

	@FXML
	private CheckMenuItem monday;
	@FXML
	private CheckMenuItem tuesday;
	@FXML
	private CheckMenuItem wednesday;
	@FXML
	private CheckMenuItem thursday;
	@FXML
	private CheckMenuItem friday;
	@FXML
	private MenuButton dayMenu;

	private String day = "";

	@FXML
	private void mondayAction(ActionEvent event) {
		day = "MONDAY";
		dayMenu.setText("Monday");
	}

	@FXML
	private void tuesdayAction(ActionEvent event) {
		day = "TUESDAY";
		dayMenu.setText("Tuesday");
	}

	@FXML
	private void wednesdayAction(ActionEvent event) {
		day = "WEDNESDAY";
		dayMenu.setText("Wednesday");
	}

	@FXML
	private void thursdayAction(ActionEvent event) {
		day = "THURSDAY";
		dayMenu.setText("Thursday");
	}

	@FXML
	private void fridayAction(ActionEvent event) {
		day = "FRIDAY";
		dayMenu.setText("Friday");
	}

	/** Time Menu */
	
	@FXML
	private MenuButton timeMenu;

	private int time = -1;

	public void timeMenu() {
		EventHandler<ActionEvent> timeAction = setTime();

		MenuItem a = new MenuItem("12am");
		a.setUserData(0);
		a.setOnAction(timeAction);
		timeMenu.getItems().add(a);

		for (int i = 1; i < 12; i++) {
			MenuItem m = new MenuItem(i + "am");
			m.setUserData(i);
			m.setOnAction(timeAction);
			timeMenu.getItems().add(m);
		}

		MenuItem b = new MenuItem("12pm");
		b.setUserData(12);
		b.setOnAction(timeAction);
		timeMenu.getItems().add(b);

		for (int i = 1; i < 12; i++) {
			MenuItem m = new MenuItem(i + "pm");
			m.setUserData(i + 12);
			m.setOnAction(timeAction);
			timeMenu.getItems().add(m);
		}
	}
	
	private EventHandler<ActionEvent> setTime() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				String t = mItem.getText();
				time = (int) mItem.getUserData();
				timeMenu.setText(t);
				System.out.println(mItem.getUserData().toString());
			}
		};
	}

	@FXML
	private Button submit;

	private BooleanProperty completed = new SimpleBooleanProperty(false);

	@FXML
	private Text confirmation;

	@FXML
	private Text error;

	private BooleanProperty hasError = new SimpleBooleanProperty(false);

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/** TRANSPORT ROUTE FORM */

	private void validateRoute(String mw, String mv, String wc, String vc,
			String d, String f) {
		if (selectedOrigin.isEmpty() || selectedOrigin.equals("Other")
				|| selectedDest.isEmpty() || selectedDest.equals("Other")
				|| type.isEmpty() || company.getText().isEmpty()
				|| mw.isEmpty() || !isDouble(mw) || mv.isEmpty()
				|| !isDouble(mv) || wc.isEmpty() || !isDouble(wc)
				|| vc.isEmpty() || !isDouble(vc) || d.isEmpty() || !isDouble(d)
				|| f.isEmpty() || !isDouble(f) || day.isEmpty() || time == -1) {

			if (selectedOrigin.isEmpty() && !originMenu.getText().equals("Other")) {
				hasError.set(true);
				originMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				originMenu.setStyle("-fx-background-color:  E9D5B9");
			}
			if (selectedOrigin.isEmpty() && originMenu.getText().equals("Other")) {
				hasError.set(true);
				otherOrigin.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				otherOrigin.setStyle("-fx-control-inner-background: white");
			}
			if (selectedDest.isEmpty() && !destinationMenu.getText().equals("Other")) {
				hasError.set(true);
				destinationMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				destinationMenu.setStyle("-fx-background-color: E9D5B9");

			}
			if (selectedDest.isEmpty() && destinationMenu.getText().equals("Other")) {
				hasError.set(true);
				otherDest.setStyle("-fx-control-inner-background: #ffff99");

			} else{
				otherDest.setStyle("-fx-control-inner-background: white");

			}
			if(type.isEmpty()){
				typemenu.setStyle("-fx-background-color: #ffff99");
			}else{
				typemenu.setStyle("-fx-background-color: E9D5B9");
			}
			if(company.getText().isEmpty()){
				company.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				company.setStyle("-fx-control-inner-background: white");

			}
			if (mw.isEmpty() || !isDouble(mw)) {
				hasError.set(true);
				maxweight.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				maxweight.setStyle("-fx-control-inner-background: white");

			}
			if (mv.isEmpty() || !isDouble(mv)) {
				hasError.set(true);
				 maxvolume.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 maxvolume.setStyle("-fx-control-inner-background: white");

			}
			if (wc.isEmpty() || !isDouble(wc)) {
				hasError.set(true);
				weightcost.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				weightcost.setStyle("-fx-control-inner-background: white");

			}
			if (vc.isEmpty() || !isDouble(vc)) {
				hasError.set(true);
				 volumecost.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 volumecost.setStyle("-fx-control-inner-background: white");

			}
			if (f.isEmpty() || !isDouble(f)) {
				hasError.set(true);
				frequency.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				frequency.setStyle("-fx-control-inner-background: white");

			}
			if (d.isEmpty() || !isDouble(d)) {
				hasError.set(true);
				 duration.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 duration.setStyle("-fx-control-inner-background: white");

			}	
			if(day.isEmpty()){
				hasError.set(true);
				dayMenu.setStyle("-fx-background-color: #ffff99");
			}else{
				dayMenu.setStyle("-fx-background-color: E9D5B9");
			}
			if(time == -1){
				hasError.set(true);
				timeMenu.setStyle("-fx-background-color: #ffff99");
			}else{
				timeMenu.setStyle("-fx-background-color: E9D5B9");
			}
		} else {
			hasError.set(false);
		}
	}

	private Stage modal;
	@FXML
	private void transportRouteButtonAction(ActionEvent event) throws IOException {
		error.visibleProperty().bind(hasError);

		String mw = this.maxweight.getText();
		String mv = this.maxvolume.getText();
		String wc = this.weightcost.getText();
		String vc = this.volumecost.getText();
		String d = this.duration.getText();
		String f = this.frequency.getText();

		if (selectedOrigin.equals("Other") || selectedOrigin.isEmpty()) {
			selectedOrigin = this.otherOrigin.getText();
		}

		if (selectedDest.equals("Other") || selectedDest.isEmpty()) {
			selectedDest = otherDest.getText();
		}

		validateRoute(mw, mv, wc, vc, d, f);

		if (!hasError.get()) {
			Route r = main.logTransportCostUpdate(selectedOrigin, selectedDest,
					company.getText(), type, Double.parseDouble(wc),
					Double.parseDouble(vc), Integer.parseInt(mw),
					Integer.parseInt(mv), Integer.parseInt(d),
					Integer.parseInt(f), DayOfWeek.valueOf(day), time, false);
			if (r.getPrice() == null) {

				FXMLLoader popup = new FXMLLoader(getClass().getResource(
						"/views/popup.fxml"));
				popup.setController(new PopUpController(main, selectedOrigin,
						selectedDest, priority, r, this));
				Parent popupGUI = popup.load();
				modal = new Stage();
				modal.setScene(new Scene(popupGUI));
				modal.setTitle("Enter customer price");
				modal.initModality(Modality.APPLICATION_MODAL);
				modal.initOwner(submit.getScene().getWindow());
				modal.showAndWait();
			}
			if (r != null) {
				confirmation.visibleProperty().bind(completed);
				completed.set(true);
				originMenu.setDisable(true);
				destinationMenu.setDisable(true);
				weightcost.setDisable(true);
				volumecost.setDisable(true);
				maxweight.setDisable(true);
				maxvolume.setDisable(true);
				duration.setDisable(true);
				frequency.setDisable(true);
				typemenu.setDisable(true);
				dayMenu.setDisable(true);
				timeMenu.setDisable(true);
				submit.setDisable(true);
				company.setDisable(true);
				otherDest.setDisable(true);
				otherOrigin.setDisable(true);
				hasError.set(false);
			}
		}
	}
	
	public void closeModal(){
		modal.close();
	}

	/** PRICE UPDATE FORM */

	public void validatePrice(String wc, String vc) {
		if (selectedOrigin.isEmpty() || selectedOrigin.equals("Other")
				|| selectedDest.isEmpty() || selectedDest.equals("Other")
				|| wc.isEmpty() || !isDouble(wc) || vc.isEmpty()
				|| !isDouble(vc) || priority.isEmpty()) {
			
			if (selectedOrigin.isEmpty() && !originMenu.getText().equals("Other")) {
				hasError.set(true);
				originMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				originMenu.setStyle("-fx-background-color:  E9D5B9");
			}
			if (selectedOrigin.isEmpty() && originMenu.getText().equals("Other")) {
				hasError.set(true);
				otherOrigin.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				otherOrigin.setStyle("-fx-control-inner-background: white");
			}
			if (selectedDest.isEmpty() && !destinationMenu.getText().equals("Other")) {
				hasError.set(true);
				destinationMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				destinationMenu.setStyle("-fx-background-color: E9D5B9");

			}
			if (selectedDest.isEmpty() && destinationMenu.getText().equals("Other")) {
				hasError.set(true);
				otherDest.setStyle("-fx-control-inner-background: #ffff99");

			} else{
				otherDest.setStyle("-fx-control-inner-background: white");

			}
			if (wc.isEmpty() || !isDouble(wc)) {
				hasError.set(true);
				weightcost.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				weightcost.setStyle("-fx-control-inner-background: white");

			}
			if (vc.isEmpty() || !isDouble(vc)) {
				hasError.set(true);
				 volumecost.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 volumecost.setStyle("-fx-control-inner-background: white");

			}
			if (priority.isEmpty()) {
				hasError.set(true);
				prioritymenu.setStyle("-fx-background-color:  #ffff99");

			} else {
				prioritymenu.setStyle("-fx-background-color:  E9D5B9");

			}				
		} else {
			hasError.set(false);
		}
	}

	@FXML
	private void priceUpdateButtonAction(ActionEvent event) {
		error.visibleProperty().bind(hasError);

		String wc = this.weightcost.getText();
		String vc = this.volumecost.getText();

		if (selectedOrigin.equals("Other") || selectedOrigin.isEmpty()) {
			selectedOrigin = this.otherOrigin.getText();

		}
		if (selectedDest.equals("Other") || selectedDest.isEmpty()) {
			selectedDest = otherDest.getText();
		}

		validatePrice(wc, vc);

		if (!hasError.get()) {
			CustomerPrice price = main.logCustomerPriceUpdate(selectedOrigin,
					selectedDest, priority, Double.parseDouble(wc),
					Double.parseDouble(vc), false);
			if (price != null) {
				confirmation.visibleProperty().bind(completed);
				completed.set(true);
				originMenu.setDisable(true);
				destinationMenu.setDisable(true);
				weightcost.setDisable(true);
				volumecost.setDisable(true);
				prioritymenu.setDisable(true);
				submit.setDisable(true);
				otherDest.setDisable(true);
				otherOrigin.setDisable(true);
				hasError.set(false);
			}
		}
	}

	/** DELIVERY REQUEST FORM */

	@FXML
	private Button reselect;

	@FXML
	private Button findPriorities;

	@FXML
	private RadioButton firstChoice;
	@FXML
	private RadioButton secondChoice;

	private BooleanProperty hasPriorities = new SimpleBooleanProperty(false);

	private RouteDisplay chosenPriority = null;

	private ArrayList<RouteDisplay> routes;

	@FXML
	private TextField weight;
	@FXML
	private TextField volume;

	@FXML
	private Text noRoutes;

	private BooleanProperty routeless = new SimpleBooleanProperty(false);

	private void validateDelivery(String w, String v) {
		if (selectedOrigin.isEmpty() || selectedDest.isEmpty()
				|| w.isEmpty() || !isDouble(w) || v.isEmpty() || !isDouble(v)) {
			if (selectedOrigin.isEmpty()) {
				hasError.set(true);
				originMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				originMenu.setStyle("-fx-background-color:  E9D5B9");
			}
			if (selectedDest.isEmpty()) {
				hasError.set(true);
				destinationMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				destinationMenu.setStyle("-fx-background-color: E9D5B9");
			}
			if (w.isEmpty() || !isDouble(w)) {
				hasError.set(true);
				 weight.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 weight.setStyle("-fx-control-inner-background: white");
			}if (v.isEmpty() || !isDouble(v)) {
				hasError.set(true);
				 volume.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 volume.setStyle("-fx-control-inner-background: white");
			}
		} else {
			hasError.set(false);
		}
	}
	

	@FXML
	private void findPrioritiesButtonAction(ActionEvent event) {
		error.visibleProperty().bind(hasError);

		String w = this.weight.getText();
		String v = this.volume.getText();
		firstChoice.setSelected(false);
		secondChoice.setSelected(false);

		validateDelivery(w, v);

		if (!hasError.get()) {
			routes = main.getPossibleRoutes(selectedOrigin, selectedDest,
					Double.parseDouble(w), Double.parseDouble(v));
			if (routes.size() > 0) {
				submit.visibleProperty().bind(hasPriorities);
				reselect.visibleProperty().bind(hasPriorities);
				firstChoice.setText(routes.get(0).getPriority() + ": $"
						+ routes.get(0).getPrice());
				firstChoice.visibleProperty().bind(hasPriorities);
				if (routes.size() == 2) {
					secondChoice.setText(routes.get(1).getPriority() + ": $"
							+ routes.get(1).getPrice());
					secondChoice.visibleProperty().bind(hasPriorities);
				}
				hasPriorities.set(true);
			} else {
				noRoutes.visibleProperty().bind(routeless);
				reselect.visibleProperty().bind(routeless);
				routeless.set(true);
			}
			originMenu.setDisable(true);
			destinationMenu.setDisable(true);
			weight.setDisable(true);
			volume.setDisable(true);
			findPriorities.setDisable(true);
		}
	}

	@FXML
	private void deliveryRequestButtonAction(ActionEvent event) {
		if (!(chosenPriority == null)) {
			DeliveryRequest req = main.getDeliveryDetails(selectedOrigin,
					selectedDest, Double.parseDouble(this.weight.getText()),
					Double.parseDouble(this.volume.getText()), chosenPriority);

			chosenPriority = null;
			routes = null;
			confirmation.visibleProperty().bind(completed);

			if (req != null) {
				completed.set(true);
				reselect.setDisable(true);
				firstChoice.setDisable(true);
				secondChoice.setDisable(true);
				submit.setDisable(true);
			}
		}
	}

	@FXML
	private void reselectButtonAction(ActionEvent event) {
		originMenu.setDisable(false);
		destinationMenu.setDisable(false);
		weight.setDisable(false);
		volume.setDisable(false);
		findPriorities.setDisable(false);
		routeless.set(false);
		hasPriorities.set(false);
	}

	@FXML
	private void firstChoiceAction(ActionEvent event) {
		chosenPriority = routes.get(0);
	}

	@FXML
	private void secondChoiceAction(ActionEvent event) {
		chosenPriority = routes.get(1);
	}

	/** DISCONTINUE TRANSPORT */

	@FXML
	private Button changeSelection;

	@FXML
	private Button findroutes;

	@FXML
	private ScrollPane scrollpane;

	private BooleanProperty hasRoutes = new SimpleBooleanProperty(false);

	@FXML
	private VBox discRoutes;

	private ArrayList<Route> disconRoutes;

	private void validateDiscontinue() {
		if (selectedOrigin.isEmpty() || selectedDest.isEmpty()) {
			
			if (selectedOrigin.isEmpty()) {
				hasError.set(true);
				originMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				originMenu.setStyle("-fx-background-color:  E9D5B9");
			}
			if (selectedDest.isEmpty()) {
				hasError.set(true);
				destinationMenu.setStyle("-fx-background-color: #ffff99");
			} else{
				destinationMenu.setStyle("-fx-background-color: E9D5B9");

			}
		} else {
			hasError.set(false);
		}
	}

	private Route selectedRoute;

	@FXML
	private void findRoutesButtonAction(ActionEvent event) {
		error.visibleProperty().bind(hasError);

		validateDiscontinue();
		if (!hasError.get()) {
			disconRoutes = main.getRoutes(selectedOrigin, selectedDest);
			final ToggleGroup group = new ToggleGroup();
			group.selectedToggleProperty().addListener(
					new ChangeListener<Toggle>() {
						@Override
						public void changed(
								ObservableValue<? extends Toggle> ov, Toggle t,
								Toggle t1) {
							selectedRoute = (Route) t1.getUserData();
						}
					});
			for (Route r : disconRoutes) {
				RadioButton b = new RadioButton(r.getCompany() + ": "
						+ r.getPriority());
				b.setUserData(r);
				b.setToggleGroup(group);
				discRoutes.getChildren().add(b);
			}
			discRoutes.setSpacing(5);

			changeSelection.visibleProperty().bind(hasRoutes);
			discRoutes.visibleProperty().bind(hasRoutes);
			submit.visibleProperty().bind(hasRoutes);
			scrollpane.visibleProperty().bind(hasRoutes);
			hasRoutes.set(true);

			originMenu.setDisable(true);
			destinationMenu.setDisable(true);
			findroutes.setDisable(true);
		}
	}

	@FXML
	private void reSelect(ActionEvent event) {
		originMenu.setDisable(false);
		destinationMenu.setDisable(false);
		findroutes.setDisable(false);
		hasRoutes.set(false);
		discRoutes.getChildren().clear();
	}

	@FXML
	private void discTransportButtonAction(ActionEvent event) {
		if (selectedRoute != null) {
			DiscontinueRoute route = main.discontinueTransportRoute(
					selectedOrigin, selectedDest, selectedRoute.getCompany(),
					selectedRoute.getType(), false);
			if (route != null) {
				changeSelection.setDisable(true);
				for (Node r : discRoutes.getChildren()) {
					r.setDisable(true);
				}
				submit.setDisable(true);
				confirmation.visibleProperty().bind(completed);
				completed.set(true);
			}
		}

	}

}