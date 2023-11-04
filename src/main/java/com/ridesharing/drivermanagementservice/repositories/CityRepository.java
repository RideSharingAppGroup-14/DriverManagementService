package com.ridesharing.drivermanagementservice.repositories;

import com.ridesharing.drivermanagementservice.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select c from City c where :latitude >= c.minLatitude and :latitude <= c.maxLatitude  and :longitude >= c.minLongitude and :longitude <= c.maxLongitude")
    Optional<City> findByCoordinates(@Param("latitude") double latitude, @Param("longitude") double longitude);

}
