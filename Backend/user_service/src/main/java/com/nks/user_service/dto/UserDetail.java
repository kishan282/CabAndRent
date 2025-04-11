package com.nks.user_service.dto;

public final class UserDetail {

    private final String name;
    private final String email;
    private final String phone;
    private final String gender;
    private final boolean maskedPhone = true;

    public UserDetail(String name, String email, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
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

    public boolean getMaskedPhone() {
        return true;
    }
}
