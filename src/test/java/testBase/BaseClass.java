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
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static Logger logger;
	public static WebDriver driver;
	public Properties p;
	
	@BeforeClass
	@Parameters({"os","br"})
	public void setUp(String os, String br) throws IOException {
		
		logger = LogManager.getLogger(this.getClass());
		
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources"
				+ "//config.properties");
		
		logger.info("BaseClass Initialized");
		
		p = new Properties();
		p.load(file);
		
		if(p.getProperty("execution_env").equals("remote"))
		{
			DesiredCapabilities cap = new DesiredCapabilities();
			
			//Operating System
			
			
			if(os.equalsIgnoreCase("mac"))
			{
				cap.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("window"))
			{
				cap.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				cap.setPlatform(Platform.LINUX);
			}
			
			
			//browser
			
			switch(br.toLowerCase()) {
			case "chrome":
				cap.setBrowserName("chrome");
				break;
			
			case "edge":
				cap.setBrowserName("MicrosoftEdge");
				break;
			
			case "firefox":
				cap.setBrowserName("firefox");
				break;
			}
			
		
			
			
			driver = new RemoteWebDriver(new URL(p.getProperty("dockerurl")), cap);
			
			
			
		}
		
		else if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			switch(br.toLowerCase())
			{
			case "chrome":
				driver = new ChromeDriver(options);
				break;
			
			case "edge":
				driver = new EdgeDriver();
				break;
			
			case "firefox":
				driver = new FirefoxDriver();
				break;
				
			case "safari":
				driver = new SafariDriver();
				break;
			}
			
		}
		

		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().deleteAllCookies();	
		driver.get(p.getProperty("appurl"));
		
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
	
	public String captureScreen(String tname)
	{
		String timeStamp = new SimpleDateFormat("yyyyddmm_HHmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"//screenshots//"+tname+"_"+timeStamp+".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath; //We will use the targetFilePath in onTestFailure that is why we return it here
		
		
		
		
	}

}
