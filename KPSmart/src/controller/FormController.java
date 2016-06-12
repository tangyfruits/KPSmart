package controller;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.ResourceBundle;

import event.CustomerPrice;
import event.DeliveryRequest;
import event.DiscontinueRoute;
import event.Route;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.Location;
import main.Main;
import main.RouteDisplay;

public class FormController implements Initializable {

	private Main main;

	public FormController(Main main) {
		this.main = main;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	private ArrayList<Location> locs;
	@FXML
	private MenuButton originMenu;
	@FXML
	private MenuButton destinationMenu;

	private String selectedOrigin = "";
	private String selectedDest = "";
	
	public void initDropdown(){
		locs = main.getLocations();
		EventHandler<ActionEvent> originAction = setSelectedOrigin();
		EventHandler<ActionEvent> destAction = setSelectedDest();
				
		for(Location l:locs){
			MenuItem i = new CheckMenuItem(l.getName());
			i.setUserData(l.getName());
			i.setOnAction(originAction);
			originMenu.getItems().add(i);
			MenuItem k = new CheckMenuItem(l.getName());
			k.setOnAction(destAction);
			destinationMenu.getItems().add(k);
		}		
	}
	
	private EventHandler<ActionEvent> setSelectedOrigin() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String loc = mItem.getText();
               System.out.println(loc);
               selectedOrigin = loc;
               originMenu.setText(loc);            
            }
        };
    }
	
	private EventHandler<ActionEvent> setSelectedDest() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String loc = mItem.getText();
               System.out.println(loc);
               selectedDest = loc;
               destinationMenu.setText(loc);            
            }
        };
	}
	/**
	 * NAV BAR BUTTONS
	 * 
	 * @throws IOException
	 */

	@FXML
	private void historyButtonAction(ActionEvent event) throws IOException {

		FXMLLoader history = new FXMLLoader(getClass().getResource(
				"/views/readlog.fxml"));
		history.setController(new HistoryController(main));
		Parent historyGUI = history.load();

		Stage stage = (Stage) logeventmenu.getScene().getWindow();
		Scene scene = new Scene(historyGUI);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void reportButtonAction(ActionEvent event) throws IOException {
		FXMLLoader reports = new FXMLLoader(getClass().getResource(
				"/views/reports.fxml"));
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
	private void logoutButtonAction(ActionEvent event) throws IOException {
		FXMLLoader login = new FXMLLoader(getClass().getResource(
				"/views/login.fxml"));
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
		controller.initDropdown();
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
		controller.initDropdown();
		stage.show();
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
		day = "MONDAY";
		dayMenu.setText("Monday");
	}

	@FXML
	private void tuesdayAction(ActionEvent event) {
		day = "TUESDAY";
		dayMenu.setText("Tuesday");
	}

	@FXML
	private void wednesdayAction(ActionEvent event) {
		day = "WEDNESDAY";
		dayMenu.setText("Wednesday");
	}

	@FXML
	private void thursdayAction(ActionEvent event) {
		day = "THURSDAY";
		dayMenu.setText("Thursday");
	}

	@FXML
	private void fridayAction(ActionEvent event) {
		day = "FRIDAY";
		dayMenu.setText("Friday");
	}

	/** Time Menu */
	@FXML
	private CheckMenuItem zero, one, two, three, four, five, six, seven, eight,
			nine, ten, eleven, twelve, thirteen, fourteen, fifteen, sixteen,
			seventeen, eighteen, nineteen, twenty, twentyone, twentytwo,
			twentythree, twentyfour;
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
		timeMenu.setText("05");
		;
	}

	@FXML
	private void sixAction(ActionEvent event) {
		time = 6;
		timeMenu.setText("06");
		;
	}

	@FXML
	private void sevenAction(ActionEvent event) {
		time = 7;
		timeMenu.setText("07");
		;
	}

	@FXML
	private void eightAction(ActionEvent event) {
		time = 8;
		timeMenu.setText("08");
		;
	}

	@FXML
	private void nineAction(ActionEvent event) {
		time = 9;
		timeMenu.setText("09");
		;
	}

	@FXML
	private void tenAction(ActionEvent event) {
		time = 10;
		timeMenu.setText("10");
		;
	}

	@FXML
	private void elevenAction(ActionEvent event) {
		time = 11;
		timeMenu.setText("11");
		;
	}

	@FXML
	private void twelveAction(ActionEvent event) {
		time = 12;
		timeMenu.setText("12");
		;
	}

	@FXML
	private void thirteenAction(ActionEvent event) {
		time = 13;
		timeMenu.setText("13");
		;
	}

	@FXML
	private void fourteenAction(ActionEvent event) {
		time = 14;
		timeMenu.setText("14");
		;
	}

	@FXML
	private void fifteenAction(ActionEvent event) {
		time = 15;
		timeMenu.setText("15");
		;
	}

	@FXML
	private void sixteenAction(ActionEvent event) {
		time = 16;
		timeMenu.setText("16");
		;
	}

	@FXML
	private void seventeenAction(ActionEvent event) {
		time = 17;
		timeMenu.setText("17");
		;
	}

	@FXML
	private void eighteenAction(ActionEvent event) {
		time = 18;
		timeMenu.setText("18");
		;
	}

	@FXML
	private void nineteenAction(ActionEvent event) {
		time = 19;
		timeMenu.setText("19");
		;
	}

	@FXML
	private void twentyAction(ActionEvent event) {
		time = 20;
		timeMenu.setText("20");
		;
	}

	@FXML
	private void twentyoneAction(ActionEvent event) {
		time = 21;
		timeMenu.setText("21");
		;
	}

	@FXML
	private void twentytwoAction(ActionEvent event) {
		time = 22;
		timeMenu.setText("22");
		;
	}

	@FXML
	private void twentythreeAction(ActionEvent event) {
		time = 23;
		timeMenu.setText("23");
		;
	}

	@FXML
	private void twentyfourAction(ActionEvent event) {
		time = 24;
		timeMenu.setText("24");
		;
	}

	@FXML
	private Button submit;

	private BooleanProperty completed = new SimpleBooleanProperty(false);

	@FXML
	private Text confirmation;

	/** TRANSPORT ROUTE FORM */

	@FXML
	private void transportRouteButtonAction(ActionEvent event) {
		String mw = this.maxweight.getText();
		String mv = this.maxvolume.getText();
		String wc = this.weightcost.getText();
		String vc = this.volumecost.getText();
		String d = this.duration.getText();
		String f = this.frequency.getText();

		Route r = main.logTransportCostUpdate(selectedOrigin, selectedDest,
				company.getText(), type, Double.parseDouble(wc),
				Double.parseDouble(vc), Integer.parseInt(mw),
				Integer.parseInt(mv), Integer.parseInt(d), Integer.parseInt(f),
				DayOfWeek.valueOf(day), time, false);
		System.out.println(main.getTotalEvents());

		if (r != null) {
			confirmation.visibleProperty().bind(completed);
			completed.set(true);
			originMenu.setDisable(true);
			destinationMenu.setDisable(true);
			weightcost.setDisable(true);
			volumecost.setDisable(true);
			maxweight.setDisable(true);
			maxvolume.setDisable(true);
			duration.setDisable(true);
			frequency.setDisable(true);
			typemenu.setDisable(true);
			dayMenu.setDisable(true);
			timeMenu.setDisable(true);
			submit.setDisable(true);
			company.setDisable(true);
		}
	}

	/** PRICE UPDATE FORM */

	@FXML
	private void priceUpdateButtonAction(ActionEvent event) {
		String wc = this.weightcost.getText();
		String vc = this.volumecost.getText();

		CustomerPrice price = main.logCustomerPriceUpdate(selectedOrigin,
				selectedDest, priority, Double.parseDouble(wc),
				Double.parseDouble(vc), false);

		if (price != null) {
			confirmation.visibleProperty().bind(completed);
			completed.set(true);
			originMenu.setDisable(true);
			destinationMenu.setDisable(true);
			weightcost.setDisable(true);
			volumecost.setDisable(true);
			prioritymenu.setDisable(true);
			submit.setDisable(true);
		}

		System.out.println(main.getTotalEvents());

	}

	/** DELIVERY REQUEST FORM */

	@FXML
	private Button reselect;

	@FXML
	private Button findPriorities;

	@FXML
	private RadioButton firstChoice;
	@FXML
	private RadioButton secondChoice;

	private BooleanProperty hasPriorities = new SimpleBooleanProperty(false);

	private RouteDisplay chosenPriority = null;

	private ArrayList<RouteDisplay> routes;

	@FXML
	private TextField weight;
	@FXML
	private TextField volume;

	@FXML
	private Text noRoutes;

	private BooleanProperty routeless = new SimpleBooleanProperty(false);

	@FXML
	private void findPrioritiesButtonAction(ActionEvent event) {
		String w = this.weight.getText();
		String v = this.volume.getText();
		firstChoice.setSelected(false);
		secondChoice.setSelected(false);
		routes = main.getPossibleRoutes(selectedOrigin, selectedDest,
				Double.parseDouble(w), Double.parseDouble(v));

		if (routes.size() > 0) {
			submit.visibleProperty().bind(hasPriorities);
			reselect.visibleProperty().bind(hasPriorities);
			firstChoice.setText(routes.get(0).getPriority() + ": $"
					+ routes.get(0).getPrice());
			firstChoice.visibleProperty().bind(hasPriorities);
			if (routes.size() == 2) {
				secondChoice.setText(routes.get(1).getPriority() + ": $"
						+ routes.get(1).getPrice());
				secondChoice.visibleProperty().bind(hasPriorities);
			}
			hasPriorities.set(true);
		} else {
			noRoutes.visibleProperty().bind(routeless);
			reselect.visibleProperty().bind(routeless);
			routeless.set(true);
		}
		originMenu.setDisable(true);
		destinationMenu.setDisable(true);
		weight.setDisable(true);
		volume.setDisable(true);
		findPriorities.setDisable(true);
	}

	@FXML
	private void deliveryRequestButtonAction(ActionEvent event) {
		if (!(chosenPriority == null)) {
			System.out.println("Priority: " + chosenPriority.getPriority());
			DeliveryRequest req = main.getDeliveryDetails(selectedOrigin,
					selectedDest, Double.parseDouble(this.weight.getText()),
					Double.parseDouble(this.volume.getText()), chosenPriority);

			System.out.println(req.toString());
			System.out.println(main.getTotalEvents());
			chosenPriority = null;
			routes = null;
			confirmation.visibleProperty().bind(completed);

			if (req != null) {
				completed.set(true);
				reselect.setDisable(true);
				firstChoice.setDisable(true);
				secondChoice.setDisable(true);
				submit.setDisable(true);
			}
		}
	}

	@FXML
	private void reselectButtonAction(ActionEvent event) {
		originMenu.setDisable(false);
		destinationMenu.setDisable(false);
		weight.setDisable(false);
		volume.setDisable(false);
		findPriorities.setDisable(false);
		routeless.set(false);
		hasPriorities.set(false);
	}

	@FXML
	private void firstChoiceAction(ActionEvent event) {
		chosenPriority = routes.get(0);
	}

	@FXML
	private void secondChoiceAction(ActionEvent event) {
		chosenPriority = routes.get(1);
	}

	/** DISCONTINUE TRANSPORT */
	
	@FXML
	private Button changeSelection;
	
	@FXML
	private Button findroutes;
	
	@FXML
	private ScrollPane scrollpane;
	
	private BooleanProperty hasRoutes = new SimpleBooleanProperty(false);

	@FXML
	private VBox discRoutes;

	private ArrayList<Route> disconRoutes;
	
	private Route selectedRoute;

	@FXML
	private void findRoutesButtonAction(ActionEvent event) {
		disconRoutes = main.getRoutes(selectedOrigin, selectedDest);
		final ToggleGroup group = new ToggleGroup();
		group.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					@Override
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle t, Toggle t1) {
						selectedRoute = (Route) t1.getUserData();
					}
				});
		for (Route r : disconRoutes) {
			RadioButton b = new RadioButton(r.getCompany() + ": "
					+ r.getPriority());
			b.setUserData(r);
			b.setToggleGroup(group);
			discRoutes.getChildren().add(b);
		}
		discRoutes.setSpacing(5);
		
		changeSelection.visibleProperty().bind(hasRoutes);
		discRoutes.visibleProperty().bind(hasRoutes);
		submit.visibleProperty().bind(hasRoutes);
		scrollpane.visibleProperty().bind(hasRoutes);
		hasRoutes.set(true);
		
		originMenu.setDisable(true);
		destinationMenu.setDisable(true);
		findroutes.setDisable(true);
		
	}
	
	@FXML
	private void reSelect(ActionEvent event){
		originMenu.setDisable(false);
		destinationMenu.setDisable(false);
		findroutes.setDisable(false);
		hasRoutes.set(false);
		discRoutes.getChildren().clear();
	}

	@FXML
	private void discTransportButtonAction(ActionEvent event) {
		if (selectedRoute!=null) {
			DiscontinueRoute route = main.discontinueTransportRoute(
					selectedOrigin, selectedDest, selectedRoute.getCompany(),
					selectedRoute.getType(), false);
			if (route != null) {
				changeSelection.setDisable(true);
				for (Node r : discRoutes.getChildren()) {
					r.setDisable(true);
				}
				submit.setDisable(true);
				confirmation.visibleProperty().bind(completed);
				completed.set(true);
			}
		}
		
		
	}

}