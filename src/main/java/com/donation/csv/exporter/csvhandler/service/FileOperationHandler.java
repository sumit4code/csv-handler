package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.fasterxml.jackson.databind.MappingIterator;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileOperationHandler {

    private final String dirPath;
    private final long pollInterval;
    private final CsvProcessor csvProcessor;

    @Autowired
    public FileOperationHandler(@Value("${file.dir.path}") String dirPath, @Value("${file.pollInterval}") long pollInterval, CsvProcessor csvProcessor) {
        this.dirPath = dirPath;
        this.pollInterval = pollInterval;
        this.csvProcessor = csvProcessor;
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
                try {
                    log.info("data {}", transactionMappingIterator.get().readAll());
                } catch (IOException e) {
                    log.error("Error occurred during loading of csv :" + file.getPath(), e);
                }
                // code for processing creation event
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
