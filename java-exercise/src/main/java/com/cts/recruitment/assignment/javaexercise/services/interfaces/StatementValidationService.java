package com.cts.recruitment.assignment.javaexercise.services.interfaces;

import com.cts.recruitment.assignment.dinesh.model.Record;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;

/*
 * This is interface for Statement validation service with only one validate method.
 * @Author Dinesh Singh
 *
 */
@Service
public interface StatementValidationService {

    /**
     * This is method to validate statements and send response to calling
     * methods
     * @Return {Response}
     * @Param {List<Record> lstRecord}
     */
    public Response validate(List<Record> lstRecord);

}
