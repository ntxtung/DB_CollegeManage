package hcmiuiot.DB_CollegeManage.Extensions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
						if (columnType[i] == Types.DATE) {
							
							CellStyle cellStyle = workbook.createCellStyle();
							CreationHelper createHelper = workbook.getCreationHelper();
							cellStyle.setDataFormat(
							    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
							Cell cell = currentRow.createCell(i);
							cell.setCellValue(rs.getDate(i+1));
							cell.setCellStyle(cellStyle);
							
						}
								
						else
							if (columnType[i] == Types.BLOB || columnType[i] == Types.BINARY ||
									columnType[i] == -4) {
								if (rs.getBlob(i+1) != null) {
								byte[] bytes = org.apache.poi.util.IOUtils.toByteArray(rs.getBlob(i+1).getBinaryStream());
								int pic_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
								XSSFDrawing drawing = sheet.createDrawingPatriarch();
								
								ClientAnchor anchor = new XSSFClientAnchor();
								anchor.setCol1(i);
								anchor.setCol2(i+1);
								anchor.setRow1(rownum-1);
								anchor.setRow2(rownum);
								XSSFPicture picture = drawing.createPicture(anchor, pic_id);
								//picture.resize();
								//picture.resize(1.2f);
								}
							}
			
					else currentRow.createCell(i).setCellValue(rs.getLong(i+1));
				}
			}
			
			for (int i = 0; i < colsCount; i++) {
				sheet.autoSizeColumn(i);
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
