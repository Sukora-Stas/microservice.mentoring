package com.epam.microservice.model;

import java.util.Objects;

public class SongMetadata {
    private String album;
    private String artist;
    private String length;
    private String name;
    private Long resourceId;
    private String year;

    public SongMetadata() {
    }

    public SongMetadata(String album, String artist, String length, String name, Long resourceId, String year) {
        this.album = album;
        this.artist = artist;
        this.length = length;
        this.name = name;
        this.resourceId = resourceId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "SongMetadata{" +
                "album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", length='" + length + '\'' +
                ", name='" + name + '\'' +
                ", resourceId=" + resourceId +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongMetadata that = (SongMetadata) o;
        return Objects.equals(album, that.album)
                && Objects.equals(artist, that.artist)
                && Objects.equals(length, that.length)
                && Objects.equals(name, that.name)
                && Objects.equals(resourceId, that.resourceId)
                && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(album, artist, length, name, resourceId, year);
    }
}
