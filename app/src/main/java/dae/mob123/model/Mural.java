package dae.mob123.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

import dae.mob123.model.util.MuralType;

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
    private String artist, imageURL, characterTitle, year;
    private MuralType muralType;
    private LatLng coordinates;

    public Mural() {
    }

    /*
    Alternate constructor used in MuralViewModel, must be ignored by Room
    */
    @Ignore
    public Mural(MuralType muralType, String muralID, String artist, String imageURL, String characterTitle, String year, LatLng coordinates) {
        this.muralType = muralType;
        this.muralID = muralID;
        this.artist = artist;
        this.imageURL = imageURL;
        this.characterTitle = characterTitle;
        this.year = year;
        this.coordinates = coordinates;
    }

    public String getMuralID() {
        return muralID;
    }

    public void setMuralID(String muralID) {
        this.muralID = muralID;
    }

    public MuralType getMuralType() {
        return muralType;
    }

    public void setMuralType(MuralType muralType) {
        this.muralType = muralType;
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

    public String getCharacterTitle() {
        return characterTitle;
    }

    public void setCharacterTitle(String characterTitle) {
        this.characterTitle = characterTitle;
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
