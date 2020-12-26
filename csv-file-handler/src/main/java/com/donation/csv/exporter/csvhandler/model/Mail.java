package com.donation.csv.exporter.csvhandler.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
public class Mail {

    String recipient;
    String subject;
    List<ImageMapper> imageMappers;
}
