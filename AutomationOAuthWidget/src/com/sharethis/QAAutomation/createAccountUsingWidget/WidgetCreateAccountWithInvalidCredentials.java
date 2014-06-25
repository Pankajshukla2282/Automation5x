package com.sharethis.QAAutomation.createAccountUsingWidget;

/**********************************************************************
 * Test Cases Covered: ST_CAW_02, ST_CAW_03, ST_CAW_04
 *********************************************************************/

import static com.sharethis.QAAutomation.XPaths.CommonXPath.*;
import static com.sharethis.QAAutomation.commonlib.STLaunchBrowser.browser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sharethis.QAAutomation.commonlib.STCommonLibrary;
import com.sharethis.QAAutomation.commonlib.STLaunchBrowser;
import com.sharethis.QAAutomation.commonlib.STRegisterAndSign;

/*******************************************************************************
 * WidgetCreateAccountWithInvalidCredentials class covered following test cases:
 * ST_CAW_02;<br>
 * ST_CAW_03;<br>
 * ST_CAW_04;<br>
 * TestNG annotations used
 * 
 * @Parameters - to read the input parameters from testng.xml file.
 * @Test - For test case
 * 
 * @see <br>
 *      int STLBDataID - Data ID for fetching data form Data.xml for
 *      stLaunchBrowser class.
 * @see <br>
 *      int STLBExpVal - Expected value according to expected result of test case
 *      also for making result for STLaunchBrowser class.
 * @see <br>
 *      int STBCDataID - Data ID for fetching data form Data.xml for
 *      stSetSearchOption class.
 * @see <br>
 *      int STRDataID - Data ID for fetching data form Data.xml for
 *      stSetSearchOption class.
 * @see <br>
 *      int STRExpVal - Expected value according to expected result of test case
 *      also for making result for stSetSearchOption class.
 *  
 * @see <br>
 *      StringBuffer red - Used for tracking fail test case.
 * 
 * @see <br>
 *      stLaunchBrowser class used for following function:
 * @see
 *      <li> stLaunch()
 * 
 * @see <br>
 *      CommonXPath class for calling XPath of elements.
 ******************************************************************************/
public class WidgetCreateAccountWithInvalidCredentials {
	StringBuffer red = new StringBuffer("1");
	StringBuffer username=new StringBuffer("");

	@Parameters( { "STLBDataID", "STLBExpVal", "STRDataID", "STRExpVal", "STBCDataID"})
	@Test
	public void test_WidgetCreateAccountWithInvalidEmail(int STLBDataID,
			int STLBExpVal, int STRDataID, int STRExpVal, int STBCDataID) {
		
		STLaunchBrowser launchBrowser = new STLaunchBrowser();
		launchBrowser.stLaunch(STLBDataID, STLBExpVal, "STOP");
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
        String stFormat = sdf.format(cal.getTime());
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/***********************************************************************
		 * ST_CAW_02- This scenario is to check Creating a new ShareThis Account with invalid email id
		 1. Browse to any website.
		 2. Click on ShareThis widget to launch.
		 3. Click on Sign in link, login window appears.
		 4. Click on Register link
		 5. Provide invalid information and click on register button.
		 
		 * ST_CAW_03- This scenario is to check Creating a new ShareThis Account without 
		 * providing data in available text fields
		 1. Browse to any website.
		 2. Click on ShareThis widget to launch.
		 3. Click on Sign in link, login window appears.
		 4. Click on Register link
		 5. Try to register without providing any data. 
		 
		 
		 **********************************************************************/		
		STCommonLibrary comLib = new STCommonLibrary();	
		STRegisterAndSign register = new STRegisterAndSign();
				
		String status;
		
		//********** Launch Widget******		
		launchBrowser.stLaunchOauthWidget(OAUTH_WIDGET, OAUTH_WIDGET_WINDOW, 0, "STOP");
		
		//********Click on Sign in link*****		
		comLib.stClickAndVerifyLink(OAUth_WIDGET_SIGN_IN_LINK, STBCDataID, 0, 1, "");
		
		//********* Click on Registration link************		
		//comLib.stClickAndVerify(RESKINNED_WIDGET_REGISTRATION_BUTTON, REGISTRATION_EMAIL_LABEL, 0, "");	
		
		//********* Click on Register Button without entering any fields************
		/* Execution of CAW_03 */ 
		status = register.stClickonRegister(2, "");
		
		/* Logging results for CAW_03 */ 
		comLib.stLogResult(status, "ST_CAW_03", "", red);
		browser.click(ERROR_MSG_OK_BUTTON);
		
		
		
		/* Execution of SIW_04 */
		browser.focus(REGISTRATION_PASSWORD1);
		browser.click(REGISTRATION_PASSWORD);		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		browser.type(REGISTRATION_PASSWORD, "tes" );
		
		browser.focus(REGISTRATION_CONFIRM_PASSWORD1);
		browser.click(REGISTRATION_CONFIRM_PASSWORD);		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		browser.type(REGISTRATION_CONFIRM_PASSWORD, "tes" );
		
		browser.focus(REGISTRATION_EMAIL1);
		browser.click(REGISTRATION_EMAIL);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String email="test" + stFormat +"@"+ "gmail.com";
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		browser.type(REGISTRATION_EMAIL, email);	
		
		browser.check(TERMS_CHECKBOX);
		//********* Click on Register Button************
		status = register.stClickonRegister(8, "");
		
		/* Logging Results for ST_SIW_04 */
		comLib.stLogResult(status, "ST_SIW_04", "", red);
		browser.click(ERROR_MSG_OK_BUTTON);
		
		/* Execution of SIW_05 */
		browser.focus(REGISTRATION_PASSWORD1);
		browser.click(REGISTRATION_PASSWORD);		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		browser.type(REGISTRATION_PASSWORD, "testing123" );
		
		browser.focus(REGISTRATION_CONFIRM_PASSWORD1);
		browser.click(REGISTRATION_CONFIRM_PASSWORD);		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		browser.type(REGISTRATION_CONFIRM_PASSWORD, "different123" );
		
		//********* Click on Register Button************
		status = register.stClickonRegister(4, "");
		
		/* Logging Results for ST_SIW_)% */
		comLib.stLogResult(status, "ST_SIW_05", "", red);
		browser.click(ERROR_MSG_OK_BUTTON);
		
			
		//***********Fill Registartion page**********
		//* Execution of CAW_02 */
		register.stRegistration(STRDataID, STRExpVal, "", username);
		
		//********* Click on Register Button************
		status = register.stClickonRegister(-2, "");
		
		comLib.stLogResult(status, "ST_CAW_02", "STOP", red);
		browser.click(ERROR_MSG_OK_BUTTON);
		
		comLib.stTearDown(red);

	}

	@AfterTest
	public void CloseBrowser() {
		STCommonLibrary comLib = new STCommonLibrary();
		red.replace(0, 1, "1");
		comLib.stTearDown(red);
	}

}
