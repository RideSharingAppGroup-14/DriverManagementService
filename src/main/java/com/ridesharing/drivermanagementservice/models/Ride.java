package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Ride", uniqueConstraints = {
        @UniqueConstraint(name = "uc_ride_rideid", columnNames = {"rideId"})
})
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
    private Double distance;
    private Float amount;
    private Integer duration;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(mappedBy = "ride")
    List<RideLocation> locations;
}
