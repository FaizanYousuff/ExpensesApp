package com.faizan.myexpenses.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.faizan.myexpenses.DataLayer.model.Credits;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Credits> creditsMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Credits> getCreditsMutableLiveData() {
        return creditsMutableLiveData;
    }

    public void setCreditsMutableLiveData(Credits credits) {
        creditsMutableLiveData.setValue(credits);
    }
}
