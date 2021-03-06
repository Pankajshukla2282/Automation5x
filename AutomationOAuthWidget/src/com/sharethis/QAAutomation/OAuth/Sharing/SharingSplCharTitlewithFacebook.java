package com.sharethis.QAAutomation.OAuth.Sharing;

import static com.sharethis.QAAutomation.XPaths.CommonXPath.CLICK_HERE_LINK;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_DONE_SCREEN;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_EMAIL_BIGICON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_FB_BIGICON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_GET_SHARETHIS_CTA_BUTTON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_SHARE_AGAIN_LINK;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_SHARE_BUTTON;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_TOOLBAR_UPSELL_DIV;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_TOOLBAR_UPSELL_IMAGE;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_WINDOW;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.OAUTH_WIDGET_TWITTER_BIGICON;

import java.util.Vector;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sharethis.QAAutomation.commonlib.STCommonLibrary;
import com.sharethis.QAAutomation.commonlib.STDoneScreen;
import com.sharethis.QAAutomation.commonlib.STLaunchBrowser;
import com.sharethis.QAAutomation.commonlib.STOAuthSignIn;
import com.sharethis.QAAutomation.commonlib.STSharing;

public class SharingSplCharTitlewithFacebook {
	
	StringBuffer red = new StringBuffer("1");

	@Parameters( { "STLBDataID", "STLBExpVal", "STLDataID", "STLExpVal", "STBCDataID", "STPDataID", "STPExpVal"})
	@Test
	public void test_SharingSplCharTitlewithFacebook(int STLBDataID,
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
		 * ST_STM_02- This scenario is to verify sharing to Facebook via OAuth Widget.
		 * (Note:Sharing title is having special character in it.)
		1. Browse to any website. Launch widget
		2. Click on Facebook chicklet
		3. Enter valid Credentials and sign in to widget. Click on Share
		 **********************************************************************/		
		STCommonLibrary comLib = new STCommonLibrary();	
		STOAuthSignIn oauthLogin= new STOAuthSignIn();
		STDoneScreen stDoneScreen=new STDoneScreen();
		
		Vector<String> xPaths = new Vector<String>();
		Vector<String> errorMsg = new Vector<String>();
				
		String status="";
		
		comLib.stClick(CLICK_HERE_LINK, "Click here link not present", "STOP");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		
		//********** Launch Widget******		
		launchBrowser.stLaunchOauthWidget(OAUTH_WIDGET,OAUTH_WIDGET_WINDOW, 0, "STOP");	
		
		//********Click on FB icon *****		
		status=comLib.stClickAndVerifyLink(OAUTH_WIDGET_FB_BIGICON, STBCDataID, 0, 1, "");
		System.out.println("status of stClickAndVerifyLink" +status);
		
				
		//********* FB sign in ************
		status=oauthLogin.stOAuthSignInFacebook(STLDataID, STLExpVal, "");
		System.out.println("status of stOAuthSignInFacebook" +status);
		//browser.click(OAUTH_WIDGET_FB_BIGICON);
		
		//*********Click on FB icon **********
	    //status=comLib.stClickAndVerifyLink1(OAUTH_WIDGET_FACEBOOK_GREEN_CHECKED_BUTTON1);
		
		/* Click on 'Share' link and wait for Done screen */
		comLib.stClickAndVerify(OAUTH_WIDGET_SHARE_BUTTON, OAUTH_WIDGET_DONE_SCREEN, 0, "");
		
		/* Execution of ST_DS_01 */
		/*Verifying presence of 'share again' link on Done Screen*/
		xPaths.add(OAUTH_WIDGET_SHARE_AGAIN_LINK);
		errorMsg.add(" 'share again'  Not found on Done screen");
		
		/*Verifying presence of Upsell image on Done Screen*/
		xPaths.add(OAUTH_WIDGET_TOOLBAR_UPSELL_IMAGE);
		errorMsg.add(" Toolbar upsell image  Not found on Done screen");
		
		xPaths.add(OAUTH_WIDGET_TOOLBAR_UPSELL_DIV);
		errorMsg.add(" Toolbar upsell div  not found on Done screen");
		
		 /*Verifying elements in xPaths vector*/
		status = comLib.stVerifyObjects(xPaths, errorMsg, "");
		
		/* Clearing up vectors */
		xPaths.clear();
		errorMsg.clear();
		
		if(status.equalsIgnoreCase("PASS"))
		{
			/* logging results for ST_DS_01 on success*/
			System.out.println(" ST_DS_06 : " +status);
			comLib.stLogResult(status, "ST_DS_06", "", red);

			/* verifying redirection from 'Get Sharethis' CTA button */
			comLib.stClickAndVerifyLink(OAUTH_WIDGET_GET_SHARETHIS_CTA_BUTTON, 16, 0, 0, "");
			System.out.println(" ST_DS_07 : " +status);
			//status="PASS";
			comLib.stLogResult("PASS", "ST_DS_07", "", red);
			
			/* Verifying 'Shared To' Text on Done screen */
			status=stDoneScreen.stVerifySharedToText("FB", 1, "");
			System.out.println(" ST_DS_08 : " +status);
			comLib.stLogResult(status, "ST_DS_08", "", red);
		
			/* Verification of 'share again' link on Done screen */
			status=comLib.stClickAndVerify(OAUTH_WIDGET_SHARE_AGAIN_LINK, OAUTH_WIDGET_TWITTER_BIGICON, 0, "");
			System.out.println(" ST_DS_09 : " +status);
			comLib.stLogResult(status, "ST_DS_09", "", red);	
		}
		else
		{
			comLib.stLogResult(status, "ST_DS_06", "", red);	
		}
		
		
		//********* Perform Verification sharing on Facebook************
		status = sharing.stFacebookSharingVerification(STPDataID, STPExpVal, "STOP");
		System.out.println(" ST_STM_10 : " +status);
		
		comLib.stLogResult(status, "ST_STM_10", "STOP", red);		
		
		comLib.stTearDown(red);

	}

	@AfterTest
	public void CloseBrowser() {
		STCommonLibrary comLib = new STCommonLibrary();
		red.replace(0, 1, "1");
		comLib.stTearDown(red);
	}

}
