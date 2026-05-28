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
package com.eric.common.petclinic.pet;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eric.common.petclinic.owner.Owner;
import com.eric.common.petclinic.owner.OwnerRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Slf4j
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController 
{
	private static final Log 
		methIDinitCreationForm, methIDprocessCreationForm, methIDfindPet,
		methIDinitUpdateForm, methIDprocessUpdateForm;
	
	static
    {
		methIDinitCreationForm  		= LogFactory.getLog(PetController.class.getName() + ".initCreationForm()");
        methIDprocessCreationForm		= LogFactory.getLog(PetController.class.getName() + ".processCreationForm()");
		methIDinitUpdateForm  			= LogFactory.getLog(PetController.class.getName() + ".initUpdateForm()");
		methIDprocessUpdateForm			= LogFactory.getLog(PetController.class.getName() + ".processUpdateForm()");
		methIDfindPet					= LogFactory.getLog(PetController.class.getName() + ".findPet()");

		//methIDprocessFindForm    		= LogFactory.getLog(PetController.class.getName() + ".processFindForm()");
		
    }

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private final OwnerRepository owners;

	public PetController(OwnerRepository owners) {
		this.owners = owners;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.owners.findPetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.owners.findById(ownerId);
	}

	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("ownerId") int ownerId,
			@PathVariable(name = "petId", required = false) Integer petId) 
	{
		Log logger = methIDfindPet;
		Pet returnValue = null;

		logger.debug("Begins...");
		
		logger.debug("OwnerId: " + ownerId);		

		//return petId == null ? new Pet() : this.owners.findById(ownerId).getPet(petId);

		if ( petId == null )
		{
			returnValue = new Pet();
		}
		else
		{
			returnValue = this.owners.findById(ownerId).getPet(petId);
		}

		logger.debug("returnValue: " + returnValue);

		logger.debug("Ends...");

		return( returnValue );
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) 
	{
		Log logger = methIDinitCreationForm;

		logger.debug("Begins...");

		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);

		logger.debug("Ends...");

		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult bindResult, ModelMap model) 
	{
		Log logger = methIDprocessCreationForm;

		logger.debug("Begins...");

		if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) 
		{
			bindResult.rejectValue("name", "duplicate", "already exists");
		}

		logger.debug("OwnerBefore: " + owner.toString());

		logger.debug("Pet: " + pet.toString());

		logger.debug("BindResult: " + bindResult.toString());	

		owner.addPet(pet);
		if (bindResult.hasErrors()) 
		{
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		this.owners.save(owner);	

		logger.debug("OwnerAfter: " + owner.toString());

		logger.debug("Ends...");		

		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(Owner owner, @PathVariable("petId") int petId, ModelMap model) 
	{
		Log logger = methIDinitUpdateForm;

		logger.debug("Begins...");

		Pet pet = owner.getPet(petId);
		model.put("pet", pet);
		
		logger.debug("Ends...");		

		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid Pet pet, BindingResult bindResult, Owner owner, ModelMap model) 
	{
		Log logger = methIDprocessUpdateForm;

		logger.debug("Begins...");

		logger.debug("OwnerBefore: " + owner.toString());

		logger.debug("bindResult: " + bindResult.toString());

		if (bindResult.hasErrors()) 
		{
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		logger.debug("Pet: " + pet.toString());		

		owner.addPet(pet);
		this.owners.save(owner);

		logger.debug("OwnerAfter: " + owner.toString());		

		logger.debug("Ends...");

		return "redirect:/owners/{ownerId}";
	}

}
