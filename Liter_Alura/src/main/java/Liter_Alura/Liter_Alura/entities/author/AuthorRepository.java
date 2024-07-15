package Liter_Alura.Liter_Alura.entities.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByFechaDeFallecimientoGreaterThan(Integer año);

    List<Author> findByNombre(String nombre);
}
