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

package com.eric.common.petclinic.welcome;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.eric.common.petclinic.system.ApplicationContextProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
class WelcomeController {

	private static final Log methIDwelcome; //, methIDgetCurrentDateTime, methIDgetJavaVersion, methIDgetSpringBootVersion;
	
	static
    {
        methIDwelcome          		= LogFactory.getLog(WelcomeController.class.getName() + ".welcome()");
    }
	
	@GetMapping("/")
	public String welcome(Model model) 	
	{
		Log logger = methIDwelcome;

		WelcomeAdapter welcomeAdapter			= null;
		RequestAttributes requestAttributes 	= null;
		ApplicationContext context				= null;
		String appVersion 						= null;
		boolean keepOnTrucking					= true;

 		logger.debug("Begins...");

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

			requestAttributes 	= RequestContextHolder.getRequestAttributes();		

			if ( requestAttributes == null )
			{
				logger.error("***ERROR: requestAttributes Received is NULL!!");
				keepOnTrucking = false;
				break;
			}

			model = welcomeAdapter.toWelcomeModel(model, requestAttributes);

			if ( model == null )
			{
				logger.error("***ERROR: Model Received is NULL!!");
				keepOnTrucking = false;
				break;
			}

			logger.debug("ModelReceivedSize: " + model.asMap().size());			

			appVersion = model.getAttribute("applicationVersion").toString();

			if ( appVersion == null )
			{
				logger.error("***ERROR: appVersion Received is NULL!!");
				keepOnTrucking = false;
				break;
			}

			logger.info("APP_VERSION: " + appVersion);

			// Safety Purposes
			keepOnTrucking = false;
			break;
		}

		logger.debug("Ends...");

		return "welcome";
	}

}
