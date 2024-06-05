package com.curso.reservaveiculosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReservaveiculosapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaveiculosapiApplication.class, args);
	}

}
