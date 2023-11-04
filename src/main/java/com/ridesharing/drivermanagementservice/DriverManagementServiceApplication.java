package com.ridesharing.drivermanagementservice;

import com.ridesharing.drivermanagementservice.models.Earnings;
import com.ridesharing.drivermanagementservice.repositories.EarningsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class DriverManagementServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DriverManagementServiceApplication.class, args);
	}


	@Autowired
	EarningsRepository earningsRepository;
	@Override
	public void run(String... args) throws Exception {
//		Earnings earnings = new Earnings();
//		earnings.setDriverId(UUID.randomUUID().toString());
//		earnings.setTotalEarnings(1000);
//		earnings.setCurrentBalance(1000);
//
//		earningsRepository.save(earnings);
	}
}
