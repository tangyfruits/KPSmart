package controller;

import javafx.fxml.Initializable;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
    
    @FXML
    private TextField idnumber;
    @FXML
    private PasswordField password;
    
    @FXML
    private void logInButtonAction(ActionEvent event) {
    	String id = this.idnumber.getText();
    	String pass = this.password.getText();
        System.out.println("ID number: " + id);
        System.out.println("Password: " + pass);
    }

}