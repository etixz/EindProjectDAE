package dae.mob123.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dae.mob123.R;
import dae.mob123.fragments.util.MuralAdapter;
import dae.mob123.model.Mural;
import dae.mob123.model.MuralViewModel;


public class ListFragment extends Fragment {

    private AppCompatActivity mContext;
    private MuralAdapter muralAdapter;
    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            muralAdapter.getFilter().filter(newText);
            return false;
        }
    };


    public ListFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = (AppCompatActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        RecyclerView muralListRV = rootView.findViewById(R.id.rv_mural_list);
        muralAdapter = new MuralAdapter(mContext.getApplication());
        muralListRV.setAdapter(muralAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        muralListRV.setLayoutManager(layoutManager);
        MuralViewModel muralViewModel = new ViewModelProvider(mContext).get(MuralViewModel.class);

        muralViewModel.getMurals().observe(mContext, new Observer<List<Mural>>() {
            @Override
            public void onChanged(List<Mural> murals) {
                muralAdapter.addItems(murals);
                muralAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        searchView.setOnQueryTextListener(searchListener);

        super.onCreateOptionsMenu(menu, inflater);
    }
}
