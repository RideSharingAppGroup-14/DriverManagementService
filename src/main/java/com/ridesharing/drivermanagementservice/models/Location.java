package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Location extends BaseDriverModel {

    private double latitude;
    private double longitude;
}
