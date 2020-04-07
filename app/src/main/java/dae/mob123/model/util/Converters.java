package dae.mob123.model.util;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
/*
Auth: EB
LatLng type must be converted to String in order to make queries to the Room database
*/
public class Converters {

    @TypeConverter
    public static LatLng convertStringToLatLng(String latlngString) {
        String[] coords = latlngString.split(", ");
        double lat = Double.parseDouble(coords[0]);
        double lng = Double.parseDouble(coords[1]);
        return (latlngString == null)? null : new LatLng(lat, lng);
    }

    @TypeConverter
    public static String convertLatLngToString(LatLng latLng){
        String latlngNaarString = (latLng.latitude + ", " + latLng.longitude);
        return (latLng == null)? null : latlngNaarString;
    }
}
