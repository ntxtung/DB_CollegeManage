package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CourseManager_CourseFormController implements Initializable {
	@FXML
	private JFXTextField txtCourseID;

	@FXML
	private JFXTextField txtCourseName;

	@FXML
	private JFXDatePicker dtBeginDate;

	@FXML
    private JFXComboBox<String> chBoxDeptList;

	@FXML
	private JFXTextField txtFee;

	@FXML
	private JFXDatePicker dtEndDate;

	@FXML
	private JFXTextField txtNumCre;

	@FXML
	private JFXTextField txtMaxSlot;

	@FXML
	private JFXTextField txtRoom;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnOK;

	private ObservableList<String> deptID;
	private ResultSet deptTable;
	private String initID;
	private int mode = 0;

	private StringConverter<LocalDate> dateFormat = new StringConverter<LocalDate>() {
		private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		@Override
		public String toString(LocalDate localDate) {
			if (localDate == null)
				return "";
			return dateTimeFormatter.format(localDate);
		}

		@Override
		public LocalDate fromString(String dateString) {
			if (dateString == null || dateString.trim().isEmpty()) {
				return null;
			}
			return LocalDate.parse(dateString, dateTimeFormatter);
		}
	};

	@FXML
	void onCancel(ActionEvent event) {
		close();
	}

	@FXML
	void onOK(ActionEvent event) {
		if (mode == 0) {
			insertData();
		} else {
			editData();
		}
		close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dtBeginDate.setConverter(dateFormat);
		dtEndDate.setConverter(dateFormat);
		
		loadDeptList();
		updateChoiceBoxView();
		
	}

	private void updateChoiceBoxView() {
		deptID = FXCollections.observableArrayList();
		try {
			while (deptTable.next()) {
				String dept = deptTable.getString("deptID");
				deptID.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		chBoxDeptList.setItems(deptID);
	}

	private void loadDeptList() {
		
		deptTable = DbHandler.execQuery("SELECT * FROM topicS.Department;");
	}

	private void close() {
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	private void insertData() {
		String _courseID = txtCourseID.getText();
		String _deptID = chBoxDeptList.getSelectionModel().getSelectedItem();
		String _courseName = txtCourseName.getText();
		String _beginDate = dtBeginDate.getValue().toString();
		String _endDate = dtEndDate.getValue().toString();
		String _fee = txtFee.getText();
		String _numCre = txtNumCre.getText();
		String _maxSlot = txtMaxSlot.getText();
		String _room = txtRoom.getText();

		String statement = "INSERT INTO `topicS`.`Course` (`courseID`, `deptID`, `name`, `begin_date`, `end_date`, `fee`, `num_of_credits`, `max_slot`, `room`) VALUES ";
		statement += "('" + _courseID + "', '" + _deptID + "', '" + _courseName + "', '" + _beginDate + "', '"
				+ _endDate + "', '" + _fee + "', '" + _numCre + "', '" + _maxSlot + "','" + _room + "')";
		DbHandler.execUpdate(statement);
	}

	private void editData() {
		String _deptID = chBoxDeptList.getSelectionModel().getSelectedItem();
		String _courseName = txtCourseName.getText();
		String _beginDate = dtBeginDate.getValue().toString();
		String _endDate = dtEndDate.getValue().toString();
		String _fee = txtFee.getText();
		String _numCre = txtNumCre.getText();
		String _maxSlot = txtMaxSlot.getText();
		String _room = txtRoom.getText();

		String statement = "UPDATE `topicS`.`Course` ";
		statement += "SET `deptID`='" + _deptID + "', `name`='" + _courseName + "', `begin_date`='" + _beginDate
				+ "', `end_date`='" + _endDate + "', `fee`='" + _fee + "', `num_of_credits`='" + _numCre
				+ "', `max_slot`='" + _maxSlot + "', `room`='" + _room + "'";
		statement += "WHERE `courseID`='" + initID + "'";

		DbHandler.execUpdate(statement);
	}

	public void setInitData(String _courseID, String _deptID, String _courseName, String _beginDate, String _endDate,
			String _fee, String _numCre, String _maxSlot, String _room) {
		initID = _courseID;
		txtCourseID.setText(_courseID);
		chBoxDeptList.getSelectionModel().select(_deptID);
		txtCourseName.setText(_courseName);
		dtBeginDate.setValue(LocalDate.parse(_beginDate));
		dtEndDate.setValue(LocalDate.parse(_endDate));
		txtFee.setText(_fee);
		txtNumCre.setText(_numCre);
		txtMaxSlot.setText(_maxSlot);
		txtRoom.setText(_room);
	}
	
	public void setToEditMode() {
		mode = 1;
		txtCourseID.setDisable(true);
	}
	
	public void setToAddMode() {
		mode = 0;
	}
}
