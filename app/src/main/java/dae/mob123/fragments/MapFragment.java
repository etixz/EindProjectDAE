package dae.mob123.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import dae.mob123.R;
import dae.mob123.model.Mural;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private MapView mapView;
    private GoogleMap myMap;
    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //field maken om de googlemap instantie in andere methoden te krijgen
            myMap = googleMap;
            //kaart klaar
            //kaart centreren op een coördinaat.
            LatLng coördBrussel = new LatLng(50.858712, 4.347446);

            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coördBrussel, 16);

            googleMap.animateCamera(moveToBrussel);
            //googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);

            myMap.setOnInfoWindowClickListener(infoWindowClickListener);
        }
    };
    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Mural mural = (Mural) marker.getTag();
            if (mural != null)
                Toast.makeText(getActivity(), mural.getCharacter(), Toast.LENGTH_SHORT).show();
        }
    };




    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReady);


        return rootView;
    }
}


