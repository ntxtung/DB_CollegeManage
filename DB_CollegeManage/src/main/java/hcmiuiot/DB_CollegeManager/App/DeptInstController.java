package hcmiuiot.DB_CollegeManager.App;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXSpinner;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
//import javafx.scene.control.ScrollBarPolicy;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
/*
 *  Control Department & Instructor Form
 */
public class DeptInstController implements Initializable {
	
	@FXML
	private JFXComboBox<String> cb;
	@FXML
	private ImageView logo;
	@FXML
	private ImageView avatar;
	@FXML 
	private Text name;
	@FXML
	private Text email;
	@FXML
	private Text phone;
	@FXML
	private JFXSpinner spinnerLoading;
	@FXML
	private ScrollPane instScroll;
	@FXML
	private FlowPane instFlow;
	@FXML
	private JFXButton loadLec;
	
	final ObservableList<String> options = FXCollections.observableArrayList();
	final HashMap<String, Department> dataDept = new HashMap<String, Department>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadCombo(null);
	}
	
	@FXML
	private void loadCombo(ActionEvent event) {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				Platform.runLater(() -> {
					spinnerLoading.setVisible(true);	
				});
				
				ResultSet rsDept = DbHandler.getInstance().execQuery("SELECT deptID, name,mail,phone,logo, head_id FROM topicS.Department");
				
				try {
					
					while (rsDept.next()) {
						
						String deptName = rsDept.getString("name");
						String deptID = rsDept.getString("deptID");
						String mail = rsDept.getString("mail");
						String phone = rsDept.getString("phone");
						String headID = rsDept.getString("head_id");
						Instructor head = null;
						Image logo = DbHandler.getInstance().convertBlob2Image(rsDept.getBlob("logo"));
						ArrayList<Instructor> instructors = new ArrayList<>();
						ResultSet rsInstructors = DbHandler.getInstance().execQuery("SELECT * FROM topicS.Instructor WHERE deptID='"+deptID+"'");
						
						while (rsInstructors.next()) {
							String insID = rsInstructors.getString("instructorID");
							String insName = rsInstructors.getString("fName") + " " + rsInstructors.getString("lName");
							Date insBirthday = rsInstructors.getDate("birthday");
							String degreeID = rsInstructors.getString("degreeID");
							String degreeStr = "";
							String insMail = rsInstructors.getString("mail");
							String insPhone = rsInstructors.getString("phone");
							Image img = DbHandler.convertBlob2Image(rsInstructors.getBlob("img"));
							Date accessionDate = rsInstructors.getDate("accession_date");
							String insdeptID = rsInstructors.getString("deptID");
							
							Instructor ins = new Instructor(insID, insName, insBirthday, degreeID, degreeStr, insMail, insPhone, img, accessionDate, insdeptID);
							instructors.add(ins);
							if (headID.equals(insID)) {
								head = ins;
							}
						}
						
						Department dept = new Department(deptID, mail, phone, logo, headID, head, instructors);
						
						dataDept.put(deptName, dept);			
					}
					
					options.addAll(dataDept.keySet());
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				Platform.runLater(() -> {
					cb.getSelectionModel().selectedItemProperty()
					.addListener(new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> observable,
								String oldValue, String selectedValue) {
							name.setText(selectedValue);
							if (selectedValue != null) {
								email.setText(dataDept.get(selectedValue).getMail());
								phone.setText(dataDept.get(selectedValue).getPhone());
								logo.setImage(dataDept.get(selectedValue).getLogo());
								
								instFlow.getChildren().clear();
								instScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
								instScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
								
								for (Instructor instructor : dataDept.get(selectedValue).getInstructorList()) 
								{
									// add each of instructor
									FXMLLoader loader = new FXMLLoader(getClass().getResource("LectureView.fxml"));
									try {
										AnchorPane lecturePane = loader.load();
										LectureViewController controller = loader.getController();
										controller.setAvatar(instructor.getImg());
										controller.setName(instructor.getName());
										controller.setEmail(instructor.getMail());
										controller.setPhone(instructor.getPhone());
										instFlow.getChildren().add(lecturePane);
										if(dataDept.get(selectedValue).getHeadID().equals(instructor.id))
										{
											controller.setSpecify("Dean");
										}
										else
										{
											controller.setSpecify("Lecture");
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								System.out.println(instFlow.getChildren().size());
							}

						}
					});
					
					cb.setItems(options);
					cb.setValue(null);
					cb.getSelectionModel().select(0);	
					
					
				});
				Platform.runLater(() -> {
					spinnerLoading.setVisible(false);	
				});
			}
			
		}).start();
		
	}
	
	
	public void loadRoundImage(Circle clip,ImageView imageView,double radius) {
		clip = new Circle(imageView.getFitWidth(),imageView.getFitHeight(),radius,Color.WHITE);
		clip.setVisible(true);
		imageView.setClip(clip);
	}
	
	class Department {
		
		String deptID;
		String mail;
		String phone;
		Image logo;
		String headID;
		Instructor head;
		ArrayList<Instructor> instructors = new ArrayList<>();

		public Department(String deptID, String mail, String phone, Image logo, String headID, Instructor head, ArrayList<Instructor> instructors) {
			super();
			this.deptID = deptID;
			this.mail = mail;
			this.phone = phone;
			this.logo = logo;
			this.headID = headID;
			this.head = head;
			this.instructors = instructors;
		}
		public String getDeptID() {return deptID;}
		public void setDeptID(String deptID) {this.deptID = deptID;}
		public Instructor getHead() {return head;}
		public void setHead(Instructor head) {this.head = head;}
		public String getHeadID() {return headID;}
		public void setHeadID(String headID) {this.headID = headID;}
		public String getMail() {return mail;}
		public void setMail(String mail) {this.mail = mail;}
		public String getPhone() {return phone;}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Image getLogo() {return logo;}
		public void setLogo(Image logo) {this.logo = logo;}
		public ArrayList<Instructor> getInstructorList() {return this.instructors;}
		public void getInstructorList(ArrayList<Instructor> instructorList) {this.instructors = instructorList;}
		
	}
	
	class Instructor{
		String id;
		String name;
		Date birthday;
		String degreeID;
		String degreeStr;
		String mail;
		String phone;
		Image img;
		Date accessionDate;
		String deptID;
		public Instructor(String id, String name, Date birthday, String degreeID, String degreeStr, String mail,
				String phone, Image img, Date accessionDate, String deptID) {
			super();
			this.id = id;
			this.name = name;
			this.birthday = birthday;
			this.degreeID = degreeID;
			this.degreeStr = degreeStr;
			this.mail = mail;
			this.phone = phone;
			this.img = img;
			this.accessionDate = accessionDate;
			this.deptID = deptID;
		}
		public String getId() {return id;}
		public void setId(String id) {this.id = id;}
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		public Date getBirthday() {return birthday;}
		public void setBirthday(Date birthday) {this.birthday = birthday;}
		public String getDegreeID() {return degreeID;}
		public void setDegreeID(String degreeID) {this.degreeID = degreeID;}
		public String getDegreeStr() {return degreeStr;}
		public void setDegreeStr(String degreeStr) {this.degreeStr = degreeStr;}
		public String getMail() {return mail;}
		public void setMail(String mail) {this.mail = mail;}
		public String getPhone() {return phone;}
		public void setPhone(String phone) {this.phone = phone;}
		public Image getImg() {return img;}
		public void setImg(Image img) {this.img = img;}
		public Date getAccessionDate() {return accessionDate;}
		public void setAccessionDate(Date accessionDate) {this.accessionDate = accessionDate;}
		public String getDeptID() {return deptID;}
		public void setDeptID(String deptID) {this.deptID = deptID;}	
		
	}

}
