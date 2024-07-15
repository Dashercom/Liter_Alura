package Liter_Alura.Liter_Alura.entities.author;

import Liter_Alura.Liter_Alura.data.DataAuthor;
import Liter_Alura.Liter_Alura.entities.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeFallecimiento;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Book> libros = new ArrayList<>();

    public Author(DataAuthor author) {
        this.nombre = author.nombre();
        this.fechaDeNacimiento = author.fechaDeNacimiento();
        this.fechaDeFallecimiento = author.fechaDeFallecimiento();
    }

    public void setLibros(List<Book> libros) {
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Book> getLibros() {
        return libros;
    }
}
