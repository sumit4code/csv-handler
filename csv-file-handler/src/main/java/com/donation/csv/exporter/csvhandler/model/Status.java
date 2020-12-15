package com.donation.csv.exporter.csvhandler.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum Status {

    SUCCESS("Success"), FAILURE("Failure"), PARTIAL_SUCCESS("PartialSuccess");

    private String status;
}
