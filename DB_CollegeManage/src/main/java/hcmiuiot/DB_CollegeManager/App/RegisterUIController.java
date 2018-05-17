package hcmiuiot.DB_CollegeManager.App;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class RegisterUIController implements Initializable {

    @FXML
    private FlowPane main;
    @FXML
    private JFXTreeTableView<Course> treeView;
    private DBConnector db;
	private ResultSet result,available;
	private ObservableList<Course> Courses1 = FXCollections.observableArrayList();

    @FXML
    private JFXButton addCourse;

    @FXML
    private JFXTreeTableView<Course> treeView1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	db = new DBConnector();
		loadDB();
		
		// T  A  B  L  E  1  //
//=================================================================================================================================================//		
        JFXTreeTableColumn<Course, String> courseID = new JFXTreeTableColumn<>("CourseID");
        courseID.setPrefWidth(150);
        courseID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().courseID;
            }
        });

        JFXTreeTableColumn<Course, String> departmentID = new JFXTreeTableColumn<>("Department");
        departmentID.setPrefWidth(150);
        departmentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Course, String> Name = new JFXTreeTableColumn<>("Name");
        Name.setPrefWidth(150);
        Name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().deptID;
            }
        });
        
        
        JFXTreeTableColumn<Course, String> BeginDate = new JFXTreeTableColumn<>("Begin Date");
        BeginDate.setPrefWidth(150);
        BeginDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().beginDate;
            }
        });
        
        JFXTreeTableColumn<Course, String> EndDate = new JFXTreeTableColumn<>("End Date");
        EndDate.setPrefWidth(150);
        EndDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().endDate;
            }
        });
        
        JFXTreeTableColumn<Course, Number> Fee = new JFXTreeTableColumn<>("Fee");
        Fee.setPrefWidth(150);
        Fee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().fee;
            }
        });
        
        JFXTreeTableColumn<Course, Number> NumberOfCredits = new JFXTreeTableColumn<>("Number of Credits");
        NumberOfCredits.setPrefWidth(150);
        NumberOfCredits.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().numberOfCredit;
            }
        });
        
        JFXTreeTableColumn<Course, Number> MaxSlot = new JFXTreeTableColumn<>("Max Slot");
        MaxSlot.setPrefWidth(150);
        MaxSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().maxSlot;
            }
        });
        
        JFXTreeTableColumn<Course, Number> AvailableSlot = new JFXTreeTableColumn<>("Available Slot");
        AvailableSlot.setPrefWidth(150);
        AvailableSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().availableSlot;
            }
        });
        
        JFXTreeTableColumn<Course, String> Room = new JFXTreeTableColumn<>("Room");
        Room.setPrefWidth(150);
        Room.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().room;
            }
        });
        
        
        ObservableList<Course> Courses = FXCollections.observableArrayList();
//       Courses.add(new Course("Computer courseID", "23", "CD 1"));
//       Courses.add(new Course("Computer courseID", "23", "CD 1"));
//       Courses.add(new Course("Computer courseID", "23", "CD 1"));
//       Courses.add(new Course("Computer courseID", "23", "CD 1"));
//       Courses.add(new Course("Computer courseID", "23", "CD 1"));
//       Courses.add(new Course("Computer courseID", "23", "CD 1"));
        Connection connect = db.connectDB();
        try {
			for(int i = 0 ; i <= result.getMetaData().getColumnCount() ; i++) {
				String courseid = result.getString("courseID");
				String deptid = result.getString("deptID");
				String name = result.getString("name");
				String begin_date = result.getString("begin_date");
				String end_date = result.getString("end_date");
				double fee = result.getDouble("fee");
				int number_of_credits = result.getInt("num_of_credits");
				int max_slot = result.getInt("max_slot");
				String room = result.getString("room");
				available = connect.createStatement().executeQuery("select topicS.GetAvaSlot('"+courseid+"')");
				available.first();
				int available_slot = available.getInt("topicS.GetAvaSlot('"+courseid+"')");
			Courses.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,available_slot,room));
			result.next();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        

        final TreeItem<Course> root = new RecursiveTreeItem<Course>(Courses, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(courseID, departmentID, Name,BeginDate,EndDate,Fee,NumberOfCredits,MaxSlot,AvailableSlot,Room);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
//===================================================================================================================================================================================//
        // T  A  B  L E 2 //
        
        
        JFXTreeTableColumn<Course, String> selectedCourseID = new JFXTreeTableColumn<>("CourseID");
        selectedCourseID.setPrefWidth(150);
        selectedCourseID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().courseID;
            }
        });

        JFXTreeTableColumn<Course, String> selectedDepartmentID = new JFXTreeTableColumn<>("Department");
        selectedDepartmentID.setPrefWidth(150);
        selectedDepartmentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Course, String> selectedName = new JFXTreeTableColumn<>("Name");
        selectedName.setPrefWidth(150);
        selectedName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().deptID;
            }
        });
        
        
        JFXTreeTableColumn<Course, String> selectedBeginDate = new JFXTreeTableColumn<>("Begin Date");
        selectedBeginDate.setPrefWidth(150);
        selectedBeginDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().beginDate;
            }
        });
        
        JFXTreeTableColumn<Course, String> selectedEndDate = new JFXTreeTableColumn<>("End Date");
        selectedEndDate.setPrefWidth(150);
        selectedEndDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().endDate;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedFee = new JFXTreeTableColumn<>("Fee");
        selectedFee.setPrefWidth(150);
        selectedFee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().fee;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedNumberOfCredits = new JFXTreeTableColumn<>("Number of Credits");
        selectedNumberOfCredits.setPrefWidth(150);
        selectedNumberOfCredits.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().numberOfCredit;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedMaxSlot = new JFXTreeTableColumn<>("Max Slot");
        selectedMaxSlot.setPrefWidth(150);
        selectedMaxSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().maxSlot;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedAvailableSlot = new JFXTreeTableColumn<>("Available Slot");
        selectedAvailableSlot.setPrefWidth(150);
        selectedAvailableSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().availableSlot;
            }
        });
        
        JFXTreeTableColumn<Course, String> selectedRoom = new JFXTreeTableColumn<>("Room");
        selectedRoom.setPrefWidth(150);
        selectedRoom.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().room;
            }
        });

        //ObservableList<Course> Courses1 = FXCollections.observableArrayList();
       
        treeView1.getColumns().setAll(selectedCourseID, selectedDepartmentID, selectedName,selectedBeginDate,selectedEndDate,selectedFee,selectedNumberOfCredits,selectedMaxSlot,selectedAvailableSlot,selectedRoom);
       
        
        
    }
	
	

    @FXML
    private void filter(ActionEvent event) {
    }
    

    @FXML
    void addCourse(MouseEvent event) {
TreeItem<Course> selection = treeView.getSelectionModel().getSelectedItem();
    	System.out.println("ĐMCS");
    	if (selection != null) {
    		 
    		 String courseid = selection.getValue().getCourseID();
				String deptid = selection.getValue().getDepartmentID();
				String name = selection.getValue().getDepartmentID();
				String begin_date = selection.getValue().getDepartmentID();
				String end_date = selection.getValue().getDepartmentID();
				double fee = selection.getValue().getFee();
				int number_of_credits = selection.getValue().getNumberOfCredit();
				int max_slot = selection.getValue().getMaxSlot();
				String room = selection.getValue().getRoom();
				int available_slot =selection.getValue().getAvailableSlot();
			Courses1.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,available_slot,room));
			 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
			 treeView1.setRoot(root1);
		        treeView1.setShowRoot(false);
    		
    	}
    }
    
    public void loadDB() {
		try {
			Connection connect = db.connectDB();
			result = connect.createStatement().executeQuery("SELECT * FROM topicS.Course");
			result.first();
			}
		 catch (SQLException e) {
			e.printStackTrace();
		}	
    }

    class Course extends RecursiveTreeObject<Course> {

        StringProperty deptID;
        StringProperty courseID;
        StringProperty name;
        StringProperty beginDate;
        StringProperty endDate;
        ObservableValue<Number> fee;
        IntegerProperty numberOfCredit;
        IntegerProperty maxSlot;
        StringProperty room;
        IntegerProperty availableSlot;
        
        
        public Course(String courseID,String deptID,String name,String beginDate,String endDate, double fee, int numberOfCredit,int maxSlot,int availableSlot,String room) {
            this.courseID = new SimpleStringProperty(courseID);
            this.deptID = new SimpleStringProperty(deptID);
            this.name = new SimpleStringProperty(name);
            this.beginDate = new SimpleStringProperty(beginDate);
            this.endDate = new SimpleStringProperty(endDate);
            this.fee = new SimpleDoubleProperty(fee);
            this.numberOfCredit = new SimpleIntegerProperty(numberOfCredit);
            this.maxSlot = new SimpleIntegerProperty(maxSlot);
            this.availableSlot = new SimpleIntegerProperty(availableSlot);
            this.room = new SimpleStringProperty(room);
        }
        public String getCourseID () {
        	return courseID.get();
        }
        public String getDepartmentID () {
        	return deptID.get();
        }
        public String getName () {
        	return name.get();
        }
        public String getBeginDate() {
        	return beginDate.get();
        }
        public String getEndDate() {
        	return endDate.get();
        }
        public  double getFee () {
        	return  (double) fee.getValue();
        }
        public int getNumberOfCredit  () {
        	return numberOfCredit.get();
        }
        public int getMaxSlot  () {
        	return maxSlot.get();
        }
        public int getAvailableSlot  () {
        	return availableSlot.get();
        }
        public String getRoom () {
        	return room.get();
        }

    }
    
   
}
