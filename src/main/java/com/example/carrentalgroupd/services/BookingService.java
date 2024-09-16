package com.example.carrentalgroupd.services;

import com.example.carrentalgroupd.dtos.BookingCancelDTO;
import com.example.carrentalgroupd.dtos.BookingCarDTO;
import com.example.carrentalgroupd.entities.Booking;
import com.example.carrentalgroupd.entities.Car;
import com.example.carrentalgroupd.entities.Customer;
import com.example.carrentalgroupd.exceptions.ResourceNotFoundException;
import com.example.carrentalgroupd.repositories.BookingRepository;
import com.example.carrentalgroupd.repositories.CarRepository;
import com.example.carrentalgroupd.repositories.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private Logger logger = Logger.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    public String createBooking(Long userId, Long carId, Date date, int numberOfDays) {
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", userId));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));

        if (car.getIsBooked()) {
            return "Car is already booked";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
        Date endDate = calendar.getTime();
        Date currentDate = new Date();
        boolean isActive = currentDate.after(date) && currentDate.before(endDate);

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setCar(car);
        booking.setBookingDate(date);
        booking.setNumberOfDays(numberOfDays);
        booking.setIsActive(isActive);
        car.setIsBooked(isActive);
        carRepository.save(car);
        bookingRepository.save(booking);
        logger.info("Admin Created a booking");
        return "Booking created successfully";
    }

    public List<Booking> listAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> findBookingsByUserId(Long userId) {
        if (customerRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("Customer", "id", userId);
        }

        return bookingRepository.findByCustomerId(userId);
    }

    @Transactional
    public ResponseEntity<String> cancelBookingAndUpdateCar2(@RequestBody BookingCancelDTO bookingCancelDto) {
        return bookingRepository.findById(bookingCancelDto.getBookingId())
                .map(booking -> {
                    if (booking.getIsActive()) {
                        return ResponseEntity.badRequest().body("Active booking ongoing");
                    }
                    booking.setIsActive(false);
                    booking.getCar().setIsBooked(false);
                    bookingRepository.save(booking);
                    carRepository.save(booking.getCar());
                    return ResponseEntity.ok("Booking cancelled and car updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    public boolean cancelBookingAndUpdateCar(BookingCancelDTO bookingCancelDto) {
        Booking booking = bookingRepository.findById(bookingCancelDto.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingCancelDto.getBookingId()));

        if (booking.getIsActive()) {
            booking.setIsActive(false);
            booking.getCar().setIsBooked(false);
            booking.setNumberOfDays(0);
            bookingRepository.save(booking);
            carRepository.save(booking.getCar());
            logger.info("Admin canceled a booking");
            return true;
        }
        return false;
    }

}
