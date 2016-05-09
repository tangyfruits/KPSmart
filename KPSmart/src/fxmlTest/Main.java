package fxmlTest;

//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {		
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		primaryStage.setTitle("KPSmart");
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
