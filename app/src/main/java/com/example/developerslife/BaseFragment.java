package com.example.developerslife;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.developerslife.network.CacheArrayList;
import com.example.developerslife.network.NetworkServiceProvider;
import com.example.developerslife.network.Post;
import com.example.developerslife.network.Result;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

abstract class BaseFragment extends Fragment {

    protected FloatingActionButton buttonPrev;
    protected FloatingActionButton buttonNext;

    protected MaterialCardView cardView;
    protected TextView descView;

    protected View emptyView;
    protected MaterialButton reloadEmptyButton;

    protected View errorView;
    protected MaterialButton reloadErrorButton;

    protected ProgressBar loadingView;

    protected boolean postsIsReached = false;

    private CacheArrayList<Post> cache = getCache();
    private int page = getFirstPage();
    private String pagePath = getPagePath();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        buttonPrev = view.findViewById(R.id.button_prev);
        buttonNext = view.findViewById(R.id.button_next);

        cardView = view.findViewById(R.id.card_view);
        descView = view.findViewById(R.id.description_view);

        emptyView = view.findViewById(R.id.empty_view);
        reloadEmptyButton = view.findViewById(R.id.button_reload);

        errorView = view.findViewById(R.id.error_view);
        reloadErrorButton = view.findViewById(R.id.button_reload_error);

        loadingView = view.findViewById(R.id.loading_view);

        bindListeners();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadFirstPage();
    }

    protected void bindListeners() {
        buttonPrev.setOnClickListener(v -> {
            if (cache.hasPrevious()) {
                descView.setText(cache.previous().getDescription());
                buttonNext.setVisibility(View.VISIBLE);
                setSuccessState();
            }
            if (!cache.hasPrevious()) {
                buttonPrev.setVisibility(View.INVISIBLE);
            }

        });

        buttonNext.setOnClickListener(v -> {
            if (cache.hasNext()) {
                descView.setText(cache.next().getDescription());
                buttonPrev.setVisibility(View.VISIBLE);
            } else {
                if (!postsIsReached) {
                    loadNextPage();
                }
            }
        });

        reloadEmptyButton.setOnClickListener(v -> {
            loadFirstPage();
        });

        reloadErrorButton.setOnClickListener(v -> {
            if (page == 0) {
                loadFirstPage();
            } else {
                loadNextPage();
            }
        });
    }

    @SuppressLint("CheckResult")
    protected void loadFirstPage() {
        NetworkServiceProvider
                .buildService()
                .getPosts(pagePath, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new SingleObserver<Result>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                  setLoadingState();
                            }

                            @Override
                            public void onSuccess(@NonNull Result result) {
                                if (result.getResult().isEmpty()) {
                                    setEmptyState();
                                    postsIsReached = true;
                                    return;
                                }

                                cache.addAll(result.getResult());
                                descView.setText(cache.next().getDescription());
                                buttonNext.setVisibility(View.VISIBLE);

                                setSuccessState();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                setErrorState();
                            }
                        }
                );
    }

    @SuppressLint("CheckResult")
    protected void loadNextPage() {
        page++;
        NetworkServiceProvider
                .buildService()
                .getPosts(pagePath, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        setLoadingState();
                    }

                    @Override
                    public void onSuccess(@NonNull Result result) {
                        if (result.getResult().isEmpty()) {
                            postsIsReached = true;
                            buttonNext.setVisibility(View.INVISIBLE);
                            return;
                        }

                        cache.addAll(result.getResult());
                        descView.setText(cache.next().getDescription());
                        buttonNext.setVisibility(View.VISIBLE);

                        setSuccessState();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        setErrorState();
                    }
                });
    }

    protected abstract CacheArrayList<Post> getCache();

    protected abstract int getFirstPage();

    protected abstract String getPagePath();

    protected void setEmptyState() {
        cardView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    protected void setSuccessState() {
        cardView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    protected void setErrorState() {
        cardView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    protected void setLoadingState() {
        buttonNext.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }
}
