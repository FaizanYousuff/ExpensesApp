package com.faizan.myexpenses.presentation.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.ExpenseApplication;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.databinding.FragmentAddEditCreditsBinding;
import com.faizan.myexpenses.presentation.Utils;
import com.faizan.myexpenses.presentation.viewmodel.CreditsViewModel;
import com.faizan.myexpenses.presentation.viewmodel.MainViewModel;

public class AddEditCreditsFragment extends Fragment {

    private FragmentAddEditCreditsBinding  addEditCreditsBinding;
    private MainViewModel mainViewModel;
    private CreditsViewModel creditsViewModel;
    private BaseActivity baseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        creditsViewModel =new CreditsViewModel(ExpenseApplication.getAppContext());
        mainViewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        baseActivity = ((BaseActivity)getActivity());
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
        addEditCreditsBinding =  DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_edit_credits, container, false);

        return addEditCreditsBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addEditCreditsBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveCredits();
            }
        });
    }

    private  void populateCredits(Credits credits){
        if(credits != null){
            addEditCreditsBinding.etAmount.setText(credits.getAmount());
            addEditCreditsBinding.etDescription.setText(credits.getDescription());
            addEditCreditsBinding.etTo.setText(credits.getTo());
            addEditCreditsBinding.etFrom.setText(credits.getFrom());
            addEditCreditsBinding.etDate.setText(credits.getDate());
        }
    }

    private void saveCredits(){
        if(Utils.isNullOrEmpty(addEditCreditsBinding.etAmount.getText().toString())) {
            addEditCreditsBinding.etAmount.setError(" Please insert Amount");
            return;
        }   if(Utils.isNullOrEmpty(addEditCreditsBinding.etDate.getText().toString())) {
            addEditCreditsBinding.etDate.setError(" Please insert Proper date");
            return;
        }    if(Utils.isNullOrEmpty(addEditCreditsBinding.etFrom.getText().toString())) {
            addEditCreditsBinding.etFrom.setError(" Please insert Credit taken From ");
            return;
        }    if(Utils.isNullOrEmpty(addEditCreditsBinding.etTo.getText().toString())) {
            addEditCreditsBinding.etTo.setError(" Please insert credit goven To ");
            return;
        }    if(Utils.isNullOrEmpty(addEditCreditsBinding.etDescription.getText().toString())) {
            addEditCreditsBinding.etDescription.setError(" Please insert credits description");
            return;
        }
        Credits credits = new Credits();
        credits.setAmount(addEditCreditsBinding.etAmount.getText().toString());
        credits.setDate(addEditCreditsBinding.etDate.getText().toString());
        credits.setDescription(addEditCreditsBinding.etDescription.getText().toString());
        credits.setFrom(addEditCreditsBinding.etFrom.getText().toString());
        credits.setTo(addEditCreditsBinding.etTo.getText().toString());
        creditsViewModel.insertCredit(credits);
    }
}