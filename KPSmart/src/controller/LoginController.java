package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class LoginController implements Initializable {

	private Main main;

	private BooleanProperty b = new SimpleBooleanProperty(false);
	public LoginController(Main main) {
		this.main = main;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	/** LOG IN SCREEN **/
	@FXML
	private TextField idnumber;
	@FXML
	private PasswordField password;
	@FXML
	private Text loginerror;

	@FXML
	private void logInButtonAction(ActionEvent event) throws IOException {
		loginerror.visibleProperty().bind(b);
		String id = this.idnumber.getText();
		String pass = this.password.getText();
		if (main.login(id, pass)) {
			FXMLLoader kpsgui = new FXMLLoader(getClass().getResource("/views/kpsgui.fxml"));
			kpsgui.setController(new ReportsController(main));
			Parent kpsguiUI = kpsgui.load();

			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			Scene scene = new Scene(kpsguiUI);
			stage.setScene(scene);
			stage.show();
		} else if (pass.equals("")) {
			password.requestFocus();
		} else{
			idnumber.clear();
			password.clear();
			idnumber.requestFocus();
			b.set(true);
		}
		
	}

}
