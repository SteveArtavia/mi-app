package com.aluracursos.miapp.principal;

import com.aluracursos.miapp.modelos.Biblioteca;
import com.aluracursos.miapp.modelos.Libro;
import com.aluracursos.miapp.service.ConsumoAPI;
import com.aluracursos.miapp.service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Principal {

    public void mostrarMenu(){
        // Trae los datos json de la API
        ConsumoAPI consumoAPI = new ConsumoAPI();
        var json = consumoAPI.obtenerDatos("https://gutendex.com/books/");
        System.out.println(json);

        // Convierte los datos json de la API a objetos Java
        ConvierteDatos conversorDeDatos = new ConvierteDatos();
        var datos = conversorDeDatos.obtenerDatos(json, Biblioteca.class);
        System.out.println(datos);

        // Formatea el json para visualizarlo mas ordenado
        ObjectMapper mapper = new ObjectMapper();
        try{
            Object jsonObj = mapper.readValue(json, Object.class);
            String jsonPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObj);
            System.out.println(jsonPretty);
        }catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
