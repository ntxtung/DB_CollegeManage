package hcmiuiot.DB_CollegeManager.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.Blob;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

public class StudentRegisterController implements Initializable {

	@FXML
	private JFXTextField txtFirstName;

	@FXML
	private JFXTextField txtLastName;

	@FXML
	private JFXTextField txtid;

	@FXML
	private JFXPasswordField txtpwd;

	@FXML
	private JFXDatePicker txtBirthday;

	@FXML
	private JFXComboBox<String> txtDept;

	@FXML
	private ImageView img;

	HashMap<String, String> deptMap;

	private File imgFile = null;

	@FXML
	void onBrowseIMG(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.jpg;*.bmp;*.png"));
		fileChooser.setTitle("Browse image");
		imgFile = fileChooser.showOpenDialog(img.getScene().getWindow());
		try {
			if (imgFile != null) {
				img.setImage(new Image(new FileInputStream(imgFile)));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onCreate(ActionEvent event) {
		PreparedStatement st = DbHandler.getInstance().getPreparedStatement(
				"INSERT INTO Student(studentID, fName, lName, birthday, password, img, deptID) VALUES "
						+ "(?,?,?,?,?,?,?)");
		try {
			if (txtid != null && txtFirstName != null && txtLastName != null && txtBirthday.getValue() != null
					&& txtpwd != null && deptMap.get(txtDept.getSelectionModel().getSelectedItem()) != null) {

				st.setString(1, txtid.getText());
				st.setString(2, txtFirstName.getText());
				st.setString(3, txtLastName.getText());
				st.setDate(4, Date.valueOf(txtBirthday.getValue()));
				st.setString(5, txtpwd.getText());
				st.setString(7, deptMap.get(txtDept.getSelectionModel().getSelectedItem()));
				if (imgFile != null)
					st.setBinaryStream(6, new FileInputStream(imgFile));
				else 
					st.setNull(6, Types.BLOB);
				if (st.executeUpdate() != 0) {
					Alert al = new Alert(AlertType.INFORMATION);
					al.setContentText("Add new student successfully!");
					al.showAndWait();
				}
			}

			else {
				Alert al2 = new Alert(AlertType.ERROR);
				al2.setContentText("Fail to add student");
				al2.showAndWait();
			}

		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				ResultSet rs = DbHandler.getInstance().execQuery("SELECT name, deptID FROM Department");
				deptMap = new HashMap<>();
				txtDept.getItems().clear();
				try {
					while (rs.next()) {
						String deptID = rs.getString("deptID");
						String deptName = rs.getString("name");
						txtDept.getItems().add(deptName);
						deptMap.put(deptName, deptID);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (txtDept.getItems().size() > 0)
					txtDept.getSelectionModel().select(0);
			}
		}).start();
		;

		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		};
		txtBirthday.setConverter(converter);
	}

}
