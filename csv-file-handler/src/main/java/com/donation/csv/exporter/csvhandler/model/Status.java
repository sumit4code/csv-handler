package com.donation.csv.exporter.csvhandler.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {

    SUCCESS("Success"), FAILURE("Failure"), PARTIAL_SUCCESS("PartialSuccess");

    private String status;

    @Override
    public String toString() {
        return status;
    }
}
