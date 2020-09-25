package com.faizan.myexpenses.DataLayer.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;
import com.faizan.myexpenses.DataLayer.model.OtherExpense;

import java.util.List;

@Dao
public interface OtherExpenseDao {

    @Query("select * from OtherExpense")
    LiveData<List<OtherExpense>> getAllOtherExpenses();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOtherExpense(OtherExpense otherExpense);

    @Delete
    void deleteOtherExpenses(OtherExpense otherExpense);

    @Update
    void updateOtherExpense(OtherExpense otherExpense);

    @Query("select * from OtherExpense where id = :id")
    OtherExpense getOtherExpense(int id);

    @Query("update OtherExpense set spentAmount =:spent where id =:id ")
    void updateSpentAmount(int spent, int id);
}
