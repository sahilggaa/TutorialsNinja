package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
	
	WebDriverWait wait;

	public LoginPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//a[@title='My Account']")
	WebElement myAccountBtn;
	
	@FindBy(xpath="//a[text()='Login']")
	WebElement loginRedirectBtn;
	
	@FindBy(xpath="//div/a[text()='Logout']")
	WebElement logoutBtn;
	
	//Login Elements
	
	@FindBy(xpath="//input[@placeholder='E-Mail Address']")
	WebElement emailField;
	
	@FindBy(xpath="//input[@placeholder='Password']")
	WebElement passwordField;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement loginBtn;
	
	@FindBy(xpath="//h2[text()='My Account']")
	WebElement verifyLogin;

	public void clickMyAccount()
	{
		wait.until(ExpectedConditions.elementToBeClickable(myAccountBtn));
		myAccountBtn.click();
	}
	
	public void clickLoginToRedirectToLoginpage()
	{
		wait.until(ExpectedConditions.elementToBeClickable(loginRedirectBtn));
		loginRedirectBtn.click();
		
	}
	
	//Enter Login Creds and hit login
	
	public void enterEmail(String s)
	{

		wait.until(ExpectedConditions.elementToBeClickable(emailField));
		emailField.sendKeys(s);
	}
	
	public void enterPassword(String s)
	{

		wait.until(ExpectedConditions.elementToBeClickable(passwordField));
		passwordField.sendKeys(s);
	}
	
	public void clickLogin()
	{
		wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
		loginBtn.click();
	}
	
	public boolean targetPage()
	{

		return verifyLogin.isDisplayed();
	}
	
	
	
	public void clickLogout()
	{
		wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
		logoutBtn.click();
	}

}
