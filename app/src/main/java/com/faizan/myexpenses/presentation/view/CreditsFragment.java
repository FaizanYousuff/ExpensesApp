package com.faizan.myexpenses.presentation.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.ExpenseApplication;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.presentation.adapter.CreditsListAdapter;
import com.faizan.myexpenses.presentation.viewmodel.CreditsViewModel;
import com.faizan.myexpenses.presentation.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CreditsFragment extends Fragment {

    private static final String TAG = "CreditsFragment";

    private CreditsViewModel creditsViewModel;
    private MainViewModel mainViewModel;
    private BaseActivity baseActivity;


    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private FloatingActionButton floatingActionButton;
    private TextView pendingCredits,pendingDebits;
    private int totalDebits;
    private int totalCredits;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creditsViewModel =new CreditsViewModel(ExpenseApplication.getAppContext());
        mainViewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        baseActivity = ((BaseActivity)getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_credits, container, false);
        pendingCredits = fragmentView.findViewById(R.id.text_pending_credit);
        pendingDebits = fragmentView.findViewById(R.id.text_pending_debit);

        //RECYCLER view
        RecyclerView recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        final CreditsListAdapter creditsAdapter = new CreditsListAdapter();
        recyclerView.setAdapter(creditsAdapter);


        // FAB BUTTON
        floatingActionButton = fragmentView.findViewById(R.id.btn_add_credit);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             baseActivity.switchFragments(new AddEditCreditsFragment());
            }
        });


        //VIEW MODEL
        // Here we are not creating Instance of view model since each time oncreate is called we will get new inbstance of view model
        // Instead we tell android to create view model instance whenever reauired and and take old view model instance whene ever required
        // and we pass our activity our fragment so that view will respect lifecycle of that activity and fragmnet and
        // vm get destroyed if corresponding activity/freagment is destroyed
        creditsViewModel.getAllCredits().observe(this, new Observer<List<Credits>>() {
            @Override
            public void onChanged(@Nullable List<Credits> credits) {
                // update RecyclerView

            /*    updateTotals(credits);
                Logger.debug(TAG,"Total Credits "+totalCredits);
                Logger.debug(TAG,"Total debits "+totalDebits);

                pendingCredits.setText(PENDING_CREDIT+totalCredits);
                pendingDebits.setText(PENDING_DEBITS+totalDebits);*/
                creditsAdapter.setCredits(credits);
            }
        });

        // For EDITING CARD VIEW
        creditsAdapter.setOnItemClickLitener(new CreditsListAdapter.onItemClickLitener() {
            @Override
            public void onItemClick(Credits credits) {
                mainViewModel.setCreditsMutableLiveData(credits);
                baseActivity.switchFragments(new AddEditCreditsFragment());

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
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
               // showPasswordDialog(creditsAdapter.getCredit(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);


        return fragmentView;
    }
}