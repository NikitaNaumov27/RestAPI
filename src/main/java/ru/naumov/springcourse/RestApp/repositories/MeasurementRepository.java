package ru.naumov.springcourse.RestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.naumov.springcourse.RestApp.models.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
