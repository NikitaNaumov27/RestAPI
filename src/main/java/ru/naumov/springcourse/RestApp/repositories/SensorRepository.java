package ru.naumov.springcourse.RestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.naumov.springcourse.RestApp.models.Sensor;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    public Optional<Sensor> findByName(String name);
}
