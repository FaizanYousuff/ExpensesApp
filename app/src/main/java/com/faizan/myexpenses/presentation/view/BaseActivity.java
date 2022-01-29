package com.faizan.myexpenses.presentation.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.ContextProvider;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextProvider.getInstance().setActivity(this);
    }

    protected void switchFragments(Fragment fragment, String stackName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(stackName);
        transaction.commit();
    }

    protected void switchFragments(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }


    public void switchFragments(String stackName) {
        FragmentManager fm = getSupportFragmentManager();
        // it will not pop due to inclusive second argument we passed
        // if we pass second argument as zero it will pop all fragments

        fm.popBackStack(stackName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStack(){
        getSupportFragmentManager().popBackStack();
    }
}
