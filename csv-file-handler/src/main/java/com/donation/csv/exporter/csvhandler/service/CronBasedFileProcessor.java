package com.donation.csv.exporter.csvhandler.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Slf4j
@Service
@ConditionalOnProperty(name = "cron-based-file-processor.enabled", havingValue = "true", matchIfMissing = true)
public class CronBasedFileProcessor {

    private final String sourceDirectory;
    private final String destinationDirectory;

    @Autowired
    public CronBasedFileProcessor(@Value("${transactionFile.dir.path}") String sourceDirectory,
                                  @Value("${file.dir.path}") String destinationDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.destinationDirectory = destinationDirectory;
    }

    @Scheduled(cron = "${cronExpression}")
    public void processFile() {
        log.info("Starting processing");
        File directoryPath = new File(sourceDirectory);
        File[] files = directoryPath.listFiles();
        if (files != null) {
            Arrays.stream(files)
                    .filter(File::isFile)
                    .filter(file -> file.getAbsolutePath().endsWith(".csv"))
                    .forEach(file -> {
                        log.info("Moving file {} to dir : {}", file.getName(), destinationDirectory);
                        String sourceFile = file.getPath();
                        String destFile = destinationDirectory + File.separator + file.getName();
                        Path source = Paths.get(file.getPath());
                        Path dest = Paths.get(destinationDirectory + File.separator + file.getName());
                        log.info("Successfully transferred file from {} to {}", sourceFile, destFile);
                        try {
                            Files.move(source, dest);
                        } catch (IOException e) {
                            log.error("Error occurred while transferring file", e);
                        }
                    });
        }
        log.info("Processing finished");
    }
}
