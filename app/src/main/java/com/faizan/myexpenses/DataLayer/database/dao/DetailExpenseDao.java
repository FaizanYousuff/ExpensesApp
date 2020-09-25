package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.faizan.myexpenses.DataLayer.model.DetailOtherExpense;
import com.faizan.myexpenses.DataLayer.model.Expense;

import java.util.List;

@Dao
public interface DetailExpenseDao {

    @Query("select * from DetailOtherExpense")
    LiveData<List<DetailOtherExpense>> getAllDetailExpense();

    @Query("select * from DetailOtherExpense where expenseId =:expenseId")
    LiveData<List<DetailOtherExpense>> getAllDetailExpense(int expenseId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetailExpense(DetailOtherExpense expense);

    @Delete
    void deleteDetailExpense(DetailOtherExpense expense);

    @Update
    void updateDetailExpense(DetailOtherExpense expense);

}
