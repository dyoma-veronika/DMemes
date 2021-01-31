package com.example.developerslife;

import com.google.android.material.card.MaterialCardView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.developerslife.network.NetworkServiceProvider;
import com.example.developerslife.network.Post;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

class BaseFragment extends Fragment {

    private Button buttonPrev;
    private Button buttonNext;

    private MaterialCardView cardView;
    private TextView descView;

    List<Post> localCache = new LinkedList<>();
    ListIterator<Post> listIterator = localCache.listIterator();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        buttonPrev = view.findViewById(R.id.button_prev);
        buttonNext = view.findViewById(R.id.button_next);

        cardView = view.findViewById(R.id.card_view);
        descView = view.findViewById(R.id.description_view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadFirstPage();
    }

    protected void loadFirstPage() {
        NetworkServiceProvider
                .buildService()
                .getPosts("top", 0)
                .subscribe(
                        result -> {
                            if (result.getResult().isEmpty()) {
                                System.out.println("is empty");
                                return;
                            }

                            localCache.addAll(result.getResult());
                            descView.setText(listIterator.next().getDescription());
                        },
                        throwable -> {
                            System.out.println("error");
                        }
                );
    }
}
