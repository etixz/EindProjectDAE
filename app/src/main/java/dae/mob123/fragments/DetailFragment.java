package dae.mob123.fragments;

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
