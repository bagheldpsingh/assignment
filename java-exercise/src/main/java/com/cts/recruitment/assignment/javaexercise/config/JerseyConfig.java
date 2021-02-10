package com.cts.recruitment.assignment.javaexercise.config;

import com.cts.recruitment.assignment.javaexercise.provider.ConstraintViolationExceptionMapper;
import com.cts.recruitment.assignment.javaexercise.provider.InternalTechnicalErrorMapper;
import com.cts.recruitment.assignment.javaexercise.resources.CustomerStatementResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        register(CustomerStatementResource.class);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        register(InternalTechnicalErrorMapper.class);
        register(ConstraintViolationExceptionMapper.class);
    }
}
