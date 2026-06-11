package com.eric.common.petclinic.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import lombok.extern.slf4j.Slf4j;


import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
public class HostInfoUtil 
{

   	private static final Log methIDgetNodeName, methIDgetDeploymentName, methIDgetHostIPAddress;
	
	static
    {
        methIDgetNodeName    		    = LogFactory.getLog(HostInfoUtil.class.getName() + ".getNodeName()");        
        methIDgetDeploymentName    		= LogFactory.getLog(HostInfoUtil.class.getName() + ".getDeploymentName()");
        methIDgetHostIPAddress     		= LogFactory.getLog(HostInfoUtil.class.getName() + ".getHostIPAddress()");        
    }

    public static String getNodeName() 
    {
   		Log logger              = methIDgetNodeName;
        String nodeName         = null;        

		logger.debug("Begins...");

        // The variable KUBE_NODE_NAME should be set with the nodeName from K8s spec.
        nodeName = System.getenv("KUBE_NODE_NAME");

        logger.debug("nodeNameReturned: " + nodeName);                            

		logger.debug("Ends...");

        return( nodeName );
    }

    public static String getDeploymentName() 
    {
   		Log logger              = methIDgetDeploymentName;
        String deploymentName   = null;        

		logger.debug("Begins...");

        // deploymentName is the HOSTNAME environment variable (common in Docker/cloud)
        deploymentName = System.getenv("HOSTNAME");

        logger.debug("deploymentNameReturned: " + deploymentName);                            

		logger.debug("Ends...");

        return( deploymentName );
    }

    public static String getHostIPAddress() 
    {
   		Log logger = methIDgetHostIPAddress;
        InetAddress localhost = null;

        String returnValue = null;

		logger.debug("Begins...");

        try 
        {
            localhost = InetAddress.getLocalHost();

            if ( localhost != null )
            {
                returnValue = localhost.getHostAddress();
                logger.debug("IPAddressReturned: " + returnValue);
            }
            else
            {
                logger.error ("***ERROR: localhost is NULL!");
            }

        } 
        catch (UnknownHostException uhe) 
        {
            logger.error("***ERROR UnknownHostException Encountered: " + uhe.getLocalizedMessage());
        }
        catch (Exception ex) 
        {
            logger.error("***ERROR Exception Encountered: " + ex.getLocalizedMessage());
        }    

		logger.debug("Ends...");     

        return( returnValue );
    }    
}

