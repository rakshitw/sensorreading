package org.openmrs.module.sensorreading.rest.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.sensorreading.SensorMapping;
import org.openmrs.module.sensorreading.api.SensorMappingService;
//import org.openmrs.module.sensorreading.rest.controller.SensorReadingRestController;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
//import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;

@Resource(name = RestConstants.VERSION_1 + "/sensor" + "/sm", supportedClass = SensorMapping.class, supportedOpenmrsVersions = "1.*.*")
public class SensorMappingResource extends DataDelegatingCrudResource<SensorMapping> {

	@Override
	public SensorMapping newDelegate() {
		System.out.println("New Request in SensorMapping newDelegate");
		return new SensorMapping();
	}

	@Override
	public SensorMapping save(SensorMapping delegate) {
		System.out.println("New Request in SensorMapping save");
		return Context.getService(SensorMappingService.class).saveSensorMapping(delegate);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		System.out.println("New Request in SensorMapping getRepresentationDescription");
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof FullRepresentation) {
			description.addProperty("sensor_id");
			description.addProperty("sensor_name");
			description.addSelfLink();			
			return description;
		} else {
			description.addProperty("sensor_id");
			description.addProperty("sensor_name");
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		}
	}

	@Override
	public SensorMapping getByUniqueId(String uniqueId) {
		System.out.println("New Request in SensorMapping getByUniqueId");
		Integer id = Integer.parseInt(uniqueId);
		System.out.println("sensor_id = "+uniqueId);
		SensorMapping obj = Context.getService(SensorMappingService.class).retrieveSensorMapping(id);
		System.out.println("Fetched Sensor = "+obj.getSensor_name());
		return obj; 
	}

	@Override
	protected void delete(SensorMapping delegate, String reason,
		RequestContext context) throws ResponseException {
		System.out.println("New Request in SensorMapping delete , reason : "+reason);
		System.out.println("Deleting "+delegate.getSensor_name());
		Context.getService(SensorMappingService.class).deleteSenorMapping(delegate);		
	}

	@Override
	public void purge(SensorMapping delegate, RequestContext context)
			throws ResponseException {
		// TODO Auto-generated method stub
		System.out.println("New Request in SensorMapping purge which is not defined yet");
	}	
	@Override
	 public DelegatingResourceDescription getCreatableProperties() {
		System.out.println("New Request in SensorMapping getCreatableProperties");
	 	DelegatingResourceDescription description = new DelegatingResourceDescription();
	 	description.addRequiredProperty("sensor_id");
	 	description.addRequiredProperty("sensor_name");
	 	return description;
	 }
	
	@Override
	 public DelegatingResourceDescription getUpdatableProperties() {
		System.out.println("New Request in SensorMapping getUpdatableProperties");
	 	return getCreatableProperties();
	 }
}
