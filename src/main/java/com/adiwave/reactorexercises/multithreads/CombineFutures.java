package com.adiwave.reactorexercises.multithreads;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CombineFutures {

    public static void main(String[] args) {

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
                (a,b) -> new AlbumFolder(a,b));

        combinedFuture.thenAccept(System.out::println);

        sleepWait(2000L);
    }

    private static void sleepWait(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
