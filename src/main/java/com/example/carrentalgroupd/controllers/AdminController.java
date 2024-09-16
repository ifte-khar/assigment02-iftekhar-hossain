package com.example.carrentalgroupd.controllers;

import com.example.carrentalgroupd.dtos.*;
import com.example.carrentalgroupd.entities.Car;
import com.example.carrentalgroupd.entities.Customer;
import com.example.carrentalgroupd.entities.Booking;
import com.example.carrentalgroupd.services.CarService;
import com.example.carrentalgroupd.services.CustomerService;
import com.example.carrentalgroupd.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    private final CustomerService customerService;
    private final BookingService bookingService;
    private final CarService carService;

    @Autowired
    public AdminController(CustomerService customerService, BookingService bookingService, CarService carService) {
        this.customerService = customerService;
        this.bookingService = bookingService;
        this.carService = carService;
    }

    @GetMapping("/allcars")
    public ResponseEntity<List<Car>> listAllCars() {
        List<Car> cars = carService.listAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> listAllCustomers() {
        List<Customer> customers = customerService.listAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Booking>> listAllBookings() {
        List<Booking> bookings = bookingService.listAll();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/addcustomer")
    public ResponseEntity<Customer> addCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        Customer customer = customerService.addCustomer(createCustomerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PostMapping("/addcar")
    public ResponseEntity<Car> addCar(@RequestBody CarDTO carDTO) {
        Car car = carService.addCar(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(car);
    }

    @PutMapping("/updatecar")
    public ResponseEntity<Car> updateCar(@RequestBody CarUpdateDTO carUpdateDTO) {
        Car updatedCar = carService.updateCar(carUpdateDTO);
        return ResponseEntity.ok(updatedCar);
    }

    @PutMapping("/updatecustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(updateCustomerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/deletecar")
    public ResponseEntity<Void> deleteCar(@RequestBody DeleteCarDTO deleteCarDTO) {
        carService.deleteCar(deleteCarDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletecustomer")
    public ResponseEntity<Void> deleteCustomer(@RequestBody CustomerIdDTO customerIdDto) {
        customerService.deleteCustomerById(customerIdDto.getId());
        return ResponseEntity.ok().build();
    }
}
