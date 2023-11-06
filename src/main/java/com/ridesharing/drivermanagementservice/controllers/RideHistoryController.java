package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.ride.RideHistoryDto;
import com.ridesharing.drivermanagementservice.exceptions.RidesNotFoundException;
import com.ridesharing.drivermanagementservice.services.RideHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rides")
public class RideHistoryController {

    private final RideHistoryService rideHistoryService;

    public RideHistoryController(RideHistoryService rideHistoryService) {
        this.rideHistoryService = rideHistoryService;
    }

    @GetMapping("/{driver_id}")
    public ResponseEntity<RideHistoryDto> getRides(
            @PathVariable("driver_id") String driverId,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) throws RidesNotFoundException {
        return ResponseEntity.ok(rideHistoryService.getRideHistory(driverId, offset, limit));
    }
}
