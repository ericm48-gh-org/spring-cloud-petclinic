package com.eric.common.petclinic.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import lombok.extern.slf4j.Slf4j;


import java.net.InetAddress;
import java.net.UnknownHostException;


@Slf4j
public class HostInfoUtil 
{

   	private static final Log methIDgetHostName, methIDgetHostIPAddress;
	
	static
    {
        methIDgetHostName          		= LogFactory.getLog(HostInfoUtil.class.getName() + ".getHostName()");
        methIDgetHostIPAddress     		= LogFactory.getLog(HostInfoUtil.class.getName() + ".getHostIPAddress()");        
    }

    public static String getHostName() 
    {
   		Log logger              = methIDgetHostName;
        InetAddress localhost   = null;

        String returnValue      = null;

		logger.debug("Begins...");

        // First, try getting from the HOSTNAME environment variable (common in Docker/cloud)
        returnValue = System.getenv("HOSTNAME");
        
        if (returnValue == null || returnValue.isEmpty()) 
        {
            try 
            {
                localhost = InetAddress.getLocalHost();

                if ( localhost != null )
                {
                    returnValue = localhost.getHostName();
                    logger.debug("HostNameReturned: " + returnValue);                    
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

        }

        logger.info("HostName: " + returnValue);

		logger.debug("Ends...");

        return( returnValue );
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

