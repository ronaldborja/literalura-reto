package com.proyectoalura.literalura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    //Método para obtener los datos de la API
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient(); //Objeto de tipo Client()
        HttpRequest request = HttpRequest.newBuilder() //Objeto de tipo Request() sobre la url
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null; //Respuesta
        try {
            response = client //El cliente envia la request -> En el cuerpo de la petición
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body(); //Se guarda el cuerpo de la respuesta .body()
        return json;
    }
}
