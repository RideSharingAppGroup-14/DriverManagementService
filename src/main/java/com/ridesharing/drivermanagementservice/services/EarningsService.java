package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.EarningsDto;
import com.ridesharing.drivermanagementservice.exceptions.EarningsNotFoundException;

import java.util.UUID;

public interface EarningsService {
    EarningsDto getEarnings(UUID driverId) throws EarningsNotFoundException;
}
