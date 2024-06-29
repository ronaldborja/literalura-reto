package com.proyectoalura.literalura.services;

//Optional -> Dado que se pueden obtener datos nulos -> Manejar la ausencia de valores y permitir convertir a cualquier tipo

//Optional -> Flexibilidad (Cualquier tipo de datos)

public interface iConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);

    //Recibe json y clase a la que se va a convertir
}
