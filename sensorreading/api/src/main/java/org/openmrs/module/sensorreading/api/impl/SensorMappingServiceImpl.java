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
package org.openmrs.module.sensorreading.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.sensorreading.SensorMapping;
import org.openmrs.module.sensorreading.api.SensorMappingService;
import org.openmrs.module.sensorreading.api.SensorReadingService;
import org.openmrs.module.sensorreading.api.db.SensorMappingDAO;

/**
 * It is a default implementation of {@link SensorReadingService}.
 */


public class SensorMappingServiceImpl extends BaseOpenmrsService implements SensorMappingService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SensorMappingDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(SensorMappingDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public SensorMappingDAO getDao() {
	    return dao;
    }

	@Override
	public SensorMapping saveSensorMapping(SensorMapping sensorMapping) {
		return dao.setSensorMapping(sensorMapping);
	}

	@Override
	public SensorMapping retrieveSensorMapping(int sensor_id) {
		return dao.getSensorMapping(sensor_id);
	}

	@Override
	public SensorMapping deleteSenorMapping(SensorMapping sensorMapping) {
		return dao.deleteSensorMapping(sensorMapping);
	}

	@Override
	public List<SensorMapping> getAllSensorMappings() {
		return getDao().getAll();
	}
}