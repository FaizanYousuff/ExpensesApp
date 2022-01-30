package com.faizan.myexpenses.presentation.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.ExpenseApplication;
import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.Utils.DialogUtils;
import com.faizan.myexpenses.logger.Logger;
import com.faizan.myexpenses.presentation.adapter.CreditsListAdapter;
import com.faizan.myexpenses.presentation.listener.DialogListener;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;
import com.faizan.myexpenses.presentation.viewmodel.CreditsViewModel;
import com.faizan.myexpenses.presentation.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.faizan.myexpenses.Utils.Constants.AUTHOR;
import static com.faizan.myexpenses.Utils.Constants.PENDING_CREDIT;
import static com.faizan.myexpenses.Utils.Constants.PENDING_DEBITS;

public class CreditsFragment extends Fragment {

    private static final String TAG = "CreditsFragment";

    private CreditsViewModel creditsViewModel;
    private MainViewModel mainViewModel;
    private BaseActivity baseActivity;
    private FloatingActionButton floatingActionButton;
    private TextView pendingCredits, pendingDebits;
    private int totalDebits;
    private int totalCredits;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creditsViewModel = new CreditsViewModel(ExpenseApplication.getAppContext());
        mainViewModel = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
        baseActivity = ((BaseActivity) getActivity());

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
                mainViewModel.setCreditsMutableLiveData(null);
                baseActivity.switchFragments(new AddEditCreditsFragment(), Constants.EDIT_FRAG);
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

                updateTotals(credits);
                Logger.debug(TAG, "Total Credits " + totalCredits);
                Logger.debug(TAG, "Total debits " + totalDebits);

                pendingCredits.setText(PENDING_CREDIT + totalCredits);
                pendingDebits.setText(PENDING_DEBITS + totalDebits);
                Credits dummyCredit = new Credits();
                dummyCredit.setId(0);
                dummyCredit.setTo("Amount Given TO");
                dummyCredit.setFrom("Amount From");
                dummyCredit.setAmount("Amount");
                dummyCredit.setDescription("Description");
                dummyCredit.setDate("Date");
                credits.add(0, dummyCredit);
                creditsAdapter.setCredits(credits);
            }
        });

        // For EDITING CARD VIEW
        creditsAdapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object... params) {
                mainViewModel.setCreditsMutableLiveData((Credits) params[0]);
                baseActivity.switchFragments(new AddEditCreditsFragment(), Constants.EDIT_FRAG);
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
                        creditsViewModel.deleteCredit(creditsAdapter.getCredit(viewHolder.getAdapterPosition()));

                    }

                    @Override
                    public void cancelPressed() {

                    }
                },false);
            }
        }).attachToRecyclerView(recyclerView);


        return fragmentView;
    }

    private void updateTotals(List<Credits> creditsList) {
        Logger.debug(TAG, creditsList.size() + "");
        totalDebits = 0;
        totalCredits = 0;

        for (int i = 0; i < creditsList.size(); i++) {
            if (creditsList.get(i).getFrom().equalsIgnoreCase(AUTHOR)) {
                totalCredits += Integer.parseInt(creditsList.get(i).getAmount());
            } else {
                totalDebits += Integer.parseInt(creditsList.get(i).getAmount());
            }
        }
    }
}