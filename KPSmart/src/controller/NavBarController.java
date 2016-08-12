package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import main.Main;

public abstract class NavBarController implements Initializable {

	protected Main main;

	// FIELDS
	protected BooleanProperty isManager = new SimpleBooleanProperty(false);
	@FXML
	protected MenuButton accounts;
	@FXML
	protected MenuButton logeventmenu;
	@FXML
	protected Button history;

	// Set Permissions for managers/clerks
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

			MenuItem deleteUser = new MenuItem("Delete User");
			deleteUser.setOnAction(deleteUserAction());
			accounts.getItems().add(deleteUser);
		}
	}

	// METHODS
	// BUTTON HANDLERS
	@FXML
	protected void historyButtonAction(ActionEvent event) throws IOException {

		FXMLLoader history = new FXMLLoader(getClass().getResource("/views/readlog.fxml"));
		history.setController(new HistoryController(main));
		Parent historyGUI = history.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(historyGUI, 720, 620);
		stage.setScene(scene);
		HistoryController cont = history.getController();
		cont.initData();
		stage.show();
	}

	@FXML
	protected void reportButtonAction(ActionEvent event) throws IOException {

		FXMLLoader reports = new FXMLLoader(getClass().getResource("/views/reports.fxml"));

		// NavBarController navbarController = new NavBarController();
		ReportsController reportsController = new ReportsController(main);
		// reportsController.navbar

		reports.setController(reportsController);

		Parent reportsGUI = reports.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(reportsGUI, 720, 620);

		stage.setScene(scene);

		ReportsController cont = reports.getController();
		cont.initData();
		stage.show();
	}

	@FXML
	protected void logoutButtonAction(ActionEvent event) throws IOException {
		main.logout();
		FXMLLoader login = new FXMLLoader(getClass().getResource("/views/login.fxml"));
		login.setController(new LoginController(main));
		Parent loginGUI = login.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(loginGUI, 720, 620);
		stage.setScene(scene);
		stage.show();
	}

	// LOG EVENT HANDLERS
	// LOG EVENTSS
	@FXML
	protected void deliveryRequestAction(ActionEvent event) throws IOException {
		FXMLLoader delivery = new FXMLLoader(getClass().getResource("/views/deliveryrequest.fxml"));
		delivery.setController(new FormController(main));
		Parent deliveryGUI = delivery.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(deliveryGUI, 720, 620);

		FormController controller = delivery.getController();
		controller.initDropdown();
		controller.initReq();

		stage.setScene(scene);
		stage.show();
	}

	@FXML
	protected void discontinueTransportAction(ActionEvent event) throws IOException {
		logeventmenu.setText("Discontinue Transport");

		FXMLLoader discontinue = new FXMLLoader(getClass().getResource("/views/discontinuetransport.fxml"));
		discontinue.setController(new FormController(main));
		Parent discontinueGUI = discontinue.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(discontinueGUI, 720, 620);
		stage.setScene(scene);
		FormController controller = discontinue.getController();
		controller.initDropdown();
		stage.show();
	}

	@FXML
	protected void transportRouteAction(ActionEvent event) throws IOException {
		logeventmenu.setText("Transport Route");

		FXMLLoader route = new FXMLLoader(getClass().getResource("/views/transportroute.fxml"));
		route.setController(new FormController(main));
		Parent routeGUI = route.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(routeGUI, 720, 620);
		stage.setScene(scene);
		FormController controller = route.getController();
		controller.initDropdownWithOther();
		controller.timeMenu();
		controller.initRoute();
		stage.show();
	}

	@FXML
	protected void priceUpdateAction(ActionEvent event) throws IOException {
		logeventmenu.setText("Customer Price Update");

		FXMLLoader price = new FXMLLoader(getClass().getResource("/views/priceupdate.fxml"));
		price.setController(new FormController(main));
		Parent priceGUI = price.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(priceGUI, 720, 620);
		stage.setScene(scene);
		FormController controller = price.getController();
		controller.initDropdownWithOther();
		controller.initPrice();
		stage.show();
	}

	// ACCOUNTS EVENT HANDLERS
	/** ACCOUNTS MENU ITEM */
	protected EventHandler<ActionEvent> addUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader addUser = new FXMLLoader(getClass().getResource("/views/adduser.fxml"));
				addUser.setController(new AccountsController(main));
				Parent addUserGUI = null;
				try {
					addUserGUI = addUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(addUserGUI, 720, 620);
				stage.setScene(scene);
				stage.show();
			}
		};
	}

	protected EventHandler<ActionEvent> editUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader editUser = new FXMLLoader(getClass().getResource("/views/edituser.fxml"));
				editUser.setController(new AccountsController(main));
				Parent editUserGUI = null;
				try {
					editUserGUI = editUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(editUserGUI, 720, 620);
				stage.setScene(scene);
				stage.show();
			}
		};
	}

	protected EventHandler<ActionEvent> changePassAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader changePass = new FXMLLoader(getClass().getResource("/views/changepass.fxml"));
				changePass.setController(new AccountsController(main));
				Parent changePassGUI;
				try {
					changePassGUI = changePass.load();
					Stage stage = (Stage) accounts.getScene().getWindow();
					Scene scene = new Scene(changePassGUI, 720, 620);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

	protected EventHandler<ActionEvent> deleteUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader deleteUser = new FXMLLoader(getClass().getResource("/views/deleteUser.fxml"));
				deleteUser.setController(new AccountsController(main));
				Parent deleteUserGUI = null;
				try {
					deleteUserGUI = deleteUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(deleteUserGUI, 720, 620);
				stage.setScene(scene);
				stage.show();
			}
		};
	}

	@FXML
	private void changePass(ActionEvent event) throws IOException {
		FXMLLoader changePass = new FXMLLoader(getClass().getResource("/views/changepass.fxml"));
		changePass.setController(new AccountsController(main));
		Parent changePassGUI;
		try {
			changePassGUI = changePass.load();
			Stage stage = (Stage) accounts.getScene().getWindow();
			Scene scene = new Scene(changePassGUI, 720, 620);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
