package com.cts.recruitment.assignment.javaexercise.resources;

import com.cts.recruitment.assignment.dinesh.model.Record;
import com.cts.recruitment.assignment.javaexercise.services.interfaces.StatementValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/records")
public class CustomerStatementResource {
    /* This is logger */

    Logger logger = LoggerFactory.getLogger(CustomerStatementResource.class);

    private static final String HEALTH_CHECK_URL = "/health/check";
    private static final String VALIDATE_URL = "/validate";

    /*
     * `injecting service in resource classs to call validate methods
     */
    private StatementValidationService statementValidationService ;

    /*
     * public constructor with @Autowire of dependency
     */
    @Autowired
    public CustomerStatementResource (StatementValidationService statementValidationService){
        this.statementValidationService = statementValidationService;
    }

    @GET
    @Path(HEALTH_CHECK_URL)
    @Produces(MediaType.TEXT_HTML)
    public Response healthCheck() {
        logger.info("Inside health check");
        return Response.status(200).entity("KeepAlive").build();
    }

    /**
     * This is method to handle post method and validate customer record
     * @param lstRecord of {List<Record>}
     * @return Response to client of API
     */
    @POST
    @Path(VALIDATE_URL)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateStatements(@Valid List<Record> lstRecord) {
        logger.info("request is recived for validation  {}", lstRecord);
        return statementValidationService.validate(lstRecord);

    }
}
