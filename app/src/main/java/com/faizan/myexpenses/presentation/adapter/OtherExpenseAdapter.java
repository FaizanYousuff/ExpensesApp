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

        import com.faizan.myexpenses.DataLayer.model.OtherExpense;
        import com.faizan.myexpenses.R;
        import com.faizan.myexpenses.Utils.ContextProvider;
        import com.faizan.myexpenses.presentation.listener.OnItemClickListener;

        import java.util.ArrayList;
        import java.util.List;

public class OtherExpenseAdapter extends RecyclerView.Adapter<OtherExpenseAdapter.ExpenseViewholder> {

    boolean isOtherExpenseScreen;
    private OnItemClickListener listener;
    private List<OtherExpense> otherExpenseList = new ArrayList<>();

    @NonNull
    @Override
    public ExpenseViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_expense, parent, false);
        return new ExpenseViewholder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewholder holder, int position) {
        Activity activity = ContextProvider.getInstance().getActivity();

        if (otherExpenseList != null) {
            OtherExpense otherExpense = otherExpenseList.get(position);
            holder.amount.setText(String.valueOf(otherExpense.getTotalAmount()));
            holder.spentAmount.setText(String.valueOf(otherExpense.getSpentAmount()));
            holder.expenseName.setText(otherExpense.getExpenseName());
            holder.remainingAmount.setText(String.valueOf(otherExpense.getTotalAmount() - otherExpense.getSpentAmount()));

            if (position == 0) {
                holder.amount.setTextColor(Color.WHITE);
                holder.amount.setTypeface(Typeface.DEFAULT);
                holder.spentAmount.setTextColor(Color.WHITE);
                holder.spentAmount.setTypeface(Typeface.DEFAULT);
                holder.expenseName.setTextColor(Color.WHITE);
                holder.expenseName.setTypeface(Typeface.DEFAULT);
                holder.remainingAmount.setTextColor(Color.WHITE);
                holder.remainingAmount.setTypeface(Typeface.DEFAULT);
                holder.bgLayout.setBackgroundColor(activity.getResources().getColor(R.color.colorOrange));

                if (isOtherExpenseScreen) {
                    holder.amount.setText("Amount");
                    holder.spentAmount.setText("Spent Amount");
                    holder.expenseName.setText("Expense name");
                    holder.remainingAmount.setText("RemainingAmount");

                } else {
                    holder.amount.setText("Amount");
                    holder.expenseName.setText("Expense Description");
                }

            }

            if (!isOtherExpenseScreen) {
                holder.spentAmount.setVisibility(View.GONE);
                holder.remainingAmount.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return otherExpenseList.size();
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void setOtherExpenseList(List<OtherExpense> otherExpenseList, boolean isOtherExpenseScreen) {
        this.otherExpenseList = otherExpenseList;
        this.isOtherExpenseScreen = isOtherExpenseScreen;
        notifyDataSetChanged();
    }

    public boolean isOtherExpenseScreen() {
        return isOtherExpenseScreen;
    }

    public OtherExpense getOtherExpense(int position) {
        return otherExpenseList.get(position);
    }

    public class ExpenseViewholder extends RecyclerView.ViewHolder {

        private TextView amount;
        private TextView expenseName;
        private TextView spentAmount;
        private TextView remainingAmount;
        private RelativeLayout bgLayout;

        public ExpenseViewholder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.text_amount);
            expenseName = itemView.findViewById(R.id.text_expense_name);
            spentAmount = itemView.findViewById(R.id.text_spent_amt);
            remainingAmount = itemView.findViewById(R.id.text_remaining_amt);
            bgLayout = itemView.findViewById(R.id.parent_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION && getAdapterPosition() != 0) {
                        listener.onItemClick(otherExpenseList.get(getAdapterPosition()));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION && getAdapterPosition() != 0) {
                        listener.onItemClick("EDIT", otherExpenseList.get(getAdapterPosition()));
                    }
                    return false;
                }
            });
        }
    }
}
