package dae.mob123.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dae.mob123.R;


public class HomeFragment extends Fragment {


    private Button toListBtn, toMapBtn, toAboutBtn, toRoutesBtn;

    private View.OnClickListener toListListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigate(R.id.home_to_list);
        }
    };
    private View.OnClickListener toMapListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(v).navigate(R.id.home_to_map);
        }
    };

    private View.OnClickListener toAboutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(v).navigate(R.id.home_to_about);
        }
    };
    private View.OnClickListener toRoutesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(v).navigate(R.id.home_to_routes);
        }
    };

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         toListBtn = rootView.findViewById(R.id.btn_home_list);
         toListBtn.setOnClickListener(toListListener);

         toMapBtn = rootView.findViewById(R.id.btn_home_map);
         toMapBtn.setOnClickListener(toMapListener);

         toAboutBtn = rootView.findViewById(R.id.btn_home_about);
         toAboutBtn.setOnClickListener(toAboutListener);

         toRoutesBtn = rootView.findViewById(R.id.btn_home_routes);
         toRoutesBtn.setOnClickListener(toRoutesListener);
        return rootView;
    }
}
