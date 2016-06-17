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
        
        FXMLLoader login = new FXMLLoader(getClass().getResource("/views/login.fxml"));
		login.setController(new LoginController(main));
		Parent UI = login.load();
						
		primaryStage.setTitle("KPSmart");
		primaryStage.setMinWidth(720);
		primaryStage.setMinHeight(620);
		Scene scene = new Scene(UI, 700, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}