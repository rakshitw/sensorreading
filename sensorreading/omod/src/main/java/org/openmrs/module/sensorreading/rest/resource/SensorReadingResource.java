package org.openmrs.module.sensorreading.rest.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.sensorreading.SensorReading;
import org.openmrs.module.sensorreading.api.SensorReadingService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
//import org.openmrs.module.sensorreading.rest.controller.SensorReadingRestController;
//import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;

@Resource(name = RestConstants.VERSION_1 + "/sensor" + "/sr", supportedClass = SensorReading.class, supportedOpenmrsVersions = "1.*.*")
public class SensorReadingResource extends DataDelegatingCrudResource<SensorReading> {

	@Override
	public SensorReading newDelegate() {
		System.out.println("New Request in SensorReading newDelegate");
		return new SensorReading();
	}

	@Override
	public SensorReading save(SensorReading delegate) {
		System.out.println("New Request in SensorReading save");
		return Context.getService(SensorReadingService.class).saveSensorReading(delegate);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		System.out.println("New Request in SensorReading getRepresentationDescription");
		/*
		 * 
			private Integer encounter_id;
			private SensorMapping sensor;
			private Obs observation;
			private Patient patient;
			private Encounter encounter;
			private Date date;
		 */
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof FullRepresentation) {
			description.addProperty("encounter_id");
			description.addProperty("sensor", Representation.FULL);
			description.addProperty("observation",Representation.FULL);
			description.addProperty("patient", Representation.FULL);
			description.addProperty("encounter",Representation.FULL);
			description.addProperty("date",Representation.FULL);
			description.addSelfLink();			
			return description;
		} else {
			description.addProperty("encounter_id");
			description.addProperty("sensor", Representation.FULL);
			description.addProperty("observation",Representation.FULL);
			description.addProperty("patient", Representation.FULL);
			description.addProperty("encounter",Representation.FULL);
			description.addProperty("date",Representation.FULL);
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		}
	}

	@Override
	public SensorReading getByUniqueId(String encounter_id) {
		System.out.println("New Request in SensorReading getByUniqueId");
		Integer id = Integer.parseInt(encounter_id);
		System.out.println("encounter_id = "+id);
		SensorReading obj = Context.getService(SensorReadingService.class).readSensorReading(id);
		System.out.println("Fetched SensorReading = "+obj.getEncounter_id());
		return obj; 
	}

	@Override
	protected void delete(SensorReading delegate, String reason,
		RequestContext context) throws ResponseException {
		System.out.println("New Request in SensorReading delete , reason : "+reason);
		System.out.println("Deleting "+delegate.getEncounter_id());
		Context.getService(SensorReadingService.class).deleteSenorReading(delegate);		
	}

	@Override
	public void purge(SensorReading delegate, RequestContext context)
			throws ResponseException {
		// TODO Auto-generated method stub
		System.out.println("New Request in SensorReading purge which is not defined yet");
	}	
	
	@Override
	 public DelegatingResourceDescription getCreatableProperties() {
		System.out.println("New Request in SensorReading getCreatableProperties");
	 	DelegatingResourceDescription description = new DelegatingResourceDescription();
	 	description.addRequiredProperty("encounter_id");
	 	description.addRequiredProperty("sensor");
	 	description.addRequiredProperty("observation");
	 	description.addRequiredProperty("patient");
	 	description.addRequiredProperty("encounter");
	 	description.addRequiredProperty("date");	 	
	 	return description;
	 }
	
	@Override
	 public DelegatingResourceDescription getUpdatableProperties() {
		System.out.println("New Request in SensorReading getUpdatableProperties");
	 	return getCreatableProperties();
	 }
	
	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		System.out.println("New Request in SensorConceptMappingResourceResource doGetAll");
		return new NeedsPaging<SensorReading>(Context.getService(SensorReadingService.class).getAllSensorReadings(), context);
	}
}
