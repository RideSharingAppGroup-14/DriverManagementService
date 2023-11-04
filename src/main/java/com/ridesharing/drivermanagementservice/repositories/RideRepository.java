package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    Optional<Ride> findByRideId(String rideId);
}
