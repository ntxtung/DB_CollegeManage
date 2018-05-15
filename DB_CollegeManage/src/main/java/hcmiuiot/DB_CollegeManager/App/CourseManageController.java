package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

class Course extends RecursiveTreeObject<Course> {
	StringProperty courseID;
	StringProperty deptID;
	StringProperty name;
	StringProperty beginDate;
	StringProperty endDate;
	ObservableValue<Number> fee;
	IntegerProperty numberOfCredit;
	IntegerProperty maxSlot;
	StringProperty room;

	public Course(String courseID, String deptID, String name, String beginDate, String endDate, double fee,
			int numberOfCredit, int maxSlot, String room) {
		this.courseID = new SimpleStringProperty(courseID);
		this.deptID = new SimpleStringProperty(deptID);
		this.name = new SimpleStringProperty(name);
		this.beginDate = new SimpleStringProperty(beginDate);
		this.endDate = new SimpleStringProperty(endDate);
		this.fee = new SimpleDoubleProperty(fee);
		this.numberOfCredit = new SimpleIntegerProperty(numberOfCredit);
		this.maxSlot = new SimpleIntegerProperty(maxSlot);
		this.room = new SimpleStringProperty(room);
	}

}

public class CourseManageController implements Initializable {
	@FXML
	private JFXTreeTableView<Course> tableView;

	@FXML
	private ChoiceBox<String> chBoxDepartPick;

	@FXML
	private JFXTextField txtFieldSearch;

	@FXML
	private TreeTableColumn<Course, String> courseID;

	@FXML
	private TreeTableColumn<Course, String> departmentID;

	@FXML
	private TreeTableColumn<Course, String> name;

	@FXML
	private TreeTableColumn<Course, String> beginDate;

	@FXML
	private TreeTableColumn<Course, String> endDate;

	@FXML
	private TreeTableColumn<Course, Number> fee;

	@FXML
	private TreeTableColumn<Course, Number> numberOfCredits;

	@FXML
	private TreeTableColumn<Course, Number> maxSlot;

	@FXML
	private TreeTableColumn<Course, String> room;

	@FXML
	private TreeTableColumn<?, ?> action;

	private ResultSet result, deptList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDB();
		loadDeptList();

		courseID.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().courseID;
					}
				});

		departmentID.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().deptID;
					}
				});

		name.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().name;
					}
				});

		beginDate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().beginDate;
					}
				});

		endDate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().endDate;
					}
				});

		fee.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
						return param.getValue().getValue().fee;
					}
				});

		numberOfCredits.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
						return param.getValue().getValue().numberOfCredit;
					}
				});

		maxSlot.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
						return param.getValue().getValue().maxSlot;
					}
				});

		room.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().room;
					}
				});
		updateTableView(result);
		updateChoiceBoxView();

	}

	private void updateTableView(ResultSet inputResult) {
		ObservableList<Course> courses = FXCollections.observableArrayList();

		try {
			while (inputResult.next()) {
				String _courseId = inputResult.getString("courseID");
				String _deptId = inputResult.getString("deptID");
				String _name = inputResult.getString("name");
				String _beginDate = inputResult.getString("begin_date");
				String _endDate = inputResult.getString("end_date");
				double _fee = inputResult.getDouble("fee");
				int _numberOfCredits = inputResult.getInt("num_of_credits");
				int _maxSlot = inputResult.getInt("max_slot");
				String _room = inputResult.getString("room");
				courses.add(new Course(_courseId, _deptId, _name, _beginDate, _endDate, _fee, _numberOfCredits,
						_maxSlot, _room));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		final TreeItem<Course> root = new RecursiveTreeItem<Course>(courses, RecursiveTreeObject::getChildren);
		tableView.getColumns().setAll(courseID, departmentID, name, beginDate, endDate, fee, numberOfCredits, room,
				maxSlot);
		tableView.setRoot(root);
		tableView.setShowRoot(false);
	}

	private void updateChoiceBoxView() {
		ObservableList<String> deptName = FXCollections.observableArrayList();
		deptName.add("--All--");
		try {
			while (deptList.next()) {
				String dept = deptList.getString("name");
				deptName.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		chBoxDepartPick.setItems(deptName);
		chBoxDepartPick.getSelectionModel().selectFirst();
		chBoxDepartPick.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				System.out.println(chBoxDepartPick.getItems().get(number2.intValue()));
				ResultSet res;
				if (number2.intValue() > 0) {
					res = DbHandler.execSQL("SELECT * FROM topicS.Course WHERE deptID IN (SELECT deptID FROM topicS.Department WHERE name = '"+ chBoxDepartPick.getItems().get(number2.intValue()) + "');");
				} else {
					res = DbHandler.execSQL("SELECT * FROM topicS.Course");
				}
				updateTableView(res);
			}
		});
	}

	private void loadDeptList() {
		deptList = DbHandler.execSQL("SELECT name FROM topicS.Department");
	}

	private void loadDB() {
		result = DbHandler.execSQL("SELECT * FROM topicS.Course");
	}

}
