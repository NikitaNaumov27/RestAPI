package ru.naumov.springcourse.RestApp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {

    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double value;

    @NotNull
    private Boolean isRaining;

    @NotNull
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        this.isRaining = raining;
    }

    public SensorDTO getSensor() {return sensor;}

    public void setSensor(SensorDTO sensor) {this.sensor = sensor;}
}