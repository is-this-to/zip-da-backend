package com.zipdabackend.global.error.custom.report;

public class ReportAlreadyExistsException extends RuntimeException {
    public ReportAlreadyExistsException(String message) {
        super(message);
    }
}
