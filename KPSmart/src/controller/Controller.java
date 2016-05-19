package controller;

import javafx.fxml.Initializable;

import java.awt.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Controller implements Initializable {
	
	private boolean loggedin = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("View is now loaded!");
    }
    /** NAV BAR BUTTONS 
     * @throws IOException */
    @FXML
    private void historyButtonAction(ActionEvent event) throws IOException{
    	Node node=(Node) event.getSource();
    	Stage stage=(Stage) node.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/kpsgui.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void reportButtonAction(ActionEvent event) throws IOException{
    	Node node=(Node) event.getSource();
    	Stage stage=(Stage) node.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/reports.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException{
    	Node node=(Node) event.getSource();
    	Stage stage=(Stage) node.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
    	loggedin = false;
    }
    
    /** LOG IN SCREEN **/
    @FXML
    private TextField idnumber;
    @FXML
    private PasswordField password;
    
    @FXML
    private void logInButtonAction(ActionEvent event) throws IOException {
    	this.loggedin = true;
    	String id = this.idnumber.getText();
    	String pass = this.password.getText();
        System.out.println("ID number: " + id);
        System.out.println("Password: " + pass);
        
        Node node=(Node) event.getSource();
    	Stage stage=(Stage) node.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/kpsgui.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
    }
    
    /** LOG EVENT SCREEN **/
    @FXML
    private MenuButton origin;
    @FXML
    private MenuButton destination;
    @FXML
    private TextField weight;
    @FXML
    private TextField volume;
    
    @FXML
    private CheckMenuItem AucklandDest;
    @FXML
    private CheckMenuItem HamiltonDest;
    @FXML
    private CheckMenuItem RotoruaDest;
    @FXML
    private CheckMenuItem PalmerstonDest;
    @FXML
    private CheckMenuItem WellingtonDest;
    @FXML
    private CheckMenuItem ChristchurchDest;
    @FXML
    private CheckMenuItem DunedinDest;
    @FXML
    private CheckMenuItem OtherDest;
    @FXML
    private CheckMenuItem AucklandOrigin;
    @FXML
    private CheckMenuItem HamiltonOrigin;
    @FXML
    private CheckMenuItem RotoruaOrigin;
    @FXML
    private CheckMenuItem PalmerstonOrigin;
    @FXML
    private CheckMenuItem WellingtonOrigin;
    @FXML
    private CheckMenuItem ChristchurchOrigin;
    @FXML
    private CheckMenuItem DunedinOrigin;
    @FXML
    private CheckMenuItem OtherOrigin;
    
    
    @FXML
    private void selectAucklandOrigin(ActionEvent event) {
    	AucklandOrigin.isSelected();
    }
    @FXML
    private void selectHamiltonOrigin(ActionEvent event) {
    	HamiltonOrigin.isSelected();
    }
    @FXML
    private void selectRotoruaOrigin(ActionEvent event) {
    	RotoruaOrigin.isSelected();
    }
    @FXML
    private void selectPalmerstonOrigin(ActionEvent event) {
    	PalmerstonOrigin.isSelected();
    }
    @FXML
    private void selectWellingtonOrigin(ActionEvent event) {
    	WellingtonOrigin.isSelected();
    }
    @FXML
    private void selectChristchurchOrigin(ActionEvent event) {
    	ChristchurchOrigin.isSelected();
    }
    @FXML
    private void selectDunedinOrigin(ActionEvent event) {
    	DunedinOrigin.isSelected();
    }
    @FXML
    private void selectOtherOrigin(ActionEvent event) {
    	OtherOrigin.isSelected();
    }
    @FXML
    private void selectAucklandDest(ActionEvent event) {
    	AucklandDest.isSelected();
    }
    @FXML
    private void selectHamiltonDest(ActionEvent event) {
    	HamiltonDest.isSelected();
    }
    @FXML
    private void selectRotoruaDest(ActionEvent event) {
    	RotoruaDest.isSelected();
    }
    @FXML
    private void selectPalmerstonDest(ActionEvent event) {
    	PalmerstonDest.isSelected();
    }
    @FXML
    private void selectWellingtonDest(ActionEvent event) {
    	WellingtonDest.isSelected();
    }
    @FXML
    private void selectChristchurchDest(ActionEvent event) {
    	ChristchurchDest.isSelected();
    }
    @FXML
    private void selectDunedinDest(ActionEvent event) {
    	DunedinDest.isSelected();
    }
    @FXML
    private void selectOtherDest(ActionEvent event) {
    	OtherDest.isSelected();
    }
    
    @FXML
    private void logEventButtonAction(ActionEvent event) {
    	for(javafx.scene.control.MenuItem item : origin.getItems()){
    		String it = item.getText();
    		System.out.println(it);
    		System.out.println(it);
    	}
    	for(javafx.scene.control.MenuItem item : destination.getItems()){
    		String it = item.getText();
    		System.out.println(it);
    	}
    	String w = this.weight.getText();
    	String v = this.volume.getText();
        System.out.println("Weight: " + w);
        System.out.println("Volume: " + v);
    }
}