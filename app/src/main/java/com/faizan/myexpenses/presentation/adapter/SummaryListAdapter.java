package com.faizan.myexpenses.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.ContextProvider;

import java.util.ArrayList;
import java.util.List;

public class SummaryListAdapter extends RecyclerView.Adapter<SummaryListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private int totalExpensesAmt = 0;

    private List<Expense> summaryList;

    public SummaryListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        summaryList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_summary, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Activity activity = ContextProvider.getInstance().getActivity();

        if (summaryList != null) {
            Expense currentExpense = summaryList.get(position);
            holder.expenseOf.setText(currentExpense.getExpenseOf());
            holder.amount.setText(currentExpense.getAmount());

            if (summaryList.size() - 1 == position) {
                holder.expenseOf.setTextColor(activity.getResources().getColor(R.color.colorBlack));
                holder.expenseOf.setTypeface(Typeface.DEFAULT_BOLD);
                holder.amount.setTextColor(activity.getResources().getColor(R.color.colorRed));
                holder.amount.setTypeface(Typeface.DEFAULT_BOLD);

            }
        } else {
            // Covers the case of data not being ready yet.
            holder.amount.setText("No Data still ");
        }
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public Expense getSummaryList(int position) {
        return summaryList.get(position);
    }

    public void setExpenseList(List<Expense> expenseList) {
        summaryList = new ArrayList<>();
        totalExpensesAmt = 0;
        for (Expense expense : expenseList) {
            boolean duplicate = false;
            for (int i = 0; i < summaryList.size(); i++) {
                if (expense.getExpenseOf().trim().equalsIgnoreCase(summaryList.get(i).getExpenseOf().trim())) {

                    String amt = String.valueOf(Integer.parseInt(expense.getAmount())
                            + Integer.parseInt(summaryList.get(i).getAmount()));
                    Expense duplicateExpense = new Expense();
                    duplicateExpense.setAmount(amt);
                    duplicateExpense.setExpenseOf(expense.getExpenseOf());
                    summaryList.set(i, duplicateExpense);
                    duplicate = true;
                }
            }

            if (!duplicate) {
                summaryList.add(expense);
            }
            totalExpensesAmt += Integer.parseInt(expense.getAmount());

        }

        Expense dummyExpense = new Expense();

        dummyExpense.setExpenseOf("Expense Of ");
        dummyExpense.setAmount("AMOUNT ");
        summaryList.add(0, dummyExpense);

        dummyExpense = new Expense();
        dummyExpense.setExpenseOf("TOTAL");
        dummyExpense.setAmount(ContextProvider.getInstance().getActivity().getResources().getString(R.string.Rs) + String.valueOf(totalExpensesAmt));
        summaryList.add(dummyExpense);
        notifyDataSetChanged();
    }

    public String getTotalExpensesAmt() {
        return String.valueOf(totalExpensesAmt);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView amount;
        private TextView expenseOf;
        private LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.tv_amount_summary_value);
            expenseOf = itemView.findViewById(R.id.tv_expense_of_value);
        }
    }
}
