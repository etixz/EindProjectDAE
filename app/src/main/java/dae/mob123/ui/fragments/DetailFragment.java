package dae.mob123.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import dae.mob123.R;
import dae.mob123.ui.util.LocationConverter;
import dae.mob123.model.Mural;

import static dae.mob123.model.util.MuralType.COMIC_BOOK;

//Author:DG
public class DetailFragment extends Fragment {

    private TextView characterTV, artistYearTV, streetAddressTV;
    private Mural muralFromList;
    private Bundle dataFromList;
    private ImageView imageIV;
    private AppCompatActivity appCompatActivity;
    private MaterialButton showOnMap;
//    private MaterialButton showRoute;

    public DetailFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appCompatActivity = (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        characterTV = rootView.findViewById(R.id.tv_detail_character);
        artistYearTV = rootView.findViewById(R.id.tv_detil_artistyear);
        streetAddressTV = rootView.findViewById(R.id.tv_detail_street);
        imageIV = rootView.findViewById(R.id.iv_detail_img);
        showOnMap = rootView.findViewById(R.id.btn_show_place_on_map);
//        showRoute = rootView.findViewById(R.id.btn_show_route_on_map);
        if (dataPassed()) {
            muralFromList = (Mural) dataFromList.getSerializable("mural_to_detail");
            characterTV.setText(muralFromList.getCharacterTitle());
            artistYearTV.setText(muralFromList.getArtist() + ", " + muralFromList.getYear());
            LocationConverter myConverter = new LocationConverter();
            streetAddressTV.setText(myConverter.convertCoordinatesToAddress(appCompatActivity, muralFromList.getCoordinates()));

            if (muralFromList.getMuralType() == COMIC_BOOK) {
                Picasso.get().load("https://opendata.bruxelles.be/api/v2/catalog/datasets/comic-book-route/files/" + muralFromList.getImageURL())
                            .placeholder(R.drawable.ic_image_placeholder)
                            .into(imageIV);
            } else {
                Picasso.get().load("https://opendata.brussel.be/api/v2/catalog/datasets/street-art/files/" + muralFromList.getImageURL())
                            .placeholder(R.drawable.ic_image_placeholder)
                            .into(imageIV);
            }
        }

        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putSerializable("mural_to_map_zoom", muralFromList);
                Navigation.findNavController(view).navigate(R.id.detail_to_map, data);
            }
        });
//        showRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle data = new Bundle();
//                data.putSerializable("mural_to_map_route", muralFromList);
//                Navigation.findNavController(view).navigate(R.id.detail_to_map, data);
//            }
//        });
        return rootView;
    }

    public boolean dataPassed() {
        dataFromList = getArguments();
        if (dataFromList != null) {
            if (dataFromList.containsKey("mural_to_detail")) {
                return true;
            }
        }
        return false;
    }


}

