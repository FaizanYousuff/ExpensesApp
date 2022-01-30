package com.faizan.myexpenses.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.Utils.ContextProvider;
import com.faizan.myexpenses.Utils.DialogUtils;
import com.faizan.myexpenses.Utils.PreferenceHelper;
import com.faizan.myexpenses.presentation.listener.DialogListener;


public class SettingFragment extends Fragment {

    private SwitchCompat incomeMaskSwitch;
    private SwitchCompat setPasswordSwitch;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        incomeMaskSwitch = view.findViewById(R.id.income_amount_mask_switch);
        setPasswordSwitch = view.findViewById(R.id.password_switch);
        updateSwitchStatus();

        incomeMaskSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    PreferenceHelper.getInstance(getContext()).edit().putBoolean(Constants.ENABLE_INCOME_MASKING,true).apply();
                } else {
                    PreferenceHelper.getInstance(getContext()).edit().putBoolean(Constants.ENABLE_INCOME_MASKING,false).apply();
                }
            }
        });
        setPasswordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    DialogUtils.getInstance().showPasswordDialog(new DialogListener() {
                        @Override
                        public void okPressed(Object... params) {
                            DialogUtils.getInstance().setPasswordDialog(new DialogListener() {
                                @Override
                                public void okPressed(Object... params) {
                                    PreferenceHelper.getInstance(getContext()).edit().putBoolean(Constants.ENABLE_PASSWORD_FOR_DELETING,true).apply();

                                }

                                @Override
                                public void cancelPressed() {
                                    updateSwitchStatus();
                                }
                            });
                        }

                        @Override
                        public void cancelPressed() {
                            updateSwitchStatus();
                        }
                    },true);

                } else {
                    PreferenceHelper.getInstance(getContext()).edit().putBoolean(Constants.ENABLE_PASSWORD_FOR_DELETING,false).apply();

                }
            }
        });

    }

    private void updateSwitchStatus(){
        setPasswordSwitch.setChecked(PreferenceHelper.getInstance(ContextProvider.getInstance().getActivity()).getBoolean(Constants.ENABLE_PASSWORD_FOR_DELETING,false));
        incomeMaskSwitch.setChecked(PreferenceHelper.getInstance(ContextProvider.getInstance().getActivity()).getBoolean(Constants.ENABLE_INCOME_MASKING,false));

    }
}