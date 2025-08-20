package com.alura.LiterAlura.servicio;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SuppressWarnings("SpellCheckingInspection")
public class ConsumoAPI {
    public String datosAPI(String url){
        HttpResponse<String> respuesta;

        try (HttpClient cliente = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            respuesta = null;
            try {
                respuesta = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return respuesta.body();
    }
}
