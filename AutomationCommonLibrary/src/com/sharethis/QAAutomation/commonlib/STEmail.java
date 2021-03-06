package com.sharethis.QAAutomation.commonlib;

import static com.sharethis.QAAutomation.XPaths.CommonXPath.*;
import static com.sharethis.QAAutomation.commonlib.STFunctionLibrary.hm;
import static com.sharethis.QAAutomation.commonlib.STLaunchBrowser.browser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import org.testng.Reporter;
import com.thoughtworks.selenium.SeleniumException;


public class STEmail {
	
	// ********************[ stComposeEmail function Start ]*************************
	/***************************************************************************
	    * stComposeEmail function is used to compose email. <br>
	    * Expected values:<br>
	    * -----------------------<br>
	    * 0 - Email Composed successfully. <br>
	    * 1 - Failed to Compose email.<br>
	    * 
	    * @param flow=
		 *            Defines the flow on the test script. If Flow="STOP", script
		 *            execution will be stopped in cases of any failure. If
		 *            Flow="Continue", script execution will not stop even in case
		 *            of any failures.
		 * @return= 1. "PASS", 2. "FAIL"
	    **************************************************************************/
	
	public String stComposeEmail(int dataId,int ExpVal,String flow )
	{		
		int actVal=1000;
		String returnVal=null;
		hm.clear();
   	hm=STFunctionLibrary.stMakeData(dataId, "Email");
   	String to = hm.get("To");		
   	String from = hm.get("From");
   	String message = hm.get("Message");
   	String widgetType= hm.get("WidgetType");
   	
   	STCommonLibrary comLib=new STCommonLibrary();
   	
   	Vector<String> xPath=new Vector<String>();
	Vector<String> errorMsg=new Vector<String>();
	
	String fromvalue = null;
	String emaillabel = null;
	
	String emailToFeld="";
	String emailFromField="";
	String emailMessageBox="";
		
	if(widgetType.equalsIgnoreCase("OAuth"))
	{
		emailToFeld=OAUTH_WIDGET_EMAIL_TO_FIELD;
		emailFromField=OAUTH_WIDGET_EMAIL_FROM_FIELD;
		emailMessageBox=OAUTH_WIDGET_EMAIL_MESSAGE_FIELD;
		
	}else
	{
		emailToFeld=EMAIL_TO_FIELD;
		emailFromField=EMAIL_FROM_FIELD;
		emailMessageBox=EMAIL_MESSAGE_BOX;	
	}
	
	
 	try {
 		if(widgetType.equalsIgnoreCase("OAuth"))
 		{
 	 	xPath.add(OAUTH_WIDGET_EMAIL_TO_FIELD);
 	 	errorMsg.add("To field is not present");
 	 		
 	 	xPath.add(OAUTH_WIDGET_EMAIL_MESSAGE_FIELD);
 	 	errorMsg.add("Message field is not present");
 	 		
 	 	xPath.add(OAUTH_WIDGET_EMAIL_FROM_FIELD);
 	 	errorMsg.add("Cancel button is not present");
 	 	
 	 	xPath.add(OAUTH_WIDGET_SHARE_BUTTON);
 	 	errorMsg.add("Share Button is not present on WIdget");			
 			
 		}
 		else
 		{
 		xPath.add(EMAIL_TO_FIELD);
 		errorMsg.add("To field is not present");
 		
 		xPath.add(EMAIL_MESSAGE_BOX);
 		errorMsg.add("Message field is not present");
 		
 		xPath.add(EMAIL_CANCEL_BUTTON);
 		errorMsg.add("Cancel button is not present");
 		
 		xPath.add(EMAIL_SEND_BUTTON);
 		errorMsg.add("Send Button is not present");
 		}
 		
 		comLib.stVerifyObjects(xPath, errorMsg, "STOP");
 		
 		xPath.clear();
 		errorMsg.clear();
	
	Block:
    {			        	
    	/*Typing Receipient and Sender*/
    	if(!"".equals(to))
    	{
    		browser.focus(emailToFeld);
			browser.click(emailToFeld);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
    		browser.type(emailToFeld, to );
    		
    	}
    	String tovalue = browser.getValue(emailToFeld);
    	System.out.println(tovalue);
    	
    	if(browser.isElementPresent(emailFromField) )	    	
    	{
    		browser.focus(emailFromField);
			browser.click(emailFromField);    		
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		browser.type(emailFromField, from ); 
    		fromvalue = browser.getValue(emailFromField);
    		System.out.println(fromvalue);
    	}
    	 else
    	{
    		browser.isElementPresent(RESKINNED_WIDGET_SIGNOUT_LINK);
    		browser.isElementPresent(RESKINNED_WIDGET_EMAIL_LABEL);
    		emaillabel= browser.getText(RESKINNED_WIDGET_EMAIL_LABEL);
    	}
		
		/* Generating phrase to make message unique */
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
        String stFormat = sdf.format(cal.getTime());		
		
		browser.type(emailMessageBox, message + stFormat);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		/* Comparing To and From field values */            
        if(tovalue.contains(to) && fromvalue.contains(from))
        {
        	actVal= 0; /*Email is Composed successfully */
        	System.out.println("Values matched");
        	break Block;
        }
        else
        {
        	actVal= 1; /*Failed to Compose email.*/
        	System.out.println("Values Not matched");
        	break Block;
        } 
		
    }  		

	}
		    	
	catch (SeleniumException sexp)
	{
		Reporter.log(sexp.getMessage());	
	} 
	
	returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stComposeEmail",flow, hm);
	if(flow.contains("STOP")){
		assertEquals("PASS",returnVal);
	}
	return returnVal; 	
}
	// ********************[ stComposeEmail function End ]*************************
	
	// ********************[ stSendOrCancelEmail function Start ]*************************
	/***************************************************************************
	    * stComposeEmail function is used to Send or Cancel email. <br>
	    * Expected values:<br>
	    * -----------------------<br>
	    * -2 - To field is invalid. <br>
	    * -1 - To or From fields are not provided <br>
	    * 0 - Email Send successfully. <br>
	    * 1 - Email Cancel successfully.<br>
	    * 2 - Failed to send Email. <br>
	    * 
	    * @param flow=
		 *            Defines the flow on the test script. If Flow="STOP", script
		 *            execution will be stopped in cases of any failure. If
		 *            Flow="Continue", script execution will not stop even in case
		 *            of any failures.
		 * @return= 1. "PASS", 2. "FAIL"
	    **************************************************************************/
	
	public String stSendOrCancelEmail(int dataId,int ExpVal,String flow )
	{		
		int actVal=1000;
		String returnVal=null;
		hm.clear();
   	hm=STFunctionLibrary.stMakeData(dataId, "Email");
   	String action = hm.get("Action");
   	String widgetType=hm.get("WidgetType");
   	
   	STCommonLibrary comLib=new STCommonLibrary();
   	
   	Vector<String> xPath=new Vector<String>();
	Vector<String> errorMsg=new Vector<String>();
	String successmsg = "";
	String errormsg = "";
	
 	try {
 		if(widgetType.equalsIgnoreCase("OAuth"))
 		{
 	 	 xPath.add(OAUTH_WIDGET_SHARE_BUTTON);
 	 	 errorMsg.add("Share Button is not present on WIdget");	
 			
 		}else
 		{
 		xPath.add(EMAIL_CANCEL_BUTTON);
 		errorMsg.add("Cancel button is not present");
 		
 		xPath.add(EMAIL_SEND_BUTTON);
 		errorMsg.add("Send Button is not present");
 		}
 		
 		comLib.stVerifyObjects(xPath, errorMsg, "STOP");
 		
 		xPath.clear();
 		errorMsg.clear();
	
	Block:
    {		
 		/* Executing Tests if widget type is OAuth */
 		if(widgetType.equalsIgnoreCase("OAuth"))
 		{
 			comLib.stClick(OAUTH_WIDGET_SHARE_BUTTON, "Send Button is not present", "STOP");
 			
 			/* Wait for processing */
 			try {
    			Thread.sleep(10000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		/*Checking if Error msg occurs*/
    		if(browser.isElementPresent(OAUTH_WIDGET_EMAIL_ERROR_MESSAGE))
    		errormsg = browser.getText(OAUTH_WIDGET_EMAIL_ERROR_MESSAGE);
    		
    		System.out.println(errormsg);
    		
    		/* If error messages appear on WIdget */
    		if(errormsg.contains("Please enter a valid email address in the "))    			
    		{
    			actVal= -1; /*Invalid email address in From field. */
    			break Block;
    		}else
    		{
    			if (errormsg.contains("Please enter a valid recipient email address."))
    			{
    				actVal= -2; /*To field is invalid*/
    				break Block;
    			}
    		}
    		
    		/* If no error messages appear on WIdget and email share is successful */
    		if(browser.isElementPresent(OAUTH_WIDGET_DONE_SCREEN))
    		{
    			/* Grabbing message on Done screen */
    			String successMsg=browser.getText(OAUTH_WIDGET_DONE_SCREEN_MESSAGE);
    			if(successMsg.equalsIgnoreCase("Your message was successfully shared!"))
    			{
    			actVal= 0; /*Email send successfully.*/
    			break Block;
    			}
    					
    		} else  
    		{
    			if(browser.isElementPresent(OAUTH_WIDGET_CAPTCHA_SCREEN))
    			{
    			actVal= 3; /*Failed to Send or Cancel email.*/
    	    	break Block;
    			}
    			else
        		/* log for failure */
    			{
        		actVal= 2; /*Failed to Send or Cancel email.*/
        		break Block;
        		}
    			
    		}
   	
 		}else /* Executing tests if Widget type is not OAuth */
 		{	
    	/*Click on Send or Cancel Button*/
    	if ("Send".equalsIgnoreCase(action))
    	{
    		comLib.stClick(EMAIL_SEND_BUTTON, "Send Button is not present", "STOP");
    		try {
    			Thread.sleep(5000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		/*Checking if Error msg occurs*/
    		if(browser.isElementPresent(EMAIL_ERROR_PANEL))
    			
    			errormsg = browser.getText(EMAIL_ERROR_PANEL);
    		System.out.println(errormsg);
    		
    		if(errormsg.contains("Please enter a valid email address in the "))    			
    		{
    			actVal= -1; /*Invalid email address in From field. */
    			browser.click(EMAIL_ERROR_PANEL_OK_BUTTON);
    			break Block;
    		}
    		else
    		{
    			if (errormsg.contains("Please enter a valid recipient"))
    			{
    				actVal= -2; /*To field is invalid*/
    				browser.click(EMAIL_ERROR_PANEL_OK_BUTTON);
    				break Block;
    			}
    		}
    		/*IF no Error Occurs*/
    		if (browser.isElementPresent(EMAIL_SUCCESS_MSG))    		
    		{
    		 successmsg = browser.getText(EMAIL_SUCCESS_MSG);
    		 System.out.println(successmsg);
    		
    		if(successmsg.contains("Your message was successfully shared!"))
    		{
    			actVal= 0; /*Email send successfully.*/
    			break Block;
    		}
    		else
    		{
    			actVal= 2; /*Failed to Send or Cancel email.*/
    			break Block;
    		}
    	}
		
    }
    	else if ("Cancel".equalsIgnoreCase(action))
    	{
    		
    		comLib.stClick(EMAIL_CANCEL_BUTTON, "Cancel Button is not present", "STOP");
    		{
    			actVal= 1; /*Email Cancel successfully.*/
    			break Block;
    		}
    	}
    	
    	}
    }

	}
		    	
	catch (SeleniumException sexp)
	{
		Reporter.log(sexp.getMessage());	
	} 
	
	returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stSendOrCancelEmail",flow, hm);
	if(flow.contains("STOP")){
		assertEquals("PASS",returnVal);
	}
	return returnVal; 	
}
	// ********************[ stSendOrCancelEmail function End ]*************************
	
	// ********************[ stSendOrCancelEmail1 function Start ]*************************
	public String stSendOrCancelEmail1(int dataId,int ExpVal,String flow )
	{		
		int actVal=1000;
		String returnVal=null;
		hm.clear();
   	hm=STFunctionLibrary.stMakeData(dataId, "Email");
   	String action = hm.get("Action");
   	String widgetType=hm.get("WidgetType");
   	
   	STCommonLibrary comLib=new STCommonLibrary();
   	
   	Vector<String> xPath=new Vector<String>();
	Vector<String> errorMsg=new Vector<String>();
	String successmsg = "";
	String errormsg = "";
	
 	try {
 		if(widgetType.equalsIgnoreCase("OAuth"))
 		{
 	 	 xPath.add(OAUTH_WIDGET_SHARE_BUTTON);
 	 	 errorMsg.add("Share Button is not present on WIdget");	
 			
 		}else
 		{
 		xPath.add(EMAIL_CANCEL_BUTTON);
 		errorMsg.add("Cancel button is not present");
 		
 		xPath.add(EMAIL_SEND_BUTTON);
 		errorMsg.add("Send Button is not present");
 		}
 		
 		comLib.stVerifyObjects(xPath, errorMsg, "STOP");
 		
 		xPath.clear();
 		errorMsg.clear();
	
	Block:
    {		
 		/* Executing Tests if widget type is OAuth */
 		if(widgetType.equalsIgnoreCase("OAuth"))
 		{
 			comLib.stClick(OAUTH_WIDGET_SHARE_BUTTON, "Send Button is not present", "STOP");
 			
 			/* Wait for processing */
 			try {
    			Thread.sleep(10000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		
    		}
    		
    		/* If no error messages appear on WIdget and email share is successful */
    		if(browser.isElementPresent(OAUTH_WIDGET_DONE_SCREEN))
    		{
    			/* Grabbing message on Done screen */
    			String successMsg=browser.getText(OAUTH_WIDGET_DONE_SCREEN_MESSAGE);
    			if(successMsg.equalsIgnoreCase("Your message was successfully shared!"))
    			{
    			actVal= 0; /*Email send successfully.*/
    			System.out.println(successMsg);
    			break Block;
    			
    			}
    					
    		} else  
    		{
    			if(browser.isElementPresent(OAUTH_WIDGET_CAPTCHA_SCREEN))
    			{
    			actVal= 3; /*Failed to Send or Cancel email.*/
    	    	break Block;
    			}
    			else
        		/* log for failure */
    			{
        		actVal= 2; /*Failed to Send or Cancel email.*/
        		break Block;
        		}
    		}
    }
 	}
   	
	catch (SeleniumException sexp)
	{
		Reporter.log(sexp.getMessage());	
	} 
	
	returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stSendOrCancelEmail1",flow, hm);
	if(flow.contains("STOP")){
		assertEquals("PASS",returnVal);
	}
	return returnVal; 
}	
	
	// ********************[ stSendOrCancelEmail1 function End ]*************************
	// ********************[ stSendEmailWithCaptcha function Start ]*************************
	/***************************************************************************
	    * stSendEmailWithCaptcha function is used to Send email with Captcha. <br>
	    * Expected values:<br>
	    * -----------------------<br>
	    * -2 - To field is invalid. <br>
	    * -1 - To or From fields are not provided <br>
	    * 0 - Email Send successfully. <br>
	    * 1 - Email Cancel successfully.<br>
	    * 2 - Failed to send Email. <br>
	    * 
	    * @param flow=
		 *            Defines the flow on the test script. If Flow="STOP", script
		 *            execution will be stopped in cases of any failure. If
		 *            Flow="Continue", script execution will not stop even in case
		 *            of any failures.
		 * @return= 1. "PASS", 2. "FAIL"
	    **************************************************************************/
	
	public String stSendEmailWithCaptcha(int dataId,int ExpVal,String flow )
	{		
		int actVal=1000;
		String returnVal=null;
		hm.clear();
   	hm=STFunctionLibrary.stMakeData(dataId, "Email");
   	String action = hm.get("Action");
   	
   	STCommonLibrary comLib=new STCommonLibrary();
   	
   	Vector<String> xPath=new Vector<String>();
	Vector<String> errorMsg=new Vector<String>();
	String successmsg = null;
	String captchatext = null;
	
 	try { 		
 		xPath.add(EMAIL_CANCEL_BUTTON);
 		errorMsg.add("Cancel button is not present");
 		
 		xPath.add(EMAIL_SEND_BUTTON);
 		errorMsg.add("Send Button is not present");
 		
 		comLib.stVerifyObjects(xPath, errorMsg, "STOP");
 		
 		xPath.clear();
 		errorMsg.clear();
	
	Block:
    {			        	
    	/*Click on Send or Cancel Button*/
    	if ("Send".equalsIgnoreCase(action))
    	{
    		comLib.stClick(EMAIL_SEND_BUTTON, "Send Button is not present", "STOP");
    		try {
    			Thread.sleep(5000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		xPath.add(EMAIL_CAPTCHA);
     		errorMsg.add("Captcha is not present");
     		
     		xPath.add(EMAIL_CAPTCHA_TEXT);
     		errorMsg.add("Captcha text is not present");
     		
     		xPath.add(EMAIL_CAPTCHA_INSERT_FIELD);
     		errorMsg.add("Captcha field is not present");
     		
     		comLib.stVerifyObjects(xPath, errorMsg, "STOP");
     		
     		xPath.clear();
     		errorMsg.clear();
     		
    		/*Checking if Error msg occurs*/    			
    		captchatext = browser.getText(EMAIL_CAPTCHA_TEXT);    		
    		System.out.println("Text : "+ captchatext);
    		
    		browser.type(EMAIL_CAPTCHA_INSERT_FIELD, captchatext);
    		try {
    			Thread.sleep(5000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    		
    		/*IF no Error Occurs*/
    		if (browser.isElementPresent(EMAIL_CAPTCHA_SUBMIT_BUTTON))    		
    		{
    			comLib.stClick(EMAIL_SEND_BUTTON, "Send Button is not present", "STOP");
        		try {
        			Thread.sleep(5000);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        		comLib.stWaitForElement(EMAIL_SUCCESS_MSG, 20);
        		successmsg = browser.getText(EMAIL_SUCCESS_MSG);
    		
    		if(successmsg.contains("Your message was successfully shared!"))
    		{
    			actVal= 0; /*Email send successfully with Captcha.*/
    			break Block;
    		}
    		else
    		{
    			actVal= 2; /*Failed to Send email with Captcha.*/
    			break Block;
    		}
    	}
		
    }
    	else if ("Cancel".equalsIgnoreCase(action))
    	{
    		
    		comLib.stClick(EMAIL_CANCEL_BUTTON, "Cancel Button is not present", "STOP");
    		{
    			actVal= 1; /*Email Cancel successfully.*/
    			break Block;
    		}
    	}
    	
    	}  		

	}
		    	
	catch (SeleniumException sexp)
	{
		Reporter.log(sexp.getMessage());	
	} 
	
	returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stSendEmailWithCaptcha",flow, hm);
	if(flow.contains("STOP")){
		assertEquals("PASS",returnVal);
	}
	return returnVal; 	
}
	// ********************[ stSendOrCancelEmail function End ]*************************	
	
	// ********************[ stVerifyRecentContacts function Start ]*************************
	/***************************************************************************
	    * stVerifyRecentContacts function is used to Sverify Recent Contacts on OAuth Widget. <br>
	    * Expected values:<br>
	    * -----------------------<br>
	    * -2 - To field is invalid. <br>
	    * -1 - To or From fields are not provided <br>
	    * 0 - Email Send successfully. <br>
	    * 1 - Email Cancel successfully.<br>
	    * 2 - Failed to send Email. <br>
	    * 
	    * @param flow=
		 *            Defines the flow on the test script. If Flow="STOP", script
		 *            execution will be stopped in cases of any failure. If
		 *            Flow="Continue", script execution will not stop even in case
		 *            of any failures.
		 * @return= 1. "PASS", 2. "FAIL"
	    **************************************************************************/
	
	public String stVerifyRecentContacts(int dataId,int ExpVal,String flow )
	{		
		int actVal=1000;
		String returnVal=null;
		hm.clear();
   	hm=STFunctionLibrary.stMakeData(dataId, "Email");
   	String recepientList=hm.get("To");
   	String recepient="";
   	
   	STCommonLibrary comLib=new STCommonLibrary();
   	
   	Vector<String> xPath=new Vector<String>();
	Vector<String> errorMsg=new Vector<String>();
	
	int numberOfRecentContacts = (Integer)browser.getXpathCount("//span[@id='recents_list']/a[@class='recent_item']");
	
	for (int i=1;i<=numberOfRecentContacts;i++)
	{
		/*Creating recent contact Xpaths dynamically */
		recepient=OAUTH_WIDGET_RECENT_EMAIL_PART1 + i + OAUTH_WIDGET_RECENT_EMAIL_PART2;
		System.out.println("recepient :" +recepient);
		
		browser.click(recepient);
	}
	
	browser.getText(OAUTH_WIDGET_EMAIL_TO_FIELD);
	
	System.out.println("OAUTH_WIDGET_EMAIL_TO_FIELD " +OAUTH_WIDGET_EMAIL_TO_FIELD);
	
	System.out.println("numberOfRecentContacts :" +numberOfRecentContacts);
	
	returnVal="true";
	
	/*returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stSendEmailWithCaptcha",flow, hm);
	if(flow.contains("STOP")){
		assertEquals("PASS",returnVal);
	}*/
	return returnVal; 	
}
	// ********************[ stSendOrCancelEmail function End ]*************************	
	
}
