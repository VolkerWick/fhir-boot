package io.github.volkerwick.fhirboot.provider;
 
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.OptionalParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import io.github.volkerwick.fhirboot.utility.Utility;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class PatientProvider implements IResourceProvider {

    @Autowired
    private Utility utility;

    @Override
    public Class<Patient> getResourceType() {
        return Patient.class;
    }

    @Create
    public MethodOutcome createPatient(@ResourceParam Patient patient) {
        insert(patient);
        return new MethodOutcome(patient.getIdElement());
    }

    @Read
    public Patient read(@IdParam final IdType theId) {

        utility.test();

        log.info("##### entering Patient.read()");
        if (patientRepo.containsKey(theId.getIdPart())) {
            log.info("##### exitinging Patient.read()");
            return patientRepo.get(theId.getIdPart());
        } else {
            log.info("##### aborting Patient.read()");
            throw new ResourceNotFoundException(theId);
        }
    }

    private static final ConcurrentHashMap<String, Patient> patientRepo = new ConcurrentHashMap<>();

    static {
        insert("89ed8360-5fef-4729-87ff-baef01c158d2", 
            new Patient().addName(new HumanName().setFamily("Kirk").addGiven("James").addGiven("Tiberius"))
                .setBirthDate(Date.valueOf("2233-03-22")));

        insert("7a8036b8-7b56-4224-93a3-fe364d5cfeed",
        new Patient().addName(new HumanName().setFamily("Kirk").addGiven("Francis").addGiven("Scott"))
        .setBirthDate(Date.valueOf("1980-03-22")));

        insert("344697e6-2c64-4ead-8e2b-d60671568c19",
            new Patient().addName(new HumanName().setFamily("McCoy").addGiven("Leonard"))
                .setBirthDate(Date.valueOf("2227-01-01"/* ?? */)));
    }

    private static void insert(Patient patient) {

        String randomUuid = UUID.randomUUID().toString();
        insert(randomUuid, patient);
    }

    private static void insert(String id, Patient patient) {

        patient.setId(id);
        patientRepo.put(id, patient);
    }

    @Search
    public List<Patient> findPatients(@OptionalParam(name = Patient.SP_FAMILY) StringParam family,
            HttpServletRequest theRequest, HttpServletResponse theResponse) {

        return patientRepo
            .values()
            .stream()
            .filter(p -> family == null || p.getNameFirstRep().getFamily().equals(family.getValueNotNull()))
            .collect(Collectors.toList());
    }

}
