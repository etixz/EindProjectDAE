package dae.mob123.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import dae.mob123.R;
import dae.mob123.fragments.util.LocationConverter;
import dae.mob123.model.Mural;

//Author:DG
public class DetailFragment extends Fragment {

    private TextView characterTV, artistYearTV, streetAddressTV;
    private Mural muralFromList;
    private Bundle dataFromList;
    private ImageView imageIV;
    private AppCompatActivity appCompatActivity;
    private GoogleMap myMap;


    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

        characterTV = rootView.findViewById(R.id.tv_detail_character);
        artistYearTV = rootView.findViewById(R.id.tv_detil_artistyear);
        streetAddressTV = rootView.findViewById(R.id.tv_detail_street);
        imageIV = rootView.findViewById(R.id.iv_detail_img);

        if (dataPassed()){
            muralFromList = (Mural) dataFromList.getSerializable("mural_to_detail");
            characterTV.setText(muralFromList.getCharacterTitle());
            artistYearTV.setText(muralFromList.getArtist() + ", " + muralFromList.getYear());
            LocationConverter myConverter = new LocationConverter();
            streetAddressTV.setText(myConverter.convertCoordinatesToAddress(appCompatActivity, muralFromList.getCoordinates()));
            switch (muralFromList.getMuralType()) {
                case COMIC_BOOK: Picasso.get().load("https://opendata.bruxelles.be/api/v2/catalog/datasets/comic-book-route/files/" + muralFromList.getImageID()).into(imageIV);
                case STREET_ART: Picasso.get().load("https://opendata.brussel.be/api/v2/catalog/datasets/street-art/files/" + muralFromList.getImageID()).into(imageIV);
            };
        }
        Button showOnMap = rootView.findViewById(R.id.btn_show_place_on_map);
        showOnMap.setOnClickListener(new View.OnClickListener() {
            private Marker marker;
            @Override
            public void onClick(View view) {
                Mural mural = (Mural) marker.getTag();
                if (mural != null) {
                    Bundle data = new Bundle();
                    data.putSerializable("detail_to_map", mural);
                    mural.getCoordinates();
                    Navigation.findNavController(view).navigate(R.id.action_detail_fragment_to_map_fragment, data);
                }
            }
        });

        return rootView;
    }

    public boolean dataPassed() {
        dataFromList = getArguments();
        if (dataFromList != null) {
            if (dataFromList.containsKey("mural_to_detail")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }


}
