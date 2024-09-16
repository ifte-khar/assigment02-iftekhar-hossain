package com.example.carrentalgroupd.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Customer customer;

    @ManyToOne
    private Car car;

    private Date bookingDate;

    private int numberOfDays;

    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    // Setter for numberOfDays
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Boolean getIsActive() {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(bookingDate);
        Calendar endCal = (Calendar) startCal.clone();
        endCal.add(Calendar.DAY_OF_MONTH, numberOfDays);

        Calendar today = Calendar.getInstance();
        return !today.before(startCal) && !today.after(endCal);
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

}
