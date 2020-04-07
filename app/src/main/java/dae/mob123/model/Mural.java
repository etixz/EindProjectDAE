package dae.mob123.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
/*
Author: EB
The Mural class represents a mural along the comic book route in Brussels.
The class must implement the Serializable interface so instances may be put in a Bundle and transferred from one fragment or activity to another.
*/
@Entity
public class Mural implements Serializable {

    @PrimaryKey
    @NonNull
    private String muralID;
    private String artist, imageID, character, year;
    private LatLng coordinates;

    public Mural() {
    }

    /*
    This alternate constructor is used in MuralViewModel must be ignored by Room
    */
    @Ignore
    public Mural(String muralID, String artist, String imageID, String character, String year, LatLng coordinates) {
        this.muralID = muralID;
        this.artist = artist;
        this.imageID = imageID;
        this.character = character;
        this.year = year;
        this.coordinates = coordinates;
    }

    public String getMuralID() {
        return muralID;
    }

    public void setMuralID(String muralID) {
        this.muralID = muralID;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
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
