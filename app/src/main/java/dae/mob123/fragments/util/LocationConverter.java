package dae.mob123.fragments.util;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationConverter{
//    private Application mApplication;


    public LocationConverter() {
    }

    public String latLngToAddress(Context context, LatLng myCoordinates) {
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
