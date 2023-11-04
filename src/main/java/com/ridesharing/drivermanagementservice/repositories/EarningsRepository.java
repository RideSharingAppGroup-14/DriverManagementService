package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.Earnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EarningsRepository extends JpaRepository<Earnings, Long> {

    Optional<Earnings> findByDriverId(String driverId);
}
