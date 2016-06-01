package controller;

import javafx.fxml.Initializable;

import java.awt.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.RadioButton;
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
    private ArrayList<CheckMenuItem> destination;
    @FXML
    private ArrayList<CheckMenuItem> origin;
    
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
    private TextField otherOriginText;
    
    @FXML
    private void selectOtherOrigin(ActionEvent event) {
    	selectedOrigin = "Other";
    	originMenu.setText(otherOriginText.getText());
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
    private TextField otherDestText;
    @FXML
    private void selectOtherDest(ActionEvent event) {
    	selectedDest = "Other";
    	destinationMenu.setText(otherDestText.getText());
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

    /** Day Menu */
    
    @FXML
    private CheckMenuItem monday;
    @FXML
    private CheckMenuItem tuesday;
    @FXML
    private CheckMenuItem wednesday;
    @FXML
    private CheckMenuItem thursday;
    @FXML
    private CheckMenuItem friday;
    @FXML
    private MenuButton dayMenu;
    
    private String day = "";
    
    @FXML
    private void mondayAction(ActionEvent event) {
    	day = "Monday";
    	dayMenu.setText("Monday");
    }
    @FXML
    private void tuesdayAction(ActionEvent event) {
    	day = "Tuesday";
    	dayMenu.setText("Tuesday");
    }
    @FXML
    private void wednesdayAction(ActionEvent event) {
    	day = "Wednesday";
    	dayMenu.setText("Wednesday");
    }
    @FXML
    private void thursdayAction(ActionEvent event) {
    	day = "Thursday";
    	dayMenu.setText("Thursday");
    }
    @FXML
    private void fridayAction(ActionEvent event) {
    	day = "Friday";
    	dayMenu.setText("Friday");
    }
    
    /** Time Menu*/
    @FXML
    private CheckMenuItem zero, one, two, three, four, five, six, seven, 
    eight, nine, ten, eleven, twelve, thirteen, fourteen, fifteen, 
    sixteen, seventeen, eighteen, nineteen, twenty, twentyone, twentytwo, twentythree, twentyfour;
    @FXML
    private MenuButton timeMenu;
    
    private int time;
    
    @FXML
    private void zeroAction(ActionEvent event) {
    	time = 0;
    	timeMenu.setText("00");
    }
    @FXML
    private void oneAction(ActionEvent event) {
    	time = 1;
    	timeMenu.setText("01");
    }
    @FXML
    private void twoAction(ActionEvent event) {
    	time = 2;
    	timeMenu.setText("02");
    }
    @FXML
    private void threeAction(ActionEvent event) {
    	time = 3;
    	timeMenu.setText("03");
    }
    @FXML
    private void fourAction(ActionEvent event) {
    	time = 4;
    	timeMenu.setText("04");
    }
    @FXML
    private void fiveAction(ActionEvent event) {
    	time = 5;
    	timeMenu.setText("05");;
    }
    @FXML
    private void sixAction(ActionEvent event) {
    	time = 6;
    	timeMenu.setText("06");;
    }
    @FXML
    private void sevenAction(ActionEvent event) {
    	time = 7;
    	timeMenu.setText("07");;
    }
    @FXML
    private void eightAction(ActionEvent event) {
    	time = 8;
    	timeMenu.setText("08");;
    }
    @FXML
    private void nineAction(ActionEvent event) {
    	time = 9;
    	timeMenu.setText("09");;
    }
    @FXML
    private void tenAction(ActionEvent event) {
    	time = 10;
    	timeMenu.setText("10");;
    }
    @FXML
    private void elevenAction(ActionEvent event) {
    	time = 11;
    	timeMenu.setText("11");;
    }
    @FXML
    private void twelveAction(ActionEvent event) {
    	time = 12;
    	timeMenu.setText("12");;
    }
    @FXML
    private void thirteenAction(ActionEvent event) {
    	time = 13;
    	timeMenu.setText("13");;
    }
    @FXML
    private void fourteenAction(ActionEvent event) {
    	time = 14;
    	timeMenu.setText("14");;
    }
    @FXML
    private void fifteenAction(ActionEvent event) {
    	time = 15;
    	timeMenu.setText("15");;
    }
    @FXML
    private void sixteenAction(ActionEvent event) {
    	time = 16;
    	timeMenu.setText("16");;
    }
    @FXML
    private void seventeenAction(ActionEvent event) {
    	time = 17;
    	timeMenu.setText("17");;
    }
    @FXML
    private void eighteenAction(ActionEvent event) {
    	time = 18;
    	timeMenu.setText("18");;
    }
    @FXML
    private void nineteenAction(ActionEvent event) {
    	time = 19;
    	timeMenu.setText("19");;
    }
    @FXML
    private void twentyAction(ActionEvent event) {
    	time = 20;
    	timeMenu.setText("20");;
    }
    @FXML
    private void twentyoneAction(ActionEvent event) {
    	time = 21;
    	timeMenu.setText("21");;
    }
    @FXML
    private void twentytwoAction(ActionEvent event) {
    	time = 22;
    	timeMenu.setText("22");;
    }
    @FXML
    private void twentythreeAction(ActionEvent event) {
    	time = 23;
    	timeMenu.setText("23");;
    }
    @FXML
    private void twentyfourAction(ActionEvent event) {
    	time = 24;
    	timeMenu.setText("24");;
    }
    
    /** TRANSPORT ROUTE FORM */
    
    @FXML
    private void transportRouteButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    	String c = this.company.getText();
    	System.out.println("Type: " + type);
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

    	System.out.println("Day: " + day);
    	System.out.println("Time: " + time);
    }
    
    /** PRICE UPDATE FORM */
    
    @FXML
    private void priceUpdateButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    	String wc = this.weightcost.getText();
    	String vc = this.volumecost.getText();
    	System.out.println("Priority: " + priority);
        System.out.println("Weight: " + wc);
        System.out.println("Volume: " + vc);
    }
    
    /** DELIVERY REQUEST FORM */
    
    @FXML
    private RadioButton firstChoice;
    @FXML
    private RadioButton secondChoice;
    
    private boolean hasPriorities = false;
    
    private String chosenPriority = "";
    
    @FXML
    private TextField weight;
    @FXML
    private TextField volume;
    
    @FXML
    private void findPrioritiesButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    	String w = this.weight.getText();
    	String v = this.volume.getText();
    	System.out.println("Weight: " + w);
        System.out.println("Volume: " + v);
        firstChoice.setText("First Priority Choice");
        secondChoice.setText("Second Priority Choice");
        hasPriorities = true;
    }
    
    @FXML
    private void deliveryRequestButtonAction(ActionEvent event) {
    	if(hasPriorities){
    		System.out.println("Priority: " + chosenPriority);
    	}
    }
    
    @FXML
    private void firstChoiceAction(ActionEvent event) {
    	chosenPriority = "First Priority Choice";
    }
    
    @FXML
    private void secondChoiceAction(ActionEvent event) {
    	chosenPriority = "Second Priority Choice";
    }
    
    /** DISCONTINUE TRANSPORT */
    
    @FXML
    private void findRoutesButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    }
    
    @FXML
    private void discTransportButtonAction(ActionEvent event) {
    	System.out.println("Origin: " + selectedOrigin);
    	System.out.println("Destination: " + selectedDest);
    }
}