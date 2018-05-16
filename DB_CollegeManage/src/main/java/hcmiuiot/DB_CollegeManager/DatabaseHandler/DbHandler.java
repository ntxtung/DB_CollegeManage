package hcmiuiot.DB_CollegeManager.DatabaseHandler;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHandler {

	private static DbHandler instance;

	private static Connection conn;

	public static DbHandler getInstance() {
		return instance;
	}

	public static DbHandler login(String username, String password) {
		Statement statement;
		String ConnectionString = "jdbc:mysql://" + Configs.dbHostname + ":" + Configs.dbPort + "/" + Configs.dbName;
		try {
			// Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(ConnectionString, username, password);
			statement = conn.createStatement();
			instance = new DbHandler();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			instance = null;
		}
		return instance;
	}

	public static ResultSet execQuery(String sql) {
		Statement statement;
		try {
			statement = conn.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public static int execUpdate(String sql) {
		Statement statement;
		try {
			statement = conn.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return 0;
	}

}
