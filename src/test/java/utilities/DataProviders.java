package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name="LoginData")
	public String[][] provideData() throws IOException
	{
		String path = ".//testData//TestNGDataProvidersTest.xlsx";
		
		ExcelUtility xlUtil = new ExcelUtility(path);
		
		int totalRows = xlUtil.getRowCount("Sheet1");
		int totalColumns = xlUtil.getCellCount("Sheet1", 1);
		
//		String loginData[][] = new String[totalRows][totalColumns];
		String loginData[][] = new String[1][totalColumns];

		//for(int i = 1; i<=totalRows;i++)

		for(int i = 1; i<=1;i++)
		{
			for(int j = 0; j<totalColumns;j++)
			{
				loginData[i-1][j] = xlUtil.getCellData("Sheet1", i, j); //1,0
			}
		}
		return loginData;
	}

}
