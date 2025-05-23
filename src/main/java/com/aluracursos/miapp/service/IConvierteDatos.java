package com.aluracursos.miapp.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
