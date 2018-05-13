package hcmiuiot.DB_CollegeManager.App;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable{
	private Label label;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		handleValidation();
        imgProgress.setVisible(false);
	}
	
	@FXML
    private void onLogin(ActionEvent event) {

        imgProgress.setVisible(true);
        
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(3));      
        
        if (DbHandler.login(username, password) != null) {
        	pauseTransition.setOnFinished(ev -> {
	            completeLogin(); 
        	});
        } else {
        	
        	pauseTransition.setOnFinished(ev -> {
        		Platform.runLater(() -> {
        			Alert al = new Alert(AlertType.ERROR);
                	al.setContentText("Login failed!");
                	al.showAndWait();
                	imgProgress.setVisible(false);
        		});
        		
        	});
        	
        }
 
        pauseTransition.play();
    }
	

    private void handleValidation() {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Input required");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtUsername.getValidators().add(fieldValidator);
        txtUsername.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) -> {
            if (!newVal) {
                txtUsername.validate();

            }
        });
        RequiredFieldValidator fieldValidator2 = new RequiredFieldValidator();
        fieldValidator2.setMessage("Input required");
        fieldValidator2.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtPassword.getValidators().add(fieldValidator2);
        txtPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtPassword.validate();
            }
        });

    }

    private void completeLogin() {
    	imgProgress.setVisible(false);
        btnLogin.getScene().getWindow().hide();
        try {
            imgProgress.setVisible(false);
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
