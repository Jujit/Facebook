package testcases;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import testscripts.DriverScript;
import testscripts.FunctionLibrary;
import testscripts.FacebookLibrary;

/**
 * @author Mindfire
 * 
 */

public class verifyPostStatus extends DriverScript {

	/* .............. Name of the WebElements present on the WebPage ................. */

	public static String nameLoginName = "'Email or Phone' button on Facebook login page";
	public static String namePassword = "'Email or Phone' button on Facebook login page";
	public static String nameLogIn = "'LogIn' Button";
	public static String nameLogout = "'Logout' Button";

    /* .............. Locators for the test ................. */
	
	public static By LocatorLoginName = By.xpath("//input[@id='email']");
	public static By LocatorPassword = By.xpath("//input[@id='pass']");
	public static By LocatorLogInButton = By.xpath("//label[@id='loginbutton']"); 
	public static By LocatorLogoutButton = By.id("u_o_b");   
	
			
		// Navigate to facebook page
	      public static String navigateToFacebookPage() throws InterruptedException,
	      IOException, BiffException {

		APPLICATION_LOGS
		.debug("Executing test case : Navigate to Facebook page");

		System.out.println("Executing test case : Navigate to Facebook page");
		
		

		// Navigate and login to facebook
		methodReturnResult = FacebookLibrary.navigateAndLoginToFacebook();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Post a message
		methodReturnResult = FacebookLibrary.postAMessage();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}

		// Logout from facebook
		methodReturnResult = FacebookLibrary.logout();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		return "Pass : Successfully posted a message in facebook";
		}
		
}