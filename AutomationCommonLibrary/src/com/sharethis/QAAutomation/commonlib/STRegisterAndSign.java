package com.sharethis.QAAutomation.commonlib;


import static com.sharethis.QAAutomation.commonlib.STFunctionLibrary.hm;
import static com.sharethis.QAAutomation.commonlib.STLaunchBrowser.browser;
import static com.sharethis.QAAutomation.XPaths.CommonXPath.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import org.testng.Reporter;
import com.thoughtworks.selenium.SeleniumException;

public class STRegisterAndSign {
	
	// ********************[ stRegistration function Start ]*************************
	 /***************************************************************************
    * stRegistration function is used to fill the registration data in registration page. <br>
    * Expected values:<br>
    * -----------------------<br>
    * 0 - Data is successfully filled in page. <br>
    * 1 - Email,Password and confirm password fields are not present<br>
    * 2 - Failed to fill the registration page. <br>
    * 
    * @param flow=
	 *            Defines the flow on the test script. If Flow="STOP", script
	 *            execution will be stopped in cases of any failure. If
	 *            Flow="Continue", script execution will not stop even in case
	 *            of any failures.
	 * @return= 1. "PASS", 2. "FAIL"
    **************************************************************************/
	
	public String stRegistration(int dataId,int ExpVal,String flow,StringBuffer username )
	{		
		int actVal=1000;
		String returnVal=null;
		hm.clear();
   	hm=STFunctionLibrary.stMakeData(dataId, "Registration");
   	String name = hm.get("Username").trim();
   	String email = hm.get("EmailAddress");		
   	String password = hm.get("Password");
   	String confirmpassword = hm.get("ConfirmPassword");
   	String terms = hm.get("Terms");
   	STCommonLibrary comLib=new STCommonLibrary();
   	
   	try {
   		
		    Block:{			
		    	
		    	Vector<String> xPath=new Vector<String>();
		    	Vector<String> errorMsg=new Vector<String>();
		    	
		    	String emailvalue = null ;
		    	String passwordvalue = null ;
		    	String cnfpasswordvalue = null ;
		    		
			    	if(!"".equals(email))
			    	{
			    		xPath.add(REGISTRATION_EMAIL_LABEL);
			    		errorMsg.add("'Email Address' field not present");
			    	}
			    	if(!"".equals(password))
			    	{
				    	xPath.add(REGISTRATION_PASSWORD_LABEL);
				    	errorMsg.add("Password field not present");
			    	}
			    	if(!"".equals(confirmpassword))
			    	{
			    		xPath.add(REGISTRATION_CONFIRM_PASSWORD_LABEL);
			    		errorMsg.add("Confirm Password field not present.");
			    	}
			    	if(!"".equals(terms))
			    	{
			    		xPath.add(TERMS_CHECKBOX);
			    		errorMsg.add("Terms checkbox not present.");
			    	}
			    	
			    	if(("".equals(email)) && ("".equals(password)) && ("".equals(confirmpassword))
			    			&& ("".equals(terms)))
			    	{
			    		actVal=1;
			    		break Block;
			    	}	
			    	else
			    		comLib.stVerifyObjects(xPath, errorMsg, flow);	
			    	
			    	/* Generating phrase to make username and email Id unique */
	                Calendar cal = Calendar.getInstance();
	                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
	                String stFormat = sdf.format(cal.getTime());
			    	
			    	if(!"".equals(name))
			    	{
			    		browser.focus(REGISTRATION_EMAIL_LABEL);
						browser.click(REGISTRATION_EMAIL_LABEL);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						email=name + stFormat +"@"+ email;
			    		browser.type(REGISTRATION_EMAIL, email);	
			    		emailvalue = browser.getValue(REGISTRATION_EMAIL);
			    		
			    	}
			    	
			    	if(!"".equals(password))
			    	{
			    		browser.focus(REGISTRATION_PASSWORD_LABEL);
						browser.click(REGISTRATION_PASSWORD_LABEL);
			    		
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
			    		browser.type(REGISTRATION_PASSWORD, password );
			    		passwordvalue = browser.getValue(REGISTRATION_PASSWORD);
			    		System.out.println(passwordvalue);
			    					    				    		
			    	}
		    		
			    	if(!"".equals(confirmpassword))
			    	{
			    		browser.focus(REGISTRATION_CONFIRM_PASSWORD_LABEL);
						browser.click(REGISTRATION_CONFIRM_PASSWORD_LABEL);
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						browser.type(REGISTRATION_CONFIRM_PASSWORD, confirmpassword );
						cnfpasswordvalue = "";
						System.out.println("Test: " + cnfpasswordvalue);
						cnfpasswordvalue = browser.getValue(REGISTRATION_CONFIRM_PASSWORD);
						System.out.println(cnfpasswordvalue);
						
						
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
			    	}
			    	
			    	if("Check".equalsIgnoreCase(terms))
			    	{
			    		browser.check(TERMS_CHECKBOX);			    		
			    	}
			    	else if ("Uncheck".equalsIgnoreCase(terms))
			    	{
			    		browser.uncheck(TERMS_CHECKBOX);			    		
			    	}
			    	
			    	
			    	try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			    	if ((email.contains(emailvalue)) && (password.contains(passwordvalue)) && 
			    			(confirmpassword.contains(cnfpasswordvalue)))			    		
			    	{
			    		actVal= 0;/* Data is successfully filled in Registration page*/
			    		System.out.println("abcd");
			    		returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stRegistration",flow, hm);
			    		break Block;
			    	}	
			    	else
			    	{
			    		actVal= 2;
			    	}
			    	
   		}
   	}
			    	
		catch (SeleniumException sexp)
		{
			Reporter.log(sexp.getMessage());	
		} 
		hm=STFunctionLibrary.stMakeData(dataId, "Registration");
		returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stRegistration",flow, hm);
		if(flow.contains("STOP")){
			assertEquals("PASS",returnVal);
		}
		return returnVal; 	
	}
	// ********************[ stRegistration function End ]*************************
	
	// ********************[ stClickonRegister function Start ]*************************
	 /***************************************************************************
    * stClickonRegister function is used for clicking on Create Account
    * button and performing verification of registration. <br>
    * Expected values:<br>
    * -----------------------<br>
    * -4 - Undefined error. <br>
    * -3 - Password should be 6 characters long <br>
    * -2 - Email Address is not Valid<br>
    * -1 - Account is already Registered.<br>
    * 0 - Account created Successfully <br>
    * 1 - Email,Password and confirm password fields are not present<br>
    * 2 - this field is required.<br>
    * 3 - Failed to create new account. <br>
    * 4 - Password and confirm password does not match<br>
    * 
    * @param flow=
	 *            Defines the flow on the test script. If Flow="STOP", script
	 *            execution will be stopped in cases of any failure. If
	 *            Flow="Continue", script execution will not stop even in case
	 *            of any failures.
	 * @return= 1. "PASS", 2. "FAIL"
    **************************************************************************/
	public String stClickonRegister(int ExpVal,String flow )
    {           
          int actVal=1000;
          String returnVal=null;
          
          String emailerrormsg = "";
          String pwderrormsg = "";
          String cnfpwderrormsg = "";
          String termsmsg = "";

    STCommonLibrary comLib=new STCommonLibrary();
    
    try {
          
              Block:{             
                
                Vector<String> xPath=new Vector<String>();
                Vector<String> errorMsg=new Vector<String>();               
                
                xPath.add(CREATEACCOUNT_BUTTON);
                errorMsg.add("Create account button is not present");
                comLib.stVerifyObjects(xPath, errorMsg, "STOP");
                xPath.clear();
                errorMsg.clear();
                
                xPath.add(REGISTRATION_EMAIL);
                errorMsg.add("Email Address field is not present");
                
                xPath.add(REGISTRATION_PASSWORD);
                errorMsg.add("Password field is not present");
                
                xPath.add(REGISTRATION_CONFIRM_PASSWORD);
                errorMsg.add("Password field is not present");   
                
                xPath.add(REGISTRATION_EMAIL1);
                errorMsg.add("Email Address field is not present");
                
                xPath.add(REGISTRATION_PASSWORD1);
                errorMsg.add("Password field is not present");
                
                xPath.add(REGISTRATION_CONFIRM_PASSWORD1);
                errorMsg.add("confirm password field is not present");
                      
                comLib.stVerifyObjects(xPath, errorMsg, "STOP");
                
                xPath.clear();
                errorMsg.clear();
                
                browser.click(CREATEACCOUNT_BUTTON);
                      try {
                                  Thread.sleep(30000);
                            } catch (InterruptedException e1) {
                                  e1.printStackTrace();
                            }
                            
                            String allwindowtitle [] = browser.getAllWindowTitles();
                            int Numberofwindows= allwindowtitle.length;     
                            
                            System.out.println("No of Window=" + Numberofwindows);
                            
                            if (Numberofwindows ==1)
                            {
                                  /*If no Error messages on Page*/
                                  browser.selectWindow(null);
                            } else
                            {
                            
                            /*IF no fields are filled then error message*/
                            if (browser.isElementPresent(EMAIL_ERROR_MSG))
                            {
                                  emailerrormsg = browser.getText(EMAIL_ERROR_MSG);
                                    
                            }
                            try {
                                  Thread.sleep(2000);
                            } catch (InterruptedException e1) {
                                  e1.printStackTrace();
                            }
                            if (browser.isElementPresent(PASSWORD_ERROR_MSG))
                            {
                                  pwderrormsg = browser.getText(PASSWORD_ERROR_MSG);
                                  
                            }
                            try {
                                  Thread.sleep(2000);
                            } catch (InterruptedException e1) {
                                  e1.printStackTrace();
                            }
                            if (browser.isElementPresent(CONFIRM_PASSWORD_ERROR_MSG))
                            {
                                  cnfpwderrormsg = browser.getText(CONFIRM_PASSWORD_ERROR_MSG);
                                 
                            }
                            if (browser.isElementPresent(TERMS_OF_SERVICE_MSG))
                            {
                            	termsmsg = browser.getText(TERMS_OF_SERVICE_MSG);
                            }
                            }
                            try {
                                  Thread.sleep(2000);
                            } catch (InterruptedException e1) {
                                  e1.printStackTrace();
                            }
                            if ((emailerrormsg.contains("Username is required")) && 
                                  (pwderrormsg.contains("Password is required.")) && 
                                  (cnfpwderrormsg.contains("Confirm Password is required.")&&
                                	termsmsg.contains("Please accept the terms of service to proceed.")))
                            {
                                  actVal = 2;
                                  break Block;
                            }
                            
                      // ********* Checking Error messages *********
                            /* Cheking error msg for Email field */
                            if(browser.isElementPresent(CONFIRM_PASSWORD_ERROR_MSG) && browser.isElementPresent(PASSWORD_ERROR_MSG))
                            {
                          	  if(pwderrormsg.contains("* Password should be atleast 6 characters long..")&& cnfpwderrormsg.equalsIgnoreCase("* Confirm Password should be atleast 6 characters long."))
                          	  {
                          		  actVal = 8;
                          		  break Block; 
                          	  }
                          	  
                            }
                            
                       //     if(browser.isElementPresent(CONFIRM_PASSWORD_ERROR_MSG))
                       //     {
                       //     	System.out.println("cnfpwderrormsg : " +cnfpwderrormsg);
                       //     	if(cnfpwderrormsg.equalsIgnoreCase("Password and Confirm Password does not match."))
                       //     	{
                       //     		actVal=-8;
                       //     		break Block;
                       //     	}
                       //     }
                                                                                                              
                            if (browser.isElementPresent(EMAIL_ERROR_MSG))
                            {
                                  emailerrormsg = browser.getText(EMAIL_ERROR_MSG);
                                  if(emailerrormsg.contains("Enter valid email e.g. user@example.com."))
                                  {
                                	  actVal= -2; /*error msg - Email is not Valid */
                                      
                                      break Block; 
                                  }
                                    
                            }
                            
                            if (browser.isElementPresent(CONFIRM_PASSWORD_ERROR_MSG))                    	  
                            { 
                                 cnfpwderrormsg = browser.getText(CONFIRM_PASSWORD_ERROR_MSG);
                                 System.out.println(cnfpwderrormsg + "  <------" );
                                if(cnfpwderrormsg.contains("Password and Confirm Password does not match."))
                               
                                {             System.out.println("INside Confirm password if condition");           
                                    actVal= 4;
                                    break Block;
                                }

                    
                      /* Cheking error msg for Password field */
                      
              //        if (browser.isElementPresent(PASSWORD_ERROR_MSG))
                    	  
              //      {
              //           pwderrormsg = browser.getText(PASSWORD_ERROR_MSG);

              //          if (pwderrormsg.contains("This field is required."))
                        	
              //          {
              //              actVal= -3;/*error msg - Password should be atleast 6 characters long.*/                              
                            
              //              break Block;
                //        }
                        
                    }
                      /* Cheking error msg for Confirm Password field */
                      
                      
                      if (browser.isElementPresent(TERMS_ERROR_MSG))
                    {
                        String errormsg = browser.getText(TERMS_ERROR_MSG);

                        if (errormsg.contains("Please accept the terms of use to proceed."))
                        {
                            actVal= 2;
                        } 
                        else
                        {
                           // actVal= -4;/*error msg - Password should be atleast 6 characters long.*/
                            
                            break Block;
                        }
                    }
                      
                                                 
                      try {
                                  Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                  e.printStackTrace();
                            }

                      if (browser.isElementPresent(COMMON_SIGN_OUT_LINK))
                      {
                            actVal= 0;
                            
                            break Block;
                      }
                      else
                      {
                            actVal= 3;
                            
                            break Block;
                      }
    }                         
    }
           
                      
          catch (SeleniumException sexp)
          {
                Reporter.log(sexp.getMessage());    
          } 
          returnVal=STFunctionLibrary.stRetValDes(ExpVal, actVal, "stClickonRegister",flow, hm);
          if(flow.contains("STOP")){
                assertEquals("PASS",returnVal);
          }
          return returnVal;       
    }
}	
// End class

