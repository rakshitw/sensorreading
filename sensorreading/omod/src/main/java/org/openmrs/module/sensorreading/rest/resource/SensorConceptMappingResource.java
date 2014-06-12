package org.openmrs.module.sensorreading.rest.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.sensorreading.SensorConceptMapping;
import org.openmrs.module.sensorreading.api.SensorConceptMappingService;
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

@Resource(name = RestConstants.VERSION_1 + "/sensor" + "/scm", supportedClass = SensorConceptMapping.class, supportedOpenmrsVersions = "1.*.*")
public class SensorConceptMappingResource extends DataDelegatingCrudResource<SensorConceptMapping> {

	@Override
	public SensorConceptMapping newDelegate() {
		System.out.println("New Request in newDelegate");
		return new SensorConceptMapping();
	}

	@Override
	public SensorConceptMapping save(SensorConceptMapping delegate) {
		System.out.println("New Request in SensorConceptMappingResource save");
		return Context.getService(SensorConceptMappingService.class).saveSensorConceptMapping(delegate);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		System.out.println("New Request in SensorConceptMappingResource getRepresentationDescription");
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof FullRepresentation) {
			description.addProperty("sensor", Representation.FULL);
			description.addProperty("concepts",Representation.FULL);
			description.addSelfLink();			
			return description;
		} else {
			description.addProperty("sensor", Representation.FULL);
			description.addProperty("concepts",Representation.FULL);
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		}
	}

	@Override
	public SensorConceptMapping getByUniqueId(String uniqueId) {
		System.out.println("New Request in SensorConceptMappingResource getByUniqueId");
		Integer id = Integer.parseInt(uniqueId);
		System.out.println("sensor_id = "+uniqueId);
		SensorConceptMapping obj = Context.getService(SensorConceptMappingService.class).retrieveSensorMapping(id);
		//System.out.println("Fetched Object = "+obj.getBp_reading());
		return obj; 
	}

	@Override
	protected void delete(SensorConceptMapping delegate, String reason,
		RequestContext context) throws ResponseException {
		System.out.println("New Request in SensorConceptMappingResource delete , reason : "+reason);
		Context.getService(SensorConceptMappingService.class).deleteSenorMapping(delegate);		
	}

	@Override
	public void purge(SensorConceptMapping delegate, RequestContext context)
			throws ResponseException {
		// TODO Auto-generated method stub
		System.out.println("New Request in SensorConceptMappingResource purge which is not defined yet");
	}	
	
	@Override
	 public DelegatingResourceDescription getCreatableProperties() {
		System.out.println("New Request in SensorConceptMappingResource getCreatableProperties");
	 	DelegatingResourceDescription description = new DelegatingResourceDescription();
	 	description.addRequiredProperty("sensor");
	 	description.addRequiredProperty("concepts");
	 	return description;
	 }
	
	@Override
	 public DelegatingResourceDescription getUpdatableProperties() {
		System.out.println("New Request in SensorConceptMappingResource getUpdatableProperties");
	 	return getCreatableProperties();
	 }

	@Override
	protected PageableResult doGetAll(RequestContext context) throws ResponseException {
		System.out.println("New Request in SensorConceptMappingResourceResource doGetAll");
		return new NeedsPaging<SensorConceptMapping>(Context.getService(SensorConceptMappingService.class).getAllSensorConceptMappings(), context);
	}

}
