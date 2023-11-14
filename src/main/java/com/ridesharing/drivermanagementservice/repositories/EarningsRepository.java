package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.Earnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EarningsRepository extends JpaRepository<Earnings, Long> {

    Optional<Earnings> findByDriverId(UUID driverId);
}
