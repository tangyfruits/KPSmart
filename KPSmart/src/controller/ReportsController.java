package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;


public class ReportsController implements Initializable {
	
	private Main main;

	public ReportsController(Main main){
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
 
	/** REPORTS PAGE */
	@FXML
	private Tab generalReport;
	@FXML
	private Text expenditure, revenue, eventcount;
	@FXML
	private TabPane reportsTab;

	@FXML
	private Button getReports;
	@FXML
	private Button routeLoad;

	@FXML
	private void getReportsAction(ActionEvent event) {
		expenditure.setText(String.valueOf(main.getTotalExp()));
		revenue.setText(String.valueOf(main.getTotalRev()));
		eventcount.setText(String.valueOf(main.getTotalEvents()));
	}

	@FXML
	private void routeLoadAction(ActionEvent event) {

	}

	
}