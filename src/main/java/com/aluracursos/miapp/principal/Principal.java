package com.aluracursos.miapp.principal;

import com.aluracursos.miapp.modelos.Biblioteca;
import com.aluracursos.miapp.modelos.Libro;
import com.aluracursos.miapp.service.ConsumoAPI;
import com.aluracursos.miapp.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class Principal {

    public void mostrarMenu(){
        // Trae los datos json de la API
        ConsumoAPI consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
//        System.out.println(json);

        // Formatea el json para visualizarlo mas ordenado
//        ObjectMapper mapper = new ObjectMapper();
//        try{
//            Object jsonObj = mapper.readValue(json, Object.class);
//            String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
//            System.out.println(jsonPretty);
//        }catch(JsonProcessingException e){
//            throw new RuntimeException(e);
//        }

        // Convierte los datos json de la API a objetos Java
        ConvierteDatos conversorDeDatos = new ConvierteDatos();
        var datos = conversorDeDatos.obtenerDatos(json, Biblioteca.class);
//        System.out.println(datos);

        // Variable que guarda la lista del Objeto datos (Biblioteca.java)
        List<Libro> libros = datos.biblioteca();

        // Se trabajan los datos de la lista con stream() para mostrar solo 5 resultados
        libros.stream()
                .limit(5)
                .forEach(System.out::println);

        // Top 5 libros mas descargados
        System.out.println("Top 3 libros mas descargados");
        libros.stream()
                // Ordena los libros por orden de cantidad de descargas de mayor a menor
                .sorted(Comparator.comparing(Libro::descargas).reversed())
                // Limita la cantidad de resultados a 3
                .limit(3)
                // Imprime el titulo de los libros en mayuscula
                .forEach(libro -> System.out.println(libro.titulo().toUpperCase()));

        // Buscar libro por nombre
        Scanner teclado = new Scanner(System.in);
        System.out.println("Escribe el nombre del libro que deseas buscar:");

        // Toma el input del usuario
        String buscarTitulo = teclado.nextLine();

        // Obtiene los datos de la busqueda en la API
        json = consumoAPI.obtenerDatos("https://gutendex.com/books/?search=" + buscarTitulo.replace(" ", "+"));

        // Convierte los datos de la API a objeto
        var libroEncontrado = conversorDeDatos.obtenerDatos(json, Biblioteca.class);

        // Muestra la cantidad de resultados de la busqueda
        System.out.println(libroEncontrado);
        long resultados = libroEncontrado.biblioteca().stream()
                .count();
        System.out.println("Total de resultados: " + resultados);

        // Genera estadisticas de la busqueda realizada
        IntSummaryStatistics estadisticas = libroEncontrado.biblioteca().stream()
                .mapToInt(Libro::descargas)
                .summaryStatistics();

        System.out.println("Descargas promedio: " + estadisticas.getAverage());
        System.out.println("Descargas minimas: " + estadisticas.getMin());
        System.out.println("Descargas maximas: " + estadisticas.getMax());
        System.out.println("Total de libros encontrados: " + estadisticas.getCount());

    }
}
