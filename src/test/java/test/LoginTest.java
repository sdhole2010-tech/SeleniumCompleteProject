package test;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.BaseTest;
import jdk.internal.org.jline.utils.Log;
import pages.LoginPage;
import utils.ExcelUtils;

public class LoginTest extends BaseTest {
	
	
	
	 @DataProvider(name="Login")
     public Object[][] logindata()
     {
		 String excelPath= prop.getProperty("testDataPath");
		 return ExcelUtils.getExcelData(excelPath, "Sheet1");
    	 
     }
	
	
	
	@Test(dataProvider="Login")
	public void loginTest(String Username, String Password)
	{
		LoginPage page = new LoginPage(driver);
		
		
		log.info("Enter Username");
		page.enterUsername(Username);
		
		log.info("Enter Password");
		page.enterPassword(Password);
		
		log.info("Click on Submit Button");
		page.submitButton();
		
		boolean result=page.isLoginSuccessful();
		Assert.assertTrue(result, "❌ Login Failed for user: " + Username);
	}

	
	
	
}
