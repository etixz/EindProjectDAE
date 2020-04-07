package dae.mob123.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import dae.mob123.R;
import dae.mob123.fragments.util.LocationConverter;
import dae.mob123.model.Mural;
import dae.mob123.model.MuralViewModel;

/*
Author: AB
 */
public class MapFragment extends Fragment {

    private MapView mapView;
    private GoogleMap myMap;
    private FragmentActivity myContext;
    private final LatLng COORD_BXL = new LatLng(50.8503463, 4.3517211);
//    private Marker customMarker;

    private GoogleMap.OnInfoWindowClickListener detailListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Mural mural = (Mural) marker.getTag();
            if (mural != null)
                //TODO: Bundle aanmaken, serializable te steken en doorsturen met navigatie naar Detail
                Toast.makeText(getActivity(), mural.getCharacter(), Toast.LENGTH_SHORT).show();
        }
    };


    public MapFragment() {
    }

    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //field maken om de googlemap instantie in andere methoden te krijgen
            myMap = googleMap;

            //kaart klaarmaken en centreren op een coördinaat.
            CameraUpdate moveToBXL = CameraUpdateFactory.newLatLngZoom(COORD_BXL, 15);
            myMap.animateCamera(moveToBXL);

//            myContext.setContentView(R.layout.custom_marker_layout);

            myMap.setOnInfoWindowClickListener(detailListener);
            setMarkerAdapter();
            drawMarkers();
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
                View cardView = getActivity().getLayoutInflater().inflate(R.layout.mural_marker, null, false);
                TextView titleTV = cardView.findViewById(R.id.tv_mural_marker_card_title);
                TextView addressTV = cardView.findViewById(R.id.tv_mural_marker_card_snippet);
                titleTV.setText(marker.getTitle());
                addressTV.setText(marker.getSnippet());
                return cardView;
            }
        });
    }

    private void drawMarkers() {

        MuralViewModel muralViewModel = new ViewModelProvider(myContext).get(MuralViewModel.class);
        muralViewModel.getMurals().observe(myContext, new Observer<List<Mural>>() {
            @Override
            public void onChanged(List<Mural> murals) {
                for (Mural mural : murals) {
                    Log.e("DEBUG", mural.toString());
                    Marker marker = myMap.addMarker(new MarkerOptions()
                            .position(mural.getCoordinates())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker)));
                    marker.setTitle(mural.getCharacter());
                    LocationConverter myConverter = new LocationConverter();
                    marker.setSnippet(myConverter.convertCoordinatesToAddress(myContext, mural.getCoordinates()));
                    marker.setTag(mural);
                }
            }
        });
    }

//    private void drawMarkers() {
//
//        View marker = getActivity().getLayoutInflater().inflate(R.layout.mural_marker, null);
//        customMarker = myMap.addMarker(new MarkerOptions()
//                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(myContext, marker))));
//
//        final View mapView = getChildFragmentManager().findFragmentById(R.id.map_fragment).getView();
//        if (mapView.getViewTreeObserver().isAlive()) {
//            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @SuppressLint("NewApi")
//                // nakijken welke build version dat wij aan gebruiken zijn.
//                @Override
//                public void onGlobalLayout() {
//                    LatLngBounds bounds = new LatLngBounds.Builder().include(COORD_BXL).build();
//                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    } else {
//                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    }
//                    myMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
//                }
//            });
//        }
//
//        MuralViewModel muralViewModel = new ViewModelProvider(myContext).get(MuralViewModel.class);
//        muralViewModel.getMurals().observe(myContext, new Observer<List<Mural>>() {
//            @Override
//            public void onChanged(List<Mural> murals) {
//                for (Mural mural : murals) {
//                    Marker m = myMap.addMarker(new MarkerOptions()
//                            .position(mural.getCoordinates()));
//                    m.setTitle(mural.getCharacter());
//                    m.setSnippet(String.valueOf(mural.getCoordinates()));
//                    m.setTag(mural);
//                }
//            }
//        });
//    }


    // omzetten van een view in een bitmap
   // public static Bitmap createDrawableFromView(Context mycontext, View mapView) {
     //   DisplayMetrics displayMetrics = new DisplayMetrics();
       // ((Activity) mycontext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //mapView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
        //mapView.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        //mapView.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        //mapView.buildDrawingCache();
        //Bitmap bitmap = Bitmap.createBitmap(mapView.getMeasuredWidth(), mapView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        //Canvas canvas = new Canvas(bitmap);
        //mapView.draw(canvas);

        //return bitmap;
    //}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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



