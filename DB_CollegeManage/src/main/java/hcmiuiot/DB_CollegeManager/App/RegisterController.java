package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class RegisterController implements Initializable {
	
	@FXML 
	private Label studentID;
	@FXML
	private Label studentName;
	@FXML
	private TableView result;
	@FXML
	private JFXButton save;
	@FXML
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private void loadTable() {
		
	}
}
