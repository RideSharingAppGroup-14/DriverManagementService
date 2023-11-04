package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Availability extends BaseDriverModel {

    private boolean status;
}
