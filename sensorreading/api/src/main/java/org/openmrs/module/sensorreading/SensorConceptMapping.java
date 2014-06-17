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
import java.util.Set;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */

/**
 * Maps sensor ids to sensor names.
 * @author rakshit
 *
 */
public class SensorConceptMapping extends BaseOpenmrsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SensorMapping sensor;
	private Set<Concept> concepts ;
	
	public SensorConceptMapping(){
		System.out.println("SensorConceptMapping() Constructor here");
	}
	
	public SensorConceptMapping(SensorMapping sensor,Set<Concept> concepts){
		System.out.println("SensorConceptMapping(sensor,concepts) Constructor here with id="+sensor.getId());
		setSensor(sensor);
		setConcepts(concepts);
	}
		
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	public SensorMapping getSensor() {
		return sensor;
	}
	public void setSensor(SensorMapping sensor) {
		System.out.println("Request in setSensor "+sensor.getSensor_name());
		this.sensor = sensor;
		System.out.println("Sensor Set Done!");
	}
	public Set<Concept> getConcepts() {
		return concepts;
	}
	public void setConcepts(Set<Concept> concepts) {
		System.out.println("Request in setConcepts with concepts set as : "+concepts.size());
		System.out.println("in concept set method");
        for (Concept c : concepts)System.out.println(c.getConceptId() + " "+c.getName());
		this.concepts = concepts;
		System.out.println("setConcepts Done!");
	}
	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	

	
}