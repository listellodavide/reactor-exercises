package com.adiwave.reactorexercises.multithreads;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class BlockingRestClient {

    private final HttpClient client;

    public BlockingRestClient() {
        this.client = HttpClient.newHttpClient();
    }

    public String getJson(String url) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString()); // BLOCKING

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode());
        }

        return response.body();
    }
    public <T> T getJson(String url, Class<T> clazz) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), clazz);
    }

    public List<Album> getAlbums(String url) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString()); // BLOCKING

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP error: " + response.statusCode());
        }
        ObjectMapper mapper = new ObjectMapper();
        Album[] albums = mapper.readValue(response.body(), Album[].class);

        return Arrays.asList(albums);
    }
}
