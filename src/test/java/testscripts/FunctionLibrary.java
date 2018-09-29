/*  
    FunctionLibrary is a java class which contains basic webdriver methods e.g. 
    1) Dealing with check-boxes - Check/UnCheck Check-box etc.
    2) Dealing with Input-boxes - Clear Input-box, Entering value into Input-box etc.
    3) Dealing with links - Click link
    4) Verify texts/conditions appear on the webpage - Verify text, verify partial text, verify number, verify page source etc.
    5) Wait methods - Wait for an element to load, wait for page to load, wait for new window to appear etc. 

 */

package testscripts;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.os.WindowsRegistryException;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FunctionLibrary extends DriverScript {

	/*
	public static void uncheckCheckBox(By locator, String elemName) method specification :-

	1) Checks a check-box if not checked already
	2) locator -> to locate the element by id,x-path,name,etc.
	3) elemName -> the name/type of the check-box which we intend to check
	4) driver.findElement(locator).getAttribute("value") == "on" -> is used to verify whether the intended checkbox is earlier checked or not
	5) driver.findElement(locator).click() -> checks the check-box

	@param : Locator for the Check-box, name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String uncheckCheckBox(By locator, String elemName) 
	{

		APPLICATION_LOGS.debug("Unchecking the checkbox : " + elemName);
		System.out.println("Unchecking the checkbox : " + elemName);

		try 
		{

			// Highlight check-box
			FunctionLibrary.highlightElement(driver, locator);

			// Wait for check-box to appear on the page
			waitForElementToLoad(locator);

			// UnCheck check-box if already checked
			if (driver.findElement(locator).isSelected()) 
			{
				driver.findElement(locator).click();
			}

			// Log the result
			System.out.println("Unchecked '" + elemName +"'");
			APPLICATION_LOGS.debug("Unchecked '" + elemName +"'");

			return "Pass : Unchecked '" + elemName +"'";

		} 

		catch (Throwable uncheckCheckBoxException) 
		{

			// Log the exception
			System.err.println("Error came while unchecking '" + elemName +"' : " +uncheckCheckBoxException.getMessage());
			APPLICATION_LOGS.debug("Error came while unchecking '" + elemName +"' : " +uncheckCheckBoxException.getMessage());

			return "Fail : Error came while unchecking '" + elemName +"' : " +uncheckCheckBoxException.getMessage();

		}

	}



	/*
	public static String verifyTextPresent(String expText, String elemName) method specification :-

	1) Verifies text present in the page source
	2) expText -> Expected text to be verified from page source
	3) elemName -> the name/type of test we are expecting
	4)  Assert.assertTrue(driver.getPageSource().contains(expText)) -> verifies whether the expected text exist int the page source or not

	@param : Expected text to verify
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String verifyTextPresent(String expText) 
	{

		APPLICATION_LOGS.debug("Verifying Text : '" +expText +"' " +"present in the Page Source");
		System.out.println("Verifying Text : '" +expText +"' " +"present in the Page Source");

		try 
		{

			// Verify page source contains expected text
			Assert.assertTrue(driver.getPageSource().contains(expText));

			// Log result
			APPLICATION_LOGS.debug("'" +expText +"' present in the Page Source");
			System.out.println("'" +expText +"' present in the Page Source");

			return "Pass : '" +expText +"' present in the Page Source";

		} 

		catch (Throwable verifyTextPresentError) 
		{

			// report error
			System.err.println("Error while Verifying Text from Page Source : " +verifyTextPresentError.getMessage());
			APPLICATION_LOGS.debug("Error while Verifying Text from Page Source : " +verifyTextPresentError.getMessage());

			return "Fail : Error while Verifying Text from Page Source : " +verifyTextPresentError.getMessage();

		}

	}



	/*
	public static String clickLink(By locator,String elemName) method specification :-

	1) Clicks on a web element
	2) locator -> to locate the element by id, x-path, name,etc.
	3) elemName -> the name of the element which we intend to click
	4) waitForElementToLoad(locator) -> waits for element to load
	5) driver.findElement(locator).click() -> clicks on the intended element

	@param : Locator for the link, name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String clickLink(By locator,String elemName) 
	{

		APPLICATION_LOGS.debug("Clicking on : " +elemName);
		System.out.println("Clicking on : " +elemName);

		try 
		{

			// Wait for link to appear on the page
			waitForElementToLoad(locator);

			// Highlight link
			FunctionLibrary.highlightElement(driver, locator);

			// Click on the link
			driver.findElement(locator).click();

			// Log result
			System.out.println("Clicked on : " +elemName);
			APPLICATION_LOGS.debug("Clicked on : " +elemName);

			return "Pass : Clicked on : " +elemName;

		} 

		catch(Throwable clickLinkException) 
		{

			// Log error
			System.err.println("Error while clicking on - '" +elemName +"' : " +clickLinkException.getMessage());
			APPLICATION_LOGS.debug("Error while clicking on - '" +elemName +"' : " +clickLinkException.getMessage());

			return "Fail : Error while clicking on - '" +elemName +"' : " +clickLinkException.getMessage();

		}

	}


	/*
	public static String clickLink(By parentLoc,By childLoc,String elemName) method specification :-

	1) Clicks on a web element 
	2) parentLoc -> locator of a parent element which contains multiple child elements
	3) childLoc -> locator of child element
	4) elemName -> the name of the element which we intend to click
	5) waitForElementToLoad(parentLoc) -> waits for element to load
	6) driver.findElement(parentLoc).findElement(childLoc).click() -> clicks on the intended element

	@param : Parent locator and child locator for the link, name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String clickLink(By parentLoc,By childLoc,String elemName) 
	{

		APPLICATION_LOGS.debug("Clicking on : " +elemName);
		System.out.println("Clicking on : " +elemName);

		try 
		{

			// Wait for parent element to load
			waitForElementToLoad(parentLoc);

			// Highlight element
			FunctionLibrary.highlightElement(driver, childLoc);

			// Click on the child element which is under parent element
			driver.findElement(parentLoc).findElement(childLoc).click();

			// Log result
			System.out.println("Clicked on : '" +elemName +"'");
			APPLICATION_LOGS.debug("Clicked on : '" +elemName +"'");

			return "Pass : Clicked on : '" +elemName +"'";

		} 

		catch(Throwable clickLinkException) 
		{

			// Log error
			System.err.println("Error while clicking on - '" +elemName +"' : " +clickLinkException.getMessage());
			APPLICATION_LOGS.debug("Error while clicking on - '" +elemName +"' : " +clickLinkException.getMessage());

			return "Fail : Error while clicking on - '" +elemName +"' : " +clickLinkException.getMessage();

		}

	}



	/*
	 public static String clearField(By locator,String elemName) method specification :-

	1) Clears a text field
	2) locator -> identify the text field by id,x-path,name,etc.
	3) elemName -> the name of the text-field which we intend to clear
	4) waitForElementToLoad(locator) -> waits for text-field to load
	5) driver.findElement(locator).clear() -> clears the intended text-field

	@param : Locator for the input-box, name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String clearField(By locator,String elemName) 
	{

		APPLICATION_LOGS.debug("Clearing field : " +elemName);
		System.out.println("Clearing field : " +elemName);

		try 
		{

			// Wait for the input-box to load on the page
			waitForElementToLoad(locator);

			// Highlight the input-box
			FunctionLibrary.highlightElement(driver, locator);

			// Clear the input-box
			driver.findElement(locator).clear();

			// Log result
			System.out.println("Cleared : " +elemName);
			APPLICATION_LOGS.debug("Cleared : " +elemName);

			return "Pass : Cleared : " +elemName;

		}

		catch(Throwable clearFieldException) 
		{

			// Log error
			System.err.println("Error while clearing - " +elemName +" : " +clearFieldException.getMessage());
			APPLICATION_LOGS.debug("Error while clearing - " +elemName +" : " +clearFieldException.getMessage());

			return "Fail : Error while clearing - " +elemName +" : " +clearFieldException.getMessage();

		}

	}






	/*

	public static String input(By locator,String elemName,String Value) method specification :-

	1) Inputs/sends value
	2) locator -> identify the web element by id,x-path,name,etc.
	3) elemName -> the name of the web element where we intend to input/send values
	4) Value -> the string value which we intend to input/send
	5) waitForElementToLoad(locator) -> waits for web element to load
	6) driver.findElement(locator).sendKeys(Value) -> inputs/sends the value to the intended web element

	@param : Locator for the input-box, name of the web element, value to be inputted
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String input(By locator,String elemName,String Value) 
	{

		APPLICATION_LOGS.debug("Sending Values in : " +elemName);
		System.out.println("Sending Values in : " +elemName);

		try 
		{

			// Wait for the input box to appear on the page
			waitForElementToLoad(locator);

			// Highlight the input box
			FunctionLibrary.highlightElement(driver, locator);

			// Send values to the input box
			driver.findElement(locator).sendKeys(Value);

			// Log result
			System.out.println("Inputted '" +Value +"' text into : " +elemName);
			APPLICATION_LOGS.debug("Inputted '" +Value +"' text into : " +elemName);

			return "Pass : Inputted '" +Value +"' text into : " +elemName;

		}

		catch(Throwable inputException) 
		{

			// Log error
			System.err.println("Error while inputting into - " +elemName +" : " +inputException.getMessage());
			APPLICATION_LOGS.debug("Error while inputting into - " +elemName +" : " +inputException.getMessage());

			return "Fail : Error while inputting into - " +elemName +" : " +inputException.getMessage();

		}

	}




	/*

	public static String inputChord(By locator,String elemName,String Value) method specification :-

	1) Inputs/sends value in chord
	2) locator -> identify the web element by id,x-path,name,etc.
	3) elemName -> the name of the web element where we intend to input/send values
	4) Value -> the string value which we intend to input/send
	5) waitForElementToLoad(locator) -> waits for web element to load
	6) driver.findElement(locator).sendKeys(Keys.chord(Value)) -> inputs/sends the value in chord to the intended web element

	@param : Locator for the input-box, name of the web element, value to be inputted
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String inputChord(By locator,String elemName,String Value) 
	{

		APPLICATION_LOGS.debug("Sending Values in : " +elemName);
		System.out.println("Sending Values in : " +elemName);

		try 
		{

			// Wait for input box to appear on the page
			waitForElementToLoad(locator);

			// Highlight input box
			FunctionLibrary.highlightElement(driver, locator);

			// Send values in chord to the input box
			driver.findElement(locator).sendKeys(Keys.chord(Value));

			// Log result
			System.out.println("Inputted '" +Value +"' text into : '" +elemName +"'");
			APPLICATION_LOGS.debug("Inputted '" +Value +"' text into : '" +elemName +"'");

			return "Pass : Inputted '" +Value +"' text into : '" +elemName +"'";

		}

		catch(Throwable inputException) 
		{

			// Log error
			System.err.println("Error while inputting into - '" +elemName +"' : " +inputException.getMessage());
			APPLICATION_LOGS.debug("Error while inputting into - '" +elemName +"' : " +inputException.getMessage());

			return "Fail : Error while inputting into - '" +elemName +"' : " +inputException.getMessage();

		}

	}





	/*
	public static String assertText(String elemName,String actValue, String expValue) method specification :-

	1) Verifies and returns TRUE if expected and actual text match
	2) elemName -> the name/type of text we intend to compare
	3) actValue -> the actual string value which is shown in the application
	4) expValue -> the expected string value which should be shown in the application
	5) Assert.assertEquals(expValue.trim(), actValue.trim())) -> trims and compares the actual and expected string value

	@param : Name of the web element, Actual text and expected text
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String assertText(String elemName,String actValue, String expValue) 
	{

		APPLICATION_LOGS.debug("Asserting  Text  where : ExpectedText = " 
				+expValue +"  and ActualText = " +actValue);

		System.out.println("Asserting  Text  where : ExpectedText = " 
				+expValue +"  and ActualText = " +actValue);

		try 
		{

			// Assert that expected value matches with actual value
			Assert.assertEquals(expValue.trim(), actValue.trim());

			// Log result
			System.out.println("Successfully asserted text for : " +elemName +" where Expected text is '" +expValue +"' and Actual text is '" +actValue +"'");
			APPLICATION_LOGS.debug("Successfully asserted text for : " +elemName +" where Expected text is '" +expValue +"' and Actual text is '" +actValue +"'");

			return "Pass : Expected text matches with actual text";

		} 

		catch(Throwable assertTextException) 
		{

			// Log error
			System.err.println("Error while Asserting Text for - " +elemName +" : " +assertTextException.getMessage());
			APPLICATION_LOGS.debug("Error while Asserting Text for - " +elemName +" : " +assertTextException.getMessage());

			return "Fail : Error while Asserting Text for - " +elemName +" : " +assertTextException.getMessage();

		}

	}
	
	
	public static String clearAndInput(By locator, String elemName, String Value) {

		try {

		    // Wait for the input box to appear on the page
		    waitForElementToLoad(locator);

		    // Highlight the input box
		    FunctionLibrary.highlightElement(driver, locator);

		    // Clear the input field before sending values
		    FunctionLibrary.clearField(locator, elemName);

		    // Send values to the input box
		    APPLICATION_LOGS.debug("Sending Values in : " + elemName);
		    driver.findElement(locator).sendKeys(Value);

		    // Log result
		    APPLICATION_LOGS.debug("Inputted '" + Value + "' text into : '" + elemName + "'");

		    return "Pass : Inputted '" + Value + "' text into : '" + elemName + "'";

		}

		catch (Throwable inputException) {

		    // Log error
		    APPLICATION_LOGS.debug("Error while inputting into - '" + elemName + "' : " + inputException.getMessage());

		    return "Fail : Error while inputting into - '" + elemName + "' : " + inputException.getMessage();

		}

	    }





	/*
	public static String assertCondition(String elemName,Boolean condition) method specification :-

	1) Verifies and returns TRUE if condition mentioned is true
	2) elemName -> Name of the web element for which we are asserting the condition
	3) condition -> Condition to be checked
	5) Assert.assertTrue(condition) -> Asserts whether the condition mentioned is TRUE or not

	@param : Name of the web element, condition to assert
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String assertCondition(String elemName,Boolean condition) 
	{

		APPLICATION_LOGS.debug("Asserting Condition for : "+elemName);
		System.out.println("Asserting Condition for : "+elemName);

		try 
		{

			// Assert that condition is true
			Assert.assertTrue(condition);

			// Log result
			APPLICATION_LOGS.debug("Successfully asserted condition for : " +elemName);
			System.out.println("Successfully asserted condition for : " +elemName);

			return "Pass : Successfully asserted condition for : " +elemName;

		} 

		catch(Throwable assertConditionException) 
		{

			// Log error
			System.err.println("Error while asserting Condition for - '" +elemName +"' : " +assertConditionException.getMessage());
			APPLICATION_LOGS.debug("Error while asserting Condition for - '" +elemName +"' : " +assertConditionException.getMessage());

			return "Fail : Error while asserting Condition for - '" +elemName +"' : " +assertConditionException.getMessage();

		}

	}






	/*
	public static String closePopupWindow() method specification :-

	1) Closes the popup window
	2) driver.close() -> closes the popup window which has the current window handle 
	3) driver.switchTo().window(mainWindow) -> switches back to main window by granting the current window handle to main window

	@param : no parameters
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String closePopupWindow() throws InterruptedException 
	{

		APPLICATION_LOGS.debug("Closing pop-up window");
		System.out.println("Closing pop-up window");

		try
		{

			// Close current window pointed by webdriver 
			driver.close();

			// Switch back to the main window
			driver.switchTo().window(defaultWindow);

			// Log result
			APPLICATION_LOGS.debug("Closed pop-up window");
			System.out.println("Closed pop-up window");

			return "Pass : Closed pop-up window";

		}

		catch(Throwable closePopUpException)
		{

			// Log error
			System.err.println("Error while closing pop-up window : " +closePopUpException.getMessage());
			APPLICATION_LOGS.debug("Error while closing pop-up window : " +closePopUpException.getMessage());

			return "Fail : Error while closing pop-up window : " +closePopUpException.getMessage();

		}

	}





	/*
	public static String switchToDefaultWindow() method specification :-

	1) Switches back to the main window
	2) driver.switchTo().window(mainWindow) -> switches back to main window by granting the current window handle to main window

	@param : no parameters
	@return : Result of execution - Pass or fail (with cause)

	 */	

	public static String switchToDefaultWindow() throws InterruptedException 
	{

		APPLICATION_LOGS.debug("Switching to Default Window");
		System.out.println("Switching to Default Window");

		try
		{

			// Switch to main window
			driver.switchTo().window(defaultWindow);

			// Log result
			System.out.println("Switched to default window");
			APPLICATION_LOGS.debug("Switched to default window");

			return "Pass : Switched to default window";

		}

		catch(Throwable switchToDefaultWindowException)
		{

			// Log error
			System.err.println("Error while switching to default window : " +switchToDefaultWindowException.getMessage());
			APPLICATION_LOGS.debug("Error while switching to default window : " +switchToDefaultWindowException.getMessage());

			return "Fail : Error while switching to default window : " +switchToDefaultWindowException.getMessage();

		}

	}






	/*
	public static String switchToPopupWindow() method specification :-

	1) Switches to pop-up window
	2) driver.getWindowHandle() -> Returns current window handle
	3) driver.getWindowHandles() -> Returns all the available window handles
	4) driver.switchTo().window(popUpWindowHandle) -> Switches to pop-up window

	@param : no parameters
	@return : Result of execution - Pass or fail (with cause)

	 */	

	public static String switchToPopupWindow() throws InterruptedException 
	{

		APPLICATION_LOGS.debug("Executing switchToPopupWindow");
		System.out.println("Switching to Pop Window : ");

		String popUpWindowHandle = null;

		try 
		{

			// Save current window handle for future reference
			defaultWindow = driver.getWindowHandle();

			// Get all the window handles one by one
			for (String windowHandle: driver.getWindowHandles()) 
			{

				// Save new window handle
				if (!windowHandle.equals(defaultWindow))
				{

					popUpWindowHandle = windowHandle;

				}

			}

			// Switches to pop-up window
			driver.switchTo().window(popUpWindowHandle);

			// Log result
			System.out.println("Switched to pop-up window");
			APPLICATION_LOGS.debug("Switched to pop-up window");

			return "Pass : Switched to pop-up window";

		}

		catch (Throwable switchToPopupWindowException) 
		{

			// Log error
			System.err.println("Error while Switching to Pop Window : " +switchToPopupWindowException.getMessage());
			APPLICATION_LOGS.debug("Error while Switching to Pop Window : " +switchToPopupWindowException.getMessage());

			return "Fail : Error while Switching to Pop Window : " +switchToPopupWindowException.getMessage();

		}

	}






	/*
	public static String closeDriver() method specification :-

	1) Closes the web driver
	2) driver.close() -> Closes the webdriver

	@param : no parameters
	@return : Result of execution - Pass or fail (with cause)

	 */	

	public static String closeDriver() throws InterruptedException 
	{

		System.out.println("Closing the driver ...");
		APPLICATION_LOGS.debug("Closing the driver ...");

		try
		{

			// Close the driver 
			driver.close();

			// Make driver to point to null
			wbdv = null;

			// Close IEDriverServer processes if browser is IE
			if (CONFIG.getProperty("test_browser").equals("InternetExplorer")) 
			{

				System.out.println("Killing IEDriverServer process");
				APPLICATION_LOGS.debug("Killing IEDriverServer process");

				// Kill IEDriverServer from Remote machine
				String remote_ip = CONFIG.getProperty("remote_ip");
				String domain = CONFIG.getProperty("domain");
				String username = CONFIG.getProperty("username");
				String password = CONFIG.getProperty("password");
				String command = "pskill \\\\" +remote_ip +" -u " +domain +"\\" +username +"-p " +password  +" iedriverserver.exe";
				Runtime rt = Runtime.getRuntime();
				Process proc = rt.exec(command);
				int exitVal = proc.waitFor();

				// If failed to kill try once with this
				if(exitVal == -1)
				{

					try
					{

						WindowsUtils.killByName("IEDriverServer.exe");
						System.out.println("Killed IEDriverServer process");
						APPLICATION_LOGS.debug("Killed IEDriverServer process");

					}

					catch(WindowsRegistryException taskKillException)
					{

						APPLICATION_LOGS.debug("IE Driver Server already killed. Skipping ...");
						System.out.println("IE Driver Server already killed. Skipping ...");

					}

				}

				else
				{

					System.out.println("Killed IEDriverServer process");
					APPLICATION_LOGS.debug("Killed IEDriverServer process");

				}

			}


			// Close IEDriverServer processes if browser is IE
			else if (CONFIG.getProperty("test_browser").equals("Chrome")) 
			{

				System.out.println("Killing ChromeDriver process");
				APPLICATION_LOGS.debug("Killing ChromeDriver process");

				// Kill IEDriverServer from Remote machine
				String remote_ip = CONFIG.getProperty("remote_ip");
				String domain = CONFIG.getProperty("domain");
				String username = CONFIG.getProperty("username");
				String password = CONFIG.getProperty("password");
				String command = "pskill \\\\" +remote_ip +" -u " +domain +"\\" +username +"-p " +password  +" chromedriver.exe";
				Runtime rt = Runtime.getRuntime();
				Process proc = rt.exec(command);
				int exitVal = proc.waitFor();

				// If failed to kill try once with this
				if(exitVal == -1)
				{

					try
					{

						WindowsUtils.killByName("chromedriver.exe");
						System.out.println("Killed ChromeDriver process");
						APPLICATION_LOGS.debug("Killed ChromeDriver process");

					}

					catch(WindowsRegistryException taskKillException)
					{

						APPLICATION_LOGS.debug("Chrome Driver already killed. Skipping ...");
						System.out.println("Chrome Driver already killed. Skipping ...");

					}

				}

				else
				{

					System.out.println("Killed ChromeDriver process");
					APPLICATION_LOGS.debug("Killed ChromeDriver process");

				}

			}			

			// Log result
			System.out.println("Closed the driver");
			APPLICATION_LOGS.debug("Closed the driver");

			return "Pass : Closed the driver";

		}

		catch(Throwable closeDriverException)
		{

			// Log error
			System.err.println("Error came while closing driver : " +closeDriverException.getMessage()) ;
			APPLICATION_LOGS.debug("Error came while closing driver : " +closeDriverException.getMessage());

			return "Fail : Error came while closing driver : " +closeDriverException.getMessage();

		}

	}






	/*
	public static String selectValueByVisibleText(By Locator, String Option, String elemName) method specification :-

	1) Select value from drop-down by visible text
	2) Select -> This is a in-built class in Selenium which is used to represent a drop-down
	3) select.selectByVisibleText(Value) -> Select by visible text

	@param : Locator for the drop-down field, Option to be selected, Name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */	

	public static String selectValueByVisibleText(By Locator, String Option, String elemName) 
	{

		APPLICATION_LOGS.debug("Selecting '" +Option +"' from : "+elemName);
		System.out.println("Selecting '" +Option +"' from : "+elemName);

		try 
		{

			// Wait for drop-down element to load on the page
			waitForElementToLoad(Locator);

			// Highlight the drop-down
			FunctionLibrary.highlightElement(driver, Locator);

			// Locate drop-down field
			Select select = new Select(driver.findElement(Locator));

			// Select value from drop-down
			select.selectByVisibleText(Option);

			// Log result
			System.out.println("Selected '" +Option +"' from : " +elemName);
			APPLICATION_LOGS.debug("Selected '" +Option +"' from : " +elemName);

			return "Pass : Selected '" +Option +"' from : " +elemName;

		} 

		catch(Throwable selectValueException) 
		{

			// Log error
			System.err.println("Error while Selecting Value from - '" +elemName +"' : " +selectValueException.getMessage());
			APPLICATION_LOGS.debug("Error while Selecting Value from - '" +elemName +"' : " +selectValueException.getMessage());

			return "Fail : Error while Selecting Value from - '" +elemName +"' : " +selectValueException.getMessage();

		}

	}





	/*
	public static String selectValueByIndex(By Locator, int index, String elemName) method specification :-

	1) Select value from drop-down by index
	2) Select -> This is a in-built class in Selenium which is used to represent a drop-down
	3) select.selectByIndex(index) -> Select by index

	@param : Locator for the drop-down field, Index for the option to be selected, Name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */	

	public static String selectValueByIndex(By Locator, int index, String elemName) 
	{

		APPLICATION_LOGS.debug("Selecting value from : "+elemName);
		System.out.println("Selecting value from : "+elemName);

		try 
		{

			// Wait for drop-down element to load on the page
			waitForElementToLoad(Locator);

			// Highlight the drop-down
			FunctionLibrary.highlightElement(driver, Locator);

			// Locate drop-down field
			Select select = new Select(driver.findElement(Locator));

			// Select value from drop-down
			select.selectByIndex(index);

			// Log result
			System.out.println("Selected value from : " +elemName);
			APPLICATION_LOGS.debug("Selected value from : " +elemName);

			return "Pass : Selected value from : " +elemName;

		} 

		catch(Throwable selectValueException) 
		{

			// Log error
			System.err.println("Error while Selecting Value from - '" +elemName +"' : " +selectValueException.getMessage());
			APPLICATION_LOGS.debug("Error while Selecting Value from - '" +elemName +"' : " +selectValueException.getMessage());

			return "Fail : Error while Selecting Value from - '" +elemName +"' : " +selectValueException.getMessage();

		}

	}






	/*
	public static Boolean isElementPresent(By Locator,String elemName) method specification :-

	1) Check whether an element present or not on the webpage
	2) driver.findElement(Locator).isDisplayed() -> Return true/false based on whether element is displayed or not on the page

	@param : Locator for the web element, Name of the web element
	@return : True/false based on whether element is displayed on the page or not

	 */

	public static Boolean isElementPresent(By Locator,String elemName) 
	{

		APPLICATION_LOGS.debug("Verifying whether Element : " +elemName +" is present");
		System.out.println("Verifying whether Element : " +elemName +" is present");

		Boolean present = null;

		try 
		{

			// Wait for web element to load
			waitForElementToLoad(Locator);

			// Highlight the web element
			FunctionLibrary.highlightElement(driver, Locator);

			// Verify whether element is displayed on the page or not
			present=driver.findElement(Locator).isDisplayed();

			// Log result
			if(present)
			{

				System.out.println("Element : " +elemName +" is present");
				APPLICATION_LOGS.debug("Element : " +elemName +" is present");

			}

			else
			{

				System.out.println("Element : " +elemName +" is not present");
				APPLICATION_LOGS.debug("Element : " +elemName +" is not present");

			}

			return present;

		} 

		catch(Throwable isElementPresentException) 
		{

			// Log error
			System.err.println("Error while verifying - " +elemName +" element Present : " +isElementPresentException.getMessage());
			APPLICATION_LOGS.debug("Error while verifying - " +elemName +" element Present : " +isElementPresentException.getMessage());

			return false;

		}

	}






	/*
	public static String retrieveText(By locator,String elemName) method specification :-

	1) Return retrieved text from webpage
	2) driver.findElement(locator).getText() -> Retrieves text from the web element targeted by specified locator

	@param : Locator for the web element, Name of the web element
	@return : Text retrieved from the webpage

	 */

	public static String retrieveText(By locator,String elemName) 
	{

		String retrievedText = null;

		APPLICATION_LOGS.debug("Retrieving Text from : " +elemName);
		System.out.println("Retrieving Text from : " +elemName);

		try 
		{

			// Wait for web element to load on the page
			waitForElementToLoad(locator);

			// Highlight the web element
			FunctionLibrary.highlightElement(driver, locator);

			// Retrieve text from web element
			retrievedText= driver.findElement(locator).getText().trim();

			// Log result
			APPLICATION_LOGS.debug("Retrieved text : " +retrievedText);
			System.out.println("Retrieved text : " +retrievedText);

		} 

		catch(Throwable retrieveTextException) 
		{

			// Log error
			System.err.println("Error while Getting Text from '" +elemName +"' : " +retrieveTextException.getMessage());
			APPLICATION_LOGS.debug("Error while Getting Text from '" +elemName +"' : " +retrieveTextException.getMessage());

			return "Fail : Error while Getting Text from '" +elemName +"' : " +retrieveTextException.getMessage();

		}

		return retrievedText;

	}








	/*
	public static String retrieveAttributeValue(By locator,String value,String elemName) method specification :-

	1) Return retrieved HTML attribute value from webpage
	2) driver.findElement(locator).getAttribute(value) -> Retrieves attribute (present under a web element) value

	@param : Locator for the web element, Attribute name, Name of the web element
	@return : Attribute value retrieved

	 */

	public static String retrieveAttributeValue(By locator,String value,String elemName) 
	{

		String attributeValue = null;

		APPLICATION_LOGS.debug("Getting Attribute '" +value +"'  Value from : " +elemName);
		System.out.println("Getting Attribute '" +value +"'  Value from : " +elemName);

		try
		{

			// Wait for web element to load
			waitForElementToLoad(locator);

			// Highlight the web element
			FunctionLibrary.highlightElement(driver, locator);

			// Get attribute value for the web element 
			attributeValue= driver.findElement(locator).getAttribute(value);

			// Log result
			APPLICATION_LOGS.debug("Got Attribute '" +value +"'  Value from : " +elemName);
			
			

		} 

		catch(Throwable retrieveAttributeValueException) 
		{

			// report error
			System.err.println("Error while Getting Attribute '" +value +"' value from '" 
					+elemName +"' : " +retrieveAttributeValueException.getMessage());

			APPLICATION_LOGS.debug("Error while Getting Attribute '" +value +"' value from '" 
					+elemName +"' : " +retrieveAttributeValueException.getMessage());

			return "Fail : Error while Getting Attribute '" +value +"' value from '" 
			+elemName +"' : " +retrieveAttributeValueException.getMessage();

		}

		return attributeValue;

	}







	/*
	public static List<WebElement> createWebList(By locator,String elemName) method specification :-

	1) Return a list of web elements
	2) driver.findElements(locator) -> Returns of list of web elements located by locator

	@param : Locator for the web element, Name of the web element
	@return : List of web elements

	 */

	public static List<WebElement> createWebList(By locator,String elemName) 
	{

		APPLICATION_LOGS.debug("Creating WebList for : " +elemName);
		System.out.println("Creating WebList for : " +elemName);

		List<WebElement> list=null;

		try 
		{

			// Wait for web element to load
			waitForElementToLoad(locator);

			// Highlight the web element
			FunctionLibrary.highlightElement(driver, locator);

			// Find the list of web elements
			list = driver.findElements(locator);

			// Log result
			APPLICATION_LOGS.debug("Created WebList for : " +elemName);
			System.out.println("Created WebList for : " +elemName);

		} 

		catch(Throwable createWebListException) 
		{

			// Log error
			System.err.println("Error while Creating WebList reference for '" 
					+elemName +"' : " +createWebListException.getMessage());

			APPLICATION_LOGS.debug("Error while Creating WebList reference for '" 
					+elemName +"' : " +createWebListException.getMessage());

		}

		return list;

	}






	/*
	public static List<WebElement> createWebList(By parentLoc,By childLoc,String elemName) method specification :-

	1) Return a list of web elements
	2) driver.findElement(parentLoc).findElements(childLoc) -> 
	Returns of list of web elements located by locator which present under parent web element

	@param : Locator for the parent web element, Locator for the child web element, Name of the web element
	@return : List of web elements

	 */

	public static List<WebElement> createWebList(By parentLoc,By childLoc,String elemName) 
	{

		List<WebElement> list=null;

		APPLICATION_LOGS.debug("Creating WebList for : "+elemName);
		System.out.println("Creating WebList for : "+elemName);

		try 
		{

			// Wait for parent web element to load
			waitForElementToLoad(parentLoc);

			// Highlight parent web element
			FunctionLibrary.highlightElement(driver, parentLoc);

			// Find all the child web elements under parent web element
			list= driver.findElement(parentLoc).findElements(childLoc);

			// Log result
			APPLICATION_LOGS.debug("Created WebList for : " +elemName);
			System.out.println("Created WebList for : " +elemName);

		} 

		catch(Throwable createWebListException) 
		{

			// Log error
			System.err.println("Error while Creating WebList reference for '" 
					+elemName +"' : " +createWebListException.getMessage());

			APPLICATION_LOGS.debug("Error while Creating WebList reference for '" 
					+elemName +"' : " +createWebListException.getMessage());

		}

		return list;

	}





	/*
	public static void waitForNewWindow(int prevWndCount) method specification :-

	1) Waits for a new window to appear
	2) new WebDriverWait(driver, 60) -> Waits for 60 seconds
	3) wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (Window count increases) met
	4) d.getWindowHandles().size() -> Returns number of window handles present

	@param : Previous window count
	@return : void

	 */

	public static void waitForNewWindow(int prevWndCount)
	{

		final int currWndCount = prevWndCount;

		try
		{

			// Waits for 60 seconds
			WebDriverWait wait = new WebDriverWait(driver, 60);

			// Wait until expected condition (Window count increases) met
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() 
					{

				public Boolean apply(WebDriver d)	
				{

					// Return true if window count increases, else return false
					return d.getWindowHandles().size() > currWndCount;

				}

					});

		}

		catch (Throwable waitForNewWindowException)
		{

			System.err.println("Exception while waiting for new window to appear : " 
					+waitForNewWindowException.getMessage());

			APPLICATION_LOGS.debug("Exception while waiting for new window to appear : " 
					+waitForNewWindowException.getMessage());

		}

	}







	/*
	public static void waitForPageToLoad() method specification :-

	1) Waits for a new page to load completely
	2) new WebDriverWait(driver, 60) -> Waits for 60 seconds
	3) wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All documents present on the page get ready) met

	@param : no parameters passed
	@return : void

	 */

	public static void waitForPageToLoad() throws InterruptedException
	{

		try
		{

			// Waits for 60 seconds
			WebDriverWait wait = new WebDriverWait(driver, 60);

			// Wait until expected condition (All documents present on the page get ready) met
			wait.until((ExpectedCondition<Boolean>) new ExpectedCondition<Boolean>() 
					{

				public Boolean apply(WebDriver d) 
				{

					if (!(d instanceof JavascriptExecutor))
						return true;

					Object result = ((JavascriptExecutor) d)
							.executeScript("return document['readyState'] ? 'complete' == document.readyState : true");

					if (result != null && result instanceof Boolean && (Boolean) result)
						return true;

					return false;

				}

					});

		}

		catch (Throwable waitForPageToLoadException)
		{

			System.err.println("Error came while waiting for page to load : " +waitForPageToLoadException.getMessage()); 
			APPLICATION_LOGS.debug("Error came while waiting for page to load : " +waitForPageToLoadException.getMessage());

		}

	}






	/*
	public static void waitForElementToLoad(By locator) method specification :-

	1) Waits for the web element to appear on the page
	2) new WebDriverWait(driver, 60) -> Waits for 60 seconds
	3) wait.until((ExpectedCondition<Boolean>) -> Wait until expected condition (All documents present on the page get ready) met

	@param : no parameters passed
	@return : void

	 */

	public static void waitForElementToLoad(final By locator)
	{

		APPLICATION_LOGS.debug("Waiting for web element to load on the page");
		System.out.println("Waiting for web element to load on the page");

		try
		{

			// Waits for 60 seconds
			Wait<WebDriver> wait = new WebDriverWait(driver, 60);

			// Wait until the element is located on the page
			@SuppressWarnings("unused")
			WebElement element = wait.until(visibilityOfElementLocated(locator));

			// Log result
			APPLICATION_LOGS.debug("Waiting ends ... Web element loaded on the page");
			System.out.println("Waiting ends ... Web element loaded on the page");

		}

		catch (Throwable waitForElementException)
		{

			// Log error
			APPLICATION_LOGS.debug("Error came while waiting for element to appear : " +waitForElementException.getMessage());
			System.err.println("Error came while waiting for element to appear : " +waitForElementException.getMessage());

		}

	}



	/*
	public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) method specification :-

	1) Waits for the web element to appear on the page
	2) WebElement toReturn.isDisplayed() -> Returns true if displayed on the page, else returns false

	@param : Locator to locate the web element
	@return : ExpectedCondition about the web element

	 */

	public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator)
	{

		return new ExpectedCondition<WebElement>()
				{

			public WebElement apply(WebDriver driver) 
			{

				// Highlight the web element
				FunctionLibrary.highlightElement(driver, locator);

				// Store the web element
				WebElement toReturn = driver.findElement(locator);

				// Check whether the web element is displayed on the page
				if (toReturn.isDisplayed()) 
					return toReturn;

				return null;

			}

				};

	}






	/*
	public static boolean verifyNumber(String elemName,int actValue, int expValue) method specification :-

	1) Verify number on the page
	2) 

	@param : Name of the web element, Actual value in integer, Expected value in integer
	@return : Boolean - true if verified successfully else false

	 */

	public static boolean verifyNumber(String elemName,int actValue, int expValue) 
	{

		APPLICATION_LOGS.debug("Verifying Number for : "+elemName);
		System.out.println("Verifying Number for : "+elemName);

		try 
		{

			// Assert whether actual value matches with expected
			Assert.assertEquals(expValue, actValue);

			// Log result
			APPLICATION_LOGS.debug("Actual value '" +actValue +"' matches with Expected value '" +expValue +"' for : " +elemName);
			System.out.println("Actual value '" +actValue +"' matches with Expected value '" +expValue +"' for : " +elemName);

			return true;

		} 

		catch(Throwable verifyNumberException) 
		{

			// Log error
			System.err.println("Error while Verifying Number for '" 
					+elemName +"' : " +verifyNumberException.getMessage());

			APPLICATION_LOGS.debug("Error while Verifying Number for '" 
					+elemName +"' : " +verifyNumberException.getMessage());

			return false;

		}

	}





	/*
	public static String acceptAlert(String elemName) method specification :-

	1) Accepts an alert
	2) driver.switchTo().alert() -> Switch to the desired alert
	3) alert.accept() -> Accepts the alert

	@param : Name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String acceptAlert(String elemName) 
	{

		APPLICATION_LOGS.debug("Accepting alert : " +elemName);
		System.out.println("Accepting alert : " +elemName);

		try
		{

			// Create a new alert object
			Alert alert = driver.switchTo().alert();

			// Accept the alert
			alert.accept();

			// Log result
			System.out.println("Accepted alert : " +elemName);
			APPLICATION_LOGS.debug("Accepted alert : " +elemName);

			return "Pass : Accepted the alert '" +elemName +"'";

		}

		catch(Throwable acceptAlertException)
		{

			// Log error
			System.err.println("Error came while accepting alert : " +acceptAlertException.getMessage());
			APPLICATION_LOGS.debug("Error came while accepting alert : " +acceptAlertException.getMessage());

			return "Fail : Error came while accepting alert : " +acceptAlertException.getMessage();

		}


	}







	/*
	public static void clickAndWait(By locator,String elemName) method specification :-

	1) Click and wait for next page to load 
	2) driver.findElement(locator).click() -> Clicks on the web element targeted by locator
	3) driver.navigate().refresh() -> Refresh the page

	@param : Locator to locate the web element, Name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String clickAndWait(By locator,String elemName) 
	{

		try 
		{

			// Highlight the web element
			FunctionLibrary.highlightElement(driver, locator);

			// Click on the web element targeted by locator 
			FunctionLibrary.clickLink(locator, elemName);

			// Wait for new page to load
			waitForPageToLoad();

			// Refresh the page
			//driver.get(driver.getCurrentUrl());

			// Log result
			System.out.println("Clicked on the element : '" +elemName 
					+" and new page loaded with title : " +driver.getTitle());

			APPLICATION_LOGS.debug("Clicked on the element : '" +elemName 
					+" and new page loaded with title : " +driver.getTitle());

			return "Pass : Clicked on the element : '" +elemName +" and new page loaded with title : " +driver.getTitle();

		} 

		catch(Throwable clickAndWaitException) 
		{

			// Log error
			System.err.println("Error while clicking on link '" +elemName 
					+"' and waiting for new page to load : " +clickAndWaitException.getMessage());

			APPLICATION_LOGS.debug("Error while clicking on link '" +elemName 
					+"' and waiting for new page to load : " +clickAndWaitException.getMessage());

			return "Error while clicking on link '" +elemName 
					+"' and waiting for new page to load : " +clickAndWaitException.getMessage();

		}

	}







	/*
	public static String assertTitle(String expectedTitle) method specification :-

	1) Asserts page title 
	2) driver.getTitle() -> Retrieves page title
	3) Assert.assertEquals() -> Asserts for equality

	@param : Expected title to assert
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String assertTitle(String expectedTitle)
	{

		String actualTitle = null;

		APPLICATION_LOGS.debug("Asserting  title  where : Expected title = " +expectedTitle);
		System.out.println("Asserting  title  where : Expected title = " +expectedTitle);

		try
		{

			// Fetch actual title of the webpage
			actualTitle = driver.getTitle();

			// Asserts whether actual title matches with expected one
			Assert.assertEquals(expectedTitle.trim(), actualTitle.trim());

			// Log result
			APPLICATION_LOGS.debug("Actual title = " +actualTitle 
					+" and matches with Expected title = " +expectedTitle);

			System.out.println("Actual title = " +actualTitle 
					+" and matches with Expected title = " +expectedTitle);

			return "Pass : Actual title = " +actualTitle 
					+" and matches with Expected title = " +expectedTitle;

		}

		catch(Throwable assertTitleException)
		{

			// Log error
			System.err.println("Error while asserting title : " 
					+assertTitleException.getMessage());

			APPLICATION_LOGS.debug("Error while asserting title : " 
					+assertTitleException.getMessage());

			return "Fail : Error while asserting title : " +assertTitleException.getMessage();

		}

	}






	/*
	public static String assertAlertAndAccept(String expectedAlertText) method specification :-

	1) Assert alert text and accept 
	2) Alert alert = driver.switchTo().alert() -> Switch to the alert appeared on the page
	3) Assert.assertEquals() -> Asserts for equality
	4) alert.accept() -> Accepts the alert

	@param : Expected alert text to assert
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String assertAlertAndAccept(String expectedAlertText)
	{

		APPLICATION_LOGS.debug("Asserting alert text : " +expectedAlertText);
		System.out.println("Asserting alert text : " +expectedAlertText);

		String actualAlertText = null;
		Alert alert = null;

		try
		{

			// Switch control to alert
			alert = driver.switchTo().alert();

			// Get the actual alert message
			actualAlertText = alert.getText();

			// Assert alert message
			Assert.assertEquals(expectedAlertText.trim(), actualAlertText.trim());
			Thread.sleep(3000L);

			// Accept alert message
			alert.accept();
			Thread.sleep(3000L);

			// log result
			APPLICATION_LOGS.debug("Success : got the alert message saying : " +actualAlertText);
			System.out.println("Success : got the alert message saying : " +actualAlertText);

			return "Pass : got the alert message saying : '" +actualAlertText;

		}

		catch(Throwable alertExcpetion)
		{

			APPLICATION_LOGS.debug("Error came while asserting alert and accepting : " +alertExcpetion.getMessage());
			System.err.println("Error came while asserting alert and accepting : " +alertExcpetion.getMessage());
			return "Fail : Error came while asserting alert and accepting : " +alertExcpetion.getMessage();

		}

	}





	public static Boolean isEnabled(By Locator,String elemName) {
		APPLICATION_LOGS.debug("Is Element enabled : "+elemName);
		System.out.println("Is Element enabled : "+elemName);
		try {
			waitForElementToLoad(Locator);
			FunctionLibrary.highlightElement(driver, Locator);
			Boolean present=driver.findElement(Locator).isEnabled();
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			// WebElement present = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("someid")));
			return present;
		} catch(Throwable e) {
			System.out.println("Error while veryfying Is Element Enabled :   -"+elemName);
			APPLICATION_LOGS.debug("Error while veryfying Is Element Enabled  :   -"+ elemName + e.getMessage());
			return false;
		}


	}


	//highlight the element on which action will be performed
	public static void highlightElement(WebDriver driver, By Locator) {

	}


	/*
	public static void checkCheckBox(By locator, String elemName) method specification : 

	1) Checks a check-box if it is not checked already
	2) if (!driver.findElement(locator).isSelected()) {
				driver.findElement(locator).click() : Checks the checkbox if it is not checked already
	3) String elemName : Passed as a parameter to name the element			

	 */
	public static void checkCheckBox(By locator, String elemName) {

		APPLICATION_LOGS.debug("Clicking on: " + elemName);
		System.out.println("Clicking on: " + elemName);

		try {

			waitForElementToLoad(locator);

			if (!driver.findElement(locator).isSelected()) {
				driver.findElement(locator).click();
			}

		}

		catch (Throwable t) {

			System.out.println("Error while clicking on link -" + elemName);
			APPLICATION_LOGS.debug("Error while clicking on link -" + elemName
					+ t.getMessage());
		}

	}
	/*

	public static Boolean verifyPartialText(String elemName, String expValue,String actValue) method specification : 

			1) This method is for verifying presence of a sub-string in between a larger string
			2) String elemName : Passed as a parameter for naming the element
			3) String expValue : Passed as a parameter for storing the expected value
			4) String actValue : Passed as a parameter for storing the actual value
			5) Boolean check = actValue.trim().contains(expValue.trim()) : Checks if actual text contains the expected text

	 */

	public static Boolean verifyPartialText(String elemName, String expValue,String actValue) 
	{

		APPLICATION_LOGS.debug("Verifying Partial Text - '" +expValue +"' for : " + elemName);
		System.out.println("Verifying Partial Text - '" +expValue +"' for : " + elemName);

		try 
		{

			Boolean check = actValue.trim().contains(expValue.trim());

			if (check)
			{

				APPLICATION_LOGS.debug("Success : Partial text - '" +expValue +"' is present within Full text - '" +actValue +"'");
				System.out.println("Success : Partial text - '" +expValue +"' is present within Full text - '" +actValue +"'");

				return true;

			}

			else
			{

				APPLICATION_LOGS.debug("Partial text - '" +expValue +"' is not present within Full text - '" +actValue +"'");
				System.err.println("Partial text - '" +expValue +"' is not present within Full text - '" +actValue +"'");

				return false;

			}

		} 

		catch (Throwable verifyPartialTextException) 
		{

			System.err.println("Error while Verifying Partial Text for - " + elemName +" : " +verifyPartialTextException.getMessage());
			APPLICATION_LOGS.debug("Error while Verifying Partial Text for : " + elemName +" : " +verifyPartialTextException.getMessage());

			return false;

		}

	}




	/*
     public static void inputInteger(By locator, String elemName, int Value) method specification

	 1) This method converts integer to string and inputs to text box
	 2) String elemName : A parameter passed to take the element name
	 3) int Value : Another parameter passed to take an integer value and input it
	 4) driver.findElement(locator).sendKeys(Value1) : Finds the element and enters the value passed via 'Value' parameter

	 */
	public static void inputInteger(By locator, String elemName, int Value) {
		APPLICATION_LOGS.debug("Sending Values in: " + elemName);
		System.out.println("Sending Values in: " + elemName);
		try {
			waitForElementToLoad(locator);
			String Value1 = Integer.toString(Value);
			driver.findElement(locator).sendKeys(Value1);
		} catch (Throwable t) {
			System.out.println("Error while Sending Values in:  -" + elemName);
			APPLICATION_LOGS.debug("Error while Sending Values in:  -"
					+ elemName + t.getMessage());
		}
	}

	/*
	public static String getDateAndTimeOfSpecificTimeZone(
			String dateAndTimeFormat, String timeZone) method specification : 

			1) This function gets date and time of a specific time zone
			2) String dateAndTimeFormat : A parameter passed in the function which takes the Date/Time format we want
			3) String timeZone : Another parameter passed mentioning the timezone for which we want the date/time
			4) Date date = new Date() : Locale date and time
			5) formatter.setTimeZone(TimeZone.getTimeZone(timeZone)) : Get US/Eastern time
			6) return dateAndTime : Prints the date in the US timezone

	 */

	public static String getDateAndTimeOfSpecificTimeZone(
			String dateAndTimeFormat, String timeZone) {

		APPLICATION_LOGS
		.debug("Executing : getDateAndTimeOfSpecificTimeZone() method");

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(dateAndTimeFormat);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		String dateAndTime = formatter.format(date);

		APPLICATION_LOGS
		.debug("Got date and time specific to timezone. Timezone = "
				+ timeZone + " and DateTime = " + dateAndTime);
		System.out
		.println("Got date and time specific to timezone. Timezone = "
				+ timeZone + " and DateTime = " + dateAndTime);
		return dateAndTime;

	}

	/*

	public static void maximizeWindow() method specification : -

	1) Maximize the currently opened browser window
	2) driver.manage().window().maximize() : Maximize browser window

	 */
	public static void maximizeWindow() {

		APPLICATION_LOGS.debug("Executing : maximizeWindow() method");

		try {

			APPLICATION_LOGS.debug("Maximizing Browser window");
			System.out.println("Maximizing Browser window");
			driver.manage().window().maximize();

			APPLICATION_LOGS.debug("Browser window successfully maximized");
			System.out.println("Browser window successfully maximized");

		}

		catch (Throwable windowMaximizeException) {

			APPLICATION_LOGS.debug("Exception came while maximizing window : "
					+ windowMaximizeException.getMessage());
			System.err.println("Exception came while maximizing window : "
					+ windowMaximizeException.getMessage());

		}

	}

	/*
	public static void refreshPage() method specification : -

	1) Refresh the page 
	2) driver.navigate().refresh : This is used to refresh the current page

	 */
	public static void refreshPage() {

		APPLICATION_LOGS.debug("Executing : refreshPage() method");

		try {

			APPLICATION_LOGS.debug("Refreshing page");
			System.out.println("Refreshing page");
			driver.navigate().refresh();
			APPLICATION_LOGS.debug("Page successfully refreshed");
			System.out.println("Page successfully refreshed");
		}
		catch (Throwable pageRefreshException) {

			APPLICATION_LOGS.debug("Exception came while refreshing page : "
					+ pageRefreshException.getMessage());
			System.err.println("Exception came while refreshing page : "
					+ pageRefreshException.getMessage());
		}
	}





	//Function for Selecting Value from Dropdown
	public static void selectValue(By Locator,String Value,String elemName) 
	{

		APPLICATION_LOGS.debug("Selecting Value from : "+elemName);
		System.out.println("Selecting Value from : "+elemName);

		try 
		{

			waitForElementToLoad(Locator);
			FunctionLibrary.highlightElement(driver, Locator);
			Select select = new Select(driver.findElement(Locator));
			select.selectByVisibleText(Value);
		} 

		catch(Exception e) 
		{

			System.out.println("Error while Selecting Value from :   -"+elemName);
			APPLICATION_LOGS.debug("Error while Selecting Value from :   -"+ elemName + e.getMessage());

		}

	}





	//Method for Checking String comparison
	public static String assertText(String expectedString,String actualString)
	{

		APPLICATION_LOGS.debug("Asserting  Text  where : ExpectedText = " +expectedString +"  and ActualText = " +actualString);
		System.out.println("Asserting  Text  where : ExpectedText = " +expectedString +"  and ActualText = " +actualString);

		try
		{

			Assert.assertEquals(expectedString.trim(), actualString.trim());

			APPLICATION_LOGS.debug("Success : ExpectedText = " +expectedString +"  and ActualText = " +actualString +" and both are same");
			System.out.println("Success : ExpectedText = " +expectedString +"  and ActualText = " +actualString +" and both are same");

		}

		catch(Throwable t)
		{

			// report error
			System.out.println("Error while asserting text :- " +t.getMessage());
			APPLICATION_LOGS.debug("Error while asserting text :- " +t.getMessage());
			return "Fail : Error while asserting text :- " +t.getMessage();

		}

		return "Pass";

	}


	//Generate randomn characters
	public static String randomnGenerator()
	{

		int PASSWORD_LENGTH = 8;
		String randomnCharacters;
		StringBuffer sb = new StringBuffer();  

		for (int x = 0; x < PASSWORD_LENGTH; x++)  
		{

			sb.append((char)((int)(Math.random()*26)+97));

		}  

		randomnCharacters = sb.toString();
		return randomnCharacters;

	}





	/*

	Generate randomn numbers within the range provided inside the method argument
	@param : Min Value in the range, Max Value in the range
	@return : Random number generated

	 */

	public static String randomnNumberGenerator(int minValue, int maxValue)
	{

		int randomnNumber;
		String numbers;
		randomnNumber = (int) (minValue + ((new Random()).nextDouble() * (maxValue - minValue)));
		numbers = Integer.toString(randomnNumber);
		return numbers;

	}






	/*
	public static void isChecked(By locator, String elemName) method specification :-

	1) Verifies whether a Checkbox is checked or not
	2) locator -> to locate the element by id,x-path,name,etc.
	3) elemName -> the name/type of the check-box which we intend to check
	4) driver.findElement(locator).isSelected() -> is to verify whether the intended checkbox is checked or not

	@param : Locator for the Check-box, name of the web element
	@return : Result of execution - Pass or fail (with cause)

	 */

	public static String isChecked(By locator, String elemName) 
	{

		APPLICATION_LOGS.debug("Verifying is the checkbox checked : " + elemName);
		System.out.println("Verifying is the checkbox checked : " + elemName);

		String result = null; 

		try 
		{

			// Highlight check-box
			FunctionLibrary.highlightElement(driver, locator);

			// Wait for check-box to appear on the page
			waitForElementToLoad(locator);

			// Verify whether check-box if already checked
			if (driver.findElement(locator).isSelected()) 
			{

				// Log the result
				System.out.println("Is checked '" + elemName +"'");
				APPLICATION_LOGS.debug("Is checked '" + elemName +"'");
				result = "Pass : Is checked '" + elemName +"'";
			}
			else
			{

				// Log the result
				System.out.println("Is not checked '" + elemName +"'");
				APPLICATION_LOGS.debug("Is not checked '" + elemName +"'");
				result = "Fail : Is not checked '" + elemName +"'";
			}

		} 

		catch (Throwable ischeckCheckBoxException) 
		{

			// Log the exception
			System.err.println("Error while verifying checkbox is checked '" + elemName +"' : " +ischeckCheckBoxException.getMessage());
			APPLICATION_LOGS.debug("Error while verifying checkbox is checked '" + elemName +"' : " +ischeckCheckBoxException.getMessage());

			result = "Error while verifying checkbox is checked: '" + elemName +"' : " +ischeckCheckBoxException.getMessage();

		}
		System.out.println("Result: " + result);
		return result;

	}



	public static Boolean isElementPresentWithoutWait(By Locator, String elemName) {

		APPLICATION_LOGS.debug("Verifying whether Element : " + elemName
				+ " is present");
		System.out.println("Verifying whether Element : " + elemName
				+ " is present");

		Boolean present ;

		try {

			// Wait for web element to load
			//waitForElementToLoad(Locator);

			// Highlight the web element
			//FunctionLibrary.highlightElement(driver, Locator);

			// Verify whether element is displayed on the page or not
			present = driver.findElement(Locator).isDisplayed();

			// Log result
			if (present) {

				System.out.println("Element : " + elemName + " is present");
				APPLICATION_LOGS.debug("Element : " + elemName + " is present");

			}

			else {

				System.out.println("Element : " + elemName + " is not present");
				APPLICATION_LOGS.debug("Element : " + elemName
						+ " is not present");

			}

			return present;

		}

		catch (Throwable isElementPresentException) {

			

			return false;

		}

	}


}