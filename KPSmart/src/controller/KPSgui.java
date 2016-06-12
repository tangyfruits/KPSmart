package controller;

import java.time.DayOfWeek;

import main.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KPSgui extends Application {
	
	private Main main;

	@Override
	public void start(Stage primaryStage) throws Exception {		

		main = new Main();
        main.logCustomerPriceUpdate("Wellington", "Auckland", "Air", 12, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "UPS", "Air", 4, 4, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logCustomerPriceUpdate("Wellington", "Auckland", "Standard", 2, 5, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "A", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "B", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "C", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "D", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "E", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "F", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "G", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "H", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
        main.logTransportCostUpdate("Wellington", "Auckland", "J", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "K", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "L", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "M", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "N", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "O", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "P", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "Q", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "R", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
//        main.logTransportCostUpdate("Wellington", "Auckland", "S", "Standard",2 , 1, 15, 15, 12, 24, DayOfWeek.THURSDAY, 12, false);
       
        FXMLLoader login = new FXMLLoader(getClass().getResource("/views/login.fxml"));
		login.setController(new LoginController(main));
		Parent UI = login.load();
						
		primaryStage.setTitle("KPSmart");
		Scene scene = new Scene(UI, 700, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}