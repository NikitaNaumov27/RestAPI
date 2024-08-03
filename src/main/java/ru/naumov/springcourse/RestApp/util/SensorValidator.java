package ru.naumov.springcourse.RestApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.naumov.springcourse.RestApp.dto.SensorDTO;
import ru.naumov.springcourse.RestApp.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SensorDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SensorDTO sensorDTO  = (SensorDTO) o;
        if (sensorService.findByName(sensorDTO.getName()).isPresent()){
            errors.rejectValue("name", "", "Сенсор с именем " + sensorDTO.getName() + " уже существует");
        }
    }
}
