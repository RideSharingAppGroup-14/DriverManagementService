package com.ridesharing.drivermanagementservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Earnings extends BaseDriverModel {

    @Column(name = "total_earnings")
    private float totalEarnings;

    @Column(name = "current_balance")
    private float currentBalance;
}
