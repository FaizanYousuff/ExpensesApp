package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.faizan.myexpenses.DataLayer.model.Expense;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Query("select * from expense_table")
    LiveData<List<Expense>> getAllExpense();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpense(Expense expense);


}
