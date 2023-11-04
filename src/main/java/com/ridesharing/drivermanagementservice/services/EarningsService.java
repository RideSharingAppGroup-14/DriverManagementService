package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.EarningsDto;
import com.ridesharing.drivermanagementservice.exceptions.EarningsNotFoundException;

public interface EarningsService {
    EarningsDto getEarnings(String driverId) throws EarningsNotFoundException;
}
