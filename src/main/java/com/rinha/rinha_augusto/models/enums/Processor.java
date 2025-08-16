package com.rinha.rinha_augusto.models.enums;

public enum Processor {
    DEFAULT("DEFAULT"),
    FALLBACK("FALLBACK");

    private final String value;

    Processor(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
