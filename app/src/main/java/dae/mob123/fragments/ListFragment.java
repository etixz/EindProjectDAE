package dae.mob123.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dae.mob123.R;
import dae.mob123.fragments.util.MuralAdapter;
import dae.mob123.model.Mural;
import dae.mob123.model.MuralViewModel;


public class ListFragment extends Fragment {

    private FragmentActivity mContext;
    private MuralAdapter muralAdapter;


    public ListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (FragmentActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView muralListRV = rootView.findViewById(R.id.rv_mural_list);
        muralAdapter = new MuralAdapter(mContext.getApplication());
        muralListRV.setAdapter(muralAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        muralListRV.setLayoutManager(layoutManager);
        MuralViewModel muralViewModel = new ViewModelProvider(mContext).get(MuralViewModel.class);
        //TODO: wanneer lijst met Murals omgezet is naar LiveData, hier Observer aanmaken en onChanged() overschrijven

        return rootView;
    }
}
