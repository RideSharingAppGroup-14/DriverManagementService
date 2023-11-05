package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.RideLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideLocationRepository extends JpaRepository<RideLocation, Long> {
}
