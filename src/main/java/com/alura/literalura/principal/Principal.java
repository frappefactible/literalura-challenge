package com.alura.literalura.principal;

import com.alura.literalura.dto.DatosAutor;
import com.alura.literalura.dto.DatosLibro;
import com.alura.literalura.dto.DatosRespuestaApi;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Libro;
import com.alura.literalura.repositorio.AutorRepository;
import com.alura.literalura.repositorio.LibroRepository;
import com.alura.literalura.servicio.ConsumoAPI;
import com.alura.literalura.servicio.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;

        while (opcion != 0) {
            String menu = """
                    
                    ============================================
                           BIENVENIDO A LITERALURA
                    ============================================
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    ============================================
                    Elija la opción a través de su número:
                    """;
            System.out.print(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Por favor ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivosEnAnio();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando la aplicación... ¡Hasta luego!");
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String titulo = teclado.nextLine().trim();

        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        String json = consumoAPI.buscarLibroPorTitulo(titulo);
        DatosRespuestaApi respuesta = conversor.obtenerDatos(json, DatosRespuestaApi.class);

        if (respuesta.resultados() == null || respuesta.resultados().isEmpty()) {
            System.out.println("El libro no fue encontrado");
            return;
        }

        DatosLibro datosLibro = respuesta.resultados().get(0);

        Optional<Libro> libroExistente = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya está registrado en la base de datos.");
            System.out.println(libroExistente.get());
            return;
        }

        Libro libro = new Libro(datosLibro);

        if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autores().get(0);
            Optional<Autor> autorExistente = autorRepository.findByNombreIgnoreCase(datosAutor.nombre());

            Autor autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                autor = new Autor(datosAutor);
                autor = autorRepository.save(autor);
            }

            libro.setAutor(autor);
            autor.getLibros().add(libro);
        }

        libroRepository.save(libro);
        System.out.println(libro);
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }
        System.out.println("\n--- LIBROS REGISTRADOS ---");
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }
        System.out.println("\n--- AUTORES REGISTRADOS ---");
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año vivo del autor(es) que desea buscar:");
        int anio;
        try {
            anio = Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Año no válido. Por favor ingrese un número entero.");
            return;
        }

        List<Autor> autores = autorRepository.buscarAutoresVivosEnAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");
            return;
        }
        System.out.println("\n--- AUTORES VIVOS EN " + anio + " ---");
        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        String menuIdiomas = """
                Ingrese el idioma para buscar los libros:
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """;
        System.out.print(menuIdiomas);
        String idioma = teclado.nextLine().trim().toLowerCase();

        if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
            System.out.println("Idioma no válido. Opciones: es, en, fr, pt");
            return;
        }

        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'.");
            return;
        }
        System.out.println("\n--- LIBROS EN IDIOMA: " + idioma.toUpperCase() + " ---");
        libros.forEach(System.out::println);
    }
}
