package hcmiuiot.DB_CollegeManager.DatabaseHandler;

import java.sql.Statement;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHandler {

	private static DbHandler instance;
  private static Connection conn;
    
	public static DbHandler getInstance() {
		return instance;
	}

	}
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
	
	 public static Image convertBlob2Image (Blob blob) {
		     	byte[] byteImage = null;
		     	if (blob != null)
		 			try {
		 				byteImage = blob.getBytes(1,(int)blob.length());
		 				return new Image(new ByteArrayInputStream(byteImage)); 
		 			} catch (SQLException e) {
		 				e.printStackTrace();
		 				return null;
		 			}
		     	return null;
		     }

	public PreparedStatement getPreparedStatement(String sql) {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
