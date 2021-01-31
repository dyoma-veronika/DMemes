package com.example.developerslife;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.view.View;

import com.example.developerslife.network.CacheArrayList;
import com.example.developerslife.network.NetworkServiceProvider;
import com.example.developerslife.network.Post;
import com.example.developerslife.network.Result;

public class TopFragment extends BaseFragment {

    int page = 0;
    CacheArrayList<Post> cache = new CacheArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void loadFirstPage() {
        NetworkServiceProvider
                .buildService()
                .getPosts("top", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new SingleObserver<Result>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }
                            @Override
                            public void onSuccess(@NonNull Result result) {
                                if (result.getResult().isEmpty()) {
                                    System.out.println("is empty");
                                    postsIsReached = true;
                                    return;
                                }

                                cache.addAll(result.getResult());
                                descView.setText(cache.next().getDescription());
                                buttonNext.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                System.out.println("error");
                            }
                        }
                );
    }

    @SuppressLint("CheckResult")
    @Override
    protected void loadNextPage() {
        page++;
        NetworkServiceProvider
                .buildService()
                .getPosts("top", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Result result) {
                        if (result.getResult().isEmpty()) {
                            System.out.println("is empty");
                            postsIsReached = true;
                            buttonNext.setVisibility(View.INVISIBLE);
                            return;
                        }

                        cache.addAll(result.getResult());
                        descView.setText(cache.next().getDescription());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("error");

                    }
                });
    }

    @Override
    protected CacheArrayList<Post> getCache() {
        return cache;
    }
}