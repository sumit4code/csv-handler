package com.donation.csv.exporter.csvhandler.service;

import com.donation.csv.exporter.csvhandler.model.Transaction;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CsvProcessor {

    private static final String EXPECTED_FILE_TYPE = "csv";
    private final CsvMapper csvMapper = new CsvMapper();
    private final CsvSchema schema = CsvSchema.emptySchema().withHeader();


    public Optional<MappingIterator<Transaction>> processFile(File file) {
        String filePath = file.getPath();
        if (this.isValidFile(filePath)) {
            log.debug("Received a valid file with {}", filePath);
            try {
                return Optional.of(csvMapper.readerWithTypedSchemaFor(Transaction.class).with(schema).readValues(file));
            } catch (IOException e) {
                log.error("Error while parsing csv", e);
            }
        } else {
            log.error("Received a invalid file {}", filePath);
        }
        return Optional.empty();
    }

    private boolean isValidFile(String file) {
        String extension = FilenameUtils.getExtension(file);
        return EXPECTED_FILE_TYPE.equals(extension);
    }
}
