package com.mpai.app.Models;

public enum EventType {
    CONFERENCE(1),
    BIRTHDAYPARTY(2);

    private final int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
