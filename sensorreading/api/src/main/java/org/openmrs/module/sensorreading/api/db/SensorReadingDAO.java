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
package org.openmrs.module.sensorreading.api.db;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.module.sensorreading.SensorReading;
import org.openmrs.module.sensorreading.api.SensorReadingService;

/**
 *  Database methods for {@link SensorReadingService}.
 */
public interface SensorReadingDAO {
	
	//TODO: add update method
	public SensorReading setSensorValue(SensorReading sensorReading);
	public SensorReading getSensorValue(Integer encounter_id);
	public SensorReading deleteSensorReading(SensorReading sensorReading);
	public List<SensorReading> getReadingByPatient(Patient patient);
	public List<SensorReading> getAll();
}