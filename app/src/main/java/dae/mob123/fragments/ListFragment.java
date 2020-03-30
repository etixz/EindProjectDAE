package dae.mob123.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dae.mob123.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private FragmentActivity myContext;

    public ListFragment() {
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext = (FragmentActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);


        return rootView;
    }
}
