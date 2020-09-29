package io.github.volkerwick.fhirboot.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Getter
@Setter
public class BannedUserAgents {

    private List<String> bannedUserAgents;
    
}
