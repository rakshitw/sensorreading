/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.sensorreading.web.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.EncounterRole;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.sensorreading.SensorConceptMapping;
import org.openmrs.module.sensorreading.SensorMapping;
import org.openmrs.module.sensorreading.SensorReading;
import org.openmrs.module.sensorreading.api.SensorConceptMappingService;
import org.openmrs.module.sensorreading.api.SensorMappingService;
import org.openmrs.module.sensorreading.api.SensorReadingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * The main controller.
 */
@Controller
public class  SensorReadingManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/sensorreading/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	}
	
//	@InitBinder
//    public void initBinder(WebDataBinder binder) { 
//    	binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), false)); 
//    }    
//    

//	@RequestMapping(method=RequestMethod.GET)
//	public void populateForm(ModelMap map) { 
//		List<Date> dates = new ArrayList<Date>();  
//		List<Location> locations = Context.getLocationService().getAllLocations();
//		map.put("dates", dates);
//		map.put("locations", locations);
//	}	
	
	
	/**
	 * @should set gender to male
	 * @should return non null patient
	 * @should return patient with patient id 
	 * @param map
	 */
//	@ModelAttribute("patient")
//	public Patient formBackingObject(
//			@RequestParam(value = "patientId", required = false) Integer patientId) { 
//		Patient patient = Context.getPatientService().getPatient(patientId);
//		log.error("Patient: " + patient);
//
//		
//		if (patient == null) { 
//			patient = new Patient();
//			patient.setGender("male");
//		}
//		
//		
//		return patient;
//	}
//	
	/**
	 * @should save observation for visit date
	 * @param patient
	 * @param visitDate
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processForm(
//			@ModelAttribute("patient") Patient patient,
			@RequestParam("patientId") int patientId,
//			@RequestParam("sensorId") int sensorId,

			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		
		//Integer patientId = Integer.parseInt(request.getParameter("patientId"));		
		//Patient patient = 
		System.out.println("\n\nthe patient id id  " + patientId);
		Patient patient = (Patient) Context.getPatientService().getPatient(patientId);
		
		/*
		 * Set an encounter, make an observation, add that observation to the
		 * encounter save the encounter. get other parameters and along with
		 * encounter and observation form the sensorReading object and save it
		 */
		
		Encounter enc = new Encounter();
//		enc.setEncounterId(encounterId);
		Date d = new Date(System.currentTimeMillis());
		enc.setEncounterDatetime(d);
		enc.setPatient(patient);
//		List<EncounterType> et = (List<EncounterType>)Context.getEncounterService().getAllEncounterTypes();
		
		
		enc.setEncounterType((EncounterType)Context.getEncounterService().getEncounterType(1));
//		List<Provider> provider = (List<Provider> )Context.getProviderService().getAllProviders();
//		for(Provider p : provider)
//			System.out.println("provider is" + p.getProviderId() + p.getId() + p.getAttributes() + p.getName());
		EncounterRole er = (EncounterRole)Context.getEncounterService().getEncounterRole(1);
		System.out.println("er is " +  er.getName() + er.getDescription());
		
		enc.setProvider((EncounterRole)Context.getEncounterService().getEncounterRole(1),(Provider) Context.getProviderService().getProvider(1));
		Person person = (Person) Context.getPatientService().getPatient(patientId);
		
		Obs obs = new Obs();
//		obs.setObsId(1012);
		obs.setPerson(person);
		Concept concept = (Concept)Context.getConceptService().getConcept(5090);
		obs.setObsDatetime(d);;
		obs.setConcept(concept);
		try {
			obs.setValueAsString("173");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		enc.addObs(obs);
		Encounter enc_formed = (Encounter)Context.getEncounterService().saveEncounter(enc);
		Obs obs_formed = (Obs)Context.getObsService().getObsByUuid(obs.getUuid());
		SensorReading sensorReading = new SensorReading();
//		sensorReading.setEncounter_id(enc_formed.getEncounterId());
		sensorReading.setPatient(patient);
		SensorMapping sensor = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(10);
		sensorReading.setSensor(sensor);
		sensorReading.setEncounter(enc_formed);
		sensorReading.setObservation(obs_formed);
		sensorReading.setDate(d);
		
		
		
		
		
		Context.getService(SensorReadingService.class).saveSensorReading(sensorReading);
//		SensorReading sr = (SensorReading)Context.getService(SensorReadingService.class).readSensorReading(encounter_id);
//		
//		System.out.println("date is " + sr.getObservation().getObsDatetime());
		SensorMapping sensorMapping = new SensorMapping();
//		sensorMapping.setSensor_id(11);
		sensorMapping.setSensor_name("pulse meter");
		
		Context.getService(SensorMappingService.class).saveSensorMapping(sensorMapping);
		SensorMapping sm = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(12);
		
		System.out.println("done" + sm.getSensor_name());
		Context.getService(SensorConceptMappingService.class).retrieveSensorMapping(12);
		
		/*
		 * Adding concepts to a sensor
		 */
		SensorConceptMapping sensorConceptMapping = new SensorConceptMapping();
		sensorConceptMapping.setSensor(sm);
		Concept concept2 = (Concept)Context.getConceptService().getConcept(5092);
		Set<Concept> concepts = new HashSet<Concept>();
		
		List<SensorConceptMapping> scmList = (List<SensorConceptMapping>)Context.getService(SensorConceptMappingService.class).getAll();
		for (SensorConceptMapping scmElement : scmList ){
			System.out.println("sensor is " + scmElement.getSensor().getSensor_id());
			for (Concept cp : scmElement.getConcepts()){
				System.out.println("concepts for scmElement.getSensor().getSensor_id() is " + cp.getConceptId());
			}
		}
		/*
		 * Commented out as it requires new pair every time(composite id)
		 */
//		concepts.add(concept);
//		concepts.add(concept2);
//		sensorConceptMapping.setConcepts(concepts);
//		Context.getService(SensorConceptMappingService.class).saveSensorConceptMapping(sensorConceptMapping);
		
	
		
		return model;
	}
}
