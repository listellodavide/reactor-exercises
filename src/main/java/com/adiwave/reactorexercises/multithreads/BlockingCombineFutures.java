package com.adiwave.reactorexercises.multithreads;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class BlockingCombineFutures {

    public void combineVirtualThreads() {
        // example using blocking Virtual Threads good for Http, I/O, Jdbc long running calls
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            CompletableFuture<PersonNote> userFuture =
                    CompletableFuture.supplyAsync(this::fetchUserApiBlocking, executor);

            CompletableFuture<List<Album>> albumsFuture =
                    CompletableFuture.supplyAsync(this::fetchAlbumsApiBlocking, executor);

            CompletableFuture<Void> result = userFuture
                    .thenCombine(albumsFuture,
                            (user, albums) -> combineUserAlbums(user, albums))
                    .thenAccept(System.out::println);
            result.join();; // wait completion
        }
        //sleepWait(2000L); not needed!
    }

    public Map<Integer, List<Album>> combineUserAlbums(PersonNote user, List<Album> albums) {
        return Map.of(user.userId(), albums);
    }

    public PersonNote fetchUserApiBlocking() {
        try {
            BlockingRestClient client = new BlockingRestClient();
            PersonNote personNote = client.getJson("https://jsonplaceholder.typicode.com/todos/2", PersonNote.class); //hardcode id = 2
            return personNote;
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
    }

    public List<Album> fetchAlbumsApiBlocking() {
        try {
            BlockingRestClient client = new BlockingRestClient();
            List<Album> albumsList = client.getAlbums("https://jsonplaceholder.typicode.com/albums"); // all albums
            return albumsList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void simpleExample() {
        CompletableFuture<List<Album>> serviceA = CompletableFuture.supplyAsync(
                () -> List.of(new Album(1, 1, "Album A"),
                        new Album(1, 2, "Album B"),
                        new Album(1, 3, "Album C"),
                        new Album(1, 4, "Album D"),
                        new Album(1, 5, "Album E")));

        CompletableFuture<PersonNote> serviceB = CompletableFuture.supplyAsync(
                () -> new PersonNote(1, 1, "Title A", true)
        );

        CompletableFuture<AlbumFolder> combinedFuture = serviceA.thenCombine(serviceB,
                (a, b) -> new AlbumFolder(a, b));

        combinedFuture.thenAccept(System.out::println);

        sleepWait(2000L);
    }

    public static void main(String[] args) {
        BlockingCombineFutures cb = new BlockingCombineFutures();
        cb.combineVirtualThreads();
    }

    private static void sleepWait(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
