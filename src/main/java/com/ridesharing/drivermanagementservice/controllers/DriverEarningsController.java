package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.driver.EarningsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/earnings")
public class DriverEarningsController {

    @GetMapping("/{driver_id}")
    public ResponseEntity<EarningsDto> getEarnings(@PathVariable("driver_id") String driverId) {
        return ResponseEntity.ok(new EarningsDto());
    }
}
