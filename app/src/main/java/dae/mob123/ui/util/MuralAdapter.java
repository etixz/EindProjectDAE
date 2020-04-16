package dae.mob123.ui.util;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import dae.mob123.R;
import dae.mob123.model.Mural;

import static java.text.Normalizer.Form.NFD;

public class MuralAdapter extends RecyclerView.Adapter<MuralAdapter.MuralViewHolder> implements Filterable {

    //inner class
    class MuralViewHolder extends RecyclerView.ViewHolder{
        private TextView artistYearMuralTV;
        private TextView characterTitleMuralTV;
        private TextView addressMuralTV;
        private final View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Bundle dataToSend = new Bundle();
                dataToSend.putSerializable("mural_to_detail", allItems.get(position));
                Navigation.findNavController(view).navigate(R.id.list_to_detail, dataToSend);
            }
        };
        public MuralViewHolder(@NonNull View cardView) {
            super(cardView);
            artistYearMuralTV = cardView.findViewById(R.id.tv_card_mural_artistyear);
            characterTitleMuralTV = cardView.findViewById(R.id.tv_card_mural_character);
            addressMuralTV = cardView.findViewById(R.id.tv_card_mural_address);
            cardView.setOnClickListener(detailListener);
        }
    }

    private Application mApplication;
    private List<Mural> allItems, filteredItems;
    private LocationConverter mConverter;

    public MuralAdapter(Application mApplication) {
        this.mApplication = mApplication;
        allItems = new ArrayList<>();
        filteredItems = new ArrayList<>();
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
        Mural currentMural = filteredItems.get(position);
        cardHolder.characterTitleMuralTV.setText(currentMural.getCharacterTitle().toUpperCase());
        cardHolder.artistYearMuralTV.setText(currentMural.getArtist() + ", " + currentMural.getYear());
        mConverter = new LocationConverter();
        cardHolder.addressMuralTV.setText(mConverter.convertCoordinatesToAddress(mApplication, currentMural.getCoordinates()));
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public void addItems(List<Mural> murals) {
        filteredItems.clear();
        filteredItems.addAll(murals);
        allItems.clear();
        allItems.addAll(murals);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                /*user input and matching data must be case insensitive and use Normalizer to ignore accented characters*/
                String input = Normalizer.normalize(constraint, NFD).toLowerCase();
                if (input.isEmpty()) {
                    filteredItems = allItems;
                } else {
                    filteredItems = allItems;
                    ArrayList<Mural> tempList = new ArrayList<>();
                    for (Mural element : filteredItems) {
                        if (Normalizer.normalize(element.getCharacterTitle().toLowerCase(), NFD).contains(input)
                                || Normalizer.normalize(element.getArtist().toLowerCase(), NFD).contains(input))
                        {
                            tempList.add(element);
                        }
                    } filteredItems = tempList;
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }

}
