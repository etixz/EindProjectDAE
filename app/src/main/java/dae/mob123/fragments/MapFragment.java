package dae.mob123.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import dae.mob123.R;
import dae.mob123.fragments.util.LocationConverter;
import dae.mob123.model.Mural;
import dae.mob123.model.MuralViewModel;

/*
Author: AB
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap myMap;
    private FragmentActivity myContext;
    private final LatLng COORD_BXL = new LatLng(50.8503463, 4.3517211);
    private Marker customMarker;
    private Location currentLocation;
    private LatLng currentLocationCoordinates;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private GoogleMap.OnInfoWindowClickListener detailListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Mural mural = (Mural) marker.getTag();
            if (mural != null)
                //TODO: Bundle aanmaken, serializable te steken en doorsturen met navigatie naar Detail
                Navigation.findNavController(mapView).navigate(R.id.detail_fragment);
                Toast.makeText(getActivity(), mural.getCharacter(), Toast.LENGTH_SHORT).show();
        }
    };

    public MapFragment() {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        CameraUpdate moveToBXL = CameraUpdateFactory.newLatLngZoom(COORD_BXL, 15);
        myMap.animateCamera(moveToBXL);
        //myContext.setContentView(R.layout.custom_marker_layout);
        myMap.setOnInfoWindowClickListener(detailListener);
        setMarkerAdapter();
        drawMuralMarkers();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(myContext);
        if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(myContext, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    drawUserLocationMarker();
                    myMap.setMyLocationEnabled(true);
                }
                break;
        }
    }

    //method causes nullpointer exception
    private void drawUserLocationMarker() {
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    currentLocationCoordinates = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    LatLng userLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(userLocation)
                            .title("I am here");
                    myMap.addMarker(markerOptions);
                }
            }
        });
    }


    private void setMarkerAdapter() {
        myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View cardView = getActivity().getLayoutInflater().inflate(R.layout.mural_marker_card, null, false);
                TextView titleTV = cardView.findViewById(R.id.tv_mural_marker_card_title);
                TextView addressTV = cardView.findViewById(R.id.tv_mural_marker_card_snippet);
                titleTV.setText(marker.getTitle());
                addressTV.setText(marker.getSnippet());
                return cardView;
            }
        });
    }

    private void drawMuralMarkers() {
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


//    private void drawMarkers() {
//
//        View marker = getActivity().getLayoutInflater().inflate(R.layout.mural_marker_card, null);
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


