package com.faizan.myexpenses.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faizan.myexpenses.DataLayer.model.DetailOtherExpense;
import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.DataLayer.model.OtherExpense;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.presentation.listener.DialogListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.faizan.myexpenses.Utils.Constants.PASSWORD;

public class DialogUtils {

    private static DialogUtils instance;
    private Dialog dialog;

    public static DialogUtils getInstance() {
        if (instance == null) {
            instance = new DialogUtils();
        }
        return instance;
    }

    public void showPasswordDialog(final DialogListener listener) {
        dialog = new Dialog(ContextProvider.getInstance().getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = ContextProvider.getInstance().getActivity().getResources().getDisplayMetrics().widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText pwd = dialog.findViewById(R.id.tid_et);
        pwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        final TextView title = dialog.findViewById(R.id.dialog_title);
        title.setText("Enter Password");
        dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd.getText().toString().trim().length() != 4) {
                    pwd.setError("Password  should be 4 characters only");
                } else if (!pwd.getText().toString().trim().equals(PASSWORD)) {
                    pwd.setError("Please enter valid password");
                } else {
                    dialog.dismiss();
                    listener.okPressed();
                }
            }
        });

        dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelPressed();
                dialog.dismiss();
            }
        });
    }

    public void showIncomeDialog(final DialogListener listener) {
        dialog = new Dialog(ContextProvider.getInstance().getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = ContextProvider.getInstance().getActivity().getResources().getDisplayMetrics().widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText pwd = dialog.findViewById(R.id.tid_et);
        final TextView title = dialog.findViewById(R.id.dialog_title);
        title.setText("Enter Income");
        dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd.getText().toString().trim().length() < 1) {
                    pwd.setError("Please enter income ");
                } else {
                    dialog.dismiss();
                    listener.okPressed(pwd.getText().toString().trim());
                }
            }
        });

        dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelPressed();
                dialog.dismiss();
            }
        });
    }

    public void addExpenseDialog(final Expense expense, final DialogListener listener) {

        dialog = new Dialog(ContextProvider.getInstance().getActivity());
        dialog.setContentView(R.layout.dialog_add_expense);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = ContextProvider.getInstance().getActivity().getResources().getDisplayMetrics().widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText etAmount = dialog.findViewById(R.id.et_amount);
        final EditText etExpenseOf = dialog.findViewById(R.id.et_expense_of);
        final EditText etDescription = dialog.findViewById(R.id.et_description);
        final EditText etDate = dialog.findViewById(R.id.et_date);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ContextProvider.getInstance().getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        if (expense != null) {
            etAmount.setText(expense.getAmount());
            etDescription.setText(expense.getDescription());
            etExpenseOf.setText(expense.getExpenseOf());
            etDate.setText(expense.getDate());
            if (expense.getAmount() != null) {
                ((Button) dialog.findViewById(R.id.btn_dialog_add)).setText("Update");
            }
        }

        dialog.findViewById(R.id.btn_dialog_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isNullOrEmpty(etAmount.getText().toString())) {
                    etAmount.setError(" Please insert Amount");
                    return;
                }
                if (Utils.isNullOrEmpty(etDate.getText().toString())) {
                    etDate.setError(" Please insert Proper date");
                    return;
                }
                if (Utils.isNullOrEmpty(etExpenseOf.getText().toString())) {
                    etExpenseOf.setError(" Please insert expense Of ");
                    return;
                }
                if (Utils.isNullOrEmpty(etDescription.getText().toString())) {
                    etDescription.setError(" Please insert Expense description");
                    return;
                }

                expense.setAmount(etAmount.getText().toString());
                expense.setDate(etDate.getText().toString());
                expense.setDescription(etDescription.getText().toString());
                expense.setExpenseOf(etExpenseOf.getText().toString());

                dialog.dismiss();
                listener.okPressed(expense);


            }
        });

        dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelPressed();
                dialog.dismiss();
            }
        });
    }

    public void addOtherDialog(final DialogListener listener, final DetailOtherExpense detailOtherExpense, final boolean isOtherExpense) {

        dialog = new Dialog(ContextProvider.getInstance().getActivity());
        dialog.setContentView(R.layout.dialog_add_other_expense);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = ContextProvider.getInstance().getActivity().getResources().getDisplayMetrics().widthPixels;
        dialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final EditText etAmount = dialog.findViewById(R.id.et_amount);
        final EditText etExpenseName = dialog.findViewById(R.id.et_expense_name);
        final EditText etDescription = dialog.findViewById(R.id.et_description);

        if (!isOtherExpense) {
            dialog.findViewById(R.id.description_layout).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.layout_expense_name).setVisibility(View.GONE);

        }


        if (detailOtherExpense != null /*&& detailOtherExpense.getId() !=0*/
                && !isOtherExpense) {
            etAmount.setText(String.valueOf(detailOtherExpense.getAmount()));
            etDescription.setText(detailOtherExpense.getDescription());

        } else {
            if (detailOtherExpense != null) {
                etAmount.setText(String.valueOf(detailOtherExpense.getAmount()));
                etExpenseName.setText(detailOtherExpense.getDescription());

            }
        }


        dialog.findViewById(R.id.btn_dialog_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isOtherExpense) {
                    if (Utils.isNullOrEmpty(etExpenseName.getText().toString())) {
                        etExpenseName.setError(" Please insert expense name ");
                        return;
                    }
                }

                if (!isOtherExpense) {
                    if (Utils.isNullOrEmpty(etDescription.getText().toString())) {
                        etDescription.setError(" Please insert Expense description");
                        return;
                    }
                }

                if (isOtherExpense) {
                    OtherExpense otherExpense = new OtherExpense();
                    if (!etAmount.getText().toString().isEmpty()) {
                        otherExpense.setTotalAmount(Integer.parseInt(etAmount.getText().toString().trim()));
                    }
                    otherExpense.setExpenseName(etExpenseName.getText().toString().trim());
                    dialog.dismiss();
                    listener.okPressed(otherExpense);
                } else {
                    DetailOtherExpense detailExpense = new DetailOtherExpense();
                    detailExpense.setAmount(Integer.parseInt(etAmount.getText().toString().trim()));
                    detailExpense.setDescription(etDescription.getText().toString().trim());
                    detailExpense.setExpenseId(detailOtherExpense.getExpenseId());
                    dialog.dismiss();
                    listener.okPressed(detailExpense);
                }


            }
        });

        dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancelPressed();
                dialog.dismiss();
            }
        });
    }
}
