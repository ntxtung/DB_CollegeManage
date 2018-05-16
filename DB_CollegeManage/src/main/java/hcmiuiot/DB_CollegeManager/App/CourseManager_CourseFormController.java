package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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
    private ChoiceBox<String> chBoxDeptList;

	@FXML
	private JFXTextField txtFee;

	@FXML
	private JFXDatePicker dtEndDate;

	@FXML
	private JFXTextField txtNumCre;

	@FXML
	private JFXTextField txtRoom;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnOK;
	
	private ObservableList<String> deptName;
	private ResultSet deptList;

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
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onOK(ActionEvent event) {
		System.out.println(txtCourseID.getText());
		System.out.println(txtCourseName.getText());
		System.out.println(dtBeginDate.getValue());
		System.out.println(dtEndDate.getValue());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dtBeginDate.setConverter(dateFormat);
		dtEndDate.setConverter(dateFormat);
		
		loadDeptList();
		updateChoiceBoxView();
	}

	public ChoiceBox<String> getChBoxDeptList() {
		return chBoxDeptList;
	}
	
	private void updateChoiceBoxView() {
		deptName = FXCollections.observableArrayList();
		deptName.add("lasda");
		try {
			while (deptList.next()) {
				String dept = deptList.getString("deptID");
				deptName.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		chBoxDeptList.setItems(deptName);
	}
	
	private void loadDeptList() {
		deptList = DbHandler.execQuery("SELECT deptID FROM topicS.Department;");
	}
}
