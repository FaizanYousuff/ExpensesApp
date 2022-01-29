package com.faizan.myexpenses.presentation.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        switchFragments(new HomeFragment());
                        break;

                    case R.id.nav_credits:
                        switchFragments(new CreditsFragment(), Constants.CREDIT_LIST_FRAG);
                        break;
                    case R.id.nav_expenses:
                        switchFragments(new ExpensesListFragment(), Constants.M_EXPENSES_FRAG);
                        break;

                    default:
                        switchFragments(new HomeFragment());
                        break;
                }
                return true;
            }
        });
        switchFragments(new HomeFragment());
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            // super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}