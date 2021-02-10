package com.cts.recruitment.assignment.javaexercise.utility;
/*
 * This is utility class where all supported methods will reside
 * @Author Dinesh singh
 */

import com.cts.recruitment.assignment.dinesh.model.Record;
import com.cts.recruitment.assignment.dinesh.model.RecordResponse;
import com.cts.recruitment.assignment.dinesh.model.RecordResponseErrorRecords;
import com.cts.recruitment.assignment.javaexercise.services.impl.StatementValidationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Utility {
    private static Logger logger = LoggerFactory.getLogger(Utility.class);

    /**
     * Method to create error details in response
     *
     * @param lstRecord is {List} of record
     * @return {List<RecordResponseErrorRecords>}
     */
    public static List<RecordResponseErrorRecords> createErrorRes(List<Record> lstRecord) {
        logger.info("Creating `Error response for records {} ", lstRecord);
        List<RecordResponseErrorRecords> lstError = new ArrayList<>();
        Optional.ofNullable(lstRecord)
                .map(Collection::stream)
                .orElseGet(Stream::empty).forEach(duplicate -> {
            RecordResponseErrorRecords recordResponseErrorRecords = new RecordResponseErrorRecords();
            recordResponseErrorRecords.reference(duplicate.getTxnReference().toString());
            recordResponseErrorRecords.accountNumber(duplicate.getAccNumber());
            lstError.add(recordResponseErrorRecords);
        });
        return lstError;
    }

    /**
     * Method to create final response
     *
     * @param lstRecord is {List} of record
     * @param recordEnum is {RecordEnum}
     * @return {RecordResponse}
     */
    public static RecordResponse createResponse(List<Record> lstRecord, RecordResponse.ResultsEnum recordEnum) {
        logger.info("Creating `Error response for records {} of type {}", lstRecord, recordEnum) ;
        RecordResponse recordResponse = new RecordResponse();
        recordResponse.errorRecords(Utility.createErrorRes(lstRecord));
        recordResponse.results(recordEnum);
        return recordResponse;
    }
}
