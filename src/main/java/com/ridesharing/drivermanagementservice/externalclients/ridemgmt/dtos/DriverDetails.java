package com.ridesharing.drivermanagementservice.externalclients.ridemgmt.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDetails {
    private String driverId;
    private String firstName;
    private String lastName;
    private String phone;
}
