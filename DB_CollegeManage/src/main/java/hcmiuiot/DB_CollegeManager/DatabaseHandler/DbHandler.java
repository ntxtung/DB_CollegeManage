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
    	return instance;
    }
    
    public static DbHandler login(String username, String password) {
    	String ConnectionString = "jdbc:mysql://" + Configs.dbHostname + ":" + Configs.dbPort + "/" + Configs.dbName;
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(ConnectionString, username, password);
            statement = conn.createStatement();
            instance = new DbHandler();
        } catch (Exception e) {
        	System.err.println(e.getMessage());
        	instance = null;
        }
        return instance;
    }
    
    public static ResultSet execSQL(String sql) {
    	try {
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
    	return null;
    }

}
