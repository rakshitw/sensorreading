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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterProvider;
import org.openmrs.EncounterRole;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.sensorreading.SensorConceptMapping;
import org.openmrs.module.sensorreading.SensorMapping;
import org.openmrs.module.sensorreading.SensorReading;
import org.openmrs.module.sensorreading.api.SensorConceptMappingService;
import org.openmrs.module.sensorreading.api.SensorMappingService;
import org.openmrs.module.sensorreading.api.SensorReadingService;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.util.PrivilegeConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * The main controller.
 */
@Controller
public class  SensorReadingManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	public void logger(String post, HttpServletRequest request, HttpServletResponse response){
		System.out.println("request: "+request);
		System.out.println("request getContentType : "+request.getContentType());
		System.out.println("response : "+response);
		System.out.println("String post: "+post);
	}
	
	@RequestMapping(value = "/module/sensorreading/newr", method = RequestMethod.POST)
	@ResponseBody
	public Object newr(@RequestBody String post, HttpServletRequest request, HttpServletResponse response) throws ResponseException, JSONException {
		logger(post,request,response);
		JSONObject header = new JSONObject(post);
		System.out.println(header);
		return "hi";
	}
	
	/*
	 * Creates/Appends Sensor Readings using JSON Payload like,
	 * POST /openmrs/module/sensorreading/sr.form
	 * {"sensor":"12","patient":"12","readings":{"5090":"170","5092":"10"}}
	 * Changed to uuid now ^^
	 * Integer sensor,Integer patient,concept_id : value
	 * Date : For Now Current Time
	 * Provider : Super User
	 * Location : Not Filled
	 * issue: Reading Values can be anything .. outsource observation?
	 * 
	 * Doesn't Look at it SCM actually exists , add that validation
	 */
	@RequestMapping(value = "/module/sensorreading/sr", method = RequestMethod.POST)
	@ResponseBody
	public Object sr_creator(@RequestBody String post, HttpServletRequest request, HttpServletResponse response) throws ResponseException, JSONException {
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_PATIENTS);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_ENCOUNTERS);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_ENCOUNTER_TYPES);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_ENCOUNTER_ROLES);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_PROVIDERS);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_CONCEPTS);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_PERSONS);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_VISITS);
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_USERS);

		Context.addProxyPrivilege(PrivilegeConstants.MANAGE_PROVIDERS);
		Context.addProxyPrivilege(PrivilegeConstants.MANAGE_ENCOUNTER_ROLES);
		
		
		Context.addProxyPrivilege(PrivilegeConstants.ADD_OBS);
		Context.addProxyPrivilege(PrivilegeConstants.ADD_ENCOUNTERS);
		
		System.out.println("New Request in SensorReadingManageController sr_creator");
		logger(post,request,response);
		JSONObject header = new JSONObject(post);
		String sensor_key = "sensor";
		String patient_key = "patient";
		String readings_key = "readings";

		SensorReading sensorReading = new SensorReading();
		
		// Setting Sensor as Per Request Header
		Integer sensor_id = Integer.parseInt((String) header.get(sensor_key));
		SensorMapping sensor = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(sensor_id);

		// Needed for Encounter and Sensor Reading
		Date d = new Date(System.currentTimeMillis());
		
		// Needed for Encounter
		Integer patient_id = Integer.parseInt((String) header.get(patient_key));
		System.out.println("Getting Patient");
		Patient patient = (Patient) Context.getPatientService().getPatient(patient_id);
		System.out.println("Done Getting Patient");
		
		// Creating New Encounter for given patientId
		System.out.println("Creating new Encounter");
		Encounter enc = new Encounter();
		System.out.println("Done new Encounter");
		enc.setEncounterDatetime(d);
		enc.setPatient(patient);
		System.out.println("Setting EncounterType");
		enc.setEncounterType((EncounterType)Context.getEncounterService().getEncounterType(1));
		System.out.println("Done EncounterType");
		
		List<User> a = Context.getUserService().getAllUsers();
		for(User x : a){
			System.out.println(x.getUsername());
			System.out.println(x);			
		}
		
		System.out.println("Getting User");
		//User u = (User) Context.getUserService().getUserByUsername("magus");
		//TODO Change Hard coded creator to the one who actually creates
		User u = a.get(0);
		System.out.println("Got User : " + u + " " + u.getName()+" "+u.getUsername());
		
		System.out.println("Setting User");
		enc.setCreator(u);
		System.out.println("Done");
		
		//Currently not used but in future provider would be expected in header
		//List<Provider> provider = (List<Provider> )Context.getProviderService().getAllProviders();
		//EncounterRole er = (EncounterRole)Context.getEncounterService().getEncounterRole(1);
		
		System.out.println("Setting ProviderType");
		Provider aprovider = (Provider) Context.getProviderService().getProvider(1);
		
		EncounterProvider encpro = new EncounterProvider();
		encpro.setCreator(u);
		encpro.setEncounter(enc);
		encpro.setProvider(aprovider);
		encpro.setEncounterRole((EncounterRole)Context.getEncounterService().getEncounterRole(1));
		
		Set <EncounterProvider> ep_set = new HashSet<EncounterProvider>() ;
		
		ep_set.add(encpro);
		enc.setEncounterProviders(ep_set);
		
		System.out.println("Provider is : "+aprovider+" "+aprovider.getName());
		//enc.setProvider((EncounterRole)Context.getEncounterService().getEncounterRole(1),aprovider);
		System.out.println("Done Setting ProviderType");
		
		// Needed for Observation
		System.out.println("Getting Person");
		Person person = (Person) Context.getPatientService().getPatient(patient_id);
		System.out.println("Done Getting Person");
		
        //New Implementation for Creating Concept and Encounter
		/*
 		 * Setting multiple concepts by creating multiple observations and
 		 * adding it to encounter everytime.
 		 */
 		Set<Obs> observations = new HashSet<Obs>();
 		JSONObject jObject = header.getJSONObject(readings_key);
		Iterator<String> keys = jObject.keys();

        while( keys.hasNext() ){
    		System.out.println("in loop");
    		String key = keys.next();
//          Integer concept_id = Integer.parseInt(key);
    		String concept_uuid = key;
           
    		String value = (String) jObject.get(key);

//          System.out.println("Creating Reading for Concept "+ concept_id +" "+key+", as :"+value);            
            System.out.println("Creating Reading for Concept "+ concept_uuid +" "+key+", as :"+value);            
            
            Obs obs = new Obs();
            obs.setCreator(u);
            obs.setPerson(person);
			obs.setObsDatetime(d);

//			Concept concept = (Concept)Context.getConceptService().getConcept(concept_id);
			Concept concept = (Concept)Context.getConceptService().getConceptByUuid(concept_uuid);

			obs.setConcept(concept);
			try { 
				obs.setValueAsString(value);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			enc.addObs(obs);
			observations.add(obs);
 		}
 		
 		
// 		EncounterProvider ep_formed = (EncounterProvider)Context.getProviderService().
		Encounter enc_formed = (Encounter)Context.getEncounterService().saveEncounter(enc);
		
       	sensorReading.setSensor(sensor);
		sensorReading.setDate(d);
		sensorReading.setPatient(patient);
		sensorReading.setEncounter_id(enc_formed.getEncounterId());
		sensorReading.setEncounter(enc_formed);
		sensorReading.setObservations(observations);
        System.out.println("finally submitting object"+ sensorReading);
 		//
		
		return Context.getService(SensorReadingService.class).saveSensorReading(sensorReading);
	}
	
	/*
	 * Creates/Appends Sensor Concept Mappings using JSON Payload like,
	 * POST /openmrs/module/sensorreading/scm.form
	 * {"sensor":"12","concepts":["1011","1012"]}
	 * Goes in Internal Server Error , if invalid details or resubmission of existing sensor,concept entry in table
	 */
	@RequestMapping(value = "/module/sensorreading/scm", method = RequestMethod.POST)
	@ResponseBody
	public Object scm_creator(@RequestBody String post, HttpServletRequest request, HttpServletResponse response) throws ResponseException, JSONException {
		System.out.println("New Request in SensorReadingManageController scm_creator");
		Context.addProxyPrivilege(PrivilegeConstants.VIEW_CONCEPTS);
		
		logger(post,request,response);
		JSONObject header = new JSONObject(post);
		String sensor_key = "sensor";
		String concepts_key = "concepts";
		
		SensorConceptMapping scm = new SensorConceptMapping();
		
		Integer id = Integer.parseInt((String) header.get(sensor_key));
		SensorMapping sm = Context.getService(SensorMappingService.class).retrieveSensorMapping(id);
		System.out.println("Setting Sensor as : "+sm.getSensor_name());
		scm.setSensor(sm);
		
		Set<Concept> concepts = new HashSet<Concept>();
		JSONArray jArray = header.getJSONArray(concepts_key);
		System.out.println(jArray);
		System.out.println(" Starting loop total iterations "+jArray.length());
		System.out.println(" First Item "+jArray.get(0));
		for (int it = 0 ; it < jArray.length(); it++) {
			System.out.println("This is loop iteration "+it);
			Integer concept_id = Integer.parseInt( (String) jArray.get(it));
			System.out.println("concept id :"+ concept_id);
			//concept_id = 5090;
			Concept concept = (Concept)Context.getConceptService().getConcept(concept_id);
			System.out.println("Fetched Concept Successfully");
			System.out.println("Adding Concept : "+concept.getDisplayString());
			concepts.add(concept);
        }
		scm.setConcepts(concepts);
		
		System.out.println("Final SensorConceptMapping as : "+scm);
		return Context.getService(SensorConceptMappingService.class).saveSensorConceptMapping(scm);
		//return "done";
	}

	@RequestMapping(value = "/module/sensorreading/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
		}

//		@InitBinder
//	    public void initBinder(WebDataBinder binder) { 
//	    	binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), false)); 
//	    }    
	//    

//		@RequestMapping(method=RequestMethod.GET)
//		public void populateForm(ModelMap map) { 
//			List<Date> dates = new ArrayList<Date>();  
//			List<Location> locations = Context.getLocationService().getAllLocations();
//			map.put("dates", dates);
//			map.put("locations", locations);
//		}	


		/**
		 * @should set gender to male
		 * @should return non null patient
		 * @should return patient with patient id 
		 * @param map
		 */
//		@ModelAttribute("patient")
//		public Patient formBackingObject(
//				@RequestParam(value = "patientId", required = false) Integer patientId) { 
//			Patient patient = Context.getPatientService().getPatient(patientId);
//			log.error("Patient: " + patient);
	//
//			
//			if (patient == null) { 
//				patient = new Patient();
//				patient.setGender("male");
//			}
//			
//			
//			return patient;
//		}
	//	
		/**
		 * @should save observation for visit date
		 * @param patient
		 * @param visitDate
		 */
		@RequestMapping(value = "/module/sensorreading/manage",method=RequestMethod.POST)
		public ModelAndView processForm(
				
//				@ModelAttribute("patient") Patient patient,
				@RequestParam("patientId") int patientId,
//				@RequestParam("sensorId") int sensorId,

				HttpServletRequest request) {
			ModelAndView model = new ModelAndView();

			System.out.println("\n\nthe patient id id  " + patientId);
			Patient patient = (Patient) Context.getPatientService().getPatient(patientId);

			/*
			 * Set an encounter, make an observation, add that observation to the
			 * encounter save the encounter. get other parameters and along with
			 * encounter and observation form the sensorReading object and save it
			 */

			Encounter enc = new Encounter();
			Date d = new Date(System.currentTimeMillis());
			enc.setEncounterDatetime(d);
			enc.setPatient(patient);


			enc.setEncounterType((EncounterType)Context.getEncounterService().getEncounterType(1));
//			List<Provider> provider = (List<Provider> )Context.getProviderService().getAllProviders();
//			for(Provider p : provider)
//				System.out.println("provider is" + p.getProviderId() + p.getId() + p.getAttributes() + p.getName());
			EncounterRole er = (EncounterRole)Context.getEncounterService().getEncounterRole(1);
			System.out.println("er is " +  er.getName() + er.getDescription());

			enc.setProvider((EncounterRole)Context.getEncounterService().getEncounterRole(1),(Provider) Context.getProviderService().getProvider(1));
			Person person = (Person) Context.getPatientService().getPatient(patientId);

			/*
			 * Setting multiple concepts by creating multiple observations and
			 * adding it to encounter everytime.
			 */
			SensorConceptMapping sensorConcepts = (SensorConceptMapping)Context.getService(SensorConceptMappingService.class).retrieveSensorConceptMapping(12);
			Set<Concept> retrievedConcepts = sensorConcepts.getConcepts();
			int counter = 0;
			Set<Obs> observations = new HashSet<Obs>();

			for (Concept retrievedElement : retrievedConcepts){
					System.out.println("in loop");
					Obs obs = new Obs();
					obs.setPerson(person);
					//change 5090 by retreiveElement.getConceptId();
					Concept concept = (Concept)Context.getConceptService().getConcept(5090);
					obs.setObsDatetime(d);
					obs.setConcept(concept);
					int height = counter + 170;
					String val = Integer.toString(height);
					System.out.println("heigh is " + val);
					try { 
						obs.setValueAsString(val);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					counter++;
					enc.addObs(obs);
					observations.add(obs);
			}



			Encounter enc_formed = (Encounter)Context.getEncounterService().saveEncounter(enc);
			SensorReading sensorReading = new SensorReading();
			sensorReading.setPatient(patient);
			SensorMapping sensor = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(10);
			sensorReading.setSensor(sensor);
			sensorReading.setEncounter(enc_formed);
			sensorReading.setObservations(observations);
			sensorReading.setDate(d);





			Context.getService(SensorReadingService.class).saveSensorReading(sensorReading);
//			SensorReading sr = (SensorReading)Context.getService(SensorReadingService.class).readSensorReading(encounter_id);
//			
//			System.out.println("date is " + sr.getObservation().getObsDatetime());
			SensorMapping sensorMapping = new SensorMapping();
//			sensorMapping.setSensor_id(11);
			sensorMapping.setSensor_name("pulse meter");

			Context.getService(SensorMappingService.class).saveSensorMapping(sensorMapping);
			SensorMapping sm = (SensorMapping)Context.getService(SensorMappingService.class).retrieveSensorMapping(12);

			System.out.println("done" + sm.getSensor_name());

			/*
			 * Adding concepts to a sensor
			 */
			SensorConceptMapping sensorConceptMapping = new SensorConceptMapping();
			sensorConceptMapping.setSensor(sm);
			Concept concept2 = (Concept)Context.getConceptService().getConcept(5092);
			Set<Concept> concepts = new HashSet<Concept>();


			/*
			 * Commented out as it requires new pair every time(composite id)
			 */
//			concepts.add(concept);
//			concepts.add(concept2);
//			sensorConceptMapping.setConcepts(concepts);
//			Context.getService(SensorConceptMappingService.class).saveSensorConceptMapping(sensorConceptMapping);



			return model;
	}
}
