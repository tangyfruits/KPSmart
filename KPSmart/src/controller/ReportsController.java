package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.Main;
import main.Tuple;
import main.TuplePriority;

public class ReportsController extends NavBarController {
	
	// FIELDS
	private DecimalFormat df = new DecimalFormat("0.00");
	private DecimalFormat odf = new DecimalFormat("0.0");
	private DecimalFormat sf = new DecimalFormat("0");

	// CONSTRUCTOR
	public ReportsController(Main main){
		this.main = main;
	}

	// REPORTS
	// General Reports
	/** REPORTS PAGE */
	@FXML
	private Tab generalReport;
	@FXML
	private Text expenditure, revenue, eventcount;
	public void initData(){
		expenditure.setText("$" +  df.format(main.getTotalExp()));
		revenue.setText("$" +  df.format(main.getTotalRev()));
		eventcount.setText(sf.format(main.getTotalEvents()));
		routeLoadAction();
		criticalRoutesAction();
		averageTimesAction();
	}
	
	// Amount of Mail Report
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
	
	// Critical Routes Report
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
	
	// Average Delivery Times Report
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