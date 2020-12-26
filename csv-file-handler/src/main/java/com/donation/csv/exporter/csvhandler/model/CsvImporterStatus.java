package com.donation.csv.exporter.csvhandler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CsvImporterStatus implements Serializable {

    private Status status;
    private int totalCount;
    private int successCount;
    private int failureCount;
    private int ignoredCount;
    private List<FailureDetails> failureDetails;

}
