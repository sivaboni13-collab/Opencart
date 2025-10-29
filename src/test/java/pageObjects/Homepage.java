package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//For every pageobject consists one construct menthod and for every webelement one action class is required
// NO validation happened in pageobject

public class Homepage extends BasePage {

	public Homepage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement lnkMyaccount;

	@FindBy(xpath = "//a[normalize-space()='Register']")
	WebElement lnkRegister;
	
	@FindBy(linkText = "Login")   // Login link added in step5
	WebElement linkLogin;

//for every webelement we need action class
	
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}

	public void clickRegister()
	{
		lnkRegister.click();
	}
	
	public void clickLogin()    // added in step5
	{
		linkLogin.click();
	}

}
