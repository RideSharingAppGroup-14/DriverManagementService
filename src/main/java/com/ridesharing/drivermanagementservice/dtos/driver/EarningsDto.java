package com.ridesharing.drivermanagementservice.dtos.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EarningsDto {

    @JsonProperty("total_earnings")
    private float totalEarnings;

    @JsonProperty("current_balance")
    private float currentBalance;
}
