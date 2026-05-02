package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;
import testBase.BaseClass2;

public class ExtentReportsManager implements ITestListener  {
	
	public ExtentSparkReporter sparkReport;
	public ExtentReports extent;
	public ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		
		repName = "TestReport-"+timeStamp+".html";
		
		sparkReport = new ExtentSparkReporter(".//reports//"+repName);
		
		sparkReport.config().setDocumentTitle("Automation Report");
		sparkReport.config().setReportName("Functional Testing");
		
		sparkReport.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		
		extent.attachReporter(sparkReport);
		extent.setSystemInfo("Application", "Tutorials Ninja");
		extent.setSystemInfo("Module", "End User");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");//Captured via xml file
		extent.setSystemInfo("Operating System", os);
		
		String br = testContext.getCurrentXmlTest().getParameter("br");
		extent.setSystemInfo("Browser", br);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test.set(extent.createTest(result.getTestClass().getName()));

		test.get().assignCategory(result.getMethod().getGroups());
		test.get().log(Status.PASS, result.getName()+" got successfully executed.");
	}
	
	public void onTestFailure(ITestResult result)
	{
		test.set(extent.createTest(result.getTestClass().getName()));

		test.get().assignCategory(result.getMethod().getGroups());
		test.get().log(Status.FAIL, result.getName()+" got failed");
		test.get().log(Status.INFO, result.getThrowable().getMessage());

		try {
		    String imgPath = new BaseClass2().captureScreen(result.getName());
		    test.get().addScreenCaptureFromPath(imgPath);
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test.set(extent.createTest(result.getTestClass().getName()));

		test.get().assignCategory(result.getMethod().getGroups());

		test.get().log(Status.SKIP, result.getName()+" got skipped.");
		test.get().log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext context)
	{
		extent.flush();
		
		String pathOfExtentReport = System.getProperty("user.dir")+"//reports//"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

}
