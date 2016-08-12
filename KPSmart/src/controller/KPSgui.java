package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import main.LogReader;
import main.Main;

public class KPSgui extends Application {

	private Main main;

	@Override
	public void start(Stage primaryStage) throws Exception {

		main = new Main();
		LogReader reader = new LogReader(main.getLogFile(), main);
		reader.parseFile();
		FXMLLoader login = new FXMLLoader(getClass().getResource("/views/login.fxml"));
		login.setController(new LoginController(main));
		Parent UI = login.load();
		primaryStage.setTitle("KPSmart");
		primaryStage.setMinWidth(700);
		primaryStage.setMinHeight(600);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../views/icon.png"))); // must be in /bin/views
		Scene scene = new Scene(UI, 700, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}