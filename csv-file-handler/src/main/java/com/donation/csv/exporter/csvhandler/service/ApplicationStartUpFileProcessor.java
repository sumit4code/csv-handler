package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.CsvImporterStatus;
import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@ConditionalOnProperty(name = "cron-based-file-processor.enabled", havingValue = "false")
public class ApplicationStartUpFileProcessor implements CommandLineRunner {

    private final CsvProcessor csvProcessor;
    private final TransactionProcessingService transactionProcessingService;
    private final PostProcessingService postProcessingService;
    private final String sourceDirectory;

    public ApplicationStartUpFileProcessor(CsvProcessor csvProcessor,
                                           TransactionProcessingService transactionProcessingService,
                                           PostProcessingService postProcessingService, @Value("${transactionFile.dir.path}") String sourceDirectory) {
        this.csvProcessor = csvProcessor;
        this.transactionProcessingService = transactionProcessingService;
        this.postProcessingService = postProcessingService;
        this.sourceDirectory = sourceDirectory;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("Starting processing on application start up");
        File directoryPath = new File(sourceDirectory);
        File[] files = directoryPath.listFiles();
        if (files != null) {
            Arrays.stream(files)
                    .filter(File::isFile)
                    .filter(file -> file.getAbsolutePath().endsWith(".csv"))
                    .forEach(file -> {
                        String traceId = UUID.randomUUID().toString();
                        MDC.put("transactionId", traceId);
                        Optional<MappingIterator<Transaction>> transactionMappingIterator = csvProcessor.processFile(file);
                        transactionMappingIterator.ifPresent(mappingIterator -> {
                            CsvImporterStatus csvImporterStatus = transactionProcessingService.process(mappingIterator);
                            postProcessingService.postProcessing(file, traceId, csvImporterStatus);
                        });
                        MDC.remove("transactionId");
                    });
        }
        log.info("Processing finished");
        System.exit(0);
    }
}
