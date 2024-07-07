package com.challengeLiterAlura.chellengeLiterAlura;

import com.challengeLiterAlura.chellengeLiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.challengeLiterAlura.chellengeLiterAlura.principal.Principal;

@SpringBootApplication
public class ChellengeLiterAluraApplication implements CommandLineRunner {


	@Autowired
	private LibroRepository repository;

	public static void main(String[] args) {

		SpringApplication.run(ChellengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraElMenu();

	}
}
