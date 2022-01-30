package com.faizan.myexpenses.presentation.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.faizan.myexpenses.DataLayer.model.MonthlyExpense;
import com.faizan.myexpenses.DataLayer.remote.MonthlyExpenseRepository;
import com.faizan.myexpenses.Utils.ThreadPoolService;
import com.faizan.myexpenses.logger.Logger;
import com.faizan.myexpenses.presentation.listener.DatabaseListener;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ExpenseMViewModel extends ViewModel {

    private static final String TAG = "ExpenseMViewModel";

    private Context context;
    private MonthlyExpenseRepository repository;

    public ExpenseMViewModel(Context context) {
        this.context = context;
        this.repository = new MonthlyExpenseRepository(context);
    }

    public void insertExpensesM(final MonthlyExpense monthlyExpense) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.insertMonthlyExpense(monthlyExpense);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  insertExpensesM onComplete");

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Insert", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteExpenseM(final MonthlyExpense monthlyExpense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.deleteMonthlyExpenses(monthlyExpense);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Deleted Monthly Expense Record", Toast.LENGTH_SHORT).show();
                Logger.debug(TAG, "  deleteExpenseM onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateExpensesM(final MonthlyExpense monthlyExpense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.updateMonthlyExpense(monthlyExpense);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  updateExpensesM onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateSpentAmount(final String income, final int month) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.updateSpentAmount(income, month);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  updateSpentAmount onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Update Income", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public LiveData<List<MonthlyExpense>> getAllExpensesM() {
        return repository.getAllMonthlyExpenses();
    }

    public void getMonthlyExpense(final int month, final DatabaseListener listener) {

        ThreadPoolService.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                MonthlyExpense monthlyExpense = repository.getMonthlyExpense(month);
                listener.dataFound(monthlyExpense);
            }
        });
    }
}
