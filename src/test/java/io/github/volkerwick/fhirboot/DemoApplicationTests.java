package io.github.volkerwick.fhirboot;

import static org.assertj.core.api.Assertions.*;

import org.hl7.fhir.dstu3.model.CapabilityStatement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
class DemoApplicationTests {

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
	void contextLoads() {
	}

	@Test
	void metadata() {
		CapabilityStatement conf = getClient().capabilities().ofType(CapabilityStatement.class).execute();

		log.info("Conformance Statement: {}", fhirContext.newXmlParser().encodeResourceToString(conf));
		assertThat(conf).isNotNull();

	}

}
