package com.faizan.myexpenses.DataLayer.remote;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.faizan.myexpenses.DataLayer.database.MyDatabase;
import com.faizan.myexpenses.DataLayer.model.Credits;

import java.util.List;


public class CreditsRepository {

    private Context context;

    private MyDatabase myDatabase;

    public CreditsRepository(Context context) {
        this.context = context;
        myDatabase = MyDatabase.getInstance(context);
    }

    // Credits
    public void insertCredits(Credits credits){
        myDatabase.creditsDao().insertCredit(credits);
    }

    public LiveData<List<Credits>> getAllCredits(){
        return  myDatabase.creditsDao().getAllCredits();
    }

}
