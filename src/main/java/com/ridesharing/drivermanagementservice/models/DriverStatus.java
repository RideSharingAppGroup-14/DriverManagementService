package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
public class DriverStatus extends BaseDriverModel {

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private Boolean status;
    private Double latitude;
    private Double longitude;

    @UpdateTimestamp
    private Instant statusUpdatedAt;

    @UpdateTimestamp
    private Instant locationUpdatedAt;
}
