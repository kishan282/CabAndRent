package com.nks.driver_details.dto;

public final class DriverDetails {

    private final String name;
    private final String phone;
    private final String email;
    private final String licenseNumber;
    private final String vehicleNumber;
    private final String vehicleType;
    private final boolean active;
    private final boolean verified;

    public DriverDetails(String name, String phone, String email,
                         String licenseNumber, String vehicleNumber,
                         String vehicleType, boolean active, boolean verified) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.active = active;
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVerified() {
        return verified;
    }
}
