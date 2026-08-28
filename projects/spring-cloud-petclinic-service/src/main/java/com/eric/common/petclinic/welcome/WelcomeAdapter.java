package com.eric.common.petclinic.welcome;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;

import com.eric.common.petclinic.util.HostInfoUtil;
import com.eric.common.petclinic.util.OSInfoUtil;

@Component
public class WelcomeAdapter 
{
	private static final Log methIDtoWelcomeModel, methIDgetCurrentDateTime, methIDgetJavaVersion, 
        methIDgetSpringBootVersion, methIDgetOSInfo;
	
    private static String applicationVersion;

	static
    {
        methIDtoWelcomeModel        = LogFactory.getLog(WelcomeAdapter.class.getName() + ".toWelcomeModel()");
        methIDgetCurrentDateTime    = LogFactory.getLog(WelcomeAdapter.class.getName() + ".getCurrentDateTime()");
        methIDgetJavaVersion    	= LogFactory.getLog(WelcomeAdapter.class.getName() + ".getJavaVersion()");		
        methIDgetSpringBootVersion 	= LogFactory.getLog(WelcomeAdapter.class.getName() + ".getSpringBootVersion()");
        methIDgetOSInfo 	        = LogFactory.getLog(WelcomeAdapter.class.getName() + ".getOSInfo()");        
    }

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
        String sessionID 					= null;
        String osInfo                       = null;

        logger.debug("Begins...");

        logger.debug("    ModelReceived: " + model.toString());
		logger.debug("ModelReceivedSize: " + model.asMap().size());

		logger.debug("APP_VERSION: " + applicationVersion);

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

        osInfo = this.getOSInfo();

		if (osInfo != null )
		{
			model.addAttribute("osInfo", osInfo);			
		}
		else
		{
			logger.error("***ERROR: osInfo is NULL!!!");
		}


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

	private String getSpringBootVersion()
    {

        Log logger = methIDgetSpringBootVersion;
        String returnValue = null;

        logger.debug("Begins...");

        returnValue = SpringBootVersion.getVersion();

       	logger.debug("SpringBootVersion: " + returnValue );

        logger.debug("Ends...");

		return( returnValue );
	}


    private String getOSInfo()
    {
        Log logger = methIDgetOSInfo;

        Map<String, String> osInfo  = null;
        String returnValue          = null;

        logger.debug("Begins...");

        osInfo = OSInfoUtil.getOSDetails();

        if ( osInfo != null )
        {
            returnValue = osInfo.get("osName");
            returnValue = returnValue + ": " + osInfo.get("lsbDescription");
            returnValue = returnValue + ": " + osInfo.get("lsbCodename");
            returnValue = returnValue + ": " + osInfo.get("osArch");            
        }
        else
        {
            logger.error("osInfo IS NULL!");
        }

        logger.debug("returnValue: " + returnValue);

        logger.debug("Ends...");

		return( returnValue );

    }

}
