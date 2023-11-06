package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class DriverProfile extends BaseDriverModel {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;

    @Column(columnDefinition = "TINYINT")
    private Boolean approved;

    private String city;
    private String state;
    private String country;
}
