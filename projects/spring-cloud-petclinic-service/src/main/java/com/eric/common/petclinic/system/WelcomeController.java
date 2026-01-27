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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
class WelcomeController {

	private String appVersion = System.getenv("MK_IMAGE_PUSH_TARGET");

	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@GetMapping("/")
	public String welcome() 
	{
		log.debug("WelcomeController.welcome() Begins...");

		//logger.debug("WelcomeControllerZZ.welcome() Begins...");

		log.info("WelcomeController.welcome() appVersion: " + appVersion);

		log.debug("WelcomeController.welcome() Ends...");
		//logger.debug("WelcomeControllerZZ.welcome() Ends...");		

		return "welcome";
	}

}
