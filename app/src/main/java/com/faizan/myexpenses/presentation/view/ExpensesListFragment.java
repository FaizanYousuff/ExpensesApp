package com.faizan.myexpenses.presentation.view;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.Utils.ContextProvider;
import com.faizan.myexpenses.Utils.DialogUtils;
import com.faizan.myexpenses.Utils.ThreadPoolService;
import com.faizan.myexpenses.Utils.Utils;
import com.faizan.myexpenses.logger.Logger;
import com.faizan.myexpenses.presentation.adapter.MonthlyExpenseListAdapter;
import com.faizan.myexpenses.presentation.listener.DatabaseListener;
import com.faizan.myexpenses.presentation.listener.DialogListener;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;
import com.faizan.myexpenses.presentation.viewmodel.ExpenseMViewModel;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.KEYGUARD_SERVICE;

public class ExpensesListFragment extends BaseFragment {

    public static final int INTENT_AUTHENTICATE = 1;
    private static final String TAG = "ExpensesListFragment";
    private ExpenseMViewModel expenseMViewModel;
    private BaseActivity baseActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseMViewModel = new ExpenseMViewModel(this.getContext());
        baseActivity = ((BaseActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //RECYCLER view
        RecyclerView recyclerView = view.findViewById(R.id.rv_expense_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final MonthlyExpenseListAdapter adapter = new MonthlyExpenseListAdapter();
        recyclerView.setAdapter(adapter);

        final MonthlyExpense expense = new MonthlyExpense();
        expense.setMonth(Utils.getCurrentMonthString());
        expense.setMonthNumber(Utils.getCurrentMonthNumber());

        expenseMViewModel.insertExpensesM(expense);
        expenseMViewModel.getAllExpensesM().observe(this, new Observer<List<MonthlyExpense>>() {
            @Override
            public void onChanged(List<MonthlyExpense> monthlyExpenses) {
                MonthlyExpense dummyExpense = new MonthlyExpense();
                dummyExpense.setSaved("Saved Amount");
                dummyExpense.setExpenses("Spent Amount ");
                dummyExpense.setMonth("Expenses of Month");
                monthlyExpenses.add(0, dummyExpense);
                adapter.setMonthlyExpenses(monthlyExpenses);
            }
        });

        adapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object... params) {

                if (params[0] instanceof String) {
                    addFingerprintCheck(getActivity(), (DialogListener) params[1]);
                } else {
                    MonthlyExpense expenseClicked = (MonthlyExpense) params[0];
                    expenseMViewModel.getMonthlyExpense(expenseClicked.getMonthNumber(), new DatabaseListener() {
                        @Override
                        public void dataFound(Object... params) {

                            final MonthlyExpense monthlyExpense = (MonthlyExpense) params[0];
                            if (monthlyExpense != null && monthlyExpense.getIncome() != null) {
                                Logger.debug("test Income " + monthlyExpense.getIncome());

                                navigateToDetails(monthlyExpense);
                            } else {
                                showIncomeDialog(monthlyExpense);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.error(TAG, " onActivityResult resultCode " + resultCode);

        if (requestCode == INTENT_AUTHENTICATE) {
            if (resultCode != RESULT_OK) {
                //do something you want when pass the security
                System.exit(0);
            }
        }
    }

    private void addFingerprintCheck(FragmentActivity activity, final DialogListener listener) {

        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, ThreadPoolService.getInstance().getExecutorService(), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Logger.error(TAG, "An onAuthenticationError error occurred " + errorCode + " " + errString);
               /* if (errorCode == BiometricConstants.ERROR_USER_CANCELED || errorCode == BiometricConstants.ERROR_NEGATIVE_BUTTON) {
                    // User canceled the operation

                    // you can either show the dialog again here

                    // or use alternate authentication (e.g. a password) - recommended wayD
                    navigateToPinPrompt();
                }*/
                listener.cancelPressed();

            }

            //onAuthenticationSucceeded is called when a fingerprint is matched successfully//
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Print a message to Logcat//
                Logger.debug(TAG, "Fingerprint recognised successfully");
                listener.okPressed();
            }

            //onAuthenticationFailed is called when the fingerprint doesn’t match//
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Logger.error(TAG, "Fingerprint not recognised");
                listener.cancelPressed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().
                setTitle("Please Authenticate").setSubtitle("Unlock MyExpenses").setNegativeButtonText("Cancel").build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void navigateToDetails(MonthlyExpense monthlyExpense) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.MONTH_CLICKED, monthlyExpense.getMonthNumber());
        DetailExpenseFragment expenseFragment = new DetailExpenseFragment();
        expenseFragment.setArguments(bundle);
        baseActivity.switchFragments(expenseFragment, Constants.D_EXPENSES_FRAG);
    }

    private void showIncomeDialog(final MonthlyExpense monthlyExpense) {
        ThreadPoolService.getInstance().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtils.getInstance().showIncomeDialog(new DialogListener() {
                    @Override
                    public void okPressed(Object... params) {
                        monthlyExpense.setMonth(Utils.getCurrentMonthString());
                        monthlyExpense.setMonthNumber(Utils.getCurrentMonthNumber());
                        monthlyExpense.setIncome((String) params[0]);
                        expenseMViewModel.updateExpensesM(monthlyExpense);
                    }

                    @Override
                    public void cancelPressed() {

                    }
                });
            }
        });
    }

    private void navigateToPinPrompt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            KeyguardManager keyguardManager = (KeyguardManager) ContextProvider.getInstance().getActivity().getSystemService(KEYGUARD_SERVICE);
            if (keyguardManager.isKeyguardSecure()) {
                Intent authIntent = keyguardManager.createConfirmDeviceCredentialIntent("Please enter pin ", "Please Authenticate");

                startActivityForResult(authIntent, INTENT_AUTHENTICATE);
            }
        }
    }
}