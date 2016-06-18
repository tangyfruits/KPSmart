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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.User;

public class AccountsController implements Initializable {
	private Main main;

	@FXML
	private MenuButton accounts;

	@FXML
	private Button history;

	private BooleanProperty isManager = new SimpleBooleanProperty(false);

	public AccountsController(Main main) {
		this.main = main;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	@FXML
	private TextField pass1,pass2;
	private BooleanProperty hasError = new SimpleBooleanProperty(false);
	private BooleanProperty nonMatch = new SimpleBooleanProperty(false);
	
	@FXML
	private Text error, match, confirmation, fail;
	@FXML
	private Button changePassButton;
	
	private void validatePassword(){
		hasError.set(false);
		nonMatch.set(false);

		if (pass1.getText().isEmpty()) {
			hasError.set(true);
			pass1.setStyle("-fx-background-color: #ffff99");
		} else {
			pass1.setStyle("-fx-background-color:  white");
		}
		if (pass2.getText().isEmpty()) {
			hasError.set(true);
			pass2.setStyle("-fx-control-inner-background: #ffff99");
		} else {
			pass2.setStyle("-fx-control-inner-background: white");
		}
		
		if (!hasError.get()) {
			if (!pass1.getText().equals(pass2.getText())) {
				nonMatch.set(true);
			}
		}		
	}
	
	@FXML
	private void changePassButtonAction(ActionEvent event){
		error.visibleProperty().bind(hasError);
		match.visibleProperty().bind(nonMatch);
		validatePassword();
		
		if (!hasError.get() && !nonMatch.get()) {
			BooleanProperty changed = new SimpleBooleanProperty(
					main.editPassword(pass1.getText()));
			if (changed.get() == true) {
				confirmation.visibleProperty().bind(changed);
				pass1.setDisable(true);
				pass2.setDisable(true);
				changePassButton.setDisable(true);
			} else {
				fail.visibleProperty().set(true);
			}
		}			
	}
	
	@FXML
	private TextField user;
	
	@FXML
	private Text userDisplay, manStat, userExists;
	@FXML
	private GridPane options;
	
	@FXML
	private Button updateButton, searchButton, addUserButton;
	
	private BooleanProperty hasUser = new SimpleBooleanProperty(false);
	
	private User u = null;
	
	@FXML
	private void searchButtonAction(ActionEvent event){
		updateButton.visibleProperty().bind(hasUser);
		if(!user.getText().isEmpty()){
			u = main.findUser(user.getText());
			hasUser.set(true);
		}
		
		if(u!=null){
			userDisplay.setText("User found: " +u.getUsername());
			if(u.isManager()){
				manStat.setText("Current Role: Manager");
			}
			else{
				manStat.setText("Current Role: Clerk");
			}
			options.visibleProperty().set(true);			
		}
	}
	
	boolean manager;
	boolean hasChosen = false;
	
	@FXML
	private RadioButton managerB, clerkB;
	
	@FXML
	private void managerButton(ActionEvent event){
		manager = true;
		hasChosen = true;
	}
	
	@FXML
	private void clerkButton(ActionEvent event){
		manager = false;
		hasChosen = true;
	}
	
	@FXML
	private void updateButtonAction(ActionEvent event){
		BooleanProperty updated;
		if (hasChosen) {
			updated = new SimpleBooleanProperty(main.editManager(
					u.getUsername(), manager));

			if (updated.get() == true) {
				confirmation.visibleProperty().bind(updated);
				user.setDisable(true);
				searchButton.setDisable(true);
				managerB.setDisable(true);
				clerkB.setDisable(true);
				updateButton.setDisable(true);
			}
		}
	}
	
	@FXML
	private TextField username,password;
	
	private void validateAddUser(){
		hasError.set(false);
		if(username.getText().isEmpty()){
			hasError.set(true);
			username.setStyle("-fx-background-color: #ffff99");
		} else {
			username.setStyle("-fx-background-color:  white");
		}
		if(password.getText().isEmpty()){
			hasError.set(true);
			password.setStyle("-fx-background-color: #ffff99");
		} else {
			password.setStyle("-fx-background-color:  white");
		}
		if(!hasChosen){
			hasError.set(true);
			managerB.setStyle("-fx-background-color: #ffff99");
			clerkB.setStyle("-fx-background-color: #ffff99");
		}
		else{
			managerB.setStyle("-fx-background-color: #FFF3E0");
			clerkB.setStyle("-fx-background-color: #FFF3E0");
		}
	}
	
	@FXML
	private void addUserButtonAction(ActionEvent event){
		validateAddUser();
		error.visibleProperty().bind(hasError);
		if(!hasError.get()){
			userExists.visibleProperty().bind(hasUser);
			u = main.findUser(username.getText());
			if(u == null){
				hasUser.set(false);
				u = new User(username.getText(),password.getText(), manager);
				main.add(u);
				confirmation.visibleProperty().set(true);
				username.setDisable(true);
				password.setDisable(true);
				managerB.setDisable(true);
				clerkB.setDisable(true);
				addUserButton.setDisable(true);
			}
			else{
				hasUser.set(true);
			}
		}
	}
}
