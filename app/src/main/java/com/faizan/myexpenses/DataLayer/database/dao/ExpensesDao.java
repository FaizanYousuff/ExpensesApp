package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.faizan.myexpenses.DataLayer.model.Expense;

import java.util.List;

@Dao
public interface ExpensesDao {

    @Query("select * from expense_table")
    LiveData<List<Expense>> getAllExpense();

    @Query("select * from expense_table where expenseOfMonth =:month")
    LiveData<List<Expense>> getAllExpense(String month);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpense(Expense expense);

    @Delete
    void  deleteExpenses(Expense expense);

    @Update
    void updateExpense(Expense expense);

}
