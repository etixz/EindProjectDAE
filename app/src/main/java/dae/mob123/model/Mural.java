package dae.mob123.model;

import com.google.android.gms.maps.model.LatLng;

public class Mural {

    private String artist, imageURL, character, year;
    private LatLng coordinates;

    public Mural(String author, String imageURL, String personnage, String year, LatLng coords) {
        this.artist = author;
        this.imageURL = imageURL;
        this.character = personnage;
        this.year = year;
        this.coordinates = coords;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
