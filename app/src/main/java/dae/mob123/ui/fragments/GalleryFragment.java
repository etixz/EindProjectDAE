package dae.mob123.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dae.mob123.R;
import dae.mob123.model.Mural;
import dae.mob123.model.MuralViewModel;
import dae.mob123.ui.util.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    private AppCompatActivity mContext;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_gallery, container, false);
        MuralViewModel muralViewModel = new ViewModelProvider(mContext).get(MuralViewModel.class);
        ArrayList<String> imageURLList = muralViewModel.getMuralPhotos();
        String[] imageURLs = imageURLList.toArray(new String[0]);
        ViewPager viewPager = rootView.findViewById(R.id.view_pager_gallery);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext, imageURLs);
        viewPager.setAdapter(viewPagerAdapter);
        return rootView;
    }
}
