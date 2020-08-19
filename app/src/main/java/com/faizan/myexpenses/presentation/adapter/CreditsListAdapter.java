package com.faizan.myexpenses.presentation.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.R;

import java.util.ArrayList;
import java.util.List;

public class CreditsListAdapter extends RecyclerView.Adapter<CreditsListAdapter.CreditsHolder> {

    private onItemClickLitener listener;
    private List<Credits> credits = new ArrayList<>();


    class CreditsHolder extends RecyclerView.ViewHolder {
        private TextView amount;
        private TextView tv_description;
        private TextView tv_id;
        private TextView tv_to;

        public CreditsHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.text_amount);
            tv_description = itemView.findViewById(R.id.text_description);
            tv_id = itemView.findViewById(R.id.text_id);
            tv_to = itemView.findViewById(R.id.text_to);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(credits.get(position));
                        //litener.onItemClick(getItem(position));
                    }
                }
            });
        }


    }

    @NonNull
    @Override
    public CreditsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.credits_item, viewGroup, false);
        return new CreditsHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull CreditsHolder creditsHolder, int i) {

        if (credits != null) {
            Credits currentCredit = credits.get(i);
            // Note currentNote = getItem(i);
            creditsHolder.amount.setText(currentCredit.getAmount());
            creditsHolder.tv_description.setText(currentCredit.getDescription());
            creditsHolder.tv_id.setText(String.valueOf(currentCredit.getId()));
            creditsHolder.tv_to.setText(currentCredit.getTo());
           // creditsHolder.tv_from.setText(currentCredit.getFrom());
        } else {
            // Covers the case of data not being ready yet.
            creditsHolder.amount.setText("No Data still ");
        }

    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public interface onItemClickLitener {

        void onItemClick(Credits note);
    }

    public void setOnItemClickLitener(onItemClickLitener litener) {
        this.listener = litener;
    }

    public void setCredits(List<Credits> credits) {
        this.credits = credits;
        notifyDataSetChanged();
    }

    public Credits getCredit(int position) {
        return credits.get(position);
        //return getItem(position);
    }
}
