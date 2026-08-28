package com.eric.common.petclinic.welcome;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;

import com.eric.common.petclinic.system.ApplicationContextProvider;


public class WelcomeAdapter_UT
{ 
	private static Log methIDSetUp, methIDrunTestToWelcomeModelSUCCESS;

	static
	{

		methIDSetUp = LogFactory
				  	.getLog(WelcomeAdapter_UT.class.getName()
						+ ".setUp()");
        
		methIDrunTestToWelcomeModelSUCCESS = LogFactory
				  	.getLog(WelcomeAdapter_UT.class.getName()
						+ ".runTestToWelcomeModelSUCCESS()");

		 
	}

   	private WelcomeAdapter welcomeAdapter			= null;    
	private ApplicationContext context				= null;
    Model model                                     = null;

    @BeforeEach
	public void setUp()
	{
        Log logger = methIDSetUp;

        boolean keepOnTrucking = true;

		logger.debug("Begins...");

        //"KUBE_NODE_NAME": "${env:HOSTNAME}"

        while ( keepOnTrucking )
        {
            // Retrieve the context statically
            context = ApplicationContextProvider.getApplicationContext();

            if ( context == null )
            {
                logger.error("***ERROR: SpringContext Received is NULL!!");
                keepOnTrucking = false;
                break;
            }

            welcomeAdapter = context.getBean(WelcomeAdapter.class);

            if ( welcomeAdapter == null )
            {
                logger.error("***ERROR: welcomeAdapter Received is NULL!!");
                keepOnTrucking = false;
                break;
            }        

            // Safety Purposes
            keepOnTrucking = false;
            break;

        }

   		logger.debug("Begins...");
        
		return;
	}
	 
	@Test
	public void runTestToWelcomeModelSUCCESS()
	{
		Log logger = methIDrunTestToWelcomeModelSUCCESS;

        Model returnValue = null;

		// Here's the important Part!!
		logger.debug("Begins...");
        
        // model = new Model() {

        //     @Override
        //     public Model addAttribute(Object attributeValue) 
        //     {
        //         attributeValue

        //         "nodeName"
        //         // TODO Auto-generated method stub
        //         return null;
        //     }
        // };

        // returnValue = welcomeAdapter.toWelcomeModel(returnValue, null);
		  
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

