package ru.naumov.springcourse.RestApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.naumov.springcourse.RestApp.dto.MeasurementDTO;
import ru.naumov.springcourse.RestApp.dto.MeasurementsResponse;
import ru.naumov.springcourse.RestApp.models.Measurement;
import ru.naumov.springcourse.RestApp.services.MeasurementService;
import ru.naumov.springcourse.RestApp.util.MeasurementErrorResponse;
import ru.naumov.springcourse.RestApp.util.MeasurementNotCreatedException;
import ru.naumov.springcourse.RestApp.util.MeasurementValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurement")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(ModelMapper modelMapper, MeasurementService measurementService,
                                  MeasurementValidator measurementValidator) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        measurementValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField()).append(" - ")
                        .append(error.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        }

        measurementService.save(convertToMeasurement(measurementDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public MeasurementsResponse getMeasurements() {
        // Обычно список из элементов оборачивается в один объект для пересылки
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }


    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }


    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(final Exception ex) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}