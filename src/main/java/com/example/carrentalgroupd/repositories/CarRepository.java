package com.example.carrentalgroupd.repositories;

import com.example.carrentalgroupd.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByRegistrationNumber(String registrationNumber);
}
