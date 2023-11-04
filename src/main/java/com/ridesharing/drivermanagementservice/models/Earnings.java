package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Earnings extends BaseModel {

    @Column(name = "driver_id")
    private String driverId;

    @Column(name = "total_earnings")
    private float totalEarnings;

    @Column(name = "current_balance")
    private float currentBalance;
}
