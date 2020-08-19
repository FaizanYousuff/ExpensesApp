package com.faizan.myexpenses.presentation.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.DataLayer.remote.CreditsRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CreditsViewModel extends ViewModel {

    private CreditsRepository creditsRepository;
    private Context context;

    public CreditsViewModel(Context context) {
        this.context = context;
         this.creditsRepository = new CreditsRepository(context);
    }

    public CreditsViewModel() {
    }

    public void insertCredit(final Credits credits){


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                creditsRepository.insertCredits(credits);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(context, "Inserted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Insert", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public LiveData<List<Credits>> getAllCredits(){
        return creditsRepository.getAllCredits();
    }
}
