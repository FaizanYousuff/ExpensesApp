package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;

import java.util.List;

@Dao
public interface MonthlyExpenseDao {

    @Query("select * from monthlyexpense")
    LiveData<List<MonthlyExpense>> getAllMonthlyExpenses();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMonthlyExpense(MonthlyExpense monthlyExpense);

    @Delete
    void  deleteMonthlyExpenses(MonthlyExpense monthlyExpense);

    @Update
    void updateMonthlyExpense(MonthlyExpense monthlyExpense);

    @Query("select * from monthlyexpense where monthNumber = :month")
    MonthlyExpense getMonthlyExpense(int month);

    @Query("update monthlyexpense set expenses =:spent where monthNumber =:month ")
    void updateSpentAmount(String spent, int month);
}
