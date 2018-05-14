package hcmiuiot.DB_CollegeManager.App;

import hcmiuiot.DB_CollegeManage.Extensions.ExportUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ExportUtils.exportResultSet("", null, "hello.xlsx");
		Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("College Database System");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
