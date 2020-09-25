package com.faizan.myexpenses.presentation.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.faizan.myexpenses.DataLayer.model.Credits;
import com.faizan.myexpenses.DataLayer.remote.CreditsRepository;
import com.faizan.myexpenses.Utils.Constants;
import com.faizan.myexpenses.logger.Logger;
import com.faizan.myexpenses.presentation.view.BaseActivity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class CreditsViewModel extends ViewModel {

    private static final String TAG = "CreditsViewModel";
    private CreditsRepository creditsRepository;
    private Context context;

    public CreditsViewModel(Context context) {
        this.context = context;
        this.creditsRepository = new CreditsRepository(context);
    }

    public CreditsViewModel() {
    }

    public void insertCredit(final Credits credits, final BaseActivity baseActivity) {


        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                creditsRepository.insertCredits(credits);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  insertCredit onComplete");
                baseActivity.switchFragments(Constants.HOME_FRAG);

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Insert", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deleteCredit(final Credits credits) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                creditsRepository.deleteCredits(credits);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  deleteCredit onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateCredits(final Credits credits) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                creditsRepository.updateCredits(credits);

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                Logger.debug(TAG, "  updateCredits onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<Credits>> getAllCredits() {
        return creditsRepository.getAllCredits();
    }
}
