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
import org.openmrs.module.sensorreading.SensorMapping;
import org.openmrs.module.sensorreading.api.db.SensorMappingDAO;
import org.openmrs.module.sensorreading.api.db.SensorReadingDAO;

/**
 * It is a default implementation of  {@link SensorReadingDAO}.
 */
public class HibernateSensorMappingDAO implements SensorMappingDAO {
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
	public SensorMapping setSensorMapping(SensorMapping sensorMapping) {
		sessionFactory.getCurrentSession().save(sensorMapping);
		return sensorMapping;
	}

	@Override
	public SensorMapping getSensorMapping(int sensor_id) {
		return (SensorMapping)sessionFactory.getCurrentSession().get(SensorMapping.class, sensor_id);
	}

	@Override
	public SensorMapping deleteSensorMapping(SensorMapping sensorMapping) {
		sessionFactory.getCurrentSession().delete(sensorMapping);
		return sensorMapping;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SensorMapping> getAll() {
		return (List<SensorMapping>) sessionFactory.getCurrentSession().createCriteria(SensorMapping.class).list();
	}
}