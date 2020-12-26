package com.donation.csv.exporter.csvhandler.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ImageMapper {

    private String mapperName;
    private String imageFileName;
    private String contentType;
}
