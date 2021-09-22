package io.github.volkerwick.fhirboot.extension;

import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.StringType;

import ca.uhn.fhir.model.api.annotation.Child;
import ca.uhn.fhir.model.api.annotation.Description;
import ca.uhn.fhir.model.api.annotation.Extension;
import ca.uhn.fhir.model.api.annotation.ResourceDef;
import lombok.Getter;
import lombok.Setter;

// Extending a Patient resource as described in https://hapifhir.io/hapi-fhir/docs/model/profiles_and_extensions.html#extensions

@ResourceDef(name="Patient", profile="http://example.com/StructureDefinition/extendedpatient")
public class ExtendedPatient extends Patient {

    private static final long serialVersionUID = 1L;

    @Child(name="petName")   
    @Extension(url="http://example.com/dontuse#petname", definedLocally=false, isModifier=false)
    @Description(shortDefinition="The name of the patient's favourite pet")

    @Getter @Setter
    private StringType myPetName;
    
}
