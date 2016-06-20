package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import event.CustomerPrice;
import event.Route;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Main;

public class PopUpController implements Initializable {

	private Main main;
	private String origin, dest, priority;
	private Route r;
	private FormController c;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
	public PopUpController(Main main, String origin, String dest, String priority, Route r, FormController c){
		this.main = main;
		this.origin = origin;
		this.dest = dest;
		this.priority = priority;
		this.r = r;
		this.c = c;
	}
	
	private BooleanProperty hasError = new SimpleBooleanProperty(false);

	@FXML
	private TextField weightPrice;
	@FXML
	private TextField volumePrice;
	@FXML
	private Text error;
	
	@FXML
	private void popUpSubmit(){
		String w = weightPrice.getText();
		String v = volumePrice.getText();
		
		validate(w,v);
		
		if (!hasError.get()) {
			CustomerPrice price = main.logCustomerPriceUpdate(origin, dest,
					priority, Double.parseDouble(w), Double.parseDouble(v),
					false, LocalDateTime.now(), main.getCurrentUser().getUsername());
			r.setPrice(price);
			System.out.println(r.getPrice().toString());
			c.closeModal();
		}
		else{
			error.visibleProperty().bind(hasError);
		}
	}
	
	private void validate(String w, String v){
		if ( w.isEmpty() || !isDouble(w) || v.isEmpty()
				|| !isDouble(v)) {
			
		
			if (w.isEmpty() || !isDouble(w)) {
				hasError.set(true);
				weightPrice.setStyle("-fx-control-inner-background: #ffff99");
			} else{
				weightPrice.setStyle("-fx-control-inner-background: white");

			}
			if (v.isEmpty() || !isDouble(v)) {
				hasError.set(true);
				 volumePrice.setStyle("-fx-control-inner-background: #ffff99");
			} else {
				 volumePrice.setStyle("-fx-control-inner-background: white");

			}
					
		} else {
			hasError.set(false);
		}
	}
	

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
