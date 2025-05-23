package com.aluracursos.miapp.modelos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Libro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Autores> autores,
        @JsonAlias("summaries") List<String> descripcion,
        @JsonAlias("subjects") List<String> temas
        ) {
}
