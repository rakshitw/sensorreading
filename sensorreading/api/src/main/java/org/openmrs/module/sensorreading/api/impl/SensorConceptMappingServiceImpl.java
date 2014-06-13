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
import org.openmrs.module.sensorreading.SensorConceptMapping;
import org.openmrs.module.sensorreading.api.SensorConceptMappingService;
import org.openmrs.module.sensorreading.api.SensorReadingService;
import org.openmrs.module.sensorreading.api.db.SensorConceptMappingDAO;
import org.openmrs.module.sensorreading.api.db.SensorMappingDAO;

/**
 * It is a default implementation of {@link SensorReadingService}.
 */


public class SensorConceptMappingServiceImpl extends BaseOpenmrsService implements SensorConceptMappingService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SensorConceptMappingDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(SensorConceptMappingDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public SensorConceptMappingDAO getDao() {
	    return dao;
    }

	@Override
	public SensorConceptMapping saveSensorConceptMapping(
			SensorConceptMapping sensorConceptMapping) {
		return dao.setSensorConceptMapping(sensorConceptMapping);
	}

	@Override
	public SensorConceptMapping retrieveSensorMapping(int sensor_id) {
		// TODO Auto-generated method stub
		return dao.getSensorConceptMapping(sensor_id);
	}

	@Override
	public SensorConceptMapping deleteSenorMapping(
			SensorConceptMapping sensorConceptMapping) {
		// TODO Auto-generated method stub
		return dao.deleteSensorConceptMapping(sensorConceptMapping);
	}

	@Override
	public List<SensorConceptMapping> getAll() {
		return dao.getAllMapping();
	}


}