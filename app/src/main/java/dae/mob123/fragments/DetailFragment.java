package dae.mob123.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dae.mob123.R;
import dae.mob123.model.Mural;

//Author:DG
public class DetailFragment extends Fragment {

    private TextView characterTV, artistYearTV, streetAddressTV;
    private Mural muralFromList;
    private Bundle dataFromList;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

        characterTV = rootView.findViewById(R.id.tv_detail_character);
        artistYearTV = rootView.findViewById(R.id.tv_detil_artistyear);
        streetAddressTV = rootView.findViewById(R.id.tv_detail_street);

        if (dataPassed()){
            muralFromList = (Mural) dataFromList.getSerializable("mural_to_detail");
            characterTV.setText(muralFromList.getCharacter());
            artistYearTV.setText(muralFromList.getArtist() + ", " + muralFromList.getYear());

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


}
