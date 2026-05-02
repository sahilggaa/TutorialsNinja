package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import testBase.BaseClass;
import testBase.BaseClass2;
import utilities.DataProviders;

public class TC001_ValidateLoginScreen extends BaseClass2 {
	LoginPage lp;

	@Test(dataProvider="LoginData" ,dataProviderClass = DataProviders.class)
	public void test_LoginScreen(String email, String password, String exp)
	{
		try {
		lp = new LoginPage(getDriver());
		
		lp.clickMyAccount();
		logger.info("Clicked on My Account");
		
		lp.clickLoginToRedirectToLoginpage();
		logger.info("Clicked Login");
		
		
		lp.enterEmail(email);
		
		lp.enterPassword(password);
		
		lp.clickLogin();
		
		boolean targetPage = lp.targetPage();
		
		
		if(exp.equalsIgnoreCase("Valid"))
		logger.info("EXP:"+exp);
		{
			if(targetPage==true)
			{
				lp.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("Invalid"))
		{
			logger.info("EXP:"+exp);
			if(targetPage==true)
			{
				lp.clickLogout();
				logger.info("EXP:"+exp);
				Assert.assertTrue(false);
			}
			else 
			{
				logger.info("EXP:"+exp);
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		
		
		
	}
}
