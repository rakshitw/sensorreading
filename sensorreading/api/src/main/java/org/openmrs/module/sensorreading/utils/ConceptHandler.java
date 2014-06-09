package org.openmrs.module.sensorreading.utils;

import java.text.ParseException;
import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.ConceptDatatype;
import org.openmrs.Obs;

/**
 * Handles concepts set as per their data types
 * and also saves the answer to the concept 
 * @author rakshit
 *
 */
public class ConceptHandler {
	Concept concept;

	public Concept getConcept() {
		if(concept == null)
			throw new NullPointerException();
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	public ConceptDatatype getConceptDatatype(){
		return concept.getDatatype();
	} 
	
	/**
	 * The answer must match with the correct data type, else it will result in
	 * runtime error. This method is required to provide a uniform interface, to
	 * allow the code to work for all types of answers.
	 * @param obs
	 * @param answer
	 */
	public void setAnswer(Obs obs, Object answer){
		if (concept.getDatatype().isBoolean())
				obs.setValueBoolean((Boolean) answer);
		
		else if (concept.getDatatype().isDate())
				obs.setValueDate((Date) answer);
		else if(concept.getDatatype().isText())
			try {
				obs.setValueAsString((String) answer);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		else if(concept.getDatatype().isNumeric())
				obs.setValueNumeric((Double) answer);;
		
				
	}
	

}
