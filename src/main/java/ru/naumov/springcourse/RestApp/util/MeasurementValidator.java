package ru.naumov.springcourse.RestApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumov.springcourse.RestApp.dto.MeasurementDTO;
import ru.naumov.springcourse.RestApp.services.SensorService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MeasurementDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) o;

        if (measurementDTO.getSensor() == null) {
            return;
        }

        if (sensorService.findByName(measurementDTO.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor","", "Нет зарегистрированного сенсора с таким именем!");
    }
}
