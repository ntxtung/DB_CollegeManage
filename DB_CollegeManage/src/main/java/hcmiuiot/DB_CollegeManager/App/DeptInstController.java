package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class DeptInstController implements Initializable {
	@FXML
	private JFXComboBox<String> cb;
	final ObservableList<String> options = FXCollections.observableArrayList();;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadCombo(null);
	}
	
	@FXML
	private void loadCombo(ActionEvent event) {
		ResultSet rs = DbHandler.getInstance().ExecSQL("SELECT name FROM topicS.Department");
		cb.setItems(options);
		try {
			while (rs.next()) {
				options.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cb.getSelectionModel().select(0);
	}
	
}
