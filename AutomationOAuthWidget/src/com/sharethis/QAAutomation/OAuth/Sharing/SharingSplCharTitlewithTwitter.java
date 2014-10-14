package com.sharethis.QAAutomation.OAuth.Sharing;

import static com.sharethis.QAAutomation.XPaths.CommonXPath.CLICK_HERE_LINK;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_DONE_SCREEN;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_SHARE_BUTTON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_TWITTER_BIGICON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_WINDOW;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sharethis.QAAutomation.commonlib.STCommonLibrary;
import com.sharethis.QAAutomation.commonlib.STDoneScreen;
import com.sharethis.QAAutomation.commonlib.STLaunchBrowser;
import com.sharethis.QAAutomation.commonlib.STOAuthSignIn;
import com.sharethis.QAAutomation.commonlib.STSharing;

public class SharingSplCharTitlewithTwitter {

	StringBuffer red = new StringBuffer("1");

	@Parameters( { "STLBDataID", "STLBExpVal", "STLDataID", "STLExpVal", "STBCDataID", "STPDataID", "STPExpVal"})
	@Test
	public void test_SharingSplCharTitlewithTwitter(int STLBDataID,
			int STLBExpVal, int STLDataID, int STLExpVal, int STBCDataID, int STPDataID, int STPExpVal) {
		
		STLaunchBrowser launchBrowser = new STLaunchBrowser();
		launchBrowser.stLaunch(STLBDataID, STLBExpVal, "STOP");
		STSharing sharing = new STSharing ();
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/***********************************************************************
		 * ST_STM_01- This scenario is to verify sharing to Twitter via OAuth Widget.
		 * (Note:Sharing title is having special character in it.)
		1. Browse to any website. Launch widget
		2. Click on Twitter chicklet
		3. Enter valid Credentials and sign in to widget. Click on Share
		 **********************************************************************/		
		STCommonLibrary comLib = new STCommonLibrary();	
		STOAuthSignIn oauthLogin= new STOAuthSignIn();
		STDoneScreen stDoneScreen=new STDoneScreen();
				
		String status;
		
		comLib.stClick(CLICK_HERE_LINK, "Click here link not present", "STOP");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		//********** Launch Widget******		
		launchBrowser.stLaunchOauthWidget(OAUTH_WIDGET,OAUTH_WIDGET_WINDOW, 0, "STOP");	
		
		//********Click on Twitter icon link*****		
		comLib.stClickAndVerifyLink(OAUTH_WIDGET_TWITTER_BIGICON, STBCDataID, 0, 1, "");
				
		//********* Twitter sign in ************
		status = oauthLogin.stOAuthSignInTwitter(STLDataID, STLExpVal, "");
		
		/* Click on 'Share' link and wait for Done screen */
		//browser.click(OAUTH_WIDGET_TWITTER_BIGICON);
		comLib.stClickAndVerify(OAUTH_WIDGET_SHARE_BUTTON, OAUTH_WIDGET_DONE_SCREEN, 0, "");
		
		/* Verifying 'Shared To' Text on Done screen */
		status=stDoneScreen.stVerifySharedToText("Twitter",2, "");
		System.out.println(" ST_DS_12 : " +status);
		comLib.stLogResult(status, "ST_DS_12", "", red);
		
		//********* Perform Verification sharing on Twitter************
		status = sharing.stTwitterSharingVerification(STPDataID, STPExpVal, "STOP");
		
		comLib.stLogResult(status, "ST_STM_11", "STOP", red);		
		
		comLib.stTearDown(red);

	}

	@AfterTest
	public void CloseBrowser() {
		STCommonLibrary comLib = new STCommonLibrary();
		red.replace(0, 1, "1");
		comLib.stTearDown(red);
	}

}
