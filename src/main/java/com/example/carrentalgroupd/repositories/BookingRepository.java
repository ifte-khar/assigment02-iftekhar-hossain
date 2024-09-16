package com.example.carrentalgroupd.repositories;

import com.example.carrentalgroupd.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long userId);

}
