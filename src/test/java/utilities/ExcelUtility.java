package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	FileInputStream fi;
	FileOutputStream fo;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	
	String path;
	
	
	public ExcelUtility(String path)
	{
		this.path = path;
	}
	
	public int getRowCount(String sheetName) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		
		int rows_count = sheet.getLastRowNum();
		
		workbook.close();
		fi.close();
		
		return rows_count;
		
	}
	
	public int getCellCount(String sheetName , int rowNum) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		
		int cell_count = row.getLastCellNum();
		
		workbook.close();
		fi.close();
		
		return cell_count;	
		
	}
	
	public String getCellData(String sheetName, int rowNum, int cellNum) throws IOException
	{
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		
		cell = row.getCell(cellNum);
		
		DataFormatter formatter = new DataFormatter();
		String data="";
		try 
		{	
			
			data = formatter.formatCellValue(cell);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		workbook.close();
		fi.close();
		return data;
		
		
		
	}
	
	public void setCellData(String sheetName, int rowNum, int cellNum, String data) throws IOException
	{
		File xlFile = new File(path);
		
		if(!xlFile.exists())
		{
			fo = new FileOutputStream(path);
			workbook = new XSSFWorkbook();
			workbook.write(fo);
		}
		
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		if(workbook.getSheetIndex(sheetName)==-1)
		{
			workbook.createSheet(sheetName);
		}
		sheet = workbook.getSheet(sheetName);
		
		if(sheet.getRow(rowNum)==null)
		{
			sheet.createRow(rowNum);
		}
		row = sheet.getRow(rowNum);
		
		cell = row.createCell(cellNum);
		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		workbook.write(fo);
		
		workbook.close();
		fo.close();
		fi.close();
	}

}
