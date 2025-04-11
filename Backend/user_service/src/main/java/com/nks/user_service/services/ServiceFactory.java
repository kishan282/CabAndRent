package com.nks.user_service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nks.user_service.dto.Response;
import com.nks.user_service.dto.UserDetail;
import com.nks.user_service.model.Customer;
import com.nks.user_service.repositiory.CustomerRepository;
import com.nks.user_service.utils.IdCreationHelper;
import com.nks.user_service.utils.RejexMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

@Service
public class ServiceFactory {

    private final CustomerRepository customerRepository;

    public ServiceFactory(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("Service is being initialized");
    }

    IdCreationHelper idCreationHelper = new IdCreationHelper();

    public Response saveUser(UserDetail userDetail) throws Exception {
        customerRepository.ensureIndexExists();
        if(!customerRepository.duplicateUser(userDetail.getEmail(), userDetail.getPhone())) {
            String userId = idCreationHelper.idCreationLogic(userDetail.getName());
            while(customerRepository.findById(userId).isPresent()) {
                userId = idCreationHelper.idCreationLogic(userDetail.getName());
            }
            Customer customer = new Customer(userId, userDetail.getEmail(), userDetail.getName(),
                    userDetail.getPhone(), userDetail.getGender(), userDetail.getMaskedPhone());
            customerRepository.save(customer);
            return new Response(customer.getUserId(), "CREATED");
        }
        return new Response("User already present, please sign in using registerd phone, email or id", "SUCCESS");
    }

    public UserDetail fetchUser(String key) throws IOException {
        Optional<Customer> customer = Optional.empty();

        String identifier = new RejexMatcher().RejexMatcher(key);
        switch (identifier) {
            case "email":
                customer = customerRepository.findByEmail(key);
                break;
            case "phone":
                customer = customerRepository.findByPhone(key);
                break;
            case "uderid":
                customer = customerRepository.findById(key);
                break;
        }
        return new ObjectMapper()
                .convertValue(customer, UserDetail.class);
    }

}
