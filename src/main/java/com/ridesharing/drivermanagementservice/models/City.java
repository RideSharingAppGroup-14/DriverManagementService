package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class City extends BaseModel {
    private String city;
    private String state;
    private String country;

    private double minLatitude;
    private double minLongitude;
    private double maxLatitude;
    private double maxLongitude;
}
