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
import javafx.scene.control.DatePicker;
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
    private void transportRouteAction(ActionEvent event) throws IOException{
    	System.out.println("Transport Route");
    	logeventmenu.setText("Transport Route");
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/transportroute.fxml"));/* Exception */
    	Scene scene = new Scene(root);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void priceUpdateAction(ActionEvent event) throws IOException{
    	System.out.println("Customer Price Update");
    	logeventmenu.setText("Customer Price Update");
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Parent root = FXMLLoader.load(getClass().getResource("/views/priceupdate.fxml"));/* Exception */
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
    
    /** Origin and Destination  **/

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
    
    private String selectedOrigin = "";
    private String selectedDest = "";
    
    @FXML
    private void selectAucklandOrigin(ActionEvent event) {
    	selectedOrigin = "Auckland";
    	originMenu.setText("Auckland");
    }
    @FXML
    private void selectHamiltonOrigin(ActionEvent event) {
    	selectedOrigin = "Hamilton";
    	originMenu.setText("Hamilton");
    }
    @FXML
    private void selectRotoruaOrigin(ActionEvent event) {
    	selectedOrigin = "Rotorua";
    	originMenu.setText("Rotorua");
    }
    @FXML
    private void selectPalmerstonOrigin(ActionEvent event) {
    	selectedOrigin = "Palmerston North";
    	originMenu.setText("Palmerston North");
    }
    @FXML
    private void selectWellingtonOrigin(ActionEvent event) {
    	selectedOrigin = "Wellington";
    	originMenu.setText("Wellington");
    }
    @FXML
    private void selectChristchurchOrigin(ActionEvent event) {
    	selectedOrigin = "Christchurch";
    	originMenu.setText("Christchurch");
    }
    @FXML
    private void selectDunedinOrigin(ActionEvent event) {
    	selectedOrigin = "Dunedin";
    	originMenu.setText("Dunedin");
    }
    @FXML
    private void selectOtherOrigin(ActionEvent event) {
    	selectedOrigin = "Other";
    	originMenu.setText("Other");
    }
    @FXML
    private void selectAucklandDest(ActionEvent event) {
    	selectedDest = "Auckland";
    	destinationMenu.setText("Auckland");
    }
    @FXML
    private void selectHamiltonDest(ActionEvent event) {
    	selectedDest = "Hamilton";
    	destinationMenu.setText("Hamilton");
    }
    @FXML
    private void selectRotoruaDest(ActionEvent event) {
    	selectedDest = "Rotorua";
    	destinationMenu.setText("Rotorua");
    }
    @FXML
    private void selectPalmerstonDest(ActionEvent event) {
    	selectedDest = "Palmerston North";
    	destinationMenu.setText("Palmerston North");
    }
    @FXML
    private void selectWellingtonDest(ActionEvent event) {
    	selectedDest = "Wellington";
    	destinationMenu.setText("Wellington");
    }
    @FXML
    private void selectChristchurchDest(ActionEvent event) {
    	selectedDest = "Christchurch";
    	destinationMenu.setText("Christchurch");
    }
    @FXML
    private void selectDunedinDest(ActionEvent event) {
    	selectedDest = "Dunedin";
    	destinationMenu.setText("Dunedin");
    }
    @FXML
    private void selectOtherDest(ActionEvent event) {
    	selectedDest = "Other";
    	destinationMenu.setText("Other");
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
    
    /** Date and Time */
    @FXML
    private DatePicker date;
    
    
    @FXML
    private void transportRouteButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    	String c = this.company.getText();
    	String mw = this.maxweight.getText();
    	String mv = this.maxvolume.getText();
    	String wc = this.weightcost.getText();
    	String vc = this.volumecost.getText();
    	String d = this.duration.getText();
    	String f = this.frequency.getText();
        System.out.println("Company: " + c);
        System.out.println("Max Weight: " + mw);
        System.out.println("Max Volume: " + mv);
        System.out.println("Weight Cost: " + wc);
        System.out.println("Volume Cost: " + vc);
        System.out.println("Duration: " + d);
        System.out.println("Frequency: " + f);
    	System.out.println("Priority: " + priority);
    	System.out.println("Type: " + type);
    	System.out.println("Time: " + this.date.getValue().toString());
    }
    
    @FXML
    private void priceUpdateButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    	String wc = this.weightcost.getText();
    	String vc = this.volumecost.getText();
        System.out.println("Weight Cost: " + wc);
        System.out.println("Volume Cost: " + vc);
    	System.out.println("Priority: " + priority);
    }

}