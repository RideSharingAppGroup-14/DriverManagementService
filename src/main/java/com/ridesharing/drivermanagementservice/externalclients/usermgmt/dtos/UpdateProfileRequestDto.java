package com.ridesharing.drivermanagementservice.externalclients.usermgmt.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDto {
    private String phone;
    private String email;
    private String city;
    private String state;
    private String country;
}
