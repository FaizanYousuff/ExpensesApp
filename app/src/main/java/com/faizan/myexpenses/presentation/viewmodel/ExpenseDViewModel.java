package com.faizan.myexpenses.presentation.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.faizan.myexpenses.DataLayer.model.Expense;
import com.faizan.myexpenses.DataLayer.remote.ExpensesRepository;
import com.faizan.myexpenses.logger.Logger;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class ExpenseDViewModel extends ViewModel {

    private static final String TAG = "ExpenseDViewModel";


    private Context context;
    private ExpensesRepository repository;

    public ExpenseDViewModel(Context context) {
        this.context = context;
        this.repository = new ExpensesRepository(context);
    }

    public void insertExpensesD(final Expense expense) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.insertExpenses(expense);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  insertExpensesD onComplete");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to Insert", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteExpenseD(final Expense expense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.deleteMonthlyExpenses(expense);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  deleteExpenseD onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateExpensesM(final Expense expense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.updateMonthlyExpense(expense);

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

    public LiveData<List<Expense>> getAllExpensesD(String month) {
        return repository.getAllExpensesD(month);
    }
}
