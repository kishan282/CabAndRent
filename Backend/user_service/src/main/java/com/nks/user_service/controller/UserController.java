package com.nks.user_service.controller;

import com.nks.user_service.dto.Response;
import com.nks.user_service.dto.UserDetail;
import com.nks.user_service.model.Customer;
import com.nks.user_service.services.ServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UserController {

    private final ServiceFactory serviceFactory;

    @Autowired
    public UserController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> registerUser(@RequestBody UserDetail userDetail) throws Exception {
        return ResponseEntity.status(201).body(serviceFactory.saveUser(userDetail));
    }

    @GetMapping(path="/user/{}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDetail fetchUser(@RequestParam String key) throws IOException {
        return serviceFactory.fetchUser(key);
    }

}
