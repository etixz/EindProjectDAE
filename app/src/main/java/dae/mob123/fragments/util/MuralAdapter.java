package dae.mob123.fragments.util;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dae.mob123.R;
import dae.mob123.model.Mural;

public class MuralAdapter extends RecyclerView.Adapter<MuralAdapter.MuralViewHolder> {

    //inner class
    class MuralViewHolder extends RecyclerView.ViewHolder{
        //verwijzingen naar componenten in cardview
        private TextView artistYearMuralTV;
        private TextView characterMuralTV;
        private TextView addressMuralTV;
        private final View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Bundle dataToSend = new Bundle();
                dataToSend.putSerializable("mural_to_detail", items.get(position));
                Navigation.findNavController(view).navigate(R.id.list_to_detail, dataToSend);
            }
        };
        public MuralViewHolder(@NonNull View cardView) {
            super(cardView);
            artistYearMuralTV = cardView.findViewById(R.id.tv_card_mural_artistyear);
            characterMuralTV = cardView.findViewById(R.id.tv_card_mural_character);
            addressMuralTV = cardView.findViewById(R.id.tv_card_mural_address);
            cardView.setOnClickListener(detailListener);
        }
    }

    private Application mApplication;
    private List<Mural> items;
    private LocationConverter mConverter;

    public MuralAdapter(Application mApplication) {
        this.mApplication = mApplication;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public MuralViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View muralCard = layoutInflater.inflate(R.layout.mural_card, parent, false);
        return new MuralViewHolder(muralCard);
    }

    @Override
    public void onBindViewHolder(@NonNull MuralViewHolder cardHolder, int position) {
        Mural currentMural = items.get(position);
        cardHolder.characterMuralTV.setText(currentMural.getCharacter().toUpperCase());
        cardHolder.artistYearMuralTV.setText("By " + currentMural.getArtist() + ", " + currentMural.getYear());
        mConverter = new LocationConverter();
        cardHolder.addressMuralTV.setText(mConverter.convertCoordinatesToAddress(mApplication, currentMural.getCoordinates()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<Mural> murals) {
        items.clear();
        items.addAll(murals);
    }
}
