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
    
    /** LOG EVENT MENU ITEM */
    
    @FXML MenuButton logeventmenu ;
    
    @FXML
    private void deliveryRequestAction(ActionEvent event) throws IOException{
    	System.out.println("Delivery Request");
    	logeventmenu.setText("Delivery Request");
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/deliveryrequest.fxml"));/* Exception */
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    	
    }
    
    @FXML
    private void discontinueTransportAction(ActionEvent event) throws IOException{
    	System.out.println("Discontinue Transport");
    	logeventmenu.setText("Discontinue Transport");
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/discontinuetransport.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void updateExistingLogAction(ActionEvent event) throws IOException{
    	System.out.println("Update Log");
    	logeventmenu.setText("Update Log");
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/updatelog.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
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
    private MenuButton originMenu;
    @FXML
    private MenuButton destinationMenu;
    
    private String selectedOrigin, selectedDest;
    
    @FXML
    private void selectAucklandOrigin(ActionEvent event) {
    	AucklandOrigin.isSelected();
    	selectedOrigin = "Auckland";
    	originMenu.setText("Auckland");
    }
    @FXML
    private void selectHamiltonOrigin(ActionEvent event) {
    	HamiltonOrigin.isSelected();
    	selectedOrigin = "Hamilton";
    	originMenu.setText("Hamilton");
    }
    @FXML
    private void selectRotoruaOrigin(ActionEvent event) {
    	RotoruaOrigin.isSelected();
    	selectedOrigin = "Rotorua";
    	originMenu.setText("Rotorua");
    }
    @FXML
    private void selectPalmerstonOrigin(ActionEvent event) {
    	PalmerstonOrigin.isSelected();
    	selectedOrigin = "Palmerston North";
    	originMenu.setText("Palmerston North");
    }
    @FXML
    private void selectWellingtonOrigin(ActionEvent event) {
    	WellingtonOrigin.isSelected();
    	selectedOrigin = "Wellington";
    	originMenu.setText("Wellington");
    }
    @FXML
    private void selectChristchurchOrigin(ActionEvent event) {
    	ChristchurchOrigin.isSelected();
    	selectedOrigin = "Christchurch";
    	originMenu.setText("Christchurch");
    }
    @FXML
    private void selectDunedinOrigin(ActionEvent event) {
    	DunedinOrigin.isSelected();
    	selectedOrigin = "Dunedin";
    	originMenu.setText("Dunedin");
    }
    @FXML
    private void selectOtherOrigin(ActionEvent event) {
    	OtherOrigin.isSelected();
    	selectedOrigin = "Other";
    	originMenu.setText("Other");
    }
    @FXML
    private void selectAucklandDest(ActionEvent event) {
    	AucklandDest.isSelected();
    	selectedDest = "Auckland";
    	destinationMenu.setText("Auckland");
    }
    @FXML
    private void selectHamiltonDest(ActionEvent event) {
    	HamiltonDest.isSelected();
    	selectedDest = "Hamilton";
    	destinationMenu.setText("Hamilton");
    }
    @FXML
    private void selectRotoruaDest(ActionEvent event) {
    	RotoruaDest.isSelected();
    	selectedDest = "Rotorua";
    	destinationMenu.setText("Rotorua");
    }
    @FXML
    private void selectPalmerstonDest(ActionEvent event) {
    	PalmerstonDest.isSelected();
    	selectedDest = "Palmerston North";
    	destinationMenu.setText("Palmerston North");
    }
    @FXML
    private void selectWellingtonDest(ActionEvent event) {
    	WellingtonDest.isSelected();
    	selectedDest = "Wellington";
    	destinationMenu.setText("Wellington");
    }
    @FXML
    private void selectChristchurchDest(ActionEvent event) {
    	ChristchurchDest.isSelected();
    	selectedDest = "Christchurch";
    	destinationMenu.setText("Christchurch");
    }
    @FXML
    private void selectDunedinDest(ActionEvent event) {
    	DunedinDest.isSelected();
    	selectedDest = "Dunedin";
    	destinationMenu.setText("Dunedin");
    }
    @FXML
    private void selectOtherDest(ActionEvent event) {
    	OtherDest.isSelected();
    	selectedDest = "Other";
    	destinationMenu.setText("Other");
    }
    
    @FXML
    private CheckMenuItem intlAir;
    @FXML
    private CheckMenuItem intlStd;
    @FXML
    private CheckMenuItem domesticAir;
    @FXML
    private CheckMenuItem domesticStd;
    @FXML
    private MenuButton prioritymenu;

    private String priority;
    
    @FXML
    private void intlAirAction(ActionEvent event) {
    	intlAir.isSelected();
    	priority = "International Air";
    	prioritymenu.setText("International Air");
    }
    @FXML
    private void intlStdAction(ActionEvent event) {
    	intlStd.isSelected();
    	priority = "International Standard";
    	prioritymenu.setText("International Standard");
    }
    @FXML
    private void domesticAirAction(ActionEvent event) {
    	domesticAir.isSelected();
    	priority = "Domestic Air";
    	prioritymenu.setText("Domestic Air");
    }
    @FXML
    private void domesticStdAction(ActionEvent event) {
    	domesticStd.isSelected();
    	priority = "Domestic Standard";
    	prioritymenu.setText("Domestic Standard");
    }
    
    @FXML
    private void logEventButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    	String w = this.weight.getText();
    	String v = this.volume.getText();
        System.out.println("Weight: " + w);
        System.out.println("Volume: " + v);
    	System.out.println("Priority: " + priority);
    }
}