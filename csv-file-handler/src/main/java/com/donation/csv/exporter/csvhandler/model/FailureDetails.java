package com.donation.csv.exporter.csvhandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class FailureDetails {

    private final int rowNumber;
    private final String reason;
}
