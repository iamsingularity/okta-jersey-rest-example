package com.okta.jerseyrest.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JerseyConfiguration extends ResourceConfig {

    @PostConstruct
    public void init() {
        packages("com.okta.jerseyrest.resource");
    }
}