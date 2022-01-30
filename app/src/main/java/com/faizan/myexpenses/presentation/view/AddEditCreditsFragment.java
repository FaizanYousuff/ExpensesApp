package com.faizan.myexpenses.presentation.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.ExpenseApplication;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.ContextProvider;
import com.faizan.myexpenses.Utils.Utils;
import com.faizan.myexpenses.databinding.FragmentAddEditCreditsBinding;
import com.faizan.myexpenses.presentation.viewmodel.CreditsViewModel;
import com.faizan.myexpenses.presentation.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditCreditsFragment extends Fragment {

    private FragmentAddEditCreditsBinding addEditCreditsBinding;
    private MainViewModel mainViewModel;
    private CreditsViewModel creditsViewModel;
    private BaseActivity baseActivity;
    private boolean isEdit;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creditsViewModel = new CreditsViewModel(ExpenseApplication.getAppContext());
        mainViewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        baseActivity = ((BaseActivity) getActivity());
        mainViewModel.getCreditsMutableLiveData().observe(this, new Observer<Credits>() {
            @Override
            public void onChanged(Credits credits1) {
                populateCredits(credits1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addEditCreditsBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_edit_credits, container, false);

        return addEditCreditsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                addEditCreditsBinding.etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        addEditCreditsBinding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ContextProvider.getInstance().getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addEditCreditsBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCredits();
            }
        });
    }

    private void populateCredits(Credits credits) {
        if (credits != null) {
            addEditCreditsBinding.etAmount.setText(credits.getAmount());
            addEditCreditsBinding.etDescription.setText(credits.getDescription());
            addEditCreditsBinding.etTo.setText(credits.getTo());
            addEditCreditsBinding.etFrom.setText(credits.getFrom());
            addEditCreditsBinding.etDate.setText(credits.getDate());
            isEdit = true;
            id = credits.getId();
        }
    }

    private void saveCredits() {
        if (Utils.isNullOrEmpty(addEditCreditsBinding.etAmount.getText().toString())) {
            addEditCreditsBinding.etAmount.setError(" Please insert Amount");
            return;
        }
        if (Utils.isNullOrEmpty(addEditCreditsBinding.etDate.getText().toString())) {
            addEditCreditsBinding.etDate.setError(" Please insert Proper date");
            return;
        }
        if (Utils.isNullOrEmpty(addEditCreditsBinding.etFrom.getText().toString())) {
            addEditCreditsBinding.etFrom.setError(" Please insert Credit taken From ");
            return;
        }
        if (Utils.isNullOrEmpty(addEditCreditsBinding.etTo.getText().toString())) {
            addEditCreditsBinding.etTo.setError(" Please insert credit given To ");
            return;
        }
        if (Utils.isNullOrEmpty(addEditCreditsBinding.etDescription.getText().toString())) {
            addEditCreditsBinding.etDescription.setError(" Please insert credits description");
            return;
        }
        Credits credits = new Credits();
        credits.setAmount(addEditCreditsBinding.etAmount.getText().toString().trim());
        credits.setDate(addEditCreditsBinding.etDate.getText().toString().trim());
        credits.setDescription(addEditCreditsBinding.etDescription.getText().toString().trim());
        credits.setFrom(addEditCreditsBinding.etFrom.getText().toString().trim());
        credits.setTo(addEditCreditsBinding.etTo.getText().toString().trim());
        credits.setId(id);

        if (isEdit) {
            creditsViewModel.updateCredits(credits,baseActivity);
        } else {
            creditsViewModel.insertCredit(credits, baseActivity);
        }
    }
}