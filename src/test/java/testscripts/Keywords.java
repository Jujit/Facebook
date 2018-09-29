package testscripts;

import java.io.IOException;
import java.sql.SQLException;

import jxl.read.biff.BiffException;
import testcases.verifyPostStatus;

public class Keywords extends DriverScript {

    /*
     * ......................... Verify name on facebook Page
     * ...............................
     */

    // Verify user details displayed on facebook page
    public static String navigateToFacebookPage()
	    throws InterruptedException, IOException, BiffException, NoSuchMethodException, SQLException {

	return verifyPostStatus.navigateToFacebookPage();

    }

}
