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
package com.eric.common.petclinic.owner;

import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */

@Slf4j
@Controller
class OwnerController {

	private static final Log 
		methIDinitCreationForm, methIDprocessCreationForm, methIDprocessFindForm, 
		methIDinitUpdateOwnerForm, methIDprocessUpdateOwnerForm, methIDshowOwner;
	
	static
    {
		methIDinitCreationForm  		= LogFactory.getLog(OwnerController.class.getName() + ".initCreationForm()");
        methIDprocessCreationForm		= LogFactory.getLog(OwnerController.class.getName() + ".processCreationForm()");		
        methIDprocessFindForm    		= LogFactory.getLog(OwnerController.class.getName() + ".processFindForm()");
		methIDinitUpdateOwnerForm  		= LogFactory.getLog(OwnerController.class.getName() + ".initUpdateOwnerForm()");
		methIDprocessUpdateOwnerForm	= LogFactory.getLog(OwnerController.class.getName() + ".processUpdateOwnerForm()");
		methIDshowOwner					= LogFactory.getLog(OwnerController.class.getName() + ".showOwner()");		
    }

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final OwnerRepository owners;

	public OwnerController(OwnerRepository clinicService) {
		this.owners = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable(name = "ownerId", required = false) Integer ownerId) {
		return ownerId == null ? new Owner() : this.owners.findById(ownerId);
	}

	@GetMapping("/owners/new")
	public String initCreationForm(Map<String, Object> model) {

		Log logger = methIDinitCreationForm;

		logger.debug("Begins...");

		Owner owner = new Owner();
		model.put("owner", owner);

		logger.debug("Ends...");

		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/new")
	public String processCreationForm(@Valid Owner owner, BindingResult bindResult) {

		Log logger = methIDprocessCreationForm;

		logger.debug("Begins...");

		logger.debug("OwnerBefore: " + owner.toString());

		logger.debug("BindResult: " + bindResult.toString());	

		if (bindResult.hasErrors()) {

			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}

		logger.info("Saving Owner:" + owner.toString());
		this.owners.save(owner);
	
		logger.debug("Ends...");

		return "redirect:/owners/" + owner.getId();
	}

	@GetMapping("/owners/find")
	public String initFindForm(Map<String, Object> model) {

		log.debug("OwnerController.initFindForm() Begins...");

		model.put("owner", new Owner());

		log.debug("OwnerController.initFindForm() Ends...");

		return "owners/findOwners";
	}

	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Owner owner, BindingResult result, Model model) {
		// allow parameterless GET request for /owners to return all records

		Log logger = methIDprocessFindForm;

		logger.debug("OwnerController.processFindForm() Begins...");

		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name

		logger.info("Searching For Owner: " + owner.getLastName());

		Page<Owner> ownersResults = findPaginatedForOwnersLastName(page, owner.getLastName());

		if (ownersResults.isEmpty()) {
			// no owners found

			log.info("NO Owners Found!");

			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}

		logger.info("Found Results: " + ownersResults.getTotalElements());

		if (ownersResults.getTotalElements() == 1) {
			// 1 owner found
			owner = ownersResults.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}

		// multiple owners found

		logger.debug("Ends...");

		return addPaginationModel(page, model, ownersResults);
	}

	@GetMapping("/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		
		Log logger = methIDinitUpdateOwnerForm;

		logger.debug("Begins...");

		Owner owner = this.owners.findById(ownerId);
		model.addAttribute(owner);

		logger.info("Owner: " + owner.toString());

		logger.debug("Ends...");

		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId) {

		Log logger = methIDprocessUpdateOwnerForm;

		logger.debug("Begins...");

		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}

		owner.setId(ownerId);
		
		logger.info("Saving Owner:" + owner.toString());

		this.owners.save(owner);
		
		logger.debug("Ends...");

		return "redirect:/owners/{ownerId}";
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
		
		Log logger = methIDshowOwner;

		logger.debug("Begins...");

		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = this.owners.findById(ownerId);

		logger.info("Owner: " + owner.toString());
		
		mav.addObject(owner);

		logger.debug("Ends...");

		return mav;
	}

	private String addPaginationModel(int page, Model model, Page<Owner> paginated) 
	{
		model.addAttribute("listOwners", paginated);
		List<Owner> listOwners = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listOwners", listOwners);

		return "owners/ownersList";
	}

	private Page<Owner> findPaginatedForOwnersLastName(int page, String lastname) 
	{
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		
		return owners.findByLastName(lastname, pageable);
	}

}
