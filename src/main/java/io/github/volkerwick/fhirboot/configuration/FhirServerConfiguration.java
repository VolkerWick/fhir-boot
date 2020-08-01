package io.github.volkerwick.fhirboot.configuration;

import java.util.stream.Collectors;

import javax.servlet.ServletException;

import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.rest.server.RestfulServer;
import lombok.extern.slf4j.Slf4j;


@Configuration
@SuppressWarnings("serial")
@Slf4j
public class FhirServerConfiguration extends RestfulServer {


    @Override
    protected void initialize() throws ServletException {
        super.initialize();

        log.info("*****\n***** Resource Providers: {}",
                getResourceProviders().stream().map(Object::toString).collect(Collectors.joining(", ")));
        log.info("*****\n***** Plain Providers: {}",
                getPlainProviders().stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

}