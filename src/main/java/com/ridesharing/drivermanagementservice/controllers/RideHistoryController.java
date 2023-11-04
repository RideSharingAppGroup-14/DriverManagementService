package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.ride.RideHistoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/rides")
public class RideHistoryController {

    @GetMapping("/{driver_id}")
    public ResponseEntity<RideHistoryDto> getRideHistory(
            @PathVariable("driver_id") String driverId,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) {
        return ResponseEntity.ok(new RideHistoryDto());
    }
}
