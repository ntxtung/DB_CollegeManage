package hcmiuiot.DB_CollegeManage.Extensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;

public class ExportUtils {
	
	public static boolean exportResultSet(String sql, DbHandler dbHandler, String fileName) {
//		ResultSet rs = dbHandler.getInstance().execSQL(sql);
//		try {
//			ResultSetMetaData meta = rs.getMetaData();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
		
		File exportFile = new File(fileName);	
		
		FileInputStream fis;
		try {
			///fis = new FileInputStream(exportFile);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("test sheet");
			
			Map<String, Object[]> data = new HashMap<String, Object[]>(); 
			data.put("7", new Object[] {7d, "Sonya", "75K", "SALES", "Rupert"}); 
			data.put("8", new Object[] {8d, "Kris", "85K", "SALES", "Rupert"}); 
			data.put("9", new Object[] {9d, "Dave", "90K", "SALES", "Rupert"}); 
			
			Set<String> newRows = data.keySet(); 
			int rownum = sheet.getLastRowNum(); 
			for (String key : newRows) { 
				Row row = sheet.createRow(rownum++); 
				Object [] objArr = data.get(key); 
				int cellnum = 0; 
				for (Object obj : objArr) { 
					Cell cell = row.createCell(cellnum++); 
					if (obj instanceof String) { 
						cell.setCellValue((String) obj); 
					} 
					else if (obj instanceof Boolean) { 
						cell.setCellValue((Boolean) obj); 
					} 
					else if (obj instanceof Date) { 
						cell.setCellValue((Date) obj); 
					} 
					else if (obj instanceof Double) { 
						cell.setCellValue((Double) obj); 
					} 
				} 
			}
			
			
			workbook.write(new FileOutputStream(exportFile));
			System.out.println("> Export to \""+fileName+"\" completed!");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
			
		
		
		return false;
	}
	
}
