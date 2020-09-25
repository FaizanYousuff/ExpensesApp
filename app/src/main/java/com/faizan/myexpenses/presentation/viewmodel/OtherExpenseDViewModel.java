package com.faizan.myexpenses.presentation.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.model.DetailOtherExpense;
import com.faizan.myexpenses.DataLayer.remote.DetailOtherExpenseRepository;
import com.faizan.myexpenses.logger.Logger;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class OtherExpenseDViewModel {

    private static final String TAG = "OtherExpenseDViewModel";

    private Context context;
    private DetailOtherExpenseRepository repository;

    public OtherExpenseDViewModel(Context context) {
        this.context = context;
        this.repository = new DetailOtherExpenseRepository(context);
    }

    public void insertDetailOtherExpense(final DetailOtherExpense expense) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.insertDetailOtherExpense(expense);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  insertDetailOtherExpense onComplete");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to Insert", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteDetailOtherExpense(final DetailOtherExpense expense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.deleteDetailOtherExpense(expense);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  deleteDetailOtherExpense onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateDetailOtherExpense(final DetailOtherExpense expense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.updateDetailOtherExpense(expense);

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


    public LiveData<List<DetailOtherExpense>> getDetailOtherExpense(int expenseId) {
        return repository.getDetailOtherExpense(expenseId);
    }
}
