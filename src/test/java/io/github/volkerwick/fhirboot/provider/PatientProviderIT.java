package io.github.volkerwick.fhirboot.provider;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.util.BundleUtil;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class PatientProviderIT {

    @Autowired
    private FhirContext fhirContext;
    
    @LocalServerPort
    private String localServerPort;

    private String getBase() {
        return String.format("http://localhost:%s/fhir/", localServerPort);
    }

    IGenericClient getClient() {
        return fhirContext.newRestfulGenericClient(getBase());
    }

    @Test
    public void create() {

        Patient patient = new Patient().addName(new HumanName().setFamily("Kirk").addGiven("James").addGiven("Tiberius"));
        patient.setId(UUID.randomUUID().toString());

        MethodOutcome outcome = getClient().create().resource(patient).execute();

        log.info("{} patient: {}", outcome.getCreated() ? "Successfully created" : "Failed to create", fhirContext.newXmlParser().encodeResourceToString(patient));

        assertThat(outcome.getCreated()).isTrue();
    }

    @Test
    public void getPatient() {

        Patient patient = getClient().read().resource(Patient.class).withId("89ed8360-5fef-4729-87ff-baef01c158d2").execute();
        assertThat(patient.getNameFirstRep().getFamily()).isEqualTo("Kirk");
    }

    @Test
    public void searchPatient() {

        Bundle bundle = getClient()
            .search()
            .forResource(Patient.class)
            .where(Patient.FAMILY.matches().value("Kirk"))
            .returnBundle(Bundle.class)
            .execute();

        List<IBaseResource> patients = new ArrayList<>();
        patients.addAll(BundleUtil.toListOfResources(fhirContext, bundle));

        assertThat(patients).isNotEmpty();
        assertThat(patients.get(0).getClass()).isEqualTo(Patient.class);

        Patient p = (Patient)patients.get(0);
        assertThat(p.getNameFirstRep().getFamily()).isEqualTo("Kirk");
    }

}