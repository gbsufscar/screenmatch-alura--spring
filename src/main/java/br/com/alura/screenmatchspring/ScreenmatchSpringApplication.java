package br.com.alura.screenmatchspring;

import br.com.alura.screenmatchspring.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchSpringApplication.class, args);
	}

	// Método da interface CommandLineRunner (Contrato). Acaba sendo um método main.
	@Override
	public void run(String... args) throws Exception {
		// Instanciar a Classe Principal
		Principal principal = new Principal();

		// Método para Exibir o Menu
		principal.exibeMenu();

	}
}
