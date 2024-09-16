package com.example.carrentalgroupd;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Car Rental API", version = "1.0", description = "API Documentation for Car Rental backend application"))
public class CarRentalGroupDApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalGroupDApplication.class, args);
    }

}
