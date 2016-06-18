package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Main;
import main.Tuple;
import main.TuplePriority;


public class ReportsController implements Initializable {
	
	private Main main;
	
	private DecimalFormat df = new DecimalFormat("0.00");
	private DecimalFormat odf = new DecimalFormat("0.0");
	private DecimalFormat sf = new DecimalFormat("0");
	
	@FXML 
	private Button history;
	
	private BooleanProperty isManager = new SimpleBooleanProperty(false);
	
	@FXML
	private MenuButton accounts;

	public ReportsController(Main main){
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isManager.set(main.getCurrentUser().isManager());
		history.visibleProperty().bind(isManager);
				
		MenuItem changePassword = new MenuItem("Change Password");
		changePassword.setOnAction(changePassAction());
		accounts.getItems().add(changePassword);

		if (main.getCurrentUser().isManager()) {
			MenuItem addUser = new MenuItem("Add User");
			addUser.setOnAction(addUserAction());
			accounts.getItems().add(addUser);
			MenuItem editUser = new MenuItem("Edit User");
			editUser.setOnAction(editUserAction());
			accounts.getItems().add(editUser);
		}
		
		
	}
	 
	public void initData(){
		expenditure.setText("$" +  df.format(main.getTotalExp()));
		revenue.setText("$" +  df.format(main.getTotalRev()));
		eventcount.setText(sf.format(main.getTotalEvents()));
		routeLoadAction();
		criticalRoutesAction();
		averageTimesAction();
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
      	HistoryController cont = history.getController();
      	cont.initData();
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
    	main.logout();
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
		FXMLLoader delivery = new FXMLLoader(getClass().getResource(
				"/views/deliveryrequest.fxml"));
		delivery.setController(new FormController(main));
		Parent deliveryGUI = delivery.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(deliveryGUI);
		
		FormController controller = delivery.getController();
		controller.initDropdown();
		controller.initReq();
		
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void discontinueTransportAction(ActionEvent event)
			throws IOException {
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
		controller.timeMenu();
		controller.initRoute();
		stage.show();
	}

	@FXML
	private void priceUpdateAction(ActionEvent event) throws IOException {
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
		controller.initPrice();
		stage.show();
	}
	
	/** ACCOUNTS MENU ITEM */

	private EventHandler<ActionEvent> addUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader addUser = new FXMLLoader(getClass().getResource(
						"/views/adduser.fxml"));
				addUser.setController(new AccountsController(main));
				Parent addUserGUI = null;
				try {
					addUserGUI = addUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(addUserGUI);
				stage.setScene(scene);
				stage.show();
			}
		};
	}
	
	private EventHandler<ActionEvent> editUserAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				FXMLLoader editUser = new FXMLLoader(getClass().getResource(
						"/views/edituser.fxml"));
				editUser.setController(new AccountsController(main));
				Parent editUserGUI = null;
				try {
					editUserGUI = editUser.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				Stage stage = (Stage) accounts.getScene().getWindow();
				Scene scene = new Scene(editUserGUI);
				stage.setScene(scene);
				stage.show();
			}
		};
	}
	
	private EventHandler<ActionEvent> changePassAction() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event){
				FXMLLoader changePass = new FXMLLoader(getClass().getResource(
						"/views/changepass.fxml"));
				changePass.setController(new AccountsController(main));
				Parent changePassGUI;
				try {
					changePassGUI = changePass.load();
					Stage stage = (Stage) accounts.getScene().getWindow();
					Scene scene = new Scene(changePassGUI);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}

				
			}
		};
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
	    	RouteLoadRow row = new RouteLoadRow(t.getOrigin(), t.getDestination(), odf.format(temp.get(t).get(0))+" g", odf.format(temp.get(t).get(1))+" cm\u00b3", sf.format(temp.get(t).get(2)));
	    	report.add(row);
	    }
	    origin.setCellValueFactory(new PropertyValueFactory<Tuple,String>("origin"));
	    dest.setCellValueFactory(new PropertyValueFactory<Tuple,String>("destination"));
	    totalWeight.setCellValueFactory(new PropertyValueFactory<Tuple,String>("totalWeight"));
	    totalVolume.setCellValueFactory(new PropertyValueFactory<Tuple,String>("totalVolume"));
	    totalCount.setCellValueFactory(new PropertyValueFactory<Tuple,String>("totalCount"));
	    table.getItems().setAll(report);
	}
	
	@FXML
	private TableView<CriticalRoutesRow> criticalTable;
	@FXML
	private TableColumn<Tuple,String> critOrigin;
	@FXML
	private TableColumn<Tuple,String> critDest;
	@FXML
	private TableColumn<Tuple,String> critPriority;
	@FXML
	private TableColumn<Tuple,String> critPrice;
	@FXML
	private TableColumn<Tuple,String> critCost;
	@FXML
	private TableColumn<Tuple,String> critDiff;
	
	private ArrayList<CriticalRoutesRow> critReport;
	
	@FXML
	private void criticalRoutesAction(){
		HashMap<TuplePriority, ArrayList<Double>> temp = main.getCriticalRoutes();
	    critReport = new ArrayList<>();
	    for(TuplePriority t: temp.keySet()){
	    	CriticalRoutesRow row = new CriticalRoutesRow(t.getOrigin(), t.getDestination(),t.getPriority(), "$" +  df.format(temp.get(t).get(0)),"$" +  df.format(temp.get(t).get(1)),
	    			"$" +  df.format(temp.get(t).get(2)));
	    	critReport.add(row);
	    }
	    
	    critOrigin.setCellValueFactory(new PropertyValueFactory<Tuple,String>("origin"));
	    critDest.setCellValueFactory(new PropertyValueFactory<Tuple,String>("destination"));
	    critPriority.setCellValueFactory(new PropertyValueFactory<Tuple,String>("priority"));
	    critPrice.setCellValueFactory(new PropertyValueFactory<Tuple,String>("price"));
	    critCost.setCellValueFactory(new PropertyValueFactory<Tuple,String>("cost"));
	    critDiff.setCellValueFactory(new PropertyValueFactory<Tuple,String>("diff"));
	    criticalTable.getItems().setAll(critReport);
	}
	
	@FXML
	private TableView<AverageTimesRow> average;
	@FXML
	private TableColumn<Tuple,String> avOrigin;
	@FXML
	private TableColumn<Tuple,String> avDest;
	@FXML
	private TableColumn<Tuple,String> avPriority;
	@FXML
	private TableColumn<Tuple,String> avTime;
	
	private ArrayList<AverageTimesRow> avReport;

	@FXML
	private void averageTimesAction(){
		HashMap<TuplePriority, ArrayList<Integer>> temp = main.getAmountOfMailDeliveryTimes();
	    avReport = new ArrayList<>();
	    for(TuplePriority t: temp.keySet()){
	    	Double average = (double)temp.get(t).get(0)/(double)temp.get(t).get(1);
	    	AverageTimesRow row = new AverageTimesRow(t.getOrigin(), t.getDestination(), t.getPriority(), odf.format(average)+ " hrs");
	    	avReport.add(row);
	    }
	    avOrigin.setCellValueFactory(new PropertyValueFactory<Tuple,String>("origin"));
	    avDest.setCellValueFactory(new PropertyValueFactory<Tuple,String>("destination"));
	    avPriority.setCellValueFactory(new PropertyValueFactory<Tuple,String>("priority"));
	    avTime.setCellValueFactory(new PropertyValueFactory<Tuple,String>("time"));
	    average.getItems().setAll(avReport);
	}
}