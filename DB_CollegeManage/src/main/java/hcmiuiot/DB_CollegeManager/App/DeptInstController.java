package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
/*
 *  Control Department & Instructor Form
 */
public class DeptInstController implements Initializable {
	
	@FXML
	private JFXComboBox<String> cb;
	@FXML 
	private Text name;
	@FXML
	private Text email;
	@FXML
	private Text phone;
	
	final ObservableList<String> options1 = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadCombo(null);
	}
	
	private void loadCombo(ActionEvent event) {
		ResultSet rs = DbHandler.getInstance().ExecSQL("SELECT name FROM topicS.Department");
		try {
			while (rs.next()) {
				// Get data from "name" column
				options1.add(rs.getString("name"));
				// Set into data from ComboBox to Text
				cb.getSelectionModel().selectedItemProperty()
			    .addListener(new ChangeListener<String>() {
			        public void changed(ObservableValue<? extends String> observable,
			                            String oldValue, String newValue) {
			            name.setText(newValue);
			            setData(oldValue);
			        }
			    });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cb.setItems(options1);
		cb.setValue(null);
		cb.getSelectionModel().select(0);
	}
	
	private void setData(String newValue) {
		ResultSet rs1 = DbHandler.getInstance().ExecSQL("SELECT mail FROM topicS.Department"
														+ "WHERE name = '" + newValue + "'" + ";");
		try {
			while(rs1.next()) 
			{
				String data = rs1.getString("mail");
				email.setText(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
