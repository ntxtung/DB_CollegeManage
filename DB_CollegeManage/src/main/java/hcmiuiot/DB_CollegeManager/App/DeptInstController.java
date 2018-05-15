package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
		
		HashMap<String, String> dataMap = new HashMap<String, String>();
		
		ResultSet rs = DbHandler.getInstance().ExecSQL("SELECT name, mail FROM topicS.Department");
		try {
			while (rs.next()) {
				String deptName = rs.getString("name");
				dataMap.put(deptName, rs.getString("mail"));
				options1.add(deptName);
				cb.getSelectionModel().selectedItemProperty()
			    .addListener(new ChangeListener<String>() {
			        public void changed(ObservableValue<? extends String> observable,
			                            String oldValue, String newValue) {
			            name.setText(newValue);
			            if (newValue != null) {
			            	email.setText(dataMap.get(newValue));
			            }
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
	
}
