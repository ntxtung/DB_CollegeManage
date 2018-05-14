package hcmiuiot.DB_CollegeManage.Extensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.cj.xdevapi.Type;

import hcmiuiot.DB_CollegeManager.DatabaseHandler.DbHandler;

public class ExportUtils {
	
	public static boolean exportResultSet(String sql, DbHandler dbHandler, String fileName) {
		
		ResultSet rs = dbHandler.execSQL(sql);
		try {
			ResultSetMetaData meta = rs.getMetaData();
			
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("HCMIU");
			
			Row titleRow = sheet.createRow(0);
			int colsCount = meta.getColumnCount();
			int[] columnType = new int[colsCount];
			
			for (int i = 0; i < colsCount; i++) {
				titleRow.createCell(i, CellType.STRING).setCellValue(meta.getColumnName(i+1));
				columnType[i] = meta.getColumnType(i+1);
			}
			
			int rownum=1;
			while (rs.next()) {
				Row currentRow = sheet.createRow(rownum++);
				for (int i = 0; i < colsCount; i++) {
					if (columnType[i] == Types.VARCHAR || columnType[i] == Types.CHAR) 
						currentRow.createCell(i).setCellValue(rs.getString(i+1));
					else 
						if (columnType[i] == Types.DATE) 
							currentRow.createCell(i).setCellValue(rs.getDate(i+1));	
						else
							if (columnType[i] == Types.BLOB) {}
								 //currentRow.createCell(i).setCellValue(rs.getString(i+1));
					//else currentRow.createCell(i).setCellValue(rs.getLong(i+1));
				}
			}
			
			workbook.write(new FileOutputStream(new File(fileName)));
			System.out.println("> Export to \""+fileName+"\" completed!");
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
