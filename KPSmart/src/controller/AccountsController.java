package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;
import main.Main;

public class AccountsController implements Initializable {
	private Main main;

	public AccountsController(Main main) {
		this.main = main;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
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

	@FXML
	MenuButton accounts;

	@FXML
	private void addUserAction(ActionEvent event) throws IOException {
		FXMLLoader addUser = new FXMLLoader(getClass().getResource(
				"/views/adduser.fxml"));
		addUser.setController(new AccountsController(main));
		Parent addUserGUI = addUser.load();

		Stage stage = (Stage) accounts.getScene().getWindow();
		Scene scene = new Scene(addUserGUI);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void editUserAction(ActionEvent event)
			throws IOException {
		FXMLLoader editUser = new FXMLLoader(getClass().getResource(
				"/views/edituser.fxml"));
		editUser.setController(new AccountsController(main));
		Parent editUserGUI = editUser.load();

		Stage stage = (Stage) accounts.getScene().getWindow();
		Scene scene = new Scene(editUserGUI);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void changePassAction(ActionEvent event) throws IOException {
		FXMLLoader changePass = new FXMLLoader(getClass().getResource(
				"/views/changepass.fxml"));
		changePass.setController(new AccountsController(main));
		Parent changePassGUI = changePass.load();

		Stage stage = (Stage) accounts.getScene().getWindow();
		Scene scene = new Scene(changePassGUI);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void addUserButtonAction(ActionEvent event){
		
	}
	
	@FXML
	private void searchButtonAction(ActionEvent event){
		
	}
	
	@FXML
	private void changePassButtonAction(ActionEvent event){
		
	}
}
