package ru.naumov.springcourse.RestApp.util;

public class MeasurementNotCreatedException extends RuntimeException {
    public MeasurementNotCreatedException(String msg) {
        super(msg);
    }
}
