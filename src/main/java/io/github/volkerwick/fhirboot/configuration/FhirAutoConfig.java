package io.github.volkerwick.fhirboot.configuration;

import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.spring.boot.autoconfigure.FhirAutoConfiguration;
import ca.uhn.fhir.spring.boot.autoconfigure.FhirProperties;

@Configuration
public class FhirAutoConfig extends FhirAutoConfiguration {

    public FhirAutoConfig(FhirProperties properties) {
        super(properties);
    }

}