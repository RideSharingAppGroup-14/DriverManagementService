package com.ridesharing.drivermanagementservice.controllers;

import com.ridesharing.drivermanagementservice.dtos.driver.EarningsDto;
import com.ridesharing.drivermanagementservice.exceptions.EarningsNotFoundException;
import com.ridesharing.drivermanagementservice.services.EarningsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/earnings")
public class DriverEarningsController {

    private EarningsService earningsService;

    public DriverEarningsController(EarningsService earningsService) {
        this.earningsService = earningsService;
    }

    @GetMapping("/{driver_id}")
    public ResponseEntity<EarningsDto> getEarnings(@PathVariable("driver_id") UUID driverId) throws EarningsNotFoundException {
        return ResponseEntity.ok(earningsService.getEarnings(driverId));
    }
}
