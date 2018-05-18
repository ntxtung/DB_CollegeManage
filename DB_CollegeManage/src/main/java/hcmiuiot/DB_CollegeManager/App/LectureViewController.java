package hcmiuiot.DB_CollegeManager.App;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LectureViewController {
	@FXML
	private ImageView avt;
	@FXML
	private Text txtEmail;
	@FXML
	private Text txtPhone;
	@FXML
	private Text txtName;
	@FXML
	private Text txtSpec;

	public void setAvatar(Image image) {
		avt.setImage(image);
	}
	
	public void setName(String name) {
		txtName.setText(name);
	}
	
	public void setPhone(String phone) {
		txtPhone.setText(phone);
	}
	
	public void setEmail(String email) {
		txtEmail.setText(email);
	}
	
	public void setSpecify(String specify) {
		txtSpec.setText(specify);
	}
}
