package com.cts.recruitment.assignment.javaexercise.provider;

import com.cts.recruitment.assignment.model.RecordResponse;
import com.cts.recruitment.assignment.javaexercise.utility.Utility;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Utility.createResponse(null, RecordResponse.ResultsEnum.BAD_REQUEST))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
