package com.sharethis.QAAutomation.SignInWidget;

import static com.sharethis.QAAutomation.XPaths.CommonXPath.*;

import org.testng.annotations.*;

import com.sharethis.QAAutomation.commonlib.STCommonLibrary;
import com.sharethis.QAAutomation.commonlib.STLaunchBrowser;
import com.sharethis.QAAutomation.commonlib.STLogin;

public class WidgetSignInWithLinkedinAccount {

	StringBuffer red = new StringBuffer("1");

	@Parameters( { "STLBDataID", "STLBExpVal", "STLDataID", "STLExpVal", "STBCDataID"})
	@Test
	public void test_WidgetSignInWithLinkedInAccount(int STLBDataID,
			int STLBExpVal, int STLDataID, int STLExpVal, int STBCDataID) {
	STLaunchBrowser launchBrowser = new STLaunchBrowser();
	launchBrowser.stLaunch(STLBDataID, STLBExpVal, "STOP");
	
	try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}

	/***********************************************************************
	 * ST_SIW_10- This scenario is to Signing-in into widget with valid Linkedin credetials.
	 1. Browse to any website. 
	 2. Click on sharethis widget to launch widget.
	 3. Click on Sign in link, login window appears.
	 4. Click on Linkedin icon
	 **********************************************************************/		
	STCommonLibrary comLib = new STCommonLibrary();	
	STLogin login = new STLogin ();
			
	String status;
	
	//********** Launch Widget******		
	launchBrowser.stLaunchOauthWidget(OAUTH_WIDGET,OAUTH_WIDGET_WINDOW, 0, "STOP");
	
	//********Click on Sign in link*****		
	comLib.stClickAndVerifyLink(OAUTH_WIDGET_SIGNIN_LINK, STBCDataID, 0, 1, "");		
			
	//********* Click on Sign In Button************
	status = login.stLinkedInLogin(STLDataID, STLExpVal, "");
	
	comLib.stLogResult(status, "ST_SIW_10", "STOP", red);		
	
	comLib.stTearDown(red);

}

@AfterTest
public void CloseBrowser() {
	STCommonLibrary comLib = new STCommonLibrary();
	red.replace(0, 1, "1");
	comLib.stTearDown(red);
}
}
