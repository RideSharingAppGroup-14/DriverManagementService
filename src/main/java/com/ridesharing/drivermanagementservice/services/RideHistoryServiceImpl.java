package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.ride.RideDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RideHistoryDto;
import com.ridesharing.drivermanagementservice.dtos.ride.RideHistoryMetadata;
import com.ridesharing.drivermanagementservice.exceptions.RidesNotFoundException;
import com.ridesharing.drivermanagementservice.models.Ride;
import com.ridesharing.drivermanagementservice.repositories.RideRepository;
import com.ridesharing.drivermanagementservice.utils.RideUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RideHistoryServiceImpl implements RideHistoryService {

    private final RideRepository rideRepository;

    public RideHistoryServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public RideHistoryDto getRideHistory(String driverId, int offset, int limit) throws RidesNotFoundException {
        Pageable pageable = PageRequest.of(offset, limit);
        // Page<Ride> executes additional count query. Use Slice<Ride> or List<Ride> for large set of data.
        Page<Ride> rideList = rideRepository.findAllByDriverIdOrderByUpdatedAtDesc(driverId, pageable);
        if (rideList.isEmpty())
            throw new RidesNotFoundException("No rides found");

        RideHistoryDto rideHistoryDto = new RideHistoryDto();

        RideHistoryMetadata metadata = new RideHistoryMetadata();
        metadata.setTotal((int) rideList.getTotalElements());
        metadata.setCount(rideList.getNumberOfElements());
        metadata.setOffset(rideList.getNumber());
        metadata.setLimit(rideList.getSize());

        rideHistoryDto.setMetadata(metadata);

        rideHistoryDto.setRides(
            rideList.get()
            .map(RideHistoryServiceImpl::getRideDto)
            .toList());

        return rideHistoryDto;
    }

    private static RideDto getRideDto(Ride ride) {
        RideDto rideDto = new RideDto();
        rideDto.setPickup(RideUtils.getPickupDto(ride));
        rideDto.setDropoff(RideUtils.getDropOffDto(ride));
        rideDto.setRider(RideUtils.getRiderDto(ride));

        rideDto.setRideId(ride.getRideId());
        rideDto.setStatus(ride.getStatus());
        rideDto.setDuration(ride.getDuration());
        rideDto.setDistance(ride.getDistance());
        rideDto.setAmount(ride.getAmount());
        return rideDto;
    }

}
