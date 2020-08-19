package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.DataLayer.model.Income;

import java.util.List;

@Dao
public interface IncomeDao {

    @Query("select * from income_table")
    LiveData<List<Income>> getAllIncome();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIncome(Income income);
}
