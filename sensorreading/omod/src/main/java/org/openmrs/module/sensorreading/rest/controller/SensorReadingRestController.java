package org.openmrs.module.sensorreading.rest.controller;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + SensorReadingRestController.SensorReading_REST_NAMESPACE)
public class SensorReadingRestController extends MainResourceController {
	
	public static final String SensorReading_REST_NAMESPACE = "/sensor";
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController#getNamespace()
	 */
	@Override
	public String getNamespace() {
		String link = "/rest/" + RestConstants.VERSION_1 + SensorReadingRestController.SensorReading_REST_NAMESPACE;
		System.out.println("SensorReadingRestController Using This Namespace for Sensor Mapping Controller : "+link);
		return RestConstants.VERSION_1 + SensorReadingRestController.SensorReading_REST_NAMESPACE;
	}
	
}

