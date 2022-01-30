package com.faizan.myexpenses.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.DetailOtherExpense;
import com.faizan.myexpenses.DataLayer.model.OtherExpense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.DialogUtils;
import com.faizan.myexpenses.presentation.adapter.OtherExpenseAdapter;
import com.faizan.myexpenses.presentation.listener.DialogListener;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;
import com.faizan.myexpenses.presentation.viewmodel.OtherExpenseDViewModel;
import com.faizan.myexpenses.presentation.viewmodel.OtherExpenseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OtherExpenseFragment extends BaseFragment {

    int totalAmt = 0;
    private BaseActivity baseActivity;
    private OtherExpenseViewModel otherExpenseViewModel;
    private OtherExpenseDViewModel expenseDViewModel;
    private FloatingActionButton floatingActionButton;
    private boolean isOtherExpenseScreen;
    private TextView tv_title;
    private int expenseId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otherExpenseViewModel = new OtherExpenseViewModel(this.getContext());
        expenseDViewModel = new OtherExpenseDViewModel(this.getContext());
        baseActivity = ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_title = view.findViewById(R.id.title);
        //RECYCLER view
        RecyclerView recyclerView = view.findViewById(R.id.rv_other_expense);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final OtherExpenseAdapter adapter = new OtherExpenseAdapter();
        recyclerView.setAdapter(adapter);


        otherExpenseViewModel.getAllOtherExpenses().observe(this, new Observer<List<OtherExpense>>() {
            @Override
            public void onChanged(List<OtherExpense> otherExpenses) {
                OtherExpense expense = new OtherExpense();
                expense.setExpenseName("Expense Name");
                expense.setTotalAmount(0);
                expense.setSpentAmount(0);
                otherExpenses.add(0, expense);
                if (totalAmt == 0) {
                    adapter.setOtherExpenseList(otherExpenses, true);

                }
            }
        });

        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object... Params) {

                if (Params[0] instanceof OtherExpense) {
                    populateExpenses(adapter, (OtherExpense) Params[0]);

                } else if (Params[0] instanceof String) {

                    final OtherExpense otherExpense = (OtherExpense) Params[1];
                    DetailOtherExpense detailOtherExpense = new DetailOtherExpense();
                    detailOtherExpense.setAmount(otherExpense.getTotalAmount());
                    detailOtherExpense.setDescription(otherExpense.getExpenseName());
                    DialogUtils.getInstance().addOtherDialog(new DialogListener() {
                        @Override
                        public void okPressed(Object... params) {
                            OtherExpense expense = (OtherExpense) params[0];
                            expense.setId(otherExpense.getId());

                            if (params[0] instanceof OtherExpense) {
                                otherExpenseViewModel.updateOtherExpense(expense);
                            }

                        }

                        @Override
                        public void cancelPressed() {

                        }
                    }, detailOtherExpense, adapter.isOtherExpenseScreen());
                } else if (Params[0] instanceof DetailOtherExpense) {
                    DialogUtils.getInstance().addOtherDialog(new DialogListener() {
                        @Override
                        public void okPressed(Object... params) {

                            if (params[0] instanceof OtherExpense) {
                                otherExpenseViewModel.insertOtherExpense((OtherExpense) params[0]);

                            } else if (params[0] instanceof DetailOtherExpense) {
                                expenseDViewModel.insertDetailOtherExpense((DetailOtherExpense) params[0]);
                            }

                        }

                        @Override
                        public void cancelPressed() {

                        }
                    }, (DetailOtherExpense) Params[0], adapter.isOtherExpenseScreen());
                }
            }
        });

        // For Swiping Options
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                DialogUtils.getInstance().showPasswordDialog(new DialogListener() {

                    @Override
                    public void okPressed(Object... params) {
                        otherExpenseViewModel.deleteOtherExpense(adapter.getOtherExpense(viewHolder.getAdapterPosition()));
                    }

                    @Override
                    public void cancelPressed() {

                    }
                },false);
            }
        }).attachToRecyclerView(recyclerView);

        // FAB BUTTON
        floatingActionButton = view.findViewById(R.id.btn_add_OtherExpense);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailOtherExpense detailOtherExpense = new DetailOtherExpense();
                detailOtherExpense.setExpenseId(expenseId);
                DialogUtils.getInstance().addOtherDialog(new DialogListener() {
                    @Override
                    public void okPressed(Object... params) {

                        if (params[0] instanceof OtherExpense) {
                            otherExpenseViewModel.insertOtherExpense((OtherExpense) params[0]);

                        } else if (params[0] instanceof DetailOtherExpense) {
                            expenseDViewModel.insertDetailOtherExpense((DetailOtherExpense) params[0]);
                        }

                    }

                    @Override
                    public void cancelPressed() {

                    }
                }, detailOtherExpense, adapter.isOtherExpenseScreen());
            }
        });
    }


    private List<OtherExpense> convertToOtherExpense(List<DetailOtherExpense> detailList, int expenseId) {
        List<OtherExpense> expenseList = new ArrayList<>();
        totalAmt = 0;
        for (DetailOtherExpense expense : detailList) {
            OtherExpense otherExpense = new OtherExpense();
            otherExpense.setTotalAmount(expense.getAmount());
            otherExpense.setExpenseName(expense.getDescription());
            otherExpense.setId(expenseId);
            expenseList.add(otherExpense);
            totalAmt += expense.getAmount();
        }

        OtherExpense otherExpense = new OtherExpense();
        otherExpense.setTotalAmount(0);
        otherExpense.setExpenseName("Expense Description");
        expenseList.add(0, otherExpense);

        if (expenseList.size() > 1) {
            otherExpense = new OtherExpense();
            otherExpense.setTotalAmount(totalAmt);
            otherExpense.setExpenseName("Total Amount");
            expenseList.add(otherExpense);
        }

        return expenseList;
    }

    private void populateExpenses(final OtherExpenseAdapter adapter, final OtherExpense otherExpense) {

        expenseDViewModel.getDetailOtherExpense(otherExpense.getId()).observe(this, new Observer<List<DetailOtherExpense>>() {
            @Override
            public void onChanged(List<DetailOtherExpense> detailOtherExpenses) {
                adapter.setOtherExpenseList(convertToOtherExpense(detailOtherExpenses, otherExpense.getId()), false);
                if (totalAmt != 0) {
                    otherExpenseViewModel.updateSpentAmount(totalAmt, expenseId);
                }
            }
        });

        isOtherExpenseScreen = false;
        tv_title.setText("Expense Of " + otherExpense.getExpenseName());
        expenseId = otherExpense.getId();
    }
}