package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;

public class HistoryController implements Initializable {
	
	private Main main;

	public HistoryController(Main main){
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	    
    /** NAV BAR BUTTONS 
     * @throws IOException */
    
    @FXML
    private void historyButtonAction(ActionEvent event) throws IOException{
   	
    	FXMLLoader history = new FXMLLoader(getClass().getResource("/views/readlog.fxml"));
    	history.setController(new HistoryController(main));
    	Parent historyGUI = history.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(historyGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void reportButtonAction(ActionEvent event) throws IOException{
    	FXMLLoader reports = new FXMLLoader(getClass().getResource("/views/reports.fxml"));
    	reports.setController(new ReportsController(main));
    	Parent reportsGUI = reports.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(reportsGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException{
    	FXMLLoader login = new FXMLLoader(getClass().getResource("/views/login.fxml"));
    	login.setController(new LoginController(main));
    	Parent loginGUI = login.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(loginGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    /** LOG EVENT MENU ITEM */
    
    @FXML MenuButton logeventmenu ;

    @FXML
    private void deliveryRequestAction(ActionEvent event) throws IOException{
    	System.out.println("Delivery Request");
    	
    	FXMLLoader delivery = new FXMLLoader(getClass().getResource("/views/deliveryrequest.fxml"));
    	delivery.setController(new FormController(main));
    	Parent deliveryGUI = delivery.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(deliveryGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void discontinueTransportAction(ActionEvent event) throws IOException{
    	System.out.println("Discontinue Transport");
    	logeventmenu.setText("Discontinue Transport");

    	FXMLLoader discontinue = new FXMLLoader(getClass().getResource("/views/discontinuetransport.fxml"));
    	discontinue.setController(new FormController(main));
    	Parent discontinueGUI = discontinue.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(discontinueGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void transportRouteAction(ActionEvent event) throws IOException{
    	System.out.println("Transport Route");
    	logeventmenu.setText("Transport Route");
    	
    	FXMLLoader route = new FXMLLoader(getClass().getResource("/views/transportroute.fxml"));
    	route.setController(new FormController(main));
    	Parent routeGUI = route.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(routeGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void priceUpdateAction(ActionEvent event) throws IOException {
    	System.out.println("Customer Price Update");
    	logeventmenu.setText("Customer Price Update");
    	
    	FXMLLoader price = new FXMLLoader(getClass().getResource("/views/priceupdate.fxml"));
    	price.setController(new FormController(main));
    	Parent priceGUI = price.load();
    	
    	
    	Stage stage = (Stage) logeventmenu.getScene().getWindow();
    	Scene scene = new Scene(priceGUI);
      	stage.setScene(scene);
    	stage.show();
    }
    
    private boolean firstview = true;

	@FXML
	private Button previous, next, readLog;
	@FXML
	private Text text1, text2, text3, text4, text5, text6, text7, text8, text9, text10, text11, text12, text13, text14,
			text15, text16, text17, text18, text19, text20, text21, text22, text23, text24, text25, text26, text27,
			text28, text29, text30, text31, text32, text33, text34;
	@FXML
	private Label logtype;

	@FXML
	private void readLogEvent(ActionEvent event) {
		if (firstview) {
			readLog.setVisible(false);
			firstview = false;
			// readLogEvent(event);
		}
		
		// if(CustomerPrice)
//		logtype.setText("Customer Price");
//		text1.setText("User: ");
//		text19.setText("Login Time: ");
//		text5.setText("Origin: ");
//		text6.setText("o");
//		text23.setText("Destination: ");
//		text24.setText("d");
//		text9.setText("Priority: ");
//		text10.setText("p");
//		text13.setText("Event Type: ");
//		text14.setText("e");
//		text27.setText("Weight Cost: ");
//		text28.setText("w");
//		text31.setText("Volume Cost: ");
//		text32.setText("v");
		
		// else if(DeliveryRequest)
//		logtype.setText("Delivery Request");
//		text1.setText("User: ");
//		text19.setText("Login Time: ");
//		text3.setText("Origin: ");
//		text4.setText("o");
//		text5.setText("Weight: ");
//		text6.setText("w");
//		text7.setText("Volume: ");
//		text8.setText("v");
//		text9.setText("Priority: ");
//		text10.setText("p");
//		text21.setText("Destination: ");
//		text22.setText("d");
//		text23.setText("Duration: ");
//		text24.setText("du");
//		text25.setText("Event Type: ");
//		text26.setText("e");
		
		// else if(DiscontinueRoute)
//		logtype.setText("Discontinue Route");
//		text1.setText("User: ");
//		text19.setText("Login Time: ");
//		text5.setText("Origin: ");
//		text6.setText("o");
//		text23.setText("Destination: ");
//		text24.setText("d");
//		text9.setText("Company: ");
//		text10.setText("c");
//		text13.setText("Event Type: ");
//		text14.setText("e");
//		text27.setText("Type: ");
//		text28.setText("t");

		// else if(Route)
//		logtype.setText("Route");
//		text1.setText("User: ");
//		text19.setText("Login Time: ");
//		text3.setText("Origin: ");
//		text4.setText("o");
//		text5.setText("Company: ");
//		text6.setText("c");
//		text7.setText("Type: ");
//		text8.setText("t");
//		text9.setText("Priority: ");
//		text10.setText("p");
//		text11.setText("Weight Cost: ");
//		text12.setText("wc");
//		text13.setText("Volume Cost: ");
//		text14.setText("vc");
//		text15.setText("Max Weight: ");
//		text16.setText("mw");
//		text17.setText("Max Volume: ");
//		text18.setText("mv");
//		text21.setText("Destination: ");
//		text22.setText("d");
//		text23.setText("Duration: ");
//		text24.setText("dur");
//		text25.setText("Frequency: ");
//		text26.setText("f");
//		text27.setText("Day: ");
//		text28.setText("day");
//		text29.setText("Start Time: ");
//		text30.setText("st");
//		text31.setText("Price: ");
//		text32.setText("$");
//		text33.setText("Event Type: ");
//		text34.setText("et");
	}
}