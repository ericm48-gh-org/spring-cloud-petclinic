package com.eric.common.petclinic.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import lombok.extern.slf4j.Slf4j;


import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
public class SmartHostNameUtil {

   	private static final Log methIDgetHostName;
	
	static
    {
        methIDgetHostName          		= LogFactory.getLog(SmartHostNameUtil.class.getName() + ".getHostName()");
    }

    public static String getHostName() 
    {
   		Log logger = methIDgetHostName;
 
        String returnValue = null;

		logger.debug("Begins...");

        // First, try getting from the HOSTNAME environment variable (common in Docker/cloud)
        returnValue = System.getenv("HOSTNAME");
        
        if (returnValue == null || returnValue.isEmpty()) 
        {
            // If not found, use the standard Java method as a fallback
            try 
            {
                returnValue = InetAddress.getLocalHost().getHostName();
            } 
            catch (UnknownHostException e) 
            {
                logger.error("Could not determine host name: " + e.getMessage());
                returnValue = "Unknown";
            }
        }

        logger.info("HostName: " + returnValue);

		logger.debug("Ends...");

        return( returnValue );
    }

    public static void getHostIp() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Host IP: " + localhost.getHostAddress());
            System.out.println("Host Name: " + localhost.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    
        return;
    }    
}

