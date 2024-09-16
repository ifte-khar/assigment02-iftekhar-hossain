package com.example.carrentalgroupd.services;

import com.example.carrentalgroupd.dtos.CarDTO;
import com.example.carrentalgroupd.dtos.CarUpdateDTO;
import com.example.carrentalgroupd.dtos.DeleteCarDTO;
import com.example.carrentalgroupd.entities.Car;
import com.example.carrentalgroupd.entities.Customer;
import com.example.carrentalgroupd.exceptions.ResourceNotFoundException;
import com.example.carrentalgroupd.repositories.CarRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    private Logger logger = Logger.getLogger(CarService.class);
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> listAllCars() {
        return carRepository.findAll();
    }

    public List<Car> listAvailableCars() {
        List<Car> allCars = carRepository.findAll();
        return allCars.stream()
                .filter(car -> !car.getIsBooked())
                .collect(Collectors.toList());
    }

    public Car addCar(CarDTO carDTO) {
        if (carRepository.findByRegistrationNumber(carDTO.getRegistrationNumber()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A car with the same registration number already exists");
        }
        Car car = new Car(carDTO);
        car.setPricePerDay(carDTO.getPricePerDay());
        car.setFabric(carDTO.getFabric());
        car.setModel(carDTO.getModel());
        car.setRegistrationNumber(carDTO.getRegistrationNumber());
        car.setIsBooked(false);
        logger.info("Admin added a car");
        return carRepository.save(car);

    }

    public Car updateCar(CarUpdateDTO carUpdateDTO) {
        Car existingCar = carRepository.findById(carUpdateDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carUpdateDTO.getId() ));

        Optional<Car> existingCarByRegNum = carRepository.findByRegistrationNumber(carUpdateDTO.getRegistrationNumber());

        if (existingCarByRegNum.isPresent()&& !existingCarByRegNum.get().getId().equals(carUpdateDTO.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A car with the same registration number already exists");
        }

        existingCar.setPricePerDay(carUpdateDTO.getPricePerDay());
        existingCar.setFabric(carUpdateDTO.getFabric());
        existingCar.setModel(carUpdateDTO.getModel());
        existingCar.setRegistrationNumber(carUpdateDTO.getRegistrationNumber());
        existingCar.setIsBooked(carUpdateDTO.getIsBooked());
        logger.info("Admin updated a car");
        return carRepository.save(existingCar);
    }

    public void deleteCar(DeleteCarDTO deleteCarDTO) {
        Car car = carRepository.findById(deleteCarDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", deleteCarDTO.getId() ));

        if (car.getIsBooked()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The car has active bookings and can not be deleted");
        }

        logger.info("Admin deleted a car");
        carRepository.delete(car);
    }
}
