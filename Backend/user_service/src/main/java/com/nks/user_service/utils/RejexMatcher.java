package com.nks.user_service.utils;

public class RejexMatcher {

    private boolean isValidEmai(String key){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-za-z0-9.-]+$";
        return key.matches(emailRegex);
    }

    private boolean isValidPhone(String key){
        String phoneRegex = "^(\\+91|91)?[6-9]\\d{9}$";
        return key.matches(phoneRegex);
    }

    private boolean isValidUserId(String key){
        String userIdRejex = "^[a-zA-Z0-9_-]{4,20}$";
        return key.matches(userIdRejex);
    }

    public String RejexMatcher(String key) {
        if(isValidEmai(key)){
            return "email";
        }
        else if(isValidPhone(key)){
            return "phone";
        }
        else if(isValidUserId(key)){
            return "userid";
        }
        return "INVALID";
    }

}
