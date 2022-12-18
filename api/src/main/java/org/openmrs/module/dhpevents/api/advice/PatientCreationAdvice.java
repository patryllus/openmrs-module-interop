/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.dhpevents.api.advice;

import java.lang.reflect.Method;

import org.openmrs.Encounter;
import org.openmrs.module.fhir2.api.FhirPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientCreationAdvice implements AfterReturningAdvice {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PatientCreationAdvice.class);
	
	@Autowired
	private FhirPatientService fhirPatientService;
	
	@Override
	public void afterReturning(Object o, Method method, Object[] args, Object o1) throws Throwable {
		if (method.getName().equals("saveEncounter")) {
			Encounter encounter = (Encounter) args[0];
			if (this.fhirPatientService == null) {
				LOGGER.error("***************** PATIENT SERVICE IS NULL");
			} else {
				LOGGER.error("================= IT IS NOT NULL");
				org.hl7.fhir.r4.model.Patient fhirPatient = this.fhirPatientService.get(encounter.getPatient().getUuid());
				LOGGER.error("the fhir patient " + fhirPatient.getBirthDate());
			}
		}
	}
}
