package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.CsvImporterStatus;
import com.donation.csv.exporter.csvhandler.model.FailureDetails;
import com.donation.csv.exporter.csvhandler.model.Status;
import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.donation.csv.exporter.csvhandler.repository.TransactionRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionProcessingService {

    private final int defaultMinimumProcessingCount;
    private final float threasholdPercentageForFailure;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionProcessingService(@Value("${batch-processing.default-minimum-processing-count:10}") int defaultMinimumProcessingCount,
                                        @Value("${batch-processing.threashold-percentage-for-failure:10}") float threasholdPercentageForFailure,
                                        TransactionRepository transactionRepository) {
        this.defaultMinimumProcessingCount = defaultMinimumProcessingCount;
        this.threasholdPercentageForFailure = threasholdPercentageForFailure;
        this.transactionRepository = transactionRepository;
    }

    public CsvImporterStatus process(MappingIterator<Transaction> mappingIterator) {
        int totalSize = 0;
        int successCount = 0;
        int failureCount = 0;
        int ignoreCount = 0;
        final List<FailureDetails> failureDetails = new ArrayList<>();
        while (mappingIterator.hasNext()) {
            totalSize++;
            Transaction transaction = null;
            try {
                transaction = mappingIterator.next();
                if (this.shouldProcess(totalSize, failureCount)) {
                    this.storeEntry(transaction);
                    if (log.isDebugEnabled()) {
                        log.debug("Saved Successfully {}", transaction);
                    } else {
                        log.info("Save successfully transaction id: {}", transaction.getTransactionId());
                    }
                    successCount++;
                } else {
                    ignoreCount++;
                    log.warn("ignored as failure percentage has reached");
                }
            } catch (DataIntegrityViolationException | RuntimeJsonMappingException e) {
                if (ExceptionUtils.indexOfType(e, SQLIntegrityConstraintViolationException.class) != -1) {
                    log.warn("Already exist for transaction {}", transaction.getTransactionId());
                    ignoreCount++;
                } else {
                    Throwable throwable = NestedExceptionUtils.getRootCause(e);
                    String cause = throwable != null ? throwable.getMessage() : e.getMessage();
                    log.error("Error while persisting data for entry at {} due to {}", totalSize + 1, cause);
                    failureCount++;
                    failureDetails.add(new FailureDetails(totalSize + 1, cause));
                }
            }
        }
        CsvImporterStatus status = getCsvImporterStatus(totalSize, successCount, failureCount, ignoreCount, failureDetails);
        log.info("Processing completed with status {}", status);
        return status;
    }

    private CsvImporterStatus getCsvImporterStatus(int totalSize, int successCount, int failureCount, int ignoreCount, List<FailureDetails> failureDetails) {
        CsvImporterStatus.CsvImporterStatusBuilder csvImporterStatusBuilder = CsvImporterStatus.builder().totalCount(totalSize);
        if (successCount == 0 && totalSize != 0) {
            csvImporterStatusBuilder.status(Status.FAILURE);
        } else if (successCount == totalSize || successCount + ignoreCount == totalSize) {
            csvImporterStatusBuilder.status(Status.SUCCESS);
        } else {
            csvImporterStatusBuilder.status(Status.PARTIAL_SUCCESS);
        }
        if (!CollectionUtils.isEmpty(failureDetails)) {
            csvImporterStatusBuilder.failureDetails(failureDetails);
        }
        return csvImporterStatusBuilder.successCount(successCount).ignoredCount(ignoreCount).failureCount(failureCount).build();
    }

    private boolean shouldProcess(int totalCount, int failureCount) {
        if (totalCount < defaultMinimumProcessingCount) {
            return true;
        }
        float failurePercentage = ((float) failureCount * 100) / totalCount;
        return failurePercentage < threasholdPercentageForFailure;
    }

    @Transactional
    public void storeEntry(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
