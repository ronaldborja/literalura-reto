package com.proyectoalura.literalura.principal;

import com.proyectoalura.literalura.models.*;
import com.proyectoalura.literalura.repository.AutorRepository;
import com.proyectoalura.literalura.repository.LibroRepository;
import com.proyectoalura.literalura.services.ConsumoAPI;
import com.proyectoalura.literalura.services.ConvierteDatos;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    //Atributo constante y estatico
    public static final String URL_BASE="https://gutendex.com/books/";

    //Objetos para consumir API:
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    //Objeto para acceder a la base de datos + Constructor
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;

    }

    //Objeto para recibir info por consola
    private Scanner teclado = new Scanner(System.in);

    public void mostrarMenu() {
        boolean ejecutar = true;

        while (ejecutar) {
            System.out.println("-------------------");
            System.out.println("Seleccione una opción en nuestro catalogo: ");
            System.out.println("1- Buscar libro por titulo");
            System.out.println("2- Listar libros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos en un determinado año");
            System.out.println("5- Listar libros por idioma");
            System.out.println("0- Salir...");

            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;

                case 2:
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    listaAutoresVivos();
                    break;

                case 5:
                    librosPorIdioma();
                    break;

                case 0:
                    ejecutar = false;
                    System.out.println("Gracias por haber usado nuestro catalogo... Te esperamos pronto");

                default:
                    System.out.println("Opción invalida, intentalo otra vez!");
                    break;
            }

        }
    }

    private void buscarLibroPorTitulo() {
        //Logica para buscar por titulo y guardar
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        teclado.nextLine();
        var tituloLibro = teclado.nextLine();

        //Obtenemos el json con la URL base y el titulo del libro:
        var json = consumoAPI.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ", "+"));

        //A continuación, los datos se convierten al tipo Datos:
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class); //Lista de tipo DatosLibros.

        //Luego, se realiza la busqueda por titulo:
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream() //Obtengo lso resultados
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst(); //Comparamos el titulo pasado con los resultados del json -> Obtenemos el 1st resultado

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado: ");

            //Recuperamos los resultados
            String titulo = libroBuscado.get().titulo();

            //Datos autor
            String nombreAutor = libroBuscado.get().autor().stream().findFirst().
                    map(DatosAutor::nombre).
                    orElse("Autor desconocido");

            String fechaNacimiento = libroBuscado.get().autor().stream().findFirst().
                    map(DatosAutor::fechaDeNacimiento).
                    orElse("Sin fecha");

            String fechaFacimiento = libroBuscado.get().autor().stream().findFirst().
                    map(DatosAutor::fechaDeFallecimiento).
                    orElse("Sin fecha");

            List<String> idiomas = libroBuscado.get().idiomas();
            String idioma = idiomas.get(0);

            Autor autor = new Autor(nombreAutor, fechaNacimiento, fechaFacimiento);
            double numeroDescargas = libroBuscado.get().numeroDeDescargas();

            Libro nuevoLibro = new Libro(titulo, autor, idioma, numeroDescargas);
            nuevoLibro.toString();

            libroRepository.save(nuevoLibro);
            autorRepository.save(autor);
        }

        else {
            System.out.println("No se encontró el libro!");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = new ArrayList<>();
        libros = libroRepository.findAll();

        System.out.println("Libros registrados en la base de datos: ");

        libros.stream().
                forEach(l -> System.out.println(
                        "Titulo: " + l.getTitulo() +
                        "\nIdiomas: " + l.getIdioma() +
                        "\nDescargas: " + l.getNumeroDescargas() +
                        "\nAutor: " + l.getAutor().getNombreAutor()));
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = new ArrayList<>();
        autores = autorRepository.findAll();

        System.out.println("Libros registrados en la base de datos");

        autores.stream().
                forEach(a -> System.out.println(
                        "Nombre autor: " + a.getNombreAutor() +
                                "\nFecha de nacimiento: " + a.getAñoNacimiento()+
                                "\nFecha de fallecimiento: " + a.getAñoFallecimiento()));
    }

    private void listaAutoresVivos() {

        System.out.println("Fecha a buscar: ");
        teclado.nextLine();
        String fecha = teclado.nextLine();

        List<Autor> autores = new ArrayList<>();
        autores = autorRepository.buscarAutoresVivosPorAño(fecha);

        System.out.println("Autores vivos desde "+ fecha + ": ");
        System.out.println("");

        autores.stream().
                forEach(a -> System.out.println(
                        "Nombre autor: " + a.getNombreAutor() +
                                "\nFecha de nacimiento: " + a.getAñoNacimiento()+
                                "\nFecha de fallecimiento: " + a.getAñoFallecimiento()));
    }

    private void librosPorIdioma() {
        System.out.println("Indique el idioma a buscar: ");
        teclado.nextLine();
        String idioma = teclado.nextLine();
        List<Libro> listaLibros = libroRepository.findLibrosByIdioma(idioma);
    }


}
