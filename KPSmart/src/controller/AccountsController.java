package controller;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import main.Main;
import main.User;

public class AccountsController extends NavBarController {

	// CONSTRUCTOR
	public AccountsController(Main main) {
		this.main = main;
	}

	// Change Password
	@FXML
	private TextField pass1, pass2;
	private BooleanProperty hasError = new SimpleBooleanProperty(false);
	private BooleanProperty nonMatch = new SimpleBooleanProperty(false);

	@FXML
	private Text error, match, confirmation, fail;
	@FXML
	private Button changePassButton;

	private void validatePassword() {
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
	private void changePassButtonAction(ActionEvent event) {
		error.visibleProperty().bind(hasError);
		match.visibleProperty().bind(nonMatch);
		validatePassword();

		if (!hasError.get() && !nonMatch.get()) {
			BooleanProperty changed = new SimpleBooleanProperty(main.editPassword(pass1.getText()));
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

	// Edit User + Create User + Delete User
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
	private void searchButtonAction(ActionEvent event) {
		updateButton.visibleProperty().bind(hasUser);
		if (!user.getText().isEmpty()) {
			u = main.findUser(user.getText());

		}

		if (u != null) {
			hasUser.set(true);
			userDisplay.setText("User found: " + u.getUsername());
			if (u.isManager()) {
				manStat.setText("Current Role: Manager");
			} else {
				manStat.setText("Current Role: Clerk");
			}
			options.visibleProperty().set(true);
		} else {
			userDisplay.setText("No user has been found");
			manStat.setText("Please try again");
		}
	}

	boolean manager;
	boolean hasChosen = false;

	@FXML
	private RadioButton managerB, clerkB;

	@FXML
	private void managerButton(ActionEvent event) {
		manager = true;
		hasChosen = true;
	}

	@FXML
	private void clerkButton(ActionEvent event) {
		manager = false;
		hasChosen = true;
	}

	@FXML
	private void updateButtonAction(ActionEvent event) {
		BooleanProperty updated;
		if (hasChosen) {
			updated = new SimpleBooleanProperty(main.editManager(u.getUsername(), manager));

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
	private TextField username, password;

	private void validateAddUser() {
		hasError.set(false);
		if (username.getText().isEmpty()) {
			hasError.set(true);
			username.setStyle("-fx-background-color: #ffff99");
		} else {
			username.setStyle("-fx-background-color:  white");
		}
		if (password.getText().isEmpty()) {
			hasError.set(true);
			password.setStyle("-fx-background-color: #ffff99");
		} else {
			password.setStyle("-fx-background-color:  white");
		}
		if (!hasChosen) {
			hasError.set(true);
			managerB.setStyle("-fx-background-color: #ffff99");
			clerkB.setStyle("-fx-background-color: #ffff99");
		} else {
			managerB.setStyle("-fx-background-color: #FFF3E0");
			clerkB.setStyle("-fx-background-color: #FFF3E0");
		}
	}

	@FXML
	private void addUserButtonAction(ActionEvent event) {
		validateAddUser();
		error.visibleProperty().bind(hasError);
		if (!hasError.get()) {
			userExists.visibleProperty().bind(hasUser);
			u = main.findUser(username.getText());
			if (u == null) {
				hasUser.set(false);
				u = new User(username.getText(), password.getText(), manager);
				main.add(u);
				confirmation.visibleProperty().set(true);
				username.setDisable(true);
				password.setDisable(true);
				managerB.setDisable(true);
				clerkB.setDisable(true);
				addUserButton.setDisable(true);
			} else {
				hasUser.set(true);
			}
		}
	}

	@FXML
	private void findUser(ActionEvent event) {
		updateButton.visibleProperty().bind(hasUser);
		if (!user.getText().isEmpty()) {
			u = main.findUser(user.getText());

		}

		if (u != null) {
			hasUser.set(true);
			userDisplay.setText("User found: " + u.getUsername());
			if (u.isManager()) {
				manStat.setText("Current Role: Manager");
			} else {
				manStat.setText("Current Role: Clerk");
			}
		} else {
			userDisplay.setText("No user has been found");
			manStat.setText("Please try again");
		}
	}

	@FXML
	private void deleteUserAction(ActionEvent event) throws IOException {
		boolean removed = main.delete(u);
		confirmation.visibleProperty().set(removed);
		if (removed) {
			user.setDisable(true);
			searchButton.setDisable(true);
			updateButton.setDisable(true);
		}
		if (u.getUsername().equals(main.getCurrentUser().getUsername())) {
			logoutButtonAction(event);
		}
	}
}
