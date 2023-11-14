package com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DriverDetails {
    private UUID driverId;
    private String firstName;
    private String lastName;
    private String phone;
}
