package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.models.DriverStatus;
import com.ridesharing.drivermanagementservice.repositories.DriverStatusRepository;
import com.ridesharing.drivermanagementservice.utils.BoundingBox;
import com.ridesharing.drivermanagementservice.utils.BoundingBoxGenerationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A very simple
 */
@Service
@AllArgsConstructor
public class BoundingBoxDriversSearchStrategy implements DriversSearchStrategy {

    private BoundingBoxGenerationStrategy boundingBoxGenerationStrategy;
    private DriverStatusRepository driverStatusRepository;

    @Override
    public List<DriverStatus> getNearbyDrivers(long cityId, double pickupLatitude, double pickupLongitude, double radiusInKm) {
        BoundingBox boundingBox = boundingBoxGenerationStrategy.generate(pickupLatitude, pickupLongitude, radiusInKm);

        List<DriverStatus> driverStatusList = driverStatusRepository
                .findAllByCity_IdAndStatusAndLatitudeBetweenAndLongitudeBetween(
                        cityId, true,
                        boundingBox.getMinLatitude(), boundingBox.getMaxLatitude(),
                        boundingBox.getMinLongitude(), boundingBox.getMaxLongitude());

        return driverStatusList;
    }
}
