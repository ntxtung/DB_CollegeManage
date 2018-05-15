package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	private ChoiceBox<?> chBoxDepartPick;

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
    private TreeTableColumn<Course, String> numberOfCredits;

    @FXML
    private TreeTableColumn<Course, String> maxSlot;

    @FXML
    private TreeTableColumn<Course, String> room;

	private ResultSet result;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDB();

		courseID = new JFXTreeTableColumn<>("CourseID");
		courseID.setPrefWidth(100);
		courseID.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().courseID;
					}
				});

		departmentID = new JFXTreeTableColumn<>("Department");
		departmentID.setPrefWidth(100);
		departmentID.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().deptID;
					}
				});

		name = new JFXTreeTableColumn<>("Name");
		name.setPrefWidth(220);
		name.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().name;
					}
				});

		beginDate = new JFXTreeTableColumn<>("Begin Date");
		beginDate.setPrefWidth(100);
		beginDate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().beginDate;
					}
				});

		endDate = new JFXTreeTableColumn<>("End Date");
		endDate.setPrefWidth(100);
		endDate.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().endDate;
					}
				});

		JFXTreeTableColumn<Course, Number> fee = new JFXTreeTableColumn<>("Fee");
		fee.setPrefWidth(90);
		fee.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
						return param.getValue().getValue().fee;
					}
				});

		JFXTreeTableColumn<Course, Number> numberOfCredits = new JFXTreeTableColumn<>("#Credits");
		numberOfCredits.setPrefWidth(80);
		numberOfCredits.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
						return param.getValue().getValue().numberOfCredit;
					}
				});

		JFXTreeTableColumn<Course, Number> maxSlot = new JFXTreeTableColumn<>("Max Slot");
		maxSlot.setPrefWidth(80);
		maxSlot.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
					@Override
					public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
						return param.getValue().getValue().maxSlot;
					}
				});

		JFXTreeTableColumn<Course, String> room = new JFXTreeTableColumn<>("Room");
		room.setPrefWidth(80);
		room.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
						return param.getValue().getValue().room;
					}
				});

		ObservableList<Course> courses = FXCollections.observableArrayList();

		try {
			while (result.next()) {
				String _courseId = result.getString("courseID");
				String _deptId = result.getString("deptID");
				String _name = result.getString("name");
				String _beginDate = result.getString("begin_date");
				String _endDate = result.getString("end_date");
				double _fee = result.getDouble("fee");
				int _numberOfCredits = result.getInt("num_of_credits");
				int _maxSlot = result.getInt("max_slot");
				String _room = result.getString("room");
				courses.add(new Course(_courseId, _deptId, _name, _beginDate, _endDate, _fee, _numberOfCredits, _maxSlot,
						_room));

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

	public void loadDB() {
		result = DbHandler.execSQL("SELECT * FROM topicS.Course");
	}

}
