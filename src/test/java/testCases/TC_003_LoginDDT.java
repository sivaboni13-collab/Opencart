package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.Baseclass;
import utilities.DataProviders;

public class TC_003_LoginDDT extends Baseclass
{

	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups = "Datadriver")
	public void verify_loginDDT(String email, String password, String exp)
	{
		logger.info("**** Starting TC_003_LoginDDT *****");
		
		try {
	
		//Home page
			Homepage hp=new Homepage(driver);
			hp.clickMyAccount();
			hp.clickLogin(); //Login link under MyAccount
				
			//Login page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(password);
			lp.clickLogin(); //Login button
				
			//My Account Page
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage=macc.isMyAccountPageExists();
			
			/*Data is valid  - login success - test pass  - logout
			                 - login failed - test fail

			Data is invalid - login success - test fail  - logout
			                - login failed - test pass
			*/
			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(true);
				}
				else
				{
					Assert.assertTrue(false);
				}
			}
			
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					macc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail("An exception occurred: " + e.getMessage());
		}
			
		logger.info("**** Finished TC_003_LoginDDT *****");
	}
	
}








