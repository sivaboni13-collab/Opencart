package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.Homepage;
import testBase.Baseclass;

public class TC_001_AccountRegistrationTest extends Baseclass {

	@Test(groups = {"Regression","Master"})
	public void verify_account_registration() {
		
		logger.info("***** Starting TC001_AccountRegistrationTest  ****");
		logger.debug("This is a debug log message");
		
		try
		{
		Homepage hp = new Homepage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link.. ");
		
		hp.clickRegister();
		logger.info("Clicked on Register Link.. ");
		

		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

		logger.info("Providing customer details...");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString() + "@gmail.com");// randomly generated the email
		regpage.setTelephone(randomNumber());

		String password = randomAlphaNumeric();

		regpage.setPassword(password);
		regpage.setConfirmPassword(password);

		regpage.setPrivacyPolicy();
		regpage.clickContinue();

		logger.info("Validating expected message..");
		String confmsg = regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
			logger.info("Test passed");
		}
		else
		{
			logger.error("Test failed....... ");
			logger.debug("debuglogstarted........");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		
		} 
	
		catch (Exception e)
		{
			Assert.fail("Test failed: " + e.getMessage());
		} 
		finally 
		{
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
		}

	}

}
