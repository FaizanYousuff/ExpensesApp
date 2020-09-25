package com.faizan.myexpenses.presentation.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.ContextProvider;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseHolder> {

    private List<Expense> expenseList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense_detail, parent, false);
        return new ExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
        Activity activity = ContextProvider.getInstance().getActivity();
        if (expenseList != null) {
            Expense currentExpense = expenseList.get(position);
            holder.expenseOf.setText(currentExpense.getExpenseOf());
            holder.description.setText(String.valueOf(currentExpense.getDescription()));
            holder.date.setText(currentExpense.getDate());
            if (position == 0) {
                holder.amount.setText(currentExpense.getAmount());
                holder.amount.setTextColor(Color.WHITE);
                holder.amount.setTypeface(Typeface.DEFAULT);
                holder.expenseOf.setTextColor(Color.WHITE);
                holder.expenseOf.setTypeface(Typeface.DEFAULT);
                holder.description.setTextColor(Color.WHITE);
                holder.description.setTypeface(Typeface.DEFAULT);
                holder.date.setTextColor(Color.WHITE);
                holder.date.setTypeface(Typeface.DEFAULT);
                holder.bgLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));

            } else {
                String amt = activity.getResources().getString(R.string.Rs)
                        + "  " + currentExpense.getAmount();
                holder.amount.setText(amt);
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.amount.setText("No Data still ");
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public Expense getExpenseList(int position) {
        return expenseList.get(position);
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
        notifyDataSetChanged();
    }

    public void setOnItemClickLitener(OnItemClickListener litener) {
        this.listener = litener;
    }

    class ExpenseHolder extends RecyclerView.ViewHolder {

        private TextView amount;
        private TextView expenseOf;
        private TextView description;
        private TextView date;
        private RelativeLayout bgLayout;

        public ExpenseHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.text_amount);
            expenseOf = itemView.findViewById(R.id.text_expense_of);
            description = itemView.findViewById(R.id.text_description);
            date = itemView.findViewById(R.id.tv_date);
            bgLayout = itemView.findViewById(R.id.expense_layout_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION && position != 0) {
                        listener.onItemClick(expenseList.get(position));
                    }
                }
            });
        }
    }
}
