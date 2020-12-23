package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.CsvImporterStatus;
import com.donation.csv.exporter.csvhandler.model.Status;
import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.donation.csv.exporter.csvhandler.repository.TransactionRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

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
        while (mappingIterator.hasNext()) {
            totalSize++;
            Transaction transaction = mappingIterator.next();
            try {
                if (this.shouldProcess(totalSize, failureCount)) {
                    this.storeEntry(transaction);
                    log.debug("Saved Successfully {}", transaction);
                    successCount++;
                } else {
                    ignoreCount++;
                    log.warn("ignored as failure percentage has reached");
                }
            } catch (DataIntegrityViolationException e) {
                log.warn("Already exist, so ignoring");
                ignoreCount++;
            } catch (ConstraintViolationException e) {
                log.error("Error while persisting data {}", transaction, e);
                failureCount++;
            }
        }
        CsvImporterStatus status = getCsvImporterStatus(totalSize, successCount, failureCount, ignoreCount);
        log.info("Processing completed with status {}", status);
        return status;
    }

    private CsvImporterStatus getCsvImporterStatus(int totalSize, int successCount, int failureCount, int ignoreCount) {
        CsvImporterStatus.CsvImporterStatusBuilder csvImporterStatusBuilder = CsvImporterStatus.builder();
        if (successCount == 0 && totalSize != 0) {
            csvImporterStatusBuilder.status(Status.FAILURE);
        } else if (successCount == totalSize || successCount + ignoreCount == totalSize) {
            csvImporterStatusBuilder.status(Status.SUCCESS);
        } else {
            csvImporterStatusBuilder.status(Status.PARTIAL_SUCCESS);
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
