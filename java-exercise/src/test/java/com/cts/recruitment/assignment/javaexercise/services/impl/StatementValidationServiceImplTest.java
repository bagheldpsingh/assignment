package com.cts.recruitment.assignment.javaexercise.services.impl;

import com.cts.recruitment.assignment.model.Record;
import com.cts.recruitment.assignment.model.RecordResponse;
import com.cts.recruitment.assignment.javaexercise.services.interfaces.StatementValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

class StatementValidationServiceImplTest {

    List<Record> lstRecord;

    StatementValidationService statementValidationService ;

    Response response;
    @BeforeEach
    void setUp() {
        lstRecord = new ArrayList<>();
        statementValidationService = new StatementValidationServiceImpl();
    }

    /**
     * This is test to verify that our service logic is working properly with
     * empty input
     */
    @Test
    void validateWithEmptyInput() {
        response = statementValidationService.validate(lstRecord);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(RecordResponse.ResultsEnum.SUCCESSFUL, ((RecordResponse)response.getEntity()).getResults());
    }
    /**
     * This is test to verify that our service logic is working properly with
     * valid input
     */
    @Test
    void validateWithMandatoryFields() {
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
        .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        response = statementValidationService.validate(lstRecord);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals( RecordResponse.ResultsEnum.SUCCESSFUL, ((RecordResponse)response.getEntity()).getResults());
    }
    /**
     * This is test to verify that our service logic is working properly with
     * duplicate and valid sum input
     * We can improve it in future while reading input data form JSON
     */
    @Test
    void validateWithDuplicateAndValidSum() {
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1001).accNumber("GB33BUKB20201555555555"));
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        response = statementValidationService.validate(lstRecord);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals(RecordResponse.ResultsEnum.DUPLICATE_REFERENCE, ((RecordResponse)response.getEntity()).getResults());
    }
    /**
     * This is test to verify that our service logic is working properly with
     * invalid sum and no duplicate
     * We can improve it in future while reading input data form JSON
     */
    @Test
    void validateWithInvalidSumWithoutDuplicate() {
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(205.00)
                .txnReference(1001).accNumber("GB33BUKB20201555555555"));
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1002).accNumber("GB33BUKB20201555555555"));
        response = statementValidationService.validate(lstRecord);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals(RecordResponse.ResultsEnum.INCORRECT_END_BALANCE, ((RecordResponse)response.getEntity()).getResults());
    }
    /**
     * This is test to verify that our service logic is working properly with
     * invalid sum and duplicate
     * We can improve it in future while reading input data form JSON
     */
    @Test
    void validateWithInvalidSumWithDuplicate() {
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(205.00)
                .txnReference(1001).accNumber("GB33BUKB20201555555555"));
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        response = statementValidationService.validate(lstRecord);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals(RecordResponse.ResultsEnum.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, ((RecordResponse) response.getEntity()).getResults());
        Assertions.assertEquals(3, ((RecordResponse) response.getEntity()).getErrorRecords().size());
    }
}
