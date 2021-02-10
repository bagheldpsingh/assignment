package com.cts.recruitment.assignment.javaexercise.provider;

import com.cts.recruitment.assignment.dinesh.model.RecordResponse;
import com.cts.recruitment.assignment.javaexercise.utility.Utility;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalTechnicalErrorMapper extends Exception implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(final Exception exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Utility.createResponse(null, RecordResponse.ResultsEnum.INTERNAL_SERVER_ERROR))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
