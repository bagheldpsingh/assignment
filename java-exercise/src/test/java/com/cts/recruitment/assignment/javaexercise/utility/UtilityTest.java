package com.cts.recruitment.assignment.javaexercise.utility;

import com.cts.recruitment.assignment.dinesh.model.Record;
import com.cts.recruitment.assignment.dinesh.model.RecordResponse;
import com.cts.recruitment.assignment.dinesh.model.RecordResponseErrorRecords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

class UtilityTest {

    List<Record> lstRecord;

    Response response;

    @BeforeEach
    void setUp() {
        lstRecord = new ArrayList<>();
    }

    /**
     * This is to test logic of create error response for given null list of Records input
     */
    @Test
    void createErrorResForNullInput() {
        List<RecordResponseErrorRecords> recordResponseErrorRecords = Utility.createErrorRes(null);
        Assertions.assertNotNull(recordResponseErrorRecords);
        Assertions.assertEquals(0, recordResponseErrorRecords.size());
    }

    @Test
    void createErrorResForEmptyList() {
        List<RecordResponseErrorRecords> recordResponseErrorRecords = Utility.createErrorRes(lstRecord);
        Assertions.assertNotNull(recordResponseErrorRecords);
        Assertions.assertEquals(0, recordResponseErrorRecords.size());
    }

    /**
     * This is to test logic of create error response for given list of Records input
     */
    @Test
    void createErrorResForValidInput() {
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        List<RecordResponseErrorRecords> recordResponseErrorRecords = Utility.createErrorRes(lstRecord);
        Assertions.assertNotNull(recordResponseErrorRecords);
        Assertions.assertEquals(1, recordResponseErrorRecords.size());
    }

    /**
     * This is to test logic of create record response for
     * null list and null RecordEnum
     */
    @Test
    void createResponseWithNull() {
        RecordResponse recordResponse = Utility.createResponse(null, null);
        Assertions.assertNotNull(recordResponse);
        Assertions.assertNull(recordResponse.getResults());
        Assertions.assertEquals(0, recordResponse.getErrorRecords().size());
    }

    /**
     * This is to test logic of create record response for
     * valid list and valid RecordEnum
     */
    @Test
    void createResponseWithValidRecAndEnum() {
        lstRecord.add(new Record().endBalance(2000.00).startBalance(1800.00).mutation(200.00)
                .txnReference(1000).accNumber("GB33BUKB20201555555555"));
        RecordResponse recordResponse = Utility.createResponse(lstRecord, RecordResponse.ResultsEnum.SUCCESSFUL);
        Assertions.assertNotNull(recordResponse);
        Assertions.assertEquals(RecordResponse.ResultsEnum.SUCCESSFUL, recordResponse.getResults());
        Assertions.assertEquals(1, recordResponse.getErrorRecords().size());
    }
}