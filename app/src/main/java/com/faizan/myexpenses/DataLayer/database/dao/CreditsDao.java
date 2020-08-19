package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.faizan.myexpenses.DataLayer.model.Credits;

import java.util.List;

@Dao
public interface CreditsDao {

    @Query("select * from credits_table")
    LiveData<List<Credits>> getAllCredits();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCredit(Credits ... credits);
}
