package com.cts.recruitment.assignment.javaexercise.services.impl;
/*
 * This is implementation class for service interface
 * @Author Dinesh Singh
 */

import com.cts.recruitment.assignment.model.Record;
import com.cts.recruitment.assignment.model.RecordResponse;
import com.cts.recruitment.assignment.model.RecordResponseErrorRecords;
import com.cts.recruitment.assignment.javaexercise.services.interfaces.StatementValidationService;
import com.cts.recruitment.assignment.javaexercise.utility.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatementValidationServiceImpl implements StatementValidationService {
    private static final Logger logger = LoggerFactory.getLogger(StatementValidationServiceImpl.class);

    /**
     * This is method to validate statement records and send response to resource
     * There is four scenario in records.
     * 1. File have duplicate reference record and same record sum are okay
     * 2. File have duplicate reference record and same record have invalid sum
     * 3. File have record with invalid sum but record reference is not a duplicate record
     * 4. File record is okay
     *
     * @param lstRecord of {List of Record}
     * @return Response of {Response}
     */
    @Override
    public Response validate(List<Record> lstRecord) {

        List<Record> duplicateRecords = duplicateReferenceRecords(lstRecord);
        logger.info("duplicate records are {}", duplicateRecords);
        List<Record> invalidSumRecords = invalidBalanceRecards(lstRecord);
        logger.info("invalidSumRecords records are {}", invalidSumRecords);
        // Checking if both lists are emplty then return happy response
        if (!duplicateRecords.isEmpty() && invalidSumRecords.isEmpty()) {
            logger.info("processing response of type {}", RecordResponse.ResultsEnum.DUPLICATE_REFERENCE);
            return Response.status(Response.Status.OK).entity(Utility.createResponse(duplicateRecords,
                    RecordResponse.ResultsEnum.DUPLICATE_REFERENCE)).build();

        } else if (duplicateRecords.isEmpty() && !invalidSumRecords.isEmpty()) {
            logger.info("processing response of type {}", RecordResponse.ResultsEnum.INCORRECT_END_BALANCE);
            return Response.status(Response.Status.OK).entity(Utility.createResponse(invalidSumRecords,
                    RecordResponse.ResultsEnum.INCORRECT_END_BALANCE)).build();
        } else if (!duplicateRecords.isEmpty() && !invalidSumRecords.isEmpty()) {
            logger.info("processing response of type {}", RecordResponse.ResultsEnum.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
            duplicateRecords.addAll(invalidSumRecords);
            return Response.status(Response.Status.OK).entity(Utility.createResponse(duplicateRecords,
                    RecordResponse.ResultsEnum.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE)).build();
        } else {
            logger.info("processing response of type {}", RecordResponse.ResultsEnum.SUCCESSFUL);
            return Response.status(Response.Status.OK).entity(Utility.createResponse(null,
                    RecordResponse.ResultsEnum.SUCCESSFUL)).build();
        }
    }

    /**
     * This is private method to find duplicate reference and storing it in
     * List of Records
     *
     * @param lstRecords
     * @return {List<RecordResponseErrorRecords>}
     */
    private List<Record> duplicateReferenceRecords(List<Record> lstRecords) {
        List<RecordResponseErrorRecords> lstError = new ArrayList<>();
        List<Record> duplicated = lstRecords
                .stream()
                .filter(n -> lstRecords
                        .stream()
                        .filter(x -> x.getTxnReference().intValue() == n.getTxnReference().intValue())
                        .count() > 1)
                .collect(Collectors.toList());
        return duplicated;
    }

    /**
     * This is private method to find duplicate reference and sending final
     * response to client
     * It is not good performance O(n*n) logic and just to show java 8
     *
     * @param lstRecords
     * @return {List<RecordResponseErrorRecords>}
     */
    private List<Record> invalidBalanceRecards(List<Record> lstRecords) {
        //List<RecordResponseErrorRecords> lstError = new ArrayList<>();
        List<Record> lstIncorrectBalanceRecord = lstRecords
                .stream()
                .filter(n -> {
                    return (n.getEndBalance().intValue() != (n.getStartBalance().intValue() - n.getMutation().intValue()))
                            && (n.getEndBalance().intValue() != (n.getStartBalance().intValue() + n.getMutation().intValue()));
                })
                .collect(Collectors.toList());
        return lstIncorrectBalanceRecord;
    }
}
