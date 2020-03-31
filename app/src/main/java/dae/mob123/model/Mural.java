package dae.mob123.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class Mural {

    @PrimaryKey
    private String artworkID;
    private String artist, imageURL, character, year;
    private LatLng coordinates;

    public Mural() {
    }

    @Ignore
    public Mural(String artworkID, String artist, String imageURL, String character, String year, LatLng coordinates) {
        this.artworkID = artworkID;
        this.artist = artist;
        this.imageURL = imageURL;
        this.character = character;
        this.year = year;
        this.coordinates = coordinates;
    }

    public String getArtworkID() {
        return artworkID;
    }

    public void setArtworkID(String artworkID) {
        this.artworkID = artworkID;
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
