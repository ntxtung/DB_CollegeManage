package hcmiuiot.DB_CollegeManager.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DashboardController implements Initializable {
	
	@FXML
    private AnchorPane stackPane;

    @FXML
    private JFXToolbar toolBar;

    @FXML
    private AnchorPane sideAnchor;

    @FXML
    private JFXButton btnCourseManager;

    @FXML
    private JFXButton btnDepartmentManager;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnClose;

    @FXML
    private AnchorPane holderPane;
    
    private AnchorPane deptPage, coursePage, studentPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPages();
    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(200));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void fixedBorderAnchor(AnchorPane pane) {
    	holderPane.setLeftAnchor(pane, 0.0);
    	holderPane.setRightAnchor(pane, 0.0);
    	holderPane.setTopAnchor(pane, 0.0);
    	holderPane.setBottomAnchor(pane, 0.0);
    }
    
    private void createPages() {
        try {
            deptPage = FXMLLoader.load(getClass().getResource("DeptInstView.fxml"));
            coursePage = FXMLLoader.load(getClass().getResource("CourseManage.fxml"));
            studentPage = FXMLLoader.load(getClass().getResource("StudentManage.fxml"));
            fixedBorderAnchor(deptPage);
            fixedBorderAnchor(coursePage);
            fixedBorderAnchor(studentPage);
            setNode(deptPage);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @FXML
    void onCourseManager(ActionEvent event) {
    	setNode(coursePage);
    }

    @FXML
    void onDepartmentManager(ActionEvent event) {
    	setNode(deptPage);
    }
    @FXML
    void onStudentManager(ActionEvent event) {
    	setNode(studentPage);
    }


    @FXML
    private void onLogOut(ActionEvent event) {
    	btnLogout.getScene().getWindow().hide();
    	try {
    		Stage dashboardStage = new Stage();
    		dashboardStage.setTitle("");
    		Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
    		Scene scene = new Scene(root);
    		dashboardStage.setScene(scene);
    		dashboardStage.show();
    	} catch (IOException ex) {
    		Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }

    @FXML
    private void onExit(ActionEvent event) {
        Platform.exit();
    }

}
