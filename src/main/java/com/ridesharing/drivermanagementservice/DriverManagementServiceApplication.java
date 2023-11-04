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

	@Override
	public void run(String... args) throws Exception {
//		Earnings earnings = new Earnings();
//		earnings.setDriverId(UUID.randomUUID().toString());
//		earnings.setTotalEarnings(1000);
//		earnings.setCurrentBalance(1000);
//
//		earningsRepository.save(earnings);

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
	}
}
