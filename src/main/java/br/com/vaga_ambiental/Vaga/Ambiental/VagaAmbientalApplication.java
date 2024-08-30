package br.com.vaga_ambiental.Vaga.Ambiental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class VagaAmbientalApplication {

	public static void main(String[] args) {
		SpringApplication.run(VagaAmbientalApplication.class, args);
	}

}
