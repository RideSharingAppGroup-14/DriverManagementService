package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    @Query("select r from Ride r where r.driverId = ?1 and r.status = 'assigned' or r.status = 'started'")
    Optional<Ride> findActiveRideByDriverId(String driverId);

    Optional<Ride> findByRideId(String rideId);


}
