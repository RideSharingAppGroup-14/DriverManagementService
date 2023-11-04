package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseDriverModel extends BaseModel {
    @Column(name = "driver_id")
    private String driverId;
}
