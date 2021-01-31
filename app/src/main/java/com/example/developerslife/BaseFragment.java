package com.example.developerslife;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.developerslife.network.CacheArrayList;
import com.example.developerslife.network.NetworkServiceProvider;
import com.example.developerslife.network.Post;
import com.example.developerslife.network.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

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

    protected boolean postsIsReached = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        buttonPrev = view.findViewById(R.id.button_prev);
        buttonNext = view.findViewById(R.id.button_next);

        cardView = view.findViewById(R.id.card_view);
        descView = view.findViewById(R.id.description_view);

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
            if (getCache().hasPrevious()) {
                descView.setText(getCache().previous().getDescription());
                buttonNext.setVisibility(View.VISIBLE);
            }
            if (!getCache().hasPrevious()) {
                buttonPrev.setVisibility(View.INVISIBLE);
            }

        });

        buttonNext.setOnClickListener(v -> {
            if (getCache().hasNext()) {
                descView.setText(getCache().next().getDescription());
                buttonPrev.setVisibility(View.VISIBLE);
            } else {
                if (!postsIsReached) {
                    loadNextPage();
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    protected abstract void loadFirstPage();

    @SuppressLint("CheckResult")
    protected abstract void loadNextPage();

    protected  abstract CacheArrayList<Post> getCache();

}
