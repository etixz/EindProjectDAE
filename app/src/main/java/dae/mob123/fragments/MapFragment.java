package dae.mob123.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dae.mob123.R;
import dae.mob123.model.Mural;
import dae.mob123.model.MuralViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private MapView mapView;
    private GoogleMap myMap;
    private AppCompatActivity mycontext;

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
            setMarkerAdapter();
            drawMarkers();
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

        private void setMarkerAdapter() {
            myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View cardView = getActivity().getLayoutInflater().inflate(R.layout.mural_card, null, false);
                    TextView tvCharacter = cardView.findViewById(R.id.tv_card_mural_character);
                    TextView tvArtistYear = cardView.findViewById(R.id.tv_card_mural_artistyear);
                    TextView tvAddress = cardView.findViewById(R.id.tv_card_mural_address);

                    tvCharacter.setText(marker.getTitle());
                    tvArtistYear.setText(marker.getTitle());
                    tvAddress.setText(marker.getTitle());

                    return cardView;
                }
            });
        }

        private void drawMarkers() {
            myMap.addMarker(new MarkerOptions());

            MuralViewModel muralViewModel = new ViewModelProvider(mycontext).get(MuralViewModel.class);
            //TODO: wanneer lijst met Murals omgezet is naar LiveData, hier Observer aanmaken en onChanged() overschrijven
            muralViewModel.getMurals().observe(mycontext, new Observer<List<Mural>>() {
                @Override
                public void onChanged(List<Mural> murals) {
                    for (Mural mural : murals) {
                        Marker m = myMap.addMarker(new MarkerOptions()
                                .position(mural.getCoordinates()));

                        m.setTitle(mural.getCharacter());
                        m.setTitle(mural.getYear());
                        m.setTitle(String.valueOf(mural.getCoordinates()));
                        m.setTag(mural);
                }
            }});
        }


        public MapFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            mapView = rootView.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(onMapReady);


            return rootView;
        }
        @Override
        public void onResume() {
            super.onResume();
            mapView.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
            mapView.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mapView.onLowMemory();
        }

    }


