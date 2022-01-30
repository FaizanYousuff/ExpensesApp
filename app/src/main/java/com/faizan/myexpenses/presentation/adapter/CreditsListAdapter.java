package com.faizan.myexpenses.presentation.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.ContextProvider;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CreditsListAdapter extends RecyclerView.Adapter<CreditsListAdapter.CreditsHolder> {

    private OnItemClickListener listener;
    private List<Credits> credits = new ArrayList<>();

    @NonNull
    @Override
    public CreditsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_credits, viewGroup, false);
        return new CreditsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsHolder creditsHolder, int i) {

        if (credits != null) {
            Credits currentCredit = credits.get(i);
            // Note currentNote = getItem(i);
            creditsHolder.amount.setText(currentCredit.getAmount());
            creditsHolder.tv_description.setText(currentCredit.getDescription());
            creditsHolder.tv_to.setText(currentCredit.getTo());
            creditsHolder.tv_from.setText(currentCredit.getFrom());
            creditsHolder.tv_date.setText(currentCredit.getDate());


            if (i == 0) {
                creditsHolder.amount.setTextColor(Color.WHITE);
                creditsHolder.amount.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_description.setTextColor(Color.WHITE);
                creditsHolder.tv_description.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_to.setTextColor(Color.WHITE);
                creditsHolder.tv_to.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_from.setTextColor(Color.WHITE);
                creditsHolder.tv_from.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_date.setTextColor(Color.WHITE);
                creditsHolder.tv_date.setTypeface(Typeface.DEFAULT);
                creditsHolder.bgLayout.setBackgroundColor(ContextProvider.getInstance().getActivity().getResources().getColor(R.color.colorOrange));
            } else  {
                creditsHolder.amount.setTextColor(Color.BLACK);
                creditsHolder.amount.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_description.setTextColor(Color.BLACK);
                creditsHolder.tv_description.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_to.setTextColor(Color.BLACK);
                creditsHolder.tv_to.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_from.setTextColor(Color.BLACK);
                creditsHolder.tv_from.setTypeface(Typeface.DEFAULT);
                creditsHolder.tv_date.setTextColor(Color.BLACK);
                creditsHolder.tv_date.setTypeface(Typeface.DEFAULT);
                creditsHolder.bgLayout.setBackgroundColor(ContextProvider.getInstance().getActivity().getResources().getColor(R.color.colorWhite));

            }
        } else {
            // Covers the case of data not being ready yet.
            creditsHolder.amount.setText("No Data still ");
        }

    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public void setOnItemClickLitener(OnItemClickListener litener) {
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

    class CreditsHolder extends RecyclerView.ViewHolder {
        private TextView amount;
        private TextView tv_description;
        private TextView tv_from;
        private TextView tv_to;
        private RelativeLayout bgLayout;
        private TextView tv_date;

        public CreditsHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.text_amount);
            tv_description = itemView.findViewById(R.id.text_description);
            tv_from = itemView.findViewById(R.id.text_from);
            tv_to = itemView.findViewById(R.id.text_to);
            bgLayout = itemView.findViewById(R.id.parent_layout);
            tv_date = itemView.findViewById(R.id.text_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION && position != 0) {
                        listener.onItemClick(credits.get(position));
                    }
                }
            });
        }
    }
}
