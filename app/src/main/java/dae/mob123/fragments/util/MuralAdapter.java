package dae.mob123.fragments.util;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import dae.mob123.R;

public class MuralAdapter extends RecyclerView.ViewHolder<MuralAdapter.MuralViewHolder> {

    //inner class
    class MuralViewHolder extends RecyclerView.ViewHolder{
        //verwijzingen naar componenten in cardview
        private CardView muralCV;
        private TextView artistYearMuralTV;
        private TextView characterMuralTV;
        private final View.OnClickListener detailListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: bundle data and put serializable
            }
        };
        public MuralViewHolder(@NonNull View cardView) {
            super(cardView);
            muralCV = cardView.findViewById(R.id.cv_mural_card);
            artistYearMuralTV = cardView.findViewById(R.id.tv_card_mural_artistyear);
            characterMuralTV = cardView.findViewById(R.id.tv_card_mural_character);
            muralCV.setOnClickListener(detailListener);
        }
    }

    private Application mApplication;


}
