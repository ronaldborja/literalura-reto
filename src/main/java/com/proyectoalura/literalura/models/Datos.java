package com.proyectoalura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        //Lista de libros
        @JsonAlias("results") List<DatosLibros> resultados

) {
}

//Retorna los resultados como una lista de objetos de tipo DatosLibro