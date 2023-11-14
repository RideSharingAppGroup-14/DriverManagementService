package com.ridesharing.drivermanagementservice.dtos.driver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DriverDto {

    @JsonProperty("driver_id")
    private UUID driverId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;

    @JsonProperty("is_approved")
    private boolean approved;

    private String city;
    private String state;
    private String country;
}
