package com.adiwave.reactorexercises.multithreads;

import java.util.List;

public class AlbumFolder {

    List<Album> albumList;
    PersonNote personNote;

    public AlbumFolder(List<Album> albumList, PersonNote personNote) {
        this.albumList = albumList;
        this.personNote = personNote;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }

    public PersonNote getPersonNote() {
        return personNote;
    }

    public void setPersonNote(PersonNote personNote) {
        this.personNote = personNote;
    }

    @Override
    public String toString() {
        return "AlbumFolder{" +
                "albumList=" + albumList.stream().map(Album::toString).toList() +
                ", personNote=" + personNote.toString() +
                '}';
    }
}
