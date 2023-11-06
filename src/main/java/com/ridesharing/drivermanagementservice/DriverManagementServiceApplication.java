package com.ridesharing.drivermanagementservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DriverManagementServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DriverManagementServiceApplication.class, args);
	}


//	@Autowired
//	EarningsRepository earningsRepository;

//	@Autowired
//	CityRepository cityRepository;

//	@Autowired
// 	RideRepository rideRepository;

// 	@Autowired
//	DriverProfileRepository profileRepository;

	@Override
	public void run(String... args) throws Exception {
//		createRides();
//		createProfile();
//		addCityToStartService();

//		Earnings earnings = new Earnings();
//		earnings.setDriverId(UUID.randomUUID().toString());
//		earnings.setTotalEarnings(1000);
//		earnings.setCurrentBalance(1000);
//
//		earningsRepository.save(earnings);

	}

//	private void addCityToStartService() {
//		City city = new City();
//		city.setCity("Bangalore");
//		city.setState("Karnataka");
//		city.setCountry("India");
//		city.setMinLatitude(12.734289);
//		city.setMinLongitude(77.379198);
//		city.setMaxLatitude(13.173706);
//		city.setMaxLongitude(77.882681);
//
//		cityRepository.save(city);
//	}

//	private void createRides() {
//		Random random = new Random();
//		List<Ride> rides = new ArrayList<>();
//		for (int i = 0; i < 101; i++) {
//			Ride ride = new Ride();
//			ride.setRideId(UUID.randomUUID().toString());
//			ride.setDriverId("d519c53b-34b0-4c94-b3d6-feeafe606d81");
//			ride.setDuration(random.nextInt(60));
//			ride.setStatus(RideStatus.COMPLETED.getValue());
//			ride.setDistance(random.nextDouble(50));
//			ride.setAmount(ride.getDistance().floatValue());
//			ride.setPickupAddress("Subramanyapura");
//			ride.setPickupLatitude(12.892007);
//			ride.setPickupLongitude(77.527817);
//			ride.setPickupTimestamp(Instant.now().minusSeconds(random.nextInt(500)));
//			ride.setDropoffAddress("BTM Layout");
//			ride.setDropoffLatitude(12.916576);
//			ride.setDropoffLongitude(77.610116);
//			ride.setDropoffTimestamp(Instant.now().plusSeconds(random.nextInt(100)));
//			ride.setRiderFirstName("Binay");
//			ride.setRiderLastName("Prakash");
//			ride.setRiderPhone("9999988888");
//
//			rides.add(ride);
//		}
//		rideRepository.saveAll(rides);
//	}

//	private void createProfile() {
//		DriverProfile profile = new DriverProfile();
//		profile.setDriverId("d519c53b-34b0-4c94-b3d6-feeafe606d81");
//		profile.setFirstName("Dave");
//		profile.setLastName("Johnson");
//		profile.setPhone("9876543210");
//		profile.setEmail("dave.johnson@gmail.com");
//		profile.setGender("Male");
//		profile.setDob(LocalDate.of(1980, Month.FEBRUARY, 29));
//		profile.setApproved(true);
//		profile.setCity("Bangalore");
//		profile.setState("Karnataka");
//		profile.setCountry("India");
//		profileRepository.save(profile);
//	}
}
