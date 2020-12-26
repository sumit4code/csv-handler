package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.CsvImporterStatus;
import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FileOperationHandler {

    private final String dirPath;
    private final long pollInterval;
    private final CsvProcessor csvProcessor;
    private final TransactionProcessingService transactionProcessingService;
    private final PostProcessingService postProcessingService;

    @Autowired
    public FileOperationHandler(@Value("${file.dir.path}") String dirPath,
                                @Value("${file.pollInterval}") long pollInterval, CsvProcessor csvProcessor,
                                TransactionProcessingService transactionProcessingService, PostProcessingService postProcessingService) {
        this.dirPath = dirPath;
        this.pollInterval = pollInterval;
        this.csvProcessor = csvProcessor;
        this.transactionProcessingService = transactionProcessingService;
        this.postProcessingService = postProcessingService;
    }

    @PostConstruct
    public void init() throws Exception {
        log.info("Initiating project with \nProject dir: {}\nPoll Interval: {}", dirPath, pollInterval);
        this.startFileListener();
    }

    private void startFileListener() throws Exception {
        log.info("Starting file listener");
        final FileAlterationObserver observer = new FileAlterationObserver(dirPath);
        final FileAlterationMonitor monitor = new FileAlterationMonitor(pollInterval);
        final FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                String traceId = UUID.randomUUID().toString();
                MDC.put("transactionId", traceId);
                Optional<MappingIterator<Transaction>> transactionMappingIterator = csvProcessor.processFile(file);
                transactionMappingIterator.ifPresent(mappingIterator -> {
                    CsvImporterStatus csvImporterStatus = transactionProcessingService.process(mappingIterator);
                    postProcessingService.postProcessing(file, traceId, csvImporterStatus);
                });
                MDC.remove("transactionId");
            }

            @Override
            public void onFileDelete(File file) {
                log.info("File deleted");
                // code for processing deletion event
            }

            @Override
            public void onFileChange(File file) {
                // code for processing change event
            }
        };
        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }
}
