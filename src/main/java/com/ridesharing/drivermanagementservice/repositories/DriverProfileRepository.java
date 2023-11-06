package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {

    Optional<DriverProfile> findByDriverId(String driverId);
}
