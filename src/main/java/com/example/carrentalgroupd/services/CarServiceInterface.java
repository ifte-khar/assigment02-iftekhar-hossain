package com.example.carrentalgroupd.services;


import com.example.carrentalgroupd.dtos.CarDTO;
import com.example.carrentalgroupd.dtos.CarUpdateDTO;
import com.example.carrentalgroupd.dtos.DeleteCarDTO;
import com.example.carrentalgroupd.entities.Car;

import java.util.List;

public interface CarServiceInterface {
    List<Car> listAllCars();
    List<Car> listAvailableCars();
    Car addCar (CarDTO carDTO);
    Car updateCar(CarUpdateDTO carUpdateDTO);
    void deleteCar(DeleteCarDTO deleteCarDTO);
}

