package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.DriverDto;
import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidDriverException;
import com.ridesharing.drivermanagementservice.models.DriverProfile;
import com.ridesharing.drivermanagementservice.repositories.DriverProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final DriverProfileRepository driverProfileRepository;

    public ProfileServiceImpl(DriverProfileRepository driverProfileRepository) {
        this.driverProfileRepository = driverProfileRepository;
    }

    @Override
    public DriverDto getProfile(String driverId) {
        DriverProfile profile = driverProfileRepository.findByDriverId(driverId)
                .orElseThrow(() -> new InvalidDriverException("Invalid driver"));

        DriverDto dto = new DriverDto();
        dto.setDriverId(driverId);
        dto.setFirstName(profile.getFirstName());
        dto.setLastName(profile.getLastName());
        dto.setPhone(profile.getPhone());
        dto.setEmail(profile.getEmail());
        dto.setGender(profile.getGender());
        dto.setDob(profile.getDob());
        dto.setApproved(profile.getApproved());
        dto.setCity(profile.getCity());
        dto.setState(profile.getState());
        dto.setCountry(profile.getCountry());

        return dto;
    }

    @Override
    public void updateProfile(String driverId, ProfileUpdateDto profileUpdateDto) {
        DriverProfile profile = driverProfileRepository.findByDriverId(driverId)
                .orElseThrow(() -> new InvalidDriverException("Invalid driver"));

        if (profileUpdateDto.getPhone() != null)
            profile.setPhone(profileUpdateDto.getPhone());
        if (profileUpdateDto.getEmail() != null)
            profile.setEmail(profileUpdateDto.getEmail());
        if (profileUpdateDto.getCity() != null)
            profile.setCity(profileUpdateDto.getCity());
        if (profileUpdateDto.getState() != null)
            profile.setState(profileUpdateDto.getState());
        if (profileUpdateDto.getCountry() != null)
            profile.setCountry(profileUpdateDto.getCountry());

        driverProfileRepository.save(profile);
    }
}
