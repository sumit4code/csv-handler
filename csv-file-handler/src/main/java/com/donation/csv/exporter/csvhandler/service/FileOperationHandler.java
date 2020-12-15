package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.fasterxml.jackson.databind.MappingIterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Optional;

@Slf4j
@Service
public class FileOperationHandler {

    private final String dirPath;
    private final long pollInterval;
    private final CsvProcessor csvProcessor;
    private final TransactionProcessingService transactionProcessingService;

    @Autowired
    public FileOperationHandler(@Value("${file.dir.path}") String dirPath, @Value("${file.pollInterval}") long pollInterval,
                                CsvProcessor csvProcessor,
                                TransactionProcessingService transactionProcessingService) {
        this.dirPath = dirPath;
        this.pollInterval = pollInterval;
        this.csvProcessor = csvProcessor;
        this.transactionProcessingService = transactionProcessingService;
    }

    @PostConstruct
    public void init() throws Exception {
        log.info("Initiating project with \nProject dir: {}\nPoll Interval: {}", dirPath, pollInterval);
        this.startFileListener();
    }

    private void startFileListener() throws Exception {
        log.debug("Starting file listener");
        final FileAlterationObserver observer = new FileAlterationObserver(dirPath);
        final FileAlterationMonitor monitor = new FileAlterationMonitor(pollInterval);
        final FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                Optional<MappingIterator<Transaction>> transactionMappingIterator = csvProcessor.processFile(file);
                transactionMappingIterator.ifPresent(transactionProcessingService::process);
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
