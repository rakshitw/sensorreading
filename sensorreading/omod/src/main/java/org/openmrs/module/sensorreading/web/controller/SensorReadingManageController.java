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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
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
		//int encounterId=2451;
		//int sensorId=1;
		System.out.println("\n\n1 the patient id id  " + patientId);
		Patient patient = (Patient) Context.getPatientService().getPatient(patientId);
		System.out.println("2 Fetched patient name " + patient.getGivenName());
		
		Encounter enc = new Encounter();
		System.out.println("3 Enc To String " +enc.toString());
//		enc.setEncounterId(encounterId);
		Date d = new Date(System.currentTimeMillis());
		enc.setEncounterDatetime(d);
		System.out.println("4 Date " +d.toString());
		
		enc.setPatient(patient);
//		List<EncounterType> et = (List<EncounterType>)Context.getEncounterService().getAllEncounterTypes();
		System.out.println("5 Enconunter patient name " +enc.getPatient().getGivenName());
		
		
		enc.setEncounterType((EncounterType)Context.getEncounterService().getEncounterType(1));
		System.out.println(enc.toString());
		List<Provider> provider = (List<Provider> )Context.getProviderService().getAllProviders();
		for(Provider p : provider)
			System.out.println("6 provider is" + p.getProviderId() + p.getId() + p.getAttributes() + p.getName());
		EncounterRole er = (EncounterRole)Context.getEncounterService().getEncounterRole(1);
		System.out.println("7 er is " +  er.getName() + er.getDescription());
		
		enc.setProvider((EncounterRole)Context.getEncounterService().getEncounterRole(1),(Provider) Context.getProviderService().getProvider(1));
		System.out.println("8 Provider is is "+((Provider) Context.getProviderService().getProvider(1)).getName());
		System.out.println("9 encounter string is " + enc.toString());
		Encounter enc_formed = (Encounter)Context.getEncounterService().saveEncounter(enc);
		Person person = (Person) Context.getPatientService().getPatient(patientId);
		System.out.println("10 person name  is " + person.getGivenName());
		
		Obs obs = new Obs();
//		obs.setObsId(1012);
		obs.setPerson(person);
		System.out.println("11 observation person name is " + obs.getPerson().getGivenName());
		Concept concept = (Concept)Context.getConceptService().getConcept(5090);
		obs.setObsDatetime(d);;
		obs.setConcept(concept);
		obs.setValueNumeric((double) 170);
		System.out.println("12 observation tostring is " +obs.toString());

//		obs.setEncounter(enc);
//		Context.getObsService().s
		Obs obs_formed = (Obs)Context.getObsService().saveObs(obs, "");
		SensorReading sensorReading = new SensorReading();
		sensorReading.setEncounter_id(enc_formed.getEncounterId());
		sensorReading.setPatient(patient);
		SensorMapping sensor = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(10);
		System.out.println("13 Sensor Mapping Object 10 is " +((SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(10)).getSensor_name());
		sensorReading.setSensor(sensor);
		sensorReading.setEncounter(enc_formed);
		sensorReading.setObservation(obs_formed);
		sensorReading.setDate(d);
		System.out.println("14 Trying to Create sensorReading object as " +sensorReading.toString());
		System.out.println("15 Transient sensorReading object is " +sensorReading.getSensor().getSensor_name()+" "+sensorReading.getEncounter().getId()+" ");
		Context.getService(SensorReadingService.class).saveSensorReading(sensorReading);
		Integer encounter_id = 22;
		SensorReading sr = (SensorReading)Context.getService(SensorReadingService.class).readSensorReading(encounter_id);
		//System.out.println("16 Sensor Reading for Encounter id " +encounter_id + " is "+((SensorReading)Context.getService(SensorReadingService.class).readSensorReading(encounter_id)).getSensor().getSensor_name());
		
		System.out.println("date is " + sr.getObservation().getObsDatetime());
		SensorMapping sensorMapping = new SensorMapping();
//		sensorMapping.setSensor_id(11);
		sensorMapping.setSensor_name("pulse meter");
		
		Context.getService(SensorMappingService.class).saveSensorMapping(sensorMapping);
		SensorMapping sm = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(12);
		
		System.out.println("done" + sm.getSensor_name());
		
		SensorConceptMapping sensorConceptMapping = new SensorConceptMapping();
		sensorConceptMapping.setSensor(sm);
		Concept concept2 = (Concept)Context.getConceptService().getConcept(5092);
		Set<Concept> concepts = new HashSet<Concept>();
		concepts.add(concept);
		concepts.add(concept2);
		sensorConceptMapping.setConcepts(concepts);
		Context.getService(SensorConceptMappingService.class).saveSensorConceptMapping(sensorConceptMapping);
		
		//		System.out.println("\n\nthe gender of patient is " + patient.getGender());
//		List<Patient> patients = (List<Patient>)Context.getPatientService().getAllPatients();
//		System.out.println("\niterating over patients");
//		for (Patient p : patients){
//			System.out.println("patient id is " + p.getPatientId() + p.getGivenName());
//		}
		//Get the users
//		List <User> providers =  (List<User>)Context.getUserService().getAllUsers();
//		for (User p: providers){
//			System.out.println(p.getUsername() + p.getUserId());
//		}
//		List<User> provider2 =  (List <User>) Context.getUserService().getAllUsers();
//		for (User p : provider2){
//			System.out.println("id = "+ p.getId() + p.getUsername()+ p.getFamilyName() + p.getDescription() + p.getRoles());
//		}
//		User provider = new User();
//		provider =  (User) Context.getUserService().getUser(1);
//		Location location = new Location();
//		location = (Location)Context.getLocationService().getLocation(1);
//		Date d2 = DateUtils.addHours(d, 60);
//		Appointment appt = new Appointment();
//		appt.setEndDatetime(d2);
//		appt.setStartDatetime(d);
//		appt.setPatient(patient);
//		appt.setLocation(location);
//		appt.setProvider(provider);
//		appt.setId(7);
//		Context.getService(AppointmentService.class).scheduleAppointment(appt);
//		
		
//		Obs obs = new Obs(patient, new Concept(5096), new Date(), new Location(1));
//		Concept concept = Context.getConceptService().getConcept(5096);
//		Obs obs = new Obs();
//		obs.setPerson(patient);
//		obs.setConcept(concept);
////		obs.setPerson(patient);
//		obs.setObsDatetime(visitDate);
//		obs.setValueDatetime(d2);
////		obs.setValueDatetime(visitDate);
//		System.out.println("observations made");
//		
//		System.out.println("Date is " + d);
//		System.out.println("observation id before saving is " + obs.getObsId());
////			obs.setObsId(1202);
//		obs = Context.getObsService().saveObs(obs, "because");
//		System.out.println("observation id after saving is " + obs.getObsId());
//		model.addObject("obs", obs);
		
		return model;
	}
}
