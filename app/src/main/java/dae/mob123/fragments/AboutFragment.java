package dae.mob123.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dae.mob123.R;

/*Author: DG*/
public class AboutFragment extends Fragment {

    private TextView titleComicBookTV, contentComicBookTV, titleStreetArtTV, contentStreetArtTV;
    private View.OnClickListener aboutComicBookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (contentComicBookTV.getVisibility() == View.GONE){
                contentComicBookTV.setVisibility(View.VISIBLE);
            } else {
                contentComicBookTV.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener aboutStreetArtListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (contentStreetArtTV.getVisibility() == View.GONE){
                contentStreetArtTV.setVisibility(View.VISIBLE);
            } else {
                contentStreetArtTV.setVisibility(View.GONE);
            }
        }
    };

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        titleComicBookTV = rootView.findViewById(R.id.tv_about_title_comicbook);
        contentComicBookTV = rootView.findViewById(R.id.tv_about_content_comicbook);
        titleStreetArtTV = rootView.findViewById((R.id.tv_about_title_streetart));
        contentStreetArtTV = rootView.findViewById(R.id.tv_about_content_streetart);
        titleComicBookTV.setOnClickListener(aboutComicBookListener);
        titleStreetArtTV.setOnClickListener(aboutStreetArtListener);

        return rootView;
    }
}
