package com.faizan.myexpenses.presentation.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.Utils.ContextProvider;
import com.faizan.myexpenses.Utils.PreferenceHelper;
import com.faizan.myexpenses.Utils.ThreadPoolService;
import com.faizan.myexpenses.Utils.Utils;
import com.faizan.myexpenses.presentation.listener.DialogListener;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;

public class MonthlyExpenseListAdapter extends RecyclerView.Adapter<MonthlyExpenseListAdapter.MonthlyExpenseHolder> {

    private OnItemClickListener listener;
    private List<MonthlyExpense> monthlyExpenses = new LinkedList<>();
    private boolean isAuthenticated;

    @NonNull
    @Override
    public MonthlyExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_m, parent, false);
        return new MonthlyExpenseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthlyExpenseHolder holder, int position) {
        Activity activity = ContextProvider.getInstance().getActivity();

        if (monthlyExpenses != null) {
            MonthlyExpense monthlyExpense = monthlyExpenses.get(position);
            holder.expenses.setText(monthlyExpense.getExpenses());
            holder.title.setText(monthlyExpense.getMonth());
            holder.saved.setText(monthlyExpense.getSaved());


            if (position == 0) {
                holder.expenses.setTextColor(Color.WHITE);
                holder.expenses.setTypeface(Typeface.DEFAULT);
                holder.title.setTextColor(Color.WHITE);
                holder.title.setTypeface(Typeface.DEFAULT);
                holder.saved.setTextColor(Color.WHITE);
                holder.saved.setTypeface(Typeface.DEFAULT);
                holder.bgLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));

            } else {
                String expenseTitle = activity.getResources().getString(R.string.Expenses_of).concat(" " + monthlyExpense.getMonth());
                holder.title.setText(expenseTitle);
                holder.title.setTextColor(Color.BLACK);
                if (monthlyExpense.getIncome() != null && monthlyExpense.getExpenses() != null) {
                    String saved = String.valueOf(Integer.parseInt(monthlyExpense.getIncome()) - Integer.parseInt(monthlyExpense.getExpenses()));
                    holder.saved.setText(returnSavedAmount(saved));
                }
            }

        } else {
            holder.title.setText("No data Yet ");
        }
    }

    @Override
    public int getItemCount() {
        return monthlyExpenses.size();
    }

    public void setMonthlyExpenses(List<MonthlyExpense> monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
        notifyDataSetChanged();
    }

    public MonthlyExpense getMonthlyExpenses(int position) {
        return monthlyExpenses.get(position);
    }

    public void setOnItemClickLitener(OnItemClickListener litener) {
        this.listener = litener;
    }

    private String returnSavedAmount(String amount) {

        if(PreferenceHelper.getInstance(ContextProvider.getInstance().getActivity()).getBoolean(Constants.ENABLE_INCOME_MASKING,false)){
            if (isAuthenticated) {
                return amount;
            } else {
                return Utils.getMaskedString(amount);
            }
        } else {
            return amount;
        }
    }

    public class MonthlyExpenseHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView saved;
        private TextView expenses;
        private LinearLayout bgLayout;

        public MonthlyExpenseHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            saved = itemView.findViewById(R.id.tv_saved);
            expenses = itemView.findViewById(R.id.tv_expenses);
            bgLayout = itemView.findViewById(R.id.parent_layout);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION && getAdapterPosition() != 0) {
                        listener.onItemClick(monthlyExpenses.get(getAdapterPosition()));
                    }

                }
            });

            saved.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    isAuthenticated = false;
                    listener.onItemClick(Constants.AUTHENTICATE, new DialogListener() {
                        @Override
                        public void okPressed(Object... params) {
                            // Remove masked amount
                            isAuthenticated = true;

                            ThreadPoolService.getInstance().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });
                        }

                        @Override
                        public void cancelPressed() {
                            isAuthenticated = false;
                        }
                    });

                    return false;
                }
            });
        }
    }
}
