package hcmiuiot.DB_CollegeManager.DatabaseHandler;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHandler {
	
	private static DbHandler instance;

    private static Connection conn;
    private static Statement statement;
    
    public static DbHandler getInstance() {
    	if (instance == null) {
    		instance = new DbHandler();
    		instance.getConnection();
    	}
    	return instance;
    }

    public static Connection getConnection() {
        String ConnectionString = "jdbc:mysql://" + Configs.dbHostname + ":" + Configs.dbPort + "/" + Configs.dbName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ConnectionString, Configs.dbUsername, Configs.dbPassword);
            statement = conn.createStatement();
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        }
        return conn;
    }
    
    public static ResultSet sqlExecute(String sql) {
    	try {
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return null;
    }

}
