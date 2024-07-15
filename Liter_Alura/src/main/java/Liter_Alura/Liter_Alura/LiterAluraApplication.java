package Liter_Alura.Liter_Alura;

import Liter_Alura.Liter_Alura.entities.author.AuthorRepository;
import Liter_Alura.Liter_Alura.entities.book.BookRepository;
import Liter_Alura.Liter_Alura.principal.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	BookRepository libroRepository;
	@Autowired
	AuthorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.mostrarMenu();
	}
}
