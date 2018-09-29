package testscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;

import jxl.JXLException;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import reports.ReportUtil;
import util.TestUtil;
import datatable.XlsReader;

public class DriverScript {

	public static Properties CONFIG;
	public static Properties LOG;

	//For 2 xls files 
	public static XlsReader controller;
	public static XlsReader testData;

	public static WebDriver wbdv = null;
	public static EventFiringWebDriver driver = null;

	public static Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");

	public static String currentTest;
	public static int testRepeat;
	public static String object;
	public static String data_column_name;
	public static String keyword;
	public static String currentTSID;
	public static String stepDescription;
	public static String proceedOnFail;
	public static String testStatus;
	public static String defaultWindow;
	public static URL remote_url = null;
	public static DesiredCapabilities dc = null;
	
	public static String failTest = "Fail";
	public static String methodReturnResult = null;
	
	public static String firstSheetName = null;
	public static int Data_Row_No;

	public static String screenshotPath = System.getProperty("user.dir")
			+ "\\Reports\\";
	public static String expectedTitle = null;
	
	/* *******Post Message global variables******* */

	/*
	 * creating a global variable to store input value(fetched from the xls
	 * sheet) to post message in facebook
	 */

	public static String inputTestValueOfPostMessage = null;

	@BeforeClass
	public static void initialize() throws IOException {
		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\java\\config\\config.properties");
		CONFIG.load(fs);

		controller = new XlsReader(System.getProperty("user.dir")
				+ "\\src\\test\\java\\config\\fb_controller.xls");

		testData = new XlsReader(System.getProperty("user.dir")
				+ "\\src\\test\\java\\config\\fb_testdata.xls");

		// Start the process of HTML report generation
		ReportUtil.startTesting(System.getProperty("user.dir")
				+ "\\Reports\\index.html",
				TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
				CONFIG.getProperty("test_browser"),
				CONFIG.getProperty("test_site_url"));

	}

	@Test
	public void testApp() throws NumberFormatException, IOException,
	JXLException {

		String startTime = null;

		// Get the first sheet name under 'controller.xls'
		firstSheetName = controller.getFirstSheetname();

		ReportUtil.startSuite(firstSheetName);

		for (int tcid = 1; tcid < controller.getRowCount(firstSheetName); tcid++) {

			// Stores the current sub-module
			currentTest = controller.getCellData(firstSheetName, "TCID", tcid);

			// Runs the respective sub-module if Runmode for the sub-module is
			// 'Y'
			if (controller.getCellData(firstSheetName, "Runmode", tcid).equals(
					"Y")) {

				// initialize start time of test
				startTime = TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");
				APPLICATION_LOGS.debug("Executing the test : " + currentTest);

				// implement keyword . Reflection API
				for (int tsid = 1; tsid < controller.getRowCount(currentTest); tsid++) {

					// values from xls
					// Stores the current keyword
					keyword = controller.getCellData(currentTest, "Keyword",
							tsid);

					// Stores the current TSID
					currentTSID = controller.getCellData(currentTest, "TSID",
							tsid);

					// Stores the current description
					stepDescription = controller.getCellData(currentTest,
							"Description", tsid);

					try {

						Method method = Keywords.class.getMethod(keyword);
						String result = (String) method.invoke(method);
						APPLICATION_LOGS.debug("***Result of execution -- "
								+ result);

						if (!result.startsWith("Fail")) {
							ReportUtil.addKeyword(stepDescription, keyword,
									result, null);
						}

						// Take screenshot - only on error
						if (result.startsWith("Fail")) {

							testStatus = "Fail";

							// Give a fileName for the screenshot and store
							String fileName = "Suite1_TC" + tcid + "_TS" + tsid
									+ "_" + keyword + testRepeat + ".jpg";
							String path = screenshotPath + fileName;
							TestUtil.takeScreenShot(path);
							APPLICATION_LOGS.debug("SCREENSHOT taken under : "
									+ path);
							System.out.println("SCREENSHOT taken under : "
									+ path);

							// Write the test result to HTML report
							ReportUtil.addKeyword(stepDescription, keyword,
									result, fileName);

							if (proceedOnFail.equalsIgnoreCase("N")) {
								break;
							}

						}

						if (wbdv != null) {
							FunctionLibrary.closeDriver();
						}

					}

					catch (Throwable testException) {

						APPLICATION_LOGS.debug("Error came : "
								+ testException.getMessage());
						System.err.println("Error came : "
								+ testException.getMessage());
						testException.printStackTrace();

					}

				}// keywords -inner for loop

				// Report pass or fail
				if (testStatus == null) {
					testStatus = "Pass";
				}

				APPLICATION_LOGS.debug("***********************************"
						+ currentTest + " --- " + testStatus);

				ReportUtil.addTestCase(currentTest, startTime,
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);

			}

			else {

				APPLICATION_LOGS.debug("Skipping the test : " + currentTest);
				System.out.println("Skipping the test : " + currentTest);
				testStatus = "Skip";

				// Report skipped
				APPLICATION_LOGS.debug("***********************************"
						+ currentTest + " --- " + testStatus);
				ReportUtil.addTestCase(currentTest,
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);

			}

			testStatus = null;

		}

		// End test reporting
		ReportUtil.endSuite();

	}

	@AfterClass
	public static void endScript() {

		// Update test end time under HTML test report
		ReportUtil.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));

	}

}
