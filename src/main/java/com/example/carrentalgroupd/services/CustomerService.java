package com.example.carrentalgroupd.services;

import com.example.carrentalgroupd.dtos.CreateCustomerDTO;
import com.example.carrentalgroupd.dtos.UpdateCustomerDTO;
import com.example.carrentalgroupd.entities.Booking;
import com.example.carrentalgroupd.entities.Customer;
import com.example.carrentalgroupd.exceptions.ResourceNotFoundException;
import com.example.carrentalgroupd.repositories.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.awt.event.WindowFocusListener;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    private BookingService bookingService;
    private Logger logger = Logger.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(CreateCustomerDTO createCustomerDTO) {
        Customer customer = convertToEntity(createCustomerDTO);
        logger.info("Admin added a customer");
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(UpdateCustomerDTO updateCustomerDTO) {
        Customer existingCustomer = customerRepository.findById(updateCustomerDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", updateCustomerDTO.getId() ));

        // Optional check for username conflicts can be added here if necessary.
        updateEntityFromDTO(existingCustomer, updateCustomerDTO);
        logger.info("Admin updated a customer");
        return customerRepository.save(existingCustomer);
    }

    @Transactional
    public void deleteCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));

        List<Booking> bookings = bookingService.findBookingsByUserId(customerId);

        if (!bookings.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The customer has associated bookings");
        }

        logger.info("Admin deleted a customer");
        customerRepository.delete(customer);
    }

    private Customer convertToEntity(CreateCustomerDTO createCustomerDTO) {
        Customer customer = new Customer();
        customer.setUsername(createCustomerDTO.getUsername());
        customer.setName(createCustomerDTO.getName());
        customer.setAddress(createCustomerDTO.getAddress());
        customer.setEmail(createCustomerDTO.getEmail());
        customer.setPhoneNumber(createCustomerDTO.getPhoneNumber());
        return customer;
    }

    private void updateEntityFromDTO(Customer customer, UpdateCustomerDTO updateCustomerDTO) {
        customer.setUsername(updateCustomerDTO.getUsername());
        customer.setName(updateCustomerDTO.getName());
        customer.setAddress(updateCustomerDTO.getAddress());
        customer.setEmail(updateCustomerDTO.getEmail());
        customer.setPhoneNumber(updateCustomerDTO.getPhoneNumber());
    }
}
