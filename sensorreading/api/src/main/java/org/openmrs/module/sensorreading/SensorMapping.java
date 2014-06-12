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
package org.openmrs.module.sensorreading;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */

/**
 * Maps sensor ids to sensor names.
 * @author rakshit
 *
 */
public class SensorMapping extends BaseOpenmrsData implements Serializable {

	/*
	 * Constructor is needed for Rest API Post Requests
	 */
	public SensorMapping(){
		System.out.println("SensorMapping() Constructor here");
	}
	
	public SensorMapping(Integer sensor_id,String sensor_name){
		System.out.println("SensorMapping(id,name) Constructor here with id="+sensor_id+" ,name = "+sensor_name);
		setSensor_id(sensor_id);
		setSensor_name(sensor_name);
	}
	
	private static final long serialVersionUID = 1L;
	
	private Integer sensor_id;
	private String sensor_name ;
	public String getSensor_name() {
		return sensor_name;
	}

	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	public Integer getSensor_id() {
		return sensor_id;
	}

	public void setSensor_id(Integer sensor_id) {
		this.sensor_id = sensor_id;
	}	
}