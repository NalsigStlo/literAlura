package com.alura.LiterAlura.principal;

import com.alura.LiterAlura.RepositorioLibro;
import com.alura.LiterAlura.modelos.DatosLibro;
import com.alura.LiterAlura.modelos.Libro;
import com.alura.LiterAlura.servicio.ConsumoAPI;
import com.alura.LiterAlura.servicio.ConversorTipoDatos;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI conexion = new ConsumoAPI();
    private ConversorTipoDatos conversor = new ConversorTipoDatos();
    private RepositorioLibro repositorio;
    private final String URL = "https://gutendex.com/books";

    public Principal(RepositorioLibro repositorio){
        this.repositorio = repositorio;
    }

    public void mostrarMenu() {
        String opcion = null;

        do {
            System.out.println("""
                    1 - Buscar libro por titulo
                    2 - Mostrar los libros buscados
                    3 - 
                    4 - 
                                  
                    0 - Salir
                    """);
            opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    buscarLibroPorTitulo();
                    break;
                case "2":
                    mostrarLibros();
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "0":
                    sc.close();
                    System.out.println("Gracias por usar la aplicacion, cerrando la consola.");
                    break;
                default:
                    System.out.println("La opcion escogida es invalida");
            }
        } while (!opcion.equals("0"));
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datos = obtenerDatosLibros();

        if (datos != null) {
            System.out.println(datos.autor().get(0));
            System.out.println(datos.titulo());
            Libro libro = new Libro(datos);
            repositorio.save(libro);
            System.out.println(libro);
        }
    }

    private void mostrarLibros() {
        List<Libro> libros = repositorio.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getDescargas))
                .forEach(System.out::println);
    }

    private DatosLibro obtenerDatosLibros() {
        System.out.println("Escriba el nombre del libro a buscar: ");
        String titulo = sc.nextLine();

        String consulta = conexion.datosAPI(URL + "/?search=" + titulo.toLowerCase().replace(" ", "+"));
        String libro = obtenerPrimerLibro(consulta);

        if (libro.isEmpty()){
            return null;
        }

        return conversor.convertirDatos(libro, DatosLibro.class);
    }

    private String obtenerPrimerLibro(String libro) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode nodo = null;

        try {
            nodo = mapper.readTree(libro);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ObjectNode objeto = (ObjectNode) nodo;

        if (objeto.get("results").isEmpty()){
            System.out.println("No existe el libro buscado");
            return "";
        }

        return objeto.get("results").get(0).toString();
    }
}
