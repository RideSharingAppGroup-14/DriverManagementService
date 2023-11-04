package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.models.DriverStatus;

import java.util.List;

public interface DriversSearchStrategy {

    List<DriverStatus> getNearbyDrivers(long cityId, double pickupLatitude, double pickupLongitude, double radiusInKm);
}
