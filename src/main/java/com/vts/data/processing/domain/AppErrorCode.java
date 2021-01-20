package com.vts.data.processing.domain;

public enum AppErrorCode {
    VALIDATE(0) ;

    private final int value;

    AppErrorCode(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
