package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.Tuple;


public class ReportsController implements Initializable {
	
	private Main main;

	public ReportsController(Main main){
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	 
	public void initData(){
		expenditure.setText(Double.toString(main.getTotalExp()));
		revenue.setText(Double.toString(main.getTotalRev()));
		eventcount.setText(Double.toString(main.getTotalEvents()));
		routeLoadAction();
	}
	
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
      	ReportsController cont = reports.getController();
    	cont.initData();
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

	@FXML
	MenuButton logeventmenu;

	@FXML
	private void deliveryRequestAction(ActionEvent event) throws IOException {
		System.out.println("Delivery Request");

		FXMLLoader delivery = new FXMLLoader(getClass().getResource(
				"/views/deliveryrequest.fxml"));
		delivery.setController(new FormController(main));
		Parent deliveryGUI = delivery.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(deliveryGUI);
		
		FormController controller = delivery.getController();
		controller.initDropdown();
		
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void discontinueTransportAction(ActionEvent event)
			throws IOException {
		System.out.println("Discontinue Transport");
		logeventmenu.setText("Discontinue Transport");

		FXMLLoader discontinue = new FXMLLoader(getClass().getResource(
				"/views/discontinuetransport.fxml"));
		discontinue.setController(new FormController(main));
		Parent discontinueGUI = discontinue.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(discontinueGUI);
		stage.setScene(scene);
		FormController controller = discontinue.getController();
		controller.initDropdown();
		stage.show();
	}

	@FXML
	private void transportRouteAction(ActionEvent event) throws IOException {
		System.out.println("Transport Route");
		logeventmenu.setText("Transport Route");

		FXMLLoader route = new FXMLLoader(getClass().getResource(
				"/views/transportroute.fxml"));
		route.setController(new FormController(main));
		Parent routeGUI = route.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(routeGUI);
		stage.setScene(scene);
		FormController controller = route.getController();
		controller.initDropdownWithOther();
		stage.show();
	}

	@FXML
	private void priceUpdateAction(ActionEvent event) throws IOException {
		System.out.println("Customer Price Update");
		logeventmenu.setText("Customer Price Update");

		FXMLLoader price = new FXMLLoader(getClass().getResource(
				"/views/priceupdate.fxml"));
		price.setController(new FormController(main));
		Parent priceGUI = price.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(priceGUI);
		stage.setScene(scene);
		FormController controller = price.getController();
		controller.initDropdownWithOther();
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
	private TableView<RouteLoadRow> table;
	@FXML
	private TableColumn<Tuple,String> origin;
	@FXML
	private TableColumn<Tuple,String> dest;
	@FXML
	private TableColumn<Tuple,String> totalWeight;
	@FXML
	private TableColumn<Tuple,String> totalVolume;
	@FXML
	private TableColumn<Tuple,String> totalCount;
		
	private ArrayList<RouteLoadRow> report;

	@FXML
	private void routeLoadAction() {
	    HashMap<Tuple, ArrayList<Double>> temp = main.getAmountOfMail();
	    report = new ArrayList<RouteLoadRow>();
	    for(Tuple t: temp.keySet()){
	    	RouteLoadRow row = new RouteLoadRow(t.getOrigin(), t.getDestination(), Double.toString(temp.get(t).get(0)), Double.toString(temp.get(t).get(1)), Double.toString(temp.get(t).get(2)));
	    	report.add(row);
	    }
	   
	    origin.setCellValueFactory(new PropertyValueFactory<Tuple,String>("origin"));
	    dest.setCellValueFactory(new PropertyValueFactory<Tuple,String>("destination"));
	    totalWeight.setCellValueFactory(new PropertyValueFactory<Tuple,String>("totalWeight"));
	    totalVolume.setCellValueFactory(new PropertyValueFactory<Tuple,String>("totalVolume"));
	    totalCount.setCellValueFactory(new PropertyValueFactory<Tuple,String>("totalCount"));
	    table.getItems().setAll(report);
	}

	
}