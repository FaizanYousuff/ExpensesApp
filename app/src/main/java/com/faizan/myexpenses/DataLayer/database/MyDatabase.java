package com.faizan.myexpenses.DataLayer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.faizan.myexpenses.DataLayer.database.dao.CreditsDao;
import com.faizan.myexpenses.DataLayer.database.dao.DetailExpenseDao;
import com.faizan.myexpenses.DataLayer.database.dao.ExpensesDao;
import com.faizan.myexpenses.DataLayer.database.dao.MonthlyExpenseDao;
import com.faizan.myexpenses.DataLayer.database.dao.OtherExpenseDao;
import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.DataLayer.model.DetailOtherExpense;
import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;
import com.faizan.myexpenses.DataLayer.model.OtherExpense;

@Database(entities = {Credits.class, Expense.class, MonthlyExpense.class, OtherExpense.class,DetailOtherExpense.class} ,version = 1,exportSchema = false)
abstract public class MyDatabase extends RoomDatabase {

    private final static String DB_NAME = "myExpensesDb";

    public static MyDatabase instance;

    public abstract CreditsDao creditsDao();

    public abstract ExpensesDao expensesDao();

    public abstract OtherExpenseDao otherExpenseDao();

    public abstract DetailExpenseDao detailExpenseDao();

    public abstract MonthlyExpenseDao monthlyExpenseDao();


    public synchronized static  MyDatabase getInstance(Context context){

        if (instance == null) {
            instance = Room.databaseBuilder(context, MyDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
