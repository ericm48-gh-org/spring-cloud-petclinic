package com.eric.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import com.eric.common.petclinic.util.OSInfoUtil;


public class OSInfoUtil_UT 
{
	private static Log methIDSetUp, methIDrunTestToWelcomeModelSUCCESS;

	static
	{

		methIDSetUp = LogFactory
				  	.getLog(OSInfoUtil_UT.class.getName()
						+ ".setUp()");
        
		methIDrunTestToWelcomeModelSUCCESS = LogFactory
				  	.getLog(OSInfoUtil_UT.class.getName()
						+ ".runTestGetOSDetailsSUCCESS()");

		 
	}

    private OSInfoUtil osInfoUtil = null;

    @BeforeEach
	public void setUp()
	{
        Log logger = methIDSetUp;

		logger.debug("Begins...");

        osInfoUtil = new OSInfoUtil();

   		logger.debug("Ends...");
        
		return;
	}
	 
	@Test
	public void runTestGetOSDetailsSUCCESS()
	{
		Log logger = methIDrunTestToWelcomeModelSUCCESS;

        Map<String, String> returnValue = new HashMap<>();

		logger.debug("Begins...");

        returnValue = OSInfoUtil.getOSDetails();

   		// Here's the important Part!!
        Assert.notNull(returnValue, "returnValue IS NULL!");
        Assert.notEmpty(returnValue, "returnValue IS EMPTY!");

        Assert.isTrue((returnValue.size() >= 6), "returnValue IS TOO-SMALL");
		  
		logger.debug("Ends...");
	
		return;
	}
	 	 
	@AfterEach
	public void cleanUp()
	{


		// System.clearProperty(AppPropFileKey.INTERNAL.toString());		 
		// System.clearProperty(AppPropFileKey.EXTERNAL.toString());	

		return;		 

	}    

}
