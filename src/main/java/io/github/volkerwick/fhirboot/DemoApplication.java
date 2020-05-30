package io.github.volkerwick.fhirboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.spring.boot.autoconfigure.FhirAutoConfiguration;
import ca.uhn.fhir.spring.boot.autoconfigure.FhirProperties;

@SpringBootApplication
public class DemoApplication extends FhirAutoConfiguration {

	public DemoApplication(FhirProperties properties) {
		super(properties);
	}

	@Override
	@Bean
	public FhirContext fhirContext() {
		return FhirContext.forDstu3();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}	

}
