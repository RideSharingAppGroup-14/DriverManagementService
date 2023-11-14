package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity(name = "ride_location")
public class RideLocation extends BaseModel {

    private double latitude;
    private double longitude;
    private Instant timestamp;

}