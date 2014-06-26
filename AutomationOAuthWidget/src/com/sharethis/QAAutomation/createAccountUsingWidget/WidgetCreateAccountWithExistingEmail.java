package com.sharethis.QAAutomation.createAccountUsingWidget;

import static com.sharethis.QAAutomation.XPaths.CommonXPath.CREATEACCOUNT_BUTTON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.EMAIL_ERROR_MSG1;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_WINDOW;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUth_WIDGET_SIGN_IN_LINK;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.REGISTRATION_CONFIRM_PASSWORD;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.REGISTRATION_CONFIRM_PASSWORD1;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.REGISTRATION_EMAIL;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.REGISTRATION_EMAIL1;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.REGISTRATION_PASSWORD;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.REGISTRATION_PASSWORD1;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.TERMS_CHECKBOX;
import static com.sharethis.QAAutomation.commonlib.STLaunchBrowser.browser;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sharethis.QAAutomation.commonlib.STCommonLibrary;
import com.sharethis.QAAutomation.commonlib.STLaunchBrowser;

public class WidgetCreateAccountWithExistingEmail {
	
	StringBuffer red = new StringBuffer("1");
	StringBuffer username=new StringBuffer("");

	@Parameters( { "STLBDataID", "STLBExpVal", "STRDataID", "STRExpVal", "STBCDataID"})
	@Test
	public void test_WidgetCreateAccountWithInvalidEmail(int STLBDataID,
			int STLBExpVal, int STRDataID, int STRExpVal, int STBCDataID) {
		
		STLaunchBrowser launchBrowser = new STLaunchBrowser();
		launchBrowser.stLaunch(STLBDataID, STLBExpVal, "STOP");
		

		/***********************************************************************
		 * ST_CAW_04- This scenario is to check Creating a new ShareThis account with existing username
		 1. Browse to any website.
		 2. Click on ShareThis widget to launch.
		 3. Click on Sign in link, login window appears.
		 4. Click on Register link
		 5. Try to register with existing account.
		 **********************************************************************/		
		STCommonLibrary comLib = new STCommonLibrary();	
						
		String status= "";
		
		//********** Launch Widget******		
		launchBrowser.stLaunchOauthWidget(OAUTH_WIDGET, OAUTH_WIDGET_WINDOW, 0, "STOP");
		
		//********Click on Sign in link*****		
		comLib.stClickAndVerifyLink(OAUth_WIDGET_SIGN_IN_LINK, STBCDataID, 0, 1, "");
		
	
		/* Execution of CAW_04 */ 
		
		browser.focus(REGISTRATION_EMAIL1);
		browser.click(REGISTRATION_EMAIL);
		browser.type(REGISTRATION_EMAIL, "testnguser1001@sharethis.com" );	
		
		
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
		browser.type(REGISTRATION_CONFIRM_PASSWORD, "testing123" );
		
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		browser.check(TERMS_CHECKBOX);
		try {
            Thread.sleep(5000);
      } catch (InterruptedException e1) {
            e1.printStackTrace();
      }
		
		//********* Click on Register Button************
		
		browser.click(CREATEACCOUNT_BUTTON);
		try {
            Thread.sleep(10000);
      } catch (InterruptedException e1) {
            e1.printStackTrace();
      }
		      String emailerrormsg1 = "";
	     		
	      
	      if(browser.isElementPresent(EMAIL_ERROR_MSG1))
	      {
	    	  emailerrormsg1 = browser.getText(EMAIL_ERROR_MSG1);
	    	  
	    	  if(emailerrormsg1.contains("An account has already been registered with this email address."))
	    	  {
	    		  
	    		  System.out.println("Account already exist");
	    		  status = "PASS";	    		  
	    		  	    		  
	    	  }
	      }
	      else
	      {
	    	 status = "FAIL";
	      }
	    comLib.stLogResult(status,"ST_CAW_04", "", red);
	}
	
	
	@AfterTest
	public void CloseBrowser() {
		STCommonLibrary comLib = new STCommonLibrary();
		red.replace(0, 1, "1");
		comLib.stTearDown(red);
	}

}




