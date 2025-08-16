package com.rinha.rinha_augusto.models.enums;

public enum ErrorType {
    REQUEUED("REQUEUED"),
    MAX_RETRIES_FAILED("MAX_RETRIES_FAILED"),
    OTHER("OTHER");

    private final String value;

    ErrorType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
