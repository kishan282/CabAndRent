package com.nks.driver_details.controller;

import com.nks.driver_details.dto.DriverDetails;
import com.nks.driver_details.dto.Response;
import com.nks.driver_details.services.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class DriverController {

    private final ServiceFactory serviceFactory;

    @Autowired
    public DriverController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> registerUser(@RequestBody DriverDetails driverDetail) throws Exception {
        return ResponseEntity.status(201).body(serviceFactory.saveUser(driverDetail));
    }

    @GetMapping(path="/user/{}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DriverDetails fetchUser(@RequestParam String key) throws IOException {
        return serviceFactory.fetchUser(key);
    }

}
