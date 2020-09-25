package com.faizan.myexpenses.presentation.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.faizan.myexpenses.DataLayer.model.OtherExpense;
import com.faizan.myexpenses.DataLayer.remote.OtherExpenseRepository;
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

public class OtherExpenseViewModel {

    private static final String TAG = "OtherExpenseViewModel";

    private Context context;
    private OtherExpenseRepository repository;

    public OtherExpenseViewModel(Context context) {
        this.context = context;
        this.repository = new OtherExpenseRepository(context);
    }

    public void insertOtherExpense(final OtherExpense otherExpense) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.insertOtherExpense(otherExpense);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  insertOtherExpense onComplete");

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Insert", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteOtherExpense(final OtherExpense otherExpense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.deleteOtherExpense(otherExpense);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  deleteOtherExpense onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateOtherExpense(final OtherExpense otherExpense) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.updateOtherExpense(otherExpense);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  updateOtherExpense onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateSpentAmount(final int spentAmount, final int id) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                repository.updateSpentAmount(spentAmount, id);

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


    public LiveData<List<OtherExpense>> getAllOtherExpenses() {
        return repository.getAllOtherExpenses();
    }

    public void getMonthlyExpense(final int id, final DatabaseListener listener) {

        ThreadPoolService.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                OtherExpense otherExpense = repository.getOtherExpense(id);
                listener.dataFound(otherExpense);
            }
        });
    }
}
