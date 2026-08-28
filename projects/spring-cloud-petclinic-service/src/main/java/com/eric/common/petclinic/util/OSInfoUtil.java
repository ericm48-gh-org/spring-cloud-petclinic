package com.eric.common.petclinic.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OSInfoUtil 
{
	private static final Log methIDgetOSDetails, methIDexecuteCommand;

	static
    {
        methIDgetOSDetails          = LogFactory.getLog(OSInfoUtil.class.getName() + ".getOSDetails()");
        methIDexecuteCommand        = LogFactory.getLog(OSInfoUtil.class.getName() + ".executeCommand()");
    }

    public static Map<String, String> getOSDetails() 
    {
        Log logger = methIDgetOSDetails;

        Map<String, String> returnValue = new HashMap<>();

        logger.debug("Begins...");

        // 1. Standard Java System Properties
        returnValue.put("osName", System.getProperty("os.name"));
        returnValue.put("osVersion", System.getProperty("os.version"));
        returnValue.put("osArch", System.getProperty("os.arch"));

        // 2. Linux-specific lsb_release data
        if (System.getProperty("os.name").toLowerCase().contains("linux")) 
        {
            returnValue.put("lsbDescription", executeCommand("lsb_release -ds"));
            returnValue.put("lsbRelease", executeCommand("lsb_release -rs"));
            returnValue.put("lsbCodename", executeCommand("lsb_release -cs"));
        }

        logger.debug("returnValueSize: " + returnValue.size());        

        logger.debug("Ends...");

        return( returnValue );
    }

    private static String executeCommand(String command) 
    {
        Log logger              = methIDexecuteCommand;
        BufferedReader reader   = null;

        logger.debug("Begins...");

        String returnValue = null;

        try 
        {
            Process process = new ProcessBuilder("sh", "-c", command).start();

            try 
            {
                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                if ( reader != null )
                {
                    returnValue =  reader.lines().collect(Collectors.joining("\n")).trim();
                }
                else
                {
                    returnValue = "***ERROR: BufferedReader is NULL!!";                    
                    logger.error(returnValue);
                }

            }
            catch (Exception ex) 
            {                
                logger.error("ERROR: ExceptionEncountered: " + ex.getLocalizedMessage());
                returnValue =  "Unavailable (lsb_release not installed)";
            }

        } 
        catch (Exception ex) 
        {
            logger.error("ERROR: ExceptionEncountered: " + ex.getLocalizedMessage());            
            returnValue = "Cannot Ope Shell; OS Does Not Appear To Be Linux."; 
        }

        logger.debug("returnValue: " + returnValue);

        logger.debug("Ends...");

        return( returnValue );

    }    





}