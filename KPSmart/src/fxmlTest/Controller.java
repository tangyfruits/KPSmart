package fxmlTest;

import javafx.fxml.Initializable;

import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controller implements Initializable {

	@FXML
	private TextField id_number;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
    
    @FXML private Text actiontarget;
    
    @FXML protected void logInActionButton(ActionEvent event) {
    	String username = this.id_number.getText();
        System.out.println("Log in button pressed");
        System.out.println(username);
    }
}