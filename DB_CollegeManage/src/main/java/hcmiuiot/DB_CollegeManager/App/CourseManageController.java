package hcmiuiot.DB_CollegeManager.App;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
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
    private JFXComboBox<String> chBoxDepartPick;

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
	private JFXButton btnAddCourse;

	@FXML
	private JFXButton btnEditCourse;

	@FXML
	private JFXButton btnDeleteCourse;

	@FXML
	private JFXButton btnStdDetail;

	@FXML
	private JFXButton btnInstructorDetail;

	private ResultSet tableData, deptList;
	private TreeItem<Course> tableItem;
	private ObservableList<String> deptName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
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
				updateTableView(tableData);
				updateChoiceBoxView();
				tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				   Platform.runLater(() -> {
					   if (newSelection != null) {
					    	btnEditCourse.setDisable(false);
					    	btnDeleteCourse.setDisable(false);
					    	btnStdDetail.setDisable(false);
					    } else {
					    	btnEditCourse.setDisable(true);
					    	btnDeleteCourse.setDisable(true);
					    	btnStdDetail.setDisable(true);
					    }   
				   });
				});
				txtFieldSearch.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

						tableView.setPredicate(new Predicate<TreeItem<Course>>() {
							@Override
							public boolean test(TreeItem<Course> t) {
								// System.out.println(newValue);
								Boolean flag = t.getValue().name.getValue().contains(newValue);
								return flag;
							}
						});
					}
				});
			}
		}).start();

		
	}

	@FXML
	public void onAddCourse(ActionEvent event) throws IOException {
		Stage formStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("CourseManage_CourseForm.fxml"));
		loader.load();

//		CourseManager_CourseFormController form = loader.getController();

		Parent root = loader.getRoot();
		Scene scene = new Scene(root);

		formStage.setScene(scene);
		formStage.setResizable(false);
		formStage.setTitle("Add course");
		formStage.showAndWait();
	}

	@FXML
	public void onDelete(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setHeaderText("Do you want to delete this course?");
		alert.setContentText("WARNING: You cannot undo this action");

		Optional<ButtonType> result = alert.showAndWait();

		TreeItem<Course> selectedCourse = tableView.getSelectionModel().getSelectedItem();
		// System.out.println(selectedCourse.getValue().courseID.get());

		if (result.get() == ButtonType.OK) {
			DbHandler.execUpdate(
					"DELETE FROM topicS.Course WHERE courseID = \"" + selectedCourse.getValue().courseID.get() + "\";");
			refreshTableView();
		} else {
			// Beautiful thing ♥
		}
	}

	@FXML
	public void onEdit(ActionEvent event) throws IOException {
		Stage formStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("CourseManage_CourseForm.fxml"));
		loader.load();

		CourseManager_CourseFormController form = loader.getController();
		TreeItem<Course> selectedCourse = tableView.getSelectionModel().getSelectedItem();

		String _courseID = selectedCourse.getValue().courseID.get();
		String _deptID = selectedCourse.getValue().deptID.get();
		String _courseName = selectedCourse.getValue().name.get();
		String _beginDate = selectedCourse.getValue().beginDate.get();
		String _endDate = selectedCourse.getValue().endDate.get();
		String _fee = selectedCourse.getValue().fee.getValue().toString();
		String _numCre = selectedCourse.getValue().numberOfCredit.getValue().toString();
		String _maxSlot = selectedCourse.getValue().maxSlot.getValue().toString();
		String _room = selectedCourse.getValue().room.get();

		form.setInitData(_courseID, _deptID, _courseName, _beginDate, _endDate, _fee, _numCre, _maxSlot, _room);
		form.setToEditMode();
		
		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		formStage.setScene(scene);
		formStage.setResizable(false);
		formStage.setTitle("Edit course");
		formStage.showAndWait();
	}

	@FXML
	public void onInstructorDetail(ActionEvent event) {

	}

	@FXML
	public void onStdDetail(ActionEvent event) throws IOException {
		Stage formStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("CourseManage_StudentDetail.fxml"));
		loader.load();

		CourseManage_StudentDetailController form = loader.getController();
		TreeItem<Course> selectedCourse = tableView.getSelectionModel().getSelectedItem();
		form.setInitDeptID(selectedCourse.getValue().courseID.get());
		
		Parent root = loader.getRoot();
		Scene scene = new Scene(root);
		formStage.setScene(scene);
		formStage.setResizable(false);
		formStage.setTitle("Show enroll student in " + selectedCourse.getValue().name.get());
		formStage.showAndWait();

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

		tableItem = new RecursiveTreeItem<Course>(courses, RecursiveTreeObject::getChildren);
		tableView.getColumns().setAll(courseID, departmentID, name, beginDate, endDate, fee, numberOfCredits, room,
				maxSlot);
		tableView.setRoot(tableItem);
		tableView.setShowRoot(false);
	}

	private void updateChoiceBoxView() {
		deptName = FXCollections.observableArrayList();
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
				if (number2.intValue() > 0) {
					tableData = DbHandler.execQuery(
							"SELECT * FROM topicS.Course WHERE deptID IN (SELECT deptID FROM topicS.Department WHERE name = '"
									+ chBoxDepartPick.getItems().get(number2.intValue()) + "');");
				} else {
					tableData = DbHandler.execQuery("SELECT * FROM topicS.Course");
				}
				updateTableView(tableData);
			}
		});
	}

	private void refreshTableView() {
		String choice = chBoxDepartPick.getItems().get(chBoxDepartPick.getSelectionModel().getSelectedIndex());
		if (choice.equals("--All--")) {
			loadDB();
		} else {
			tableData = DbHandler.execQuery(
					"SELECT * FROM topicS.Course WHERE deptID IN (SELECT deptID FROM topicS.Department WHERE name = '"
							+ chBoxDepartPick.getSelectionModel().getSelectedItem()
							+ "');");
		}
		updateTableView(tableData);
	}

	private void loadDeptList() {
		deptList = DbHandler.execQuery("SELECT name FROM topicS.Department");
	}

	private void loadDB() {
		tableData = DbHandler.execQuery("SELECT * FROM topicS.Course");
	}

}
