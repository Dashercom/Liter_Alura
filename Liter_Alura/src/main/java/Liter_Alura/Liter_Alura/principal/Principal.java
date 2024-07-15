package Liter_Alura.Liter_Alura.principal;

import Liter_Alura.Liter_Alura.conversor.ConsumeAPI;
import Liter_Alura.Liter_Alura.conversor.Conversor;
import Liter_Alura.Liter_Alura.data.Data;
import Liter_Alura.Liter_Alura.data.DataBook;
import Liter_Alura.Liter_Alura.entities.author.Author;
import Liter_Alura.Liter_Alura.entities.author.AuthorRepository;
import Liter_Alura.Liter_Alura.entities.book.Book;
import Liter_Alura.Liter_Alura.entities.book.BookRepository;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL = "https://gutendex.com/books/";
    private ConsumeAPI consumoApi = new ConsumeAPI();
    private Conversor conversor = new Conversor();
    private Integer opcion = 10;
    private Scanner scanner = new Scanner(System.in);
    private BookRepository libroRepository;
    private AuthorRepository autorRepository;

    public Principal(BookRepository libroRepository, AuthorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    private void leerLibro(Book libro) {
        System.out.printf("""
                        ----- LIBRO -----
                        Titulo: %s
                        Autor: %s
                        Idioma: %s
                        Numero de descargas: %d
                        -------------------- \n
                        """,
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getIdioma(),
                libro.getNumeroDeDescargas());
    }
    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        scanner.nextLine();
        String nombreLibro = scanner.nextLine();
        String json = consumoApi.obtenerLibros(URL + "?search=" + nombreLibro.replace(" ", "+"));
        List<DataBook> libros = conversor.obtenerDatos(json, Data.class).resultados();
        Optional<DataBook> libroOptional = libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();
        if (libroOptional.isPresent()) {
            var libro = new Book(libroOptional.get());
            libroRepository.save(libro);
            leerLibro(libro);
        }
        System.out.println("El libro no ha podido ser encontrado");
    }
    private void listarLibros() {
        List<Book> libros = libroRepository.findAll();
        libros.stream()
                .forEach(this::leerLibro);
    }


    private void leerAutor(Author autor) {
        System.out.printf("""
                        Autor: %s
                        Fecha de nacimiento: %s
                        Fecha de fallecimiento: %s
                        """,
                autor.getNombre(),
                autor.getFechaDeNacimiento(),
                autor.getFechaDeFallecimiento());

        var libros = autor.getLibros().stream()
                .map(a -> a.getTitulo())
                .collect(Collectors.toList());
        System.out.println("Libros: " + libros + "\n");
    }

    private void listarAutores() {
        List<Author> autores = autorRepository.findAll();
        autores.stream()
                .forEach(this::leerAutor);
    }
    private void listarAutoresPorAño() {
        System.out.println("Ingresa el año vivo de autor(es) que desea buscar");
        Integer año = scanner.nextInt();
        List<Author> autores = autorRepository.findByFechaDeFallecimientoGreaterThan(año);
        autores.stream()
                .forEach(this::leerAutor);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
        String idioma = scanner.next();
        List<Book> libros = libroRepository.findByIdioma(idioma);
        libros.stream()
                .forEach(this::leerLibro);
    }

    private void generarEstadisticasDelNumeroDeDescargas() {
        var libros = libroRepository.findAll();
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        for (Book libro : libros) doubleSummaryStatistics.accept(libro.getNumeroDeDescargas());
        System.out.println("Conteo del numero de descargas - " + doubleSummaryStatistics.getCount());
        System.out.println("Numero de descargas minimo - " + doubleSummaryStatistics.getMin());
        System.out.println("Numero de descargas maximo - " + doubleSummaryStatistics.getMax());
        System.out.println("Suma del numero de descargas - " + doubleSummaryStatistics.getSum());
        System.out.println("Promedio del numero de descargas - " + doubleSummaryStatistics.getAverage() + "\n");
    }

    private void listarTop10Libros() {
        libroRepository.buscarTop10Libros().stream()
                .forEach(this::leerLibro);
    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingresa un nombre para buscar al autor");
        scanner.nextLine();
        var nombre = scanner.nextLine();
        autorRepository.findByNombre(nombre).stream()
                .forEach(this::leerAutor);
    }

    public void mostrarMenu() {
        while (opcion != 9) {
            System.out.println("""
                    Elija la opcion a traves de su numero:
                    1- buscar libro por titulo
                    2- listar libros registrados
                    3- listar autores registrados
                    4- listar autores vivos en un determinado año
                    5- listar libros por idioma
                    6- generar estadisticas del numero de descargas
                    7- listar el top 10 de libros mas descargados
                    8- buscar autor por nombre
                    9- salir
                    """);
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresPorAño();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    generarEstadisticasDelNumeroDeDescargas();
                    break;
                case 7:
                    listarTop10Libros();
                    break;
                case 8:
                    buscarAutorPorNombre();
                    break;
            }
        }
    }
}
