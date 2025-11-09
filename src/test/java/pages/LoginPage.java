package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BaseTest;

public class LoginPage  {

	protected WebDriver driver;
	
	 public LoginPage( WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(id="username")
	WebElement Username;
	
	@FindBy(id="password")
	WebElement Password;
	
	@FindBy(id="submit")
	WebElement Submit;
	
	
	
	public void enterUsername(String username)
	{
		Username.sendKeys(username);
	}
	public void enterPassword(String password)
	{
		Password.sendKeys(password);
	}
	public void submitButton()
	{
		Submit.click();
	}
	public boolean isLoginSuccessful()
	{
		return driver.getCurrentUrl().contains("practicetestautomation.com/logged-in-successfully/");
		
	}
	
}
