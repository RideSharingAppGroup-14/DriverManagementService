package com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UserProfileDto {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;
    private Boolean approved;
    private String city;
    private String state;
    private String country;
}
