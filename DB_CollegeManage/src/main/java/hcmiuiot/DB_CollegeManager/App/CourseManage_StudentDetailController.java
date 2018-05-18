package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

class EnrollStudent extends RecursiveTreeObject<EnrollStudent> {
	StringProperty studentID;
	StringProperty fName;
	StringProperty lName;

	public EnrollStudent(String studentID, String fName, String lName) {
		this.studentID = new SimpleStringProperty(studentID);
		this.fName = new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
	}
}

public class CourseManage_StudentDetailController implements Initializable {
	@FXML
	private JFXTreeTableView<EnrollStudent> tableView;

	@FXML
	private TreeTableColumn<EnrollStudent, String> studentID;

	@FXML
	private TreeTableColumn<EnrollStudent, String> fName;
	
	@FXML
	private TreeTableColumn<EnrollStudent, String> lName;

	private ResultSet tableData;
	private TreeItem<EnrollStudent> tableItem;
	private String initDeptID;

	private void loadDB() {
		tableData = DbHandler.execQuery("CALL GetStudentsEnrolledCourse('"+initDeptID+"')");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				loadDB();

				studentID.setCellValueFactory(
						new Callback<TreeTableColumn.CellDataFeatures<EnrollStudent, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(
									TreeTableColumn.CellDataFeatures<EnrollStudent, String> param) {
								return param.getValue().getValue().studentID;
							}
						});

				fName.setCellValueFactory(
						new Callback<TreeTableColumn.CellDataFeatures<EnrollStudent, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(
									TreeTableColumn.CellDataFeatures<EnrollStudent, String> param) {
								return param.getValue().getValue().fName;
							}
						});

				lName.setCellValueFactory(
						new Callback<TreeTableColumn.CellDataFeatures<EnrollStudent, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(
									TreeTableColumn.CellDataFeatures<EnrollStudent, String> param) {
								return param.getValue().getValue().lName;
							}
						});
				

				updateTableView(tableData);
			}
		}).start();

	}

	private void updateTableView(ResultSet inputResult) {
		ObservableList<EnrollStudent> students = FXCollections.observableArrayList();

		try {
			while (inputResult.next()) {
				String _studentId = inputResult.getString("studentID");
				String _fName = inputResult.getString("fName");
				String _lName = inputResult.getString("lName");
				students.add(new EnrollStudent(_studentId, _fName, _lName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tableItem = new RecursiveTreeItem<EnrollStudent>(students, RecursiveTreeObject::getChildren);
		tableView.setRoot(tableItem);
		tableView.setShowRoot(false);
	}

	private void refreshTableView() {
		loadDB();
		updateTableView(tableData);
	}

	public void setInitDeptID(String initDeptID) {
		this.initDeptID = initDeptID;
	}

}
