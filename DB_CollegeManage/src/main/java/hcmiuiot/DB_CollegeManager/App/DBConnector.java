package hcmiuiot.DB_CollegeManager.App;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnector {

	public Connection connectDB() {
		/// Driver
		try {
			String url = "jdbc:mysql://db.hcmiuiot.club/";
			String username = "dbproject";
			String password = "1.Dbproject";

			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection(url, username, password);
			return connect;
		} catch (ClassNotFoundException | SQLException e) {
			Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}
}
