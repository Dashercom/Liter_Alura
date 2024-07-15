package Liter_Alura.Liter_Alura.entities.book;

import Liter_Alura.Liter_Alura.data.DataAuthor;
import Liter_Alura.Liter_Alura.data.DataBook;
import Liter_Alura.Liter_Alura.entities.author.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Entity
@Table(name = "libros")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author autor;
    private String idioma;
    @Column(name = "numero_de_descargas")
    private Integer numeroDeDescargas;

    public Book(DataBook libro) {
        this.titulo = libro.titulo();
        Optional<DataAuthor> autor = libro.autores().stream().findFirst();
        if (autor.isPresent()) {
            this.autor = new Author(autor.get());
        } else {
            System.out.println("No se ha podido encontrar el autor");
        }
        this.idioma = libro.idiomas().get(0);
        this.numeroDeDescargas = libro.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Author getAutor() {
        return autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public void setAutor(Author autor) {
        this.autor = autor;
    }

}
