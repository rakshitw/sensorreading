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
import org.openmrs.module.sensorreading.SensorReading;
import org.openmrs.module.sensorreading.api.SensorReadingService;
import org.openmrs.module.sensorreading.api.db.SensorReadingDAO;

/**
 * It is a default implementation of {@link SensorReadingService}.
 */


public class SensorReadingServiceImpl extends BaseOpenmrsService implements SensorReadingService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SensorReadingDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(SensorReadingDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public SensorReadingDAO getDao() {
	    return dao;
    }

	@Override
	public SensorReading saveSensorReading(SensorReading sensorReading) {
		return dao.setSensorValue(sensorReading);
		
	}

	@Override
	public SensorReading readSensorReading(Integer encounter_id) {
		return dao.getSensorValue(encounter_id);
	}

	@Override
	public SensorReading deleteSenorReading(SensorReading sensorReading) {
		return dao.deleteSensorReading(sensorReading);
	}
	
	@Override
	public List<SensorReading> getAllSensorReadings() {
		return getDao().getAll();
	}
}