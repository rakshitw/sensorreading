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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.Patient;
import org.openmrs.module.sensorreading.SensorReading;
import org.openmrs.module.sensorreading.api.db.SensorReadingDAO;

/**
 * It is a default implementation of  {@link SensorReadingDAO}.
 */
public class HibernateSensorReadingDAO implements SensorReadingDAO {
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
	public SensorReading setSensorValue(SensorReading sensorReading) {
		sessionFactory.getCurrentSession().save(sensorReading);
		return sensorReading;
		
	}

	@Override
	public SensorReading getSensorValue(Integer encounter_id) {
		// TODO Auto-generated method stub
		return (SensorReading)sessionFactory.getCurrentSession().get(SensorReading.class,  encounter_id);
	}
	
	//TODO
	@Override
	public List<SensorReading> getReadingByPatient(Patient patient) {
		// TODO Auto-generated method stub
//		return (List<SensorReading>)sessionFactory.getCurrentSession().get(SensorReading.class, patient_id);
		return null;
	}

	@Override
	public SensorReading deleteSensorReading(SensorReading sensorReading) {
		 sessionFactory.getCurrentSession().delete(sensorReading);	
		 return sensorReading;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SensorReading> getAll() {
		return (List<SensorReading>) sessionFactory.getCurrentSession().createCriteria(SensorReading.class).list();
	}
}