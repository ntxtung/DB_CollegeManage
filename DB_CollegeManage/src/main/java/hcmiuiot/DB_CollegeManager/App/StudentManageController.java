package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

class Student extends RecursiveTreeObject<Student> {
	StringProperty studentID;
	StringProperty fName;
	StringProperty lName;
	StringProperty birthday;
	StringProperty deptID;

	public Student(String studentID, String fName, String lName, Date birthday, String deptID) {
		this.studentID = new SimpleStringProperty(studentID);
		this.fName = new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
		this.birthday = new SimpleStringProperty(birthday.toString());
		this.deptID = new SimpleStringProperty(deptID);
	}
}

public class StudentManageController implements Initializable {
	@FXML
    private JFXTreeTableView<Student> tableView;

    @FXML
    private TreeTableColumn<Student, String> studentID;

    @FXML
    private TreeTableColumn<Student, String> fName;

    @FXML
    private TreeTableColumn<Student, String> lName;

    @FXML
    private TreeTableColumn<Student, String> dateOfBirth;

    @FXML
    private TreeTableColumn<Student, String> deptID;

    @FXML
    private ChoiceBox<String> chBoxDepartPick;

    @FXML
    private JFXTextField txtFieldSearch;

    @FXML
    private JFXButton btnAddStudent;

    @FXML
    private JFXButton btnEditStudent;

    @FXML
    private JFXButton btnDeleteStudent;

    @FXML
    void onAddStudent(ActionEvent event) {

    }

    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onEdit(ActionEvent event) {

    }
    
    private ResultSet tableData, deptList;
	private TreeItem<Student> tableItem;
	private ObservableList<String> deptName;
	private HashMap<String, String> deptMap;
    
    private void loadDeptList() {
		deptList = DbHandler.execQuery("SELECT name, deptID FROM topicS.Department");
	}

	private void loadDB() {
		tableData = DbHandler.execQuery("SELECT studentID, fName, lName, birthday, deptID FROM topicS.Student");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadDB();
		loadDeptList();

		studentID.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
						return param.getValue().getValue().studentID;
					}
				});

		fName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
						return param.getValue().getValue().fName;
					}
				});

		lName.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
						return param.getValue().getValue().lName;
					}
				});

		dateOfBirth.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
						return param.getValue().getValue().birthday;
					}
				});

		deptID.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Student, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Student, String> param) {
						return param.getValue().getValue().deptID;
					}
				});

		
		updateTableView(tableData);
		updateChoiceBoxView();
		
		txtFieldSearch.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String filter) {

				tableView.setPredicate(new Predicate<TreeItem<Student>>() {
					@Override
					public boolean test(TreeItem<Student> t) {
						Boolean flag = t.getValue().studentID.getValue().contains(filter) || 
									   t.getValue().fName.getValue().contains(filter) ||
									   t.getValue().lName.getValue().contains(filter);
						return flag;
					}
				});
			}
		});    
		
	}
	
	private void updateTableView(ResultSet inputResult) {
		ObservableList<Student> students = FXCollections.observableArrayList();

		try {
			while (inputResult.next()) {
				String _studentId = inputResult.getString("studentID");
				String _fName = inputResult.getString("fName");
				String _lName = inputResult.getString("lName");
				Date _birthday = inputResult.getDate("birthday");
				String _deptID = inputResult.getString("deptID");
				students.add(new Student(_studentId, _fName, _lName, _birthday, _deptID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		tableItem = new RecursiveTreeItem<Student>(students, RecursiveTreeObject::getChildren);
//		tableView.getColumns().setAll(studentID, fName, name, beginDate, endDate, fee, numberOfCredits, room,
//				maxSlot);
		tableView.setRoot(tableItem);
		tableView.setShowRoot(false);
	}
	
	private void updateChoiceBoxView() {
		deptName = FXCollections.observableArrayList();
		deptMap = new HashMap<>();
		deptName.add("--All--");
		try {
			while (deptList.next()) {
				String dept = deptList.getString("name");
				String deptID = deptList.getString("deptID");
				deptMap.put(dept, deptID);
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
					tableData = DbHandler.execQuery("SELECT studentID, fName, lName, birthday, deptID FROM topicS.Student WHERE deptID='"+deptMap.get(chBoxDepartPick.getItems().get(number2.intValue()))+"'");
				} else {
					tableData = DbHandler.execQuery("SELECT studentID, fName, lName, birthday, deptID FROM topicS.Student");
				}
				updateTableView(tableData);
			}
		});
	}

}
