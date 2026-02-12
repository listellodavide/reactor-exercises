package com.adiwave.reactorexercises.multithreads;

public record PersonNote(
        int userId,
        int id,
        String title,
        boolean completed
) {
    @Override
    public String toString() {
        return String.format("PersonNote userId:%d, id:%d, title:%s", userId, id, title, completed);
    }
}
