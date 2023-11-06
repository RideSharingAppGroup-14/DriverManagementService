package com.ridesharing.drivermanagementservice.utils;

import com.ridesharing.drivermanagementservice.dtos.ride.RidePlaceDetailsDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RiderDto;
import com.ridesharing.drivermanagementservice.models.Ride;

public class RideUtils {

    public static RidePlaceDetailsDto getPickupDto(Ride ride) {
        RidePlaceDetailsDto pickup = new RidePlaceDetailsDto();
        pickup.setLatitude(ride.getPickupLatitude());
        pickup.setLongitude(ride.getPickupLongitude());
        pickup.setName(ride.getPickupAddress());
        pickup.setTimestamp(ride.getPickupTimestamp());
        return pickup;
    }

    public static RidePlaceDetailsDto getDropOffDto(Ride ride) {
        RidePlaceDetailsDto dropoff = new RidePlaceDetailsDto();
        dropoff.setLatitude(ride.getDropoffLatitude());
        dropoff.setLongitude(ride.getDropoffLongitude());
        dropoff.setName(ride.getDropoffAddress());
        dropoff.setTimestamp(ride.getDropoffTimestamp());
        return dropoff;
    }

    public static RiderDto getRiderDto(Ride ride) {
        RiderDto riderDto = new RiderDto();
        riderDto.setFirstName(ride.getRiderFirstName());
        riderDto.setLastName(ride.getRiderLastName());
        riderDto.setPhone(ride.getRiderPhone());
        return riderDto;
    }
}
