package com.faizan.myexpenses.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faizan.myexpenses.R;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.model.MenuItems;
import com.faizan.myexpenses.presentation.adapter.MenuAdapter;
import com.faizan.myexpenses.presentation.listener.OnItemClickListener;

import java.util.LinkedList;
import java.util.List;


public class HomeFragment extends Fragment {
    private BaseActivity baseActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        baseActivity = ((BaseActivity) getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Adapter
        MenuAdapter menuAdapter = new MenuAdapter();
        menuAdapter.setMenuItemsList(getMenuItems());

        // Recycler view
        RecyclerView recyclerView = view.findViewById(R.id.rv_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recyclerView.setAdapter(menuAdapter);


        menuAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Object... Params) {
                MenuItems items = (MenuItems) Params[0];

                switch (items.getText()) {
                    case Constants.MY_EXPENSES:
                        baseActivity.switchFragments(new ExpensesListFragment(), Constants.M_EXPENSES_FRAG);
                        break;

                    case Constants.CREDIT_LIST:
                        baseActivity.switchFragments(new CreditsFragment(), Constants.CREDIT_FRAG);
                        break;

                    case Constants.OTHER_EXPENSES:
                        baseActivity.switchFragments(new OtherExpenseFragment(), Constants.OTHER_EXPENSE);
                        break;

                    default:
                        // baseActivity.switchFragments(new HomeFragment(), Constants.HOME_FRAG);
                        break;
                }
            }
        });
    }

    private List<MenuItems> getMenuItems() {
        List<MenuItems> items = new LinkedList<>();
        MenuItems menuItems = new MenuItems();
        menuItems.setId(R.drawable.expenses);
        menuItems.setText(Constants.MY_EXPENSES);
        items.add(menuItems);

        menuItems = new MenuItems();
        menuItems.setId(R.drawable.cr_dr);
        menuItems.setText(Constants.CREDIT_LIST);
        items.add(menuItems);
        menuItems = new MenuItems();

        menuItems.setId(R.drawable.my_expenses);
        menuItems.setText(Constants.OTHER_EXPENSES);
        items.add(menuItems);

        menuItems = new MenuItems();
        menuItems.setId(R.drawable.set);
        menuItems.setText(Constants.SETTINGS);
        items.add(menuItems);
        return items;

    }
}