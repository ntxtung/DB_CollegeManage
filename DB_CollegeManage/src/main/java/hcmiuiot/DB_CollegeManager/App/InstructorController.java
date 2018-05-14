package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class InstructorController implements Initializable {
	@FXML
	private JFXComboBox cb;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		loadCombo();
	}
	
	private void loadCombo() {
		ResultSet rs = DbHandler.getInstance().ExecSQL("SELECT name FROM Department");
		try {
			while(rs.next()) {
				cb.getItems().removeAll(cb.getItems());
				cb.getItems().addAll(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
