package com.adiwave.reactorexercises.multithreads;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class NonBlockingFetchMultiSource {
    WebClient client = WebClient.create("https://jsonplaceholder.typicode.com/"); //async non-blocking rest client

    public Mono<PersonNote> fetchUserDetails(Integer id) {
        return client.get()
                .uri("todos/{id}", id).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PersonNote.class);
    }

    public Flux<Album> fetchUserAlbums() {
        return client.get()
                .uri("albums").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Album.class);
    }


    public CompletableFuture<Void> combineRequests() {
        Integer id = 1;
        return fetchUserDetails(id).toFuture()
                .thenCombine(fetchUserAlbums().collectList().toFuture(), (person, albums) -> {
                    Map<Integer, List<Album>> userData = new HashMap<>();
                    userData.put(person.userId(), albums);
                    return userData;
                })
                .thenAccept(this::printData);
    }

    private void printData(Map<Integer, List<Album>> userData) {
        userData.forEach((userId, albums) -> {
            String albumList = albums.stream()
                    .map(Album::toString)
                    .collect(Collectors.joining(", "));
            System.out.println("User ID: " + userId + " -> " + albumList);
        });
    }

    public static void main(String[] args) {
        NonBlockingFetchMultiSource nonBlockingFetchMultiSource = new NonBlockingFetchMultiSource();
        nonBlockingFetchMultiSource.combineRequests().join();

    }
}
