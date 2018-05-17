package hcmiuiot.DB_CollegeManager.App;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
/*
 *  Control Department & Instructor Form
 */
public class DeptInstController implements Initializable {
	
	@FXML
	private JFXComboBox<String> cb;
	@FXML
	private ImageView logo;
	@FXML
	private ImageView dean;
	@FXML
	private ImageView l1;
	@FXML
	private ImageView l2;
	@FXML
	private ImageView l3;
	@FXML
	private ImageView l4;
	@FXML 
	private Text name;
	@FXML
	private Text email;
	@FXML
	private Text phone;
	
	final ObservableList<String> options = FXCollections.observableArrayList();
	final HashMap<String, Department> dataDept = new HashMap<String, Department>();
	final HashMap<String, Instructor> dataInst = new HashMap<String, Instructor>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		loadCombo(null);
	}
	
	private void loadCombo(ActionEvent event) {
		
		ResultSet rsDept = DbHandler.getInstance().execQuery("SELECT name,mail,phone,logo FROM topicS.Department");
		ResultSet rsDean = DbHandler.getInstance().execQuery("SELECT Department.*, Instructor.img FROM topicS.Instructor,topicS.Department WHERE Department.head_id= Instructor.instructorID AND Department.deptID = Instructor.deptID");
		ResultSet rsInst = DbHandler.getInstance().execQuery("SELECT Department.*, Instructor.img FROM topicS.Instructor,topicS.Department WHERE Department.deptID = Instructor.deptID AND Department.head_id <> instructorID");
		try {
			while (rsDept.next() && rsDean.next() && rsInst.next()) {
				String deptName = rsDept.getString("name");
				dataDept.put(deptName, new Department(rsDept.getString("mail"), rsDept.getString("phone"), DbHandler.convertBlob2Image(rsDept.getBlob("logo"))));
				dataInst.put(deptName, new Instructor(DbHandler.convertBlob2Image(rsDean.getBlob("img")), 
													  DbHandler.convertBlob2Image(rsInst.getBlob("img")), 
													  DbHandler.convertBlob2Image(rsInst.getBlob("img")), 
													  DbHandler.convertBlob2Image(rsInst.getBlob("img")),
													  DbHandler.convertBlob2Image(rsInst.getBlob("img"))));
				options.add(deptName);
				cb.getSelectionModel().selectedItemProperty()
			    .addListener(new ChangeListener<String>() {
			        public void changed(ObservableValue<? extends String> observable,
			                            String oldValue, String newValue) {
			            name.setText(newValue);
			            if (newValue != null) {
			            	email.setText(dataDept.get(newValue).getMail());
			            	phone.setText(dataDept.get(newValue).getPhone());
			            	logo.setImage(dataDept.get(newValue).getLogo());
			            	dean.setImage(dataInst.get(newValue).getDean());
			            	l1.setImage(dataInst.get(newValue).getLecture1());
			            	l2.setImage(dataInst.get(newValue).getLecture2());
			            	l3.setImage(dataInst.get(newValue).getLecture3());
			            	l4.setImage(dataInst.get(newValue).getLecture4());
			            }
			        }
			    });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cb.setItems(options);
		cb.setValue(null);
		cb.getSelectionModel().select(0);
	}
	
	class Department{
		String mail;
		String phone;
		Image logo;
		/**
		 * @param mail
		 * @param phone
		 * @param logo
		 */
		public Department(String mail, String phone, Image logo) {
			super();
			this.mail = mail;
			this.phone = phone;
			this.logo = logo;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Image getLogo() {
			return logo;
		}
		public void setLogo(Image logo) {
			this.logo = logo;
		}
		
	}
	
	class Instructor{
		Image dean;
		Image lecture1;
		Image lecture2;
		Image lecture3;
		Image lecture4;
		/**
		 * @param dean
		 * @param lecture1
		 * @param lecture2
		 * @param lecture3
		 * @param lecture4
		 */
		public Instructor(Image dean, Image lecture1, Image lecture2, Image lecture3, Image lecture4) {
			super();
			this.dean = dean;
			this.lecture1 = lecture1;
			this.lecture2 = lecture2;
			this.lecture3 = lecture3;
			this.lecture4 = lecture4;
		}
		public Image getDean() {
			return dean;
		}
		public void setDean(Image dean) {
			this.dean = dean;
		}
		public Image getLecture1() {
			return lecture1;
		}
		public void setLecture1(Image lecture1) {
			this.lecture1 = lecture1;
		}
		public Image getLecture2() {
			return lecture2;
		}
		public void setLecture2(Image lecture2) {
			this.lecture2 = lecture2;
		}
		public Image getLecture3() {
			return lecture3;
		}
		public void setLecture3(Image lecture3) {
			this.lecture3 = lecture3;
		}
		public Image getLecture4() {
			return lecture4;
		}
		public void setLecture4(Image lecture4) {
			this.lecture4 = lecture4;
		}
	}
}
