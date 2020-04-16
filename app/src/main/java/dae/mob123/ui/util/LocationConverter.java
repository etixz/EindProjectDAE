package dae.mob123.ui.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/*
Author: DG
LocationConverter is used for reverse geocoding of coordinates to an address. Its use requires the Google Maps and Android Location libraries
*/
public class LocationConverter{

    public LocationConverter() {
    }

    /*
    The conversion method needs the Context in which the method is called and the coordinates as LatLng to be converted.
    It returns a String made up of the first line of the full Address using the app's default Locale.
    */
    public String convertCoordinatesToAddress(Context context, LatLng myCoordinates) {
        String myAddress = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
            myAddress = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myAddress;
    }
}
