package com.nks.driver_details.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nks.driver_details.dto.DriverDetails;
import com.nks.driver_details.dto.Response;
import com.nks.driver_details.model.Driver;
import com.nks.driver_details.repository.DriverRepository;
import com.nks.driver_details.utils.IdCreationHelper;
import com.nks.driver_details.utils.RejexMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

@Service
public class ServiceFactory {

    private final DriverRepository driverRepository;

    public ServiceFactory(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("Service is being initialized");
    }

    IdCreationHelper idCreationHelper = new IdCreationHelper();

    public Response saveUser(DriverDetails driverDetails) throws Exception {
        driverRepository.ensureIndexExists();
        if(!driverRepository.duplicateUser(driverDetails.getEmail(), driverDetails.getPhone())) {
            String userId = idCreationHelper.idCreationLogic(driverDetails.getName());
            while(driverRepository.findById(userId).isPresent()) {
                userId = idCreationHelper.idCreationLogic(driverDetails.getName());
            }
            Driver driver = new Driver(userId, driverDetails.getName(), driverDetails.getPhone(), driverDetails.getEmail(),
                    driverDetails.getLicenseNumber(), driverDetails.getVehicleNumber(), driverDetails.getVehicleType(),
                    driverDetails.isActive(), driverDetails.isVerified());
            driverRepository.save(driver);
            return new Response(driver.getDriverId(), "CREATED");
        }
        return new Response("Driver already present, please sign in using registerd phone, email or id", "SUCCESS");
    }

    public DriverDetails fetchUser(String key) throws IOException {
        Optional<Driver> driver = Optional.empty();

        String identifier = new RejexMatcher().RejexMatcher(key);
        switch (identifier) {
            case "email":
                driver = driverRepository.findByEmail(key);
                break;
            case "phone":
                driver = driverRepository.findByPhone(key);
                break;
            case "id":
                driver = driverRepository.findById(key);
                break;
        }
        return new ObjectMapper()
                .convertValue(driver, DriverDetails.class);
    }

}