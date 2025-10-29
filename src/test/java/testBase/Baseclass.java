package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; //for logger
import org.apache.logging.log4j.Logger; //for logger
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class Baseclass {

public static WebDriver driver;
public Logger logger;
public Properties p;
	
	@BeforeClass(groups = {"Master","Sanity","Regression"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws Throwable
	{
		//loading properties file
		FileInputStream file = new FileInputStream(".//src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		////loading log4j file
		logger=LogManager.getLogger(this.getClass());  //Log4j
		
		// for remote condition - Standalone : single hub with single node
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) //Take it from property file
		{
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows")) // from xml we have to configure with capabilities
			{
				capabilities.setPlatform(Platform.WIN10);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
		}
		
		// For local condition		
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) //
		{

			switch(br.toLowerCase())
			{
			case "chrome" : driver=new ChromeDriver(); break;
			case "edge" : driver=new EdgeDriver(); break;
			case "firefox": driver=new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name.."); return;
			}
		}

		
		/*launching browser based on condition
		switch(br.toLowerCase())
		{
		case "chrome": driver=new ChromeDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("No matching browser..");
					return;
		}
		
		
		driver=new ChromeDriver();
		*/
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL")); //Reading the properties
		driver.manage().window().maximize();
		Thread.sleep(5000);
	}
	
	@AfterClass(groups = {"Master","Sanity","Regression"})
	public void tearDown()
	{
		driver.close();
	}
	
	//RandomStringUtils is a predefined class in commons liberaray. commons liberay is 3rd party dependency
		public String randomString()
		{
			String generatedstring = RandomStringUtils.randomAlphabetic(5); 
			return generatedstring;
		}
		
		public String randomNumber()
		{
			String generatednumber = RandomStringUtils.randomNumeric(10); 
			return generatednumber;
		}
		
		public String randomAlphaNumeric()
		{
			String generatedstring = RandomStringUtils.randomAlphabetic(3); 
			String generatednumber = RandomStringUtils.randomNumeric(3); 
			return (generatedstring+'@'+generatednumber);
		}
		
		public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
			String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

			try {
				FileUtils.copyFile(source, new File(destination));
			} catch (Exception e) {
				e.getMessage();
			}
			return destination;

		}

			
}
