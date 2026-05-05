package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass2 {

	public static Logger logger;
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public Properties p;

	public WebDriver getDriver()
	{
		return driver.get();
	}

	@BeforeClass
	@Parameters({"os","br"})
	public void setUp(String os, String br) throws IOException {

		logger = LogManager.getLogger(this.getClass());
		logger.info("Base Class Initialized");

		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir")+"//src//test//resources//config.properties");

		p = new Properties();
		p.load(file);
		
		WebDriver driver_select = getDriver();
		
		if(p.getProperty("execution_env").equals("remote"))
		{
			String gridUrl = p.getProperty("jenkinsdockerurl");
	
//			WebDriver remoteDriver = null;
	
			switch(br.toLowerCase())
			{
			case "chrome":
	
				ChromeOptions chromeOptions = new ChromeOptions();
				driver_select = new RemoteWebDriver(new URL(gridUrl), chromeOptions);
	
				break;
	
			case "firefox":
	
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				driver_select = new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
	
				break;
			
				
			}
			// Store driver in ThreadLocal
					driver.set(driver_select);
		}
		else if(p.getProperty("execution_env").equals("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome":
				driver_select = new ChromeDriver();
				break;
			}
			
			driver.set(driver_select);
			
			
			
		}

		

		

		// Browser setup
		getDriver().get(p.getProperty("appurl"));
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		getDriver().manage().deleteAllCookies();

		
	}

	@AfterClass
	public void tearDown()
	{
		WebDriver drv = getDriver();

		if(drv != null)
		{
			drv.quit();
			driver.remove();
		}
	}

	public String captureScreen(String tname)
	{
		String timeStamp = new SimpleDateFormat("yyyyddMM_HHmmss").format(new Date());

		WebDriver drv = getDriver();

		TakesScreenshot takesScreenshot = (TakesScreenshot) drv;

		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir")+"//screenshots//"+tname+"_"+timeStamp+".png";

		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;
	}
}
