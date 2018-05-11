package hcmiuiot.DB_CollegeManager.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXToolbar;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DashboardController implements Initializable {
	
	@FXML
    private Label lblDash;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private AnchorPane sideAnchor;
    @FXML
    private Label lblMenu;
    @FXML
    private JFXToolbar toolBar;
    @FXML
    private HBox toolBarRight;
    @FXML
    private VBox overflowContainer;
    @FXML
    private ToggleButton menuHome;
    @FXML
    private ToggleButton menuAdd;
    @FXML
    private ToggleButton menuList;
    @FXML
    private ToggleButton menuLogg;

    private Parent home, add, list;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private JFXButton btnExit;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXRippler fXRippler = new JFXRippler(lblDash);
        JFXRippler fXRippler2 = new JFXRippler(lblMenu);
        fXRippler2.setMaskType((JFXRippler.RipplerMask.RECT));
        sideAnchor.getChildren().add(fXRippler);
        toolBarRight.getChildren().add(fXRippler2);
        createPages();

    }

    //Set selected node to a content holder
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

    private void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("Overview.fxml"));
            add = FXMLLoader.load(getClass().getResource("Register.fxml"));
            list = FXMLLoader.load(getClass().getResource("Register.fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void openHome(ActionEvent event) {
        setNode(home);
    }

    @FXML
    private void openAddStudent(ActionEvent event) {
        setNode(add);
    }

    @FXML
    private void openListStudent(ActionEvent event) {
        setNode(list);
    }

    @FXML
    private void logOff(ActionEvent event) {

    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
