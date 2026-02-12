package com.adiwave.reactorexercises.multithreads;

public record Album(
    int userId,
    int id,
    String title
) {
    @Override
    public String toString() {
        return String.format("Album userId:%d, id:%d, title:%s", userId, id, title);
    }
}
