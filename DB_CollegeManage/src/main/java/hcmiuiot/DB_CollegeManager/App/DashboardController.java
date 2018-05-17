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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private JFXButton btnHome;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnClose;

    @FXML
    private AnchorPane holderPane;
    
    private AnchorPane home, add, list;

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
            home = FXMLLoader.load(getClass().getResource("DeptInstView.fxml"));
            add = FXMLLoader.load(getClass().getResource("CourseManage.fxml"));
            list = FXMLLoader.load(getClass().getResource("StudentManage.fxml"));
            fixedBorderAnchor(home);
            fixedBorderAnchor(add);
            fixedBorderAnchor(list);
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onOpenHome(ActionEvent event) {
        setNode(home);
    }

    @FXML
    private void onOpenAddStudent(ActionEvent event) {
        setNode(add);
    }

    @FXML
    private void onOpenListStudent(ActionEvent event) {
        setNode(list);
    }

    @FXML
    private void onLogOff(ActionEvent event) {
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
