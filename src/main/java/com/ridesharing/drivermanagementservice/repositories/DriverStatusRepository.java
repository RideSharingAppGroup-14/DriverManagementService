package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverStatusRepository extends JpaRepository<DriverStatus, Long> {

    Optional<DriverStatus> findByDriverId(UUID driverId);

    List<DriverStatus> findAllByCity_IdAndStatusAndLatitudeBetweenAndLongitudeBetween(
            Long cityId, boolean status,
            double minLatitude, double maxLatitude,
            double minLongitude, double maxLongitude);
}
