package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.CsvImporterStatus;
import com.donation.csv.exporter.csvhandler.model.Mail;
import com.donation.csv.exporter.csvhandler.notification.HtmlEmailNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Slf4j
@Service
public class PostProcessingService {

    private final String backupDirPath;
    private final HtmlEmailNotification emailNotification;

    @Autowired
    public PostProcessingService(@Value("${file.backup.dir.path}") String backupDirPath, HtmlEmailNotification emailNotification) {
        this.backupDirPath = backupDirPath;
        this.emailNotification = emailNotification;
    }

    public void postProcessing(File file, String traceId, CsvImporterStatus csvImporterStatus) {
        Mail mail = Mail.builder().subject(String.format("CSV Import status :: %s | Tracing Id :: %s", csvImporterStatus.getStatus(), traceId)).recipient("sumitbppimt@gmail.com").build();
        Context context = new Context(Locale.forLanguageTag("en"));
        context.setVariable("csvImporterStatus", csvImporterStatus);
        context.setVariable("transactionId", traceId);
        emailNotification.send(mail, "email/email-template", context);
        Path source = Paths.get(file.getPath());
        Path dest = Paths.get(backupDirPath + File.separator + traceId + file.getName());
        log.debug("Created backup file to {}", dest);
        try {
            Files.move(source, dest);
        } catch (IOException e) {
            log.error("Error occurred while transferring file", e);
        }
    }
}
