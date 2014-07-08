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
package org.openmrs.module.sensorreading.api.db.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.sensorreading.SensorConceptMapping;
import org.openmrs.module.sensorreading.SensorMapping;
import org.openmrs.module.sensorreading.api.SensorMappingService;
import org.openmrs.module.sensorreading.api.db.SensorConceptMappingDAO;
import org.openmrs.module.sensorreading.api.db.SensorReadingDAO;
import org.openmrs.module.sensorreading.utils.SensorConceptUtil;

/**
 * It is a default implementation of  {@link SensorReadingDAO}.
 */
public class HibernateSensorConceptMappingDAO implements SensorConceptMappingDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

	@Override
	public SensorConceptMapping setSensorConceptMapping(
			SensorConceptMapping sensorConceptMapping) {
		/* Added by Magus for Debugging */
		System.out.println("New Request in HibernateSensorConceptMappingDAO setSensorConceptMapping");
		System.out.println("Sensor: "+sensorConceptMapping.getSensor().getSensor_name());
		System.out.println("Concepts :");
		for (Concept c : sensorConceptMapping.getConcepts()){
			System.out.println(c);
		}
		/* Added by Magus for Debugging */
		
		Set<Concept> concepts =  sensorConceptMapping.getConcepts();
		for(Concept concept : concepts){
			SensorConceptUtil scu = new SensorConceptUtil();
			scu.setConcept(concept);
			scu.setSensor(sensorConceptMapping.getSensor());
			sessionFactory.getCurrentSession().save(scu);
		}
		return sensorConceptMapping;
	}
	
//TODO write get method
	@Override
	public SensorConceptMapping getSensorConceptMapping(int sensor_id) {
		
		SensorMapping sm = Context.getService(SensorMappingService.class).retrieveSensorMapping(sensor_id);
		Query conceptQuery = sessionFactory.getCurrentSession().createQuery("select E.concept from SensorConceptUtil E WHERE E.sensor = :userValue");
		conceptQuery.setParameter("userValue",sm);
		@SuppressWarnings("unchecked")
		List<Concept> conceptList = conceptQuery.list();
		Set<Concept> conceptSet = new HashSet<Concept>();
		for(Concept concept_element : conceptList)
		{
//			Concept concept = (Concept) concept_element;
			conceptSet.add(concept_element);
		}
		
		SensorConceptMapping scm = new SensorConceptMapping();
		scm.setConcepts(conceptSet);
		scm.setSensor(sm);
		return scm;
	}

	@Override
	public SensorConceptMapping deleteSensorConceptMapping(
			SensorConceptMapping sensorConceptMapping) {
		// TODO Auto-generated method stub
		Set<Concept> concepts =  sensorConceptMapping.getConcepts();
		for(Concept concept : concepts){
			SensorConceptUtil scu = new SensorConceptUtil();
			// Adding sensor_id and concept_id (check)
			scu.setConcept(concept);
			scu.setSensor(sensorConceptMapping.getSensor());
			scu.setConcept(concept);
			scu.setSensor(sensorConceptMapping.getSensor());
			sessionFactory.getCurrentSession().delete(scu);
		}
		return sensorConceptMapping;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SensorConceptMapping> getAll() {
		System.out.println("New Request in HibernateSensorConceptMappingDAO getAll");
		return getAllMapping();
	}
	/**
	 * Returns the list of concept-sensor mappings 
	 */
	//TODO delete print statements
	@Override
	public List<SensorConceptMapping> getAllMapping() {
		Query sensorConceptQuery = sessionFactory.getCurrentSession().createQuery("from SensorConceptUtil");
		List<SensorConceptUtil> sensorConceptList = sensorConceptQuery.list();
		System.out.println("the size of the sensorConcept list is");
		System.out.println(sensorConceptList.size());
		List<SensorConceptMapping> scmList = new ArrayList<SensorConceptMapping>();
		
		for(SensorConceptUtil sensorConceptElement : sensorConceptList)
		{	int sensor_id = sensorConceptElement.getSensor().getSensor_id();
			System.out.println("sensor id is DAO " + sensor_id);
			Set<Concept> conceptSet = new HashSet<Concept>();
			for (SensorConceptUtil sensorConceptUtilElement : sensorConceptList){
				if (sensor_id == sensorConceptUtilElement.getSensor().getSensor_id()){
					conceptSet.add(sensorConceptUtilElement.getConcept());
				}
			}
			SensorConceptMapping scm = new SensorConceptMapping();
			scm.setConcepts(conceptSet);
			scm.setSensor(sensorConceptElement.getSensor());
			scmList.add(scm);
		}
		
		return scmList;
	}

	
}