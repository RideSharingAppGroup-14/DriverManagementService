package com.ridesharing.drivermanagementservice.services;

import com.ridesharing.drivermanagementservice.dtos.driver.DriverDto;
import com.ridesharing.drivermanagementservice.dtos.requests.ProfileUpdateDto;
import com.ridesharing.drivermanagementservice.exceptions.InvalidDriverException;
import com.ridesharing.drivermanagementservice.models.DriverProfile;
import com.ridesharing.drivermanagementservice.repositories.DriverProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final DriverProfileRepository driverProfileRepository;
    private final ExternalServicesHandler externalServicesHandler;

    @Override
    public DriverDto getProfile(String driverId) {
        Optional<DriverProfile> profileOptional = driverProfileRepository.findByDriverId(driverId);
        DriverProfile profile;
        if (profileOptional.isPresent()) {
            profile = profileOptional.get();
        } else {
            profile = externalServicesHandler.getProfile(driverId);
            if (profile == null)
                throw new InvalidDriverException("Invalid driver");
            driverProfileRepository.save(profile);
        }

        return getDriverDto(profile);
    }

    @Override
    public void updateProfile(String driverId, ProfileUpdateDto profileUpdateDto) {
        DriverProfile profile = driverProfileRepository.findByDriverId(driverId)
                .orElseThrow(() -> new InvalidDriverException("Invalid driver"));

        // Update in User Management first
        externalServicesHandler.updateProfile(driverId, profileUpdateDto);

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

    private static DriverDto getDriverDto(DriverProfile profile) {
        DriverDto dto = new DriverDto();
        dto.setDriverId(profile.getDriverId());
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
}
