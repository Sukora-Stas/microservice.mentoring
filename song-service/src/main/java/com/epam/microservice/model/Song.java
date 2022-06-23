package com.epam.microservice.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String year;

    @Column
    private String album;

    @Column
    private String artist;

    @Column
    private String length;

    @NotNull
    @Column(name = "resource_id")
    private Long resourceId;

    public Song() {
    }

    public Song(String name, String year, String album, String artist, String length, Long resourceId) {
        this.name = name;
        this.year = year;
        this.album = album;
        this.artist = artist;
        this.length = length;
        this.resourceId = resourceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", length='" + length + '\'' +
                ", resourceId=" + resourceId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id) && Objects.equals(name, song.name) && Objects.equals(year, song.year) && Objects.equals(album, song.album) && Objects.equals(artist, song.artist) && Objects.equals(length, song.length) && Objects.equals(resourceId, song.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, album, artist, length, resourceId);
    }
}
