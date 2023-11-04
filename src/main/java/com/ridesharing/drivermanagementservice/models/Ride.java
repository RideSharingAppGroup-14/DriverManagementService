package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
public class Ride extends BaseDriverModel {
    private String rideId;
    private String pickupAddress;
    private double pickupLatitude;
    private double pickupLongitude;
    private Instant pickupTimestamp;
    private String dropoffAddress;
    private double dropoffLatitude;
    private double dropoffLongitude;
    private Instant dropoffTimestamp;
    private String riderFirstName;
    private String riderLastName;
    private String riderPhone;
    private String status;
    private Float distance;
    private Float amount;
    private Integer duration;

    @UpdateTimestamp
    private Instant updatedAt;
}
