package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.EarningsDto;
import com.ridesharing.drivermanagementservice.exceptions.EarningsNotFoundException;
import com.ridesharing.drivermanagementservice.models.Earnings;
import com.ridesharing.drivermanagementservice.repositories.EarningsRepository;
import org.springframework.stereotype.Service;

@Service
public class EarningsServiceImpl implements EarningsService {

    private final EarningsRepository earningsRepository;

    public EarningsServiceImpl(EarningsRepository earningsRepository) {
        this.earningsRepository = earningsRepository;
    }

    @Override
    public EarningsDto getEarnings(String driverId) throws EarningsNotFoundException {
        Earnings earnings = earningsRepository.findByDriverId(driverId)
                .orElseThrow(() -> new EarningsNotFoundException("No earnings found"));

        EarningsDto earningsDto = new EarningsDto();
        earningsDto.setTotalEarnings(earnings.getTotalEarnings());
        earningsDto.setCurrentBalance(earnings.getCurrentBalance());
        return earningsDto;
    }
}
