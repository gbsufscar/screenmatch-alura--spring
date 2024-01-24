package br.com.alura.screenmatchspring.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    /*
Classe que serve para qualquer tipo de requisição, quer seja um filme, uma imágem, um áudio etc.
 */

    // Método obter dados via requisição
    /// Método para requisição no site
    public String obterDados(String endereco){
        /// Conexão e requisição
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        /// Resposta à consulta
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /// Variável para receber a consulta no formato json
        String json = response.body();
        return json;
    }
}