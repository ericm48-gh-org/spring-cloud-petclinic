package com.eric.common.petclinic.welcome;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;

import com.eric.common.petclinic.util.HostInfoUtil;

@Component
public class WelcomeAdapter 
{
	private static final Log methIDtoWelcomeModel, methIDgetCurrentDateTime, methIDgetJavaVersion, methIDgetSpringBootVersion;
	
    private static String applicationVersion;

	static
    {
        methIDtoWelcomeModel        = LogFactory.getLog(WelcomeAdapter.class.getName() + ".toWelcomeModel()");
        methIDgetCurrentDateTime    = LogFactory.getLog(WelcomeAdapter.class.getName() + ".getCurrentDateTime()");
        methIDgetJavaVersion    	= LogFactory.getLog(WelcomeAdapter.class.getName() + ".getJavaVersion()");		
        methIDgetSpringBootVersion 	= LogFactory.getLog(WelcomeAdapter.class.getName() + ".getSpringBootVersion()");
    }
	
 	// @Value("${application.version}")
	// private String APP_VERSION;

    public WelcomeAdapter(@Value("${application.version}") String newValue) 
    {
        WelcomeAdapter.applicationVersion = newValue;
    }

    public WelcomeAdapter getInstance()
    {
        return(this);
    }

    public Model toWelcomeModel(Model model, RequestAttributes requestAttributes)
    {
		Log logger = methIDtoWelcomeModel;

   		String nodeName  					= null;
   		String deploymentName  				= null;
		String ipAddress 					= null;
		//RequestAttributes requestAttributes = null;

		String sessionID 					= null;

        logger.debug("Begins...");

        logger.debug("    ModelReceived: " + model.toString());
		logger.debug("ModelReceivedSize: " + model.asMap().size());

		logger.debug("APP_VERSION: " + applicationVersion);

		//requestAttributes 	= RequestContextHolder.getRequestAttributes();

		nodeName 			= HostInfoUtil.getNodeName();		
		deploymentName 		= HostInfoUtil.getDeploymentName();
		ipAddress 			= HostInfoUtil.getHostIPAddress();

        model.addAttribute("applicationVersion", applicationVersion);

        model.addAttribute("nodeName", nodeName);		
        model.addAttribute("deploymentName", deploymentName);
        model.addAttribute("ipAddress", ipAddress);

        model.addAttribute("spring.message", "Hello, Thymeleaf in Spring Boot!");
        model.addAttribute("currentDate", getCurrentDateTime());
        model.addAttribute("javaVersion", getJavaVersion());
        model.addAttribute("springBootVersion", getSpringBootVersion());		

		if (requestAttributes != null )
		{
			sessionID = requestAttributes.getSessionId();
			model.addAttribute("sessionID", sessionID);			
		}
		else
		{
			logger.error("***ERROR: requestAttributes is NULL!!!");
		}

		logger.debug("ModelSize: " + model.asMap().size());

        logger.debug("Ends...");

        return( model );
    }
	private String getCurrentDateTime()
    {
        Log logger = methIDgetCurrentDateTime;
        String returnValue = null;

        logger.debug("Begins...");

        returnValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

        logger.debug("returnValue: " + returnValue);

        logger.debug("Ends...");

        return( returnValue );
    }

	private String getJavaVersion()
	{
        Log logger = methIDgetJavaVersion;
        String returnValue = null;

        logger.debug("Begins...");

 		// Returns a Runtime.Version object
        Runtime.Version version = Runtime.version();
        
        // Extract version components cleanly
        int major = version.feature(); // e.g., 11, 17, 21
        int interim = version.interim();
        int update = version.update();

		returnValue = major + "." + interim + "." + update;

       	logger.debug("JavaVersion: " + returnValue );

        logger.debug("Ends...");

		return( returnValue );
	}

	private String getSpringBootVersion(){

        Log logger = methIDgetSpringBootVersion;
        String returnValue = null;

        logger.debug("Begins...");

        returnValue = SpringBootVersion.getVersion();

       	logger.debug("SpringBootVersion: " + returnValue );

        logger.debug("Ends...");

		return( returnValue );
	}

}
