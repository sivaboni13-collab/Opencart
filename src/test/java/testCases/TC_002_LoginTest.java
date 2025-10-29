package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.Baseclass;

public class TC_002_LoginTest extends Baseclass {
	
	@Test(groups = {"Sanity","Master"})
	public void verify_login()
	{
	logger.info("**** Starting TC_002_LoginTest  ****");
	logger.debug("capturing application debug logs....");

	try
	{
	//Home page
	Homepage hp = new Homepage(driver);
	hp.clickMyAccount();
	logger.info("clicked on myaccount link on the home page..");
	hp.clickLogin();;
	logger.info("clicked on login link under myaccount..");
	
	//LOgin page
	LoginPage lp = new LoginPage(driver);
	lp.setEmail(p.getProperty("email"));
	lp.setPassword(p.getProperty("password"));
	lp.clickLogin(); //loginbutton
	logger.info("clicked on ligin button.."); 
	
	//MyaccountPage
	MyAccountPage mac = new MyAccountPage(driver);
	
	boolean targetpage = mac.isMyAccountPageExists();
	
	Assert.assertTrue(targetpage);
	}
	catch(Exception e)
	{
		Assert.fail();
	}
	logger.info("**** Finished TC_002_LoginTest  ****");
	
	}
}
