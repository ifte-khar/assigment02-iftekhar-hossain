package com.example.carrentalgroupd.services;


import com.example.carrentalgroupd.entities.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingServiceInterface {
    List<Booking> listAll();
    List<Booking> findBookingsByUserId(Long userId);
    boolean cancelBookingAndUpdateCar(Long bookingId);

    List<Booking> findBookingsByCustomerId(Long customerId);
}

