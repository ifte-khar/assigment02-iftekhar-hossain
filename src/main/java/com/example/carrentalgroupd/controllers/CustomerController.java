package com.example.carrentalgroupd.controllers;

import com.example.carrentalgroupd.dtos.*;
import com.example.carrentalgroupd.entities.Booking;
import com.example.carrentalgroupd.entities.Car;
import com.example.carrentalgroupd.services.BookingService;
import com.example.carrentalgroupd.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private final CarService carService;
    private final BookingService bookingService;

    @Autowired
    public CustomerController(CarService carService, BookingService bookingService) {
        this.carService = carService;
        this.bookingService = bookingService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> list() {
        return ResponseEntity.ok(carService.listAvailableCars());
    }

    @PostMapping("/ordercar")
    public ResponseEntity<String> createBooking(@RequestBody BookingCarDTO bookingCarDTO) {
        String result = bookingService.createBooking(bookingCarDTO.getUserId(), bookingCarDTO.getCarId(),
                bookingCarDTO.getDate(), bookingCarDTO.getNumberOfDays());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/myorders")
    public ResponseEntity<List<Booking>> listUserBookings(@RequestBody CustomerOrdersDTO customerOrdersDto) {
        List<Booking> bookings = bookingService.findBookingsByUserId(customerOrdersDto.getId());
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/cancelorder")
    public ResponseEntity<String> cancelBooking(@RequestBody BookingCancelDTO bookingCancelDto) {
        boolean updated = bookingService.cancelBookingAndUpdateCar(bookingCancelDto);

        if (updated) {
            return ResponseEntity.ok("Booking cancelled and car updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Unable to cancel booking");
        }
    }


}
