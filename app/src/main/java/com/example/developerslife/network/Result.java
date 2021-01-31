package com.example.developerslife.network;

import java.util.List;

public class Result {

    private List<Post> result;
    private int totalCount;

    public Result(List<Post> result, int totalCount) {
        this.result = result;
        this.totalCount = totalCount;
    }

    public List<Post> getResult() {
        return result;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
