package testscripts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;



public class FacebookLibrary extends DriverScript {

	// Stores current window handle
	public static String currentWindowHandle;

	// Store method return result
	public static String methodReturnResult = null;

	// Site name
	public static String testSiteName = "Facebook";

	// User name
	public static String userName = null;

	// Expected page titles
	public static String facebookMyProfilePageTitle = null;
	public static String facebookLoginPageTitle = "Facebook – log in or sign up";
	public static String facebookPageTitle = null;

	/*
	 * .............. Name of the WebElements present on the WebPage
	 * .................
	 */
	
	public static String nameLoginName = "'Email or Phone' button on Facebook login page";
	public static String namePassword = "'Email or Phone' button on Facebook login page";
	public static String nameLogIn = "'LogIn' Button";
	public static String nameLogout = "'Logout' Button";
	public static String namePostInputBox = "'Post' Input Box";
	public static String nameComposePost = "'Post' Input Box";
	public static String nameLogoutDropDown = "'Logout' DropDown";
	public static String nameWritePost = "'Write' Post";
	public static String nameClosePopUp = "'Close' Pop up";
	public static String nameShareButton = "'Share' Button";

	/* .............. Locators for the test ................. */
	
	public static By LocatorLoginName = By.xpath("//input[@id='email']");
	public static By LocatorPassword = By.xpath("//input[@id='pass']");
	public static By LocatorLogInButton = By.xpath("//label[@id='loginbutton']"); 
	public static By LocatorLogoutButton = By.partialLinkText("Log Out"); 
	public static By LocatorPostInputBox = By.cssSelector("[data-testid=\"status-attachment-mentions-input\"]");
	public static By LocatorComposePost = By.xpath("//*[contains(text(),'Compose Post')]");
	public static By LocatorLogoutDropDown = By.xpath("//*[@id='userNavigationLabel']");
	public static By LocatorWritePost = By.xpath("//div[@class='_1mf _1mj firepath-matching-node']");
	public static By LocatorWritePostDiv = By.cssSelector("[data-testid=\"status-attachment-mentions-input\"]");
	public static By locatorClosePopUp = By.xpath("//*[@class='img sp_EMK0iFazUia sx_9246f2']");
	public static By LocatorShareButton = By.xpath("//button[contains(.,'Share')]");
	public static By LocatorVerifyReview = By.xpath("//*[@class='reviews']");
	
	// Create a browser instance and navigate to the test site
	public static String navigate() throws MalformedURLException,
	InterruptedException {

		System.out.println("Navigating to the test site - " + testSiteName
				+ " ...");
		APPLICATION_LOGS.debug("Navigating to the test site - " + testSiteName
				+ " ...");

		// Open a driver instance if not opened already

		try {

			if (wbdv == null) {

				if (CONFIG.getProperty("is_remote").equals("true")) {

					// Generate Remote address
					String remote_address = "http://"
							+ CONFIG.getProperty("remote_ip") + ":4444/wd/hub";
					remote_url = new URL(remote_address);

					if (CONFIG.getProperty("test_browser").equals(
							"InternetExplorer"))
						dc = DesiredCapabilities.internetExplorer();

					else if (CONFIG.getProperty("test_browser").equals(
							"Firefox"))
						dc = DesiredCapabilities.firefox();

					else if (CONFIG.getProperty("test_browser")
							.equals("Chrome"))
						dc = DesiredCapabilities.chrome();

					// Initiate Remote Webdriver instance
					wbdv = new RemoteWebDriver(remote_url, dc);

				}

				else {

					if (CONFIG.getProperty("test_browser").equals(
							"InternetExplorer"))
						wbdv = new InternetExplorerDriver();

					else if (CONFIG.getProperty("test_browser").equals(
							"Firefox"))
						wbdv = new FirefoxDriver();

					else if (CONFIG.getProperty("test_browser")
							.equals("Chrome")) {
					//Create prefs map to store all preferences 
					Map<String, Object> prefs = new HashMap<String, Object>();

					//Put this into prefs map to switch off browser notification
					prefs.put("profile.default_content_setting_values.notifications", 2);

					//Create chrome options to set this prefs
					ChromeOptions options = new ChromeOptions();
					options.setExperimentalOption("prefs", prefs);

					//Now initialize chrome driver with chrome options which will switch off this browser notification on the chrome browser
					wbdv = new ChromeDriver(options);

					//Now do your further steps

					}
				}
			}
		}

		catch (Throwable initException) {

			APPLICATION_LOGS.debug("Error came while initiating driver : "
					+ initException.getMessage());
			System.err.println("Error came while initiating driver : "
					+ initException.getMessage());

		}

		// Initiate Event Firing Web Driver instance
		driver = new EventFiringWebDriver(wbdv);

		// Implicitly wait for 30 seconds for browser to open
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Delete all browser cookies
		driver.manage().deleteAllCookies();

		// Navigate to facebook application
		driver.navigate().to(CONFIG.getProperty("test_site_url"));

		// Maximize browser window
		driver.manage().window().maximize();

		
		Thread.sleep(3000);
		
		// Verify Login page appears
		expectedTitle = facebookLoginPageTitle;
		methodReturnResult = FunctionLibrary.assertTitle(expectedTitle);
		if (methodReturnResult.contains(failTest)) {

			// Log result
			APPLICATION_LOGS.debug("Not navigated to the test site - "
					+ testSiteName);
			System.err.println("Not navigated to the test site - "
					+ testSiteName);
			return methodReturnResult;

		}

		APPLICATION_LOGS.debug("Navigated to the test site - " + testSiteName);
		System.out.println("Navigated to the test site - " + testSiteName);

		return "Pass : Navigated to the test site - " + testSiteName;

	}

	// Retrive Login data to the facebook application
	public static String login(int Data_Row_No) throws InterruptedException,
	IOException {

		APPLICATION_LOGS.debug("Logging in to the test site - " + testSiteName);
		System.out.println("Logging in to the test site - " + testSiteName);

		String userName = null;
		String password = null;

		try {

			userName = testData.getCellData("Login", "UserId_In", Data_Row_No);

			password = testData
					.getCellData("Login", "Password_In", Data_Row_No);

			APPLICATION_LOGS
			.debug("Successfully Retrieved data from Xls File :-  Username : "
					+ userName + " and Password : " + password);
			System.out
			.println("Successfully Retrieved data from Xls File :-  Username : "
					+ userName + " and Password : " + password);

		}

		catch (Exception e) {

			APPLICATION_LOGS.debug("Error while retrieving data from xls file"
					+ e.getMessage());
			System.out.println("Error while retrieving data from xls file"
					+ userName);
			return "Fail : Error while retrieving data from xls file";

		}

		// Verify whether Username input-box, Password input-box and SignIn
		// button present on the page or not
		Boolean usernameFieldPresent = FunctionLibrary.isElementPresent(
				LocatorLoginName, nameLoginName);
		Boolean passwdFieldPresent = FunctionLibrary.isElementPresent(
				LocatorPassword, namePassword);
		Boolean signInButtonPresent = FunctionLibrary.isElementPresent(
				LocatorLogInButton, nameLogIn);

		if (!usernameFieldPresent && !passwdFieldPresent
				&& !signInButtonPresent) {
			return "Fail : Username Field or Password Field or LogIn button is not present on the page ";
		}

		// Clear Username input-box and input username
		FunctionLibrary.clearField(LocatorLoginName,
				nameLoginName);
		FunctionLibrary.input(LocatorLoginName, nameLoginName,
				userName);

		// Clear Password input-box and input password
		FunctionLibrary.clearField(LocatorPassword,
				namePassword);
		FunctionLibrary.input(LocatorPassword, namePassword,
				password);

		// Click on the Log in button
		FunctionLibrary.clickAndWait(LocatorLogInButton, nameLogIn);

		APPLICATION_LOGS.debug("Successfully logged in to the test site - "
				+ testSiteName);
		System.out.println("Successfully logged in to the test site - "
				+ testSiteName);

		return "Pass : Logged in to the test site - " + testSiteName;

	}

	
	 // Login to the facebook application
	public static String verifyFacebookPage() throws BiffException,
	IOException {

		APPLICATION_LOGS.debug("Verifying Facebook page appears");
		System.out.println("Verifying Facebook page appears");
		return "Pass : Facebook page appears";

}

	// Navigate and login to facebook
	public static String navigateAndLoginToFacebook() throws InterruptedException,
	IOException, BiffException {

		// Navigate to facebook
		methodReturnResult = FacebookLibrary.navigate();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}

		// Login to facebook
		methodReturnResult = FacebookLibrary.login(1);
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}

		// Verify facebook page appears
		methodReturnResult = FacebookLibrary.verifyFacebookPage();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		return "Pass : Navigated and logged in to Facebook";

	}
	
	public static String postAMessage() throws InterruptedException,
	IOException, BiffException {
	
		APPLICATION_LOGS.debug("verifying the Facebook home page ");
		System.out.println("verifying the Facebook home page");
		
		FunctionLibrary.waitForElementToLoad(LocatorComposePost);
	
		
		Thread.sleep(15000);
		
        FunctionLibrary.clickAndWait(LocatorComposePost, nameComposePost);
            
		FunctionLibrary.clearAndInput(LocatorPostInputBox, namePostInputBox, Keys.chord(" Hello World")); 
		
		FunctionLibrary.clickAndWait(LocatorShareButton, nameShareButton);
		
		FunctionLibrary.clickAndWait(locatorClosePopUp, nameClosePopUp);
		
		Thread.sleep(5000);
			
			return "Pass : Message posted in wall";
	}
	
	// Logout from the fcebook application
	public static String logout() throws InterruptedException {

		APPLICATION_LOGS
		.debug("Logging out of the test site - " + testSiteName);
		System.out.println("Logging out of the test site - " + testSiteName);

		FunctionLibrary.waitForElementToLoad(LocatorLogoutDropDown);

		methodReturnResult = FunctionLibrary.clickAndWait(LocatorLogoutDropDown, nameLogoutDropDown);
		if (methodReturnResult.contains(failTest)) {
		    return methodReturnResult;
		}

		FunctionLibrary.waitForElementToLoad(LocatorLogoutButton);

		methodReturnResult = FunctionLibrary.clickAndWait(LocatorLogoutButton, nameLogout);
		if (methodReturnResult.contains(failTest)) {
		    return methodReturnResult;
		} 
		
		
		// Delete all cookies
		try {
			driver.manage().deleteAllCookies();
		}

		catch (Throwable deleteCookieException) {

			APPLICATION_LOGS.debug("Error came while clearing cookies : "
					+ deleteCookieException.getMessage());
			System.err.println("Error came while clearing cookies : "
					+ deleteCookieException.getMessage());

		}
		return methodReturnResult;

	}	

}
