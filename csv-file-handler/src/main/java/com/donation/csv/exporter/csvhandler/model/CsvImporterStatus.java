package com.donation.csv.exporter.csvhandler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsvImporterStatus implements Serializable {

    private Status status;
    private int successCount;
    private int failureCount;
    private int ignoredCount;


}
