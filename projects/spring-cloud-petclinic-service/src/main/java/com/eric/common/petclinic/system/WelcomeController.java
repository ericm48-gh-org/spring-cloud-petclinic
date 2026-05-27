/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eric.common.petclinic.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eric.common.petclinic.util.HostInfoUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
class WelcomeController {

	private static final Log methIDwelcome, methIDgetCurrentDateTime;
	
	static
    {
        methIDwelcome          		= LogFactory.getLog(WelcomeController.class.getName() + ".welcome()");
        methIDgetCurrentDateTime    = LogFactory.getLog(WelcomeController.class.getName() + ".getCurrentDateTime()");
    }
	
 	@Value("${application.version}")
	private String APP_VERSION;


	@GetMapping("/")
	public String welcome(Model model) 	
	{
   		String hostName = null;	
		String ipAddress = null;

		Log logger = methIDwelcome;
 		logger.debug("Begins...");

		logger.info("APP_VERSION: " + APP_VERSION);

		hostName 	= HostInfoUtil.getHostName();		
		ipAddress 	= HostInfoUtil.getHostIPAddress();

        model.addAttribute("applicationVersion", APP_VERSION);
        model.addAttribute("hostName", hostName);
        model.addAttribute("ipAddress", ipAddress);		
        model.addAttribute("spring.message", "Hello, Thymeleaf in Spring Boot!");
        model.addAttribute("currentDate", getCurrentDateTime());

		logger.debug("ModelSize: " + model.asMap().size());

		logger.debug("Ends...");

		return "welcome";
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

}
