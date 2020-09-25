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

import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.Utils.DialogUtils;
import com.faizan.myexpenses.Utils.Utils;
import com.faizan.myexpenses.presentation.adapter.ExpenseListAdapter;
import com.faizan.myexpenses.presentation.adapter.SummaryListAdapter;
import com.faizan.myexpenses.presentation.listener.DialogListener;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;
import com.faizan.myexpenses.presentation.viewmodel.ExpenseDViewModel;
import com.faizan.myexpenses.presentation.viewmodel.ExpenseMViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class DetailExpenseFragment extends BaseFragment {

    private SummaryListAdapter listAdapter;
    private BaseActivity baseActivity;
    private int monthNumber = 0;
    private ExpenseDViewModel expenseDViewModel;
    private ExpenseMViewModel expenseMViewModel;

    private ExpenseListAdapter expenseListAdapter;
    private RecyclerView rvExpenses;
    private RecyclerView rvSummary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = ((BaseActivity) getActivity());
        expenseDViewModel = new ExpenseDViewModel(this.getContext());
        expenseMViewModel = new ExpenseMViewModel(this.getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_detail_expense, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            monthNumber = bundle.getInt(Constants.MONTH_CLICKED);
        }
        ((TextView) fragmentView.findViewById(R.id.tv_summary)).setText(Utils.getMonthStr(monthNumber) + " " + getResources().getString(R.string.summary));


        // fragmentView.findViewById(R.id.tv_summarytvSummary.setText(String.valueOf(monthNumber));


        // Recycler view
        listAdapter = new SummaryListAdapter(getActivity());
        rvSummary = fragmentView.findViewById(R.id.summary_list_view);
        rvSummary.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSummary.setHasFixedSize(true);
        rvSummary.setAdapter(listAdapter);

        // Recycler view
        rvExpenses = fragmentView.findViewById(R.id.rv_expenses);
        rvExpenses.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvExpenses.setHasFixedSize(true);

        /*detailExpenseBinding.rvExpenses.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/
        expenseListAdapter = new ExpenseListAdapter();
        rvExpenses.setAdapter(expenseListAdapter);


        expenseDViewModel.getAllExpensesD(String.valueOf(monthNumber)).observe(this, new Observer<List<Expense>>() {
            @Override
            public void onChanged(@Nullable List<Expense> expenses) {

                Expense title = new Expense();
                title.setDate("Date of Expense");
                title.setExpenseOf("Expense of");
                title.setAmount("Amount");
                title.setDescription("Expense Description");
                if (expenses != null) {
                    listAdapter.setExpenseList(expenses);
                    expenses.add(0, title);
                }

                // update RecyclerView
                expenseListAdapter.setExpenseList(expenses);
                expenseMViewModel.updateSpentAmount(listAdapter.getTotalExpensesAmt(), monthNumber);

            }
        });

        // For EDITING CARD VIEW
        expenseListAdapter.setOnItemClickLitener(new OnItemClickListener() {
                                                     @Override
                                                     public void onItemClick(Object... params) {
                                                         DialogUtils.getInstance().addExpenseDialog((Expense) params[0], new DialogListener() {
                                                             @Override
                                                             public void okPressed(Object... params) {

                                                                 expenseDViewModel.updateExpensesM((Expense) params[0]);

                                                             }

                                                             @Override
                                                             public void cancelPressed() {

                                                             }
                                                         });
                                                     }

                                                 }
        );


        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final Expense expense = new Expense();
        expense.setExpenseOfMonth(String.valueOf(monthNumber));
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btn_add_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getInstance().addExpenseDialog(expense, new DialogListener() {
                    @Override
                    public void okPressed(Object... params) {
                     /*   if(isEdit){
                            expenseDViewModel.updateExpensesM((Expense) params[0]);
                        } else {
                            expenseDViewModel.insertExpensesD((Expense) params[0]);
                        }*/

                        expenseDViewModel.insertExpensesD((Expense) params[0]);

                    }

                    @Override
                    public void cancelPressed() {

                    }
                });
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
                        expenseDViewModel.deleteExpenseD(expenseListAdapter.getExpenseList(viewHolder.getAdapterPosition()));
                    }

                    @Override
                    public void cancelPressed() {

                    }
                });
            }
        }).attachToRecyclerView(rvExpenses);
    }
}