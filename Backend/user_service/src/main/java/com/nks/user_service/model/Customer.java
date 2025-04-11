package com.nks.user_service.model;

public final class Customer {

    private final String userId;
    private final String name;
    private final String email;

    public Customer(String userId, String email, String name, String phone, String gender, boolean maskedPhone) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.maskedPhone = maskedPhone;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public boolean isMaskedPhone() {
        return maskedPhone;
    }

    private final String phone;
    private final String gender;
    private final boolean maskedPhone;

}
