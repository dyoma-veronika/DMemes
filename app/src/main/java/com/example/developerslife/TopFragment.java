package com.example.developerslife;

import com.example.developerslife.network.CacheArrayList;
import com.example.developerslife.network.Post;

public class TopFragment extends BaseFragment {

    @Override
    protected CacheArrayList<Post> getCache() {
        return new CacheArrayList<>();
    }

    @Override
    protected int getFirstPage() {
        return 0;
    }

    @Override
    protected String getPagePath() {
        return "top";
    }
}