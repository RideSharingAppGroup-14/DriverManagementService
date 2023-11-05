package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity(name = "ride_location")
public class RideLocation extends BaseModel {

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "ride_id", referencedColumnName = "rideId")
    private Ride ride;

    private double latitude;
    private double longitude;
    private Instant timestamp;

}